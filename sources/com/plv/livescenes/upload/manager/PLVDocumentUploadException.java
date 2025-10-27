package com.plv.livescenes.upload.manager;

import java.io.IOException;

/* loaded from: classes5.dex */
class PLVDocumentUploadException extends IOException {
    private int code;
    private Type type;

    public enum Type {
        TYPE_TOKEN,
        TYPE_UPLOAD_PROCESS
    }

    public PLVDocumentUploadException(String str, Throwable th, int i2, Type type) {
        super(str, th);
        this.code = i2;
        this.type = type;
    }

    public int getCode() {
        return this.code;
    }

    public Type getType() {
        return this.type;
    }
}
