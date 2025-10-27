package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LayoutFragmentErrorBinding implements ViewBinding {

    @NonNull
    public final AppCompatTextView failedReload;

    @NonNull
    public final AppCompatTextView failedText;

    @NonNull
    private final ConstraintLayout rootView;

    private LayoutFragmentErrorBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatTextView appCompatTextView, @NonNull AppCompatTextView appCompatTextView2) {
        this.rootView = constraintLayout;
        this.failedReload = appCompatTextView;
        this.failedText = appCompatTextView2;
    }

    @NonNull
    public static LayoutFragmentErrorBinding bind(@NonNull View view) {
        int i2 = R.id.failed_reload;
        AppCompatTextView appCompatTextView = (AppCompatTextView) ViewBindings.findChildViewById(view, i2);
        if (appCompatTextView != null) {
            i2 = R.id.failed_text;
            AppCompatTextView appCompatTextView2 = (AppCompatTextView) ViewBindings.findChildViewById(view, i2);
            if (appCompatTextView2 != null) {
                return new LayoutFragmentErrorBinding((ConstraintLayout) view, appCompatTextView, appCompatTextView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutFragmentErrorBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutFragmentErrorBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_fragment_error, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
