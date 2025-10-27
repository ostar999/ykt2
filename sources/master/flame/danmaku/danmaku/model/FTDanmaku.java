package master.flame.danmaku.danmaku.model;

/* loaded from: classes8.dex */
public class FTDanmaku extends BaseDanmaku {
    private int mLastDispWidth;
    private float mLastLeft;
    private float mLastPaintWidth;

    /* renamed from: x, reason: collision with root package name */
    private float f27736x = 0.0f;

    /* renamed from: y, reason: collision with root package name */
    protected float f27737y = -1.0f;
    private float[] RECT = null;

    public FTDanmaku(Duration duration) {
        this.duration = duration;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f27737y + this.paintHeight;
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

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
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
        float f2 = this.f27737y;
        fArr[1] = f2;
        fArr[2] = left + this.paintWidth;
        fArr[3] = f2 + this.paintHeight;
        return fArr;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f27736x + this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f27737y;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 5;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f2, float f3) {
        DanmakuTimer danmakuTimer = this.mTimer;
        if (danmakuTimer != null) {
            long actualTime = danmakuTimer.currMillisecond - getActualTime();
            if (actualTime <= 0 || actualTime >= this.duration.value) {
                setVisibility(false);
                this.f27737y = -1.0f;
                this.f27736x = iDisplayer.getWidth();
            } else {
                if (isShown()) {
                    return;
                }
                this.f27736x = getLeft(iDisplayer);
                this.f27737y = f3;
                setVisibility(true);
            }
        }
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f27736x;
    }
}
