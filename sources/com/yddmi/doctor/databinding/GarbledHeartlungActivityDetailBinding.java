package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungActivityDetailBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout titleBarCl;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final ViewPager2 vp;

    private GarbledHeartlungActivityDetailBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView, @NonNull ViewPager2 viewPager2) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.titleBarCl = constraintLayout2;
        this.titleTv = textView;
        this.vp = viewPager2;
    }

    @NonNull
    public static GarbledHeartlungActivityDetailBinding bind(@NonNull View view) {
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.titleBarCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.titleTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.vp;
                    ViewPager2 viewPager2 = (ViewPager2) ViewBindings.findChildViewById(view, i2);
                    if (viewPager2 != null) {
                        return new GarbledHeartlungActivityDetailBinding((ConstraintLayout) view, appCompatImageView, constraintLayout, textView, viewPager2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungActivityDetailBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungActivityDetailBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_activity_detail, viewGroup, false);
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
