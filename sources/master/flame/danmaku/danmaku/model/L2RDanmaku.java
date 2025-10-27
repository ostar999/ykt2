package master.flame.danmaku.danmaku.model;

/* loaded from: classes8.dex */
public class L2RDanmaku extends R2LDanmaku {
    public L2RDanmaku(Duration duration) {
        super(duration);
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku
    public float getAccurateLeft(IDisplayer iDisplayer, long j2) {
        long actualTime = j2 - getActualTime();
        return actualTime >= this.duration.value ? iDisplayer.getWidth() : (this.mStepX * actualTime) - this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.f27739y + this.paintHeight;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.f27738x;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
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

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.f27738x + this.paintWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.f27739y;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 6;
    }

    @Override // master.flame.danmaku.danmaku.model.R2LDanmaku, master.flame.danmaku.danmaku.model.BaseDanmaku
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
}
