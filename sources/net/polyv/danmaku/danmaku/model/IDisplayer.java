package net.polyv.danmaku.danmaku.model;

/* loaded from: classes9.dex */
public interface IDisplayer {
    public static final int DANMAKU_STYLE_DEFAULT = -1;
    public static final int DANMAKU_STYLE_NONE = 0;
    public static final int DANMAKU_STYLE_PROJECTION = 3;
    public static final int DANMAKU_STYLE_SHADOW = 1;
    public static final int DANMAKU_STYLE_STROKEN = 2;

    int draw(BaseDanmaku baseDanmaku);

    int getAllMarginTop();

    float getDensity();

    int getDensityDpi();

    int getHeight();

    int getMargin();

    int getMaximumCacheHeight();

    int getMaximumCacheWidth();

    float getScaledDensity();

    int getSlopPixel();

    float getStrokeWidth();

    int getWidth();

    boolean isHardwareAccelerated();

    void measure(BaseDanmaku baseDanmaku, boolean z2);

    void prepare(BaseDanmaku baseDanmaku, boolean z2);

    void recycle(BaseDanmaku baseDanmaku);

    void resetSlopPixel(float f2);

    void setAllMarginTop(int i2);

    void setDanmakuStyle(int i2, float[] fArr);

    void setDensities(float f2, int i2, float f3);

    void setHardwareAccelerated(boolean z2);

    void setMargin(int i2);

    void setSize(int i2, int i3);
}
