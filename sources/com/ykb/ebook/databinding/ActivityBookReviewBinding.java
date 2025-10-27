package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ActivityBookReviewBinding implements ViewBinding {

    @NonNull
    public final AppBarLayout appbar;

    @NonNull
    public final CoordinatorLayout coordinatorLayout;

    @NonNull
    public final CollapsingToolbarLayout ctl;

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final LinearLayout llAdd;

    @NonNull
    public final LinearLayout llCommentLocate;

    @NonNull
    public final RLinearLayout llCommentWrap;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    public final SmartRefreshLayout refreshLayout;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RecyclerView rvComments;

    @NonNull
    public final Toolbar titleBar;

    @NonNull
    public final RTextView toolbarContinue;

    @NonNull
    public final TextView toolbarTitle;

    @NonNull
    public final RTextView tvAddSp;

    @NonNull
    public final RTextView tvHot;

    @NonNull
    public final RTextView tvNew;

    @NonNull
    public final RImageView userAvatar;

    @NonNull
    public final View vBottom;

    private ActivityBookReviewBinding(@NonNull FrameLayout frameLayout, @NonNull AppBarLayout appBarLayout, @NonNull CoordinatorLayout coordinatorLayout, @NonNull CollapsingToolbarLayout collapsingToolbarLayout, @NonNull ImageView imageView, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull RLinearLayout rLinearLayout, @NonNull ClassicsHeader classicsHeader, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull RecyclerView recyclerView, @NonNull Toolbar toolbar, @NonNull RTextView rTextView, @NonNull TextView textView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull RTextView rTextView4, @NonNull RImageView rImageView, @NonNull View view) {
        this.rootView = frameLayout;
        this.appbar = appBarLayout;
        this.coordinatorLayout = coordinatorLayout;
        this.ctl = collapsingToolbarLayout;
        this.imgBack = imageView;
        this.llAdd = linearLayout;
        this.llCommentLocate = linearLayout2;
        this.llCommentWrap = rLinearLayout;
        this.refreshHeader = classicsHeader;
        this.refreshLayout = smartRefreshLayout;
        this.rvComments = recyclerView;
        this.titleBar = toolbar;
        this.toolbarContinue = rTextView;
        this.toolbarTitle = textView;
        this.tvAddSp = rTextView2;
        this.tvHot = rTextView3;
        this.tvNew = rTextView4;
        this.userAvatar = rImageView;
        this.vBottom = view;
    }

    @NonNull
    public static ActivityBookReviewBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.appbar;
        AppBarLayout appBarLayout = (AppBarLayout) ViewBindings.findChildViewById(view, i2);
        if (appBarLayout != null) {
            i2 = R.id.coordinatorLayout;
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) ViewBindings.findChildViewById(view, i2);
            if (coordinatorLayout != null) {
                i2 = R.id.ctl;
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) ViewBindings.findChildViewById(view, i2);
                if (collapsingToolbarLayout != null) {
                    i2 = R.id.img_back;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        i2 = R.id.ll_add;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            i2 = R.id.ll_comment_locate;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout2 != null) {
                                i2 = R.id.ll_comment_wrap;
                                RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (rLinearLayout != null) {
                                    i2 = R.id.refresh_header;
                                    ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                                    if (classicsHeader != null) {
                                        i2 = R.id.refresh_layout;
                                        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                        if (smartRefreshLayout != null) {
                                            i2 = R.id.rvComments;
                                            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                            if (recyclerView != null) {
                                                i2 = R.id.title_bar;
                                                Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                                                if (toolbar != null) {
                                                    i2 = R.id.toolbar_continue;
                                                    RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (rTextView != null) {
                                                        i2 = R.id.toolbar_title;
                                                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView != null) {
                                                            i2 = R.id.tv_add_sp;
                                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView2 != null) {
                                                                i2 = R.id.tv_hot;
                                                                RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                if (rTextView3 != null) {
                                                                    i2 = R.id.tv_new;
                                                                    RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (rTextView4 != null) {
                                                                        i2 = R.id.user_avatar;
                                                                        RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
                                                                        if (rImageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.v_bottom))) != null) {
                                                                            return new ActivityBookReviewBinding((FrameLayout) view, appBarLayout, coordinatorLayout, collapsingToolbarLayout, imageView, linearLayout, linearLayout2, rLinearLayout, classicsHeader, smartRefreshLayout, recyclerView, toolbar, rTextView, textView, rTextView2, rTextView3, rTextView4, rImageView, viewFindChildViewById);
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
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_book_review, viewGroup, false);
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
