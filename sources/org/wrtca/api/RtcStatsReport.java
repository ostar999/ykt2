package org.wrtca.api;

import java.util.Map;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class RtcStatsReport {
    private final Map<String, RtcStats> stats;
    private final long timestampUs;

    public RtcStatsReport(long j2, Map<String, RtcStats> map) {
        this.timestampUs = j2;
        this.stats = map;
    }

    @CalledByNative
    private static RtcStatsReport create(long j2, Map map) {
        return new RtcStatsReport(j2, map);
    }

    public Map<String, RtcStats> getStatsMap() {
        return this.stats;
    }

    public double getTimestampUs() {
        return this.timestampUs;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ timestampUs: ");
        sb.append(this.timestampUs);
        sb.append(", stats: [\n");
        boolean z2 = true;
        for (RtcStats rtcStats : this.stats.values()) {
            if (!z2) {
                sb.append(",\n");
            }
            sb.append(rtcStats);
            z2 = false;
        }
        sb.append(" ] }");
        return sb.toString();
    }
}
