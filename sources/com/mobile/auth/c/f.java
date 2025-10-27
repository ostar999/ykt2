package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.security.interfaces.RSAPublicKey;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9642a = "f";

    public static String a(String str, String str2) {
        try {
            return a.a(str, str2);
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

    public static String a(String str, RSAPublicKey rSAPublicKey) {
        try {
            return m.a(str, rSAPublicKey);
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

    public static RSAPublicKey a() {
        try {
            return m.a();
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

    public static String b(String str, String str2) {
        try {
            return a.b(str, str2);
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

    public static String c(String str, String str2) {
        try {
            return g.b(g.a(str, str2));
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
