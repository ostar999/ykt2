package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ActivityWebLandscapeBinding implements ViewBinding {

    @NonNull
    public final ImageView backImgv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final DWebView web;

    private ActivityWebLandscapeBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull DWebView dWebView) {
        this.rootView = frameLayout;
        this.backImgv = imageView;
        this.web = dWebView;
    }

    @NonNull
    public static ActivityWebLandscapeBinding bind(@NonNull View view) {
        int i2 = R.id.backImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.web;
            DWebView dWebView = (DWebView) ViewBindings.findChildViewById(view, i2);
            if (dWebView != null) {
                return new ActivityWebLandscapeBinding((FrameLayout) view, imageView, dWebView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityWebLandscapeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityWebLandscapeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_web_landscape, viewGroup, false);
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
