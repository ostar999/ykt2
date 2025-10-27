package com.psychiatrygarden.widget;

import android.os.Parcelable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ViewPagerAdapter extends PagerAdapter {
    private final List<View> views;

    public ViewPagerAdapter(List<View> views) {
        ArrayList arrayList = new ArrayList();
        this.views = arrayList;
        arrayList.clear();
        if (views == null || views.isEmpty()) {
            return;
        }
        arrayList.addAll(views);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull View arg0, int arg1, @NonNull Object arg2) {
        ((ViewPager) arg0).removeView(this.views.get(arg1));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.views.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(@NonNull View arg0, int arg1) {
        ((ViewPager) arg0).addView(this.views.get(arg1), 0);
        return this.views.get(arg1);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(@NonNull View arg0) {
    }
}
