package com.mobile.auth.gatewayauth.network;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JsonerTag;

/* loaded from: classes4.dex */
public class DispatchInfoRequest extends AuthRequest {

    @JsonerTag(keyName = "Ability")
    private String Ability;

    @JsonerTag(keyName = "SceneCode")
    private String SceneCode;

    @JsonerTag(keyName = "SdkVersion")
    private String SdkVersion;

    @JsonerTag(keyName = "TerminalInfo")
    private String TerminalInfo;

    public String getAbility() {
        try {
            return this.Ability;
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

    public String getSceneCode() {
        try {
            return this.SceneCode;
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

    public String getSdkVersion() {
        try {
            return this.SdkVersion;
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

    public void setAbility(String str) {
        try {
            this.Ability = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSceneCode(String str) {
        try {
            this.SceneCode = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSdkVersion(String str) {
        try {
            this.SdkVersion = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
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
