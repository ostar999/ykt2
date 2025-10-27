package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.slider.ArrowView;

/* loaded from: classes6.dex */
public final class LayoutDefaultTipViewBinding implements ViewBinding {

    @NonNull
    public final ArrowView arrowView;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView tipText;

    private LayoutDefaultTipViewBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ArrowView arrowView, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.arrowView = arrowView;
        this.tipText = textView;
    }

    @NonNull
    public static LayoutDefaultTipViewBinding bind(@NonNull View view) {
        int i2 = R.id.arrow_view;
        ArrowView arrowView = (ArrowView) ViewBindings.findChildViewById(view, i2);
        if (arrowView != null) {
            i2 = R.id.tip_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                return new LayoutDefaultTipViewBinding((ConstraintLayout) view, arrowView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutDefaultTipViewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutDefaultTipViewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_default_tip_view, viewGroup, false);
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
