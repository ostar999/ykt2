package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.RequestQueue;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private Context f10192a;

    /* renamed from: b, reason: collision with root package name */
    private com.mobile.auth.o.a f10193b;

    /* renamed from: c, reason: collision with root package name */
    private volatile ConfigRule f10194c;

    /* renamed from: d, reason: collision with root package name */
    private VendorSdkInfoManager f10195d;

    public b(Context context, VendorSdkInfoManager vendorSdkInfoManager, d dVar) {
        Context applicationContext = context.getApplicationContext();
        this.f10192a = applicationContext;
        this.f10194c = UTSharedPreferencesHelper.readSDKConfig(applicationContext);
        this.f10195d = vendorSdkInfoManager;
        this.f10193b = dVar.a();
        if (this.f10194c != null) {
            this.f10193b.a(this.f10194c);
        }
    }

    public static /* synthetic */ ConfigRule a(b bVar) {
        try {
            return bVar.f10194c;
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

    public static /* synthetic */ ConfigRule a(b bVar, ConfigRule configRule) {
        try {
            bVar.f10194c = configRule;
            return configRule;
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

    public static /* synthetic */ com.mobile.auth.o.a b(b bVar) {
        try {
            return bVar.f10193b;
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

    public static /* synthetic */ Context c(b bVar) {
        try {
            return bVar.f10192a;
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

    private boolean s() {
        try {
            return this.f10194c != null;
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

    private boolean t() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getAuth_token().getIs_limited())) {
                return this.f10194c.getAuth_token().getLimit_time_hour() > 0;
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

    private boolean u() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getLogin_token().getIs_limited())) {
                return this.f10194c.getLogin_token().getLimit_time_hour() > 0;
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

    private boolean v() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getSls().getIs_limited())) {
                return this.f10194c.getSls().getLimit_time_hour() > 0;
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

    private boolean w() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getGet_vendor_list().getIs_limited())) {
                return this.f10194c.getGet_vendor_list().getLimit_time_hour() > 0;
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

    private boolean x() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getLogin_page().getIs_limited())) {
                return this.f10194c.getLogin_page().getLimit_time_hour() > 0;
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

    private boolean y() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getLogin_phone().getIs_limited())) {
                return this.f10194c.getLogin_phone().getLimit_time_hour() > 0;
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

    private boolean z() {
        try {
            if (Boolean.parseBoolean(this.f10194c.getGet_config().getIs_limited())) {
                return this.f10194c.getGet_config().getLimit_time_hour() > 0;
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

    public void a(final RequestCallback<ConfigRule, Void> requestCallback) {
        try {
            if (r()) {
                requestCallback.onError(null);
                return;
            }
            q();
            RequestQueue.getInstance().pushRequest(new com.mobile.auth.t.a(new Callback<com.mobile.auth.u.a>(ThreadStrategy.THREAD, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) { // from class: com.mobile.auth.gatewayauth.manager.b.1
                public void a(com.mobile.auth.u.a aVar) {
                    try {
                        if (aVar.a() == null) {
                            requestCallback.onError(null);
                            return;
                        }
                        ConfigRule configRuleA = aVar.a();
                        if (b.a(b.this) == null || !b.a(b.this).toString().equals(configRuleA.toString())) {
                            b.a(b.this, configRuleA);
                            b.b(b.this).a(b.a(b.this));
                            UTSharedPreferencesHelper.clearLimitCount(b.c(b.this));
                            UTSharedPreferencesHelper.saveSDKConfig(b.c(b.this), b.a(b.this).toJsonString());
                        }
                        requestCallback.onSuccess(b.a(b.this));
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
                        a((com.mobile.auth.u.a) response);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, new com.mobile.auth.p.a(this.f10192a, this.f10195d, this.f10193b), 5000L, com.mobile.auth.u.a.class));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            if (s()) {
                return Boolean.parseBoolean(this.f10194c.getIs_demoted());
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

    public boolean a(int i2) {
        try {
            switch (i2) {
                case 1:
                    return h();
                case 2:
                    return j();
                case 3:
                    return p();
                case 4:
                    return d();
                case 5:
                    return f();
                case 6:
                    return n();
                case 7:
                    return l();
                default:
                    return false;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(int i2) {
        try {
            switch (i2) {
                case 1:
                    i();
                    break;
                case 2:
                    k();
                    break;
                case 3:
                    q();
                    break;
                case 4:
                    e();
                    break;
                case 5:
                    g();
                    break;
                case 6:
                    o();
                    break;
                case 7:
                    m();
                    break;
                default:
                    return;
            }
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
            if (!s()) {
                return false;
            }
            if (!a()) {
                if (!Boolean.parseBoolean(this.f10194c.getIs_auth_demoted())) {
                    return false;
                }
            }
            return true;
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

    public boolean c() {
        try {
            if (!s()) {
                return false;
            }
            if (!a()) {
                if (!Boolean.parseBoolean(this.f10194c.getIs_login_demoted())) {
                    return false;
                }
            }
            return true;
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

    public boolean d() {
        try {
            if (s() && t()) {
                return UTSharedPreferencesHelper.readAuthTokenLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getAuth_token().getLimit_time_hour())) >= this.f10194c.getAuth_token().getLimit_count();
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

    public void e() {
        try {
            if (s() && t()) {
                UTSharedPreferencesHelper.saveAuthTokenLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getAuth_token().getLimit_time_hour()));
            }
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
            if (s() && u()) {
                return UTSharedPreferencesHelper.readLoginTokenLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_token().getLimit_time_hour())) >= this.f10194c.getLogin_token().getLimit_count();
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

    public void g() {
        try {
            if (s() && u()) {
                UTSharedPreferencesHelper.saveLoginTokenLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_token().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean h() {
        try {
            if (s() && v()) {
                return UTSharedPreferencesHelper.readSLSLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getSls().getLimit_time_hour())) >= this.f10194c.getSls().getLimit_count();
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

    public void i() {
        try {
            if (s() && v()) {
                UTSharedPreferencesHelper.saveSLSLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getSls().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean j() {
        try {
            if (s() && w()) {
                return UTSharedPreferencesHelper.readVendorLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getGet_vendor_list().getLimit_time_hour())) >= this.f10194c.getGet_vendor_list().getLimit_count();
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

    public void k() {
        try {
            if (s() && w()) {
                UTSharedPreferencesHelper.saveVendorLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getGet_vendor_list().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean l() {
        try {
            if (s() && x()) {
                return UTSharedPreferencesHelper.readLoginPageLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_page().getLimit_time_hour())) >= this.f10194c.getLogin_page().getLimit_count();
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

    public void m() {
        try {
            if (s() && x()) {
                UTSharedPreferencesHelper.saveLoginPageLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_page().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean n() {
        try {
            if (s() && y()) {
                return UTSharedPreferencesHelper.readLoginPhoneLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_phone().getLimit_time_hour())) >= this.f10194c.getLogin_phone().getLimit_count();
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

    public void o() {
        try {
            if (s() && y()) {
                UTSharedPreferencesHelper.saveLoginPhoneLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getLogin_phone().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean p() {
        try {
            if (s() && z()) {
                return UTSharedPreferencesHelper.readConfigLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getGet_config().getLimit_time_hour())) >= this.f10194c.getGet_config().getLimit_count();
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

    public void q() {
        try {
            if (s() && z()) {
                UTSharedPreferencesHelper.saveConfigLimitCount(this.f10192a, com.mobile.auth.gatewayauth.utils.a.a(this.f10194c.getGet_config().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean r() {
        try {
            String sDKConfigLimitFlag = UTSharedPreferencesHelper.readSDKConfigLimitFlag(this.f10192a);
            if ((TextUtils.isEmpty(sDKConfigLimitFlag) || !com.mobile.auth.gatewayauth.utils.a.a(sDKConfigLimitFlag)) && !UTSharedPreferencesHelper.readSDKConfigCloseFlag(this.f10192a)) {
                return p();
            }
            return true;
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
