package com.mobile.auth.gatewayauth.network;

import com.heytap.mcssdk.constant.b;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.Jsoner;
import com.nirvana.tools.jsoner.JsonerTag;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DispatchInfoItem implements Jsoner {

    @JsonerTag(keyName = "appId")
    private String appId;

    @JsonerTag(keyName = b.A)
    private String appSecret;

    @JsonerTag(keyName = "channelCode")
    private String channelCode;

    @JsonerTag(keyName = "dispatchRatio")
    private int dispatchRatio;

    @JsonerTag(keyName = "operator")
    private int operator;

    @JsonerTag(keyName = "supplierSdkType")
    private String supplierSdkType;

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
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

    public String getAppSecret() {
        try {
            return this.appSecret;
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

    public String getChannelCode() {
        try {
            return this.channelCode;
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

    public int getDispatchRatio() {
        try {
            return this.dispatchRatio;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public int getOperator() {
        try {
            return this.operator;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public String getSupplierSdkType() {
        try {
            return this.supplierSdkType;
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

    public void setAppSecret(String str) {
        try {
            this.appSecret = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setChannelCode(String str) {
        try {
            this.channelCode = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDispatchRatio(int i2) {
        try {
            this.dispatchRatio = i2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setOperator(int i2) {
        try {
            this.operator = i2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSupplierSdkType(String str) {
        try {
            this.supplierSdkType = str;
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
