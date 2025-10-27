package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class ExceptionFactory {
    public static final int TYPE_FTP = 1;
    public static final int TYPE_GROUP = 5;
    public static final int TYPE_HTTP = 2;
    public static final int TYPE_M3U8 = 3;
    public static final int TYPE_SFTP = 4;

    public static AriaException getException(int i2, String str, Exception exc) {
        if (i2 == 1) {
            return exc == null ? new AriaFTPException(str) : new AriaFTPException(str, exc);
        }
        if (i2 == 2) {
            return exc == null ? new AriaHTTPException(str) : new AriaHTTPException(str, exc);
        }
        if (i2 == 3) {
            return exc == null ? new AriaM3U8Exception(str) : new AriaM3U8Exception(str, exc);
        }
        if (i2 == 4) {
            return exc == null ? new AriaSFTPException(str) : new AriaSFTPException(str, exc);
        }
        if (i2 != 5) {
            return exc == null ? new AriaException(str) : new AriaException(str, exc);
        }
        return exc == null ? new AriaGroupException(str) : new AriaGroupException(str, exc);
    }
}
