package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.RoundCornerProgressBar;

/* loaded from: classes6.dex */
public final class LayoutBookReviewHeaderviewBinding implements ViewBinding {

    @NonNull
    public final RoundCornerProgressBar highCommentProgress;

    @NonNull
    public final RImageView imgCover;

    @NonNull
    public final ImageView imgLock;

    @NonNull
    public final View line;

    @NonNull
    public final LinearLayout llCommentHint;

    @NonNull
    public final LinearLayout llHead;

    @NonNull
    public final LinearLayout llPj;

    @NonNull
    public final RoundCornerProgressBar lowCommentProgress;

    @NonNull
    public final RoundCornerProgressBar middleCommentProgress;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final View titleLine;

    @NonNull
    public final TextView tvAuthor;

    @NonNull
    public final TextView tvCommentNum;

    @NonNull
    public final RTextView tvContinue;

    @NonNull
    public final RTextView tvHigh;

    @NonNull
    public final TextView tvHighTop;

    @NonNull
    public final RTextView tvLow;

    @NonNull
    public final TextView tvLowTop;

    @NonNull
    public final RTextView tvMiddle;

    @NonNull
    public final TextView tvMiddleTop;

    @NonNull
    public final TextView tvPj;

    @NonNull
    public final TextView tvReadCommentHint;

    @NonNull
    public final TextView tvScore;

    @NonNull
    public final TextView tvScoreHint;

    @NonNull
    public final TextView tvTitle;

    @NonNull
    public final View vLine;

    private LayoutBookReviewHeaderviewBinding(@NonNull LinearLayout linearLayout, @NonNull RoundCornerProgressBar roundCornerProgressBar, @NonNull RImageView rImageView, @NonNull ImageView imageView, @NonNull View view, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull RoundCornerProgressBar roundCornerProgressBar2, @NonNull RoundCornerProgressBar roundCornerProgressBar3, @NonNull View view2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull TextView textView3, @NonNull RTextView rTextView3, @NonNull TextView textView4, @NonNull RTextView rTextView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull View view3) {
        this.rootView = linearLayout;
        this.highCommentProgress = roundCornerProgressBar;
        this.imgCover = rImageView;
        this.imgLock = imageView;
        this.line = view;
        this.llCommentHint = linearLayout2;
        this.llHead = linearLayout3;
        this.llPj = linearLayout4;
        this.lowCommentProgress = roundCornerProgressBar2;
        this.middleCommentProgress = roundCornerProgressBar3;
        this.titleLine = view2;
        this.tvAuthor = textView;
        this.tvCommentNum = textView2;
        this.tvContinue = rTextView;
        this.tvHigh = rTextView2;
        this.tvHighTop = textView3;
        this.tvLow = rTextView3;
        this.tvLowTop = textView4;
        this.tvMiddle = rTextView4;
        this.tvMiddleTop = textView5;
        this.tvPj = textView6;
        this.tvReadCommentHint = textView7;
        this.tvScore = textView8;
        this.tvScoreHint = textView9;
        this.tvTitle = textView10;
        this.vLine = view3;
    }

    @NonNull
    public static LayoutBookReviewHeaderviewBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        int i2 = R.id.high_comment_progress;
        RoundCornerProgressBar roundCornerProgressBar = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
        if (roundCornerProgressBar != null) {
            i2 = R.id.img_cover;
            RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
            if (rImageView != null) {
                i2 = R.id.imgLock;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                    i2 = R.id.ll_comment_hint;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.ll_head;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout2 != null) {
                            i2 = R.id.ll_pj;
                            LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout3 != null) {
                                i2 = R.id.low_comment_progress;
                                RoundCornerProgressBar roundCornerProgressBar2 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                if (roundCornerProgressBar2 != null) {
                                    i2 = R.id.middle_comment_progress;
                                    RoundCornerProgressBar roundCornerProgressBar3 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                    if (roundCornerProgressBar3 != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.title_line))) != null) {
                                        i2 = R.id.tv_author;
                                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView != null) {
                                            i2 = R.id.tv_comment_num;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView2 != null) {
                                                i2 = R.id.tv_continue;
                                                RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                if (rTextView != null) {
                                                    i2 = R.id.tv_high;
                                                    RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (rTextView2 != null) {
                                                        i2 = R.id.tv_high_top;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.tv_low;
                                                            RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView3 != null) {
                                                                i2 = R.id.tv_low_top;
                                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView4 != null) {
                                                                    i2 = R.id.tv_middle;
                                                                    RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (rTextView4 != null) {
                                                                        i2 = R.id.tv_middle_top;
                                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView5 != null) {
                                                                            i2 = R.id.tv_pj;
                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView6 != null) {
                                                                                i2 = R.id.tvReadCommentHint;
                                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView7 != null) {
                                                                                    i2 = R.id.tv_score;
                                                                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView8 != null) {
                                                                                        i2 = R.id.tv_score_hint;
                                                                                        TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView9 != null) {
                                                                                            i2 = R.id.tv_title;
                                                                                            TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (textView10 != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) != null) {
                                                                                                return new LayoutBookReviewHeaderviewBinding((LinearLayout) view, roundCornerProgressBar, rImageView, imageView, viewFindChildViewById, linearLayout, linearLayout2, linearLayout3, roundCornerProgressBar2, roundCornerProgressBar3, viewFindChildViewById2, textView, textView2, rTextView, rTextView2, textView3, rTextView3, textView4, rTextView4, textView5, textView6, textView7, textView8, textView9, textView10, viewFindChildViewById3);
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
    public static LayoutBookReviewHeaderviewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutBookReviewHeaderviewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_book_review_headerview, viewGroup, false);
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
