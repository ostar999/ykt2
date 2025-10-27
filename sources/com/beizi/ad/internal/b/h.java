package com.beizi.ad.internal.b;

import android.app.Activity;
import android.view.ViewGroup;
import com.beizi.ad.internal.network.ServerResponse;

/* loaded from: classes2.dex */
public class h extends b {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0057 A[Catch: Error -> 0x0063, Exception -> 0x0070, TRY_LEAVE, TryCatch #2 {Error -> 0x0063, Exception -> 0x0070, blocks: (B:7:0x0028, B:9:0x002e, B:10:0x0057), top: B:20:0x0028 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private h(android.app.Activity r10, com.beizi.ad.internal.e r11, com.beizi.ad.internal.b.a r12, com.beizi.ad.internal.b r13, android.view.ViewGroup r14, com.beizi.ad.internal.network.ServerResponse r15) {
        /*
            r9 = this;
            com.beizi.ad.internal.l r4 = com.beizi.ad.internal.l.SPLASH
            r0 = r9
            r1 = r11
            r2 = r12
            r3 = r13
            r5 = r15
            r0.<init>(r1, r2, r3, r4, r5)
            java.lang.Class<com.beizi.ad.internal.b.i> r11 = com.beizi.ad.internal.b.i.class
            boolean r11 = r9.a(r11)
            if (r11 != 0) goto L13
            return
        L13:
            java.lang.String r11 = com.beizi.ad.internal.utilities.HaoboLog.mediationLogTag
            int r12 = com.beizi.ad.R.string.mediated_request
            java.lang.String r12 = com.beizi.ad.internal.utilities.HaoboLog.getString(r12)
            com.beizi.ad.internal.utilities.HaoboLog.d(r11, r12)
            r9.f()
            r9.h()
            r11 = -1
            r12 = 0
            if (r10 == 0) goto L57
            boolean r13 = r9.e()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            if (r13 != 0) goto L57
            com.beizi.ad.b.b r13 = r9.f4016b     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            r0 = r13
            com.beizi.ad.internal.b.i r0 = (com.beizi.ad.internal.b.i) r0     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.internal.b.a r13 = r9.f4017c     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            java.lang.String r3 = r13.b()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.internal.b.a r13 = r9.f4017c     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            java.lang.String r4 = r13.e()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.internal.b.a r13 = r9.f4017c     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            int r5 = r13.c()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.internal.b.a r13 = r9.f4017c     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            int r6 = r13.d()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.b.a r7 = r9.a()     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            r1 = r9
            r2 = r10
            r8 = r14
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            r12 = r11
            goto L7c
        L57:
            java.lang.String r10 = com.beizi.ad.internal.utilities.HaoboLog.mediationLogTag     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            int r13 = com.beizi.ad.R.string.mediated_request_null_activity     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            java.lang.String r13 = com.beizi.ad.internal.utilities.HaoboLog.getString(r13)     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            com.beizi.ad.internal.utilities.HaoboLog.e(r10, r13)     // Catch: java.lang.Error -> L63 java.lang.Exception -> L70
            goto L7c
        L63:
            r10 = move-exception
            java.lang.String r13 = com.beizi.ad.internal.utilities.HaoboLog.mediationLogTag
            int r14 = com.beizi.ad.R.string.mediated_request_error
            java.lang.String r14 = com.beizi.ad.internal.utilities.HaoboLog.getString(r14)
            com.beizi.ad.internal.utilities.HaoboLog.e(r13, r14, r10)
            goto L7c
        L70:
            r10 = move-exception
            java.lang.String r13 = com.beizi.ad.internal.utilities.HaoboLog.mediationLogTag
            int r14 = com.beizi.ad.R.string.mediated_request_exception
            java.lang.String r14 = com.beizi.ad.internal.utilities.HaoboLog.getString(r14)
            com.beizi.ad.internal.utilities.HaoboLog.e(r13, r14, r10)
        L7c:
            if (r12 == r11) goto L81
            r9.a(r12)
        L81:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.b.h.<init>(android.app.Activity, com.beizi.ad.internal.e, com.beizi.ad.internal.b.a, com.beizi.ad.internal.b, android.view.ViewGroup, com.beizi.ad.internal.network.ServerResponse):void");
    }

    public static h a(Activity activity, com.beizi.ad.internal.e eVar, a aVar, com.beizi.ad.internal.b bVar, ViewGroup viewGroup, ServerResponse serverResponse) {
        h hVar = new h(activity, eVar, aVar, bVar, viewGroup, serverResponse);
        if (hVar.f4021g) {
            return null;
        }
        return hVar;
    }

    @Override // com.beizi.ad.internal.b.b
    public boolean c() {
        return true;
    }

    @Override // com.beizi.ad.internal.b.b
    public void d() {
    }

    @Override // com.beizi.ad.internal.b.b
    public void j() {
        this.f4023i = true;
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.f();
        }
    }

    @Override // com.beizi.ad.internal.b.b
    public void k() {
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.d();
        }
    }

    @Override // com.beizi.ad.internal.b.b
    public void l() {
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.e();
        }
    }
}
