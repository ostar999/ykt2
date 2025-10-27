package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class FeedbackActivityBinding implements ViewBinding {

    @NonNull
    public final EditText et;

    @NonNull
    public final ImageView imgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView sendTv;

    private FeedbackActivityBinding(@NonNull ConstraintLayout constraintLayout, @NonNull EditText editText, @NonNull ImageView imageView, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.et = editText;
        this.imgv = imageView;
        this.sendTv = textView;
    }

    @NonNull
    public static FeedbackActivityBinding bind(@NonNull View view) {
        int i2 = R.id.et;
        EditText editText = (EditText) ViewBindings.findChildViewById(view, i2);
        if (editText != null) {
            i2 = R.id.imgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.sendTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    return new FeedbackActivityBinding((ConstraintLayout) view, editText, imageView, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static FeedbackActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FeedbackActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.feedback_activity, viewGroup, false);
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
