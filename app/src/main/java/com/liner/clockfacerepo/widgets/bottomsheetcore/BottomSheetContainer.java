package com.liner.clockfacerepo.widgets.bottomsheetcore;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liner.clockfacerepo.utils.Views;
import com.liner.clockfacerepo.widgets.bottomsheetcore.config.BaseConfig;

public abstract class BottomSheetContainer extends FrameLayout implements BottomSheet {
    protected final BaseConfig config;
    private float dimAmount;
    private float sheetCornerRadius;
    private float maxSheetWidth;
    private float statusBarSize;
    private float gapSize;
    private Point displaySize;
    private int dimColor;
    private int navColor;
    private int navOriginalColor;
    private int backgroundColor;
    private long animationDuration;
    private Interpolator animationInterpolator;
    private ValueAnimator animator;
    private ViewGroup contentContainer;
    private FrameLayout sheetView;
    private State state;
    private boolean isDismissableOnTouchOutside;
    private Runnable managementAction;
    private Callback callback;
    private Activity hostActivity;

    public BottomSheetContainer(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity);
        this.config = config;
        this.hostActivity = hostActivity;
        init(hostActivity);
    }


    private void init(Activity hostActivity) {
        initContainer(hostActivity);
        initResources(hostActivity);
        initBottomSheet();
        requestWindowInsetsWhenAttached();
    }


    @SuppressWarnings("NewApi")
    private void initContainer(Activity hostActivity) {
        contentContainer = hostActivity.findViewById(android.R.id.content);
        setElevation(999f);
    }


    private void initResources(Activity hostActivity) {
        dimAmount = config.getDimAmount();
        initDimensions(hostActivity);
        initColors(hostActivity);
        initAnimations();
        initStates();
    }


    private void initDimensions(Activity hostActivity) {
        sheetCornerRadius = config.getSheetCornerRadius();
        statusBarSize = Views.getStatusBarSize(getContext());
        gapSize = config.getTopGapSize();
        maxSheetWidth = config.getMaxSheetWidth();
        displaySize = new Point();
        hostActivity.getWindowManager().getDefaultDisplay().getSize(displaySize);
    }

    private void initColors(Activity hostActivity) {
        dimColor = config.getDimColor();
        navColor = config.getNavColor();
        navOriginalColor = hostActivity.getWindow().getNavigationBarColor();
        backgroundColor = config.getSheetBackgroundColor();
    }

    private void initAnimations() {
        animationDuration = config.getSheetAnimationDuration();
        animationInterpolator = config.getSheetAnimationInterpolator();
    }

    private void initStates() {
        state = State.COLLAPSED;
        isDismissableOnTouchOutside = config.isDismissableOnTouchOutside();
    }

    private void initBottomSheet() {
        sheetView = new FrameLayout(getContext());
        sheetView.setLayoutParams(generateDefaultLayoutParams());
        sheetView.setBackground(createBottomSheetBackgroundDrawable());
        sheetView.setPadding(
                sheetView.getPaddingLeft(),
                ((int) config.getExtraPaddingTop()),
                sheetView.getPaddingRight(),
                ((int) config.getExtraPaddingBottom())
        );
        final View createdSheetView = LayoutInflater.from(getContext()).inflate(layoutResource(), this, false);
        sheetView.addView(createdSheetView);
        addView(sheetView);
        onCreateSheetView(createdSheetView);
        setBackgroundColor(dimColor);
    }

    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        sheetView.setPadding(
                sheetView.getPaddingLeft(),
                sheetView.getPaddingTop(),
                sheetView.getPaddingRight(),
                (int) (insets.getSystemWindowInsetBottom() + config.getExtraPaddingBottom())
        );
        return insets;
    }

    private void requestWindowInsetsWhenAttached() {
        if (isAttachedToWindow()) {
            requestApplyInsets();
        } else {
            addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                    removeOnAttachStateChangeListener(this);
                    requestApplyInsets();
                }
                @Override
                public void onViewDetachedFromWindow(View view) {
                }
            });
        }
    }

    @Override
    protected final LayoutParams generateDefaultLayoutParams() {
        final LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        return layoutParams;
    }

    private void addToContainer() {
        contentContainer.addView(
                this,
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
    }

    private void removeFromContainer() {
        contentContainer.removeView(this);
    }

    @Override
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelStateTransitionAnimation();
    }

    @NonNull
    protected abstract void onCreateSheetView(View sheetView);

    @LayoutRes
    protected abstract int layoutResource();

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if (isDismissableOnTouchOutside) {
            dismiss();
        }
        return true;
    }


    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setUiState(state);
    }


    @Override
    protected final void measureChildWithMargins(View child,
                                                 int parentWidthMeasureSpec,
                                                 int widthUsed,
                                                 int parentHeightMeasureSpec,
                                                 int heightUsed) {
        final int parentWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        final int parentHeight = MeasureSpec.getSize(parentHeightMeasureSpec);
        final int displayHeight = displaySize.y;
        final int verticalGapSize = (int) ((displayHeight > parentHeight) ? 0 : statusBarSize);
        final int maxWidth = (int) Math.min(parentWidth, maxSheetWidth);
        final int maxHeight = (int) (parentHeight - verticalGapSize - gapSize);
        int adjustedParentWidthMeasureSpec = parentWidthMeasureSpec;
        int adjustedParentHeightMeasureSpec = parentHeightMeasureSpec;
        if (child == sheetView) {
            adjustedParentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.getMode(parentWidthMeasureSpec));
            adjustedParentHeightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.getMode(parentHeightMeasureSpec));
        }
        super.measureChildWithMargins(
                child,
                adjustedParentWidthMeasureSpec,
                widthUsed,
                adjustedParentHeightMeasureSpec,
                heightUsed
        );
    }

    @Override
    public final void show() {
        show(true);
    }

    @Override
    public final void show(final boolean animate) {
        if (isAttachedToContainer()) {
            return;
        }
        cancelStateTransitionAnimation();
        addToContainer();
        reportOnShow();
        setNavigationBarColor(navColor, config.getSheetAnimationDuration());
        postViewShowingAction(animate);
    }

    @Override
    public final void dismiss() {
        dismiss(true);
    }

    @Override
    public final void dismiss(boolean animate) {
        if (isAttachedToContainer() && (!animate || !State.COLLAPSING.equals(state))) {
            cancelStateTransitionAnimation();
            reportOnDismiss();
            setNavigationBarColor(navOriginalColor, 65);
            postViewDismissingAction(animate);
        }
    }

    private void postViewShowingAction(final boolean animate) {
        postPendingViewManagementAction(() -> BottomSheetContainer.this.expandSheet(animate));
    }

    private void expandSheet(final boolean animate) {
        if (animate) {
            if (!State.EXPANDED.equals(state) && !State.EXPANDING.equals(state)) {
                setUiState(State.COLLAPSED);
                animateStateTransition(State.EXPANDING);
            }
        } else {
            setUiState(state = State.EXPANDED);
        }
    }

    private void postViewDismissingAction(final boolean animate) {
        postPendingViewManagementAction(() -> BottomSheetContainer.this.collapseSheet(animate));
    }

    private void collapseSheet(final boolean animate) {
        if (animate) {
            if (!State.COLLAPSED.equals(state) && !State.COLLAPSING.equals(state)) {
                animateStateTransition(State.COLLAPSING);
            }
        } else {
            removeFromContainer();
            setUiState(state = State.COLLAPSED);
        }
    }

    private void postPendingViewManagementAction(Runnable action) {
        cancelPendingViewManagementAction();
        post(managementAction = action);
    }

    private void cancelPendingViewManagementAction() {
        if (managementAction != null) {
            removeCallbacks(managementAction);
        }
    }

    private boolean isAttachedToContainer() {
        final int containerChildCount = contentContainer.getChildCount();
        for (int i = 0; i < containerChildCount; i++) {
            if (contentContainer.getChildAt(i) == this) {
                return true;
            }
        }
        return false;
    }


    private void animateStateTransition(final State state) {
        cancelStateTransitionAnimation();
        final boolean isExpanding = State.EXPANDING.equals(state);
        final float startY = getMeasuredHeight();
        final float endY = (getMeasuredHeight() - sheetView.getMeasuredHeight());
        final float deltaY = (startY - endY);
        final float startValue = ((getMeasuredHeight() - sheetView.getY()) / deltaY);
        final float endValue = (isExpanding ? 1f : 0f);
        animator = ValueAnimator.ofFloat(startValue, endValue);
        animator.addUpdateListener(valueAnimator -> {
            final float animatedValue = (Float) valueAnimator.getAnimatedValue();
            final float newY = (startY + (animatedValue * (endY - startY)));
            sheetView.setY(newY);
            setAlpha(animatedValue);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStarted(Animator animation) {
                BottomSheetContainer.this.state = (isExpanding ? State.EXPANDING : State.COLLAPSING);
            }

            @Override
            public void onAnimationEnded(Animator animation) {
                if (isExpanding) {
                    BottomSheetContainer.this.state = State.EXPANDED;
                } else {
                    BottomSheetContainer.this.state = State.COLLAPSED;
                    removeFromContainer();
                }
            }
        });
        animator.setInterpolator(animationInterpolator);
        animator.setDuration(animationDuration);
        animator.start();
    }

    private void cancelStateTransitionAnimation() {
        if ((animator != null) && animator.isRunning()) {
            animator.cancel();
        }
    }

    private void reportOnDismiss() {
        if (callback != null) {
            callback.onDismiss(this);
        }
    }

    private void reportOnShow() {
        if (callback != null) {
            callback.onShow(this);
        }
    }

    private Drawable createBottomSheetBackgroundDrawable() {
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor);
        drawable.setCornerRadii(new float[]{
                sheetCornerRadius,
                sheetCornerRadius,
                sheetCornerRadius,
                sheetCornerRadius,
                0f,
                0f,
                0f,
                0f
        });
        return drawable;
    }

    private void setUiState(State state) {
        setBottomSheetState(state);
        setBackgroundState(state);
    }

    private void setBottomSheetState(State state) {
        if (State.EXPANDED.equals(state)) {
            sheetView.setY(getMeasuredHeight() - sheetView.getMeasuredHeight());
        } else if (State.COLLAPSED.equals(state)) {
            sheetView.setY(getMeasuredHeight());
        }
    }

    private void setBackgroundState(State state) {
        if (State.EXPANDED.equals(state)) {
            setAlpha(1f);
        } else if (State.COLLAPSED.equals(state)) {
            setAlpha(0f);
        }
    }

    @NonNull
    @Override
    public final State getState() {
        return state;
    }

    @Override
    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    private void setNavigationBarColor(@ColorInt int color, long delay){
        postDelayed(() -> Views.setNavigationBarColor(hostActivity, color), delay-165);
    }

    public boolean onBackPressed(){
        if(state == State.EXPANDED) {
            dismiss();
            return false;
        }
        return true;
    }
}