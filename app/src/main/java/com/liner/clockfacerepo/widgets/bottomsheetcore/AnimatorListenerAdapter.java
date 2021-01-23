package com.liner.clockfacerepo.widgets.bottomsheetcore;

import android.animation.Animator;


public abstract class AnimatorListenerAdapter implements Animator.AnimatorListener {
    private boolean isCanceled;

    protected AnimatorListenerAdapter() {
        isCanceled = false;
    }

    @Override
    public final void onAnimationStart(Animator animation, boolean isReverse) {
        isCanceled = false;
        onAnimationStarted(animation);
    }

    @Override
    public final void onAnimationStart(Animator animation) {
        isCanceled = false;
        onAnimationStarted(animation);
    }

    public void onAnimationStarted(Animator animation) {
    }

    @Override
    public final void onAnimationEnd(Animator animation, boolean isReverse) {
        onAnimationEnded(animation);
    }

    @Override
    public final void onAnimationEnd(Animator animation) {
        onAnimationEnded(animation);
    }

    public void onAnimationEnded(Animator animation) {
    }

    @Override
    public final void onAnimationCancel(Animator animation) {
        isCanceled = true;
        onAnimationCancelled(animation);
    }

    public void onAnimationCancelled(Animator animation) {
    }

    @Override
    public final void onAnimationRepeat(Animator animation) {
        isCanceled = false;
        onAnimationRepeated(animation);
    }

    public void onAnimationRepeated(Animator animation) {
    }

    public final boolean isCancelled() {
        return isCanceled;
    }

}
