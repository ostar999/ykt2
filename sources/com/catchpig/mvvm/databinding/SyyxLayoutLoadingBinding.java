package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.loading.view.LoadingView;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class SyyxLayoutLoadingBinding implements ViewBinding {

    @NonNull
    public final FrameLayout loadingFrame;

    @NonNull
    public final LoadingView loadingView;

    @NonNull
    private final FrameLayout rootView;

    private SyyxLayoutLoadingBinding(@NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2, @NonNull LoadingView loadingView) {
        this.rootView = frameLayout;
        this.loadingFrame = frameLayout2;
        this.loadingView = loadingView;
    }

    @NonNull
    public static SyyxLayoutLoadingBinding bind(@NonNull View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i2 = R.id.loading_view;
        LoadingView loadingView = (LoadingView) ViewBindings.findChildViewById(view, i2);
        if (loadingView != null) {
            return new SyyxLayoutLoadingBinding(frameLayout, frameLayout, loadingView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static SyyxLayoutLoadingBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static SyyxLayoutLoadingBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.syyx_layout_loading, viewGroup, false);
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
