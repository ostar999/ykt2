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
import com.noober.background.view.BLLinearLayout;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ExamAdapterTopBinding implements ViewBinding {

    @NonNull
    public final TextView fourTv;

    @NonNull
    public final TextView oneTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final TextView threeTv;

    @NonNull
    public final BLLinearLayout topTypeLl;

    @NonNull
    public final TextView twoTv;

    private ExamAdapterTopBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull BLLinearLayout bLLinearLayout, @NonNull TextView textView4) {
        this.rootView = frameLayout;
        this.fourTv = textView;
        this.oneTv = textView2;
        this.threeTv = textView3;
        this.topTypeLl = bLLinearLayout;
        this.twoTv = textView4;
    }

    @NonNull
    public static ExamAdapterTopBinding bind(@NonNull View view) {
        int i2 = R.id.fourTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.oneTv;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.threeTv;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView3 != null) {
                    i2 = R.id.topTypeLl;
                    BLLinearLayout bLLinearLayout = (BLLinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (bLLinearLayout != null) {
                        i2 = R.id.twoTv;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView4 != null) {
                            return new ExamAdapterTopBinding((FrameLayout) view, textView, textView2, textView3, bLLinearLayout, textView4);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ExamAdapterTopBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ExamAdapterTopBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.exam_adapter_top, viewGroup, false);
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
