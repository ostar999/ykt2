package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ActivityBookInfoBinding implements ViewBinding {

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final RImageView imgCover;

    @NonNull
    public final ImageView ivBookCommentCount;

    @NonNull
    public final ImageView ivBookReviewCount;

    @NonNull
    public final View line1;

    @NonNull
    public final View line2;

    @NonNull
    public final LinearLayout llBookComment;

    @NonNull
    public final LinearLayout llComment;

    @NonNull
    public final LinearLayout llStartPage;

    @NonNull
    public final LinearLayout lyMore;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final Toolbar toolbar;

    @NonNull
    public final RTextView tvAddBook;

    @NonNull
    public final RTextView tvBeginRead;

    @NonNull
    public final TextView tvBookComment;

    @NonNull
    public final TextView tvBookInfo;

    @NonNull
    public final TextView tvCommentNum;

    @NonNull
    public final TextView tvDesc;

    @NonNull
    public final TextView tvGotoComment;

    @NonNull
    public final TextView tvRead;

    @NonNull
    public final TextView tvReadNum;

    @NonNull
    public final TextView tvReviewNum;

    @NonNull
    public final TextView tvSimpleIntroduce;

    @NonNull
    public final TextView tvTitle;

    @NonNull
    public final TextView tvWordNum;

    private ActivityBookInfoBinding(@NonNull LinearLayout linearLayout, @NonNull ImageView imageView, @NonNull RImageView rImageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull View view, @NonNull View view2, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull LinearLayout linearLayout5, @NonNull Toolbar toolbar, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull TextView textView11) {
        this.rootView = linearLayout;
        this.imgBack = imageView;
        this.imgCover = rImageView;
        this.ivBookCommentCount = imageView2;
        this.ivBookReviewCount = imageView3;
        this.line1 = view;
        this.line2 = view2;
        this.llBookComment = linearLayout2;
        this.llComment = linearLayout3;
        this.llStartPage = linearLayout4;
        this.lyMore = linearLayout5;
        this.toolbar = toolbar;
        this.tvAddBook = rTextView;
        this.tvBeginRead = rTextView2;
        this.tvBookComment = textView;
        this.tvBookInfo = textView2;
        this.tvCommentNum = textView3;
        this.tvDesc = textView4;
        this.tvGotoComment = textView5;
        this.tvRead = textView6;
        this.tvReadNum = textView7;
        this.tvReviewNum = textView8;
        this.tvSimpleIntroduce = textView9;
        this.tvTitle = textView10;
        this.tvWordNum = textView11;
    }

    @NonNull
    public static ActivityBookInfoBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        int i2 = R.id.img_back;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.img_cover;
            RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
            if (rImageView != null) {
                i2 = R.id.iv_book_comment_count;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.iv_book_review_count;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView3 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line_1))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.line_2))) != null) {
                        i2 = R.id.ll_book_comment;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            i2 = R.id.ll_comment;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout2 != null) {
                                LinearLayout linearLayout3 = (LinearLayout) view;
                                i2 = R.id.ly_more;
                                LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout4 != null) {
                                    i2 = R.id.toolbar;
                                    Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                                    if (toolbar != null) {
                                        i2 = R.id.tv_add_book;
                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                        if (rTextView != null) {
                                            i2 = R.id.tv_begin_read;
                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                            if (rTextView2 != null) {
                                                i2 = R.id.tv_book_comment;
                                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView != null) {
                                                    i2 = R.id.tv_book_info;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView2 != null) {
                                                        i2 = R.id.tv_comment_num;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.tv_desc;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView4 != null) {
                                                                i2 = R.id.tv_goto_comment;
                                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView5 != null) {
                                                                    i2 = R.id.tv_read;
                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView6 != null) {
                                                                        i2 = R.id.tv_read_num;
                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView7 != null) {
                                                                            i2 = R.id.tv_review_num;
                                                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView8 != null) {
                                                                                i2 = R.id.tv_simple_introduce;
                                                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView9 != null) {
                                                                                    i2 = R.id.tv_title;
                                                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView10 != null) {
                                                                                        i2 = R.id.tv_word_num;
                                                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView11 != null) {
                                                                                            return new ActivityBookInfoBinding(linearLayout3, imageView, rImageView, imageView2, imageView3, viewFindChildViewById, viewFindChildViewById2, linearLayout, linearLayout2, linearLayout3, linearLayout4, toolbar, rTextView, rTextView2, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11);
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
    public static ActivityBookInfoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityBookInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_book_info, viewGroup, false);
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
