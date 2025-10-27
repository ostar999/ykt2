package com.arialyy.aria.util;

import com.google.android.exoplayer2.C;

/* loaded from: classes2.dex */
public class BandwidthLimiter {
    public static int maxBandWith = 2048;
    private int bytesWillBeSentOrReceive = 0;
    private long lastPieceSentOrReceiveTick = System.nanoTime();
    private int maxRate = 1024;
    private long timeCostPerChunk = (CHUNK_LENGTH.longValue() * C.NANOS_PER_SECOND) / (this.maxRate * KB.longValue());
    private static Long KB = 1024L;
    private static Long CHUNK_LENGTH = 1024L;

    public BandwidthLimiter(int i2, int i3) throws IllegalArgumentException {
        setMaxRate(i3 > 1 ? i2 / i3 : i2);
    }

    public synchronized void limitNextBytes() {
        limitNextBytes(1);
    }

    public synchronized void setMaxRate(int i2) throws IllegalArgumentException {
        if (i2 < 0) {
            throw new IllegalArgumentException("maxRate can not less than 0");
        }
        this.maxRate = i2;
        if (i2 == 0) {
            this.timeCostPerChunk = 0L;
        } else {
            this.timeCostPerChunk = (CHUNK_LENGTH.longValue() * C.NANOS_PER_SECOND) / (this.maxRate * KB.longValue());
        }
    }

    public synchronized void limitNextBytes(int i2) {
        this.bytesWillBeSentOrReceive += i2;
        while (!Thread.currentThread().isInterrupted() && this.bytesWillBeSentOrReceive > CHUNK_LENGTH.longValue()) {
            long jNanoTime = System.nanoTime();
            long j2 = this.timeCostPerChunk - (jNanoTime - this.lastPieceSentOrReceiveTick);
            if (j2 > 0) {
                try {
                    Thread.currentThread();
                    Thread.sleep(j2 / 1000000, (int) (j2 % 1000000));
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            this.bytesWillBeSentOrReceive = (int) (this.bytesWillBeSentOrReceive - CHUNK_LENGTH.longValue());
            if (j2 <= 0) {
                j2 = 0;
            }
            this.lastPieceSentOrReceiveTick = jNanoTime + j2;
        }
    }
}
