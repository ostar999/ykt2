package com.mobile.auth.gatewayauth.utils;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    protected static volatile d f10293a;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f10294b = true;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f10295c = true;

    /* renamed from: d, reason: collision with root package name */
    private volatile boolean f10296d = false;

    /* renamed from: e, reason: collision with root package name */
    private volatile boolean f10297e = false;

    /* renamed from: f, reason: collision with root package name */
    private volatile boolean f10298f = true;

    /* renamed from: g, reason: collision with root package name */
    private volatile boolean f10299g = false;

    public static d a() {
        try {
            if (f10293a == null) {
                synchronized (d.class) {
                    if (f10293a == null) {
                        f10293a = new d();
                    }
                }
            }
            return f10293a;
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

    public void a(boolean z2) {
        try {
            this.f10294b = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(boolean z2) {
        try {
            this.f10295c = z2;
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
            return this.f10294b;
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

    public void c(boolean z2) {
        try {
            this.f10296d = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean c() {
        try {
            return this.f10295c;
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

    public void d(boolean z2) {
        try {
            this.f10297e = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean d() {
        try {
            return this.f10296d;
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

    public void e(boolean z2) {
        try {
            this.f10298f = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean e() {
        try {
            return this.f10297e;
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

    public void f(boolean z2) {
        try {
            this.f10299g = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean f() {
        try {
            return this.f10298f;
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

    public boolean g() {
        try {
            return this.f10299g;
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
}
