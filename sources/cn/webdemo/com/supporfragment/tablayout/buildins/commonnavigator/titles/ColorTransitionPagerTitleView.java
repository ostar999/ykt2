package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles;

import android.content.Context;
import cn.webdemo.com.supporfragment.tablayout.buildins.ArgbEvaluatorHolder;

/* loaded from: classes.dex */
public class ColorTransitionPagerTitleView extends SimplePagerTitleView {
    public ColorTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        setTextColor(ArgbEvaluatorHolder.eval(f2, this.mNormalColor, this.mSelectedColor));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        setTextColor(ArgbEvaluatorHolder.eval(f2, this.mSelectedColor, this.mNormalColor));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
    }
}
