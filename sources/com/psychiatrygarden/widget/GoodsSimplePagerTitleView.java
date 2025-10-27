package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Typeface;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView;

/* loaded from: classes6.dex */
public class GoodsSimplePagerTitleView extends SimplePagerTitleView {
    public GoodsSimplePagerTitleView(Context context) {
        super(context);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setTypeface(Typeface.DEFAULT);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        setTypeface(Typeface.DEFAULT_BOLD);
    }
}
