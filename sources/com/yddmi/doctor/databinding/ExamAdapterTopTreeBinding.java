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
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ExamAdapterTopTreeBinding implements ViewBinding {

    @NonNull
    public final ImageView aImgv;

    @NonNull
    public final FrameLayout fl;

    @NonNull
    public final ImageView imgv;

    @NonNull
    private final FrameLayout rootView;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25734tv;

    private ExamAdapterTopTreeBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull FrameLayout frameLayout2, @NonNull ImageView imageView2, @NonNull TextView textView) {
        this.rootView = frameLayout;
        this.aImgv = imageView;
        this.fl = frameLayout2;
        this.imgv = imageView2;
        this.f25734tv = textView;
    }

    @NonNull
    public static ExamAdapterTopTreeBinding bind(@NonNull View view) {
        int i2 = R.id.aImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            FrameLayout frameLayout = (FrameLayout) view;
            i2 = R.id.imgv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.f25733tv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    return new ExamAdapterTopTreeBinding(frameLayout, imageView, frameLayout, imageView2, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ExamAdapterTopTreeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ExamAdapterTopTreeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.exam_adapter_top_tree, viewGroup, false);
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
