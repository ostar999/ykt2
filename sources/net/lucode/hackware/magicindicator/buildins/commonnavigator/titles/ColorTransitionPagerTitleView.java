package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;

/* loaded from: classes9.dex */
public class ColorTransitionPagerTitleView extends SimplePagerTitleView {
    public ColorTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        setTextColor(ArgbEvaluatorHolder.eval(f2, this.mNormalColor, this.mSelectedColor));
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        setTextColor(ArgbEvaluatorHolder.eval(f2, this.mSelectedColor, this.mNormalColor));
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
    }
}
