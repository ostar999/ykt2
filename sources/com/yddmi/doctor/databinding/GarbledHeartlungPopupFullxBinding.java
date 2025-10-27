package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungPopupFullxBinding implements ViewBinding {

    @NonNull
    public final BLTextView callBtv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ImageView xImgv;

    private GarbledHeartlungPopupFullxBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLTextView bLTextView, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.callBtv = bLTextView;
        this.xImgv = imageView;
    }

    @NonNull
    public static GarbledHeartlungPopupFullxBinding bind(@NonNull View view) {
        int i2 = R.id.callBtv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null) {
            i2 = R.id.xImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                return new GarbledHeartlungPopupFullxBinding((ConstraintLayout) view, bLTextView, imageView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungPopupFullxBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungPopupFullxBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_popup_fullx, viewGroup, false);
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
