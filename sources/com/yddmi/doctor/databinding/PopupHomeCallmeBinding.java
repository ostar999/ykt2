package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeCallmeBinding implements ViewBinding {

    @NonNull
    public final TextView callTv;

    @NonNull
    public final TextView flowTv;

    @NonNull
    public final ImageView logoImgv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final TextView sloganTv;

    @NonNull
    public final ImageView xImgv;

    private PopupHomeCallmeBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull TextView textView3, @NonNull ImageView imageView2) {
        this.rootView = frameLayout;
        this.callTv = textView;
        this.flowTv = textView2;
        this.logoImgv = imageView;
        this.sloganTv = textView3;
        this.xImgv = imageView2;
    }

    @NonNull
    public static PopupHomeCallmeBinding bind(@NonNull View view) {
        int i2 = R.id.callTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.flowTv;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.logoImgv;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.sloganTv;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        i2 = R.id.xImgv;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView2 != null) {
                            return new PopupHomeCallmeBinding((FrameLayout) view, textView, textView2, imageView, textView3, imageView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupHomeCallmeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeCallmeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_callme, viewGroup, false);
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
