package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class WidgetNumberKeyboardBinding implements ViewBinding {

    @NonNull
    public final RecyclerView recyclerView2;

    @NonNull
    private final ConstraintLayout rootView;

    private WidgetNumberKeyboardBinding(@NonNull ConstraintLayout constraintLayout, @NonNull RecyclerView recyclerView) {
        this.rootView = constraintLayout;
        this.recyclerView2 = recyclerView;
    }

    @NonNull
    public static WidgetNumberKeyboardBinding bind(@NonNull View view) {
        int i2 = R.id.recyclerView2;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
        if (recyclerView != null) {
            return new WidgetNumberKeyboardBinding((ConstraintLayout) view, recyclerView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static WidgetNumberKeyboardBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static WidgetNumberKeyboardBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.widget_number_keyboard, viewGroup, false);
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
