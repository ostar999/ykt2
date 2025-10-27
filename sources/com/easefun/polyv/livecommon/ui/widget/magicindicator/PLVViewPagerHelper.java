package com.easefun.polyv.livecommon.ui.widget.magicindicator;

import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public class PLVViewPagerHelper {
    public static void bind(final PLVMagicIndicator magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVViewPagerHelper.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }
        });
    }
}
