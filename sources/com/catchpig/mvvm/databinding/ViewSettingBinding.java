package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class ViewSettingBinding implements ViewBinding {

    @NonNull
    public final ImageView ivLefticon;

    @NonNull
    public final ImageView ivRighticon;

    @NonNull
    public final AppCompatCheckBox rightcheck;

    @NonNull
    public final FrameLayout rightlayout;

    @NonNull
    public final SwitchCompat rightswitch;

    @NonNull
    public final RelativeLayout rootLayout;

    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final TextView tvLefttext;

    @NonNull
    public final TextView tvRighttext;

    @NonNull
    public final View underline;

    private ViewSettingBinding(@NonNull RelativeLayout relativeLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull AppCompatCheckBox appCompatCheckBox, @NonNull FrameLayout frameLayout, @NonNull SwitchCompat switchCompat, @NonNull RelativeLayout relativeLayout2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull View view) {
        this.rootView = relativeLayout;
        this.ivLefticon = imageView;
        this.ivRighticon = imageView2;
        this.rightcheck = appCompatCheckBox;
        this.rightlayout = frameLayout;
        this.rightswitch = switchCompat;
        this.rootLayout = relativeLayout2;
        this.tvLefttext = textView;
        this.tvRighttext = textView2;
        this.underline = view;
    }

    @NonNull
    public static ViewSettingBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.iv_lefticon;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.iv_righticon;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.rightcheck;
                AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) ViewBindings.findChildViewById(view, i2);
                if (appCompatCheckBox != null) {
                    i2 = R.id.rightlayout;
                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                    if (frameLayout != null) {
                        i2 = R.id.rightswitch;
                        SwitchCompat switchCompat = (SwitchCompat) ViewBindings.findChildViewById(view, i2);
                        if (switchCompat != null) {
                            RelativeLayout relativeLayout = (RelativeLayout) view;
                            i2 = R.id.tv_lefttext;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.tv_righttext;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.underline))) != null) {
                                    return new ViewSettingBinding(relativeLayout, imageView, imageView2, appCompatCheckBox, frameLayout, switchCompat, relativeLayout, textView, textView2, viewFindChildViewById);
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
    public static ViewSettingBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ViewSettingBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.view_setting, viewGroup, false);
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
