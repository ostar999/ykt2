package com.google.android.material.textfield;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.google.android.material.internal.CheckableImageButton;

/* loaded from: classes3.dex */
abstract class EndIconDelegate {
    Context context;

    @DrawableRes
    final int customEndIcon;
    CheckableImageButton endIconView;
    TextInputLayout textInputLayout;

    public EndIconDelegate(@NonNull TextInputLayout textInputLayout, @DrawableRes int i2) {
        this.textInputLayout = textInputLayout;
        this.context = textInputLayout.getContext();
        this.endIconView = textInputLayout.getEndIconView();
        this.customEndIcon = i2;
    }

    public abstract void initialize();

    public boolean isBoxBackgroundModeSupported(int i2) {
        return true;
    }

    public void onSuffixVisibilityChanged(boolean z2) {
    }

    public boolean shouldTintIconOnError() {
        return false;
    }
}
