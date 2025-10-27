package com.hyphenate.chat;

/* loaded from: classes4.dex */
class EMTimeTag {
    private long oldTime = 0;
    private long timeSpent = 0;

    public void start() {
        this.oldTime = System.currentTimeMillis();
    }

    public long stop() {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.oldTime;
        this.timeSpent = jCurrentTimeMillis;
        return jCurrentTimeMillis;
    }

    public long timeSpent() {
        return this.timeSpent;
    }

    public String timeStr() {
        return EMCollector.timeToString(this.timeSpent);
    }
}
