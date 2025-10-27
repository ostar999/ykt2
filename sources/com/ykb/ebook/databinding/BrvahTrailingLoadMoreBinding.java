package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class BrvahTrailingLoadMoreBinding implements ViewBinding {

    @NonNull
    public final FrameLayout loadMoreLoadCompleteView;

    @NonNull
    public final FrameLayout loadMoreLoadEndView;

    @NonNull
    public final FrameLayout loadMoreLoadFailView;

    @NonNull
    public final LinearLayout loadMoreLoadingView;

    @NonNull
    public final ProgressBar loadingProgress;

    @NonNull
    public final TextView loadingText;

    @NonNull
    private final FrameLayout rootView;

    private BrvahTrailingLoadMoreBinding(@NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2, @NonNull FrameLayout frameLayout3, @NonNull FrameLayout frameLayout4, @NonNull LinearLayout linearLayout, @NonNull ProgressBar progressBar, @NonNull TextView textView) {
        this.rootView = frameLayout;
        this.loadMoreLoadCompleteView = frameLayout2;
        this.loadMoreLoadEndView = frameLayout3;
        this.loadMoreLoadFailView = frameLayout4;
        this.loadMoreLoadingView = linearLayout;
        this.loadingProgress = progressBar;
        this.loadingText = textView;
    }

    @NonNull
    public static BrvahTrailingLoadMoreBinding bind(@NonNull View view) {
        int i2 = R.id.load_more_load_complete_view;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
        if (frameLayout != null) {
            i2 = R.id.load_more_load_end_view;
            FrameLayout frameLayout2 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
            if (frameLayout2 != null) {
                i2 = R.id.load_more_load_fail_view;
                FrameLayout frameLayout3 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                if (frameLayout3 != null) {
                    i2 = R.id.load_more_loading_view;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.loading_progress;
                        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i2);
                        if (progressBar != null) {
                            i2 = R.id.loading_text;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                return new BrvahTrailingLoadMoreBinding((FrameLayout) view, frameLayout, frameLayout2, frameLayout3, linearLayout, progressBar, textView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static BrvahTrailingLoadMoreBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static BrvahTrailingLoadMoreBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.brvah_trailing_load_more, viewGroup, false);
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
