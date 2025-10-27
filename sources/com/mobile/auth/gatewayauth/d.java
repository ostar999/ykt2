package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.utils.ReflectionUtils;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.gatewayauth.utils.l;
import com.nirvana.tools.core.AppUtils;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.core.SupportJarUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: c, reason: collision with root package name */
    private final Context f9980c;

    /* renamed from: d, reason: collision with root package name */
    private AuthUIControlClickListener f9981d;

    /* renamed from: e, reason: collision with root package name */
    private volatile WeakReference<Activity> f9982e;

    /* renamed from: f, reason: collision with root package name */
    private volatile WeakReference<Activity> f9983f;

    /* renamed from: g, reason: collision with root package name */
    private AuthUIConfig f9984g;

    /* renamed from: h, reason: collision with root package name */
    private LinkedHashMap<String, AuthRegisterViewConfig> f9985h;

    /* renamed from: i, reason: collision with root package name */
    private LinkedHashMap<String, AuthRegisterViewConfig> f9986i;

    /* renamed from: j, reason: collision with root package name */
    private LinkedHashMap<String, Integer> f9987j;

    /* renamed from: k, reason: collision with root package name */
    private ArrayList<AuthRegisterXmlConfig> f9988k;

    /* renamed from: l, reason: collision with root package name */
    private ArrayList<AuthRegisterXmlConfig> f9989l;

    /* renamed from: m, reason: collision with root package name */
    private ArrayList<Object> f9990m;

    /* renamed from: n, reason: collision with root package name */
    private ArrayList<Object> f9991n;

    /* renamed from: p, reason: collision with root package name */
    private final com.mobile.auth.o.a f9993p;

    /* renamed from: q, reason: collision with root package name */
    private WeakReference<Activity> f9994q;

    /* renamed from: r, reason: collision with root package name */
    private TokenResultListener f9995r;

    /* renamed from: s, reason: collision with root package name */
    private ActivityResultListener f9996s;

    /* renamed from: t, reason: collision with root package name */
    private final PhoneNumberAuthHelper f9997t;

    /* renamed from: u, reason: collision with root package name */
    private final SystemManager f9998u;

    /* renamed from: v, reason: collision with root package name */
    private ResultCodeProcessor f9999v;

    /* renamed from: w, reason: collision with root package name */
    private final com.mobile.auth.gatewayauth.manager.d f10000w;

    /* renamed from: x, reason: collision with root package name */
    private long f10001x;

    /* renamed from: y, reason: collision with root package name */
    private long f10002y;

    /* renamed from: b, reason: collision with root package name */
    private static final ConcurrentHashMap<Integer, d> f9979b = new ConcurrentHashMap<>(5);

    /* renamed from: a, reason: collision with root package name */
    public static final AuthUIConfig f9978a = new AuthUIConfig.Builder().create();

    /* renamed from: z, reason: collision with root package name */
    private volatile boolean f10003z = false;
    private volatile boolean A = false;
    private volatile boolean B = false;
    private volatile boolean C = false;
    private volatile boolean D = true;
    private volatile boolean E = false;
    private volatile boolean F = false;
    private volatile boolean G = false;
    private final Application.ActivityLifecycleCallbacks H = new Application.ActivityLifecycleCallbacks() { // from class: com.mobile.auth.gatewayauth.d.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            try {
                d.this.a(activity);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Activity activity2;
            try {
                if ((activity instanceof LoginAuthActivity) && ((LoginAuthActivity) activity).getUIManagerID() == d.a(d.this) && d.b(d.this) != null && (activity2 = (Activity) d.b(d.this).get()) != null && activity2 == activity) {
                    Application application = ReflectionUtils.getApplication();
                    if (application != null) {
                        application.unregisterActivityLifecycleCallbacks(d.c(d.this));
                    }
                    d.a(d.this, (WeakReference) null);
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            try {
                if ((activity instanceof LoginAuthActivity) && ((LoginAuthActivity) activity).getUIManagerID() == d.a(d.this)) {
                    d.a(d.this, false);
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            try {
                if ((activity instanceof LoginAuthActivity) && ((LoginAuthActivity) activity).getUIManagerID() == d.a(d.this)) {
                    d.a(d.this, true);
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    };

    /* renamed from: o, reason: collision with root package name */
    private final int f9992o = hashCode();

    public d(Context context, com.mobile.auth.gatewayauth.manager.d dVar, SystemManager systemManager, PhoneNumberAuthHelper phoneNumberAuthHelper) {
        this.f9980c = context.getApplicationContext();
        this.f10000w = dVar;
        this.f9993p = dVar.a();
        this.f9998u = systemManager;
        this.f9997t = phoneNumberAuthHelper;
    }

    public static /* synthetic */ int a(d dVar) {
        try {
            return dVar.f9992o;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static /* synthetic */ AuthUIConfig a(d dVar, AuthUIConfig authUIConfig) {
        try {
            dVar.f9984g = authUIConfig;
            return authUIConfig;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static d a(int i2) {
        try {
            return f9979b.get(Integer.valueOf(i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ WeakReference a(d dVar, WeakReference weakReference) {
        try {
            dVar.f9994q = weakReference;
            return weakReference;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(AuthUIConfig authUIConfig, int i2, Activity activity) {
        try {
            if (authUIConfig.isStatusBarHidden()) {
                l.a(activity);
            } else {
                l.c(activity, authUIConfig.getStatusBarUIFlag());
            }
            l.a(activity, i2);
            l.b(activity, authUIConfig.getBottomNavBarColor());
            l.a(activity, authUIConfig.isLightColor());
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void a(final String str, final String str2, final String str3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).sessionId(d.h(d.this).c()).authSdkCode(ResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL).carrierUrl(str3).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void a(final String str, final String str2, final String str3, final String str4, final String str5) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).protocolName(str5).protocolUrl(str4).sessionId(d.h(d.this).c()).authSdkCode(d.i(d.this).convertCode(str3)).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void a(final String str, final String str2, final boolean z2) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).sessionId(d.h(d.this).c()).authSdkCode(ResultCode.CODE_START_AUTH_PRIVACY).isAuthPageLegal(String.valueOf(z2)).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void a(final String str, final String str2, boolean z2, final boolean z3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).sessionId(d.h(d.this).c()).authSdkCode(ResultCode.CODE_ERROR_USER_LOGIN_BTN).isAuthPageLegal(String.valueOf(z3)).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void a(final boolean z2, final String str, final String str2, final boolean z3) {
        try {
            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.d.6
                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onException(Throwable th) {
                    try {
                        d.d(d.this).e("QuitActivity error!", ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onFinal() {
                    try {
                        super.onFinal();
                        if (z3 && d.f(d.this) != null && z2) {
                            TokenRet tokenRetConvertErrorInfo = d.this.a().convertErrorInfo(str, str2, d.g(d.this).c());
                            tokenRetConvertErrorInfo.setVendorName(d.g(d.this).d());
                            tokenRetConvertErrorInfo.setRequestId(d.h(d.this).e());
                            d.f(d.this).onTokenFailed(tokenRetConvertErrorInfo.toJsonString());
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void safeRun() {
                    Activity activity;
                    try {
                        if (d.b(d.this) != null && (activity = (Activity) d.b(d.this).get()) != null) {
                            activity.finish();
                            d dVar = d.this;
                            d.a(dVar, dVar.r());
                            if (d.e(d.this).getAuthPageActOut() != null && d.e(d.this).getActivityIn() != null) {
                                activity.overridePendingTransition(AppUtils.getAnimResID(activity, d.e(d.this).getAuthPageActOut()), AppUtils.getAnimResID(activity, d.e(d.this).getActivityIn()));
                            }
                        }
                        d.this.B();
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static /* synthetic */ boolean a(d dVar, boolean z2) {
        try {
            dVar.f10003z = z2;
            return z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static /* synthetic */ WeakReference b(d dVar) {
        try {
            return dVar.f9994q;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ WeakReference b(d dVar, WeakReference weakReference) {
        try {
            dVar.f9983f = weakReference;
            return weakReference;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private void b(final String str, final String str2, final String str3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).sessionId(d.h(d.this).c()).authSdkCode(d.i(d.this).convertCode(str3)).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static /* synthetic */ Application.ActivityLifecycleCallbacks c(d dVar) {
        try {
            return dVar.H;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private void c(final String str, final String str2, final String str3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        d.d(d.this).a(d.h(d.this).b(str, str2, UStruct.newUStruct().startTime(d.j(d.this)).endTime(System.currentTimeMillis()).requestId(d.h(d.this).e()).suspendDisMissVC(d.k(d.this)).sessionId(d.h(d.this).c()).authSdkCode(d.i(d.this).convertCode(str3)).build(), ""), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a d(d dVar) {
        try {
            return dVar.f9993p;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ AuthUIConfig e(d dVar) {
        try {
            return dVar.f9984g;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ TokenResultListener f(d dVar) {
        try {
            return dVar.f9995r;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ SystemManager g(d dVar) {
        try {
            return dVar.f9998u;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ com.mobile.auth.gatewayauth.manager.d h(d dVar) {
        try {
            return dVar.f10000w;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ ResultCodeProcessor i(d dVar) {
        try {
            return dVar.f9999v;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ long j(d dVar) {
        try {
            return dVar.f10001x;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1L;
        }
    }

    public static /* synthetic */ boolean k(d dVar) {
        try {
            return dVar.E;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void A() {
        try {
            if (this.f9983f == null || this.f9983f.get() == null || !(this.f9983f.get() instanceof PrivacyDialogActivity)) {
                return;
            }
            ((PrivacyDialogActivity) this.f9983f.get()).cancelPrivacyDialog();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void B() {
        try {
            f9979b.remove(Integer.valueOf(this.f9992o));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public com.mobile.auth.o.a C() {
        try {
            return this.f9993p;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public boolean D() {
        try {
            return this.f10003z;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void E() {
        try {
            LinkedHashMap<String, Integer> linkedHashMap = this.f9987j;
            if (linkedHashMap != null) {
                linkedHashMap.clear();
                this.f9987j = null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public ResultCodeProcessor a() {
        try {
            if (this.f9999v == null) {
                this.f9999v = new com.mobile.auth.gatewayauth.manager.compat.a();
            }
            return this.f9999v;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public void a(long j2) {
        try {
            this.f10001x = j2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(long j2, String str, String str2, ResultCodeProcessor resultCodeProcessor, e eVar) {
        Context context;
        try {
            this.f10002y = j2;
            d();
            Intent intent = new Intent(this.f9980c, (Class<?>) LoginAuthActivity.class);
            intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, str);
            intent.putExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY, str2);
            intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.f9992o);
            intent.putExtra("startTime", System.currentTimeMillis());
            try {
                a(resultCodeProcessor);
                f9979b.put(Integer.valueOf(this.f9992o), this);
                Activity activity = this.f9982e != null ? this.f9982e.get() : null;
                if (r().getAuthPageActIn() == null || r().getActivityOut() == null) {
                    if (activity != null) {
                        activity.startActivityForResult(intent, 1);
                    } else {
                        intent.addFlags(268435456);
                        context = this.f9980c;
                        context.startActivity(intent);
                    }
                } else if (activity != null) {
                    String authPageActIn = r().getAuthPageActIn();
                    String activityOut = r().getActivityOut();
                    if (TextUtils.isEmpty(authPageActIn) || TextUtils.isEmpty(activityOut)) {
                        SupportJarUtils.startActivityForResult(activity, intent, 1, null, null);
                    } else {
                        SupportJarUtils.startActivityForResult(activity, intent, 1, authPageActIn, activityOut);
                    }
                } else {
                    intent.addFlags(268435456);
                    context = this.f9980c;
                    context.startActivity(intent);
                }
                if (eVar != null) {
                    eVar.a(str2, str);
                }
            } catch (Exception e2) {
                String errorInfoFromException = ExecutorManager.getErrorInfoFromException(e2);
                i.c(errorInfoFromException);
                eVar.a(errorInfoFromException);
                B();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(Activity activity) {
        Intent intent;
        try {
            if ((activity instanceof LoginAuthActivity) && (intent = activity.getIntent()) != null && intent.getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, -1) == this.f9992o) {
                this.f9994q = new WeakReference<>(activity);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(ActivityResultListener activityResultListener) {
        try {
            this.f9996s = activityResultListener;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(AuthRegisterXmlConfig authRegisterXmlConfig) {
        try {
            try {
                if (this.f9988k == null) {
                    this.f9988k = new ArrayList<>();
                }
                this.f9988k.add(authRegisterXmlConfig);
                a((Object) authRegisterXmlConfig);
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(AuthUIConfig authUIConfig) {
        try {
            this.f9984g = authUIConfig;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(AuthUIControlClickListener authUIControlClickListener) {
        try {
            this.f9981d = authUIControlClickListener;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(TokenResultListener tokenResultListener) {
        try {
            this.f9995r = tokenResultListener;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(ResultCodeProcessor resultCodeProcessor) {
        try {
            this.f9999v = resultCodeProcessor;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(Class<?> cls, int i2, int i3) {
        Activity activity;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                return;
            }
            ((LoginAuthActivity) activity).openUserPage(cls, i2, i3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(Object obj) {
        try {
            if (this.f9990m == null) {
                this.f9990m = new ArrayList<>();
            }
            this.f9990m.add(obj);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_ERROR_USER_CANCEL, this.f9980c, null);
            }
            c(str, this.f9998u.j(str), Constant.CODE_ERROR_USER_CANCEL);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str, int i2) {
        try {
            if (this.f9987j == null) {
                this.f9987j = new LinkedHashMap<>();
            }
            this.f9987j.put(str, Integer.valueOf(i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(final String str, final long j2, final boolean z2, final boolean z3) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.d.8
                @Override // java.lang.Runnable
                public void run() {
                    ResultCodeProcessor resultCodeProcessorI;
                    String str2;
                    try {
                        com.mobile.auth.o.a aVarD = d.d(d.this);
                        com.mobile.auth.gatewayauth.manager.d dVarH = d.h(d.this);
                        String str3 = str;
                        String strM = d.g(d.this).m(str);
                        UStruct.Builder builderEndTime = UStruct.newUStruct().isSuccess(z3).isFullScreen(String.valueOf(!d.this.r().isDialog())).isVertical(String.valueOf(z2)).isChecked(String.valueOf(d.this.r().isCheckboxHidden() || d.this.r().isPrivacyState())).isCheckboxHidden(String.valueOf(d.this.r().isCheckboxHidden())).requestId(d.h(d.this).e()).sessionId(d.h(d.this).c()).startTime(j2).endTime(d.j(d.this));
                        if (z3) {
                            resultCodeProcessorI = d.i(d.this);
                            str2 = Constant.CODE_START_AUTH_PAGE_SUCCESS;
                        } else {
                            resultCodeProcessorI = d.i(d.this);
                            str2 = Constant.CODE_ERROR_START_AUTH_PAGE_FAIL;
                        }
                        aVarD.a(dVarH.b(str3, strM, builderEndTime.authSdkCode(resultCodeProcessorI.convertCode(str2)).build(), ""), 1);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str, AuthRegisterViewConfig authRegisterViewConfig) {
        try {
            try {
                if (this.f9985h == null) {
                    this.f9985h = new LinkedHashMap<>();
                }
                this.f9985h.put(str, authRegisterViewConfig);
                if (authRegisterViewConfig.getRootViewId() == 0) {
                    a(authRegisterViewConfig);
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str, String str2) {
        Activity activity;
        Intent intent;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                this.f9993p.e("LoginAuthActivity实例被释放");
                return;
            }
            AuthUIConfig authUIConfigR = r();
            this.f9984g = authUIConfigR;
            if (TextUtils.isEmpty(authUIConfigR.getProtocolAction())) {
                intent = new Intent(activity, (Class<?>) AuthWebVeiwActivity.class);
                intent.putExtra("url", str2);
                intent.putExtra("name", str);
                intent.putExtra("orientation", r().getScreenOrientation());
                intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.f9992o);
            } else {
                intent = new Intent();
                intent.setAction(this.f9984g.getProtocolAction());
                if (!TextUtils.isEmpty(this.f9984g.getPackageName())) {
                    intent.setPackage(this.f9984g.getPackageName());
                }
                intent.putExtra("url", str2);
                intent.putExtra("name", str);
                intent.putExtra("orientation", r().getScreenOrientation());
            }
            activity.startActivity(intent);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str, String str2, String str3, boolean z2) {
        try {
            if (this.f9981d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", str2);
                    jSONObject.put("url", str3);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f9981d.onClick(ResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL, this.f9980c, jSONObject.toString());
            }
            if (z2) {
                a(str, this.f9998u.l(str), str3);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(String str, boolean z2, boolean z3) {
        try {
            if (this.f9981d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("isChecked", z2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f9981d.onClick(ResultCode.CODE_ERROR_USER_LOGIN_BTN, this.f9980c, jSONObject.toString());
            }
            a(str, this.f9998u.h(str), z2, z3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(boolean z2) {
        try {
            this.A = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void a(boolean z2, String str, String str2) {
        try {
            a(z2, str, str2, true);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(Activity activity) {
        try {
            this.f9982e = new WeakReference<>(activity);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(AuthRegisterXmlConfig authRegisterXmlConfig) {
        try {
            try {
                if (this.f9989l == null) {
                    this.f9989l = new ArrayList<>();
                }
                this.f9989l.add(authRegisterXmlConfig);
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(ResultCodeProcessor resultCodeProcessor) {
        try {
            this.f9997t.a(this.f10002y, new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.d.4
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str) {
                    try {
                        if (d.f(d.this) != null) {
                            d.f(d.this).onTokenFailed(str);
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str) {
                    try {
                        if (d.f(d.this) != null) {
                            d.f(d.this).onTokenSuccess(str);
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            }, resultCodeProcessor);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(Object obj) {
        try {
            if (this.f9991n == null) {
                this.f9991n = new ArrayList<>();
            }
            this.f9991n.add(obj);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_ERROR_USER_CONTROL_CANCEL_BYBTN, this.f9980c, null);
            }
            c(str, this.f9998u.j(str), ResultCode.CODE_ERROR_USER_CONTROL_CANCEL_BYBTN);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(String str, AuthRegisterViewConfig authRegisterViewConfig) {
        try {
            try {
                if (this.f9986i == null) {
                    this.f9986i = new LinkedHashMap<>();
                }
                this.f9986i.put(str, authRegisterViewConfig);
                if (authRegisterViewConfig.getRootViewId() == 0) {
                    b(authRegisterViewConfig);
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(String str, String str2, String str3, boolean z2) {
        try {
            if (this.f9981d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", str2);
                    jSONObject.put("url", str3);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f9981d.onClick(ResultCode.CODE_CLICK_AUTH_PRIVACY_WEBURL, this.f9980c, jSONObject.toString());
            }
            if (z2) {
                a(str, this.f9998u.k(str), ResultCode.CODE_CLICK_AUTH_PRIVACY_WEBURL, str3, str2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(String str, boolean z2, boolean z3) {
        try {
            if (this.f9981d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("isChecked", z2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f9981d.onClick(ResultCode.CODE_START_AUTH_PRIVACY, this.f9980c, jSONObject.toString());
            }
            a(str, this.f9998u.i(str), z3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void b(boolean z2) {
        try {
            this.B = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public boolean b() {
        try {
            return this.F;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void c() {
        try {
            this.F = true;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void c(Activity activity) {
        try {
            this.f9983f = new WeakReference<>(activity);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void c(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_ERROR_USER_CONTROL_CANCEL_BYKEY, this.f9980c, null);
            }
            c(str, this.f9998u.j(str), ResultCode.CODE_ERROR_USER_CONTROL_CANCEL_BYKEY);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void c(boolean z2) {
        try {
            this.C = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void d() {
        try {
            Application application = ReflectionUtils.getApplication();
            if (application != null) {
                application.registerActivityLifecycleCallbacks(this.H);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void d(final Activity activity) {
        try {
            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.d.7
                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onException(Throwable th) {
                    try {
                        d.d(d.this).e("QuitActivity error!", ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onFinal() {
                    try {
                        super.onFinal();
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void safeRun() {
                    try {
                        Activity activity2 = activity;
                        if (activity2 != null) {
                            activity2.finish();
                            d.b(d.this, (WeakReference) null);
                            d dVar = d.this;
                            d.a(dVar, dVar.r());
                            if (d.e(d.this).getPrivacyAlertExitAnimation() == null || d.e(d.this).getPrivacyAlertEntryAnimation() == null) {
                                return;
                            }
                            Activity activity3 = activity;
                            activity3.overridePendingTransition(AppUtils.getAnimResID(activity3, d.e(d.this).getPrivacyAlertEntryAnimation()), AppUtils.getAnimResID(activity, d.e(d.this).getPrivacyAlertExitAnimation()));
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void d(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_ERROR_USER_SWITCH, this.f9980c, null);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void d(boolean z2) {
        try {
            this.E = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void e(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_CLICK_AUTH_PRIVACY_CONFIRM, this.f9980c, null);
            }
            b(str, Constant.ACTION_CLICK_PRIVACYALERT_CONFIRM, ResultCode.CODE_CLICK_AUTH_PRIVACY_CONFIRM);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void e(boolean z2) {
        try {
            this.D = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public boolean e() {
        try {
            return this.A;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void f(String str) {
        try {
            AuthUIControlClickListener authUIControlClickListener = this.f9981d;
            if (authUIControlClickListener != null) {
                authUIControlClickListener.onClick(ResultCode.CODE_AUTH_PRIVACY_CLOSE, this.f9980c, null);
            }
            b(str, Constant.ACTION_PRIVACYALERT_CLOSE, ResultCode.CODE_AUTH_PRIVACY_CLOSE);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void f(boolean z2) {
        Activity activity;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                return;
            }
            ((LoginAuthActivity) activity).setProtocolChecked(z2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public boolean f() {
        try {
            return this.B;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public int g(String str) {
        try {
            if (this.f9987j == null) {
                this.f9987j = new LinkedHashMap<>();
                return 0;
            }
            if (this.f9986i.size() != 0 && this.f9986i.containsKey(str)) {
                return this.f9987j.get(str).intValue();
            }
            return 0;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public void g(boolean z2) {
        try {
            if (this.f9981d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("isChecked", z2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f9981d.onClick(ResultCode.CODE_ERROR_USER_CHECKBOX, this.f9980c, jSONObject.toString());
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public boolean g() {
        try {
            return this.C;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public boolean h() {
        try {
            return this.E;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public boolean i() {
        try {
            return this.D;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public boolean j() {
        try {
            return this.G;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void k() {
        try {
            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.d.5
                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void onException(Throwable th) {
                    try {
                        d.d(d.this).e("Hide Loading error!", ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                public void safeRun() {
                    Activity activity;
                    try {
                        if (d.b(d.this) == null || (activity = (Activity) d.b(d.this).get()) == null) {
                            return;
                        }
                        ((LoginAuthActivity) activity).hideLoadingDialog();
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void l() {
        Activity activity;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                return;
            }
            ((LoginAuthActivity) activity).animateProtocolTV();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void m() {
        Activity activity;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                return;
            }
            ((LoginAuthActivity) activity).animateCheckBox();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public boolean n() {
        Activity activity;
        try {
            WeakReference<Activity> weakReference = this.f9994q;
            if (weakReference == null || (activity = weakReference.get()) == null) {
                return true;
            }
            return ((LoginAuthActivity) activity).queryCheckBoxIsChecked();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void o() {
        try {
            a(false, (String) null, (String) null, false);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public ActivityResultListener p() {
        try {
            return this.f9996s;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public void q() {
        try {
            if (this.f9995r != null) {
                TokenRet tokenRet = new TokenRet();
                tokenRet.setVendorName(this.f9998u.d());
                tokenRet.setCode(ResultCode.CODE_ERROR_LOAD_CUSTOM_VIEWS);
                tokenRet.setMsg(ResultCode.MSG_ERROR_LOAD_CUSTOM_VIEWS);
                this.f9995r.onTokenFailed(tokenRet.toJsonString());
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public AuthUIConfig r() {
        try {
            AuthUIConfig authUIConfig = this.f9984g;
            return authUIConfig == null ? f9978a : authUIConfig;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public LinkedHashMap<String, AuthRegisterViewConfig> s() {
        try {
            if (this.f9985h == null) {
                this.f9985h = new LinkedHashMap<>();
            }
            return this.f9985h;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public LinkedHashMap<String, AuthRegisterViewConfig> t() {
        try {
            if (this.f9986i == null) {
                this.f9986i = new LinkedHashMap<>();
            }
            return this.f9986i;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public ArrayList<AuthRegisterXmlConfig> u() {
        try {
            if (this.f9988k == null) {
                this.f9988k = new ArrayList<>();
            }
            return this.f9988k;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public ArrayList<AuthRegisterXmlConfig> v() {
        try {
            if (this.f9989l == null) {
                this.f9989l = new ArrayList<>();
            }
            return this.f9989l;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public void w() {
        try {
            try {
                LinkedHashMap<String, AuthRegisterViewConfig> linkedHashMap = this.f9985h;
                if (linkedHashMap != null) {
                    ArrayList<Object> arrayList = this.f9990m;
                    if (arrayList != null) {
                        arrayList.removeAll(linkedHashMap.values());
                    }
                    this.f9985h.clear();
                    this.f9985h = null;
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void x() {
        try {
            try {
                LinkedHashMap<String, AuthRegisterViewConfig> linkedHashMap = this.f9986i;
                if (linkedHashMap != null) {
                    ArrayList<Object> arrayList = this.f9991n;
                    if (arrayList != null) {
                        arrayList.removeAll(linkedHashMap.values());
                    }
                    this.f9986i.clear();
                    this.f9986i = null;
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void y() {
        try {
            try {
                ArrayList<AuthRegisterXmlConfig> arrayList = this.f9989l;
                if (arrayList != null) {
                    arrayList.clear();
                    this.f9989l = null;
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void z() {
        try {
            try {
                ArrayList<AuthRegisterXmlConfig> arrayList = this.f9988k;
                if (arrayList != null) {
                    ArrayList<Object> arrayList2 = this.f9990m;
                    if (arrayList2 != null) {
                        arrayList2.removeAll(arrayList);
                    }
                    this.f9988k.clear();
                    this.f9988k = null;
                }
            } catch (Exception e2) {
                i.a(e2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
