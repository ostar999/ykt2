package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class HomeActivityBinding implements ViewBinding {

    @NonNull
    public final Flow actionFlow;

    @NonNull
    public final ImageView backImgv;

    @NonNull
    public final BLView bgV;

    @NonNull
    public final ImageView bodyImgv;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final ImageView iconImgv;

    @NonNull
    public final ConstraintLayout integralCl;

    @NonNull
    public final TextView integralTv;

    @NonNull
    public final TextView meTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ImageView scoreImgv;

    @NonNull
    public final ImageView trainImgv;

    @NonNull
    public final ImageView xftzImgv;

    private HomeActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull Flow flow, @NonNull ImageView imageView, @NonNull BLView bLView, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull ImageView imageView3, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6) {
        this.rootView = constraintLayout;
        this.actionFlow = flow;
        this.backImgv = imageView;
        this.bgV = bLView;
        this.bodyImgv = imageView2;
        this.codeTv = textView;
        this.iconImgv = imageView3;
        this.integralCl = constraintLayout2;
        this.integralTv = textView2;
        this.meTv = textView3;
        this.scoreImgv = imageView4;
        this.trainImgv = imageView5;
        this.xftzImgv = imageView6;
    }

    @NonNull
    public static HomeActivityBinding bind(@NonNull View view) {
        int i2 = R.id.actionFlow;
        Flow flow = (Flow) ViewBindings.findChildViewById(view, i2);
        if (flow != null) {
            i2 = R.id.backImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.bgV;
                BLView bLView = (BLView) ViewBindings.findChildViewById(view, i2);
                if (bLView != null) {
                    i2 = R.id.bodyImgv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.codeTv;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            i2 = R.id.iconImgv;
                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView3 != null) {
                                i2 = R.id.integralCl;
                                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout != null) {
                                    i2 = R.id.integralTv;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView2 != null) {
                                        i2 = R.id.meTv;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView3 != null) {
                                            i2 = R.id.scoreImgv;
                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView4 != null) {
                                                i2 = R.id.trainImgv;
                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView5 != null) {
                                                    i2 = R.id.xftzImgv;
                                                    ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                    if (imageView6 != null) {
                                                        return new HomeActivityBinding((ConstraintLayout) view, flow, imageView, bLView, imageView2, textView, imageView3, constraintLayout, textView2, textView3, imageView4, imageView5, imageView6);
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
    public static HomeActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static HomeActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.home_activity, viewGroup, false);
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
