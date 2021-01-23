

package com.liner.clockfacerepo.widgets.bottomsheetcore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public interface BottomSheet {
    void show();
    void show(boolean animate);
    void dismiss();
    void dismiss(boolean animate);
    void setCallback(@Nullable Callback callback);
    @NonNull
    State getState();
    enum State {
        COLLAPSED,
        COLLAPSING,
        EXPANDED,
        EXPANDING
    }

    interface Callback {
        void onDismiss(@NonNull BottomSheet bottomSheet);
        void onShow(@NonNull BottomSheet bottomSheet);
    }
}