package org.slf4j.event;

import com.plv.foundationsdk.web.PLVWebview;

/* loaded from: classes9.dex */
public enum Level {
    ERROR(40, PLVWebview.MESSAGE_ERROR),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    private int levelInt;
    private String levelStr;

    Level(int i2, String str) {
        this.levelInt = i2;
        this.levelStr = str;
    }

    public int toInt() {
        return this.levelInt;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.levelStr;
    }
}
