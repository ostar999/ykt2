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
public abstract class b implements TimeoutCallable<com.mobile.auth.u.b> {

    /* renamed from: a, reason: collision with root package name */
    private e f10487a;

    /* renamed from: b, reason: collision with root package name */
    private String f10488b;

    /* renamed from: c, reason: collision with root package name */
    private long f10489c;

    /* renamed from: d, reason: collision with root package name */
    private String f10490d;

    public b(e eVar, String str, long j2, String str2) {
        this.f10487a = eVar;
        this.f10488b = str;
        this.f10489c = j2;
        this.f10490d = str2;
    }

    public com.mobile.auth.u.b a() {
        try {
            com.mobile.auth.u.b bVar = new com.mobile.auth.u.b(true);
            bVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            return bVar;
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

    public abstract void a(RequestCallback requestCallback, com.mobile.auth.gatewayauth.manager.a aVar);

    public com.mobile.auth.u.b b() {
        try {
            com.mobile.auth.gatewayauth.utils.e.a().a(this.f10490d, "doRequest", System.currentTimeMillis());
            com.mobile.auth.gatewayauth.manager.a aVarA = this.f10487a.a(this.f10488b);
            if (Constant.VENDOR_CMCC.equals(this.f10488b) && this.f10487a.a()) {
                aVarA = this.f10487a.a(Constant.VENDOR_NTCM);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final com.mobile.auth.u.b bVar = new com.mobile.auth.u.b(false);
            a(new RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.p.b.1
                public void a(a.C0199a c0199a) {
                    try {
                        bVar.a(true);
                        bVar.a(c0199a);
                        LoginPhoneInfo loginPhoneInfoBuild = LoginPhoneInfo.newLoginPhoneInfo().build();
                        loginPhoneInfoBuild.setVendor(c0199a.i());
                        loginPhoneInfoBuild.setChannelCode(c0199a.h());
                        loginPhoneInfoBuild.setDispatchFlag(c0199a.g());
                        loginPhoneInfoBuild.setProtocolName(c0199a.e());
                        loginPhoneInfoBuild.setProtocolUrl(c0199a.f());
                        loginPhoneInfoBuild.setPhoneNumber(c0199a.b());
                        bVar.a(com.mobile.auth.gatewayauth.manager.base.b.a().c(c0199a.c()).a(loginPhoneInfoBuild).a(c0199a.d()).a());
                        countDownLatch.countDown();
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
                        bVar.a(bVar2);
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
            }, aVarA);
            try {
                long j2 = this.f10489c;
                if (j2 <= 5000) {
                    j2 = 5000;
                }
                countDownLatch.await(j2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                bVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(Constant.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e2)));
            }
            return bVar;
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
    public /* synthetic */ com.mobile.auth.u.b onTimeout() {
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
