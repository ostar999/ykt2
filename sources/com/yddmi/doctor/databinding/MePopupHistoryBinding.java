package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class MePopupHistoryBinding implements ViewBinding {

    @NonNull
    public final TextView closeTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final TextView scoreTv;

    @NonNull
    public final TextView timeTipTv;

    @NonNull
    public final BLTextView timeTv;

    @NonNull
    public final ImageView tipImgv;

    @NonNull
    public final TextView tipTv;

    private MePopupHistoryBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull RecyclerView recyclerView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull BLTextView bLTextView, @NonNull ImageView imageView, @NonNull TextView textView4) {
        this.rootView = frameLayout;
        this.closeTv = textView;
        this.rv = recyclerView;
        this.scoreTv = textView2;
        this.timeTipTv = textView3;
        this.timeTv = bLTextView;
        this.tipImgv = imageView;
        this.tipTv = textView4;
    }

    @NonNull
    public static MePopupHistoryBinding bind(@NonNull View view) {
        int i2 = R.id.closeTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.rv;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.scoreTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.timeTipTv;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        i2 = R.id.timeTv;
                        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                        if (bLTextView != null) {
                            i2 = R.id.tipImgv;
                            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView != null) {
                                i2 = R.id.tipTv;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView4 != null) {
                                    return new MePopupHistoryBinding((FrameLayout) view, textView, recyclerView, textView2, textView3, bLTextView, imageView, textView4);
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
    public static MePopupHistoryBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static MePopupHistoryBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.me_popup_history, viewGroup, false);
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
