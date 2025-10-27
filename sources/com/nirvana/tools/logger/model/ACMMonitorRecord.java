package com.nirvana.tools.logger.model;

/* loaded from: classes4.dex */
public class ACMMonitorRecord extends ACMRecord {
    public static final int MONITOR_URGENCY_DELAYED = 2;
    public static final int MONITOR_URGENCY_REAL_TIME = 1;
    private int urgency;

    public ACMMonitorRecord() {
    }

    public ACMMonitorRecord(int i2) {
        if (i2 != 1 && i2 != 2) {
            i2 = 2;
        }
        this.urgency = i2;
    }

    public int getUrgency() {
        return this.urgency;
    }

    public void setUrgency(int i2) {
        this.urgency = i2;
    }
}
