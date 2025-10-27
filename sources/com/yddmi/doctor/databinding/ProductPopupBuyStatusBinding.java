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
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ProductPopupBuyStatusBinding implements ViewBinding {

    @NonNull
    public final TextView addTv;

    @NonNull
    public final ConstraintLayout qrCl;

    @NonNull
    public final ImageView qrImgv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final BLTextView saveBtv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final ImageView xImgv;

    private ProductPopupBuyStatusBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull ImageView imageView2) {
        this.rootView = frameLayout;
        this.addTv = textView;
        this.qrCl = constraintLayout;
        this.qrImgv = imageView;
        this.saveBtv = bLTextView;
        this.tipTv = textView2;
        this.titleTv = textView3;
        this.xImgv = imageView2;
    }

    @NonNull
    public static ProductPopupBuyStatusBinding bind(@NonNull View view) {
        int i2 = R.id.addTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.qrCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.qrImgv;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.saveBtv;
                    BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                    if (bLTextView != null) {
                        i2 = R.id.tipTv;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.titleTv;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                i2 = R.id.xImgv;
                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView2 != null) {
                                    return new ProductPopupBuyStatusBinding((FrameLayout) view, textView, constraintLayout, imageView, bLTextView, textView2, textView3, imageView2);
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
    public static ProductPopupBuyStatusBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ProductPopupBuyStatusBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.product_popup_buy_status, viewGroup, false);
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
