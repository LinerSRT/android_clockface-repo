package com.liner.clockfacerepo.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class Views {
    public static void setStatusBarBackground(Activity activity, int resourceID) {
        setStatusBarBackground(activity, ContextCompat.getDrawable(activity, resourceID));
    }

    public static void setStatusBarBackground(Activity activity, Bitmap bitmap) {
        setStatusBarBackground(activity, new BitmapDrawable(activity.getResources(), bitmap));
    }

    public static void setStatusBarBackground(Activity activity, Drawable drawable) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
    }
}
