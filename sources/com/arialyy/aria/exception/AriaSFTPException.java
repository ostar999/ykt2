package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaSFTPException extends AriaException {
    private static final String SFTP_EXCEPTION = "Aria SFTP Exception:";

    public AriaSFTPException(String str) {
        super(String.format("%s\n%s", SFTP_EXCEPTION, str));
    }

    public AriaSFTPException(String str, Exception exc) {
        super(str, exc);
    }
}
