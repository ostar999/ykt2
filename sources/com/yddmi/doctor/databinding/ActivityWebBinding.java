package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ActivityWebBinding implements ViewBinding {

    @NonNull
    public final FrameLayout fl;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final DWebView web;

    @NonNull
    public final ProgressBar webPb;

    private ActivityWebBinding(@NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2, @NonNull DWebView dWebView, @NonNull ProgressBar progressBar) {
        this.rootView = frameLayout;
        this.fl = frameLayout2;
        this.web = dWebView;
        this.webPb = progressBar;
    }

    @NonNull
    public static ActivityWebBinding bind(@NonNull View view) {
        int i2 = R.id.fl;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
        if (frameLayout != null) {
            i2 = R.id.web;
            DWebView dWebView = (DWebView) ViewBindings.findChildViewById(view, i2);
            if (dWebView != null) {
                i2 = R.id.webPb;
                ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i2);
                if (progressBar != null) {
                    return new ActivityWebBinding((FrameLayout) view, frameLayout, dWebView, progressBar);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityWebBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityWebBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_web, viewGroup, false);
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
