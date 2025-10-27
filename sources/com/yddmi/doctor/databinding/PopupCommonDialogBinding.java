package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupCommonDialogBinding implements ViewBinding {

    @NonNull
    public final TextView detailTv;

    @NonNull
    public final BLTextView oneTv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final BLTextView singleTv;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final Group twoCvGrop;

    @NonNull
    public final BLTextView twoTv;

    private PopupCommonDialogBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull BLTextView bLTextView, @NonNull BLTextView bLTextView2, @NonNull TextView textView2, @NonNull Group group, @NonNull BLTextView bLTextView3) {
        this.rootView = frameLayout;
        this.detailTv = textView;
        this.oneTv = bLTextView;
        this.singleTv = bLTextView2;
        this.titleTv = textView2;
        this.twoCvGrop = group;
        this.twoTv = bLTextView3;
    }

    @NonNull
    public static PopupCommonDialogBinding bind(@NonNull View view) {
        int i2 = R.id.detailTv;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.oneTv;
            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
            if (bLTextView != null) {
                i2 = R.id.singleTv;
                BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                if (bLTextView2 != null) {
                    i2 = R.id.titleTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.twoCvGrop;
                        Group group = (Group) ViewBindings.findChildViewById(view, i2);
                        if (group != null) {
                            i2 = R.id.twoTv;
                            BLTextView bLTextView3 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                            if (bLTextView3 != null) {
                                return new PopupCommonDialogBinding((FrameLayout) view, textView, bLTextView, bLTextView2, textView2, group, bLTextView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopupCommonDialogBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupCommonDialogBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_common_dialog, viewGroup, false);
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
