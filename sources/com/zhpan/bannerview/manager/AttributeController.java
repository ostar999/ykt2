package com.zhpan.bannerview.manager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.catchpig.mvvm.R;
import com.zhpan.bannerview.utils.BannerUtils;

/* loaded from: classes8.dex */
public class AttributeController {
    private final BannerOptions mBannerOptions;

    public AttributeController(BannerOptions bannerOptions) {
        this.mBannerOptions = bannerOptions;
    }

    private void initBannerAttrs(TypedArray typedArray) {
        int integer = typedArray.getInteger(R.styleable.BannerViewPager_bvp_interval, 3000);
        boolean z2 = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_auto_play, true);
        boolean z3 = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_can_loop, true);
        int dimension = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_page_margin, 0.0f);
        int dimension2 = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_round_corner, 0.0f);
        int dimension3 = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_reveal_width, -1000.0f);
        int i2 = typedArray.getInt(R.styleable.BannerViewPager_bvp_page_style, 0);
        int i3 = typedArray.getInt(R.styleable.BannerViewPager_bvp_scroll_duration, 0);
        this.mBannerOptions.setInterval(integer);
        this.mBannerOptions.setAutoPlay(z2);
        this.mBannerOptions.setCanLoop(z3);
        this.mBannerOptions.setPageMargin(dimension);
        this.mBannerOptions.setRoundRectRadius(dimension2);
        this.mBannerOptions.setRightRevealWidth(dimension3);
        this.mBannerOptions.setLeftRevealWidth(dimension3);
        this.mBannerOptions.setPageStyle(i2);
        this.mBannerOptions.setScrollDuration(i3);
    }

    private void initIndicatorAttrs(TypedArray typedArray) {
        int color = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_checked_color, Color.parseColor("#8C18171C"));
        int color2 = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_normal_color, Color.parseColor("#8C6C6D72"));
        int dimension = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_indicator_radius, BannerUtils.dp2px(8.0f));
        int i2 = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_gravity, 0);
        int i3 = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_style, 0);
        int i4 = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_slide_mode, 0);
        int i5 = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_visibility, 0);
        this.mBannerOptions.setIndicatorSliderColor(color2, color);
        this.mBannerOptions.setIndicatorSliderWidth(dimension, dimension);
        this.mBannerOptions.setIndicatorGravity(i2);
        this.mBannerOptions.setIndicatorStyle(i3);
        this.mBannerOptions.setIndicatorSlideMode(i4);
        this.mBannerOptions.setIndicatorVisibility(i5);
        this.mBannerOptions.setIndicatorGap(dimension);
        this.mBannerOptions.setIndicatorHeight(dimension / 2);
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BannerViewPager);
            initBannerAttrs(typedArrayObtainStyledAttributes);
            initIndicatorAttrs(typedArrayObtainStyledAttributes);
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
