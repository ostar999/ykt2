package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LayoutEmptyBinding implements ViewBinding {

    @NonNull
    public final LinearLayout emptyType100;

    @NonNull
    public final BLTextView emptyType101;

    @NonNull
    public final AppCompatImageView noDataImgv;

    @NonNull
    public final TextView noDataTv;

    @NonNull
    private final ConstraintLayout rootView;

    private LayoutEmptyBinding(@NonNull ConstraintLayout constraintLayout, @NonNull LinearLayout linearLayout, @NonNull BLTextView bLTextView, @NonNull AppCompatImageView appCompatImageView, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.emptyType100 = linearLayout;
        this.emptyType101 = bLTextView;
        this.noDataImgv = appCompatImageView;
        this.noDataTv = textView;
    }

    @NonNull
    public static LayoutEmptyBinding bind(@NonNull View view) {
        int i2 = R.id.emptyType100;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
        if (linearLayout != null) {
            i2 = R.id.emptyType101;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                i2 = R.id.noDataImgv;
                AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                if (appCompatImageView != null) {
                    i2 = R.id.noDataTv;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        return new LayoutEmptyBinding((ConstraintLayout) view, linearLayout, bLTextView, appCompatImageView, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutEmptyBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutEmptyBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_empty, viewGroup, false);
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
