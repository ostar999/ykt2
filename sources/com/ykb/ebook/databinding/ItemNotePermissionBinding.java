package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ItemNotePermissionBinding implements ViewBinding {

    @NonNull
    public final ImageView iv;

    @NonNull
    public final ImageView ivIcon;

    @NonNull
    public final View line;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView tvPermission;

    @NonNull
    public final TextView tvTip;

    private ItemNotePermissionBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull View view, @NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = constraintLayout;
        this.iv = imageView;
        this.ivIcon = imageView2;
        this.line = view;
        this.tvPermission = textView;
        this.tvTip = textView2;
    }

    @NonNull
    public static ItemNotePermissionBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.iv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.iv_icon;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                i2 = R.id.tv_permission;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.tv_tip;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        return new ItemNotePermissionBinding((ConstraintLayout) view, imageView, imageView2, viewFindChildViewById, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ItemNotePermissionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemNotePermissionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_note_permission, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
