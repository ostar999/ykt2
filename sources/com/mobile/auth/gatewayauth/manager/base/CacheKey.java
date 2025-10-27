package com.mobile.auth.gatewayauth.manager.base;

import cn.hutool.core.text.CharPool;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class CacheKey {
    private boolean isLocalIp;
    private String key;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private String f10201a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f10202b;

        private a() {
        }

        public static /* synthetic */ String a(a aVar) {
            try {
                return aVar.f10201a;
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

        public static /* synthetic */ boolean b(a aVar) {
            try {
                return aVar.f10202b;
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

        public a a(String str) {
            try {
                this.f10201a = str;
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

        public a a(boolean z2) {
            try {
                this.f10202b = z2;
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

        public CacheKey a() {
            try {
                return new CacheKey(this);
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

    private CacheKey(a aVar) {
        this.key = a.a(aVar);
        this.isLocalIp = a.b(aVar);
    }

    public static a newSimKey() {
        try {
            return new a();
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

    public String getKey() {
        try {
            return this.key;
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

    public boolean isLocalIp() {
        try {
            return this.isLocalIp;
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

    public void setKey(String str) {
        try {
            this.key = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLocalIp(boolean z2) {
        try {
            this.isLocalIp = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String toString() {
        try {
            return "SimKey{key='" + this.key + CharPool.SINGLE_QUOTE + ", isLocalIp=" + this.isLocalIp + '}';
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
