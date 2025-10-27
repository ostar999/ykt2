package net.polyv.danmaku.danmaku.model;

/* loaded from: classes9.dex */
public class FTDanmaku extends BaseDanmaku {
    private int mLastDispWidth;
    private float mLastLeft;
    private float mLastPaintWidth;

    /* renamed from: x, reason: collision with root package name */
    private float f27742x = 0.0f;

    /* renamed from: y, reason: collision with root package name */
    protected float f27743y = -1.0f;
    private float[] RECT = null;

    public FTDanmaku(Duration duration) {
        this.duration = duration;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f27743y + this.paintHeight;
    }

    public float getLeft(IDisplayer iDisplayer) {
        if (this.mLastDispWidth == iDisplayer.getWidth() && this.mLastPaintWidth == this.paintWidth) {
            return this.mLastLeft;
        }
        float width = (iDisplayer.getWidth() - this.paintWidth) / 2.0f;
        this.mLastDispWidth = iDisplayer.getWidth();
        this.mLastPaintWidth = this.paintWidth;
        this.mLastLeft = width;
        return width;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j2) {
        if (!isMeasured()) {
            return null;
        }
        float left = getLeft(iDisplayer);
        if (this.RECT == null) {
            this.RECT = new float[4];
        }
        float[] fArr = this.RECT;
        fArr[0] = left;
        float f2 = this.f27743y;
        fArr[1] = f2;
        fArr[2] = left + this.paintWidth;
        fArr[3] = f2 + this.paintHeight;
        return fArr;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f27742x + this.paintWidth;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f27743y;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 5;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f2, float f3) {
        DanmakuTimer danmakuTimer = this.mTimer;
        if (danmakuTimer != null) {
            long actualTime = danmakuTimer.currMillisecond - getActualTime();
            if (actualTime <= 0 || actualTime >= this.duration.value) {
                setVisibility(false);
                this.f27743y = -1.0f;
                this.f27742x = iDisplayer.getWidth();
            } else {
                if (isShown()) {
                    return;
                }
                this.f27742x = getLeft(iDisplayer);
                this.f27743y = f3;
                setVisibility(true);
            }
        }
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f27742x;
    }
}
