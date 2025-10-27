package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeMsgBinding implements ViewBinding {

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final TextView desTv;

    @NonNull
    public final BLTextView goTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final TextView subTitleTv;

    @NonNull
    public final TextView titleTv;

    private PopupHomeMsgBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull TextView textView3) {
        this.rootView = frameLayout;
        this.closeImgv = imageView;
        this.desTv = textView;
        this.goTv = bLTextView;
        this.subTitleTv = textView2;
        this.titleTv = textView3;
    }

    @NonNull
    public static PopupHomeMsgBinding bind(@NonNull View view) {
        int i2 = R.id.closeImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.desTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.goTv;
                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                if (bLTextView != null) {
                    i2 = R.id.subTitleTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.titleTv;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView3 != null) {
                            return new PopupHomeMsgBinding((FrameLayout) view, imageView, textView, bLTextView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupHomeMsgBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeMsgBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_msg, viewGroup, false);
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
