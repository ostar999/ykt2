package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ProductPopPayBinding implements ViewBinding {

    @NonNull
    public final BLConstraintLayout aliCl;

    @NonNull
    public final ImageView aliImgv;

    @NonNull
    public final ImageView aliSelectImgv;

    @NonNull
    public final TextView aliTipTv;

    @NonNull
    public final TextView aliTv;

    @NonNull
    public final BLTextView goTv;

    @NonNull
    public final TextView moneyTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final BLConstraintLayout wxCl;

    @NonNull
    public final ImageView wxImgv;

    @NonNull
    public final ImageView wxSelectImgv;

    @NonNull
    public final TextView wxTipTv;

    @NonNull
    public final TextView wxTv;

    @NonNull
    public final ImageView xImgv;

    @NonNull
    public final TextView yTv;

    private ProductPopPayBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull BLTextView bLTextView, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull BLConstraintLayout bLConstraintLayout2, @NonNull ImageView imageView3, @NonNull ImageView imageView4, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull ImageView imageView5, @NonNull TextView textView8) {
        this.rootView = constraintLayout;
        this.aliCl = bLConstraintLayout;
        this.aliImgv = imageView;
        this.aliSelectImgv = imageView2;
        this.aliTipTv = textView;
        this.aliTv = textView2;
        this.goTv = bLTextView;
        this.moneyTv = textView3;
        this.tipTv = textView4;
        this.titleTv = textView5;
        this.wxCl = bLConstraintLayout2;
        this.wxImgv = imageView3;
        this.wxSelectImgv = imageView4;
        this.wxTipTv = textView6;
        this.wxTv = textView7;
        this.xImgv = imageView5;
        this.yTv = textView8;
    }

    @NonNull
    public static ProductPopPayBinding bind(@NonNull View view) {
        int i2 = R.id.aliCl;
        BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (bLConstraintLayout != null) {
            i2 = R.id.aliImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.aliSelectImgv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.aliTipTv;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        i2 = R.id.aliTv;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.goTv;
                            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                            if (bLTextView != null) {
                                i2 = R.id.moneyTv;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView3 != null) {
                                    i2 = R.id.tipTv;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView4 != null) {
                                        i2 = R.id.titleTv;
                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView5 != null) {
                                            i2 = R.id.wxCl;
                                            BLConstraintLayout bLConstraintLayout2 = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                            if (bLConstraintLayout2 != null) {
                                                i2 = R.id.wxImgv;
                                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView3 != null) {
                                                    i2 = R.id.wxSelectImgv;
                                                    ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                    if (imageView4 != null) {
                                                        i2 = R.id.wxTipTv;
                                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView6 != null) {
                                                            i2 = R.id.wxTv;
                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView7 != null) {
                                                                i2 = R.id.xImgv;
                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                if (imageView5 != null) {
                                                                    i2 = R.id.yTv;
                                                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView8 != null) {
                                                                        return new ProductPopPayBinding((ConstraintLayout) view, bLConstraintLayout, imageView, imageView2, textView, textView2, bLTextView, textView3, textView4, textView5, bLConstraintLayout2, imageView3, imageView4, textView6, textView7, imageView5, textView8);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
    public static ProductPopPayBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ProductPopPayBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.product_pop_pay, viewGroup, false);
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
