package com.mobile.auth.v;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.just.agentweb.DefaultWebClient;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static volatile d f10564a;

    /* renamed from: b, reason: collision with root package name */
    private int f10565b = 0;

    /* renamed from: c, reason: collision with root package name */
    private int f10566c = 3000;

    /* renamed from: d, reason: collision with root package name */
    private int f10567d = 3000;

    /* renamed from: e, reason: collision with root package name */
    private c f10568e;

    public static d a() {
        try {
            if (f10564a == null) {
                synchronized (d.class) {
                    if (f10564a == null) {
                        f10564a = new d();
                    }
                }
            }
            return f10564a;
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

    public d a(int i2) {
        try {
            this.f10566c = i2;
            return f10564a;
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

    public d a(c cVar) {
        try {
            this.f10568e = cVar;
            return f10564a;
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

    public e b() {
        e eVar;
        c cVar;
        try {
            eVar = new e();
            cVar = this.f10568e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        if (cVar == null) {
            eVar.a("100008");
            eVar.b("请求参数为空");
            eVar.a(false);
            return eVar;
        }
        if (TextUtils.isEmpty(cVar.getBaseUrl())) {
            eVar.a("100004");
            eVar.b("url 为空");
            eVar.a(false);
            return eVar;
        }
        try {
            if (TextUtils.isEmpty(new URL(this.f10568e.getBaseUrl()).getHost())) {
                eVar.a("100001");
                eVar.b("host 为空");
                eVar.a(false);
                return eVar;
            }
            if (TextUtils.isEmpty(this.f10568e.getMethod()) && TextUtils.isEmpty(this.f10568e.getAction())) {
                eVar.a("100002");
                eVar.b("api 为空");
                eVar.a(false);
                return eVar;
            }
            if (this.f10568e.isSign() && TextUtils.isEmpty(this.f10568e.getAccessKeySecret())) {
                eVar.a("100003");
                eVar.b("未设置secretkey");
                eVar.a(false);
                return eVar;
            }
            try {
                this.f10568e.setRequestMethod("POST");
                this.f10565b = 0;
                String strA = this.f10568e.getBaseUrl().startsWith(DefaultWebClient.HTTPS_SCHEME) ? a.a(this.f10568e, this.f10566c, this.f10567d) : a.a(this.f10568e, this.f10566c, this.f10567d, this.f10565b);
                if (TextUtils.isEmpty(strA) || StrPool.EMPTY_JSON.equals(strA)) {
                    eVar.a("100007");
                    eVar.b("数据返回错误");
                    eVar.a(false);
                } else {
                    eVar.a("100000");
                    eVar.b("请求成功");
                    eVar.a(true);
                    eVar.c(strA);
                }
                return eVar;
            } catch (IOException e2) {
                e2.printStackTrace();
                eVar.a("100006");
                eVar.b(e2.getLocalizedMessage());
                eVar.a(false);
                return eVar;
            }
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
            eVar.a("100001");
            eVar.b("host 为空");
            eVar.a(false);
            return eVar;
        }
        ExceptionProcessor.processException(th);
        return null;
    }
}
