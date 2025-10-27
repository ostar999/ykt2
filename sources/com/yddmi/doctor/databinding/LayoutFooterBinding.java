package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class LayoutFooterBinding implements ViewBinding {

    @NonNull
    public final TextView footerName;

    @NonNull
    public final LinearLayout header;

    @NonNull
    private final LinearLayout rootView;

    private LayoutFooterBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull LinearLayout linearLayout2) {
        this.rootView = linearLayout;
        this.footerName = textView;
        this.header = linearLayout2;
    }

    @NonNull
    public static LayoutFooterBinding bind(@NonNull View view) {
        int i2 = R.id.footer_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView == null) {
            throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
        }
        LinearLayout linearLayout = (LinearLayout) view;
        return new LayoutFooterBinding(linearLayout, textView, linearLayout);
    }

    @NonNull
    public static LayoutFooterBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutFooterBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_footer, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
