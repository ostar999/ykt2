package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MainItemLeftBinding implements ViewBinding {

    @NonNull
    private final FrameLayout rootView;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final BLTextView f25739tv;

    private MainItemLeftBinding(@NonNull FrameLayout frameLayout, @NonNull BLTextView bLTextView) {
        this.rootView = frameLayout;
        this.f25739tv = bLTextView;
    }

    @NonNull
    public static MainItemLeftBinding bind(@NonNull View view) {
        int i2 = R.id.f25733tv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null) {
            return new MainItemLeftBinding((FrameLayout) view, bLTextView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MainItemLeftBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MainItemLeftBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.main_item_left, viewGroup, false);
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
