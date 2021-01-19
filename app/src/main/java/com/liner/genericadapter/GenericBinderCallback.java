package com.liner.genericadapter;


import androidx.annotation.NonNull;

public interface GenericBinderCallback<T, S extends GenericBinder<T>> {
    void itemBound(@NonNull S binder, @NonNull T model);
}
