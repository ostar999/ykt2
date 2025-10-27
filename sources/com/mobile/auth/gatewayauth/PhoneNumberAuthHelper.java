package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.utils.g;
import com.nirvana.tools.core.ExecutorManager;

/* loaded from: classes4.dex */
public class PhoneNumberAuthHelper {

    @AuthNumber
    public static final int SERVICE_TYPE_AUTH = 1;

    @AuthNumber
    public static final int SERVICE_TYPE_LOGIN = 2;

    /* renamed from: a, reason: collision with root package name */
    protected static volatile PhoneNumberAuthHelper f9778a;

    /* renamed from: b, reason: collision with root package name */
    private TokenResultListener f9779b;

    /* renamed from: c, reason: collision with root package name */
    private d f9780c;

    /* renamed from: d, reason: collision with root package name */
    private ResultCodeProcessor f9781d = new com.mobile.auth.gatewayauth.manager.compat.a();

    /* renamed from: e, reason: collision with root package name */
    private PhoneNumberAuthHelperProxy f9782e;

    private PhoneNumberAuthHelper(Context context, TokenResultListener tokenResultListener) {
        this.f9782e = PhoneNumberAuthHelperProxy.getInstance(context, tokenResultListener);
        this.f9779b = tokenResultListener;
        this.f9780c = new d(context, this.f9782e.b(), this.f9782e.a(), this);
    }

