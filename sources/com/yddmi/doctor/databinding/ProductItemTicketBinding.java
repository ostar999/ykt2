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
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ProductItemTicketBinding implements ViewBinding {

    @NonNull
    public final ConstraintLayout constraintLayout;

    @NonNull
    public final View lineV;

    @NonNull
    public final TextView nameTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView saleTv;

    @NonNull
    public final ImageView selectImgv;

    @NonNull
    public final ConstraintLayout ticketCl;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final TextView tipTv;

    private ProductItemTicketBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ConstraintLayout constraintLayout2, @NonNull View view, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView3, @NonNull TextView textView4) {
        this.rootView = constraintLayout;
        this.constraintLayout = constraintLayout2;
        this.lineV = view;
        this.nameTv = textView;
        this.saleTv = textView2;
        this.selectImgv = imageView;
        this.ticketCl = constraintLayout3;
        this.timeTv = textView3;
        this.tipTv = textView4;
    }

    @NonNull
    public static ProductItemTicketBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.constraintLayout;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (constraintLayout != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
            i2 = R.id.nameTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.saleTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.selectImgv;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        ConstraintLayout constraintLayout2 = (ConstraintLayout) view;
                        i2 = R.id.timeTv;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView3 != null) {
                            i2 = R.id.tipTv;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView4 != null) {
                                return new ProductItemTicketBinding(constraintLayout2, constraintLayout, viewFindChildViewById, textView, textView2, imageView, constraintLayout2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ProductItemTicketBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ProductItemTicketBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.product_item_ticket, viewGroup, false);
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
