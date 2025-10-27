package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeAdBinding implements ViewBinding {

    @NonNull
    public final ImageView adImgv;

    @NonNull
    public final View leftV;

    @NonNull
    public final View lineV;

    @NonNull
    public final ImageView oneImgv;

    @NonNull
    public final ConstraintLayout recommendCl;

    @NonNull
    public final View rightV;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ImageView twoImgv;

    @NonNull
    public final ImageView xImgv;

    private PopupHomeAdBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull View view, @NonNull View view2, @NonNull ImageView imageView2, @NonNull ConstraintLayout constraintLayout, @NonNull View view3, @NonNull ImageView imageView3, @NonNull ImageView imageView4) {
        this.rootView = frameLayout;
        this.adImgv = imageView;
        this.leftV = view;
        this.lineV = view2;
        this.oneImgv = imageView2;
        this.recommendCl = constraintLayout;
        this.rightV = view3;
        this.twoImgv = imageView3;
        this.xImgv = imageView4;
    }

    @NonNull
    public static PopupHomeAdBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        int i2 = R.id.adImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.leftV))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
            i2 = R.id.oneImgv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.recommendCl;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.rightV))) != null) {
                    i2 = R.id.twoImgv;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView3 != null) {
                        i2 = R.id.xImgv;
                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView4 != null) {
                            return new PopupHomeAdBinding((FrameLayout) view, imageView, viewFindChildViewById, viewFindChildViewById2, imageView2, constraintLayout, viewFindChildViewById3, imageView3, imageView4);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupHomeAdBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeAdBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_ad, viewGroup, false);
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
