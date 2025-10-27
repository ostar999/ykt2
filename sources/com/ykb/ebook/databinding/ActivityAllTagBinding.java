package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ActivityAllTagBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView btnSearch;

    @NonNull
    public final FrameLayout flNavigation;

    @NonNull
    public final AppCompatImageView imgBack;

    @NonNull
    public final View line;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TabLayout tabLayout;

    @NonNull
    public final ViewPager2 viewPager;

    private ActivityAllTagBinding(@NonNull LinearLayout linearLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull FrameLayout frameLayout, @NonNull AppCompatImageView appCompatImageView2, @NonNull View view, @NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2) {
        this.rootView = linearLayout;
        this.btnSearch = appCompatImageView;
        this.flNavigation = frameLayout;
        this.imgBack = appCompatImageView2;
        this.line = view;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager2;
    }

    @NonNull
    public static ActivityAllTagBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.btn_search;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.fl_navigation;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
            if (frameLayout != null) {
                i2 = R.id.img_back;
                AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                if (appCompatImageView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                    i2 = R.id.tab_layout;
                    TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(view, i2);
                    if (tabLayout != null) {
                        i2 = R.id.view_pager;
                        ViewPager2 viewPager2 = (ViewPager2) ViewBindings.findChildViewById(view, i2);
                        if (viewPager2 != null) {
                            return new ActivityAllTagBinding((LinearLayout) view, appCompatImageView, frameLayout, appCompatImageView2, viewFindChildViewById, tabLayout, viewPager2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityAllTagBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityAllTagBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_all_tag, viewGroup, false);
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
