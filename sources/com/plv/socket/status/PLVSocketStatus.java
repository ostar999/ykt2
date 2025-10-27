package com.plv.socket.status;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVSocketStatus {
    public static final int STATUS_IDLE = 0;
    public static final int STATUS_LOGINFAIL = 5;
    public static final int STATUS_LOGINING = 1;
    public static final int STATUS_LOGINSUCCESS = 2;
    public static final int STATUS_RECONNECTING = 3;
    public static final int STATUS_RECONNECTSUCCESS = 4;
    private int errorCode;
    private String message;
    private int status;
    private Throwable throwable;

    private PLVSocketStatus() {
    }

    public static PLVSocketStatus create(int i2) {
        return create(i2, 0, null);
    }

    public static PLVSocketStatus fail(int i2, Throwable th) {
        return create(5, i2, th);
    }

    private static String toMessageFromStatus(int i2, int i3, Throwable th) {
        String str = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "unknown" : "登录失败" : "重连成功" : "重连中" : "登录成功" : "登录中" : "闲置";
        if (th == null) {
            return str;
        }
        return str + "(" + th.getMessage() + "-" + i3 + ")";
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public Throwable getThrowable() {
        Throwable th = this.throwable;
        return th == null ? new Exception("") : th;
    }

    public void setErrorCode(int i2) {
        this.errorCode = i2;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setThrowable(Throwable th) {
        this.throwable = th;
    }

    public String toString() {
        return "PLVSocketStatus{status=" + this.status + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", errorCode=" + this.errorCode + ", throwable=" + this.throwable + '}';
    }

    public static PLVSocketStatus create(int i2, int i3, Throwable th) {
        PLVSocketStatus pLVSocketStatus = new PLVSocketStatus();
        pLVSocketStatus.setStatus(i2);
        pLVSocketStatus.setErrorCode(i3);
        pLVSocketStatus.setMessage(toMessageFromStatus(i2, i3, th));
        pLVSocketStatus.setThrowable(th);
        return pLVSocketStatus;
    }
}
