package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungActivityCollectBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final ConstraintLayout titleBarCl;

    @NonNull
    public final TextView titleTv;

    private GarbledHeartlungActivityCollectBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull RecyclerView recyclerView, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.rv = recyclerView;
        this.titleBarCl = constraintLayout2;
        this.titleTv = textView;
    }

    @NonNull
    public static GarbledHeartlungActivityCollectBinding bind(@NonNull View view) {
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.rv;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.titleBarCl;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout != null) {
                    i2 = R.id.titleTv;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        return new GarbledHeartlungActivityCollectBinding((ConstraintLayout) view, appCompatImageView, recyclerView, constraintLayout, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungActivityCollectBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungActivityCollectBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_activity_collect, viewGroup, false);
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
