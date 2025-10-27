package com.mobile.auth.gatewayauth.model.popsdkconfig;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.jsoner.Jsoner;
import com.nirvana.tools.jsoner.JsonerTag;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class SDKConfigData implements Jsoner {

    @JsonerTag(keyName = "AuthToken")
    private ConfigData AuthToken;

    @JsonerTag(keyName = "DownGradeInfoList")
    private List<DownGradleConfig> DownGradeInfoList;

    @JsonerTag(keyName = "GetConfig")
    private ConfigData GetConfig;

    @JsonerTag(keyName = "GetVendorList")
    private ConfigData GetVendorList;

    @JsonerTag(keyName = "IsAuthDemoted")
    private String IsAuthDemoted;

    @JsonerTag(keyName = "IsCrashDemoted")
    private String IsCrashDemoted;

    @JsonerTag(keyName = "IsDemoted")
    private String IsDemoted;

    @JsonerTag(keyName = "IsLoginDemoted")
    private String IsLoginDemoted;

    @JsonerTag(keyName = "IsSLSDemoted")
    private String IsSLSDemoted;

    @JsonerTag(keyName = "LoginPage")
    private ConfigData LoginPage;

    @JsonerTag(keyName = "LoginPhone")
    private ConfigData LoginPhone;

    @JsonerTag(keyName = "LoginToken")
    private ConfigData LoginToken;

    @JsonerTag(keyName = "Sls")
    private ConfigData Sls;

    @JsonerTag(keyName = "UploadLog")
    private UploadLogData UploadLog;

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
            if (jSONObject != null) {
                setGetVendorList((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("GetVendorList"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.1
                }, (List<Field>) null));
                setLoginPhone((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("LoginPhone"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.2
                }, (List<Field>) null));
                setLoginPage((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("LoginPage"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.3
                }, (List<Field>) null));
                setGetConfig((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("GetConfig"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.4
                }, (List<Field>) null));
                setAuthToken((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("AuthToken"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.5
                }, (List<Field>) null));
                setLoginToken((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("LoginToken"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.6
                }, (List<Field>) null));
                setSls((ConfigData) JSONUtils.fromJson(jSONObject.optJSONObject("Sls"), (JsonType) new JsonType<ConfigData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.7
                }, (List<Field>) null));
                setUploadLog((UploadLogData) JSONUtils.fromJson(jSONObject.optJSONObject("UploadLog"), (JsonType) new JsonType<UploadLogData>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.8
                }, (List<Field>) null));
                if (jSONObject.optJSONObject("DownGradeInfoList") != null) {
                    setDownGradeInfoList(JSONUtils.parseJsonArray2JsonerList(jSONObject.optJSONObject("DownGradeInfoList").optJSONArray("List"), new JsonType<DownGradleConfig>() { // from class: com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigData.9
                    }));
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

    public ConfigData getAuthToken() {
        try {
            return this.AuthToken;
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

    public List<DownGradleConfig> getDownGradeInfoList() {
        try {
            return this.DownGradeInfoList;
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

    public ConfigData getGetConfig() {
        try {
            return this.GetConfig;
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

    public ConfigData getGetVendorList() {
        try {
            return this.GetVendorList;
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

    public String getIsAuthDemoted() {
        try {
            return this.IsAuthDemoted;
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

    public String getIsCrashDemoted() {
        try {
            return this.IsCrashDemoted;
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

    public String getIsDemoted() {
        try {
            return this.IsDemoted;
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

    public String getIsLoginDemoted() {
        try {
            return this.IsLoginDemoted;
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

    public String getIsSLSDemoted() {
        try {
            return this.IsSLSDemoted;
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

    public ConfigData getLoginPage() {
        try {
            return this.LoginPage;
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

    public ConfigData getLoginPhone() {
        try {
            return this.LoginPhone;
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

    public ConfigData getLoginToken() {
        try {
            return this.LoginToken;
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

    public ConfigData getSls() {
        try {
            return this.Sls;
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

    public UploadLogData getUploadLog() {
        try {
            return this.UploadLog;
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

    public void setAuthToken(ConfigData configData) {
        try {
            this.AuthToken = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDownGradeInfoList(List<DownGradleConfig> list) {
        try {
            this.DownGradeInfoList = list;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setGetConfig(ConfigData configData) {
        try {
            this.GetConfig = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setGetVendorList(ConfigData configData) {
        try {
            this.GetVendorList = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIsAuthDemoted(String str) {
        try {
            this.IsAuthDemoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIsCrashDemoted(String str) {
        try {
            this.IsCrashDemoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIsDemoted(String str) {
        try {
            this.IsDemoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIsLoginDemoted(String str) {
        try {
            this.IsLoginDemoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIsSLSDemoted(String str) {
        try {
            this.IsSLSDemoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLoginPage(ConfigData configData) {
        try {
            this.LoginPage = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLoginPhone(ConfigData configData) {
        try {
            this.LoginPhone = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLoginToken(ConfigData configData) {
        try {
            this.LoginToken = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSls(ConfigData configData) {
        try {
            this.Sls = configData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setUploadLog(UploadLogData uploadLogData) {
        try {
            this.UploadLog = uploadLogData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.nirvana.tools.jsoner.Jsoner
    public JSONObject toJson() {
        try {
            return JSONUtils.toJson(this, null);
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
