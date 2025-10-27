package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class LayoutTitleBarBinding implements ViewBinding {

    @NonNull
    public final ImageView backIcon;

    @NonNull
    public final View line;

    @NonNull
    public final ImageView rightFirstDrawable;

    @NonNull
    public final TextView rightFirstText;

    @NonNull
    public final ImageView rightSecondDrawable;

    @NonNull
    public final TextView rightSecondText;

    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final RelativeLayout titleBar;

    @NonNull
    public final TextView titleText;

    private LayoutTitleBarBinding(@NonNull RelativeLayout relativeLayout, @NonNull ImageView imageView, @NonNull View view, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull ImageView imageView3, @NonNull TextView textView2, @NonNull RelativeLayout relativeLayout2, @NonNull TextView textView3) {
        this.rootView = relativeLayout;
        this.backIcon = imageView;
        this.line = view;
        this.rightFirstDrawable = imageView2;
        this.rightFirstText = textView;
        this.rightSecondDrawable = imageView3;
        this.rightSecondText = textView2;
        this.titleBar = relativeLayout2;
        this.titleText = textView3;
    }

    @NonNull
    public static LayoutTitleBarBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.back_icon;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
            i2 = R.id.rightFirstDrawable;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.rightFirstText;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.rightSecondDrawable;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView3 != null) {
                        i2 = R.id.rightSecondText;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView2 != null) {
                            RelativeLayout relativeLayout = (RelativeLayout) view;
                            i2 = R.id.title_text;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                return new LayoutTitleBarBinding(relativeLayout, imageView, viewFindChildViewById, imageView2, textView, imageView3, textView2, relativeLayout, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutTitleBarBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutTitleBarBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_title_bar, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RelativeLayout getRoot() {
        return this.rootView;
    }
}
