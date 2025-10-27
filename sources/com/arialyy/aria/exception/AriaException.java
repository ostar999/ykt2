package com.arialyy.aria.exception;

/* loaded from: classes2.dex */
public class AriaException extends Exception {
    public AriaException(String str) {
        super(str);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AriaException(String str, Exception exc) {
        Object[] objArr = new Object[2];
        objArr[0] = str == null ? "" : str;
        objArr[1] = exc != null ? exc.getMessage() : "";
        super(String.format("%s\n%s", objArr));
    }
}
