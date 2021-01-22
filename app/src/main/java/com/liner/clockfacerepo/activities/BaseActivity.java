package com.liner.clockfacerepo.activities;

import android.os.Handler;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.liner.clockfacerepo.R;
import com.liner.clockfacerepo.utils.Views;

public abstract class BaseActivity extends AppCompatActivity {
    private final SparseArray<View> sparseArray = new SparseArray<>();

    @Override
    protected void onStart() {
        super.onStart();
        Views.setStatusBarBackground(this, R.color.background);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.background));
    }


    @SuppressWarnings("unchecked")
    protected <V> V find(@IdRes int id) {
        View view = sparseArray.get(id);
        if (view == null) {
            view = findViewById(id);
            sparseArray.put(id, view);
        }
        return (V) view;
    }

    public void onUI(Runnable runnable){
        onUI(runnable, 0);
    }

    public void onUI(Runnable runnable, int delay){
        new Handler(getMainLooper()).postDelayed(runnable, delay);
    }

    public void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void showNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
}
