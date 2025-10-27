package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class PopQuestionCommentMenuBottomBookBinding implements ViewBinding {

    @NonNull
    public final RLinearLayout ivTopIndicator;

    @NonNull
    public final View line;

    @NonNull
    public final RLinearLayout llRoot;

    @NonNull
    public final RecyclerView recyclerView;

    @NonNull
    public final TextView reportBot;

    @NonNull
    public final LinearLayout reportLl;

    @NonNull
    public final TextView reportTop;

    @NonNull
    private final RLinearLayout rootView;

    @NonNull
    public final RTextView tvCommentCancel;

    @NonNull
    public final TextView tvCommentContent;

    private PopQuestionCommentMenuBottomBookBinding(@NonNull RLinearLayout rLinearLayout, @NonNull RLinearLayout rLinearLayout2, @NonNull View view, @NonNull RLinearLayout rLinearLayout3, @NonNull RecyclerView recyclerView, @NonNull TextView textView, @NonNull LinearLayout linearLayout, @NonNull TextView textView2, @NonNull RTextView rTextView, @NonNull TextView textView3) {
        this.rootView = rLinearLayout;
        this.ivTopIndicator = rLinearLayout2;
        this.line = view;
        this.llRoot = rLinearLayout3;
        this.recyclerView = recyclerView;
        this.reportBot = textView;
        this.reportLl = linearLayout;
        this.reportTop = textView2;
        this.tvCommentCancel = rTextView;
        this.tvCommentContent = textView3;
    }

    @NonNull
    public static PopQuestionCommentMenuBottomBookBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.iv_top_indicator;
        RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
        if (rLinearLayout != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
            RLinearLayout rLinearLayout2 = (RLinearLayout) view;
            i2 = R.id.recyclerView;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.reportBot;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.reportLl;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.reportTop;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            i2 = R.id.tv_comment_cancel;
                            RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                            if (rTextView != null) {
                                i2 = R.id.tv_comment_content;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView3 != null) {
                                    return new PopQuestionCommentMenuBottomBookBinding(rLinearLayout2, rLinearLayout, viewFindChildViewById, rLinearLayout2, recyclerView, textView, linearLayout, textView2, rTextView, textView3);
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
    public static PopQuestionCommentMenuBottomBookBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopQuestionCommentMenuBottomBookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.pop_question_comment_menu_bottom_book, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RLinearLayout getRoot() {
        return this.rootView;
    }
}
