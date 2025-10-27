package com.alibaba.fastjson.serializer;

import kotlin.text.Typography;

/* loaded from: classes2.dex */
public class SerialContext {
    public final int features;
    public final Object fieldName;
    public final Object object;
    public final SerialContext parent;

    public SerialContext(SerialContext serialContext, Object obj, Object obj2, int i2, int i3) {
        this.parent = serialContext;
        this.object = obj;
        this.fieldName = obj2;
        this.features = i2;
    }

    public Object getFieldName() {
        return this.fieldName;
    }

    public Object getObject() {
        return this.object;
    }

    public SerialContext getParent() {
        return this.parent;
    }

    public String getPath() {
        return toString();
    }

    public String toString() {
        if (this.parent == null) {
            return "$";
        }
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    public void toString(StringBuilder sb) {
        boolean z2;
        SerialContext serialContext = this.parent;
        if (serialContext == null) {
            sb.append(Typography.dollar);
            return;
        }
        serialContext.toString(sb);
        Object obj = this.fieldName;
        if (obj == null) {
            sb.append(".null");
            return;
        }
        if (obj instanceof Integer) {
            sb.append('[');
            sb.append(((Integer) this.fieldName).intValue());
            sb.append(']');
            return;
        }
        sb.append('.');
        String string = this.fieldName.toString();
        int i2 = 0;
        while (true) {
            if (i2 >= string.length()) {
                z2 = false;
                break;
            }
            char cCharAt = string.charAt(i2);
            if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt < 'a' || cCharAt > 'z') && cCharAt <= 128))) {
                z2 = true;
                break;
            }
            i2++;
        }
        if (z2) {
            for (int i3 = 0; i3 < string.length(); i3++) {
                char cCharAt2 = string.charAt(i3);
                if (cCharAt2 == '\\') {
                    sb.append('\\');
                    sb.append('\\');
                    sb.append('\\');
                } else if ((cCharAt2 >= '0' && cCharAt2 <= '9') || ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || ((cCharAt2 >= 'a' && cCharAt2 <= 'z') || cCharAt2 > 128))) {
                    sb.append(cCharAt2);
                } else {
                    sb.append('\\');
                    sb.append('\\');
                }
                sb.append(cCharAt2);
            }
            return;
        }
        sb.append(string);
    }
}
