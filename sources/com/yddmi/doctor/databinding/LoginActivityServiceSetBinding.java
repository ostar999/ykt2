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
import com.noober.background.view.BLEditText;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginActivityServiceSetBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final BLEditText et;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView saveTv;

    @NonNull
    public final TextView tipTv;

    private LoginActivityServiceSetBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull BLEditText bLEditText, @NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.et = bLEditText;
        this.saveTv = textView;
        this.tipTv = textView2;
    }

    @NonNull
    public static LoginActivityServiceSetBinding bind(@NonNull View view) {
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.et;
            BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
            if (bLEditText != null) {
                i2 = R.id.saveTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.tipTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        return new LoginActivityServiceSetBinding((ConstraintLayout) view, appCompatImageView, bLEditText, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginActivityServiceSetBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginActivityServiceSetBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_activity_service_set, viewGroup, false);
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
