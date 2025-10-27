package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs;

/* loaded from: classes3.dex */
public interface IPLVPagerTitleView {
    void onDeselected(int index, int totalCount);

    void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);

    void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

    void onSelected(int index, int totalCount);
}
