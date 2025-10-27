package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MainPopupRuleBinding implements ViewBinding {

    @NonNull
    public final BLConstraintLayout cl;

    @NonNull
    public final TextView knowTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView ruleTv;

    @NonNull
    public final ScrollView scrollV;

    @NonNull
    public final ImageView top1Imgv;

    @NonNull
    public final ImageView webImgv;

    @NonNull
    public final ImageView xImgv;

    private MainPopupRuleBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ScrollView scrollView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3) {
        this.rootView = constraintLayout;
        this.cl = bLConstraintLayout;
        this.knowTv = textView;
        this.ruleTv = textView2;
        this.scrollV = scrollView;
        this.top1Imgv = imageView;
        this.webImgv = imageView2;
        this.xImgv = imageView3;
    }

    @NonNull
    public static MainPopupRuleBinding bind(@NonNull View view) {
        int i2 = R.id.cl;
        BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (bLConstraintLayout != null) {
            i2 = R.id.knowTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.ruleTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.scrollV;
                    ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i2);
                    if (scrollView != null) {
                        i2 = R.id.top1Imgv;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null) {
                            i2 = R.id.webImgv;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView2 != null) {
                                i2 = R.id.xImgv;
                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView3 != null) {
                                    return new MainPopupRuleBinding((ConstraintLayout) view, bLConstraintLayout, textView, textView2, scrollView, imageView, imageView2, imageView3);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MainPopupRuleBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MainPopupRuleBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.main_popup_rule, viewGroup, false);
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
