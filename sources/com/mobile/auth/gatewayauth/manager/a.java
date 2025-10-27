package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.detectnet.f;
import com.mobile.auth.gatewayauth.manager.base.b;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.nirvana.tools.core.ExecutorManager;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    protected volatile String f10153a;

    /* renamed from: b, reason: collision with root package name */
    protected volatile String f10154b;

    /* renamed from: c, reason: collision with root package name */
    protected volatile long f10155c = 5000;

    /* renamed from: d, reason: collision with root package name */
    protected Context f10156d;

    /* renamed from: e, reason: collision with root package name */
    protected d f10157e;

    /* renamed from: f, reason: collision with root package name */
    protected String f10158f;

    /* renamed from: g, reason: collision with root package name */
    protected String f10159g;

    /* renamed from: h, reason: collision with root package name */
    protected com.mobile.auth.o.a f10160h;

    /* renamed from: com.mobile.auth.gatewayauth.manager.a$a, reason: collision with other inner class name */
    public static class C0199a {

        /* renamed from: a, reason: collision with root package name */
        private String f10172a;

        /* renamed from: b, reason: collision with root package name */
        private String f10173b;

        /* renamed from: c, reason: collision with root package name */
        private long f10174c;

        /* renamed from: d, reason: collision with root package name */
        private String f10175d;

        /* renamed from: e, reason: collision with root package name */
        private String f10176e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f10177f;

        /* renamed from: g, reason: collision with root package name */
        private String f10178g;

        /* renamed from: h, reason: collision with root package name */
        private String f10179h;

        /* renamed from: com.mobile.auth.gatewayauth.manager.a$a$a, reason: collision with other inner class name */
        public static final class C0200a {

            /* renamed from: a, reason: collision with root package name */
            private String f10180a;

            /* renamed from: b, reason: collision with root package name */
            private String f10181b;

            /* renamed from: c, reason: collision with root package name */
            private long f10182c;

            /* renamed from: d, reason: collision with root package name */
            private String f10183d;

            /* renamed from: e, reason: collision with root package name */
            private String f10184e;

            /* renamed from: f, reason: collision with root package name */
            private boolean f10185f;

            /* renamed from: g, reason: collision with root package name */
            private String f10186g;

            /* renamed from: h, reason: collision with root package name */
            private String f10187h;

            private C0200a() {
            }

            public static /* synthetic */ String a(C0200a c0200a) {
                try {
                    return c0200a.f10180a;
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

            public static /* synthetic */ String b(C0200a c0200a) {
                try {
                    return c0200a.f10181b;
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

            public static /* synthetic */ long c(C0200a c0200a) {
                try {
                    return c0200a.f10182c;
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

            public static /* synthetic */ String d(C0200a c0200a) {
                try {
                    return c0200a.f10183d;
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

            public static /* synthetic */ String e(C0200a c0200a) {
                try {
                    return c0200a.f10184e;
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

            public static /* synthetic */ boolean f(C0200a c0200a) {
                try {
                    return c0200a.f10185f;
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

            public static /* synthetic */ String g(C0200a c0200a) {
                try {
                    return c0200a.f10186g;
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

            public static /* synthetic */ String h(C0200a c0200a) {
                try {
                    return c0200a.f10187h;
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

            public C0200a a(long j2) {
                try {
                    this.f10182c = j2;
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

            public C0200a a(String str) {
                try {
                    this.f10180a = str;
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

            public C0200a a(boolean z2) {
                try {
                    this.f10185f = z2;
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

            public C0199a a() {
                try {
                    return new C0199a(this);
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

            public C0200a b(String str) {
                try {
                    this.f10181b = str;
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

            public C0200a c(String str) {
                try {
                    this.f10183d = str;
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

            public C0200a d(String str) {
                try {
                    this.f10184e = str;
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

            public C0200a e(String str) {
                try {
                    this.f10186g = str;
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
        }

        private C0199a(C0200a c0200a) {
            this.f10172a = C0200a.a(c0200a);
            this.f10173b = C0200a.b(c0200a);
            this.f10174c = C0200a.c(c0200a);
            this.f10175d = C0200a.d(c0200a);
            this.f10176e = C0200a.e(c0200a);
            this.f10177f = C0200a.f(c0200a);
            this.f10178g = C0200a.g(c0200a);
            this.f10179h = C0200a.h(c0200a);
        }

        public static C0200a a() {
            try {
                return new C0200a();
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

        public String b() {
            try {
                return this.f10172a;
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

        public String c() {
            try {
                return this.f10173b;
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

        public long d() {
            try {
                return this.f10174c;
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

        public String e() {
            try {
                return this.f10175d;
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

        public String f() {
            try {
                return this.f10176e;
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

        public boolean g() {
            try {
                return this.f10177f;
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

        public String h() {
            try {
                return this.f10178g;
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

        public String i() {
            try {
                return this.f10179h;
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

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        String f10188a;

        /* renamed from: b, reason: collision with root package name */
        String f10189b;

        /* renamed from: com.mobile.auth.gatewayauth.manager.a$b$a, reason: collision with other inner class name */
        public static final class C0201a {

            /* renamed from: a, reason: collision with root package name */
            private String f10190a;

            /* renamed from: b, reason: collision with root package name */
            private String f10191b;

            private C0201a() {
            }

            public static /* synthetic */ String a(C0201a c0201a) {
                try {
                    return c0201a.f10190a;
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

            public static /* synthetic */ String b(C0201a c0201a) {
                try {
                    return c0201a.f10191b;
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

            public C0201a a(String str) {
                try {
                    this.f10190a = str;
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

            public b a() {
                try {
                    return new b(this);
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

            public C0201a b(String str) {
                try {
                    this.f10191b = str;
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
        }

        private b(C0201a c0201a) {
            this.f10188a = C0201a.a(c0201a);
            this.f10189b = C0201a.b(c0201a);
        }

        public static C0201a a() {
            try {
                return new C0201a();
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

        public String b() {
            try {
                return this.f10188a;
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

        public String c() {
            try {
                return this.f10189b;
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

    public a(Context context, d dVar, String str, String str2) {
        this.f10156d = context;
        this.f10157e = dVar;
        this.f10158f = str;
        this.f10159g = str2;
        this.f10160h = dVar.a();
    }

    public static /* synthetic */ void a(a aVar, String str, String str2, String str3) {
        try {
            aVar.a(str, str2, str3);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, String str2, String str3) {
        try {
            f.a(str, str2, str3, this.f10156d, this.f10159g, this.f10158f);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String a() {
        try {
            return this.f10153a;
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

    public void a(long j2) {
        try {
            if (j2 >= 5000) {
                this.f10155c = j2;
            } else {
                this.f10155c = 5000L;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public final synchronized void a(final RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } finally {
            return;
        }
        if (!TextUtils.isEmpty(this.f10153a) && !TextUtils.isEmpty(this.f10154b)) {
            d(new RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.1
                public void a(C0199a c0199a) {
                    try {
                        requestCallback.onSuccess(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a aVar = a.this;
                        String strB = bVar2.b();
                        b bVar3 = bVar;
                        a.a(aVar, strB, bVar3.f10188a, bVar3.f10189b);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0199a c0199a) {
                    try {
                        a(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    public void a(RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            a(str, str2, str3, false, str4, monitorStruct);
            LoginPhoneInfo loginPhoneInfoBuild = LoginPhoneInfo.newLoginPhoneInfo().build();
            if (Constant.VENDOR_NTCM.equals(str4)) {
                loginPhoneInfoBuild.setVendor(str4);
            }
            b.a aVarA = com.mobile.auth.gatewayauth.manager.base.b.a().d(com.mobile.auth.gatewayauth.utils.a.a(str, str2)).a(b(str, str5));
            if (!Constant.VENDOR_NTCM.equals(str4)) {
                loginPhoneInfoBuild = null;
            }
            requestCallback.onError(aVarA.a(loginPhoneInfoBuild).b(str2).c(str3).a());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str, String str2) {
        try {
            this.f10153a = str;
            this.f10154b = str2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str, String str2, String str3, boolean z2, String str4, final MonitorStruct monitorStruct) {
        if (monitorStruct != null) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                monitorStruct.setCarrierSdkCode(str);
                monitorStruct.setSuccess(z2);
                monitorStruct.setEndTime(jCurrentTimeMillis);
                if (!z2) {
                    monitorStruct.setCarrierSdkMsg(str2);
                    monitorStruct.setFailRet(com.mobile.auth.gatewayauth.utils.a.a(str, str2));
                    monitorStruct.setCarrierFailedResultData(str3);
                }
                monitorStruct.setUrgency(1);
                monitorStruct.setVendorKey(str4);
                if (monitorStruct.getAction().indexOf("logintoken") > -1 || monitorStruct.getAction().indexOf("getoken") > -1 || monitorStruct.getAction().indexOf("logincode") > -1) {
                    com.mobile.auth.gatewayauth.utils.c.g(this.f10156d);
                }
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.a.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            monitorStruct.setPrivateIp(com.mobile.auth.gatewayauth.utils.c.h(a.this.f10156d));
                            a aVar = a.this;
                            aVar.f10160h.a(aVar.f10157e.a(monitorStruct), monitorStruct.getUrgency());
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public abstract void a(boolean z2);

    public String b() {
        try {
            return this.f10154b;
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

    public abstract String b(String str, String str2);

    public final synchronized void b(final RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } finally {
            return;
        }
        if (!TextUtils.isEmpty(this.f10153a) && !TextUtils.isEmpty(this.f10154b)) {
            e(new RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.2
                public void a(C0199a c0199a) {
                    try {
                        requestCallback.onSuccess(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a aVar = a.this;
                        String strB = bVar2.b();
                        b bVar3 = bVar;
                        a.a(aVar, strB, bVar3.f10188a, bVar3.f10189b);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0199a c0199a) {
                    try {
                        a(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    public abstract void c();

    public final synchronized void c(final RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } finally {
            return;
        }
        if (!TextUtils.isEmpty(this.f10153a) && !TextUtils.isEmpty(this.f10154b)) {
            f(new RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.3
                public void a(C0199a c0199a) {
                    try {
                        requestCallback.onSuccess(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a aVar = a.this;
                        String strB = bVar2.b();
                        b bVar3 = bVar;
                        a.a(aVar, strB, bVar3.f10188a, bVar3.f10189b);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0199a c0199a) {
                    try {
                        a(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    public abstract void d(RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);

    public abstract void e(RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);

    public abstract void f(RequestCallback<C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);
}
