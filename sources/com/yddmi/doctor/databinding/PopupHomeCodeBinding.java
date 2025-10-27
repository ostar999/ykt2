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
import com.noober.background.view.BLEditText;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeCodeBinding implements ViewBinding {

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final BLEditText codeEt;

    @NonNull
    public final ConstraintLayout errorCl;

    @NonNull
    public final TextView goBtnTv;

    @NonNull
    public final TextView goTv;

    @NonNull
    public final ConstraintLayout popCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout succesCl;

    @NonNull
    public final TextView tipSuccessTv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final TextView titleTv;

    private PopupHomeCodeBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull BLEditText bLEditText, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout4, @NonNull ConstraintLayout constraintLayout5, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5) {
        this.rootView = constraintLayout;
        this.closeImgv = imageView;
        this.codeCl = constraintLayout2;
        this.codeEt = bLEditText;
        this.errorCl = constraintLayout3;
        this.goBtnTv = textView;
        this.goTv = textView2;
        this.popCl = constraintLayout4;
        this.succesCl = constraintLayout5;
        this.tipSuccessTv = textView3;
        this.tipTv = textView4;
        this.titleTv = textView5;
    }

    @NonNull
    public static PopupHomeCodeBinding bind(@NonNull View view) {
        int i2 = R.id.closeImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.codeEt;
                BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
                if (bLEditText != null) {
                    i2 = R.id.errorCl;
                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                    if (constraintLayout2 != null) {
                        i2 = R.id.goBtnTv;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            i2 = R.id.goTv;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView2 != null) {
                                i2 = R.id.popCl;
                                ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout3 != null) {
                                    i2 = R.id.succesCl;
                                    ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout4 != null) {
                                        i2 = R.id.tipSuccessTv;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView3 != null) {
                                            i2 = R.id.tipTv;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView4 != null) {
                                                i2 = R.id.titleTv;
                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView5 != null) {
                                                    return new PopupHomeCodeBinding((ConstraintLayout) view, imageView, constraintLayout, bLEditText, constraintLayout2, textView, textView2, constraintLayout3, constraintLayout4, textView3, textView4, textView5);
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
    public static PopupHomeCodeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeCodeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_code, viewGroup, false);
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
