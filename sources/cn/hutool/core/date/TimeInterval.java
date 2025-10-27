package cn.hutool.core.date;

/* loaded from: classes.dex */
public class TimeInterval extends GroupTimeInterval {
    private static final String DEFAULT_ID = "";
    private static final long serialVersionUID = 1;

    public TimeInterval() {
        this(false);
    }

    public long interval() {
        return interval("");
    }

    public long intervalDay() {
        return intervalDay("");
    }

    public long intervalHour() {
        return intervalHour("");
    }

    public long intervalMinute() {
        return intervalMinute("");
    }

    public long intervalMs() {
        return intervalMs("");
    }

    public String intervalPretty() {
        return intervalPretty("");
    }

    public long intervalRestart() {
        return intervalRestart("");
    }

    public long intervalSecond() {
        return intervalSecond("");
    }

    public long intervalWeek() {
        return intervalWeek("");
    }

    public TimeInterval restart() {
        start("");
        return this;
    }

    public long start() {
        return start("");
    }

    public TimeInterval(boolean z2) {
        super(z2);
        start();
    }
}
