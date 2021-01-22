package com.liner.clockfacerepo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liner.clockfacerepo.R;
import com.liner.clockfacerepo.widgets.YSTextView;

public class SplashActivity extends BaseActivity {
    private ImageView clockfaceLogo;
    private ImageView linerLogo;
    private ProgressBar progressBar;
    private YSTextView clockfaceApplicationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        setContentView(R.layout.splash_activity);
        clockfaceLogo = find(R.id.splashClockfaceLogo);
        linerLogo = find(R.id.splashLinerLogo);
        progressBar = find(R.id.splashProgress);
        clockfaceApplicationName = find(R.id.splashApplicationName);
        animateSplash();
        //TODO Check user & check cached and saved clockskin & check internet connection
        onUI(() -> startActivity(new Intent(getApplicationContext(), MainActivity.class)), 4000);
    }


    private void animateSplash() {
        clockfaceLogo.setAlpha(0f);
        clockfaceApplicationName.setAlpha(0f);
        linerLogo.setAlpha(0f);
        new Handler().postDelayed(() -> {
            clockfaceLogo.animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            clockfaceApplicationName.animate()
                    .alpha(1f)
                    .translationY(-(clockfaceApplicationName.getHeight() / 2f))
                    .setDuration(500)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
            linerLogo.animate()
                    .alpha(1f)
                    .translationY(-(linerLogo.getHeight() / 2f))
                    .setDuration(400)
                    .setInterpolator(new OvershootInterpolator())
                    .setStartDelay(1000)
                    .start();
        }, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}