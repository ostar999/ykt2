package com.mobile.auth.p;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;
import com.mobile.auth.gatewayauth.model.psc_sdk_config.ConfigRB;
import com.mobile.auth.gatewayauth.network.RequestState;
import com.mobile.auth.gatewayauth.network.RequestUtil;
import com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper;
import com.mobile.auth.gatewayauth.utils.EncryptUtils;
import com.nirvana.tools.requestqueue.TimeoutCallable;
import com.plv.foundationsdk.web.PLVWebview;

/* loaded from: classes4.dex */
public class a implements TimeoutCallable<com.mobile.auth.u.a> {

    /* renamed from: a, reason: collision with root package name */
    private Context f10484a;

    /* renamed from: b, reason: collision with root package name */
    private com.mobile.auth.o.a f10485b;

    /* renamed from: c, reason: collision with root package name */
    private VendorSdkInfoManager f10486c;

    public a(Context context, VendorSdkInfoManager vendorSdkInfoManager, com.mobile.auth.o.a aVar) {
        this.f10484a = context;
        this.f10485b = aVar;
        this.f10486c = vendorSdkInfoManager;
    }

    public com.mobile.auth.u.a a() {
        try {
            return new com.mobile.auth.u.a(true);
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

    public com.mobile.auth.u.a b() throws Exception {
        try {
            if (!RequestState.getInstance().checkTokenValied(1)) {
                this.f10485b.e("request expiration date out");
                return new com.mobile.auth.u.a(false);
            }
            try {
                EncryptUtils.generateAesKey();
                String sDKConfigByPop = RequestUtil.getSDKConfigByPop(RequestState.getInstance().getKeyRespone().getSk(), EncryptUtils.noEncryptTinfo(this.f10484a, this.f10486c.b(), com.mobile.auth.gatewayauth.utils.c.b(this.f10484a)));
                this.f10485b.a("getSdkConfig Ret:", sDKConfigByPop);
                ConfigRB configRBFromJson = ConfigRB.fromJson(sDKConfigByPop);
                if (configRBFromJson != null) {
                    if (configRBFromJson.getResponse() == null || configRBFromJson.getResponse().getResult() == null) {
                        if (configRBFromJson.getErrorResponse() != null) {
                            if (configRBFromJson.getErrorResponse().getCode() == 22) {
                                UTSharedPreferencesHelper.saveSDKConfigCloseFlag(this.f10484a, true);
                            } else if (configRBFromJson.getErrorResponse().getCode() == 7) {
                                UTSharedPreferencesHelper.saveSDKConfigLimitFlag(this.f10484a, com.mobile.auth.gatewayauth.utils.a.a());
                            }
                        }
                    } else if (configRBFromJson.getResponse().getResult().getModel() != null && PLVWebview.MESSAGE_OK.equals(configRBFromJson.getResponse().getResult().getCode())) {
                        return new com.mobile.auth.u.a(false, configRBFromJson.getResponse().getResult().getModel());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return new com.mobile.auth.u.a(false);
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

    @Override // java.util.concurrent.Callable
    public /* synthetic */ Object call() throws Exception {
        try {
            return b();
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

    @Override // com.nirvana.tools.requestqueue.TimeoutCallable
    public /* synthetic */ com.mobile.auth.u.a onTimeout() {
        try {
            return a();
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
