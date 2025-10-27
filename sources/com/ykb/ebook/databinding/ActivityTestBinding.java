package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ActivityTestBinding implements ViewBinding {

    @NonNull
    public final ImageView imgBack;

    @NonNull
    private final CoordinatorLayout rootView;

    @NonNull
    public final RecyclerView rvHot;

    @NonNull
    public final RecyclerView rvNewest;

    @NonNull
    public final Toolbar toolbar;

    @NonNull
    public final TextView tvTitle;

    private ActivityTestBinding(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView imageView, @NonNull RecyclerView recyclerView, @NonNull RecyclerView recyclerView2, @NonNull Toolbar toolbar, @NonNull TextView textView) {
        this.rootView = coordinatorLayout;
        this.imgBack = imageView;
        this.rvHot = recyclerView;
        this.rvNewest = recyclerView2;
        this.toolbar = toolbar;
        this.tvTitle = textView;
    }

    @NonNull
    public static ActivityTestBinding bind(@NonNull View view) {
        int i2 = R.id.img_back;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.rv_hot;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.rv_newest;
                RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                if (recyclerView2 != null) {
                    i2 = R.id.toolbar;
                    Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                    if (toolbar != null) {
                        i2 = R.id.tv_title;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            return new ActivityTestBinding((CoordinatorLayout) view, imageView, recyclerView, recyclerView2, toolbar, textView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityTestBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityTestBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_test, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public CoordinatorLayout getRoot() {
        return this.rootView;
    }
}
