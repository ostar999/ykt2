package cn.hutool.core.date;

import java.time.ZoneId;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class ZoneUtil {
    public static TimeZone toTimeZone(ZoneId zoneId) {
        return zoneId == null ? TimeZone.getDefault() : TimeZone.getTimeZone(zoneId);
    }

    public static ZoneId toZoneId(TimeZone timeZone) {
        return timeZone == null ? ZoneId.systemDefault() : timeZone.toZoneId();
    }
}
