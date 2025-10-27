package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class ViewRootBinding implements ViewBinding {

    @NonNull
    public final FrameLayout layoutBody;

    @NonNull
    public final ConstraintLayout layoutRoot;

    @NonNull
    public final ViewStub loadingViewStub;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ViewStub titleBarViewStub;

    private ViewRootBinding(@NonNull ConstraintLayout constraintLayout, @NonNull FrameLayout frameLayout, @NonNull ConstraintLayout constraintLayout2, @NonNull ViewStub viewStub, @NonNull ViewStub viewStub2) {
        this.rootView = constraintLayout;
        this.layoutBody = frameLayout;
        this.layoutRoot = constraintLayout2;
        this.loadingViewStub = viewStub;
        this.titleBarViewStub = viewStub2;
    }

    @NonNull
    public static ViewRootBinding bind(@NonNull View view) {
        int i2 = R.id.layout_body;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
        if (frameLayout != null) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            i2 = R.id.loading_view_stub;
            ViewStub viewStub = (ViewStub) ViewBindings.findChildViewById(view, i2);
            if (viewStub != null) {
                i2 = R.id.title_bar_view_stub;
                ViewStub viewStub2 = (ViewStub) ViewBindings.findChildViewById(view, i2);
                if (viewStub2 != null) {
                    return new ViewRootBinding(constraintLayout, frameLayout, constraintLayout, viewStub, viewStub2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ViewRootBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ViewRootBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.view_root, viewGroup, false);
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
