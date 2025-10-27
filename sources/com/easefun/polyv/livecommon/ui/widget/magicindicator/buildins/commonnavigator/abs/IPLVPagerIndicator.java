package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs;

import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVPagerIndicator {
    void onPageScrollStateChanged(int state);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPositionDataProvide(List<PLVPositionData> dataList);
}
