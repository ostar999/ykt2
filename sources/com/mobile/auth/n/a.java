package com.mobile.auth.n;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.PnsLoggerHandler;
import com.mobile.auth.gatewayauth.PnsReporter;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.manager.e;
import com.mobile.auth.gatewayauth.utils.i;
import com.nirvana.tools.logger.utils.ConsoleLogUtils;

/* loaded from: classes4.dex */
public class a implements PnsReporter {

    /* renamed from: a, reason: collision with root package name */
    private com.mobile.auth.o.a f10448a;

    /* renamed from: b, reason: collision with root package name */
    private d f10449b;

    /* renamed from: c, reason: collision with root package name */
    private e f10450c;

    public a(com.mobile.auth.o.a aVar, d dVar) {
        this.f10448a = aVar;
        this.f10449b = dVar;
    }

    private static boolean a() {
        try {
            Class.forName("com.nirvana.tools.logger.utils.ConsoleLogUtils");
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

    public void a(e eVar) {
        try {
            this.f10450c = eVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLogExtension(String str) {
        try {
            this.f10449b.c(str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLoggerEnable(boolean z2) {
        try {
            System.currentTimeMillis();
            i.a(z2);
            a();
            ConsoleLogUtils.setLoggerEnable(z2);
            e eVar = this.f10450c;
            if (eVar != null) {
                for (com.mobile.auth.gatewayauth.manager.a aVar : eVar.b()) {
                    if (aVar != null) {
                        aVar.a(z2);
                    }
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

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLoggerHandler(PnsLoggerHandler pnsLoggerHandler) {
        try {
            this.f10448a.a(pnsLoggerHandler);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setUploadEnable(boolean z2) {
        try {
            System.currentTimeMillis();
            i.b(z2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
