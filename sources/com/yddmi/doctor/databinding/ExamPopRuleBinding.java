package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class ExamPopRuleBinding implements ViewBinding {

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final ImageView imageView;

    @NonNull
    public final ConstraintLayout popCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ScrollView scrollV;

    @NonNull
    public final TextView tipTv;

    private ExamPopRuleBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull ImageView imageView2, @NonNull ConstraintLayout constraintLayout3, @NonNull ScrollView scrollView, @NonNull TextView textView) {
        this.rootView = constraintLayout;
        this.closeImgv = imageView;
        this.codeCl = constraintLayout2;
        this.imageView = imageView2;
        this.popCl = constraintLayout3;
        this.scrollV = scrollView;
        this.tipTv = textView;
    }

    @NonNull
    public static ExamPopRuleBinding bind(@NonNull View view) {
        int i2 = R.id.closeImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.imageView;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.popCl;
                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                    if (constraintLayout2 != null) {
                        i2 = R.id.scrollV;
                        ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i2);
                        if (scrollView != null) {
                            i2 = R.id.tipTv;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                return new ExamPopRuleBinding((ConstraintLayout) view, imageView, constraintLayout, imageView2, constraintLayout2, scrollView, textView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ExamPopRuleBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ExamPopRuleBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.exam_pop_rule, viewGroup, false);
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
