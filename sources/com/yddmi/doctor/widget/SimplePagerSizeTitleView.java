package com.yddmi.doctor.widget;

import android.content.Context;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/* loaded from: classes6.dex */
public class SimplePagerSizeTitleView extends SimplePagerTitleView {
    protected float mNormalSize;
    protected float mSelectedSize;

    public SimplePagerSizeTitleView(Context context) {
        super(context);
        this.mSelectedSize = 14.0f;
        this.mNormalSize = 14.0f;
    }

    public float getNormalSize() {
        return this.mNormalSize;
    }

    public float getSelectedSize() {
        return this.mSelectedSize;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
        setTextSize(2, getNormalSize());
        super.onDeselected(i2, i3);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView, net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
        setTextSize(2, getSelectedSize());
        super.onSelected(i2, i3);
    }

    public void setNormalSize(float f2) {
        this.mNormalSize = f2;
    }

    public void setSelectedSize(float f2) {
        this.mSelectedSize = f2;
    }
}
