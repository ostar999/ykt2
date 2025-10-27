package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupLogoutDialogBinding implements ViewBinding {

    @NonNull
    public final TextView detailTv;

    @NonNull
    public final TextView oneTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ConstraintLayout twoCl;

    @NonNull
    public final TextView twoTv;

    private PopupLogoutDialogBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout, @NonNull TextView textView3) {
        this.rootView = frameLayout;
        this.detailTv = textView;
        this.oneTv = textView2;
        this.twoCl = constraintLayout;
        this.twoTv = textView3;
    }

    @NonNull
    public static PopupLogoutDialogBinding bind(@NonNull View view) {
        int i2 = R.id.detailTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.oneTv;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.twoCl;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout != null) {
                    i2 = R.id.twoTv;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        return new PopupLogoutDialogBinding((FrameLayout) view, textView, textView2, constraintLayout, textView3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupLogoutDialogBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupLogoutDialogBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_logout_dialog, viewGroup, false);
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
