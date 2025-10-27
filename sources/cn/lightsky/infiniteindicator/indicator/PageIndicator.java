package cn.lightsky.infiniteindicator.indicator;

import androidx.viewpager.widget.ViewPager;

/* loaded from: classes.dex */
public interface PageIndicator extends ViewPager.OnPageChangeListener {
    void notifyDataSetChanged();

    void setCurrentItem(int i2);

    void setViewPager(ViewPager viewPager, int i2);
}
