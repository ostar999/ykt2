package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginActivityPswBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final AppCompatImageView eyeImgv;

    @NonNull
    public final TextView forgetTv;

    @NonNull
    public final TextView goLoginTv;

    @NonNull
    public final View lineLfet;

    @NonNull
    public final View lineRight;

    @NonNull
    public final LinearLayout linearLayout;

    @NonNull
    public final TextView loginTv;

    @NonNull
    public final TextView numTv;

    @NonNull
    public final ConstraintLayout otherCl;

    @NonNull
    public final TextView otherTv;

    @NonNull
    public final EditText phoneEt;

    @NonNull
    public final AppCompatImageView phoneImgv;

    @NonNull
    public final EditText pswEt;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout selectCl;

    @NonNull
    public final AppCompatImageView selectImgv;

    @NonNull
    public final AppCompatImageView serviceImgv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final View view;

    @NonNull
    public final AppCompatImageView wxImgv;

    @NonNull
    public final AppCompatImageView x1Imgv;

    @NonNull
    public final AppCompatImageView xImgv;

    private LoginActivityPswBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull AppCompatImageView appCompatImageView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull View view, @NonNull View view2, @NonNull LinearLayout linearLayout, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView5, @NonNull EditText editText, @NonNull AppCompatImageView appCompatImageView3, @NonNull EditText editText2, @NonNull ConstraintLayout constraintLayout3, @NonNull AppCompatImageView appCompatImageView4, @NonNull AppCompatImageView appCompatImageView5, @NonNull TextView textView6, @NonNull View view3, @NonNull AppCompatImageView appCompatImageView6, @NonNull AppCompatImageView appCompatImageView7, @NonNull AppCompatImageView appCompatImageView8) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.eyeImgv = appCompatImageView2;
        this.forgetTv = textView;
        this.goLoginTv = textView2;
        this.lineLfet = view;
        this.lineRight = view2;
        this.linearLayout = linearLayout;
        this.loginTv = textView3;
        this.numTv = textView4;
        this.otherCl = constraintLayout2;
        this.otherTv = textView5;
        this.phoneEt = editText;
        this.phoneImgv = appCompatImageView3;
        this.pswEt = editText2;
        this.selectCl = constraintLayout3;
        this.selectImgv = appCompatImageView4;
        this.serviceImgv = appCompatImageView5;
        this.tipTv = textView6;
        this.view = view3;
        this.wxImgv = appCompatImageView6;
        this.x1Imgv = appCompatImageView7;
        this.xImgv = appCompatImageView8;
    }

    @NonNull
    public static LoginActivityPswBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.eyeImgv;
            AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
            if (appCompatImageView2 != null) {
                i2 = R.id.forgetTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.goLoginTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineLfet))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.lineRight))) != null) {
                        i2 = R.id.linearLayout;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            i2 = R.id.loginTv;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                i2 = R.id.numTv;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView4 != null) {
                                    i2 = R.id.otherCl;
                                    ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout != null) {
                                        i2 = R.id.otherTv;
                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView5 != null) {
                                            i2 = R.id.phoneEt;
                                            EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
                                            if (editText != null) {
                                                i2 = R.id.phoneImgv;
                                                AppCompatImageView appCompatImageView3 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                if (appCompatImageView3 != null) {
                                                    i2 = R.id.pswEt;
                                                    EditText editText2 = (EditText) ViewBindings.findChildViewById(view, i2);
                                                    if (editText2 != null) {
                                                        i2 = R.id.selectCl;
                                                        ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (constraintLayout2 != null) {
                                                            i2 = R.id.selectImgv;
                                                            AppCompatImageView appCompatImageView4 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                            if (appCompatImageView4 != null) {
                                                                i2 = R.id.serviceImgv;
                                                                AppCompatImageView appCompatImageView5 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                if (appCompatImageView5 != null) {
                                                                    i2 = R.id.tipTv;
                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView6 != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                                                        i2 = R.id.wxImgv;
                                                                        AppCompatImageView appCompatImageView6 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                        if (appCompatImageView6 != null) {
                                                                            i2 = R.id.x1Imgv;
                                                                            AppCompatImageView appCompatImageView7 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                            if (appCompatImageView7 != null) {
                                                                                i2 = R.id.xImgv;
                                                                                AppCompatImageView appCompatImageView8 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                if (appCompatImageView8 != null) {
                                                                                    return new LoginActivityPswBinding((ConstraintLayout) view, appCompatImageView, appCompatImageView2, textView, textView2, viewFindChildViewById, viewFindChildViewById2, linearLayout, textView3, textView4, constraintLayout, textView5, editText, appCompatImageView3, editText2, constraintLayout2, appCompatImageView4, appCompatImageView5, textView6, viewFindChildViewById3, appCompatImageView6, appCompatImageView7, appCompatImageView8);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginActivityPswBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginActivityPswBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_activity_psw, viewGroup, false);
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
