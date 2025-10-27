package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaHTTPException extends AriaException {
    private static final String HTTP_EXCEPTION = "Aria HTTP Exception:";

    public AriaHTTPException(String str) {
        super(String.format("%s\n%s", HTTP_EXCEPTION, str));
    }

    public AriaHTTPException(String str, Exception exc) {
        super(str, exc);
    }
}
