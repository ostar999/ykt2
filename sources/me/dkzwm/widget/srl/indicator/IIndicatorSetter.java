package me.dkzwm.widget.srl.indicator;

import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public interface IIndicatorSetter {
    void onFingerDown();

    void onFingerDown(float f2, float f3);

    void onFingerMove(float f2, float f3);

    void onFingerUp();

    void setCurrentPos(int i2);

    void setFooterHeight(int i2);

    void setHeaderHeight(int i2);

    void setMaxMoveRatio(float f2);

    void setMaxMoveRatioOfFooter(float f2);

    void setMaxMoveRatioOfHeader(float f2);

    void setMovingStatus(int i2);

    void setOffsetCalculator(IIndicator.IOffsetCalculator iOffsetCalculator);

    void setRatioOfFooterToRefresh(float f2);

    void setRatioOfHeaderToRefresh(float f2);

    void setRatioToKeepFooter(float f2);

    void setRatioToKeepHeader(float f2);

    void setRatioToRefresh(float f2);

    void setResistance(float f2);

    void setResistanceOfFooter(float f2);

    void setResistanceOfHeader(float f2);
}
