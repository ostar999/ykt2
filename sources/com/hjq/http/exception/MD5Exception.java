package com.hjq.http.exception;

/* loaded from: classes4.dex */
public final class MD5Exception extends HttpException {
    private final String mMD5;

    public MD5Exception(String str, String str2) {
        super(str);
        this.mMD5 = str2;
    }

    public String getMD5() {
        return this.mMD5;
    }
}
