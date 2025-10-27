package com.tencent.tbs.one;

import cn.hutool.core.text.StrPool;

/* loaded from: classes6.dex */
public class TBSOneException extends Exception {

    /* renamed from: a, reason: collision with root package name */
    public int f21682a;

    public TBSOneException(int i2, String str) {
        super(str);
        this.f21682a = i2;
    }

    public TBSOneException(int i2, String str, Throwable th) {
        super(str, th);
        this.f21682a = i2;
    }

    public int getErrorCode() {
        return this.f21682a;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return StrPool.BRACKET_START + this.f21682a + "] " + getLocalizedMessage();
    }
}