    public static /* synthetic */ ResultCodeProcessor a(PhoneNumberAuthHelper phoneNumberAuthHelper) {
        try {
            return phoneNumberAuthHelper.f9781d;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private boolean a(long j2, final String str, final ResultCodeProcessor resultCodeProcessor, final TokenResultListener tokenResultListener, LoginPhoneInfo loginPhoneInfo, final String str2) {
        if (loginPhoneInfo != null) {
            try {
                if (!TextUtils.isEmpty(loginPhoneInfo.getPhoneNumber())) {
                    try {
                        this.f9780c.a(j2, loginPhoneInfo.getPhoneNumber(), str, resultCodeProcessor, new e() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelper.3
                            @Override // com.mobile.auth.gatewayauth.e
                            public void a(String str3) {
                                try {
                                    PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).a(false, true, Constant.CODE_ERROR_START_AUTH_PAGE_FAIL, "唤起授权页失败", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_START_AUTH_PAGE_FAIL, "唤起授权页失败"), str, null, tokenResultListener, resultCodeProcessor, str2);
                                } catch (Throwable th) {
                                    ExceptionProcessor.processException(th);
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.e
                            public void a(String str3, String str4) {
                                try {
                                    PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).a(true, false, Constant.CODE_START_AUTH_PAGE_SUCCESS, "唤起授权页成功", "", str, null, tokenResultListener, resultCodeProcessor, str2);
                                } catch (Throwable th) {
                                    ExceptionProcessor.processException(th);
                                }
                            }
                        });
                        return true;
                    } catch (Throwable th) {
                        th = th;
                        ExceptionProcessor.processException(th);
                        return false;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean a(PhoneNumberAuthHelper phoneNumberAuthHelper, long j2, String str, ResultCodeProcessor resultCodeProcessor, TokenResultListener tokenResultListener, LoginPhoneInfo loginPhoneInfo, String str2) {
        try {
            return phoneNumberAuthHelper.a(j2, str, resultCodeProcessor, tokenResultListener, loginPhoneInfo, str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static /* synthetic */ PhoneNumberAuthHelperProxy b(PhoneNumberAuthHelper phoneNumberAuthHelper) {
        try {
            return phoneNumberAuthHelper.f9782e;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ d c(PhoneNumberAuthHelper phoneNumberAuthHelper) {
        try {
            return phoneNumberAuthHelper.f9780c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ TokenResultListener d(PhoneNumberAuthHelper phoneNumberAuthHelper) {
        try {
            return phoneNumberAuthHelper.f9779b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @AuthNumber
    public static PhoneNumberAuthHelper getInstance(Context context, TokenResultListener tokenResultListener) {
        try {
            if (f9778a == null && context != null) {
                synchronized (PhoneNumberAuthHelper.class) {
                    if (f9778a == null) {
                        f9778a = new PhoneNumberAuthHelper(context, tokenResultListener);
                    }
                }
            }
            f9778a.setAuthListener(tokenResultListener);
            return f9778a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @AuthNumber
    public static String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public void a(long j2, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor) {
        try {
            this.f9782e.a(j2, tokenResultListener, resultCodeProcessor);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void accelerateLoginPage(int i2, PreLoginResultListener preLoginResultListener) {
        try {
            this.f9782e.accelerateLoginPage(i2, preLoginResultListener, this.f9780c.D());
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void accelerateVerify(int i2, PreLoginResultListener preLoginResultListener) {
        try {
            this.f9782e.accelerateVerify(i2, preLoginResultListener);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void addAuthRegistViewConfig(String str, AuthRegisterViewConfig authRegisterViewConfig) {
        try {
            this.f9780c.a(str, authRegisterViewConfig);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void addAuthRegisterXmlConfig(AuthRegisterXmlConfig authRegisterXmlConfig) {
        try {
            this.f9780c.a(authRegisterXmlConfig);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void addPrivacyAuthRegistViewConfig(String str, AuthRegisterViewConfig authRegisterViewConfig) {
        try {
            this.f9780c.b(str, authRegisterViewConfig);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void addPrivacyRegisterXmlConfig(AuthRegisterXmlConfig authRegisterXmlConfig) {
        try {
            this.f9780c.b(authRegisterXmlConfig);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void checkBoxAnimationStart() {
        try {
            this.f9780c.m();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void checkEnvAvailable(@IntRange(from = 1, to = 2) int i2) {
        try {
            this.f9782e.checkEnvAvailable(i2, this.f9779b);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    @Deprecated
    public boolean checkEnvAvailable() {
        try {
            return this.f9782e.checkEnvAvailable();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    @AuthNumber
    public void clearPreInfo() {
        try {
            this.f9782e.clearPreInfo();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void closeAuthPageReturnBack(boolean z2) {
        try {
            d dVar = this.f9780c;
            if (dVar != null) {
                dVar.a(z2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void expandAuthPageCheckedScope(boolean z2) {
        try {
            d dVar = this.f9780c;
            if (dVar != null) {
                dVar.c(z2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public String getCurrentCarrierName() {
        try {
            return this.f9782e.getCurrentCarrierName();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @AuthNumber
    public void getLoginToken(final Context context, final int i2) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelper.2
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                public void a() {
                    try {
                        if (context instanceof Activity) {
                            PhoneNumberAuthHelper.c(PhoneNumberAuthHelper.this).b((Activity) context);
                        }
                        final String strC = PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).a().c();
                        final String strJ = PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).b().j();
                        if (!com.mobile.auth.gatewayauth.utils.d.a().b()) {
                            PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).a(true, false, Constant.CODE_ERROR_START_AUTH_PAGE_FAIL, Constant.MSG_ERROR_AUTHPAGE_FAIL, "", strC, null, PhoneNumberAuthHelper.d(PhoneNumberAuthHelper.this), PhoneNumberAuthHelper.a(PhoneNumberAuthHelper.this), strJ);
                        } else {
                            com.mobile.auth.gatewayauth.utils.d.a().a(false);
                            PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).getLoginMaskPhone(i2, strC, new OnLoginPhoneListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelper.2.1
                                @Override // com.mobile.auth.gatewayauth.OnLoginPhoneListener
                                public void onGetFailed(String str) {
                                    try {
                                        com.mobile.auth.gatewayauth.utils.d.a().a(true);
                                        if (PhoneNumberAuthHelper.d(PhoneNumberAuthHelper.this) != null) {
                                            PhoneNumberAuthHelper.d(PhoneNumberAuthHelper.this).onTokenFailed(str);
                                        }
                                    } catch (Throwable th) {
                                        ExceptionProcessor.processException(th);
                                    }
                                }

                                @Override // com.mobile.auth.gatewayauth.OnLoginPhoneListener
                                public void onGetLoginPhone(LoginPhoneInfo loginPhoneInfo) {
                                    try {
                                        PhoneNumberAuthHelper phoneNumberAuthHelper = PhoneNumberAuthHelper.this;
                                        PhoneNumberAuthHelper.a(phoneNumberAuthHelper, i2, strC, PhoneNumberAuthHelper.a(phoneNumberAuthHelper), PhoneNumberAuthHelper.d(PhoneNumberAuthHelper.this), loginPhoneInfo, strJ);
                                    } catch (Throwable th) {
                                        ExceptionProcessor.processException(th);
                                    }
                                }
                            }, true, true, strJ);
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

    @AuthNumber
    public PnsReporter getReporter() {
        try {
            return this.f9782e.getReporter();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @AuthNumber
    public void getVerifyToken(int i2) {
        try {
            this.f9782e.getVerifyToken(i2, this.f9779b);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void hideLoginLoading() {
        try {
            this.f9780c.k();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void keepAllPageHideNavigationBar() {
        try {
            d dVar = this.f9780c;
            if (dVar != null) {
                dVar.c();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void keepAuthPageLandscapeFullSreen(boolean z2) {
        try {
            d dVar = this.f9780c;
            if (dVar != null) {
                dVar.b(z2);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void openUserPage(Class<?> cls, int i2, int i3) {
        try {
            this.f9780c.a(cls, i2, i3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void privacyAnimationStart() {
        try {
            this.f9780c.l();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void prohibitUseUtdid() {
        try {
            this.f9782e.prohibitUseUtdid();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public boolean queryCheckBoxIsChecked() {
        try {
            return this.f9780c.n();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    @AuthNumber
    public void quitLoginPage() {
        try {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            this.f9780c.a(this.f9781d);
            this.f9780c.o();
            com.mobile.auth.gatewayauth.utils.d.a().a(true);
            final long jCurrentTimeMillis2 = System.currentTimeMillis();
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).c().a(PhoneNumberAuthHelper.b(PhoneNumberAuthHelper.this).b().b("", Constant.ACTION_SDK_QUIT_AUTH_PAGE, UStruct.newUStruct().startTime(jCurrentTimeMillis).endTime(jCurrentTimeMillis2).build(), PhoneNumberAuthHelper.a(PhoneNumberAuthHelper.this).getApiLevel()), 2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void quitPrivacyPage() {
        try {
            this.f9780c.A();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void removeAuthRegisterViewConfig() {
        try {
            this.f9780c.w();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void removeAuthRegisterXmlConfig() {
        try {
            this.f9780c.z();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void removePrivacyAuthRegisterViewConfig() {
        try {
            this.f9780c.x();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void removePrivacyRegisterXmlConfig() {
        try {
            this.f9780c.y();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setActivityResultListener(ActivityResultListener activityResultListener) {
        try {
            this.f9780c.a(activityResultListener);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setAuthListener(TokenResultListener tokenResultListener) {
        try {
            this.f9779b = tokenResultListener;
            this.f9782e.setAuthListener(tokenResultListener);
            this.f9780c.a(tokenResultListener);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setAuthPageUseDayLight(boolean z2) {
        try {
            this.f9780c.e(z2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setAuthSDKInfo(String str) {
        try {
            this.f9782e.setAuthSDKInfo(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setAuthUIConfig(AuthUIConfig authUIConfig) {
        try {
            this.f9780c.a(authUIConfig);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setProtocolChecked(boolean z2) {
        try {
            this.f9780c.f(z2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void setUIClickListener(AuthUIControlClickListener authUIControlClickListener) {
        try {
            this.f9780c.a(authUIControlClickListener);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @AuthNumber
    public void userControlAuthPageCancel() {
        try {
            d dVar = this.f9780c;
            if (dVar != null) {
                dVar.d(true);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
