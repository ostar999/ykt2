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
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PhysicalTipActivityBinding implements ViewBinding {

    @NonNull
    public final ImageView callImgv;

    @NonNull
    public final ImageView codeImgv;

    @NonNull
    public final ImageView iconImgv;

    @NonNull
    public final ImageView meImgv;

    @NonNull
    public final ImageView newImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ImageView setImgv;

    @NonNull
    public final ImageView tipImgv;

    @NonNull
    public final ConstraintLayout titleCl;

    @NonNull
    public final View view;

    private PhysicalTipActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6, @NonNull ImageView imageView7, @NonNull ConstraintLayout constraintLayout2, @NonNull View view) {
        this.rootView = constraintLayout;
        this.callImgv = imageView;
        this.codeImgv = imageView2;
        this.iconImgv = imageView3;
        this.meImgv = imageView4;
        this.newImgv = imageView5;
        this.setImgv = imageView6;
        this.tipImgv = imageView7;
        this.titleCl = constraintLayout2;
        this.view = view;
    }

    @NonNull
    public static PhysicalTipActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.callImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeImgv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.iconImgv;
                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView3 != null) {
                    i2 = R.id.meImgv;
                    ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView4 != null) {
                        i2 = R.id.newImgv;
                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView5 != null) {
                            i2 = R.id.setImgv;
                            ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView6 != null) {
                                i2 = R.id.tipImgv;
                                ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView7 != null) {
                                    i2 = R.id.titleCl;
                                    ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                        return new PhysicalTipActivityBinding((ConstraintLayout) view, imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, constraintLayout, viewFindChildViewById);
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
    public static PhysicalTipActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PhysicalTipActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.physical_tip_activity, viewGroup, false);
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
