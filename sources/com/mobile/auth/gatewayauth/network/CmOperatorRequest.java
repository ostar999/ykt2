package com.mobile.auth.gatewayauth.network;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.EncryptUtils;
import com.nirvana.tools.jsoner.JsonerTag;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CmOperatorRequest {
    public static final String ENCRYPT_KEY = "2Dsx3oz01nIK5ob4";

    @JsonerTag(keyName = "appId")
    private String appId;

    @JsonerTag(keyName = "ct")
    private String ct;

    @JsonerTag(keyName = "reqId")
    private String reqId = UUID.randomUUID().toString();

    public String buildPopRequestParamas() {
        try {
            String strHashWithSalt = EncryptUtils.hashWithSalt("reqId=" + this.reqId + "&appId=" + this.appId + "&ct=" + this.ct, "2Dsx3oz01nIK5ob4");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("reqId", this.reqId);
                jSONObject.put("appId", this.appId);
                jSONObject.put("ct", this.ct);
                jSONObject.put("sign", strHashWithSalt);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return jSONObject.toString();
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

    public String getAppId() {
        try {
            return this.appId;
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

    public String getCt() {
        try {
            return this.ct;
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

    public String getReqId() {
        try {
            return this.reqId;
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

    public void setAppId(String str) {
        try {
            this.appId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setCt(String str) {
        try {
            this.ct = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setReqId(String str) {
        try {
            this.reqId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
