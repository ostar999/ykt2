package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogReviewCommentBinding implements ViewBinding {

    @NonNull
    public final TextView etComment;

    @NonNull
    public final ImageView imgClose;

    @NonNull
    public final RImageView imgHead;

    @NonNull
    public final ImageView ivAddComment;

    @NonNull
    public final View line;

    @NonNull
    public final LinearLayout llComment;

    @NonNull
    public final LinearLayout llCommentLocate;

    @NonNull
    public final RLinearLayout llCommentWrap;

    @NonNull
    public final LinearLayout lyView;

    @NonNull
    public final RecyclerView recycler;

    @NonNull
    public final SmartRefreshLayout refresh;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final RTextView tvHot;

    @NonNull
    public final RTextView tvNew;

    @NonNull
    public final TextView tvTitle;

    private DialogReviewCommentBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull ImageView imageView, @NonNull RImageView rImageView, @NonNull ImageView imageView2, @NonNull View view, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull RLinearLayout rLinearLayout, @NonNull LinearLayout linearLayout4, @NonNull RecyclerView recyclerView, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull ClassicsHeader classicsHeader, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull TextView textView2) {
        this.rootView = linearLayout;
        this.etComment = textView;
        this.imgClose = imageView;
        this.imgHead = rImageView;
        this.ivAddComment = imageView2;
        this.line = view;
        this.llComment = linearLayout2;
        this.llCommentLocate = linearLayout3;
        this.llCommentWrap = rLinearLayout;
        this.lyView = linearLayout4;
        this.recycler = recyclerView;
        this.refresh = smartRefreshLayout;
        this.refreshHeader = classicsHeader;
        this.tvHot = rTextView;
        this.tvNew = rTextView2;
        this.tvTitle = textView2;
    }

    @NonNull
    public static DialogReviewCommentBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.et_comment;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.img_close;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.img_head;
                RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
                if (rImageView != null) {
                    i2 = R.id.iv_add_comment;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                        i2 = R.id.ll_comment;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            i2 = R.id.ll_comment_locate;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout2 != null) {
                                i2 = R.id.ll_comment_wrap;
                                RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (rLinearLayout != null) {
                                    LinearLayout linearLayout3 = (LinearLayout) view;
                                    i2 = R.id.recycler;
                                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                    if (recyclerView != null) {
                                        i2 = R.id.refresh;
                                        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                        if (smartRefreshLayout != null) {
                                            i2 = R.id.refresh_header;
                                            ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                                            if (classicsHeader != null) {
                                                i2 = R.id.tv_hot;
                                                RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                if (rTextView != null) {
                                                    i2 = R.id.tv_new;
                                                    RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (rTextView2 != null) {
                                                        i2 = R.id.tv_title;
                                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView2 != null) {
                                                            return new DialogReviewCommentBinding(linearLayout3, textView, imageView, rImageView, imageView2, viewFindChildViewById, linearLayout, linearLayout2, rLinearLayout, linearLayout3, recyclerView, smartRefreshLayout, classicsHeader, rTextView, rTextView2, textView2);
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
    public static DialogReviewCommentBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogReviewCommentBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_review_comment, viewGroup, false);
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
