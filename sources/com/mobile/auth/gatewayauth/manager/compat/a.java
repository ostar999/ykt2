package com.mobile.auth.gatewayauth.manager.compat;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.TokenRet;

/* loaded from: classes4.dex */
public class a implements ResultCodeProcessor {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r3) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.manager.compat.a.a(java.lang.String):java.lang.String");
    }

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public String convertCode(String str) {
        try {
            return a(str);
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

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public TokenRet convertErrorInfo(String str, String str2, String str3) {
        try {
            TokenRet tokenRet = new TokenRet();
            tokenRet.setCode(a(str));
            tokenRet.setMsg(str2);
            if (Constant.VENDOR_CUCC.equals(str3)) {
                tokenRet.setVendorName(Constant.VENDOR_CUXZ);
            } else {
                tokenRet.setVendorName(str3);
            }
            return tokenRet;
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

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public String getApiLevel() {
        try {
            return String.valueOf(1);
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
