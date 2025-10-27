package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungActivityBinding implements ViewBinding {

    @NonNull
    public final AppCompatImageView backImgv;

    @NonNull
    public final TextView collectTv;

    @NonNull
    public final View lineV;

    @NonNull
    public final TextView mesteredNoNumTv;

    @NonNull
    public final TextView mesteredNumTv;

    @NonNull
    public final BLConstraintLayout numCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final ConstraintLayout titleBarCl;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final ImageView updownImgv;

    private GarbledHeartlungActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull AppCompatImageView appCompatImageView, @NonNull TextView textView, @NonNull View view, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull RecyclerView recyclerView, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView4, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.backImgv = appCompatImageView;
        this.collectTv = textView;
        this.lineV = view;
        this.mesteredNoNumTv = textView2;
        this.mesteredNumTv = textView3;
        this.numCl = bLConstraintLayout;
        this.rv = recyclerView;
        this.titleBarCl = constraintLayout2;
        this.titleTv = textView4;
        this.updownImgv = imageView;
    }

    @NonNull
    public static GarbledHeartlungActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.backImgv;
        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
        if (appCompatImageView != null) {
            i2 = R.id.collectTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
                i2 = R.id.mesteredNoNumTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.mesteredNumTv;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        i2 = R.id.numCl;
                        BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (bLConstraintLayout != null) {
                            i2 = R.id.rv;
                            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                            if (recyclerView != null) {
                                i2 = R.id.titleBarCl;
                                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout != null) {
                                    i2 = R.id.titleTv;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView4 != null) {
                                        i2 = R.id.updownImgv;
                                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView != null) {
                                            return new GarbledHeartlungActivityBinding((ConstraintLayout) view, appCompatImageView, textView, viewFindChildViewById, textView2, textView3, bLConstraintLayout, recyclerView, constraintLayout, textView4, imageView);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_activity, viewGroup, false);
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
