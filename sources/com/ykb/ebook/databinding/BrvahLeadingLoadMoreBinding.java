package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class BrvahLeadingLoadMoreBinding implements ViewBinding {

    @NonNull
    public final ProgressBar loadingProgress;

    @NonNull
    private final FrameLayout rootView;

    private BrvahLeadingLoadMoreBinding(@NonNull FrameLayout frameLayout, @NonNull ProgressBar progressBar) {
        this.rootView = frameLayout;
        this.loadingProgress = progressBar;
    }

    @NonNull
    public static BrvahLeadingLoadMoreBinding bind(@NonNull View view) {
        int i2 = R.id.loading_progress;
        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i2);
        if (progressBar != null) {
            return new BrvahLeadingLoadMoreBinding((FrameLayout) view, progressBar);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static BrvahLeadingLoadMoreBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static BrvahLeadingLoadMoreBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.brvah_leading_load_more, viewGroup, false);
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
