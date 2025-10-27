package cn.hutool.core.thread;

import cn.hutool.core.date.TimeInterval;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes.dex */
public class ConcurrencyTester implements Closeable {
    private long interval;
    private final SyncFinisher sf;
    private final TimeInterval timeInterval = new TimeInterval();

    public ConcurrencyTester(int i2) {
        this.sf = new SyncFinisher(i2);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.sf.close();
    }

    public long getInterval() {
        return this.interval;
    }

    public ConcurrencyTester reset() {
        this.sf.clearWorker();
        this.timeInterval.restart();
        return this;
    }

    public ConcurrencyTester test(Runnable runnable) throws InterruptedException {
        this.sf.clearWorker();
        this.timeInterval.start();
        this.sf.addRepeatWorker(runnable).setBeginAtSameTime(true).start();
        this.interval = this.timeInterval.interval();
        return this;
    }
}
