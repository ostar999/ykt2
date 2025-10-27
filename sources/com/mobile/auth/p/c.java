package com.mobile.auth.p;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.e;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.requestqueue.TimeoutCallable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class c implements TimeoutCallable<com.mobile.auth.u.c> {

    /* renamed from: a, reason: collision with root package name */
    private e f10494a;

    /* renamed from: b, reason: collision with root package name */
    private a.b f10495b;

    /* renamed from: c, reason: collision with root package name */
    private String f10496c;

    /* renamed from: d, reason: collision with root package name */
    private long f10497d;

    public c(e eVar, String str, a.b bVar, long j2) {
        this.f10494a = eVar;
        this.f10495b = bVar;
        this.f10496c = str;
        this.f10497d = j2;
    }

    public com.mobile.auth.u.c a() {
        try {
            com.mobile.auth.u.c cVar = new com.mobile.auth.u.c(true);
            cVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            return cVar;
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

    public com.mobile.auth.u.c b() {
        try {
            com.mobile.auth.gatewayauth.utils.e.a().a(this.f10495b.b(), "doRequest", System.currentTimeMillis());
            com.mobile.auth.gatewayauth.manager.a aVarA = this.f10494a.a(this.f10496c);
            if (Constant.VENDOR_CMCC.equals(this.f10496c) && this.f10494a.a()) {
                aVarA = this.f10494a.a(Constant.VENDOR_NTCM);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final com.mobile.auth.u.c cVar = new com.mobile.auth.u.c(false);
            aVarA.c(new RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.p.c.1
                public void a(a.C0199a c0199a) {
                    try {
                        cVar.a(true);
                        LoginPhoneInfo loginPhoneInfoBuild = LoginPhoneInfo.newLoginPhoneInfo().build();
                        loginPhoneInfoBuild.setVendor(c0199a.i());
                        loginPhoneInfoBuild.setChannelCode(c0199a.h());
                        loginPhoneInfoBuild.setDispatchFlag(c0199a.g());
                        loginPhoneInfoBuild.setProtocolName(c0199a.e());
                        loginPhoneInfoBuild.setProtocolUrl(c0199a.f());
                        loginPhoneInfoBuild.setPhoneNumber(c0199a.b());
                        cVar.a(com.mobile.auth.gatewayauth.manager.base.b.a().c(c0199a.c()).a(loginPhoneInfoBuild).a(c0199a.d()).a());
                        countDownLatch.countDown();
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        LoginPhoneInfo loginPhoneInfoBuild = LoginPhoneInfo.newLoginPhoneInfo().build();
                        if (bVar.h() != null) {
                            loginPhoneInfoBuild.setVendor(bVar.h().getVendor());
                        }
                        bVar.a(loginPhoneInfoBuild);
                        cVar.a(bVar);
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
                public /* synthetic */ void onSuccess(a.C0199a c0199a) {
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
            }, this.f10495b);
            try {
                long j2 = this.f10497d;
                if (j2 <= 5000) {
                    j2 = 5000;
                }
                countDownLatch.await(j2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                cVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(Constant.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e2)));
            }
            return cVar;
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
    public /* synthetic */ com.mobile.auth.u.c onTimeout() {
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
