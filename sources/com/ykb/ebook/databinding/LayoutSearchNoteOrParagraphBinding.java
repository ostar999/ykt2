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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.REditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class LayoutSearchNoteOrParagraphBinding implements ViewBinding {

    @NonNull
    public final TextView btnSearch;

    @NonNull
    public final REditText etInput;

    @NonNull
    public final AppCompatImageView imgBack;

    @NonNull
    public final ImageView imgClean;

    @NonNull
    public final FrameLayout layoutSearch;

    @NonNull
    public final RecyclerView recyclerView;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    public final SmartRefreshLayout refreshLayout;

    @NonNull
    private final LinearLayout rootView;

    private LayoutSearchNoteOrParagraphBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull REditText rEditText, @NonNull AppCompatImageView appCompatImageView, @NonNull ImageView imageView, @NonNull FrameLayout frameLayout, @NonNull RecyclerView recyclerView, @NonNull ClassicsHeader classicsHeader, @NonNull SmartRefreshLayout smartRefreshLayout) {
        this.rootView = linearLayout;
        this.btnSearch = textView;
        this.etInput = rEditText;
        this.imgBack = appCompatImageView;
        this.imgClean = imageView;
        this.layoutSearch = frameLayout;
        this.recyclerView = recyclerView;
        this.refreshHeader = classicsHeader;
        this.refreshLayout = smartRefreshLayout;
    }

    @NonNull
    public static LayoutSearchNoteOrParagraphBinding bind(@NonNull View view) {
        int i2 = R.id.btn_search;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.et_input;
            REditText rEditText = (REditText) ViewBindings.findChildViewById(view, i2);
            if (rEditText != null) {
                i2 = R.id.img_back;
                AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                if (appCompatImageView != null) {
                    i2 = R.id.img_clean;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        i2 = R.id.layoutSearch;
                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                        if (frameLayout != null) {
                            i2 = R.id.recycler_view;
                            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                            if (recyclerView != null) {
                                i2 = R.id.refresh_header;
                                ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                                if (classicsHeader != null) {
                                    i2 = R.id.refresh_layout;
                                    SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                    if (smartRefreshLayout != null) {
                                        return new LayoutSearchNoteOrParagraphBinding((LinearLayout) view, textView, rEditText, appCompatImageView, imageView, frameLayout, recyclerView, classicsHeader, smartRefreshLayout);
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
    public static LayoutSearchNoteOrParagraphBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutSearchNoteOrParagraphBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_search_note_or_paragraph, viewGroup, false);
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
