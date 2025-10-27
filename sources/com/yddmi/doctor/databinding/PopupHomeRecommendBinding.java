package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeRecommendBinding implements ViewBinding {

    @NonNull
    public final ImageView adImgv;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final ConstraintLayout qrCl;

    @NonNull
    public final ImageView qrImgv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ImageView saveImgv;

    @NonNull
    public final ImageView xImgv;

    private PopupHomeRecommendBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull ImageView imageView4) {
        this.rootView = frameLayout;
        this.adImgv = imageView;
        this.codeTv = textView;
        this.qrCl = constraintLayout;
        this.qrImgv = imageView2;
        this.saveImgv = imageView3;
        this.xImgv = imageView4;
    }

    @NonNull
    public static PopupHomeRecommendBinding bind(@NonNull View view) {
        int i2 = R.id.adImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.qrCl;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout != null) {
                    i2 = R.id.qrImgv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.saveImgv;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView3 != null) {
                            i2 = R.id.xImgv;
                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView4 != null) {
                                return new PopupHomeRecommendBinding((FrameLayout) view, imageView, textView, constraintLayout, imageView2, imageView3, imageView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupHomeRecommendBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeRecommendBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_recommend, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
