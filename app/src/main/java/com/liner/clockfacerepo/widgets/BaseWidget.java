package com.liner.clockfacerepo.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

public abstract class BaseWidget extends FrameLayout {
    protected Context context;
    protected LayoutInflater inflater;

    public BaseWidget(Context context) {
        this(context, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    public BaseWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        inflater.inflate(getLayoutResource(), this);
        onCreate();
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void onCreate();
}
