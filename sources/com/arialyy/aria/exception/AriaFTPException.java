package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaFTPException extends AriaException {
    private static final String FTP_EXCEPTION = "Aria FTP Exception:";

    public AriaFTPException(String str) {
        super(String.format("%s\n%s", FTP_EXCEPTION, str));
    }

    public AriaFTPException(String str, Exception exc) {
        super(str, exc);
    }
}
