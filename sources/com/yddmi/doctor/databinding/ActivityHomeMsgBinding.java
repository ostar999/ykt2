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
public final class ActivityHomeMsgBinding implements ViewBinding {

    @NonNull
    public final TextView detailsTv;

    @NonNull
    public final View lineV;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final TextView typeTv;

    private ActivityHomeMsgBinding(@NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull View view, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4) {
        this.rootView = constraintLayout;
        this.detailsTv = textView;
        this.lineV = view;
        this.timeTv = textView2;
        this.titleTv = textView3;
        this.typeTv = textView4;
    }

    @NonNull
    public static ActivityHomeMsgBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.detailsTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
            i2 = R.id.timeTv;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.titleTv;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView3 != null) {
                    i2 = R.id.typeTv;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView4 != null) {
                        return new ActivityHomeMsgBinding((ConstraintLayout) view, textView, viewFindChildViewById, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityHomeMsgBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityHomeMsgBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_home_msg, viewGroup, false);
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
