package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles;

import android.content.Context;
import cn.webdemo.com.supporfragment.R;

/* loaded from: classes.dex */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    private float mMinScale;
    private boolean selectedBold;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
        this.mMinScale = 0.8f;
        this.selectedBold = true;
    }

    public float getMinScale() {
        return this.mMinScale;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
        if (this.selectedBold) {
            getPaint().setFakeBoldText(false);
            invalidate();
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        super.onEnter(i2, i3, f2, z2);
        setTextSize(0, getResources().getDimensionPixelSize(R.dimen.process_txt_size));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        super.onLeave(i2, i3, f2, z2);
        setTextSize(0, getResources().getDimensionPixelSize(R.dimen.default_txt_size));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
        if (this.selectedBold) {
            getPaint().setFakeBoldText(true);
            invalidate();
        }
    }

    public void setMinScale(float f2) {
        this.mMinScale = f2;
    }

    public void setSelectedBold(boolean z2) {
        this.selectedBold = z2;
    }
}
