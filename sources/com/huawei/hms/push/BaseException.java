package com.huawei.hms.push;

import com.huawei.hms.aaid.constant.ErrorEnum;

/* loaded from: classes4.dex */
public class BaseException extends Exception {

    /* renamed from: a, reason: collision with root package name */
    public final int f7944a;

    /* renamed from: b, reason: collision with root package name */
    public final ErrorEnum f7945b;

    public BaseException(int i2) {
        ErrorEnum errorEnumFromCode = ErrorEnum.fromCode(i2);
        this.f7945b = errorEnumFromCode;
        this.f7944a = errorEnumFromCode.getExternalCode();
    }

    public int getErrorCode() {
        return this.f7944a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.f7945b.getMessage();
    }
}
