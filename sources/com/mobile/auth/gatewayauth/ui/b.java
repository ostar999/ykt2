package com.mobile.auth.gatewayauth.ui;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private String f10280a;

    /* renamed from: b, reason: collision with root package name */
    private String f10281b;

    /* renamed from: c, reason: collision with root package name */
    private int f10282c;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private String f10283a;

        /* renamed from: b, reason: collision with root package name */
        private String f10284b;

        /* renamed from: c, reason: collision with root package name */
        private int f10285c;

        private a() {
        }

        public static /* synthetic */ String a(a aVar) {
            try {
                return aVar.f10283a;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public static /* synthetic */ String b(a aVar) {
            try {
                return aVar.f10284b;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public static /* synthetic */ int c(a aVar) {
            try {
                return aVar.f10285c;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return -1;
            }
        }

        public a a(int i2) {
            try {
                this.f10285c = i2;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public a a(String str) {
            try {
                this.f10283a = str;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public b a() {
            try {
                return new b(this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public a b(String str) {
            try {
                this.f10284b = str;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
    }

    private b(a aVar) {
        this.f10280a = a.a(aVar);
        this.f10281b = a.b(aVar);
        this.f10282c = a.c(aVar);
    }

    public static a a() {
        try {
            return new a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public String b() {
        try {
            return this.f10280a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public String c() {
        try {
            return this.f10281b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public int d() {
        try {
            return this.f10282c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }
}
