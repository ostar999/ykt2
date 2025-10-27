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
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginActivityPswSetBinding implements ViewBinding {

    @NonNull
    public final ConstraintLayout againCl;

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final BLTextView goTv;

    @NonNull
    public final View line2;

    @NonNull
    public final EditText pswAgainEt;

    @NonNull
    public final EditText pswEt;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final TextView skipTv;

    @NonNull
    public final TextView tipTv;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25736tv;

    @NonNull
    public final AppCompatImageView x1Imgv;

    @NonNull
    public final AppCompatImageView xImgv;

    private LoginActivityPswSetBinding(@NonNull FrameLayout frameLayout, @NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull BLTextView bLTextView, @NonNull View view, @NonNull EditText editText, @NonNull EditText editText2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull AppCompatImageView appCompatImageView2, @NonNull AppCompatImageView appCompatImageView3) {
        this.rootView = frameLayout;
        this.againCl = constraintLayout;
        this.backImgv = appCompatImageView;
        this.goTv = bLTextView;
        this.line2 = view;
        this.pswAgainEt = editText;
        this.pswEt = editText2;
        this.skipTv = textView;
        this.tipTv = textView2;
        this.f25736tv = textView3;
        this.x1Imgv = appCompatImageView2;
        this.xImgv = appCompatImageView3;
    }

    @NonNull
    public static LoginActivityPswSetBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.againCl;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (constraintLayout != null) {
            i2 = R.id.backImgv;
            AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
            if (appCompatImageView != null) {
                i2 = R.id.goTv;
                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                if (bLTextView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line2))) != null) {
                    i2 = R.id.pswAgainEt;
                    EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
                    if (editText != null) {
                        i2 = R.id.pswEt;
                        EditText editText2 = (EditText) ViewBindings.findChildViewById(view, i2);
                        if (editText2 != null) {
                            i2 = R.id.skipTv;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.tipTv;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null) {
                                    i2 = R.id.f25733tv;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView3 != null) {
                                        i2 = R.id.x1Imgv;
                                        AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                        if (appCompatImageView2 != null) {
                                            i2 = R.id.xImgv;
                                            AppCompatImageView appCompatImageView3 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                            if (appCompatImageView3 != null) {
                                                return new LoginActivityPswSetBinding((FrameLayout) view, constraintLayout, appCompatImageView, bLTextView, viewFindChildViewById, editText, editText2, textView, textView2, textView3, appCompatImageView2, appCompatImageView3);
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
    public static LoginActivityPswSetBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginActivityPswSetBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_activity_psw_set, viewGroup, false);
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
