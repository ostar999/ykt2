package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RRadioButton;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class PopFlipPageSetBinding implements ViewBinding {

    @NonNull
    public final RRadioButton cbPageFz;

    @NonNull
    public final RRadioButton cbPageSx;

    @NonNull
    public final RRadioButton cbPageZy;

    @NonNull
    public final ImageView ivClose;

    @NonNull
    private final LinearLayout rootView;

    private PopFlipPageSetBinding(@NonNull LinearLayout linearLayout, @NonNull RRadioButton rRadioButton, @NonNull RRadioButton rRadioButton2, @NonNull RRadioButton rRadioButton3, @NonNull ImageView imageView) {
        this.rootView = linearLayout;
        this.cbPageFz = rRadioButton;
        this.cbPageSx = rRadioButton2;
        this.cbPageZy = rRadioButton3;
        this.ivClose = imageView;
    }

    @NonNull
    public static PopFlipPageSetBinding bind(@NonNull View view) {
        int i2 = R.id.cb_page_fz;
        RRadioButton rRadioButton = (RRadioButton) ViewBindings.findChildViewById(view, i2);
        if (rRadioButton != null) {
            i2 = R.id.cb_page_sx;
            RRadioButton rRadioButton2 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
            if (rRadioButton2 != null) {
                i2 = R.id.cb_page_zy;
                RRadioButton rRadioButton3 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                if (rRadioButton3 != null) {
                    i2 = R.id.iv_close;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        return new PopFlipPageSetBinding((LinearLayout) view, rRadioButton, rRadioButton2, rRadioButton3, imageView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static PopFlipPageSetBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopFlipPageSetBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.pop_flip_page_set, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
