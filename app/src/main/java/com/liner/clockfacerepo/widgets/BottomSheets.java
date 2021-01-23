package com.liner.clockfacerepo.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.liner.clockfacerepo.R;
import com.liner.clockfacerepo.utils.Density;
import com.liner.clockfacerepo.widgets.bottomsheetcore.BottomSheetContainer;
import com.liner.clockfacerepo.widgets.bottomsheetcore.config.BaseConfig;
import com.liner.clockfacerepo.widgets.bottomsheetcore.config.Config;

@SuppressLint("ViewConstructor")
public class BottomSheets {
    public static BaseConfig getDefaultConfig(Activity activity) {
        return new Config.Builder(activity)
                .dimAmount(0.4f)
                .sheetAnimationDuration(400)
                .sheetAnimationInterpolator(new LinearOutSlowInInterpolator())
                .sheetBackgroundColor(Color.TRANSPARENT)
                .navColor(ContextCompat.getColor(activity, R.color.background_light))
                .sheetCornerRadius(Density.dpToPx(32))
                .build();
    }

    public static class Alert extends BottomSheetContainer implements com.liner.clockfacerepo.widgets.bottomsheetcore.BottomSheet.Callback {
        private ImageView icon;
        private YSTextView title;
        private YSTextView text;
        private MaterialButton button;
        private Alert.Builder builder;

        public Alert(@NonNull Activity hostActivity, @NonNull BaseConfig config, Alert.Builder builder) {
            super(hostActivity, config);
            this.builder = builder;
            setCallback(this);
        }

        @NonNull
        @Override
        protected void onCreateSheetView(View sheetView) {
            icon = sheetView.findViewById(R.id.bottom_sheet_alert_icon);
            title = sheetView.findViewById(R.id.bottom_sheet_alert_title);
            text = sheetView.findViewById(R.id.bottom_sheet_alert_text);
            button = sheetView.findViewById(R.id.bottom_sheet_alert_button);
        }

        @Override
        protected int layoutResource() {
            return R.layout.bottom_sheet_alert;
        }

        public void setClickListener(OnClickListener clickListener) {
            button.setOnClickListener(clickListener);
        }

        @Override
        public void onDismiss(@NonNull com.liner.clockfacerepo.widgets.bottomsheetcore.BottomSheet bottomSheet) {

        }

        @Override
        public void onShow(@NonNull com.liner.clockfacerepo.widgets.bottomsheetcore.BottomSheet bottomSheet) {
            if (builder != null) {
                if (builder.icon != -1)
                    Glide.with(icon).load(builder.icon).into(icon);
                title.setText(builder.title);
                text.setText(builder.text);
                button.setText(builder.buttonText);
            }
        }

        public static class Builder {
            private Activity activity;
            @DrawableRes
            private int icon;
            private String title;
            private String text;
            private String buttonText;

            public Builder(Activity activity) {
                this.activity = activity;
                this.icon = -1;
                this.title = "";
                this.text = "";
                this.buttonText = "Okay";
            }

            public Builder setIcon(int icon) {
                this.icon = icon;
                return this;
            }

            public Builder setTitle(String title) {
                this.title = title;
                return this;
            }

            public Builder setText(String text) {
                this.text = text;
                return this;
            }

            public Builder setButtonText(String buttonText) {
                this.buttonText = buttonText;
                return this;
            }

            public Alert build() {
                return new Alert(activity, getDefaultConfig(activity), this);
            }
        }
    }
}
