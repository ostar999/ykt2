package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLTextView;
import com.noober.background.view.BLView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungPopupCorrectionBinding implements ViewBinding {

    @NonNull
    public final BLTextView answerTv;

    @NonNull
    public final BLEditText et;

    @NonNull
    public final TextView historyTv;

    @NonNull
    public final BLTextView questionTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final BLTextView submitTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final BLView topV;

    private GarbledHeartlungPopupCorrectionBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLTextView bLTextView, @NonNull BLEditText bLEditText, @NonNull TextView textView, @NonNull BLTextView bLTextView2, @NonNull BLTextView bLTextView3, @NonNull TextView textView2, @NonNull BLView bLView) {
        this.rootView = constraintLayout;
        this.answerTv = bLTextView;
        this.et = bLEditText;
        this.historyTv = textView;
        this.questionTv = bLTextView2;
        this.submitTv = bLTextView3;
        this.titleTv = textView2;
        this.topV = bLView;
    }

    @NonNull
    public static GarbledHeartlungPopupCorrectionBinding bind(@NonNull View view) {
        int i2 = R.id.answerTv;
        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
        if (bLTextView != null) {
            i2 = R.id.et;
            BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
            if (bLEditText != null) {
                i2 = R.id.historyTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.questionTv;
                    BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                    if (bLTextView2 != null) {
                        i2 = R.id.submitTv;
                        BLTextView bLTextView3 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                        if (bLTextView3 != null) {
                            i2 = R.id.titleTv;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView2 != null) {
                                i2 = R.id.topV;
                                BLView bLView = (BLView) ViewBindings.findChildViewById(view, i2);
                                if (bLView != null) {
                                    return new GarbledHeartlungPopupCorrectionBinding((ConstraintLayout) view, bLTextView, bLEditText, textView, bLTextView2, bLTextView3, textView2, bLView);
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
    public static GarbledHeartlungPopupCorrectionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungPopupCorrectionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_popup_correction, viewGroup, false);
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
