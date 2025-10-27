package com.tencent.smtt.export.external.interfaces;

/* loaded from: classes6.dex */
public interface ConsoleMessage {

    public enum MessageLevel {
        TIP,
        LOG,
        WARNING,
        ERROR,
        DEBUG
    }

    int lineNumber();

    String message();

    MessageLevel messageLevel();

    String sourceId();
}
