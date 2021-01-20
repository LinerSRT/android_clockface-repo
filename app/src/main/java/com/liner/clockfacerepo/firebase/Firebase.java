package com.liner.clockfacerepo.firebase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liner.clockfacerepo.Config;
import com.liner.clockfacerepo.firebase.auth.Auth;

public class Firebase {
    private static Firebase firebase;
    private Context context;
    private Auth auth;

    public static Firebase getInstance(Context context) {
        return firebase == null ? firebase = new Firebase(context) : firebase;
    }

    private Firebase(Context context) {
        this.context = context;
        this.auth = Auth.getInstance(context);
    }

    public DatabaseReference getUsersDatabase(){
        return getDatabase(Config.Firebase.DATABASE_USERS_PATH);
    }

    public DatabaseReference getWatchfacesDatabase(){
        return getDatabase(Config.Firebase.DATABASE_WATCHFACE_PATH);
    }

    public DatabaseReference getDatabase(String reference) {
        if(auth.isLoggedIn() || auth.isAnonymous())
            return FirebaseDatabase.getInstance().getReference(reference);
        throw new RuntimeException("This user not allowed to connect into database! UID: "+auth.getUID());
    }
}
