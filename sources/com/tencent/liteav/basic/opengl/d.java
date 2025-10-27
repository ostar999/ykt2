package com.tencent.liteav.basic.opengl;

import java.io.IOException;

/* loaded from: classes6.dex */
public class d extends IOException {
    private static final long serialVersionUID = 2723743254380545567L;
    private final int mErrorCode;
    private final String mErrorMessage;

    public d(int i2) {
        this(i2, null);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.mErrorMessage == null) {
            return "EGL error code: " + this.mErrorCode;
        }
        return "EGL error code: " + this.mErrorCode + this.mErrorMessage;
    }

    public d(int i2, String str) {
        this.mErrorCode = i2;
        this.mErrorMessage = str;
    }
}
