package com.hyphenate.easeui.adapter;

import android.view.View;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

/* loaded from: classes4.dex */
public class EmojiconPagerAdapter extends PagerAdapter {
    private List<View> views;

    public EmojiconPagerAdapter(List<View> list) {
        this.views = list;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(View view, int i2, Object obj) {
        ((ViewPager) view).removeView(this.views.get(i2));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.views.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(View view, int i2) {
        ((ViewPager) view).addView(this.views.get(i2));
        return this.views.get(i2);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
