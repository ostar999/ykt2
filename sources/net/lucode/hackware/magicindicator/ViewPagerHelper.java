package net.lucode.hackware.magicindicator;

import androidx.viewpager.widget.ViewPager;

/* loaded from: classes9.dex */
public class ViewPagerHelper {
    public static void bind(final MagicIndicator magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: net.lucode.hackware.magicindicator.ViewPagerHelper.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
                magicIndicator.onPageScrollStateChanged(i2);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f2, int i3) {
                magicIndicator.onPageScrolled(i2, f2, i3);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                magicIndicator.onPageSelected(i2);
            }
        });
    }
}
