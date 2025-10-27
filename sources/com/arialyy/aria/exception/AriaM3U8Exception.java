package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaM3U8Exception extends AriaException {
    private static final String M3U8_EXCEPTION = "Aria M3U8 Exception:";

    public AriaM3U8Exception(String str) {
        super(String.format("%s\n%s", M3U8_EXCEPTION, str));
    }

    public AriaM3U8Exception(String str, Exception exc) {
        super(str, exc);
    }
}
