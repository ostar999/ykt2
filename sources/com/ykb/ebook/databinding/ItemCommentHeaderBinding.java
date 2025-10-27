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
public final class ItemCommentHeaderBinding implements ViewBinding {

    @NonNull
    public final LinearLayout llFloat;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvTypeTextFloat;

    @NonNull
    public final View typeLine;

    private ItemCommentHeaderBinding(@NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull TextView textView, @NonNull View view) {
        this.rootView = linearLayout;
        this.llFloat = linearLayout2;
        this.tvTypeTextFloat = textView;
        this.typeLine = view;
    }

    @NonNull
    public static ItemCommentHeaderBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        LinearLayout linearLayout = (LinearLayout) view;
        int i2 = R.id.tv_type_text_float;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView == null || (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.type_line))) == null) {
            throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
        }
        return new ItemCommentHeaderBinding(linearLayout, linearLayout, textView, viewFindChildViewById);
    }

    @NonNull
    public static ItemCommentHeaderBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemCommentHeaderBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_comment_header, viewGroup, false);
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
