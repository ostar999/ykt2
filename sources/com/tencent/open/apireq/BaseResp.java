package com.tencent.open.apireq;

import cn.hutool.core.text.CharPool;

/* loaded from: classes6.dex */
public class BaseResp {
    public static final int CODE_ERROR_PARAMS = -2000;
    public static final int CODE_NOT_LOGIN = -2001;
    public static final int CODE_QQ_LOW_VERSION = -1001;
    public static final int CODE_QQ_NOT_INSTALLED = -1000;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_UNSUPPORTED_BRANCH = -1002;

    /* renamed from: a, reason: collision with root package name */
    private int f20516a = 0;

    /* renamed from: b, reason: collision with root package name */
    private String f20517b = "";

    public String a(int i2) {
        return "Api call failed.";
    }

    public int getCode() {
        return this.f20516a;
    }

    public String getErrorMsg() {
        return this.f20517b;
    }

    public boolean isSuccess() {
        return this.f20516a == 0;
    }

    public void setCode(int i2) {
        String strA;
        this.f20516a = i2;
        if (i2 == -2001) {
            strA = "Not login.";
        } else if (i2 == -2000) {
            strA = "The given params check failed.";
        } else if (i2 != 0) {
            switch (i2) {
                case -1002:
                    strA = "The QQ branch (e.g. TIM) is not supported";
                    break;
                case CODE_QQ_LOW_VERSION /* -1001 */:
                    strA = "QQ version is too low.";
                    break;
                case -1000:
                    strA = "QQ is not installed.";
                    break;
                default:
                    strA = a(i2);
                    break;
            }
        } else {
            strA = "";
        }
        setErrorMsg(strA);
    }

    public void setErrorMsg(String str) {
        this.f20517b = str;
    }

    public String toString() {
        return "BaseResp{mCode=" + this.f20516a + ", mErrorMsg='" + this.f20517b + CharPool.SINGLE_QUOTE + '}';
    }
}
