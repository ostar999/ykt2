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
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class SetPopupWxqrBinding implements ViewBinding {

    @NonNull
    public final BLTextView cancleTv;

    @NonNull
    public final View lineV;

    @NonNull
    public final BLConstraintLayout qrCl;

    @NonNull
    public final ImageView qrImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final BLTextView saveTv;

    private SetPopupWxqrBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLTextView bLTextView, @NonNull View view, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull ImageView imageView, @NonNull BLTextView bLTextView2) {
        this.rootView = constraintLayout;
        this.cancleTv = bLTextView;
        this.lineV = view;
        this.qrCl = bLConstraintLayout;
        this.qrImgv = imageView;
        this.saveTv = bLTextView2;
    }

    @NonNull
    public static SetPopupWxqrBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.cancleTv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
            i2 = R.id.qrCl;
            BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (bLConstraintLayout != null) {
                i2 = R.id.qrImgv;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.saveTv;
                    BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                    if (bLTextView2 != null) {
                        return new SetPopupWxqrBinding((ConstraintLayout) view, bLTextView, viewFindChildViewById, bLConstraintLayout, imageView, bLTextView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static SetPopupWxqrBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static SetPopupWxqrBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.set_popup_wxqr, viewGroup, false);
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
