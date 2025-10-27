package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MeItemIntegralBinding implements ViewBinding {

    @NonNull
    public final TextView detailTv;

    @NonNull
    public final TextView integralTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final TextView typeTv;

    private MeItemIntegralBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4) {
        this.rootView = frameLayout;
        this.detailTv = textView;
        this.integralTv = textView2;
        this.timeTv = textView3;
        this.typeTv = textView4;
    }

    @NonNull
    public static MeItemIntegralBinding bind(@NonNull View view) {
        int i2 = R.id.detailTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.integralTv;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.timeTv;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView3 != null) {
                    i2 = R.id.typeTv;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView4 != null) {
                        return new MeItemIntegralBinding((FrameLayout) view, textView, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MeItemIntegralBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MeItemIntegralBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_item_integral, viewGroup, false);
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
