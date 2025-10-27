package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungAdapterBinding implements ViewBinding {

    @NonNull
    public final BLTextView goingTv;

    @NonNull
    public final TextView nameTv;

    @NonNull
    public final TextView numTv;

    @NonNull
    public final ProgressBar progressBar;

    @NonNull
    private final BLConstraintLayout rootView;

    private GarbledHeartlungAdapterBinding(@NonNull BLConstraintLayout bLConstraintLayout, @NonNull BLTextView bLTextView, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ProgressBar progressBar) {
        this.rootView = bLConstraintLayout;
        this.goingTv = bLTextView;
        this.nameTv = textView;
        this.numTv = textView2;
        this.progressBar = progressBar;
    }

    @NonNull
    public static GarbledHeartlungAdapterBinding bind(@NonNull View view) {
        int i2 = R.id.goingTv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null) {
            i2 = R.id.nameTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.numTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.progressBar;
                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i2);
                    if (progressBar != null) {
                        return new GarbledHeartlungAdapterBinding((BLConstraintLayout) view, bLTextView, textView, textView2, progressBar);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungAdapterBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungAdapterBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_adapter, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public BLConstraintLayout getRoot() {
        return this.rootView;
    }
}
