package com.liner.clockfacerepo.firebase;

import android.util.Log;

public class FireLog {
    private static String TAG = FireLog.class.getSimpleName();

    public static void d(String s){
        Log.d(TAG, s);
    }
    public static void e(String s){
        Log.e(TAG, s);
    }
    public static void i(String s){
        Log.i(TAG, s);
    }
}
