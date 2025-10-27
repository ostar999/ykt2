package com.plv.linkmic.repository;

import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class PLVLinkMicHttpRequestException extends Exception {
    private int errorCode;

    public static class Builder {
        Throwable cause;
        int errorCode;
        String msg;

        public Builder(int i2) {
            this.errorCode = i2;
        }

        public PLVLinkMicHttpRequestException build() {
            return new PLVLinkMicHttpRequestException(this.msg, this.cause, this.errorCode);
        }

        public Builder cause(Throwable th) {
            this.cause = th;
            return this;
        }

        public Builder msg(String str) {
            this.msg = str;
            return this;
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    private PLVLinkMicHttpRequestException(String str, @Nullable Throwable th, int i2) {
        super(str, th);
        this.errorCode = i2;
    }
}
