package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LoginAliSwitchOtherBinding implements ViewBinding {

    @NonNull
    public final View lineLfet;

    @NonNull
    public final View lineRight;

    @NonNull
    public final TextView otherTv;

    @NonNull
    private final ConstraintLayout rootView;

    private LoginAliSwitchOtherBinding(@NonNull ConstraintLayout constraintLayout, @NonNull View view, @NonNull View view2, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.lineLfet = view;
        this.lineRight = view2;
        this.otherTv = textView;
    }

    @NonNull
    public static LoginAliSwitchOtherBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.lineLfet;
        View viewFindChildViewById2 = ViewBindings.findChildViewById(view, i2);
        if (viewFindChildViewById2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineRight))) != null) {
            i2 = R.id.otherTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                return new LoginAliSwitchOtherBinding((ConstraintLayout) view, viewFindChildViewById2, viewFindChildViewById, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LoginAliSwitchOtherBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LoginAliSwitchOtherBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.login_ali_switch_other, viewGroup, false);
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
