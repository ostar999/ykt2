package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.al.open.SplitEditTextView;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginActivityBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final TextView codeTipTv;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final FrameLayout hostFl;

    @NonNull
    public final ConstraintLayout inputPhoneCl;

    @NonNull
    public final View lineLfet;

    @NonNull
    public final View lineRight;

    @NonNull
    public final TextView loginTipTv;

    @NonNull
    public final TextView loginTv;

    @NonNull
    public final ConstraintLayout otherCl;

    @NonNull
    public final TextView otherTv;

    @NonNull
    public final ConstraintLayout phoneCl;

    @NonNull
    public final EditText phoneEt;

    @NonNull
    public final AppCompatImageView phoneImgv;

    @NonNull
    public final TextView pswTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout selectCl;

    @NonNull
    public final AppCompatImageView selectImgv;

    @NonNull
    public final BLTextView sendNumG0Tv;

    @NonNull
    public final TextView sendNumTv;

    @NonNull
    public final AppCompatImageView serviceImgv;

    @NonNull
    public final SplitEditTextView set;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final AppCompatImageView wxImgv;

    @NonNull
    public final AppCompatImageView xImgv;

    private LoginActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull FrameLayout frameLayout, @NonNull ConstraintLayout constraintLayout3, @NonNull View view, @NonNull View view2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull ConstraintLayout constraintLayout4, @NonNull TextView textView5, @NonNull ConstraintLayout constraintLayout5, @NonNull EditText editText, @NonNull AppCompatImageView appCompatImageView2, @NonNull TextView textView6, @NonNull ConstraintLayout constraintLayout6, @NonNull AppCompatImageView appCompatImageView3, @NonNull BLTextView bLTextView, @NonNull TextView textView7, @NonNull AppCompatImageView appCompatImageView4, @NonNull SplitEditTextView splitEditTextView, @NonNull TextView textView8, @NonNull AppCompatImageView appCompatImageView5, @NonNull AppCompatImageView appCompatImageView6) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.codeCl = constraintLayout2;
        this.codeTipTv = textView;
        this.codeTv = textView2;
        this.hostFl = frameLayout;
        this.inputPhoneCl = constraintLayout3;
        this.lineLfet = view;
        this.lineRight = view2;
        this.loginTipTv = textView3;
        this.loginTv = textView4;
        this.otherCl = constraintLayout4;
        this.otherTv = textView5;
        this.phoneCl = constraintLayout5;
        this.phoneEt = editText;
        this.phoneImgv = appCompatImageView2;
        this.pswTv = textView6;
        this.selectCl = constraintLayout6;
        this.selectImgv = appCompatImageView3;
        this.sendNumG0Tv = bLTextView;
        this.sendNumTv = textView7;
        this.serviceImgv = appCompatImageView4;
        this.set = splitEditTextView;
        this.tipTv = textView8;
        this.wxImgv = appCompatImageView5;
        this.xImgv = appCompatImageView6;
    }

    @NonNull
    public static LoginActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
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
                        i2 = R.id.hostFl;
                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                        if (frameLayout != null) {
                            i2 = R.id.inputPhoneCl;
                            ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineLfet))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.lineRight))) != null) {
                                i2 = R.id.loginTipTv;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView3 != null) {
                                    i2 = R.id.loginTv;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView4 != null) {
                                        i2 = R.id.otherCl;
                                        ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                        if (constraintLayout3 != null) {
                                            i2 = R.id.otherTv;
                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView5 != null) {
                                                i2 = R.id.phoneCl;
                                                ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                if (constraintLayout4 != null) {
                                                    i2 = R.id.phoneEt;
                                                    EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
                                                    if (editText != null) {
                                                        i2 = R.id.phoneImgv;
                                                        AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                        if (appCompatImageView2 != null) {
                                                            i2 = R.id.pswTv;
                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView6 != null) {
                                                                i2 = R.id.selectCl;
                                                                ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                if (constraintLayout5 != null) {
                                                                    i2 = R.id.selectImgv;
                                                                    AppCompatImageView appCompatImageView3 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                    if (appCompatImageView3 != null) {
                                                                        i2 = R.id.sendNumG0Tv;
                                                                        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (bLTextView != null) {
                                                                            i2 = R.id.sendNumTv;
                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView7 != null) {
                                                                                i2 = R.id.serviceImgv;
                                                                                AppCompatImageView appCompatImageView4 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                if (appCompatImageView4 != null) {
                                                                                    i2 = R.id.set;
                                                                                    SplitEditTextView splitEditTextView = (SplitEditTextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (splitEditTextView != null) {
                                                                                        i2 = R.id.tipTv;
                                                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView8 != null) {
                                                                                            i2 = R.id.wxImgv;
                                                                                            AppCompatImageView appCompatImageView5 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (appCompatImageView5 != null) {
                                                                                                i2 = R.id.xImgv;
                                                                                                AppCompatImageView appCompatImageView6 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (appCompatImageView6 != null) {
                                                                                                    return new LoginActivityBinding((ConstraintLayout) view, appCompatImageView, constraintLayout, textView, textView2, frameLayout, constraintLayout2, viewFindChildViewById, viewFindChildViewById2, textView3, textView4, constraintLayout3, textView5, constraintLayout4, editText, appCompatImageView2, textView6, constraintLayout5, appCompatImageView3, bLTextView, textView7, appCompatImageView4, splitEditTextView, textView8, appCompatImageView5, appCompatImageView6);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_activity, viewGroup, false);
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
