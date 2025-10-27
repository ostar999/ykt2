package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MainGuide1Binding implements ViewBinding {

    @NonNull
    public final ImageView imgv;

    @NonNull
    public final BLTextView nextTv;

    @NonNull
    public final ConstraintLayout rootCl;

    @NonNull
    private final ConstraintLayout rootView;

    private MainGuide1Binding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull BLTextView bLTextView, @NonNull ConstraintLayout constraintLayout2) {
        this.rootView = constraintLayout;
        this.imgv = imageView;
        this.nextTv = bLTextView;
        this.rootCl = constraintLayout2;
    }

    @NonNull
    public static MainGuide1Binding bind(@NonNull View view) {
        int i2 = R.id.imgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.nextTv;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                ConstraintLayout constraintLayout = (ConstraintLayout) view;
                return new MainGuide1Binding(constraintLayout, imageView, bLTextView, constraintLayout);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MainGuide1Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MainGuide1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.main_guide_1, viewGroup, false);
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
