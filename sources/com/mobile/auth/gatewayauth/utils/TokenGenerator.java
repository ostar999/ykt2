package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;

/* loaded from: classes4.dex */
public class TokenGenerator {

    /* renamed from: a, reason: collision with root package name */
    private com.mobile.auth.o.a f10286a;

    /* renamed from: b, reason: collision with root package name */
    private SystemManager f10287b;

    /* renamed from: c, reason: collision with root package name */
    private VendorSdkInfoManager f10288c;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public TokenGenerator(com.mobile.auth.o.a aVar, SystemManager systemManager, VendorSdkInfoManager vendorSdkInfoManager) {
        this.f10286a = aVar;
        this.f10287b = systemManager;
        this.f10288c = vendorSdkInfoManager;
    }

    @SafeProtector
    private native String assembleCustomizeToken(Context context, String str, String str2, String str3, String str4, String str5, String str6);

    @SafeProtector
    private native String generateCsrf(String str);

    public String a(Context context, String str, String str2, String str3, boolean z2, String str4, String str5, String str6, boolean z3, String str7, boolean z4, String str8) {
        try {
            return assembleToken(context, null, null, null, str, str2, str3, z2, str4, str5, str6, z3, str7, z4, str8);
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x002e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @com.mobile.auth.gatewayauth.annotations.SafeProtector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String assembleToken(android.content.Context r26, java.lang.String r27, java.lang.String r28, java.lang.String r29, java.lang.String r30, java.lang.String r31, java.lang.String r32, boolean r33, java.lang.String r34, java.lang.String r35, java.lang.String r36, boolean r37, java.lang.String r38, boolean r39, java.lang.String r40) {
        /*
            r25 = this;
            r9 = r25
            r10 = 0
            java.lang.String r0 = ""
            if (r33 == 0) goto Le
            r4 = r31
            java.lang.String r0 = r9.generateCsrf(r4)     // Catch: java.lang.Throwable -> L81
            goto L10
        Le:
            r4 = r31
        L10:
            r19 = r0
            r8 = 1
            r24 = 0
            if (r37 == 0) goto L27
            java.lang.String r0 = "rpk"
            r2 = r26
            java.lang.String r0 = com.mobile.auth.gatewayauth.utils.k.g(r2, r0)     // Catch: java.lang.Throwable -> L81
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L81
            if (r1 != 0) goto L2a
            r1 = r8
            goto L2c
        L27:
            r2 = r26
            r0 = r10
        L2a:
            r1 = r24
        L2c:
            if (r1 != 0) goto L5c
            com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager r0 = r9.f10288c     // Catch: java.io.UnsupportedEncodingException -> L59 java.lang.Throwable -> L81
            java.lang.String r15 = r0.b()     // Catch: java.io.UnsupportedEncodingException -> L59 java.lang.Throwable -> L81
            r20 = 1
            r11 = r26
            r12 = r27
            r13 = r28
            r14 = r29
            r16 = r30
            r17 = r31
            r18 = r32
            r21 = r38
            r22 = r39
            r23 = r40
            java.lang.String r0 = com.mobile.auth.gatewayauth.utils.EncryptUtils.encryptToken(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)     // Catch: java.io.UnsupportedEncodingException -> L59 java.lang.Throwable -> L81
            java.lang.String r1 = "UTF-8"
            byte[] r0 = r0.getBytes(r1)     // Catch: java.io.UnsupportedEncodingException -> L59 java.lang.Throwable -> L81
            java.lang.String r0 = com.nirvana.tools.core.CryptUtil.Base64.encode(r0)     // Catch: java.io.UnsupportedEncodingException -> L59 java.lang.Throwable -> L81
            goto L70
        L59:
            r0 = move-exception
            r11 = r8
            goto L73
        L5c:
            r1 = r25
            r2 = r26
            r3 = r30
            r4 = r31
            r5 = r34
            r6 = r35
            r7 = r36
            r11 = r8
            r8 = r0
            java.lang.String r0 = r1.assembleCustomizeToken(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.io.UnsupportedEncodingException -> L72 java.lang.Throwable -> L81
        L70:
            r10 = r0
            goto L80
        L72:
            r0 = move-exception
        L73:
            com.mobile.auth.o.a r1 = r9.f10286a     // Catch: java.lang.Throwable -> L81
            java.lang.String[] r2 = new java.lang.String[r11]     // Catch: java.lang.Throwable -> L81
            java.lang.String r0 = com.nirvana.tools.core.ExecutorManager.getErrorInfoFromException(r0)     // Catch: java.lang.Throwable -> L81
            r2[r24] = r0     // Catch: java.lang.Throwable -> L81
            r1.e(r2)     // Catch: java.lang.Throwable -> L81
        L80:
            return r10
        L81:
            r0 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)     // Catch: java.lang.Throwable -> L86
            return r10
        L86:
            r0 = move-exception
            r1 = r0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r1)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.utils.TokenGenerator.assembleToken(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, boolean, java.lang.String):java.lang.String");
    }
}
