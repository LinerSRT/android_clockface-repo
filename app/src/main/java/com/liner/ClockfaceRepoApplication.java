package com.liner;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.liner.preferencemanager.PM;

public class ClockfaceRepoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PM.init(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
