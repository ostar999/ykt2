package cn.hutool.core.io.file;

import cn.hutool.core.text.StrPool;

/* loaded from: classes.dex */
public enum LineSeparator {
    MAC(StrPool.CR),
    LINUX("\n"),
    WINDOWS("\r\n");

    private final String value;

    LineSeparator(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
