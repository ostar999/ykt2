package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RRelativeLayout;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class EbookLoadingPopBinding implements ViewBinding {

    @NonNull
    public final ImageView ivLoading;

    @NonNull
    public final RRelativeLayout layoutRoot;

    @NonNull
    private final RRelativeLayout rootView;

    @NonNull
    public final TextView tvLoadingTitle;

    private EbookLoadingPopBinding(@NonNull RRelativeLayout rRelativeLayout, @NonNull ImageView imageView, @NonNull RRelativeLayout rRelativeLayout2, @NonNull TextView textView) {
        this.rootView = rRelativeLayout;
        this.ivLoading = imageView;
        this.layoutRoot = rRelativeLayout2;
        this.tvLoadingTitle = textView;
    }

    @NonNull
    public static EbookLoadingPopBinding bind(@NonNull View view) {
        int i2 = R.id.iv_loading;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            RRelativeLayout rRelativeLayout = (RRelativeLayout) view;
            int i3 = R.id.tv_loading_title;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i3);
            if (textView != null) {
                return new EbookLoadingPopBinding(rRelativeLayout, imageView, rRelativeLayout, textView);
            }
            i2 = i3;
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static EbookLoadingPopBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static EbookLoadingPopBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.ebook_loading_pop, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RRelativeLayout getRoot() {
        return this.rootView;
    }
}
