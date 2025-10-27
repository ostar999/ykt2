package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RLinearLayout;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogNotePermissionNewBinding implements ViewBinding {

    @NonNull
    public final ImageView imgClose;

    @NonNull
    public final RLinearLayout layoutRoot;

    @NonNull
    public final View line;

    @NonNull
    private final RLinearLayout rootView;

    @NonNull
    public final RecyclerView rvPermissions;

    @NonNull
    public final TextView tvTitle;

    private DialogNotePermissionNewBinding(@NonNull RLinearLayout rLinearLayout, @NonNull ImageView imageView, @NonNull RLinearLayout rLinearLayout2, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull TextView textView) {
        this.rootView = rLinearLayout;
        this.imgClose = imageView;
        this.layoutRoot = rLinearLayout2;
        this.line = view;
        this.rvPermissions = recyclerView;
        this.tvTitle = textView;
    }

    @NonNull
    public static DialogNotePermissionNewBinding bind(@NonNull View view) {
        int i2 = R.id.img_close;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            RLinearLayout rLinearLayout = (RLinearLayout) view;
            i2 = R.id.line;
            View viewFindChildViewById = ViewBindings.findChildViewById(view, i2);
            if (viewFindChildViewById != null) {
                i2 = R.id.rvPermissions;
                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                if (recyclerView != null) {
                    i2 = R.id.tv_title;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        return new DialogNotePermissionNewBinding(rLinearLayout, imageView, rLinearLayout, viewFindChildViewById, recyclerView, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static DialogNotePermissionNewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogNotePermissionNewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_note_permission_new, viewGroup, false);
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
