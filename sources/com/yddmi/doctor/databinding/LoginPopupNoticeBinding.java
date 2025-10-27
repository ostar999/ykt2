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
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginPopupNoticeBinding implements ViewBinding {

    @NonNull
    public final BLConstraintLayout aCl;

    @NonNull
    public final ImageView adImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView tip1Tv;

    @NonNull
    public final BLTextView tip2Tv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final ImageView xImgv;

    private LoginPopupNoticeBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull ImageView imageView2) {
        this.rootView = constraintLayout;
        this.aCl = bLConstraintLayout;
        this.adImgv = imageView;
        this.tip1Tv = textView;
        this.tip2Tv = bLTextView;
        this.tipTv = textView2;
        this.xImgv = imageView2;
    }

    @NonNull
    public static LoginPopupNoticeBinding bind(@NonNull View view) {
        int i2 = R.id.aCl;
        BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (bLConstraintLayout != null) {
            i2 = R.id.adImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.tip1Tv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.tip2Tv;
                    BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                    if (bLTextView != null) {
                        i2 = R.id.tipTv;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.xImgv;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView2 != null) {
                                return new LoginPopupNoticeBinding((ConstraintLayout) view, bLConstraintLayout, imageView, textView, bLTextView, textView2, imageView2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginPopupNoticeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginPopupNoticeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_popup_notice, viewGroup, false);
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
