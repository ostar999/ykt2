package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.os.Debug;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.base.Cache;
import com.mobile.auth.gatewayauth.manager.base.CacheKey;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.utils.Checker;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.gatewayauth.utils.security.CheckProxy;
import com.mobile.auth.gatewayauth.utils.security.CheckRoot;
import com.mobile.auth.gatewayauth.utils.security.EmulatorDetector;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.core.CryptUtil;
import com.nirvana.tools.core.ExecutorManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class SystemManager {

    /* renamed from: a, reason: collision with root package name */
    private final Context f10072a;

    /* renamed from: b, reason: collision with root package name */
    private String f10073b;

    /* renamed from: c, reason: collision with root package name */
    private String f10074c;

    /* renamed from: d, reason: collision with root package name */
    private final com.mobile.auth.o.a f10075d;

    /* renamed from: e, reason: collision with root package name */
    private volatile boolean f10076e = true;

    /* renamed from: f, reason: collision with root package name */
    private Future<?> f10077f;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public SystemManager(final Context context, com.mobile.auth.o.a aVar) {
        this.f10072a = context.getApplicationContext();
        this.f10077f = ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.SystemManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SystemManager.a(SystemManager.this, PackageUtils.getPackageName(context));
                    SystemManager.b(SystemManager.this, PackageUtils.getSign(context));
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }
        });
        this.f10075d = aVar;
    }

    private TokenRet a(ResultCodeProcessor resultCodeProcessor, String str) {
        try {
            if (!com.mobile.auth.gatewayauth.utils.c.f(this.f10072a)) {
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_NO_SIM_FAIL, "SIM卡无法检测", str);
            }
            if (com.mobile.auth.gatewayauth.utils.c.e(this.f10072a)) {
                return null;
            }
            return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_NO_MOBILE_NETWORK_FAIL, "移动网络未开启", str);
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

    public static /* synthetic */ String a(SystemManager systemManager, String str) {
        try {
            systemManager.f10073b = str;
            return str;
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

    public static /* synthetic */ String b(SystemManager systemManager, String str) {
        try {
            systemManager.f10074c = str;
            return str;
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

    public TokenRet a(ResultCodeProcessor resultCodeProcessor, boolean z2, String str) {
        try {
            TokenRet tokenRetCheckEnvSafe = checkEnvSafe(resultCodeProcessor, str);
            return (tokenRetCheckEnvSafe == null && z2) ? a(resultCodeProcessor, str) : tokenRetCheckEnvSafe;
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

    public synchronized String a() {
        try {
            Future<?> future = this.f10077f;
            if (future != null) {
                try {
                    future.get();
                    this.f10077f = null;
                } catch (InterruptedException e2) {
                    e = e2;
                    e.printStackTrace();
                    return this.f10073b;
                } catch (ExecutionException e3) {
                    e = e3;
                    e.printStackTrace();
                    return this.f10073b;
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.f10073b;
    }

    public void a(String str) {
        try {
            if ((FeatureManager.getInstance().get("whiteFileCheck") == null || !k.a.f27524v.equals(FeatureManager.getInstance().get("whiteFileCheck").toString())) && !TextUtils.isEmpty(str)) {
                InputStream inputStream = null;
                try {
                    try {
                        String strMd5Hex = CryptUtil.md5Hex(str);
                        if (TextUtils.isEmpty(strMd5Hex)) {
                            this.f10076e = true;
                            return;
                        }
                        InputStream inputStreamOpen = this.f10072a.getAssets().open(strMd5Hex);
                        this.f10076e = false;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception unused) {
                        this.f10076e = true;
                        if (0 != 0) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    }
                } finally {
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized <T> boolean a(String str, Cache<T> cache, long j2) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (cache != null && cache.getKey() != null && cache.getKey().equals(str) && cache.getExpiredTime() - j2 > jCurrentTimeMillis) {
                return true;
            }
            if (cache != null) {
                this.f10075d.a("ExpiredTime:", String.valueOf(cache.getExpiredTime()), "|threshold:", String.valueOf(j2), "|currTime:", String.valueOf(jCurrentTimeMillis));
            }
            return false;
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

    public synchronized String b() {
        try {
            Future<?> future = this.f10077f;
            if (future != null) {
                try {
                    future.get();
                    this.f10077f = null;
                } catch (InterruptedException e2) {
                    e = e2;
                    e.printStackTrace();
                    return this.f10074c;
                } catch (ExecutionException e3) {
                    e = e3;
                    e.printStackTrace();
                    return this.f10074c;
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.f10074c;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "unknown"
            if (r6 != 0) goto L5
            return r0
        L5:
            int r1 = r6.hashCode()     // Catch: java.lang.Throwable -> L4a
            r2 = -1350608857(0xffffffffaf7f5827, float:-2.3223433E-10)
            r3 = 2
            r4 = 1
            if (r1 == r2) goto L2f
            r2 = 95009260(0x5a9b9ec, float:1.596098E-35)
            if (r1 == r2) goto L25
            r2 = 880617272(0x347d2738, float:2.3576729E-7)
            if (r1 == r2) goto L1b
            goto L39
        L1b:
            java.lang.String r1 = "cm_zyhl"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L39
            r6 = 0
            goto L3a
        L25:
            java.lang.String r1 = "cu_xw"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L39
            r6 = r4
            goto L3a
        L2f:
            java.lang.String r1 = "ct_sjl"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L39
            r6 = r3
            goto L3a
        L39:
            r6 = -1
        L3a:
            if (r6 == 0) goto L47
            if (r6 == r4) goto L44
            if (r6 == r3) goto L41
            return r0
        L41:
            java.lang.String r6 = "CTCC"
            return r6
        L44:
            java.lang.String r6 = "CUCC"
            return r6
        L47:
            java.lang.String r6 = "CMCC"
            return r6
        L4a:
            r6 = move-exception
            r0 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)     // Catch: java.lang.Throwable -> L50
            return r0
        L50:
            r6 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.manager.SystemManager.b(java.lang.String):java.lang.String");
    }

    public String c() {
        try {
            return com.mobile.auth.gatewayauth.utils.c.b(this.f10072a);
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

    public String c(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_PRE_LOGIN_CODE;
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

    @SafeProtector
    public TokenRet checkEnvSafe(ResultCodeProcessor resultCodeProcessor, String str) {
        try {
            try {
                if (this.f10076e) {
                    String strC = Checker.c();
                    if (!TextUtils.isEmpty(strC) && !"0".equals(strC)) {
                        return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the app is attached, please use safe phone!", str);
                    }
                }
                String strIsDeviceRooted = CheckRoot.isDeviceRooted();
                if (!TextUtils.isEmpty(strIsDeviceRooted)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the phone is root, " + strIsDeviceRooted, str);
                }
                if (Thread.currentThread() == Looper.getMainLooper().getThread() && EmulatorDetector.isEmulator(this.f10072a)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:Emulator is detected, please use real phone!", str);
                }
                if (CheckProxy.isDevicedProxy(this.f10072a)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the phone is proxy, please do not proxy!", str);
                }
                if (!Debug.isDebuggerConnected() || i.a()) {
                    return null;
                }
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the app is debuggerConnected, please do not debug!", str);
            } catch (Exception e2) {
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "无法判运营商: " + e2.getMessage(), str);
            }
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

    public String d() {
        try {
            return com.mobile.auth.gatewayauth.utils.c.c(this.f10072a);
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

    public String d(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_PRE_AUTH_CODE;
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

    @SafeProtector
    public native synchronized String decryptContent(String str);

    public Context e() {
        try {
            return this.f10072a;
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

    public String e(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_LOGIN_CODE;
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

    @SafeProtector
    public native synchronized String encryptContent(String str);

    public String f(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_LOGIN_TOKEN;
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

    public String g(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_AUTH_TOKEN;
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

    @SafeProtector
    public native CacheKey getSimCacheKey(boolean z2, String str);

    @SafeProtector
    public native CacheKey getVendorCacheKey(String str);

    public String h(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_LOGIN;
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

    public String i(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_PRIVACYALERT;
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

    public String j(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_RETURN;
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

    public String k(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_CLICK_PRIVACYALERT_PRIVACY;
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

    public String l(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_PROTOCOL;
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

    public String m(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_START;
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
