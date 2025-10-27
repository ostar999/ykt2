package net.lucode.hackware.magicindicator.buildins.commonnavigator.abs;

import java.util.List;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

/* loaded from: classes9.dex */
public interface IPagerIndicator {
    void onPageScrollStateChanged(int i2);

    void onPageScrolled(int i2, float f2, int i3);

    void onPageSelected(int i2);

    void onPositionDataProvide(List<PositionData> list);
}
