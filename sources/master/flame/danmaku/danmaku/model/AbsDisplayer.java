package master.flame.danmaku.danmaku.model;

import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;

/* loaded from: classes8.dex */
public abstract class AbsDisplayer<T, F> implements IDisplayer {
    public abstract void clearTextHeightCache();

    public abstract void drawDanmaku(BaseDanmaku baseDanmaku, T t2, float f2, float f3, boolean z2);

    public abstract BaseCacheStuffer getCacheStuffer();

    public abstract T getExtraData();

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public boolean isHardwareAccelerated() {
        return false;
    }

    public abstract void setCacheStuffer(BaseCacheStuffer baseCacheStuffer);

    public abstract void setExtraData(T t2);

    public abstract void setFakeBoldText(boolean z2);

    public abstract void setScaleTextSizeFactor(float f2);

    public abstract void setTransparency(int i2);

    public abstract void setTypeFace(F f2);
}
