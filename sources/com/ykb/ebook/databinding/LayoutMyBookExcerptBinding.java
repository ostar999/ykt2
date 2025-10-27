package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class LayoutMyBookExcerptBinding implements ViewBinding {

    @NonNull
    public final AppBarLayout appbarlayout;

    @NonNull
    public final RTextView btnToRead;

    @NonNull
    public final CollapsingToolbarLayout collapse;

    @NonNull
    public final RelativeLayout flNavigation;

    @NonNull
    public final AppCompatImageView imgBack;

    @NonNull
    public final ImageView imgPic;

    @NonNull
    public final View line;

    @NonNull
    public final RelativeLayout lyBookInfo;

    @NonNull
    public final TextView navTitle;

    @NonNull
    public final RTextView navToRead;

    @NonNull
    private final CoordinatorLayout rootView;

    @NonNull
    public final TabLayout tabLayout;

    @NonNull
    public final Toolbar toobars1;

    @NonNull
    public final TextView tvBookAuthor;

    @NonNull
    public final TextView tvBookName;

    @NonNull
    public final ViewPager2 viewPager;

    private LayoutMyBookExcerptBinding(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, @NonNull RTextView rTextView, @NonNull CollapsingToolbarLayout collapsingToolbarLayout, @NonNull RelativeLayout relativeLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull ImageView imageView, @NonNull View view, @NonNull RelativeLayout relativeLayout2, @NonNull TextView textView, @NonNull RTextView rTextView2, @NonNull TabLayout tabLayout, @NonNull Toolbar toolbar, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull ViewPager2 viewPager2) {
        this.rootView = coordinatorLayout;
        this.appbarlayout = appBarLayout;
        this.btnToRead = rTextView;
        this.collapse = collapsingToolbarLayout;
        this.flNavigation = relativeLayout;
        this.imgBack = appCompatImageView;
        this.imgPic = imageView;
        this.line = view;
        this.lyBookInfo = relativeLayout2;
        this.navTitle = textView;
        this.navToRead = rTextView2;
        this.tabLayout = tabLayout;
        this.toobars1 = toolbar;
        this.tvBookAuthor = textView2;
        this.tvBookName = textView3;
        this.viewPager = viewPager2;
    }

    @NonNull
    public static LayoutMyBookExcerptBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.appbarlayout;
        AppBarLayout appBarLayout = (AppBarLayout) ViewBindings.findChildViewById(view, i2);
        if (appBarLayout != null) {
            i2 = R.id.btn_to_read;
            RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
            if (rTextView != null) {
                i2 = R.id.collapse;
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) ViewBindings.findChildViewById(view, i2);
                if (collapsingToolbarLayout != null) {
                    i2 = R.id.fl_navigation;
                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                    if (relativeLayout != null) {
                        i2 = R.id.img_back;
                        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                        if (appCompatImageView != null) {
                            i2 = R.id.img_pic;
                            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                                i2 = R.id.ly_book_info;
                                RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                if (relativeLayout2 != null) {
                                    i2 = R.id.nav_title;
                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView != null) {
                                        i2 = R.id.nav_to_read;
                                        RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                        if (rTextView2 != null) {
                                            i2 = R.id.tab_layout;
                                            TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(view, i2);
                                            if (tabLayout != null) {
                                                i2 = R.id.toobars1;
                                                Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                                                if (toolbar != null) {
                                                    i2 = R.id.tv_book_author;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView2 != null) {
                                                        i2 = R.id.tv_book_name;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.view_pager;
                                                            ViewPager2 viewPager2 = (ViewPager2) ViewBindings.findChildViewById(view, i2);
                                                            if (viewPager2 != null) {
                                                                return new LayoutMyBookExcerptBinding((CoordinatorLayout) view, appBarLayout, rTextView, collapsingToolbarLayout, relativeLayout, appCompatImageView, imageView, viewFindChildViewById, relativeLayout2, textView, rTextView2, tabLayout, toolbar, textView2, textView3, viewPager2);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutMyBookExcerptBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutMyBookExcerptBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_my_book_excerpt, viewGroup, false);
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
