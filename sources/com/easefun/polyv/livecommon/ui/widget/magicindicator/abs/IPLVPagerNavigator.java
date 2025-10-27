package com.easefun.polyv.livecommon.ui.widget.magicindicator.abs;

/* loaded from: classes3.dex */
public interface IPLVPagerNavigator {
    void notifyDataSetChanged();

    void onAttachToMagicIndicator();

    void onDetachFromMagicIndicator();

    void onPageScrollStateChanged(int state);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);
}
