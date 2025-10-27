package com.mobile.auth.gatewayauth.network;

import com.aliyun.auth.core.AliyunVodKey;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JsonerTag;
import com.tencent.smtt.export.external.TbsCoreSettings;

/* loaded from: classes4.dex */
public class UploadRequest extends AuthRequest {

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_ACCESSKEYID)
    private String AccessKeyId;

    @JsonerTag(keyName = TbsCoreSettings.TBS_SETTINGS_APP_KEY)
    private String AppKey = BuildConfig.APP_KEY;

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_SECURITY_TOKEN)
    private String SecurityToken;

    @JsonerTag(keyName = "UploadInfo")
    private String UploadInfo;

    public String getAccessKeyId() {
        try {
            return this.AccessKeyId;
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

    public String getSecurityToken() {
        try {
            return this.SecurityToken;
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

    public String getUploadInfo() {
        try {
            return this.UploadInfo;
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

    public void setAccessKeyId(String str) {
        try {
            this.AccessKeyId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSecurityToken(String str) {
        try {
            this.SecurityToken = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setUploadInfo(String str) {
        try {
            this.UploadInfo = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
