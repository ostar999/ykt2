package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupListAttachBinding implements ViewBinding {

    @NonNull
    public final FrameLayout fl;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RecyclerView rv;

    private PopupListAttachBinding(@NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2, @NonNull RecyclerView recyclerView) {
        this.rootView = frameLayout;
        this.fl = frameLayout2;
        this.rv = recyclerView;
    }

    @NonNull
    public static PopupListAttachBinding bind(@NonNull View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i2 = R.id.rv;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
        if (recyclerView != null) {
            return new PopupListAttachBinding(frameLayout, frameLayout, recyclerView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupListAttachBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupListAttachBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_list_attach, viewGroup, false);
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
