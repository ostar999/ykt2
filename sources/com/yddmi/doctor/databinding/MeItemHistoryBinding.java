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
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MeItemHistoryBinding implements ViewBinding {

    @NonNull
    public final ImageView imgv;

    @NonNull
    public final TextView nameTv;

    @NonNull
    public final ImageView rightImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView scoreTipTv;

    @NonNull
    public final BLTextView scoreTv;

    @NonNull
    public final TextView timeTipTv;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final BLTextView timeUseTv;

    private MeItemHistoryBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull ImageView imageView2, @NonNull TextView textView2, @NonNull BLTextView bLTextView, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull BLTextView bLTextView2) {
        this.rootView = constraintLayout;
        this.imgv = imageView;
        this.nameTv = textView;
        this.rightImgv = imageView2;
        this.scoreTipTv = textView2;
        this.scoreTv = bLTextView;
        this.timeTipTv = textView3;
        this.timeTv = textView4;
        this.timeUseTv = bLTextView2;
    }

    @NonNull
    public static MeItemHistoryBinding bind(@NonNull View view) {
        int i2 = R.id.imgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.nameTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.rightImgv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.scoreTipTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.scoreTv;
                        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                        if (bLTextView != null) {
                            i2 = R.id.timeTipTv;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                i2 = R.id.timeTv;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView4 != null) {
                                    i2 = R.id.timeUseTv;
                                    BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                    if (bLTextView2 != null) {
                                        return new MeItemHistoryBinding((ConstraintLayout) view, imageView, textView, imageView2, textView2, bLTextView, textView3, textView4, bLTextView2);
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
    public static MeItemHistoryBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MeItemHistoryBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_item_history, viewGroup, false);
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
