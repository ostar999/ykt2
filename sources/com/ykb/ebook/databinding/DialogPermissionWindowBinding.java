package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogPermissionWindowBinding implements ViewBinding {

    @NonNull
    public final LinearLayout lyView;

    @NonNull
    public final RecyclerView recyclerview;

    @NonNull
    private final LinearLayout rootView;

    private DialogPermissionWindowBinding(@NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull RecyclerView recyclerView) {
        this.rootView = linearLayout;
        this.lyView = linearLayout2;
        this.recyclerview = recyclerView;
    }

    @NonNull
    public static DialogPermissionWindowBinding bind(@NonNull View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        int i2 = R.id.recyclerview;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
        if (recyclerView != null) {
            return new DialogPermissionWindowBinding(linearLayout, linearLayout, recyclerView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static DialogPermissionWindowBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogPermissionWindowBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_permission_window, viewGroup, false);
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
