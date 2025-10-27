package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupLoginHostBinding implements ViewBinding {

    @NonNull
    public final TextView bodyTv;

    @NonNull
    public final BLTextView devTv;

    @NonNull
    public final TextView filePrivateTv;

    @NonNull
    public final TextView fileTv;

    @NonNull
    public final Flow flow;

    @NonNull
    public final BLTextView formalTv;

    @NonNull
    public final TextView hostChangeTv;

    @NonNull
    public final TextView hostTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final BLTextView saveTv;

    @NonNull
    public final BLTextView test126Tv;

    @NonNull
    public final BLTextView test192Tv;

    @NonNull
    public final BLTextView testTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final BLTextView uatTv;

    @NonNull
    public final ImageView xImgv;

    private PopupLoginHostBinding(@NonNull ConstraintLayout constraintLayout, @NonNull TextView textView, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull Flow flow, @NonNull BLTextView bLTextView2, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull BLTextView bLTextView3, @NonNull BLTextView bLTextView4, @NonNull BLTextView bLTextView5, @NonNull BLTextView bLTextView6, @NonNull TextView textView6, @NonNull BLTextView bLTextView7, @NonNull ImageView imageView) {
        this.rootView = constraintLayout;
        this.bodyTv = textView;
        this.devTv = bLTextView;
        this.filePrivateTv = textView2;
        this.fileTv = textView3;
        this.flow = flow;
        this.formalTv = bLTextView2;
        this.hostChangeTv = textView4;
        this.hostTv = textView5;
        this.saveTv = bLTextView3;
        this.test126Tv = bLTextView4;
        this.test192Tv = bLTextView5;
        this.testTv = bLTextView6;
        this.titleTv = textView6;
        this.uatTv = bLTextView7;
        this.xImgv = imageView;
    }

    @NonNull
    public static PopupLoginHostBinding bind(@NonNull View view) {
        int i2 = R.id.bodyTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.devTv;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                i2 = R.id.filePrivateTv;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    i2 = R.id.fileTv;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView3 != null) {
                        i2 = R.id.flow;
                        Flow flow = (Flow) ViewBindings.findChildViewById(view, i2);
                        if (flow != null) {
                            i2 = R.id.formalTv;
                            BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                            if (bLTextView2 != null) {
                                i2 = R.id.hostChangeTv;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView4 != null) {
                                    i2 = R.id.hostTv;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView5 != null) {
                                        i2 = R.id.saveTv;
                                        BLTextView bLTextView3 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                        if (bLTextView3 != null) {
                                            i2 = R.id.test126Tv;
                                            BLTextView bLTextView4 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                            if (bLTextView4 != null) {
                                                i2 = R.id.test192Tv;
                                                BLTextView bLTextView5 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                if (bLTextView5 != null) {
                                                    i2 = R.id.testTv;
                                                    BLTextView bLTextView6 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (bLTextView6 != null) {
                                                        i2 = R.id.titleTv;
                                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView6 != null) {
                                                            i2 = R.id.uatTv;
                                                            BLTextView bLTextView7 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (bLTextView7 != null) {
                                                                i2 = R.id.xImgv;
                                                                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                if (imageView != null) {
                                                                    return new PopupLoginHostBinding((ConstraintLayout) view, textView, bLTextView, textView2, textView3, flow, bLTextView2, textView4, textView5, bLTextView3, bLTextView4, bLTextView5, bLTextView6, textView6, bLTextView7, imageView);
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
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupLoginHostBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupLoginHostBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_login_host, viewGroup, false);
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
