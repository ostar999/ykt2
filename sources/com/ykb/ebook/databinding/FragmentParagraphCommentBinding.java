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
import com.ruffian.library.widget.RCheckBox;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class FragmentParagraphCommentBinding implements ViewBinding {

    @NonNull
    public final RCheckBox cbSort;

    @NonNull
    public final RecyclerView recyclerView;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    public final SmartRefreshLayout refreshLayout;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvAllDrawLine;

    private FragmentParagraphCommentBinding(@NonNull LinearLayout linearLayout, @NonNull RCheckBox rCheckBox, @NonNull RecyclerView recyclerView, @NonNull ClassicsHeader classicsHeader, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull TextView textView) {
        this.rootView = linearLayout;
        this.cbSort = rCheckBox;
        this.recyclerView = recyclerView;
        this.refreshHeader = classicsHeader;
        this.refreshLayout = smartRefreshLayout;
        this.tvAllDrawLine = textView;
    }

    @NonNull
    public static FragmentParagraphCommentBinding bind(@NonNull View view) {
        int i2 = R.id.cb_sort;
        RCheckBox rCheckBox = (RCheckBox) ViewBindings.findChildViewById(view, i2);
        if (rCheckBox != null) {
            i2 = R.id.recycler_view;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.refresh_header;
                ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                if (classicsHeader != null) {
                    i2 = R.id.refresh_layout;
                    SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                    if (smartRefreshLayout != null) {
                        i2 = R.id.tv_all_draw_line;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            return new FragmentParagraphCommentBinding((LinearLayout) view, rCheckBox, recyclerView, classicsHeader, smartRefreshLayout, textView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static FragmentParagraphCommentBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FragmentParagraphCommentBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_paragraph_comment, viewGroup, false);
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
