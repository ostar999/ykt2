package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginAliSwitchOtherGoBinding implements ViewBinding {

    @NonNull
    public final View lineLfet;

    @NonNull
    public final View lineRight;

    @NonNull
    public final ConstraintLayout otherCl;

    @NonNull
    public final TextView otherTv;

    @NonNull
    public final AppCompatImageView phoneImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final AppCompatImageView wxImgv;

    private LoginAliSwitchOtherGoBinding(@NonNull ConstraintLayout constraintLayout, @NonNull View view, @NonNull View view2, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView, @NonNull AppCompatImageView appCompatImageView, @NonNull AppCompatImageView appCompatImageView2) {
        this.rootView = constraintLayout;
        this.lineLfet = view;
        this.lineRight = view2;
        this.otherCl = constraintLayout2;
        this.otherTv = textView;
        this.phoneImgv = appCompatImageView;
        this.wxImgv = appCompatImageView2;
    }

    @NonNull
    public static LoginAliSwitchOtherGoBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.lineLfet;
        View viewFindChildViewById2 = ViewBindings.findChildViewById(view, i2);
        if (viewFindChildViewById2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineRight))) != null) {
            i2 = R.id.otherCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.otherTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.phoneImgv;
                    AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                    if (appCompatImageView != null) {
                        i2 = R.id.wxImgv;
                        AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                        if (appCompatImageView2 != null) {
                            return new LoginAliSwitchOtherGoBinding((ConstraintLayout) view, viewFindChildViewById2, viewFindChildViewById, constraintLayout, textView, appCompatImageView, appCompatImageView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginAliSwitchOtherGoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginAliSwitchOtherGoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_ali_switch_other_go, viewGroup, false);
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
