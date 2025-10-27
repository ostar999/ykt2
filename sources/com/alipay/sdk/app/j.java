package com.alipay.sdk.app;

import com.mobile.auth.gatewayauth.ResultCode;

/* loaded from: classes2.dex */
public enum j {
    SUCCEEDED(9000, "处理成功"),
    FAILED(4000, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(4001, ResultCode.MSG_ERROR_INVALID_PARAM),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");


    /* renamed from: h, reason: collision with root package name */
    public int f3105h;

    /* renamed from: i, reason: collision with root package name */
    public String f3106i;

    j(int i2, String str) {
        this.f3105h = i2;
        this.f3106i = str;
    }

    private int a() {
        return this.f3105h;
    }

    private void b(int i2) {
        this.f3105h = i2;
    }

    private void a(String str) {
        this.f3106i = str;
    }

    private String b() {
        return this.f3106i;
    }

    public static j a(int i2) {
        if (i2 == 4001) {
            return PARAMS_ERROR;
        }
        if (i2 == 5000) {
            return DOUBLE_REQUEST;
        }
        if (i2 == 8000) {
            return PAY_WAITTING;
        }
        if (i2 == 9000) {
            return SUCCEEDED;
        }
        if (i2 == 6001) {
            return CANCELED;
        }
        if (i2 != 6002) {
            return FAILED;
        }
        return NETWORK_ERROR;
    }
}
