package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MeItemHistoryDetailBinding implements ViewBinding {

    @NonNull
    public final ImageView lineImgv;

    @NonNull
    private final BLConstraintLayout rootView;

    @NonNull
    public final BLTextView scoreTv;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25742tv;

    private MeItemHistoryDetailBinding(@NonNull BLConstraintLayout bLConstraintLayout, @NonNull ImageView imageView, @NonNull BLTextView bLTextView, @NonNull TextView textView) {
        this.rootView = bLConstraintLayout;
        this.lineImgv = imageView;
        this.scoreTv = bLTextView;
        this.f25742tv = textView;
    }

    @NonNull
    public static MeItemHistoryDetailBinding bind(@NonNull View view) {
        int i2 = R.id.lineImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.scoreTv;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                i2 = R.id.f25733tv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    return new MeItemHistoryDetailBinding((BLConstraintLayout) view, imageView, bLTextView, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static MeItemHistoryDetailBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MeItemHistoryDetailBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_item_history_detail, viewGroup, false);
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
