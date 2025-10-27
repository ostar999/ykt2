package master.flame.danmaku.danmaku.model;

/* loaded from: classes8.dex */
public class DanmakuTimer {
    public long currMillisecond;
    private long lastInterval;

    public DanmakuTimer() {
    }

    public long add(long j2) {
        return update(this.currMillisecond + j2);
    }

    public long lastInterval() {
        return this.lastInterval;
    }

    public long update(long j2) {
        long j3 = j2 - this.currMillisecond;
        this.lastInterval = j3;
        this.currMillisecond = j2;
        return j3;
    }

    public DanmakuTimer(long j2) {
        update(j2);
    }
}
