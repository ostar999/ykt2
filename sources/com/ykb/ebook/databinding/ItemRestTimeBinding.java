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
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ItemRestTimeBinding implements ViewBinding {

    @NonNull
    public final TextView cbItem;

    @NonNull
    public final ImageView iv;

    @NonNull
    public final View lineV;

    @NonNull
    private final LinearLayout rootView;

    private ItemRestTimeBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull ImageView imageView, @NonNull View view) {
        this.rootView = linearLayout;
        this.cbItem = textView;
        this.iv = imageView;
        this.lineV = view;
    }

    @NonNull
    public static ItemRestTimeBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.cb_item;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.iv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
                return new ItemRestTimeBinding((LinearLayout) view, textView, imageView, viewFindChildViewById);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ItemRestTimeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemRestTimeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_rest_time, viewGroup, false);
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
