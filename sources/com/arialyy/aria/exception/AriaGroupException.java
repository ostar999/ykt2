package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaGroupException extends AriaException {
    private static final String FTP_EXCEPTION = "Aria Group Exception:";

    public AriaGroupException(String str) {
        super(String.format("%s\n%s", FTP_EXCEPTION, str));
    }

    public AriaGroupException(String str, Exception exc) {
        super(str, exc);
    }
}
