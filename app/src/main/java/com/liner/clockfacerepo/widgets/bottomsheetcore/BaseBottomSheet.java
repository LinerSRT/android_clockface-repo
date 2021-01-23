

package com.liner.clockfacerepo.widgets.bottomsheetcore;

import android.app.Activity;
import android.view.View;
import android.view.animation.Interpolator;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liner.clockfacerepo.utils.Views;
import com.liner.clockfacerepo.widgets.bottomsheetcore.config.BaseConfig;


public abstract class BaseBottomSheet extends BottomSheetContainer {
    private boolean tintNavigationBar = true;
    @ColorInt
    private int originalNavigationBarColor;
    @ColorInt
    private int backgroundColor;

    public BaseBottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
        backgroundColor = config.getSheetBackgroundColor();
        originalNavigationBarColor = hostActivity.getWindow().getNavigationBarColor();
        setCallback(new Callback() {
            @Override
            public void onDismiss(@NonNull BottomSheet bottomSheet) {
                Views.setNavigationBarColor(hostActivity, originalNavigationBarColor);
                BaseBottomSheet.this.onDismiss();
            }

            @Override
            public void onShow(@NonNull BottomSheet bottomSheet) {
                Views.setNavigationBarColor(hostActivity, backgroundColor);
                BaseBottomSheet.this.onShow();
            }
        });
    }

    public abstract void onShow();
    public abstract void onDismiss();
}