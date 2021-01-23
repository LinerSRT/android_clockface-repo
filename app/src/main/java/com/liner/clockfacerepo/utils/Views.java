package com.liner.clockfacerepo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
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
        window.setBackgroundDrawable(drawable);
    }
    public static void setNavigationBarColor(Activity activity, @ColorInt int color){
        activity.getWindow().setNavigationBarColor(color);
    }

    public static int getStatusBarSize(@NonNull Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
        );
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getNavigationBarSize(@NonNull Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(
                "navigation_bar_height",
                "dimen",
                "android"
        );
        return resources.getDimensionPixelSize(resourceId);
    }
}
