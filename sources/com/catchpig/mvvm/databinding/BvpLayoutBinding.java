package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class BvpLayoutBinding implements ViewBinding {

    @NonNull
    public final RelativeLayout bvpLayoutIndicator;

    @NonNull
    private final View rootView;

    @NonNull
    public final ViewPager2 vpMain;

    private BvpLayoutBinding(@NonNull View view, @NonNull RelativeLayout relativeLayout, @NonNull ViewPager2 viewPager2) {
        this.rootView = view;
        this.bvpLayoutIndicator = relativeLayout;
        this.vpMain = viewPager2;
    }

    @NonNull
    public static BvpLayoutBinding bind(@NonNull View view) {
        int i2 = R.id.bvp_layout_indicator;
        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
        if (relativeLayout != null) {
            i2 = R.id.vp_main;
            ViewPager2 viewPager2 = (ViewPager2) ViewBindings.findChildViewById(view, i2);
            if (viewPager2 != null) {
                return new BvpLayoutBinding(view, relativeLayout, viewPager2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static BvpLayoutBinding inflate(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        if (viewGroup == null) {
            throw new NullPointerException("parent");
        }
        layoutInflater.inflate(R.layout.bvp_layout, viewGroup);
        return bind(viewGroup);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public View getRoot() {
        return this.rootView;
    }
}
