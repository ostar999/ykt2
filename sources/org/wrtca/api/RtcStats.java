package org.wrtca.api;

import java.util.Map;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class RtcStats {
    private final String id;
    private final Map<String, Object> members;
    private final long timestampUs;
    private final String type;

    public RtcStats(long j2, String str, String str2, Map<String, Object> map) {
        this.timestampUs = j2;
        this.type = str;
        this.id = str2;
        this.members = map;
    }

    private static void appendValue(StringBuilder sb, Object obj) {
        if (!(obj instanceof Object[])) {
            if (!(obj instanceof String)) {
                sb.append(obj);
                return;
            }
            sb.append('\"');
            sb.append(obj);
            sb.append('\"');
            return;
        }
        Object[] objArr = (Object[]) obj;
        sb.append('[');
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (i2 != 0) {
                sb.append(", ");
            }
            appendValue(sb, objArr[i2]);
        }
        sb.append(']');
    }

    @CalledByNative
    public static RtcStats create(long j2, String str, String str2, Map map) {
        return new RtcStats(j2, str, str2, map);
    }

    public String getId() {
        return this.id;
    }

    public Map<String, Object> getMembers() {
        return this.members;
    }

    public double getTimestampUs() {
        return this.timestampUs;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ timestampUs: ");
        sb.append(this.timestampUs);
        sb.append(", type: ");
        sb.append(this.type);
        sb.append(", id: ");
        sb.append(this.id);
        for (Map.Entry<String, Object> entry : this.members.entrySet()) {
            sb.append(", ");
            sb.append(entry.getKey());
            sb.append(": ");
            appendValue(sb, entry.getValue());
        }
        sb.append(" }");
        return sb.toString();
    }
}
