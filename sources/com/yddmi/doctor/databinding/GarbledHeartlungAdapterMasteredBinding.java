package com.yddmi.doctor.databinding;

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
import com.catchpig.mvvm.widget.DotView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungAdapterMasteredBinding implements ViewBinding {

    @NonNull
    public final DotView dotV;

    @NonNull
    public final TextView nameTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ImageView vipTag;

    private GarbledHeartlungAdapterMasteredBinding(@NonNull ConstraintLayout constraintLayout, @NonNull DotView dotView, @NonNull TextView textView, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.dotV = dotView;
        this.nameTv = textView;
        this.vipTag = imageView;
    }

    @NonNull
    public static GarbledHeartlungAdapterMasteredBinding bind(@NonNull View view) {
        int i2 = R.id.dotV;
        DotView dotView = (DotView) ViewBindings.findChildViewById(view, i2);
        if (dotView != null) {
            i2 = R.id.nameTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.vipTag;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    return new GarbledHeartlungAdapterMasteredBinding((ConstraintLayout) view, dotView, textView, imageView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungAdapterMasteredBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungAdapterMasteredBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_adapter_mastered, viewGroup, false);
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
