package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ProductPopupTicketBinding implements ViewBinding {

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final BLTextView sureBtv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final ImageView xImgv;

    private ProductPopupTicketBinding(@NonNull ConstraintLayout constraintLayout, @NonNull RecyclerView recyclerView, @NonNull BLTextView bLTextView, @NonNull TextView textView, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.rv = recyclerView;
        this.sureBtv = bLTextView;
        this.titleTv = textView;
        this.xImgv = imageView;
    }

    @NonNull
    public static ProductPopupTicketBinding bind(@NonNull View view) {
        int i2 = R.id.rv;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
        if (recyclerView != null) {
            i2 = R.id.sureBtv;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                i2 = R.id.titleTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.xImgv;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        return new ProductPopupTicketBinding((ConstraintLayout) view, recyclerView, bLTextView, textView, imageView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ProductPopupTicketBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ProductPopupTicketBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.product_popup_ticket, viewGroup, false);
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
