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
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MainPopupBuyBinding implements ViewBinding {

    @NonNull
    public final TextView buyBtnTv;

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final ConstraintLayout codeBtnCl;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final TextView detailTv;

    @NonNull
    public final ConstraintLayout errorCl;

    @NonNull
    public final TextView goTv;

    @NonNull
    public final TextView integralTv;

    @NonNull
    public final ConstraintLayout popCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView shareBtnTv;

    @NonNull
    public final TextView shareTv;

    @NonNull
    public final ConstraintLayout succesCl;

    @NonNull
    public final TextView tipSuccessTv;

    @NonNull
    public final TextView tipTv;

    private MainPopupBuyBinding(@NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout4, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull ConstraintLayout constraintLayout5, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull ConstraintLayout constraintLayout6, @NonNull TextView textView7, @NonNull TextView textView8) {
        this.rootView = constraintLayout;
        this.buyBtnTv = textView;
        this.closeImgv = imageView;
        this.codeBtnCl = constraintLayout2;
        this.codeCl = constraintLayout3;
        this.detailTv = textView2;
        this.errorCl = constraintLayout4;
        this.goTv = textView3;
        this.integralTv = textView4;
        this.popCl = constraintLayout5;
        this.shareBtnTv = textView5;
        this.shareTv = textView6;
        this.succesCl = constraintLayout6;
        this.tipSuccessTv = textView7;
        this.tipTv = textView8;
    }

    @NonNull
    public static MainPopupBuyBinding bind(@NonNull View view) {
        int i2 = R.id.buyBtnTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.closeImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.codeBtnCl;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout != null) {
                    i2 = R.id.codeCl;
                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                    if (constraintLayout2 != null) {
                        i2 = R.id.detailTv;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.errorCl;
                            ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout3 != null) {
                                i2 = R.id.goTv;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView3 != null) {
                                    i2 = R.id.integralTv;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView4 != null) {
                                        i2 = R.id.popCl;
                                        ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                        if (constraintLayout4 != null) {
                                            i2 = R.id.shareBtnTv;
                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView5 != null) {
                                                i2 = R.id.shareTv;
                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView6 != null) {
                                                    i2 = R.id.succesCl;
                                                    ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                    if (constraintLayout5 != null) {
                                                        i2 = R.id.tipSuccessTv;
                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView7 != null) {
                                                            i2 = R.id.tipTv;
                                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView8 != null) {
                                                                return new MainPopupBuyBinding((ConstraintLayout) view, textView, imageView, constraintLayout, constraintLayout2, textView2, constraintLayout3, textView3, textView4, constraintLayout4, textView5, textView6, constraintLayout5, textView7, textView8);
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
    public static MainPopupBuyBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MainPopupBuyBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.main_popup_buy, viewGroup, false);
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
