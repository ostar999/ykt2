package cn.hutool.core.date;

import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;
import java.util.Map;

/* loaded from: classes.dex */
public class GroupTimeInterval implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Map<String, Long> groupMap = new SafeConcurrentHashMap();
    private final boolean isNano;

    public GroupTimeInterval(boolean z2) {
        this.isNano = z2;
    }

    private long getTime() {
        return this.isNano ? System.nanoTime() : System.currentTimeMillis();
    }

    public GroupTimeInterval clear() {
        this.groupMap.clear();
        return this;
    }

    public long interval(String str) {
        Long l2 = this.groupMap.get(str);
        if (l2 == null) {
            return 0L;
        }
        return getTime() - l2.longValue();
    }

    public long intervalDay(String str) {
        return interval(str, DateUnit.DAY);
    }

    public long intervalHour(String str) {
        return interval(str, DateUnit.HOUR);
    }

    public long intervalMinute(String str) {
        return interval(str, DateUnit.MINUTE);
    }

    public long intervalMs(String str) {
        return interval(str, DateUnit.MS);
    }

    public String intervalPretty(String str) {
        return DateUtil.formatBetween(intervalMs(str));
    }

    public long intervalRestart(String str) {
        long time = getTime();
        return time - ((Long) ObjectUtil.defaultIfNull(this.groupMap.put(str, Long.valueOf(time)), Long.valueOf(time))).longValue();
    }

    public long intervalSecond(String str) {
        return interval(str, DateUnit.SECOND);
    }

    public long intervalWeek(String str) {
        return interval(str, DateUnit.WEEK);
    }

    public long start(String str) {
        long time = getTime();
        this.groupMap.put(str, Long.valueOf(time));
        return time;
    }

    public long interval(String str, DateUnit dateUnit) {
        long jInterval = this.isNano ? interval(str) / 1000000 : interval(str);
        return DateUnit.MS == dateUnit ? jInterval : jInterval / dateUnit.getMillis();
    }
}
