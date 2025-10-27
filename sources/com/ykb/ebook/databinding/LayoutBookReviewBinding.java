package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.RoundCornerProgressBar;

/* loaded from: classes6.dex */
public final class LayoutBookReviewBinding implements ViewBinding {

    @NonNull
    public final RTextView btnHot;

    @NonNull
    public final RTextView btnNews;

    @NonNull
    public final RoundCornerProgressBar highCommentProgress;

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final RImageView imgCover;

    @NonNull
    public final LinearLayout llHead;

    @NonNull
    public final RoundCornerProgressBar lowCommentProgress;

    @NonNull
    public final RelativeLayout lyTab;

    @NonNull
    public final RLinearLayout lyTabView;

    @NonNull
    public final LinearLayout lyTitleView;

    @NonNull
    public final RoundCornerProgressBar middleCommentProgress;

    @NonNull
    public final RecyclerView recyexpend;

    @NonNull
    public final SmartRefreshLayout refreshLayout;

    @NonNull
    private final SmartRefreshLayout rootView;

    @NonNull
    public final RecyclerView rvNewest;

    @NonNull
    public final RTextView toolbarContinue;

    @NonNull
    public final TextView toolbarTitle;

    @NonNull
    public final TextView tvAuthor;

    @NonNull
    public final TextView tvCommentNum;

    @NonNull
    public final RTextView tvContinue;

    @NonNull
    public final RTextView tvHigh;

    @NonNull
    public final RTextView tvLow;

    @NonNull
    public final RTextView tvMiddle;

    @NonNull
    public final TextView tvScore;

    @NonNull
    public final TextView tvTabTitle;

    @NonNull
    public final TextView tvTitle;

    private LayoutBookReviewBinding(@NonNull SmartRefreshLayout smartRefreshLayout, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull RoundCornerProgressBar roundCornerProgressBar, @NonNull ImageView imageView, @NonNull RImageView rImageView, @NonNull LinearLayout linearLayout, @NonNull RoundCornerProgressBar roundCornerProgressBar2, @NonNull RelativeLayout relativeLayout, @NonNull RLinearLayout rLinearLayout, @NonNull LinearLayout linearLayout2, @NonNull RoundCornerProgressBar roundCornerProgressBar3, @NonNull RecyclerView recyclerView, @NonNull SmartRefreshLayout smartRefreshLayout2, @NonNull RecyclerView recyclerView2, @NonNull RTextView rTextView3, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull RTextView rTextView4, @NonNull RTextView rTextView5, @NonNull RTextView rTextView6, @NonNull RTextView rTextView7, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6) {
        this.rootView = smartRefreshLayout;
        this.btnHot = rTextView;
        this.btnNews = rTextView2;
        this.highCommentProgress = roundCornerProgressBar;
        this.imgBack = imageView;
        this.imgCover = rImageView;
        this.llHead = linearLayout;
        this.lowCommentProgress = roundCornerProgressBar2;
        this.lyTab = relativeLayout;
        this.lyTabView = rLinearLayout;
        this.lyTitleView = linearLayout2;
        this.middleCommentProgress = roundCornerProgressBar3;
        this.recyexpend = recyclerView;
        this.refreshLayout = smartRefreshLayout2;
        this.rvNewest = recyclerView2;
        this.toolbarContinue = rTextView3;
        this.toolbarTitle = textView;
        this.tvAuthor = textView2;
        this.tvCommentNum = textView3;
        this.tvContinue = rTextView4;
        this.tvHigh = rTextView5;
        this.tvLow = rTextView6;
        this.tvMiddle = rTextView7;
        this.tvScore = textView4;
        this.tvTabTitle = textView5;
        this.tvTitle = textView6;
    }

    @NonNull
    public static LayoutBookReviewBinding bind(@NonNull View view) {
        int i2 = R.id.btn_hot;
        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
        if (rTextView != null) {
            i2 = R.id.btn_news;
            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
            if (rTextView2 != null) {
                i2 = R.id.high_comment_progress;
                RoundCornerProgressBar roundCornerProgressBar = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                if (roundCornerProgressBar != null) {
                    i2 = R.id.img_back;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        i2 = R.id.img_cover;
                        RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
                        if (rImageView != null) {
                            i2 = R.id.ll_head;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout != null) {
                                i2 = R.id.low_comment_progress;
                                RoundCornerProgressBar roundCornerProgressBar2 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                if (roundCornerProgressBar2 != null) {
                                    i2 = R.id.ly_tab;
                                    RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                    if (relativeLayout != null) {
                                        i2 = R.id.ly_tab_view;
                                        RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                        if (rLinearLayout != null) {
                                            i2 = R.id.ly_title_view;
                                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                            if (linearLayout2 != null) {
                                                i2 = R.id.middle_comment_progress;
                                                RoundCornerProgressBar roundCornerProgressBar3 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                                if (roundCornerProgressBar3 != null) {
                                                    i2 = R.id.recyexpend;
                                                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                    if (recyclerView != null) {
                                                        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) view;
                                                        i2 = R.id.rv_newest;
                                                        RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                        if (recyclerView2 != null) {
                                                            i2 = R.id.toolbar_continue;
                                                            RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView3 != null) {
                                                                i2 = R.id.toolbar_title;
                                                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView != null) {
                                                                    i2 = R.id.tv_author;
                                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView2 != null) {
                                                                        i2 = R.id.tv_comment_num;
                                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView3 != null) {
                                                                            i2 = R.id.tv_continue;
                                                                            RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (rTextView4 != null) {
                                                                                i2 = R.id.tv_high;
                                                                                RTextView rTextView5 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (rTextView5 != null) {
                                                                                    i2 = R.id.tv_low;
                                                                                    RTextView rTextView6 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (rTextView6 != null) {
                                                                                        i2 = R.id.tv_middle;
                                                                                        RTextView rTextView7 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (rTextView7 != null) {
                                                                                            i2 = R.id.tv_score;
                                                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (textView4 != null) {
                                                                                                i2 = R.id.tv_tab_title;
                                                                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (textView5 != null) {
                                                                                                    i2 = R.id.tv_title;
                                                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (textView6 != null) {
                                                                                                        return new LayoutBookReviewBinding(smartRefreshLayout, rTextView, rTextView2, roundCornerProgressBar, imageView, rImageView, linearLayout, roundCornerProgressBar2, relativeLayout, rLinearLayout, linearLayout2, roundCornerProgressBar3, recyclerView, smartRefreshLayout, recyclerView2, rTextView3, textView, textView2, textView3, rTextView4, rTextView5, rTextView6, rTextView7, textView4, textView5, textView6);
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
    public static LayoutBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_book_review, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public SmartRefreshLayout getRoot() {
        return this.rootView;
    }
}
