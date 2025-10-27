package master.flame.danmaku.danmaku.model;

/* loaded from: classes8.dex */
public class R2LDanmaku extends BaseDanmaku {
    protected static final long CORDON_RENDERING_TIME = 40;
    protected static final long MAX_RENDERING_TIME = 100;
    protected int mDistance;
    protected long mLastTime;
    protected float mStepX;

    /* renamed from: x, reason: collision with root package name */
    protected float f27738x = 0.0f;

    /* renamed from: y, reason: collision with root package name */
    protected float f27739y = -1.0f;
    protected float[] RECT = null;

    public R2LDanmaku(Duration duration) {
        this.duration = duration;
    }

    public float getAccurateLeft(IDisplayer iDisplayer, long j2) {
        long actualTime = j2 - getActualTime();
        return actualTime >= this.duration.value ? -this.paintWidth : iDisplayer.getWidth() - (actualTime * this.mStepX);
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f27739y + this.paintHeight;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f27738x;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j2) {
        if (!isMeasured()) {
            return null;
        }
        float accurateLeft = getAccurateLeft(iDisplayer, j2);
        if (this.RECT == null) {
            this.RECT = new float[4];
        }
        float[] fArr = this.RECT;
        fArr[0] = accurateLeft;
        float f2 = this.f27739y;
        fArr[1] = f2;
        fArr[2] = accurateLeft + this.paintWidth;
        fArr[3] = f2 + this.paintHeight;
        return fArr;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f27738x + this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f27739y;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 1;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f2, float f3) {
        DanmakuTimer danmakuTimer = this.mTimer;
        if (danmakuTimer != null) {
            long j2 = danmakuTimer.currMillisecond;
            long actualTime = j2 - getActualTime();
            if (actualTime > 0 && actualTime < this.duration.value) {
                this.f27738x = getAccurateLeft(iDisplayer, j2);
                if (!isShown()) {
                    this.f27739y = f3;
                    setVisibility(true);
                }
                this.mLastTime = j2;
                return;
            }
            this.mLastTime = j2;
        }
        setVisibility(false);
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void measure(IDisplayer iDisplayer, boolean z2) {
        super.measure(iDisplayer, z2);
        int width = (int) (iDisplayer.getWidth() + this.paintWidth);
        this.mDistance = width;
        this.mStepX = width / this.duration.value;
    }
}
