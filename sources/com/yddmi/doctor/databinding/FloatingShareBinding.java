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
public final class FloatingShareBinding implements ViewBinding {

    @NonNull
    public final BLTextView feedbackTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ImageView wxImgv;

    @NonNull
    public final ImageView xImgv;

    private FloatingShareBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLTextView bLTextView, @NonNull ImageView imageView, @NonNull ImageView imageView2) {
        this.rootView = constraintLayout;
        this.feedbackTv = bLTextView;
        this.wxImgv = imageView;
        this.xImgv = imageView2;
    }

    @NonNull
    public static FloatingShareBinding bind(@NonNull View view) {
        int i2 = R.id.feedbackTv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null) {
            i2 = R.id.wxImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.xImgv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    return new FloatingShareBinding((ConstraintLayout) view, bLTextView, imageView, imageView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static FloatingShareBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FloatingShareBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.floating_share, viewGroup, false);
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
