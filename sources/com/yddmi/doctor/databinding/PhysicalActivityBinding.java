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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.angcyo.tablayout.DslTabLayout;
import com.noober.background.view.BLFrameLayout;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PhysicalActivityBinding implements ViewBinding {

    @NonNull
    public final ImageView callImgv;

    @NonNull
    public final ImageView codeImgv;

    @NonNull
    public final DslTabLayout dslTablayout;

    @NonNull
    public final ImageView iconImgv;

    @NonNull
    public final RecyclerView leftRv;

    @NonNull
    public final ImageView meImgv;

    @NonNull
    public final ImageView newImgv;

    @NonNull
    public final RecyclerView rightRv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final FrameLayout ruleFl;

    @NonNull
    public final ImageView ruleImgv;

    @NonNull
    public final TextView ruleTv;

    @NonNull
    public final ImageView setImgv;

    @NonNull
    public final BLFrameLayout tableBfl;

    @NonNull
    public final ConstraintLayout titleCl;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final View view;

    private PhysicalActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull DslTabLayout dslTabLayout, @NonNull ImageView imageView3, @NonNull RecyclerView recyclerView, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull RecyclerView recyclerView2, @NonNull FrameLayout frameLayout, @NonNull ImageView imageView6, @NonNull TextView textView, @NonNull ImageView imageView7, @NonNull BLFrameLayout bLFrameLayout, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView2, @NonNull View view) {
        this.rootView = constraintLayout;
        this.callImgv = imageView;
        this.codeImgv = imageView2;
        this.dslTablayout = dslTabLayout;
        this.iconImgv = imageView3;
        this.leftRv = recyclerView;
        this.meImgv = imageView4;
        this.newImgv = imageView5;
        this.rightRv = recyclerView2;
        this.ruleFl = frameLayout;
        this.ruleImgv = imageView6;
        this.ruleTv = textView;
        this.setImgv = imageView7;
        this.tableBfl = bLFrameLayout;
        this.titleCl = constraintLayout2;
        this.titleTv = textView2;
        this.view = view;
    }

    @NonNull
    public static PhysicalActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.callImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeImgv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.dslTablayout;
                DslTabLayout dslTabLayout = (DslTabLayout) ViewBindings.findChildViewById(view, i2);
                if (dslTabLayout != null) {
                    i2 = R.id.iconImgv;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView3 != null) {
                        i2 = R.id.leftRv;
                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                        if (recyclerView != null) {
                            i2 = R.id.meImgv;
                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView4 != null) {
                                i2 = R.id.newImgv;
                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView5 != null) {
                                    i2 = R.id.rightRv;
                                    RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                    if (recyclerView2 != null) {
                                        i2 = R.id.ruleFl;
                                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                        if (frameLayout != null) {
                                            i2 = R.id.ruleImgv;
                                            ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView6 != null) {
                                                i2 = R.id.ruleTv;
                                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView != null) {
                                                    i2 = R.id.setImgv;
                                                    ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                    if (imageView7 != null) {
                                                        i2 = R.id.tableBfl;
                                                        BLFrameLayout bLFrameLayout = (BLFrameLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (bLFrameLayout != null) {
                                                            i2 = R.id.titleCl;
                                                            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (constraintLayout != null) {
                                                                i2 = R.id.titleTv;
                                                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                                                    return new PhysicalActivityBinding((ConstraintLayout) view, imageView, imageView2, dslTabLayout, imageView3, recyclerView, imageView4, imageView5, recyclerView2, frameLayout, imageView6, textView, imageView7, bLFrameLayout, constraintLayout, textView2, viewFindChildViewById);
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
    public static PhysicalActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PhysicalActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.physical_activity, viewGroup, false);
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
