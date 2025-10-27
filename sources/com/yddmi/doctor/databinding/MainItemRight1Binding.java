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
import com.kaelli.niceratingbar.NiceRatingBar;
import com.noober.background.view.BLTextView;
import com.noober.background.view.BLView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MainItemRight1Binding implements ViewBinding {

    @NonNull
    public final ImageView bgImgv;

    @NonNull
    public final ConstraintLayout contentCl;

    @NonNull
    public final BLTextView detailTv;

    @NonNull
    public final ImageView iconImgv;

    @NonNull
    public final ConstraintLayout introduceCl;

    @NonNull
    public final ConstraintLayout lockCl;

    @NonNull
    public final ImageView lockImgv;

    @NonNull
    public final ConstraintLayout rootCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout shareCl;

    @NonNull
    public final ImageView shareImgv;

    @NonNull
    public final NiceRatingBar starBar;

    @NonNull
    public final ImageView tryImgv;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25740tv;

    @NonNull
    public final TextView tvUnlockTitle;

    @NonNull
    public final BLView unlockView;

    private MainItemRight1Binding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull BLTextView bLTextView, @NonNull ImageView imageView2, @NonNull ConstraintLayout constraintLayout3, @NonNull ConstraintLayout constraintLayout4, @NonNull ImageView imageView3, @NonNull ConstraintLayout constraintLayout5, @NonNull ConstraintLayout constraintLayout6, @NonNull ImageView imageView4, @NonNull NiceRatingBar niceRatingBar, @NonNull ImageView imageView5, @NonNull TextView textView, @NonNull TextView textView2, @NonNull BLView bLView) {
        this.rootView = constraintLayout;
        this.bgImgv = imageView;
        this.contentCl = constraintLayout2;
        this.detailTv = bLTextView;
        this.iconImgv = imageView2;
        this.introduceCl = constraintLayout3;
        this.lockCl = constraintLayout4;
        this.lockImgv = imageView3;
        this.rootCl = constraintLayout5;
        this.shareCl = constraintLayout6;
        this.shareImgv = imageView4;
        this.starBar = niceRatingBar;
        this.tryImgv = imageView5;
        this.f25740tv = textView;
        this.tvUnlockTitle = textView2;
        this.unlockView = bLView;
    }

    @NonNull
    public static MainItemRight1Binding bind(@NonNull View view) {
        int i2 = R.id.bgImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.contentCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.detailTv;
                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                if (bLTextView != null) {
                    i2 = R.id.iconImgv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.introduceCl;
                        ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (constraintLayout2 != null) {
                            i2 = R.id.lockCl;
                            ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout3 != null) {
                                i2 = R.id.lockImgv;
                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView3 != null) {
                                    ConstraintLayout constraintLayout4 = (ConstraintLayout) view;
                                    i2 = R.id.shareCl;
                                    ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout5 != null) {
                                        i2 = R.id.shareImgv;
                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView4 != null) {
                                            i2 = R.id.starBar;
                                            NiceRatingBar niceRatingBar = (NiceRatingBar) ViewBindings.findChildViewById(view, i2);
                                            if (niceRatingBar != null) {
                                                i2 = R.id.tryImgv;
                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView5 != null) {
                                                    i2 = R.id.f25733tv;
                                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView != null) {
                                                        i2 = R.id.tv_unlock_title;
                                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView2 != null) {
                                                            i2 = R.id.unlockView;
                                                            BLView bLView = (BLView) ViewBindings.findChildViewById(view, i2);
                                                            if (bLView != null) {
                                                                return new MainItemRight1Binding(constraintLayout4, imageView, constraintLayout, bLTextView, imageView2, constraintLayout2, constraintLayout3, imageView3, constraintLayout4, constraintLayout5, imageView4, niceRatingBar, imageView5, textView, textView2, bLView);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
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
    public static MainItemRight1Binding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MainItemRight1Binding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.main_item_right1, viewGroup, false);
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
