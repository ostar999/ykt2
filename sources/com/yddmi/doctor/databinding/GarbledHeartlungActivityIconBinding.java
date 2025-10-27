package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungActivityIconBinding implements ViewBinding {

    @NonNull
    public final ImageView backImgv;

    @NonNull
    public final ConstraintLayout icon1Cl;

    @NonNull
    public final ImageView icon1Imgv;

    @NonNull
    public final ConstraintLayout icon2Cl;

    @NonNull
    public final ImageView icon2Imgv;

    @NonNull
    public final ConstraintLayout icon3Cl;

    @NonNull
    public final ImageView icon3Imgv;

    @NonNull
    public final ConstraintLayout icon4Cl;

    @NonNull
    public final ImageView icon4Imgv;

    @NonNull
    public final ConstraintLayout icon5Cl;

    @NonNull
    public final ImageView icon5Imgv;

    @NonNull
    public final Flow iconFlow;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ConstraintLayout titleCl;

    @NonNull
    public final View view;

    private GarbledHeartlungActivityIconBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView2, @NonNull ConstraintLayout constraintLayout2, @NonNull ImageView imageView3, @NonNull ConstraintLayout constraintLayout3, @NonNull ImageView imageView4, @NonNull ConstraintLayout constraintLayout4, @NonNull ImageView imageView5, @NonNull ConstraintLayout constraintLayout5, @NonNull ImageView imageView6, @NonNull Flow flow, @NonNull ConstraintLayout constraintLayout6, @NonNull View view) {
        this.rootView = frameLayout;
        this.backImgv = imageView;
        this.icon1Cl = constraintLayout;
        this.icon1Imgv = imageView2;
        this.icon2Cl = constraintLayout2;
        this.icon2Imgv = imageView3;
        this.icon3Cl = constraintLayout3;
        this.icon3Imgv = imageView4;
        this.icon4Cl = constraintLayout4;
        this.icon4Imgv = imageView5;
        this.icon5Cl = constraintLayout5;
        this.icon5Imgv = imageView6;
        this.iconFlow = flow;
        this.titleCl = constraintLayout6;
        this.view = view;
    }

    @NonNull
    public static GarbledHeartlungActivityIconBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.backImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.icon1Cl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.icon1Imgv;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.icon2Cl;
                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                    if (constraintLayout2 != null) {
                        i2 = R.id.icon2Imgv;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView3 != null) {
                            i2 = R.id.icon3Cl;
                            ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout3 != null) {
                                i2 = R.id.icon3Imgv;
                                ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView4 != null) {
                                    i2 = R.id.icon4Cl;
                                    ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout4 != null) {
                                        i2 = R.id.icon4Imgv;
                                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView5 != null) {
                                            i2 = R.id.icon5Cl;
                                            ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                            if (constraintLayout5 != null) {
                                                i2 = R.id.icon5Imgv;
                                                ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView6 != null) {
                                                    i2 = R.id.iconFlow;
                                                    Flow flow = (Flow) ViewBindings.findChildViewById(view, i2);
                                                    if (flow != null) {
                                                        i2 = R.id.titleCl;
                                                        ConstraintLayout constraintLayout6 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (constraintLayout6 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                                            return new GarbledHeartlungActivityIconBinding((FrameLayout) view, imageView, constraintLayout, imageView2, constraintLayout2, imageView3, constraintLayout3, imageView4, constraintLayout4, imageView5, constraintLayout5, imageView6, flow, constraintLayout6, viewFindChildViewById);
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
    public static GarbledHeartlungActivityIconBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungActivityIconBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_activity_icon, viewGroup, false);
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
