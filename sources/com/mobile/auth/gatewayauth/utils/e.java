package com.mobile.auth.gatewayauth.utils;

import cn.hutool.core.text.StrPool;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.FeatureManager;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    private static e f10300b;

    /* renamed from: a, reason: collision with root package name */
    private ConcurrentHashMap<String, StringBuffer> f10301a = new ConcurrentHashMap<>();

    private e() {
    }

    public static e a() {
        try {
            if (f10300b == null) {
                synchronized (e.class) {
                    if (f10300b == null) {
                        f10300b = new e();
                    }
                }
            }
            return f10300b;
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

    public String a(String str) {
        StringBuffer stringBufferRemove;
        try {
            if (FeatureManager.getInstance().get(FeatureManager.FEATURE_KEY_PERFORMANCE_TRACKER) == null && (stringBufferRemove = this.f10301a.remove(str)) != null) {
                return stringBufferRemove.toString();
            }
            return null;
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

    public void a(String str, String str2, long j2) {
        try {
            a(str, str2, j.a(j2));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str, String str2, String str3) {
        StringBuffer stringBuffer;
        try {
            if (FeatureManager.getInstance().get(FeatureManager.FEATURE_KEY_PERFORMANCE_TRACKER) != null) {
                return;
            }
            if (this.f10301a.containsKey(str)) {
                stringBuffer = this.f10301a.get(str);
            } else {
                synchronized (this.f10301a) {
                    if (this.f10301a.containsKey(str)) {
                        stringBuffer = null;
                    } else {
                        stringBuffer = new StringBuffer(str);
                        this.f10301a.put(str, stringBuffer);
                    }
                }
            }
            stringBuffer.append(StrPool.BRACKET_START + str2 + ":" + str3 + StrPool.BRACKET_END);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
