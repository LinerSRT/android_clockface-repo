package com.liner.clockfacerepo.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.liner.clockfacerepo.Config;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class FirebaseDatabaseValueCallback<T> {
    private final Query query;
    private final List<T> values;
    private final List<String> keys;
    private final HashMap<String, DatabaseReference> databaseReferenceHashMap;
    private final ChildEventListener listener = new ChildEventListener() {
        String key;
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            key = snapshot.getKey();
            if(!databaseReferenceHashMap.containsKey(key))
                databaseReferenceHashMap.put(key, snapshot.getRef());
            if(!keys.contains(key)){
                T value = getValue(snapshot);
                int position;
                if(previousChildName == null){
                    addValue(key, value);
                    position = 0;
                } else {
                    int previousPosition = keys.indexOf(previousChildName);
                    int nextPosition = previousPosition + 1;
                    if(nextPosition == values.size()){
                        addValue(key, value);
                    } else {
                        addValue(key, value, nextPosition);
                    }
                    position = nextPosition;
                }
                valueAdded(key, value, position, snapshot.getRef());
                if(Config.Firebase.ENABLE_LOGGING){
                    FireLog.d(getClass().getSimpleName()+" | Added new value: "+value.toString()+", with: "+key+" key, to: "+position+" position");
                }
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            key = snapshot.getKey();
            if(databaseReferenceHashMap.containsKey(key)){
                databaseReferenceHashMap.remove(key);
                databaseReferenceHashMap.put(key, snapshot.getRef());
            }
            if(keys.contains(key)){
                int position = keys.indexOf(key);
                T value = getValue(snapshot);
                setValue(key, value, position);
                valueChanged(key, value, position, snapshot.getRef());
                if(Config.Firebase.ENABLE_LOGGING){
                    FireLog.d(getClass().getSimpleName()+" | Value has been changed: "+value.toString()+", with: "+key+" key, in: "+position+" position");
                }
            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            key = snapshot.getKey();
            databaseReferenceHashMap.remove(key);
            if(keys.contains(key)){
                int position = keys.indexOf(key);
                T value = getValue(snapshot);
                removeValue(key, value);
                valueRemoved(key, value, position, snapshot.getRef());
                if(Config.Firebase.ENABLE_LOGGING){
                    FireLog.d(getClass().getSimpleName()+" | Value has been removed: "+value.toString()+", with: "+key+" key, from: "+position+" position");
                }
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            key = snapshot.getKey();
            int position = keys.indexOf(key);
            T value = getValue(snapshot);
            removeValue(key, value);
            int newPosition;
            if(previousChildName == null) {
                addValue(key, value);
                newPosition = keys.size()-1;
            } else {
                int previousPosition = keys.indexOf(previousChildName);
                int nextPosition = previousPosition + 1;
                if(nextPosition == keys.size())
                    addValue(key, value);
                else
                    addValue(key, value, nextPosition);
                newPosition = nextPosition;
            }
            valueMoved(key, value, position, newPosition);
            if(Config.Firebase.ENABLE_LOGGING){
                FireLog.d(getClass().getSimpleName()+" | Value has been moved: "+value.toString()+", with: "+key+" key, from: "+position+" position, to: "+position+" position");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public FirebaseDatabaseValueCallback(String referencePath, int limit){
        this(FirebaseDatabase.getInstance().getReference(referencePath).limitToLast(limit));
    }

    public FirebaseDatabaseValueCallback(Query query){
        this(query, new ArrayList<>(), new ArrayList<>());
    }

    public FirebaseDatabaseValueCallback(Query query, List<T> values, List<String> keys){
        this.query = query;
        this.values = values;
        this.keys = keys;
        this.databaseReferenceHashMap = new HashMap<>();
    }

    public void start(){
        query.addChildEventListener(listener);
    }

    public void stop(){
        query.removeEventListener(listener);
    }

    public HashMap<String, DatabaseReference> getDatabaseReferences() {
        return databaseReferenceHashMap;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<T> getValues() {
        return values;
    }

    public int getValuePosition(T value){
        return values != null && values.size() > 0 ? values.indexOf(value):-1;
    }

    public boolean containValue(T value){
        return values != null && values.contains(value);
    }

    @SuppressWarnings("unchecked")
    private T getValue(DataSnapshot dataSnapshot){
        return dataSnapshot.getValue((Class<T>) ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    private void addValue(String key, T value, int position){
        keys.add(position, key);
        values.add(position, value);
    }

    private void addValue(String key, T value){
        addValue(key, value, keys.size()-1);
    }

    private void removeValue(String key, T value){
        keys.remove(key);
        values.remove(value);
    }

    private void setValue(String key, T value, int position){
        keys.set(position, key);
        values.set(position, value);
    }

    public abstract void valueAdded(String key, T value, int position, DatabaseReference reference);
    public abstract void valueChanged(String key, T value, int position, DatabaseReference reference);
    public abstract void valueRemoved(String key, T value, int position, DatabaseReference reference);
    public abstract void valueMoved(String key, T value, int position, int newPosition);

}
