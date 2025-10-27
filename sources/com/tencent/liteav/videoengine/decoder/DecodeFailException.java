package com.tencent.liteav.videoengine.decoder;

import java.io.IOException;

/* loaded from: classes6.dex */
public class DecodeFailException extends IOException {
    private static final long serialVersionUID = 5540389146110443860L;
    private final String mErrorMessage;

    public DecodeFailException(String str) {
        this.mErrorMessage = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Decode failed, " + this.mErrorMessage;
    }
}
