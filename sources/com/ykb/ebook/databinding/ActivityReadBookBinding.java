package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.ReadMenu;
import com.ykb.ebook.weight.ReadView;

/* loaded from: classes6.dex */
public final class ActivityReadBookBinding implements ViewBinding {

    @NonNull
    public final ImageView cursorLeft;

    @NonNull
    public final ImageView cursorRight;

    @NonNull
    public final View emptyView;

    @NonNull
    public final ReadMenu readMenu;

    @NonNull
    public final ReadView readView;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final View textMenuPosition;

    private ActivityReadBookBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull View view, @NonNull ReadMenu readMenu, @NonNull ReadView readView, @NonNull View view2) {
        this.rootView = frameLayout;
        this.cursorLeft = imageView;
        this.cursorRight = imageView2;
        this.emptyView = view;
        this.readMenu = readMenu;
        this.readView = readView;
        this.textMenuPosition = view2;
    }

    @NonNull
    public static ActivityReadBookBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        int i2 = R.id.cursor_left;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.cursor_right;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.empty_view))) != null) {
                i2 = R.id.read_menu;
                ReadMenu readMenu = (ReadMenu) ViewBindings.findChildViewById(view, i2);
                if (readMenu != null) {
                    i2 = R.id.read_view;
                    ReadView readView = (ReadView) ViewBindings.findChildViewById(view, i2);
                    if (readView != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.text_menu_position))) != null) {
                        return new ActivityReadBookBinding((FrameLayout) view, imageView, imageView2, viewFindChildViewById, readMenu, readView, viewFindChildViewById2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ActivityReadBookBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityReadBookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_read_book, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
