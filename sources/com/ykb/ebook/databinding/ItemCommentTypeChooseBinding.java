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
public final class ItemCommentTypeChooseBinding implements ViewBinding {

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvContent;

    @NonNull
    public final View vLine;

    private ItemCommentTypeChooseBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull View view) {
        this.rootView = linearLayout;
        this.tvContent = textView;
        this.vLine = view;
    }

    @NonNull
    public static ItemCommentTypeChooseBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.tv_content;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView == null || (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) == null) {
            throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
        }
        return new ItemCommentTypeChooseBinding((LinearLayout) view, textView, viewFindChildViewById);
    }

    @NonNull
    public static ItemCommentTypeChooseBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemCommentTypeChooseBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_comment_type_choose, viewGroup, false);
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
