package com.mobile.auth.w;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.logger.model.ACMMonitorRecord;

/* loaded from: classes4.dex */
public class c extends a<ACMMonitorRecord> {
    @Override // com.mobile.auth.w.a
    public boolean a(String str) {
        try {
            com.mobile.auth.gatewayauth.b bVar = this.f10575a;
            if (bVar != null) {
                return bVar.a(str);
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
}
