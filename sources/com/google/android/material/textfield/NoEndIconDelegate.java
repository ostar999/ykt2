package com.google.android.material.textfield;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
class NoEndIconDelegate extends EndIconDelegate {
    public NoEndIconDelegate(@NonNull TextInputLayout textInputLayout) {
        super(textInputLayout, 0);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public void initialize() {
        this.textInputLayout.setEndIconOnClickListener(null);
        this.textInputLayout.setEndIconDrawable((Drawable) null);
        this.textInputLayout.setEndIconContentDescription((CharSequence) null);
    }
}
