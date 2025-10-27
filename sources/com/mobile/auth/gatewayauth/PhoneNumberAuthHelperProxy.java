package com.mobile.auth.gatewayauth;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.google.android.exoplayer2.C;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.TokenMaskManager;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.network.DispatchInfoItem;
import com.mobile.auth.gatewayauth.network.DispatchInfoRespone;
import com.mobile.auth.gatewayauth.network.PrivateKeyRespone;
import com.mobile.auth.gatewayauth.network.PrivateRespone;
import com.mobile.auth.gatewayauth.network.RequestState;
import com.mobile.auth.gatewayauth.network.RequestUtil;
import com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper;
import com.mobile.auth.gatewayauth.utils.EncryptUtils;
import com.mobile.auth.gatewayauth.utils.g;
import com.nirvana.tools.core.ComponentSdkCore;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.logger.UaidTracker;
import com.plv.foundationsdk.web.PLVWebview;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class PhoneNumberAuthHelperProxy {

    @AuthNumber
    public static final int SERVICE_TYPE_AUTH = 1;

    @AuthNumber
    public static final int SERVICE_TYPE_LOGIN = 2;

    /* renamed from: a, reason: collision with root package name */
    protected static volatile PhoneNumberAuthHelperProxy f9797a;

    /* renamed from: b, reason: collision with root package name */
    private TokenResultListener f9798b;

    /* renamed from: c, reason: collision with root package name */
    private SystemManager f9799c;

    /* renamed from: d, reason: collision with root package name */
    private com.mobile.auth.gatewayauth.manager.b f9800d;

    /* renamed from: e, reason: collision with root package name */
    private VendorSdkInfoManager f9801e;

    /* renamed from: f, reason: collision with root package name */
    private TokenMaskManager f9802f;

    /* renamed from: g, reason: collision with root package name */
    private com.mobile.auth.gatewayauth.manager.d f9803g;

    /* renamed from: h, reason: collision with root package name */
    private com.mobile.auth.gatewayauth.manager.e f9804h;

    /* renamed from: i, reason: collision with root package name */
    private Future<?> f9805i;

    /* renamed from: j, reason: collision with root package name */
    private com.mobile.auth.o.a f9806j;

    /* renamed from: k, reason: collision with root package name */
    private String f9807k;

    /* renamed from: l, reason: collision with root package name */
    private final ResultCodeProcessor f9808l = new com.mobile.auth.gatewayauth.manager.compat.a();

    /* renamed from: m, reason: collision with root package name */
    private volatile String f9809m;

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$10, reason: invalid class name */
    public class AnonymousClass10 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f9811a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ TokenResultListener f9812b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ ResultCodeProcessor f9813c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ MonitorStruct f9814d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ String f9815e;

        public AnonymousClass10(String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2) {
            this.f9811a = str;
            this.f9812b = tokenResultListener;
            this.f9813c = resultCodeProcessor;
            this.f9814d = monitorStruct;
            this.f9815e = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), this.f9811a, this.f9812b, this.f9813c, this.f9814d, this.f9815e);
                PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("justGetLoginToken Timeout!");
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$11, reason: invalid class name */
    public class AnonymousClass11 implements RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ c f9817a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ MonitorStruct f9818b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f9819c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ TokenResultListener f9820d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ ResultCodeProcessor f9821e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ String f9822f;

        public AnonymousClass11(c cVar, MonitorStruct monitorStruct, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2) {
            this.f9817a = cVar;
            this.f9818b = monitorStruct;
            this.f9819c = str;
            this.f9820d = tokenResultListener;
            this.f9821e = resultCodeProcessor;
            this.f9822f = str2;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9817a.d()) {
                    this.f9818b.setCache(String.valueOf(bVar.e()));
                    if (bVar.h() != null && !TextUtils.isEmpty(bVar.h().getVendor())) {
                        this.f9818b.setAction(Constant.ACTION_SDK_CMNT_LOGIN_TOKEN);
                        this.f9818b.setVendorKey(bVar.h().getVendor());
                    }
                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, this.f9819c, this.f9820d, this.f9821e, bVar.d(), this.f9818b, this.f9822f);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9817a.d()) {
                    this.f9818b.setCache(k.a.f27524v);
                    this.f9818b.setCarrierFailedResultData(bVar.d());
                    if (bVar.h() != null && !TextUtils.isEmpty(bVar.h().getVendor())) {
                        this.f9818b.setAction(Constant.ACTION_SDK_CMNT_LOGIN_TOKEN);
                        this.f9818b.setVendorKey(bVar.h().getVendor());
                    }
                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), this.f9819c, this.f9820d, this.f9821e, this.f9818b, this.f9822f);
                }
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
                b(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$13, reason: invalid class name */
    public class AnonymousClass13 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f9826a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ TokenResultListener f9827b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ ResultCodeProcessor f9828c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ MonitorStruct f9829d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ String f9830e;

        public AnonymousClass13(String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2) {
            this.f9826a = str;
            this.f9827b = tokenResultListener;
            this.f9828c = resultCodeProcessor;
            this.f9829d = monitorStruct;
            this.f9830e = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), this.f9826a, this.f9827b, this.f9828c, this.f9829d, this.f9830e);
                PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("justGetToken Timeout!");
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$14, reason: invalid class name */
    public class AnonymousClass14 implements RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ c f9832a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ MonitorStruct f9833b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f9834c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ ResultCodeProcessor f9835d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ TokenResultListener f9836e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ String f9837f;

        public AnonymousClass14(c cVar, MonitorStruct monitorStruct, String str, ResultCodeProcessor resultCodeProcessor, TokenResultListener tokenResultListener, String str2) {
            this.f9832a = cVar;
            this.f9833b = monitorStruct;
            this.f9834c = str;
            this.f9835d = resultCodeProcessor;
            this.f9836e = tokenResultListener;
            this.f9837f = str2;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9832a.d()) {
                    this.f9833b.setCache(String.valueOf(bVar.e()));
                    if (bVar.h() != null && !TextUtils.isEmpty(bVar.h().getVendor())) {
                        this.f9833b.setAction(Constant.ACTION_SDK_CMNT_AUTH_TOKEN);
                        this.f9833b.setVendorKey(bVar.h().getVendor());
                    }
                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, this.f9834c, this.f9835d, this.f9833b, bVar.d(), this.f9836e, this.f9837f);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9832a.d()) {
                    this.f9833b.setCache(k.a.f27524v);
                    this.f9833b.setCarrierFailedResultData(bVar.d());
                    if (bVar.h() != null && !TextUtils.isEmpty(bVar.h().getVendor())) {
                        this.f9833b.setAction(Constant.ACTION_SDK_CMNT_AUTH_TOKEN);
                        this.f9833b.setVendorKey(bVar.h().getVendor());
                    }
                    PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), this.f9834c, this.f9836e, this.f9835d, this.f9833b, this.f9837f);
                }
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
                b(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$22, reason: invalid class name */
    public class AnonymousClass22 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ RequestCallback f9874a;

        public AnonymousClass22(RequestCallback requestCallback) {
            this.f9874a = requestCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.f9874a.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$24, reason: invalid class name */
    public class AnonymousClass24 implements RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ c f9877a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ MonitorStruct f9878b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ ResultCodeProcessor f9879c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ RequestCallback f9880d;

        public AnonymousClass24(c cVar, MonitorStruct monitorStruct, ResultCodeProcessor resultCodeProcessor, RequestCallback requestCallback) {
            this.f9877a = cVar;
            this.f9878b = monitorStruct;
            this.f9879c = resultCodeProcessor;
            this.f9880d = requestCallback;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9877a.d()) {
                    this.f9878b.setCache(String.valueOf(bVar.e()));
                    LoginPhoneInfo loginPhoneInfoH = bVar.h();
                    this.f9878b.setPhoneNumber(loginPhoneInfoH.getPhoneNumber());
                    PhoneNumberAuthHelperProxy.this.a(loginPhoneInfoH.getPhoneNumber());
                    if (com.mobile.auth.gatewayauth.utils.d.a().g()) {
                        this.f9878b.setAction(Constant.ACTION_SDK_CMNT_LOGIN_CODE);
                        this.f9878b.setVendorKey(bVar.h().getVendor());
                    }
                    this.f9878b.setAuthSdkCode(this.f9879c.convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                    this.f9880d.onSuccess(loginPhoneInfoH);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.f9877a.d()) {
                    this.f9878b.setCache(k.a.f27524v);
                    if (com.mobile.auth.gatewayauth.utils.d.a().g()) {
                        this.f9878b.setAction(Constant.ACTION_SDK_CMNT_LOGIN_CODE);
                        this.f9878b.setVendorKey(bVar.h().getVendor());
                    }
                    this.f9878b.setCarrierFailedResultData(bVar.d());
                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("justGetLoginPhone failed!", bVar.i());
                    this.f9880d.onError(bVar);
                }
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
                b(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        f9797a = null;
    }

    private PhoneNumberAuthHelperProxy(Context context, TokenResultListener tokenResultListener) {
        this.f9798b = tokenResultListener;
        ComponentSdkCore.register(context.getApplicationContext());
        a(context.getApplicationContext());
    }

    public static /* synthetic */ SystemManager a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9799c;
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

    private void a(Context context) {
        try {
            com.mobile.auth.gatewayauth.manager.d dVar = new com.mobile.auth.gatewayauth.manager.d(context);
            this.f9803g = dVar;
            this.f9806j = dVar.a();
            this.f9799c = new SystemManager(context, this.f9806j);
            VendorSdkInfoManager vendorSdkInfoManager = new VendorSdkInfoManager(this.f9803g, this.f9799c);
            this.f9801e = vendorSdkInfoManager;
            this.f9800d = new com.mobile.auth.gatewayauth.manager.b(context, vendorSdkInfoManager, this.f9803g);
            com.mobile.auth.gatewayauth.manager.e eVar = new com.mobile.auth.gatewayauth.manager.e(this.f9799c, this.f9803g);
            this.f9804h = eVar;
            this.f9803g.a(eVar);
            this.f9802f = new TokenMaskManager(this.f9800d, this.f9799c, this.f9803g, this.f9804h, this.f9801e);
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).getSimCacheKey(false, null);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            if (c(this.f9799c.e())) {
                this.f9805i = f();
            }
            d();
            UaidTracker.getInstance().setKey(EncryptUtils.getSecret5().substring(4, 10) + EncryptUtils.getSecret6().substring(1, 7), EncryptUtils.getSecret5().substring(15) + EncryptUtils.getSecret6().substring(38, 54), EncryptUtils.getSecret6().substring(70, 76) + EncryptUtils.getSecret6().substring(86, 92));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, int i2, ResultCodeProcessor resultCodeProcessor, boolean z2, TokenResultListener tokenResultListener) {
        try {
            phoneNumberAuthHelperProxy.justGetToken(i2, resultCodeProcessor, z2, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j2, PreLoginResultListener preLoginResultListener) {
        try {
            phoneNumberAuthHelperProxy.justPreVerify(j2, preLoginResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j2, PreLoginResultListener preLoginResultListener, ResultCodeProcessor resultCodeProcessor, boolean z2, boolean z3) {
        try {
            phoneNumberAuthHelperProxy.justPreLogin(j2, preLoginResultListener, resultCodeProcessor, z2, z3);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j2, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor) {
        try {
            phoneNumberAuthHelperProxy.justGetLoginToken(j2, tokenResultListener, resultCodeProcessor);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, Context context) {
        try {
            phoneNumberAuthHelperProxy.d(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            phoneNumberAuthHelperProxy.a(str, str2, str3, str4, tokenResultListener, resultCodeProcessor, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z2, boolean z3, String str, String str2, MonitorStruct monitorStruct, TokenResultListener tokenResultListener) {
        try {
            phoneNumberAuthHelperProxy.a(z2, z3, str, str2, monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            this.f9803g.h();
            if (monitorStruct.getAction().equals(this.f9799c.c(str4))) {
                a(false, false, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            } else {
                a(false, true, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(final String str, final boolean z2, final MonitorStruct monitorStruct, final boolean z3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.29
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (monitorStruct != null) {
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            monitorStruct.setSuccess(z2);
                            monitorStruct.setEndTime(jCurrentTimeMillis);
                            if (!z2) {
                                monitorStruct.setFailRet(str);
                            }
                            PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).a(monitorStruct), monitorStruct.getUrgency());
                        }
                        if (z3) {
                            PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).b();
                        }
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

    private void a(final boolean z2, final String str, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.28
                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onException(Throwable th) {
                    try {
                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("TokenResultListener callback exception!", ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void safeRun() {
                    try {
                        if (z2) {
                            tokenResultListener.onTokenSuccess(str);
                        } else {
                            tokenResultListener.onTokenFailed(str);
                        }
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

    private void a(boolean z2, boolean z3, String str, String str2, MonitorStruct monitorStruct, TokenResultListener tokenResultListener) {
        if (tokenResultListener != null) {
            try {
                a(z2, str2, tokenResultListener);
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
        if (monitorStruct != null && !Constant.ACTION_SDK_LIFE_BODY_VERIFY.equals(monitorStruct.getAction())) {
            a(str, z2, monitorStruct, z3);
        }
    }

    public static /* synthetic */ boolean a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z2, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2, MonitorStruct monitorStruct, String str3) {
        try {
            return phoneNumberAuthHelperProxy.a(z2, str, tokenResultListener, resultCodeProcessor, str2, monitorStruct, str3);
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

    public static /* synthetic */ boolean a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z2, String str, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2, TokenResultListener tokenResultListener, String str3) {
        try {
            return phoneNumberAuthHelperProxy.a(z2, str, resultCodeProcessor, monitorStruct, str2, tokenResultListener, str3);
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

    private boolean a(final String str, String str2, final String str3) {
        try {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            boolean z2 = (str3 == null || str3.equals(str2)) ? false : true;
            final long jCurrentTimeMillis2 = System.currentTimeMillis();
            final boolean z3 = z2;
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.30
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).a(str3, Constant.ACTION_SDK_CROSS_CARRIER_CHANGE, UStruct.newUStruct().isCarrierChanged(String.valueOf(z3)).requestId(PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e()).sessionId(str).startTime(jCurrentTimeMillis).endTime(jCurrentTimeMillis2).build(), ""), 2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            return z2;
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

    private boolean a(boolean z2, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2, MonitorStruct monitorStruct, String str3) {
        try {
            this.f9803g.h();
            if (!TextUtils.isEmpty(str2)) {
                a(str2, str, monitorStruct, resultCodeProcessor, tokenResultListener);
                return true;
            }
            this.f9806j.e("GetLoginToken from cache is null!");
            if (z2) {
                monitorStruct.setAuthSdkCode(Constant.CODE_ERROR_UNKNOWN_FAIL);
                a(false, true, Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常"), str, monitorStruct, tokenResultListener, resultCodeProcessor, str3);
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

    private boolean a(boolean z2, String str, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2, TokenResultListener tokenResultListener, String str3) {
        try {
            this.f9803g.i();
            if (!TextUtils.isEmpty(str2)) {
                a(str2, str, monitorStruct, resultCodeProcessor, tokenResultListener);
                return true;
            }
            this.f9806j.e("GetVerifyToken from cache is null!");
            if (z2) {
                a(false, true, Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常"), str, monitorStruct, tokenResultListener, resultCodeProcessor, str3);
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

    public static /* synthetic */ com.mobile.auth.gatewayauth.manager.b b(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9800d;
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

    public static /* synthetic */ void b(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            phoneNumberAuthHelperProxy.b(str, str2, str3, str4, tokenResultListener, resultCodeProcessor, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void b(String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            this.f9803g.i();
            if (monitorStruct.getAction().equals(this.f9799c.d(str4))) {
                a(false, false, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            } else {
                a(false, true, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private boolean b(final Context context) {
        try {
            String authSDKPrivateKey = UTSharedPreferencesHelper.readAuthSDKPrivateKey(context);
            if (TextUtils.isEmpty(authSDKPrivateKey)) {
                this.f9806j.a("local pritekey is empty");
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.12
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                return false;
            }
            try {
                PrivateKeyRespone privateKeyRespone = (PrivateKeyRespone) JSONUtils.fromJson(new JSONObject(new String(com.mobile.auth.gatewayauth.utils.security.a.a(authSDKPrivateKey))), (JsonType) new JsonType<PrivateKeyRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.23
                }, (List<Field>) null);
                if (privateKeyRespone == null) {
                    this.f9806j.a("parse local privatekey is empty");
                    RequestState.getInstance().setUseRequest(true);
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.31
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    });
                    return false;
                }
                RequestState.getInstance().setKeyRespone(privateKeyRespone);
                if (RequestState.getInstance().checkTokenValied(30)) {
                    return true;
                }
                this.f9806j.a("local privatekey expired not enough");
                long expiredTime = privateKeyRespone.getExpiredTime();
                long jCurrentTimeMillis = System.currentTimeMillis();
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.32
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                if (expiredTime - jCurrentTimeMillis > 0) {
                    return true;
                }
                this.f9806j.a("local privatekey has expired");
                return false;
            } catch (JSONException e2) {
                e2.printStackTrace();
                this.f9806j.a("parse local privatekey error");
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.33
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                return false;
            }
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

    public static /* synthetic */ void c(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            phoneNumberAuthHelperProxy.e();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private boolean c(Context context) {
        try {
            String authSDKPrivateKey = UTSharedPreferencesHelper.readAuthSDKPrivateKey(context);
            if (!TextUtils.isEmpty(authSDKPrivateKey)) {
                try {
                    PrivateKeyRespone privateKeyRespone = (PrivateKeyRespone) JSONUtils.fromJson(new JSONObject(new String(com.mobile.auth.gatewayauth.utils.security.a.a(authSDKPrivateKey))), (JsonType) new JsonType<PrivateKeyRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.34
                    }, (List<Field>) null);
                    if (privateKeyRespone == null) {
                        return false;
                    }
                    RequestState.getInstance().setKeyRespone(privateKeyRespone);
                    return RequestState.getInstance().checkTokenValied(0);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    this.f9806j.a("parse local privatekey error");
                }
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

    public static /* synthetic */ com.mobile.auth.gatewayauth.manager.d d(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9803g;
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

    private void d(Context context) {
        try {
            com.mobile.auth.v.e privateKey = RequestUtil.getPrivateKey(context, this.f9801e.b(), this.f9803g.j());
            if (!privateKey.b() || TextUtils.isEmpty(privateKey.a())) {
                return;
            }
            try {
                PrivateRespone privateRespone = (PrivateRespone) JSONUtils.fromJson(new JSONObject(privateKey.a()), (JsonType) new JsonType<PrivateRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.35
                }, (List<Field>) null);
                if (PLVWebview.MESSAGE_OK.equals(privateRespone.getCode())) {
                    PrivateKeyRespone data = privateRespone.getData();
                    if (data.getExpiredTime() <= System.currentTimeMillis()) {
                        return;
                    }
                    UTSharedPreferencesHelper.saveAuthSDKPrivateKey(context, com.mobile.auth.gatewayauth.utils.security.a.a(data.toJson().toString().getBytes()));
                    RequestState.getInstance().setKeyRespone(data);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static boolean d() {
        try {
            Class.forName("com.nirvana.tools.logger.UaidTracker");
            return true;
        } catch (ClassNotFoundException unused) {
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

    public static /* synthetic */ ResultCodeProcessor e(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9808l;
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

    private void e() {
        try {
            if (TextUtils.isEmpty(this.f9801e.b())) {
                return;
            }
            MonitorStruct monitorStruct = new MonitorStruct();
            long jCurrentTimeMillis = System.currentTimeMillis();
            monitorStruct.setStartTime(jCurrentTimeMillis);
            monitorStruct.setAction(Constant.ACTION_SDK_VENDOTLIST_DISPATCH);
            DispatchInfoRespone dispatchInfoResponeQueryDispatchInfo = RequestUtil.queryDispatchInfo(this.f9799c.e(), this.f9801e.b(), this.f9803g.j(), "1", 1000);
            monitorStruct.setRequestId(this.f9803g.e());
            monitorStruct.setSessionId(this.f9803g.c());
            monitorStruct.setUrgency(1);
            if (dispatchInfoResponeQueryDispatchInfo == null || !dispatchInfoResponeQueryDispatchInfo.isSuccess() || dispatchInfoResponeQueryDispatchInfo.getData() == null || dispatchInfoResponeQueryDispatchInfo.getItems() == null || dispatchInfoResponeQueryDispatchInfo.getItems().size() <= 0) {
                monitorStruct.setSuccess(false);
                com.mobile.auth.gatewayauth.utils.d.a().b(true);
                com.mobile.auth.gatewayauth.utils.d.a().c(false);
            } else {
                monitorStruct.setSuccess(true);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                int iRandom = (int) (Math.random() * 100.0d);
                List<DispatchInfoItem> items = dispatchInfoResponeQueryDispatchInfo.getItems();
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i2 < items.size()) {
                    DispatchInfoItem dispatchInfoItem = items.get(i2);
                    int dispatchRatio = dispatchInfoItem.getDispatchRatio();
                    List<DispatchInfoItem> list = items;
                    if (dispatchInfoItem.getOperator() == 1) {
                        i3 = dispatchRatio + i3;
                        if (i3 >= iRandom) {
                            int i6 = i3 - iRandom;
                            linkedHashMap3.put(Integer.valueOf(i6), dispatchInfoItem);
                            arrayList3.add(Integer.valueOf(i6));
                        }
                    } else if (dispatchInfoItem.getOperator() == 2) {
                        int i7 = dispatchRatio + i4;
                        if (i7 >= iRandom) {
                            int i8 = i7 - iRandom;
                            i4 = i7;
                            linkedHashMap2.put(Integer.valueOf(i8), dispatchInfoItem);
                            arrayList2.add(Integer.valueOf(i8));
                        } else {
                            i4 = i7;
                        }
                    } else if (dispatchInfoItem.getOperator() == 3) {
                        int i9 = dispatchRatio + i5;
                        if (i9 >= iRandom) {
                            int i10 = i9 - iRandom;
                            i5 = i9;
                            linkedHashMap.put(Integer.valueOf(i10), dispatchInfoItem);
                            arrayList.add(Integer.valueOf(i10));
                        } else {
                            i5 = i9;
                        }
                    }
                    i2++;
                    items = list;
                }
                if (!linkedHashMap3.isEmpty()) {
                    if (linkedHashMap3.size() > 1) {
                        Collections.sort(arrayList3, new Comparator<Integer>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.36
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
                        });
                    }
                    this.f9801e.a((DispatchInfoItem) linkedHashMap3.get(arrayList3.get(0)));
                    this.f9801e.a(System.currentTimeMillis());
                }
                if (!linkedHashMap2.isEmpty()) {
                    if (linkedHashMap2.size() > 1) {
                        Collections.sort(arrayList2, new Comparator<Integer>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.2
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
                        });
                    }
                    this.f9801e.b((DispatchInfoItem) linkedHashMap2.get(arrayList2.get(0)));
                    this.f9801e.a(System.currentTimeMillis());
                }
                if (!linkedHashMap.isEmpty()) {
                    if (linkedHashMap.size() > 1) {
                        Collections.sort(arrayList, new Comparator<Integer>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.3
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
                        });
                    }
                    this.f9801e.c((DispatchInfoItem) linkedHashMap.get(arrayList.get(0)));
                    this.f9801e.a(System.currentTimeMillis());
                }
                this.f9804h.a(this.f9801e, true, true);
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            monitorStruct.setEndTime(jCurrentTimeMillis2);
            monitorStruct.setWholeMS(jCurrentTimeMillis2 - jCurrentTimeMillis);
            this.f9806j.a(this.f9803g.a(monitorStruct), monitorStruct.getUrgency());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a f(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9806j;
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

    private Future<?> f() {
        try {
            return ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).a(new RequestCallback<ConfigRule, Void>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.4.1
                            public void a(ConfigRule configRule) {
                                try {
                                    countDownLatch.countDown();
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            public void a(Void r12) {
                                try {
                                    countDownLatch.countDown();
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                            public /* synthetic */ void onError(Void r12) {
                                try {
                                    a(r12);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                            public /* synthetic */ void onSuccess(ConfigRule configRule) {
                                try {
                                    a(configRule);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }
                        });
                        try {
                            countDownLatch.await(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
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
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ VendorSdkInfoManager g(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9801e;
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

    @AuthNumber
    public static PhoneNumberAuthHelperProxy getInstance(Context context, TokenResultListener tokenResultListener) {
        try {
            if (f9797a == null && context != null) {
                synchronized (PhoneNumberAuthHelperProxy.class) {
                    if (f9797a == null) {
                        f9797a = new PhoneNumberAuthHelperProxy(context, tokenResultListener);
                    }
                }
            }
            f9797a.setAuthListener(tokenResultListener);
            return f9797a;
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

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:8:0x0009
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Unreachable blocks removed: 7, instructions: 7 */
    @com.mobile.auth.gatewayauth.annotations.AuthNumber
    public static java.lang.String getVersion() {
        /*
            java.lang.String r0 = "2.14.3"
            return r0
        L3:
            r0 = move-exception
            r1 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)     // Catch: java.lang.Throwable -> L9
            return r1
        L9:
            r0 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.getVersion():java.lang.String");
    }

    public static /* synthetic */ com.mobile.auth.gatewayauth.manager.e h(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9804h;
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

    public static /* synthetic */ Future i(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.f9805i;
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
    private native void justGetLoginPhone(MonitorStruct monitorStruct, String str, int i2, ResultCodeProcessor resultCodeProcessor, boolean z2, RequestCallback<LoginPhoneInfo, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, String str2, String str3, int i3);

    @SafeProtector
    private native void justGetLoginToken(long j2, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor);

    @SafeProtector
    private native void justGetToken(int i2, ResultCodeProcessor resultCodeProcessor, boolean z2, TokenResultListener tokenResultListener);

    @SafeProtector
    private void justPreLogin(long j2, final PreLoginResultListener preLoginResultListener, final ResultCodeProcessor resultCodeProcessor, boolean z2, boolean z3) {
        int i2;
        String str;
        String str2;
        try {
            final String strJ = this.f9803g.j();
            String strF = this.f9803g.f();
            long j3 = j2 <= 0 ? 5000L : j2;
            final String strC = this.f9799c.c();
            final String strB = this.f9799c.b(strC);
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setApiLevel(resultCodeProcessor.getApiLevel());
            monitorStruct.putApiParam("timeout", String.valueOf(j3));
            monitorStruct.setSessionId(strF);
            monitorStruct.setRequestId(strJ);
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setVendorKey(strC);
            monitorStruct.setAction(this.f9799c.c(strC));
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f9799c.e(), true));
            if (preLoginResultListener == null) {
                a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null"), strC, (TokenResultListener) null, resultCodeProcessor, monitorStruct, strJ);
                this.f9806j.e("accelerateLoginPage errorMsg = PreLoginResultListener is null");
                return;
            }
            final TokenResultListener tokenResultListener = new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.25
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str3) {
                    try {
                        preLoginResultListener.onTokenFailed(strB, str3);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str3) {
                    try {
                        preLoginResultListener.onTokenSuccess(strB);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            final c cVar = new c(j3, new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.26
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("justPreLogin errorCode = ", ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "; errorMsg = ", "请求超时", "; action = ", monitorStruct.getAction());
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            cVar.a();
            TokenRet tokenRetA = this.f9799c.a(resultCodeProcessor, false, strC);
            if (tokenRetA != null && cVar.d()) {
                a(tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                this.f9806j.e("justPreLogin errorCode = ", tokenRetA.getCode(), "; errorMsg = ", tokenRetA.getMsg(), "; action = ", monitorStruct.getAction());
                return;
            }
            com.mobile.auth.gatewayauth.manager.a aVarA = this.f9804h.a(strC);
            if (aVarA == null) {
                if (cVar.d()) {
                    str = "justPreLogin errorCode = ";
                    str2 = "; errorMsg = ";
                    a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                } else {
                    str = "justPreLogin errorCode = ";
                    str2 = "; errorMsg = ";
                }
                this.f9806j.e(str, ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, str2, "无法判运营商");
                return;
            }
            if (this.f9800d.c()) {
                if (cVar.d()) {
                    i2 = 6;
                    a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                } else {
                    i2 = 6;
                }
                com.mobile.auth.o.a aVar = this.f9806j;
                String[] strArr = new String[i2];
                strArr[0] = "justPreLogin errorCode = ";
                strArr[1] = Constant.CODE_ERROR_FUNCTION_DEMOTE;
                strArr[2] = "; errorMsg = ";
                strArr[3] = "系统维护，功能不可用";
                strArr[4] = "; action = ";
                strArr[5] = monitorStruct.getAction();
                aVar.e(strArr);
                return;
            }
            if (z3) {
                if (cVar.d()) {
                    a(ResultCode.CODE_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, ResultCode.MSG_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, ResultCode.MSG_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                }
                this.f9806j.e("Auth page has been showing!");
            } else if (!z2 || this.f9801e.a()) {
                aVarA.a(j3);
                this.f9802f.updateMask(j3, strC, new RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.27
                    public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                        try {
                            if (cVar.d()) {
                                monitorStruct.setCache(String.valueOf(bVar.e()));
                                monitorStruct.setAuthSdkCode(resultCodeProcessor.convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                                if (com.mobile.auth.gatewayauth.utils.d.a().g()) {
                                    monitorStruct.setAction(Constant.ACTION_SDK_CMNT_LOGIN_CODE);
                                    monitorStruct.setVendorKey(bVar.h().getVendor());
                                }
                                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, false, "", strB, monitorStruct, tokenResultListener);
                            }
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }

                    public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                        try {
                            if (cVar.d()) {
                                monitorStruct.setCache(k.a.f27524v);
                                monitorStruct.setCarrierFailedResultData(bVar.d());
                                if (com.mobile.auth.gatewayauth.utils.d.a().g()) {
                                    monitorStruct.setAction(Constant.ACTION_SDK_CMNT_LOGIN_CODE);
                                    monitorStruct.setVendorKey(bVar.h().getVendor());
                                }
                                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                            }
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
                            b(bVar);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }

                    @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                    public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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
                }, this.f9799c.getSimCacheKey(!z2, strC), 6, this.f9801e.a(!z2), strJ, strF);
            } else {
                this.f9806j.e("justGetToken SceneCode = null");
                if (cVar.d()) {
                    a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO), strB, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
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

    @SafeProtector
    private void justPreVerify(long j2, final PreLoginResultListener preLoginResultListener) {
        char c3;
        int i2;
        String str;
        String str2;
        String str3;
        String str4;
        int i3;
        char c4;
        char c5;
        long j3 = j2 <= 0 ? 5000L : j2;
        try {
            final String strJ = this.f9803g.j();
            String strG = this.f9803g.g();
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setApiLevel(this.f9808l.getApiLevel());
            final String strC = this.f9799c.c();
            final String strB = this.f9799c.b(strC);
            monitorStruct.putApiParam("timeout", String.valueOf(j3));
            monitorStruct.setSessionId(strG);
            monitorStruct.setRequestId(strJ);
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setVendorKey(strC);
            monitorStruct.setAction(this.f9799c.d(strC));
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f9799c.e(), true));
            if (preLoginResultListener == null) {
                b(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null"), strC, null, this.f9808l, monitorStruct, strJ);
                this.f9806j.e("accelerateVerify errorMsg = PreLoginResultListener is null");
                return;
            }
            final TokenResultListener tokenResultListener = new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.18
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str5) {
                    try {
                        preLoginResultListener.onTokenFailed(strB, str5);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str5) {
                    try {
                        preLoginResultListener.onTokenSuccess(strB);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            final c cVar = new c(j3, new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), strC, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), monitorStruct, strJ);
                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("justPreVerify errorCode = ", ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "; errorMsg = ", "请求超时", "; action = ", monitorStruct.getAction());
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            cVar.a();
            TokenRet tokenRetA = this.f9799c.a(this.f9808l, false, strC);
            if (tokenRetA != null) {
                if (cVar.d()) {
                    str3 = "justPreVerify errorCode = ";
                    str4 = "; errorMsg = ";
                    i3 = 6;
                    c4 = 0;
                    c5 = 1;
                    b(tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, tokenResultListener, this.f9808l, monitorStruct, strJ);
                } else {
                    str3 = "justPreVerify errorCode = ";
                    str4 = "; errorMsg = ";
                    i3 = 6;
                    c4 = 0;
                    c5 = 1;
                }
                com.mobile.auth.o.a aVar = this.f9806j;
                String[] strArr = new String[i3];
                strArr[c4] = str3;
                strArr[c5] = tokenRetA.getCode();
                strArr[2] = str4;
                strArr[3] = tokenRetA.getMsg();
                strArr[4] = "; action = ";
                strArr[5] = monitorStruct.getAction();
                aVar.e(strArr);
                return;
            }
            com.mobile.auth.gatewayauth.manager.a aVarA = this.f9804h.a(strC);
            if (aVarA == null) {
                if (cVar.d()) {
                    str = "justPreVerify errorCode = ";
                    str2 = "; errorMsg = ";
                    b(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商"), strC, tokenResultListener, this.f9808l, monitorStruct, strJ);
                } else {
                    str = "justPreVerify errorCode = ";
                    str2 = "; errorMsg = ";
                }
                this.f9806j.e(str, ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, str2, "无法判运营商");
                return;
            }
            if (!this.f9800d.b()) {
                if (this.f9801e.a()) {
                    aVarA.a(j3);
                    this.f9802f.b(j3, strC, new RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.20
                        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                            try {
                                if (cVar.d()) {
                                    monitorStruct.setCache(k.a.f27524v);
                                    if (bVar.h() != null && !TextUtils.isEmpty(bVar.h().getVendor())) {
                                        monitorStruct.setAction(Constant.ACTION_SDK_CMNT_PRE_AUTH_TOKEN);
                                        monitorStruct.setVendorKey(bVar.h().getVendor());
                                    }
                                    monitorStruct.setCarrierFailedResultData(bVar.d());
                                    PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), strC, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), monitorStruct, strJ);
                                }
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }

                        public void a(String str5) {
                            try {
                                if (cVar.d()) {
                                    monitorStruct.setCache(str5);
                                    if (com.mobile.auth.gatewayauth.utils.d.a().g()) {
                                        monitorStruct.setAction(Constant.ACTION_SDK_CMNT_PRE_AUTH_TOKEN);
                                        monitorStruct.setVendorKey(Constant.VENDOR_NTCM);
                                    }
                                    monitorStruct.setAuthSdkCode(PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, false, "", strB, monitorStruct, tokenResultListener);
                                }
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
                        public /* synthetic */ void onSuccess(String str5) {
                            try {
                                a(str5);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    }, this.f9799c.getSimCacheKey(false, strC), com.mobile.auth.gatewayauth.manager.c.a(strC), this.f9801e.a(false), strJ, strG);
                    return;
                } else {
                    this.f9806j.e("justPreVerify SceneCode = null");
                    if (cVar.d()) {
                        b(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO), strB, tokenResultListener, this.f9808l, monitorStruct, strJ);
                        return;
                    }
                    return;
                }
            }
            if (cVar.d()) {
                c3 = 0;
                i2 = 6;
                b(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用"), strC, tokenResultListener, this.f9808l, monitorStruct, strJ);
            } else {
                c3 = 0;
                i2 = 6;
            }
            com.mobile.auth.o.a aVar2 = this.f9806j;
            String[] strArr2 = new String[i2];
            strArr2[c3] = "justPreVerify errorCode = ";
            strArr2[1] = Constant.CODE_ERROR_FUNCTION_DEMOTE;
            strArr2[2] = "; errorMsg = ";
            strArr2[3] = "系统维护，功能不可用";
            strArr2[4] = "; action = ";
            strArr2[5] = monitorStruct.getAction();
            aVar2.e(strArr2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public SystemManager a() {
        try {
            return this.f9799c;
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

    public void a(final long j2, final TokenResultListener tokenResultListener, final ResultCodeProcessor resultCodeProcessor) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(tokenResultListener) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.9
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, j2, tokenResultListener, resultCodeProcessor);
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

    public synchronized void a(String str) {
        try {
            this.f9809m = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str, String str2, MonitorStruct monitorStruct, ResultCodeProcessor resultCodeProcessor, TokenResultListener tokenResultListener) {
        if (tokenResultListener == null) {
            return;
        }
        try {
            TokenRet tokenRetConvertErrorInfo = resultCodeProcessor.convertErrorInfo(Constant.CODE_GET_TOKEN_SUCCESS, "获取token成功", str2);
            tokenRetConvertErrorInfo.setToken(str);
            tokenRetConvertErrorInfo.setRequestId(monitorStruct.getRequestId());
            monitorStruct.setPerformanceTrace(com.mobile.auth.gatewayauth.utils.e.a().a(monitorStruct.getRequestId()));
            monitorStruct.setAccessCode(str);
            monitorStruct.setAuthSdkCode(tokenRetConvertErrorInfo.getCode());
            a(true, true, "", tokenRetConvertErrorInfo.toJsonString(), monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(boolean z2, boolean z3, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str5) {
        try {
            TokenRet tokenRetConvertErrorInfo = resultCodeProcessor.convertErrorInfo(str, str2, str4);
            if (monitorStruct != null) {
                monitorStruct.setPerformanceTrace(com.mobile.auth.gatewayauth.utils.e.a().a(str5));
                monitorStruct.setAuthSdkCode(tokenRetConvertErrorInfo.getCode());
                tokenRetConvertErrorInfo.setCarrierFailedResultData(monitorStruct.getCarrierFailedResultData());
            }
            tokenRetConvertErrorInfo.setRequestId(str5);
            a(z2, z3, str3, tokenRetConvertErrorInfo.toJsonString(), monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void accelerateLoginPage(final int i2, final PreLoginResultListener preLoginResultListener, final boolean z2) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(this.f9798b) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.15
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    try {
                        PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy = PhoneNumberAuthHelperProxy.this;
                        PhoneNumberAuthHelperProxy.a(phoneNumberAuthHelperProxy, i2, preLoginResultListener, PhoneNumberAuthHelperProxy.e(phoneNumberAuthHelperProxy), true, z2);
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

    @AuthNumber
    public void accelerateVerify(final int i2, final PreLoginResultListener preLoginResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.16
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str) {
                    try {
                        PreLoginResultListener preLoginResultListener2 = preLoginResultListener;
                        if (preLoginResultListener2 != null) {
                            preLoginResultListener2.onTokenFailed("", str);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str) {
                }
            }) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.17
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, i2, preLoginResultListener);
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

    public com.mobile.auth.gatewayauth.manager.d b() {
        try {
            return this.f9803g;
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

    public com.mobile.auth.o.a c() {
        try {
            return this.f9806j;
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

    @AuthNumber
    public void checkEnvAvailable(@IntRange(from = 1, to = 2) final int i2, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.7
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    int i3;
                    try {
                        String strC = PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).c();
                        String strJ = PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).j();
                        TokenRet tokenRet = null;
                        try {
                            try {
                                int i4 = i2;
                                if (i4 != 1 && i4 != 2) {
                                    TokenRet tokenRetConvertErrorInfo = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo("600025", ResultCode.MSG_ERROR_INVALID_PARAM, strC);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                    if (tokenRetConvertErrorInfo != null) {
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        return;
                                    }
                                    return;
                                }
                                TokenRet tokenRetA = PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), true, strC);
                                if (tokenRetA != null) {
                                    try {
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", tokenRetA.getCode(), "; errorMsg = ", tokenRetA.getMsg());
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        return;
                                    } catch (Exception e2) {
                                        e = e2;
                                        i3 = 2;
                                        e.printStackTrace();
                                        TokenRet tokenRetConvertErrorInfo2 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e), strC);
                                        com.mobile.auth.o.a aVarF = PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this);
                                        String[] strArr = new String[i3];
                                        strArr[0] = "checkEnvAvailable exception:";
                                        strArr[1] = tokenRetConvertErrorInfo2.toJsonString();
                                        aVarF.e(strArr);
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                    } catch (Throwable th) {
                                        th = th;
                                        tokenRet = tokenRetA;
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                        if (tokenRet != null) {
                                            PhoneNumberAuthHelperProxy.this.a(false, false, tokenRet.getCode(), tokenRet.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRet.getCode(), tokenRet.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        }
                                        throw th;
                                    }
                                }
                                if (!PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).a()) {
                                    TokenRet tokenRetConvertErrorInfo3 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, strC);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, "; errorMsg = ", ResultCode.MSG_ERROR_ANALYZE_SDK_INFO);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                    if (tokenRetConvertErrorInfo3 != null) {
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        return;
                                    }
                                    return;
                                }
                                if (PhoneNumberAuthHelperProxy.h(PhoneNumberAuthHelperProxy.this).a(strC) == null) {
                                    TokenRet tokenRetConvertErrorInfo4 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", strC);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable failed! Unsupported Vendor!");
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                    if (tokenRetConvertErrorInfo4 != null) {
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo4.getCode(), tokenRetConvertErrorInfo4.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo4.getCode(), tokenRetConvertErrorInfo4.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        return;
                                    }
                                    return;
                                }
                                if (PhoneNumberAuthHelperProxy.i(PhoneNumberAuthHelperProxy.this) != null) {
                                    PhoneNumberAuthHelperProxy.i(PhoneNumberAuthHelperProxy.this).get();
                                }
                                if (i2 == 1) {
                                    if (PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).b()) {
                                        TokenRet tokenRetConvertErrorInfo5 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", strC);
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "; errorMsg = ", "系统维护，功能不可用");
                                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                        if (tokenRetConvertErrorInfo5 != null) {
                                            PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo5.getCode(), tokenRetConvertErrorInfo5.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo5.getCode(), tokenRetConvertErrorInfo5.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                            return;
                                        }
                                        return;
                                    }
                                } else if (PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).c()) {
                                    TokenRet tokenRetConvertErrorInfo6 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", strC);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "; errorMsg = ", "系统维护，功能不可用");
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                    if (tokenRetConvertErrorInfo6 != null) {
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo6.getCode(), tokenRetConvertErrorInfo6.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo6.getCode(), tokenRetConvertErrorInfo6.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                        return;
                                    }
                                    return;
                                }
                                PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy = PhoneNumberAuthHelperProxy.this;
                                i3 = 2;
                                try {
                                    phoneNumberAuthHelperProxy.a(true, false, ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS, ResultCode.MSG_ERROR_ENV_CHECK_SUCCESS, "", strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(phoneNumberAuthHelperProxy), strJ);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                } catch (Exception e3) {
                                    e = e3;
                                    e.printStackTrace();
                                    TokenRet tokenRetConvertErrorInfo22 = PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e), strC);
                                    com.mobile.auth.o.a aVarF2 = PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this);
                                    String[] strArr2 = new String[i3];
                                    strArr2[0] = "checkEnvAvailable exception:";
                                    strArr2[1] = tokenRetConvertErrorInfo22.toJsonString();
                                    aVarF2.e(strArr2);
                                    PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).c();
                                    PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo22.getCode(), tokenRetConvertErrorInfo22.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo22.getCode(), tokenRetConvertErrorInfo22.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), strJ);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } catch (Exception e4) {
                            e = e4;
                        }
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
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

    /* JADX WARN: Removed duplicated region for block: B:53:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0239  */
    @com.mobile.auth.gatewayauth.annotations.AuthNumber
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkEnvAvailable() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.checkEnvAvailable():boolean");
    }

    @AuthNumber
    public void clearPreInfo() {
        try {
            this.f9802f.a();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public String getCurrentCarrierName() {
        try {
            return this.f9799c.d();
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

    @AuthNumber
    public String getLoginMaskPhone(int i2, final String str, final OnLoginPhoneListener onLoginPhoneListener, final boolean z2, boolean z3, final String str2) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            String strF = this.f9803g.f();
            monitorStruct.setStartTime(System.currentTimeMillis());
            justGetLoginPhone(monitorStruct, str, i2, this.f9808l, z3, new RequestCallback<LoginPhoneInfo, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.21
                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).h();
                        PhoneNumberAuthHelperProxy.this.a(false, z2, bVar.b(), bVar.c(), bVar.g(), str, monitorStruct, new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.21.3
                            @Override // com.mobile.auth.gatewayauth.TokenResultListener
                            public void onTokenFailed(String str3) {
                                try {
                                    onLoginPhoneListener.onGetFailed(str3);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.TokenResultListener
                            public void onTokenSuccess(String str3) {
                            }
                        }, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), str2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(final LoginPhoneInfo loginPhoneInfo) {
                    try {
                        loginPhoneInfo.setVendor(PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).b(str));
                        if (com.mobile.auth.gatewayauth.utils.c.e(PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).e())) {
                            PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy = PhoneNumberAuthHelperProxy.this;
                            phoneNumberAuthHelperProxy.a(true, z2, "600000", "获取掩码成功", "", str, monitorStruct, null, PhoneNumberAuthHelperProxy.e(phoneNumberAuthHelperProxy), str2);
                            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.21.1
                                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                                public void onException(Throwable th) {
                                }

                                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                                public void safeRun() {
                                    try {
                                        onLoginPhoneListener.onGetLoginPhone(loginPhoneInfo);
                                    } catch (Throwable th) {
                                        try {
                                            ExceptionProcessor.processException(th);
                                        } catch (Throwable th2) {
                                            ExceptionProcessor.processException(th2);
                                        }
                                    }
                                }
                            });
                        } else {
                            PhoneNumberAuthHelperProxy.this.a(false, z2, ResultCode.CODE_ERROR_NO_MOBILE_NETWORK_FAIL, ResultCode.MSG_ERROR_NO_MOBILE_NETWORK_FAIL, "", str, monitorStruct, new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.21.2
                                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                                public void onTokenFailed(String str3) {
                                    try {
                                        onLoginPhoneListener.onGetFailed(str3);
                                    } catch (Throwable th) {
                                        try {
                                            ExceptionProcessor.processException(th);
                                        } catch (Throwable th2) {
                                            ExceptionProcessor.processException(th2);
                                        }
                                    }
                                }

                                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                                public void onTokenSuccess(String str3) {
                                }
                            }, PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), str2);
                        }
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
                public /* synthetic */ void onSuccess(LoginPhoneInfo loginPhoneInfo) {
                    try {
                        a(loginPhoneInfo);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, str2, strF, 6);
            return str2;
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

    @AuthNumber
    public PnsReporter getReporter() {
        try {
            return this.f9803g.b();
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

    @AuthNumber
    public void getVerifyToken(final int i2, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(tokenResultListener) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.8
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    try {
                        PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy = PhoneNumberAuthHelperProxy.this;
                        PhoneNumberAuthHelperProxy.a(phoneNumberAuthHelperProxy, i2, PhoneNumberAuthHelperProxy.e(phoneNumberAuthHelperProxy), true, tokenResultListener);
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

    @AuthNumber
    public void prohibitUseUtdid() {
        try {
            com.mobile.auth.gatewayauth.manager.d dVar = this.f9803g;
            if (dVar != null) {
                dVar.n();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void setAuthListener(TokenResultListener tokenResultListener) {
        try {
            this.f9798b = tokenResultListener;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void setAuthSDKInfo(String str) {
        try {
            this.f9806j.a("setAuthSDKInfo secretInfo = ", str);
            this.f9799c.a(str);
            this.f9801e.setLocalVendorSdkInfo(str);
            com.mobile.auth.gatewayauth.utils.d.a().b(false);
            com.mobile.auth.gatewayauth.utils.d.a().c(true);
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.c(PhoneNumberAuthHelperProxy.this);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            if (!this.f9804h.a(this.f9801e, false, true)) {
                this.f9806j.e("VendorSdkFactor update local VendorConfig failed!");
                return;
            }
            com.mobile.auth.gatewayauth.utils.c.g(this.f9799c.e());
            while (true) {
                if (!com.mobile.auth.gatewayauth.utils.d.a().d() && com.mobile.auth.gatewayauth.utils.d.a().c()) {
                    break;
                }
            }
            if (b(this.f9799c.e())) {
                if (RequestState.getInstance().checkTokenValied(1)) {
                    this.f9806j.e();
                } else {
                    if (RequestState.getInstance().isUseRequest() || RequestState.getInstance().checkTokenValied(1)) {
                        return;
                    }
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.6
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy = PhoneNumberAuthHelperProxy.this;
                                PhoneNumberAuthHelperProxy.a(phoneNumberAuthHelperProxy, PhoneNumberAuthHelperProxy.a(phoneNumberAuthHelperProxy).e());
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
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
