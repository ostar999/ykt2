package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ItemChapterSearchSubBinding implements ViewBinding {

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvTitle;

    @NonNull
    public final View viewLine;

    private ItemChapterSearchSubBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull View view) {
        this.rootView = linearLayout;
        this.tvTitle = textView;
        this.viewLine = view;
    }

    @NonNull
    public static ItemChapterSearchSubBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.tv_title;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView == null || (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.viewLine))) == null) {
            throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
        }
        return new ItemChapterSearchSubBinding((LinearLayout) view, textView, viewFindChildViewById);
    }

    @NonNull
    public static ItemChapterSearchSubBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemChapterSearchSubBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_chapter_search_sub, viewGroup, false);
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
