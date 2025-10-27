package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs;

import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.model.PositionData;
import java.util.List;

/* loaded from: classes.dex */
public interface IPagerIndicator {
    void onPageScrollStateChanged(int i2);

    void onPageScrolled(int i2, float f2, int i3);

    void onPageSelected(int i2);

    void onPositionDataProvide(List<PositionData> list);
}
