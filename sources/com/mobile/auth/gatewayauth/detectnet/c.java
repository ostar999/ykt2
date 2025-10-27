package com.mobile.auth.gatewayauth.detectnet;

import cn.hutool.core.text.CharPool;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private String f10048a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f10049b;

    /* renamed from: c, reason: collision with root package name */
    private String f10050c = null;

    /* renamed from: d, reason: collision with root package name */
    private int f10051d;

    /* renamed from: e, reason: collision with root package name */
    private long f10052e;

    public c a(int i2) {
        try {
            this.f10051d = i2;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public c a(long j2) {
        try {
            this.f10052e = j2;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public c a(String str) {
        try {
            this.f10048a = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public c a(boolean z2) {
        try {
            this.f10049b = z2;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public boolean a() {
        try {
            return this.f10049b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public long b() {
        try {
            return this.f10052e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public c b(String str) {
        try {
            this.f10050c = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String toString() {
        try {
            return "PingResult{address='" + this.f10048a + CharPool.SINGLE_QUOTE + ", isReachable=" + this.f10049b + ", error='" + this.f10050c + CharPool.SINGLE_QUOTE + ", errorCode=" + this.f10051d + ", timeTaken=" + this.f10052e + '}';
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
