package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.al.open.SplitEditTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginActivityWxBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final TextView codeTipTv;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final ConstraintLayout inputPhoneCl;

    @NonNull
    public final TextView loginTv;

    @NonNull
    public final ConstraintLayout phoneCl;

    @NonNull
    public final EditText phoneEt;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout selectCl;

    @NonNull
    public final AppCompatImageView selectImgv;

    @NonNull
    public final TextView sendNumG0Tv;

    @NonNull
    public final TextView sendNumTv;

    @NonNull
    public final SplitEditTextView set;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final AppCompatImageView xImgv;

    private LoginActivityWxBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView3, @NonNull ConstraintLayout constraintLayout4, @NonNull EditText editText, @NonNull ConstraintLayout constraintLayout5, @NonNull AppCompatImageView appCompatImageView2, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull SplitEditTextView splitEditTextView, @NonNull TextView textView6, @NonNull AppCompatImageView appCompatImageView3) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.codeCl = constraintLayout2;
        this.codeTipTv = textView;
        this.codeTv = textView2;
        this.inputPhoneCl = constraintLayout3;
        this.loginTv = textView3;
        this.phoneCl = constraintLayout4;
        this.phoneEt = editText;
        this.selectCl = constraintLayout5;
        this.selectImgv = appCompatImageView2;
        this.sendNumG0Tv = textView4;
        this.sendNumTv = textView5;
        this.set = splitEditTextView;
        this.tipTv = textView6;
        this.xImgv = appCompatImageView3;
    }

    @NonNull
    public static LoginActivityWxBinding bind(@NonNull View view) {
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.codeCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.codeTipTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.codeTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.inputPhoneCl;
                        ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (constraintLayout2 != null) {
                            i2 = R.id.loginTv;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                i2 = R.id.phoneCl;
                                ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout3 != null) {
                                    i2 = R.id.phoneEt;
                                    EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
                                    if (editText != null) {
                                        i2 = R.id.selectCl;
                                        ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                        if (constraintLayout4 != null) {
                                            i2 = R.id.selectImgv;
                                            AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                            if (appCompatImageView2 != null) {
                                                i2 = R.id.sendNumG0Tv;
                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView4 != null) {
                                                    i2 = R.id.sendNumTv;
                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView5 != null) {
                                                        i2 = R.id.set;
                                                        SplitEditTextView splitEditTextView = (SplitEditTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (splitEditTextView != null) {
                                                            i2 = R.id.tipTv;
                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView6 != null) {
                                                                i2 = R.id.xImgv;
                                                                AppCompatImageView appCompatImageView3 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                if (appCompatImageView3 != null) {
                                                                    return new LoginActivityWxBinding((ConstraintLayout) view, appCompatImageView, constraintLayout, textView, textView2, constraintLayout2, textView3, constraintLayout3, editText, constraintLayout4, appCompatImageView2, textView4, textView5, splitEditTextView, textView6, appCompatImageView3);
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
    public static LoginActivityWxBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginActivityWxBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_activity_wx, viewGroup, false);
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
