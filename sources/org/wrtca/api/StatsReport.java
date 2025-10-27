package org.wrtca.api;

import cn.hutool.core.text.StrPool;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class StatsReport {
    public final String id;
    public final double timestamp;
    public final String type;
    public final Value[] values;

    public static class Value {
        public final String name;
        public final String value;

        @CalledByNative("Value")
        public Value(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        public String toString() {
            return StrPool.BRACKET_START + this.name + ": " + this.value + StrPool.BRACKET_END;
        }
    }

    @CalledByNative
    public StatsReport(String str, String str2, double d3, Value[] valueArr) {
        this.id = str;
        this.type = str2;
        this.timestamp = d3;
        this.values = valueArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(this.id);
        sb.append(", type: ");
        sb.append(this.type);
        sb.append(", timestamp: ");
        sb.append(this.timestamp);
        sb.append(", values: ");
        int i2 = 0;
        while (true) {
            Value[] valueArr = this.values;
            if (i2 >= valueArr.length) {
                return sb.toString();
            }
            sb.append(valueArr[i2].toString());
            sb.append(", ");
            i2++;
        }
    }
}
