package com.mobile.auth.v;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private String f10569a;

    /* renamed from: b, reason: collision with root package name */
    private String f10570b;

    /* renamed from: c, reason: collision with root package name */
    private String f10571c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f10572d;

    public String a() {
        try {
            return this.f10571c;
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

    public void a(String str) {
        try {
            this.f10569a = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(boolean z2) {
        try {
            this.f10572d = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(String str) {
        try {
            this.f10570b = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean b() {
        try {
            return this.f10572d;
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

    public void c(String str) {
        try {
            this.f10571c = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
