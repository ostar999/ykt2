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
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginPopupBottomAgreementBinding implements ViewBinding {

    @NonNull
    public final BLTextView goTv;

    @NonNull
    public final View lineV;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView titleTv;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25738tv;

    @NonNull
    public final TextView tv1;

    @NonNull
    public final ImageView xImgv;

    private LoginPopupBottomAgreementBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLTextView bLTextView, @NonNull View view, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.goTv = bLTextView;
        this.lineV = view;
        this.titleTv = textView;
        this.f25738tv = textView2;
        this.tv1 = textView3;
        this.xImgv = imageView;
    }

    @NonNull
    public static LoginPopupBottomAgreementBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.goTv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
            i2 = R.id.titleTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.f25733tv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.tv1;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        i2 = R.id.xImgv;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null) {
                            return new LoginPopupBottomAgreementBinding((ConstraintLayout) view, bLTextView, viewFindChildViewById, textView, textView2, textView3, imageView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginPopupBottomAgreementBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginPopupBottomAgreementBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_popup_bottom_agreement, viewGroup, false);
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
