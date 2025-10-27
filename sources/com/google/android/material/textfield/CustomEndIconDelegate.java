package com.google.android.material.textfield;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
class CustomEndIconDelegate extends EndIconDelegate {
    public CustomEndIconDelegate(@NonNull TextInputLayout textInputLayout, @DrawableRes int i2) {
        super(textInputLayout, i2);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public void initialize() {
        this.textInputLayout.setEndIconDrawable(this.customEndIcon);
        this.textInputLayout.setEndIconOnClickListener(null);
        this.textInputLayout.setEndIconOnLongClickListener(null);
    }
}
