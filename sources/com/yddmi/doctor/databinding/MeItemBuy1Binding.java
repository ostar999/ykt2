package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MeItemBuy1Binding implements ViewBinding {

    @NonNull
    public final ConstraintLayout contentCl;

    @NonNull
    public final TextView createTimeTv;

    @NonNull
    public final BLTextView detailTv;

    @NonNull
    public final TextView endTimeTv;

    @NonNull
    public final ImageView iconImgv;

    @NonNull
    public final ConstraintLayout introduceCl;

    @NonNull
    public final FrameLayout rootFl;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ConstraintLayout timeCl;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25741tv;

    private MeItemBuy1Binding(@NonNull FrameLayout frameLayout, @NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull FrameLayout frameLayout2, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView3) {
        this.rootView = frameLayout;
        this.contentCl = constraintLayout;
        this.createTimeTv = textView;
        this.detailTv = bLTextView;
        this.endTimeTv = textView2;
        this.iconImgv = imageView;
        this.introduceCl = constraintLayout2;
        this.rootFl = frameLayout2;
        this.timeCl = constraintLayout3;
        this.f25741tv = textView3;
    }

    @NonNull
    public static MeItemBuy1Binding bind(@NonNull View view) {
        int i2 = R.id.contentCl;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (constraintLayout != null) {
            i2 = R.id.createTimeTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.detailTv;
                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                if (bLTextView != null) {
                    i2 = R.id.endTimeTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.iconImgv;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null) {
                            i2 = R.id.introduceCl;
                            ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout2 != null) {
                                FrameLayout frameLayout = (FrameLayout) view;
                                i2 = R.id.timeCl;
                                ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout3 != null) {
                                    i2 = R.id.f25733tv;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView3 != null) {
                                        return new MeItemBuy1Binding(frameLayout, constraintLayout, textView, bLTextView, textView2, imageView, constraintLayout2, frameLayout, constraintLayout3, textView3);
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
    public static MeItemBuy1Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MeItemBuy1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_item_buy1, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
