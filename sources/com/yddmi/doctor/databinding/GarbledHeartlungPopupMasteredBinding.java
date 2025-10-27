package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.widget.DotView;
import com.noober.background.view.BLView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungPopupMasteredBinding implements ViewBinding {

    @NonNull
    public final DotView dotBlue;

    @NonNull
    public final DotView dotGray;

    @NonNull
    public final TextView masteredNoTv;

    @NonNull
    public final TextView masteredTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final BLView topV;

    private GarbledHeartlungPopupMasteredBinding(@NonNull ConstraintLayout constraintLayout, @NonNull DotView dotView, @NonNull DotView dotView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull RecyclerView recyclerView, @NonNull BLView bLView) {
        this.rootView = constraintLayout;
        this.dotBlue = dotView;
        this.dotGray = dotView2;
        this.masteredNoTv = textView;
        this.masteredTv = textView2;
        this.rv = recyclerView;
        this.topV = bLView;
    }

    @NonNull
    public static GarbledHeartlungPopupMasteredBinding bind(@NonNull View view) {
        int i2 = R.id.dotBlue;
        DotView dotView = (DotView) ViewBindings.findChildViewById(view, i2);
        if (dotView != null) {
            i2 = R.id.dotGray;
            DotView dotView2 = (DotView) ViewBindings.findChildViewById(view, i2);
            if (dotView2 != null) {
                i2 = R.id.masteredNoTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.masteredTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.rv;
                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                        if (recyclerView != null) {
                            i2 = R.id.topV;
                            BLView bLView = (BLView) ViewBindings.findChildViewById(view, i2);
                            if (bLView != null) {
                                return new GarbledHeartlungPopupMasteredBinding((ConstraintLayout) view, dotView, dotView2, textView, textView2, recyclerView, bLView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static GarbledHeartlungPopupMasteredBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungPopupMasteredBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_popup_mastered, viewGroup, false);
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
