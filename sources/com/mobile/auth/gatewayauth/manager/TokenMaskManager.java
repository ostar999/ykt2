package com.mobile.auth.gatewayauth.manager;

import android.text.TextUtils;
import android.util.LruCache;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.Cache;
import com.mobile.auth.gatewayauth.manager.base.CacheKey;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.utils.TokenGenerator;
import com.mobile.auth.gatewayauth.utils.g;
import com.nirvana.tools.cache.CacheHandler;
import com.nirvana.tools.cache.CacheManager;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class TokenMaskManager {

    /* renamed from: a, reason: collision with root package name */
    private b f10080a;

    /* renamed from: b, reason: collision with root package name */
    private SystemManager f10081b;

    /* renamed from: c, reason: collision with root package name */
    private d f10082c;

    /* renamed from: d, reason: collision with root package name */
    private VendorSdkInfoManager f10083d;

    /* renamed from: e, reason: collision with root package name */
    private e f10084e;

    /* renamed from: f, reason: collision with root package name */
    private TokenGenerator f10085f;

    /* renamed from: g, reason: collision with root package name */
    private com.mobile.auth.o.a f10086g;

    /* renamed from: h, reason: collision with root package name */
    private volatile Map<String, LoginPhoneInfo> f10087h = new ConcurrentHashMap();

    /* renamed from: i, reason: collision with root package name */
    private volatile Cache<LoginPhoneInfo> f10088i = null;

    /* renamed from: j, reason: collision with root package name */
    private volatile LruCache<String, Cache<String>> f10089j = new LruCache<>(10);

    /* renamed from: k, reason: collision with root package name */
    private volatile LruCache<String, Cache<String>> f10090k = new LruCache<>(10);

    /* renamed from: l, reason: collision with root package name */
    private CacheHandler f10091l;

    /* renamed from: m, reason: collision with root package name */
    private CacheHandler f10092m;

    /* renamed from: n, reason: collision with root package name */
    private CacheHandler f10093n;

    /* renamed from: o, reason: collision with root package name */
    private CacheHandler f10094o;

    /* renamed from: p, reason: collision with root package name */
    private CacheManager f10095p;

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$10, reason: invalid class name */
    public class AnonymousClass10 implements Runnable {
        public AnonymousClass10() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TokenMaskManager.g(TokenMaskManager.this).save(TokenMaskManager.f(TokenMaskManager.this).encryptContent(TokenMaskManager.e(TokenMaskManager.this).toJson().toString()));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$11, reason: invalid class name */
    public class AnonymousClass11 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ JSONObject f10098a;

        public AnonymousClass11(JSONObject jSONObject) {
            this.f10098a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TokenMaskManager.h(TokenMaskManager.this).save(TokenMaskManager.f(TokenMaskManager.this).encryptContent(this.f10098a.toString()));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$12, reason: invalid class name */
    public class AnonymousClass12 implements RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> {
        public AnonymousClass12() {
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                com.mobile.auth.o.a aVarD = TokenMaskManager.d(TokenMaskManager.this);
                String[] strArr = new String[2];
                strArr[0] = "Update LoginToken failed when update mask!";
                strArr[1] = bVar == null ? "" : bVar.toString();
                aVarD.e(strArr);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void a(String str) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken success when update mask!");
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                a(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(String str) {
            try {
                a(str);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$13, reason: invalid class name */
    public class AnonymousClass13 implements RequestCallback<com.mobile.auth.u.b, com.mobile.auth.gatewayauth.manager.base.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ CacheKey f10101a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10102b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10103c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ String f10104d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ CacheKey f10105e;

        public AnonymousClass13(CacheKey cacheKey, String str, RequestCallback requestCallback, String str2, CacheKey cacheKey2) {
            this.f10101a = cacheKey;
            this.f10102b = str;
            this.f10103c = requestCallback;
            this.f10104d = str2;
            this.f10105e = cacheKey2;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            if (bVar == null) {
                try {
                    bVar = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return;
                    }
                }
            }
            this.f10103c.onError(bVar);
        }

        public void a(com.mobile.auth.u.b bVar) {
            try {
                LoginPhoneInfo loginPhoneInfoH = bVar.b().h();
                TokenMaskManager.a(TokenMaskManager.this, this.f10101a, loginPhoneInfoH, this.f10102b);
                this.f10103c.onSuccess(com.mobile.auth.gatewayauth.manager.base.b.a().a(false).a(loginPhoneInfoH).a());
                bVar.b().a(Math.min(System.currentTimeMillis() + 86400000, bVar.b().f()));
                if (TextUtils.isEmpty(bVar.b().d())) {
                    return;
                }
                TokenMaskManager.a(TokenMaskManager.this, this.f10104d, this.f10102b, this.f10105e, bVar.b().d(), bVar.b().f(), loginPhoneInfoH);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                a(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.u.b bVar) {
            try {
                a(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$14, reason: invalid class name */
    public class AnonymousClass14 extends Callback<com.mobile.auth.u.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10107a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass14(ThreadStrategy threadStrategy, long j2, RequestCallback requestCallback) {
            super(threadStrategy, j2);
            this.f10107a = requestCallback;
        }

        public void a(com.mobile.auth.u.b bVar) {
            try {
                if (bVar.a()) {
                    this.f10107a.onSuccess(bVar);
                    return;
                }
                com.mobile.auth.gatewayauth.manager.base.b bVarB = bVar.b();
                if (bVarB == null) {
                    bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                }
                this.f10107a.onError(bVarB);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.b) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$15, reason: invalid class name */
    public class AnonymousClass15 extends com.mobile.auth.p.b {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10109a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10110b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(e eVar, String str, long j2, String str2, String str3, String str4) {
            super(eVar, str, j2, str2);
            this.f10109a = str3;
            this.f10110b = str4;
        }

        @Override // com.mobile.auth.p.b
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.a((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.f10109a).b(this.f10110b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$16, reason: invalid class name */
    public class AnonymousClass16 extends Callback<com.mobile.auth.u.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10112a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10113b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ CacheKey f10114c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ LoginPhoneInfo f10115d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10116e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass16(ThreadStrategy threadStrategy, long j2, String str, String str2, CacheKey cacheKey, LoginPhoneInfo loginPhoneInfo, RequestCallback requestCallback) {
            super(threadStrategy, j2);
            this.f10112a = str;
            this.f10113b = str2;
            this.f10114c = cacheKey;
            this.f10115d = loginPhoneInfo;
            this.f10116e = requestCallback;
        }

        public void a(com.mobile.auth.u.b bVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken from network!");
                if (bVar.a()) {
                    bVar.b().a(Math.min(System.currentTimeMillis() + 86400000, bVar.b().f()));
                    TokenMaskManager.a(TokenMaskManager.this, this.f10112a, this.f10113b, this.f10114c, bVar.b().d(), bVar.b().f(), this.f10115d);
                    this.f10116e.onSuccess(k.a.f27524v);
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = bVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.f10116e.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.b) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$17, reason: invalid class name */
    public class AnonymousClass17 extends com.mobile.auth.p.b {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10118a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10119b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass17(e eVar, String str, long j2, String str2, String str3, String str4) {
            super(eVar, str, j2, str2);
            this.f10118a = str3;
            this.f10119b = str4;
        }

        @Override // com.mobile.auth.p.b
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.b((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.f10118a).b(this.f10119b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$18, reason: invalid class name */
    public class AnonymousClass18 extends Callback<com.mobile.auth.u.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10121a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10122b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass18(ThreadStrategy threadStrategy, long j2, String str, RequestCallback requestCallback) {
            super(threadStrategy, j2);
            this.f10121a = str;
            this.f10122b = requestCallback;
        }

        public void a(com.mobile.auth.u.b bVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken from network!");
                if (bVar.a()) {
                    bVar.b().a(TokenMaskManager.a(TokenMaskManager.this, bVar.b().d(), this.f10121a, true, bVar.b().h()));
                    this.f10122b.onSuccess(bVar.b());
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = bVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.f10122b.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.b) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$19, reason: invalid class name */
    public class AnonymousClass19 extends com.mobile.auth.p.b {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10124a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10125b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass19(e eVar, String str, long j2, String str2, String str3, String str4) {
            super(eVar, str, j2, str2);
            this.f10124a = str3;
            this.f10125b = str4;
        }

        @Override // com.mobile.auth.p.b
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.b((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.f10124a).b(this.f10125b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$2, reason: invalid class name */
    public class AnonymousClass2 implements Comparator<Integer> {
        public AnonymousClass2() {
        }

        public int a(Integer num, Integer num2) {
            try {
                return num.intValue() < num2.intValue() ? -1 : 1;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }

        @Override // java.util.Comparator
        public /* synthetic */ int compare(Integer num, Integer num2) {
            try {
                return a(num, num2);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$3, reason: invalid class name */
    public class AnonymousClass3 implements Comparator<Integer> {
        public AnonymousClass3() {
        }

        public int a(Integer num, Integer num2) {
            try {
                return num.intValue() < num2.intValue() ? -1 : 1;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }

        @Override // java.util.Comparator
        public /* synthetic */ int compare(Integer num, Integer num2) {
            try {
                return a(num, num2);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$4, reason: invalid class name */
    public class AnonymousClass4 implements Comparator<Integer> {
        public AnonymousClass4() {
        }

        public int a(Integer num, Integer num2) {
            try {
                return num.intValue() < num2.intValue() ? -1 : 1;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }

        @Override // java.util.Comparator
        public /* synthetic */ int compare(Integer num, Integer num2) {
            try {
                return a(num, num2);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$5, reason: invalid class name */
    public class AnonymousClass5 extends Callback<com.mobile.auth.u.c> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10130a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10131b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(ThreadStrategy threadStrategy, long j2, String str, RequestCallback requestCallback) {
            super(threadStrategy, j2);
            this.f10130a = str;
            this.f10131b = requestCallback;
        }

        public void a(com.mobile.auth.u.c cVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update VerifyToken from network!");
                if (cVar.a()) {
                    cVar.b().a(TokenMaskManager.a(TokenMaskManager.this, cVar.b().d(), this.f10130a, false, cVar.b().h()));
                    this.f10131b.onSuccess(cVar.b());
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = cVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.f10131b.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.c) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$6, reason: invalid class name */
    public class AnonymousClass6 extends Callback<com.mobile.auth.u.c> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f10133a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f10134b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ CacheKey f10135c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ RequestCallback f10136d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(ThreadStrategy threadStrategy, long j2, String str, String str2, CacheKey cacheKey, RequestCallback requestCallback) {
            super(threadStrategy, j2);
            this.f10133a = str;
            this.f10134b = str2;
            this.f10135c = cacheKey;
            this.f10136d = requestCallback;
        }

        public void a(com.mobile.auth.u.c cVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update VerifyToken from network!");
                if (cVar.a()) {
                    cVar.b().a(Math.min(System.currentTimeMillis() + 86400000, cVar.b().f()));
                    TokenMaskManager.b(TokenMaskManager.this, this.f10133a, this.f10134b, this.f10135c, cVar.b().d(), cVar.b().f(), cVar.b().h());
                    this.f10136d.onSuccess(k.a.f27524v);
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = cVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.f10136d.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.c) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$7, reason: invalid class name */
    public class AnonymousClass7 extends JsonType<LoginPhoneInfo> {
        public AnonymousClass7() {
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$8, reason: invalid class name */
    public class AnonymousClass8 extends JsonType<String> {
        public AnonymousClass8() {
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$9, reason: invalid class name */
    public class AnonymousClass9 extends JsonType<String> {
        public AnonymousClass9() {
        }
    }

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public TokenMaskManager(b bVar, SystemManager systemManager, d dVar, e eVar, VendorSdkInfoManager vendorSdkInfoManager) {
        this.f10080a = bVar;
        this.f10081b = systemManager;
        this.f10082c = dVar;
        this.f10086g = dVar.a();
        this.f10083d = vendorSdkInfoManager;
        this.f10084e = eVar;
        this.f10085f = new TokenGenerator(this.f10086g, this.f10081b, this.f10083d);
        this.f10095p = CacheManager.getInstance(this.f10081b.e());
        b();
        g.a(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.TokenMaskManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    TokenMaskManager.a(TokenMaskManager.this);
                    TokenMaskManager.b(TokenMaskManager.this);
                    TokenMaskManager.c(TokenMaskManager.this);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }
        });
    }

    public static /* synthetic */ String a(TokenMaskManager tokenMaskManager, String str, String str2, boolean z2, LoginPhoneInfo loginPhoneInfo) {
        try {
            return tokenMaskManager.a(str, str2, z2, loginPhoneInfo);
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

    private native String a(String str, CacheKey cacheKey, long j2);

    private native String a(String str, String str2, CacheKey cacheKey, long j2);

    private native String a(String str, String str2, boolean z2, LoginPhoneInfo loginPhoneInfo);

    public static /* synthetic */ void a(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(TokenMaskManager tokenMaskManager, CacheKey cacheKey, LoginPhoneInfo loginPhoneInfo, String str) {
        try {
            tokenMaskManager.a(cacheKey, loginPhoneInfo, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(TokenMaskManager tokenMaskManager, String str, String str2, CacheKey cacheKey, String str3, long j2, LoginPhoneInfo loginPhoneInfo) {
        try {
            tokenMaskManager.b(str, str2, cacheKey, str3, j2, loginPhoneInfo);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private native synchronized void a(CacheKey cacheKey, LoginPhoneInfo loginPhoneInfo, String str);

    private native void a(String str, int i2);

    private native synchronized void a(String str, String str2, CacheKey cacheKey, String str3, long j2, LoginPhoneInfo loginPhoneInfo);

    private native boolean a(String str, String str2, long j2);

    private native synchronized boolean a(String str, String str2, LruCache<String, Cache<String>> lruCache, long j2);

    private native void b();

    public static /* synthetic */ void b(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void b(TokenMaskManager tokenMaskManager, String str, String str2, CacheKey cacheKey, String str3, long j2, LoginPhoneInfo loginPhoneInfo) {
        try {
            tokenMaskManager.a(str, str2, cacheKey, str3, j2, loginPhoneInfo);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private native synchronized void b(String str, String str2, CacheKey cacheKey, String str3, long j2, LoginPhoneInfo loginPhoneInfo);

    private native boolean b(CacheKey cacheKey);

    private native boolean b(String str, String str2, long j2);

    private native synchronized void c();

    public static /* synthetic */ void c(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.e();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a d(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.f10086g;
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

    private native synchronized void d();

    public static /* synthetic */ Cache e(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.f10088i;
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

    private native synchronized void e();

    public static /* synthetic */ SystemManager f(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.f10081b;
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

    private native synchronized void f();

    public static /* synthetic */ CacheHandler g(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.f10094o;
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

    private native synchronized void g();

    public static /* synthetic */ CacheHandler h(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.f10093n;
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

    private native synchronized void h();

    @SafeProtector
    private native synchronized String popToken(String str, CacheKey cacheKey, CacheHandler cacheHandler, LruCache<String, Cache<String>> lruCache, long j2);

    @SafeProtector
    private native void requestMask(long j2, String str, RequestCallback<com.mobile.auth.u.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, int i2, String str3, String str4);

    public native LoginPhoneInfo a(CacheKey cacheKey);

    public native synchronized void a();

    public native void a(long j2, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, long j3, String str2, String str3, String str4);

    public native void a(long j2, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, long j3, int i2, String str3, String str4);

    public native void a(long j2, String str, RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, long j3, int i2, String str3, String str4, LoginPhoneInfo loginPhoneInfo);

    public native void b(long j2, String str, RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, long j3, String str2, String str3, String str4);

    @SafeProtector
    public native void updateMask(long j2, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, int i2, String str2, String str3, String str4);
}
