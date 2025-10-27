package com.mobile.auth.gatewayauth.model;

import cn.hutool.core.text.CharPool;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.Serializable;

/* loaded from: classes4.dex */
public class TerminalInfo implements Serializable {
    private String accessCode;
    private String apiCode;
    private String appVersion;
    private String certifyId;
    private String channelCode;
    private String csrf;
    private String customId;
    private String deviceBrand;
    private String deviceName;
    private boolean dispatchFlag;
    private String iccid;
    private String imei;
    private String imsi;
    private String innerIP;
    private String mark;
    private String metaInfo;
    private String networkType;
    private String operatorCode;
    private String osType = "Android";
    private String osVersion;
    private String packageName;
    private String phoneNumber;
    private String sceneCode;
    private String sdkVersion;
    private String sign;
    private String timeStamp;
    private String uniqueId;
    private String vendorKey;

    public String getAccessCode() {
        try {
            return this.accessCode;
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

    public String getApiCode() {
        try {
            return this.apiCode;
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

    public String getAppVersion() {
        try {
            return this.appVersion;
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

    public String getCertifyId() {
        try {
            return this.certifyId;
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

    public String getCsrf() {
        try {
            return this.csrf;
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

    public String getCustomId() {
        try {
            return this.customId;
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

    public String getDeviceBrand() {
        try {
            return this.deviceBrand;
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

    public String getDeviceName() {
        try {
            return this.deviceName;
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

    public String getIccid() {
        try {
            return this.iccid;
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

    public String getImei() {
        try {
            return this.imei;
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

    public String getImsi() {
        try {
            return this.imsi;
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

    public String getInnerIP() {
        try {
            return this.innerIP;
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

    public String getMark() {
        try {
            return this.mark;
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

    public String getMetaInfo() {
        try {
            return this.metaInfo;
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

    public String getNetworkType() {
        try {
            return this.networkType;
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

    public String getOperatorCode() {
        try {
            return this.operatorCode;
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

    public String getOsType() {
        try {
            return this.osType;
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

    public String getOsVersion() {
        try {
            return this.osVersion;
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

    public String getPackageName() {
        try {
            return this.packageName;
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

    public String getPhoneNumber() {
        try {
            return this.phoneNumber;
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
            return this.sceneCode;
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
            return this.sdkVersion;
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

    public String getSign() {
        try {
            return this.sign;
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

    public String getTimeStamp() {
        try {
            return this.timeStamp;
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

    public String getUniqueId() {
        try {
            return this.uniqueId;
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

    public String getVendorKey() {
        try {
            return this.vendorKey;
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

    public boolean isDispatchFlag() {
        try {
            return this.dispatchFlag;
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

    public void setAccessCode(String str) {
        try {
            this.accessCode = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setApiCode(String str) {
        try {
            this.apiCode = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setAppVersion(String str) {
        try {
            this.appVersion = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setCertifyId(String str) {
        try {
            this.certifyId = str;
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

    public void setCsrf(String str) {
        try {
            this.csrf = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setCustomId(String str) {
        try {
            this.customId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDeviceBrand(String str) {
        try {
            this.deviceBrand = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDeviceName(String str) {
        try {
            this.deviceName = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDispatchFlag(boolean z2) {
        try {
            this.dispatchFlag = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIccid(String str) {
        try {
            this.iccid = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setImei(String str) {
        try {
            this.imei = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setImsi(String str) {
        try {
            this.imsi = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setInnerIP(String str) {
        try {
            this.innerIP = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setMark(String str) {
        try {
            this.mark = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setMetaInfo(String str) {
        try {
            this.metaInfo = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setNetworkType(String str) {
        try {
            this.networkType = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setOperatorCode(String str) {
        try {
            this.operatorCode = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setOsType(String str) {
        try {
            this.osType = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setOsVersion(String str) {
        try {
            this.osVersion = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setPackageName(String str) {
        try {
            this.packageName = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setPhoneNumber(String str) {
        try {
            this.phoneNumber = str;
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
            this.sceneCode = str;
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
            this.sdkVersion = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSign(String str) {
        try {
            this.sign = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setTimeStamp(String str) {
        try {
            this.timeStamp = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setUniqueId(String str) {
        try {
            this.uniqueId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setVendorKey(String str) {
        try {
            this.vendorKey = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String toString() {
        try {
            return "TerminalInfo{accessCode='" + this.accessCode + CharPool.SINGLE_QUOTE + ", packageName='" + this.packageName + CharPool.SINGLE_QUOTE + ", appVersion='" + this.appVersion + CharPool.SINGLE_QUOTE + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + ", deviceName='" + this.deviceName + CharPool.SINGLE_QUOTE + ", imsi='" + this.imsi + CharPool.SINGLE_QUOTE + ", imei='" + this.imei + CharPool.SINGLE_QUOTE + ", iccid='" + this.iccid + CharPool.SINGLE_QUOTE + ", operatorCode='" + this.operatorCode + CharPool.SINGLE_QUOTE + ", osVersion='" + this.osVersion + CharPool.SINGLE_QUOTE + ", phoneNumber='" + this.phoneNumber + CharPool.SINGLE_QUOTE + ", vendorKey='" + this.vendorKey + CharPool.SINGLE_QUOTE + ", sdkVersion='" + this.sdkVersion + CharPool.SINGLE_QUOTE + ", deviceBrand='" + this.deviceBrand + CharPool.SINGLE_QUOTE + ", networkType='" + this.networkType + CharPool.SINGLE_QUOTE + ", innerIP='" + this.innerIP + CharPool.SINGLE_QUOTE + ", csrf='" + this.csrf + CharPool.SINGLE_QUOTE + ", osType='" + this.osType + CharPool.SINGLE_QUOTE + ", sceneCode='" + this.sceneCode + CharPool.SINGLE_QUOTE + ", uniqueId='" + this.uniqueId + CharPool.SINGLE_QUOTE + ", customId='" + this.customId + CharPool.SINGLE_QUOTE + ", metaInfo='" + this.metaInfo + CharPool.SINGLE_QUOTE + ", certifyId='" + this.certifyId + CharPool.SINGLE_QUOTE + ", dispatchFlag='" + this.dispatchFlag + CharPool.SINGLE_QUOTE + ", channelCode='" + this.channelCode + CharPool.SINGLE_QUOTE + ", timeStamp='" + this.timeStamp + CharPool.SINGLE_QUOTE + '}';
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
