package com.liner.genericadapter;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

public abstract class GenericBinder<T> {
    public View view;
    private final SparseArray<View> sparseArray = new SparseArray<>();

    void setView(@NonNull View view) {
        this.view = view;
    }

    public abstract void init();

    public abstract void bind(@NonNull T model);

    @SuppressWarnings("unchecked")
    protected <V> V find(@IdRes int id) {
        View view = sparseArray.get(id);
        if (view == null) {
            view = f(id);
            sparseArray.put(id, view);
        }
        return (V) view;
    }

    private View f(@IdRes int id) {
        return view.findViewById(id);
    }

    public abstract int getDragDirections();

    public abstract int getSwipeDirections();
}
