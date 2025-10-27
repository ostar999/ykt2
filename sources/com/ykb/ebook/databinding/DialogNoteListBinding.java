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
import com.ruffian.library.widget.RRelativeLayout;
import com.ruffian.library.widget.RTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogNoteListBinding implements ViewBinding {

    @NonNull
    public final ImageView imgClose;

    @NonNull
    public final RImageView imgHead;

    @NonNull
    public final ImageView ivAddNote;

    @NonNull
    public final LinearLayout layoutAddNote;

    @NonNull
    public final View line;

    @NonNull
    public final LinearLayout llMore;

    @NonNull
    public final TextView moreNoteContent;

    @NonNull
    public final RecyclerView recycler;

    @NonNull
    public final SmartRefreshLayout refresh;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    public final RRelativeLayout relayoutNoteContent;

    @NonNull
    public final RLinearLayout relayoutNoteRoot;

    @NonNull
    private final RLinearLayout rootView;

    @NonNull
    public final TextView tvAddNote;

    @NonNull
    public final TextView tvContent;

    @NonNull
    public final RTextView tvCopy;

    @NonNull
    public final RTextView tvDrawLine;

    @NonNull
    public final TextView tvNoteTitle;

    @NonNull
    public final RTextView tvShare;

    @NonNull
    public final View viewContentLine;

    @NonNull
    public final View viewLine;

    private DialogNoteListBinding(@NonNull RLinearLayout rLinearLayout, @NonNull ImageView imageView, @NonNull RImageView rImageView, @NonNull ImageView imageView2, @NonNull LinearLayout linearLayout, @NonNull View view, @NonNull LinearLayout linearLayout2, @NonNull TextView textView, @NonNull RecyclerView recyclerView, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull ClassicsHeader classicsHeader, @NonNull RRelativeLayout rRelativeLayout, @NonNull RLinearLayout rLinearLayout2, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull TextView textView4, @NonNull RTextView rTextView3, @NonNull View view2, @NonNull View view3) {
        this.rootView = rLinearLayout;
        this.imgClose = imageView;
        this.imgHead = rImageView;
        this.ivAddNote = imageView2;
        this.layoutAddNote = linearLayout;
        this.line = view;
        this.llMore = linearLayout2;
        this.moreNoteContent = textView;
        this.recycler = recyclerView;
        this.refresh = smartRefreshLayout;
        this.refreshHeader = classicsHeader;
        this.relayoutNoteContent = rRelativeLayout;
        this.relayoutNoteRoot = rLinearLayout2;
        this.tvAddNote = textView2;
        this.tvContent = textView3;
        this.tvCopy = rTextView;
        this.tvDrawLine = rTextView2;
        this.tvNoteTitle = textView4;
        this.tvShare = rTextView3;
        this.viewContentLine = view2;
        this.viewLine = view3;
    }

    @NonNull
    public static DialogNoteListBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        int i2 = R.id.img_close;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.img_head;
            RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
            if (rImageView != null) {
                i2 = R.id.iv_add_note;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.layoutAddNote;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                        i2 = R.id.ll_more;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout2 != null) {
                            i2 = R.id.moreNoteContent;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.recycler;
                                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                if (recyclerView != null) {
                                    i2 = R.id.refresh;
                                    SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                    if (smartRefreshLayout != null) {
                                        i2 = R.id.refresh_header;
                                        ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                                        if (classicsHeader != null) {
                                            i2 = R.id.relayoutNoteContent;
                                            RRelativeLayout rRelativeLayout = (RRelativeLayout) ViewBindings.findChildViewById(view, i2);
                                            if (rRelativeLayout != null) {
                                                RLinearLayout rLinearLayout = (RLinearLayout) view;
                                                i2 = R.id.tv_add_note;
                                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView2 != null) {
                                                    i2 = R.id.tv_content;
                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView3 != null) {
                                                        i2 = R.id.tv_copy;
                                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView != null) {
                                                            i2 = R.id.tv_draw_line;
                                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView2 != null) {
                                                                i2 = R.id.tvNoteTitle;
                                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView4 != null) {
                                                                    i2 = R.id.tv_share;
                                                                    RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (rTextView3 != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.viewContentLine))) != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.viewLine))) != null) {
                                                                        return new DialogNoteListBinding(rLinearLayout, imageView, rImageView, imageView2, linearLayout, viewFindChildViewById, linearLayout2, textView, recyclerView, smartRefreshLayout, classicsHeader, rRelativeLayout, rLinearLayout, textView2, textView3, rTextView, rTextView2, textView4, rTextView3, viewFindChildViewById2, viewFindChildViewById3);
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
    public static DialogNoteListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogNoteListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_note_list, viewGroup, false);
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
