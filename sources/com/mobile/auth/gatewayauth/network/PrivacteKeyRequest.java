package com.mobile.auth.gatewayauth.network;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JsonerTag;

/* loaded from: classes4.dex */
public class PrivacteKeyRequest extends AuthRequest {

    @JsonerTag(keyName = "TerminalInfo")
    private String TerminalInfo;

    public String getTerminalInfo() {
        try {
            return this.TerminalInfo;
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

    public void setTerminalInfo(String str) {
        try {
            this.TerminalInfo = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
