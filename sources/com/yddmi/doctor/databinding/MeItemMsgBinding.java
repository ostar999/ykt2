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
import com.catchpig.mvvm.widget.DotView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MeItemMsgBinding implements ViewBinding {

    @NonNull
    public final TextView detailTv;

    @NonNull
    public final DotView dotV;

    @NonNull
    public final ImageView goImgv;

    @NonNull
    public final ImageView imgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final TextView titleTv;

    private MeItemMsgBinding(@NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull DotView dotView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull TextView textView2, @NonNull TextView textView3) {
        this.rootView = constraintLayout;
        this.detailTv = textView;
        this.dotV = dotView;
        this.goImgv = imageView;
        this.imgv = imageView2;
        this.timeTv = textView2;
        this.titleTv = textView3;
    }

    @NonNull
    public static MeItemMsgBinding bind(@NonNull View view) {
        int i2 = R.id.detailTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.dotV;
            DotView dotView = (DotView) ViewBindings.findChildViewById(view, i2);
            if (dotView != null) {
                i2 = R.id.goImgv;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.imgv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.timeTv;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.titleTv;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                return new MeItemMsgBinding((ConstraintLayout) view, textView, dotView, imageView, imageView2, textView2, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MeItemMsgBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MeItemMsgBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_item_msg, viewGroup, false);
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
