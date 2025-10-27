package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.huawei.hms.framework.common.Logger;
import java.util.Locale;

/* loaded from: classes4.dex */
public class GrsBaseInfo implements Cloneable {
    private static final String TAG = "GrsBaseInfo";
    private String androidVersion;
    private String appName;
    private String countrySource;
    private String deviceModel;
    private String issueCountry;
    private String regCountry;
    private String romVersion;
    private String serCountry;
    private String uid;
    private String versionName;

    public @interface CountryCodeSource {
        public static final String APP = "APP";
        public static final String LOCALE_INFO = "LOCALE_INFO";
        public static final String NETWORK_COUNTRY = "NETWORK_COUNTRY";
        public static final String SIM_COUNTRY = "SIM_COUNTRY";
        public static final String UNKNOWN = "UNKNOWN";
        public static final String VENDOR_COUNTRY = "VENDOR_COUNTRY";
    }

    public GrsBaseInfo() {
    }

    public GrsBaseInfo(Context context) {
        this.issueCountry = GrsApp.getInstance().getIssueCountryCode(context);
    }

    private StringBuffer getStringBuffer(StringBuffer stringBuffer, boolean z2, Context context) {
        String androidVersion = getAndroidVersion();
        if (!TextUtils.isEmpty(androidVersion)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("android_version");
            stringBuffer.append("=");
            stringBuffer.append(androidVersion);
        }
        String romVersion = getRomVersion();
        if (!TextUtils.isEmpty(romVersion)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("rom_version");
            stringBuffer.append("=");
            stringBuffer.append(romVersion);
        }
        String deviceModel = getDeviceModel();
        if (!TextUtils.isEmpty(deviceModel)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("device_model");
            stringBuffer.append("=");
            stringBuffer.append(deviceModel);
        }
        String countrySource = getCountrySource();
        if (!TextUtils.isEmpty(countrySource)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("country_source");
            stringBuffer.append("=");
            stringBuffer.append(countrySource);
        }
        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            stringBuffer.append("&");
        }
        stringBuffer.append("package_name");
        stringBuffer.append("=");
        stringBuffer.append(context.getPackageName());
        return stringBuffer;
    }

    private boolean isEqual(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public GrsBaseInfo m96clone() {
        return (GrsBaseInfo) super.clone();
    }

    public boolean compare(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || GrsBaseInfo.class != obj.getClass() || !(obj instanceof GrsBaseInfo)) {
            return false;
        }
        GrsBaseInfo grsBaseInfo = (GrsBaseInfo) obj;
        return isEqual(this.serCountry, grsBaseInfo.serCountry) && isEqual(this.versionName, grsBaseInfo.versionName) && isEqual(this.appName, grsBaseInfo.appName) && isEqual(this.uid, grsBaseInfo.uid) && isEqual(this.regCountry, grsBaseInfo.regCountry) && isEqual(this.issueCountry, grsBaseInfo.issueCountry) && isEqual(this.androidVersion, grsBaseInfo.androidVersion) && isEqual(this.romVersion, grsBaseInfo.romVersion) && isEqual(this.deviceModel, grsBaseInfo.deviceModel) && isEqual(this.countrySource, grsBaseInfo.countrySource);
    }

    public GrsBaseInfo copy() {
        GrsBaseInfo grsBaseInfo = new GrsBaseInfo();
        grsBaseInfo.setAppName(this.appName);
        grsBaseInfo.setSerCountry(this.serCountry);
        grsBaseInfo.setRegCountry(this.regCountry);
        grsBaseInfo.setIssueCountry(this.issueCountry);
        grsBaseInfo.setCountrySource(this.countrySource);
        grsBaseInfo.setAndroidVersion(this.androidVersion);
        grsBaseInfo.setDeviceModel(this.deviceModel);
        grsBaseInfo.setRomVersion(this.romVersion);
        grsBaseInfo.setUid(this.uid);
        grsBaseInfo.setVersionName(this.versionName);
        return grsBaseInfo;
    }

    public String getAndroidVersion() {
        return this.androidVersion;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getCountrySource() {
        return this.countrySource;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public String getGrsParasKey(boolean z2, boolean z3, Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        com.huawei.hms.framework.network.grs.f.b bVarA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this);
        com.huawei.hms.framework.network.grs.local.model.a aVarA = bVarA != null ? bVarA.a() : null;
        String grsReqParamJoint = getGrsReqParamJoint(z2, z3, aVarA != null ? aVarA.b() : "", context);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append(grsReqParamJoint);
        }
        return stringBuffer.toString();
    }

    public String getGrsReqParamJoint(boolean z2, boolean z3, String str, Context context) {
        String strA;
        StringBuffer stringBuffer = new StringBuffer();
        if ("1.0".equals(str)) {
            Logger.v(TAG, "1.0 interface has no query param appname");
        } else {
            if (!TextUtils.isEmpty(str)) {
                stringBuffer.append("app_name");
                stringBuffer.append("=");
            } else if (!TextUtils.isEmpty(getAppName())) {
                stringBuffer.append("app_name");
                stringBuffer.append("=");
                str = getAppName();
            }
            stringBuffer.append(str);
        }
        String versionName = getVersionName();
        if (!TextUtils.isEmpty(versionName)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("app_version");
            stringBuffer.append("=");
            stringBuffer.append(versionName);
        }
        String uid = getUid();
        if (!TextUtils.isEmpty(uid)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("uid");
            stringBuffer.append("=");
            if (z2) {
                strA = com.huawei.hms.framework.network.grs.h.b.b(uid);
            } else if (z3) {
                strA = com.huawei.hms.framework.network.grs.h.b.a(uid);
            } else {
                stringBuffer.append(uid);
            }
            stringBuffer.append(strA);
        }
        String regCountry = getRegCountry();
        if (!TextUtils.isEmpty(regCountry) && !"UNKNOWN".equals(regCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("reg_country");
            stringBuffer.append("=");
            stringBuffer.append(regCountry);
        }
        String serCountry = getSerCountry();
        if (!TextUtils.isEmpty(serCountry) && !"UNKNOWN".equals(serCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("ser_country");
            stringBuffer.append("=");
            stringBuffer.append(serCountry);
        }
        String issueCountry = getIssueCountry();
        if (!TextUtils.isEmpty(issueCountry) && !"UNKNOWN".equals(issueCountry)) {
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                stringBuffer.append("&");
            }
            stringBuffer.append("issue_country");
            stringBuffer.append("=");
            stringBuffer.append(issueCountry);
        }
        return getStringBuffer(stringBuffer, z3, context).toString();
    }

    public String getIssueCountry() {
        return this.issueCountry;
    }

    public String getRegCountry() {
        return this.regCountry;
    }

    public String getRomVersion() {
        return this.romVersion;
    }

    public String getSerCountry() {
        return this.serCountry;
    }

    public String getUid() {
        return this.uid;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setAndroidVersion(String str) {
        this.androidVersion = str;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public void setCountrySource(String str) {
        this.countrySource = str;
    }

    public void setDeviceModel(String str) {
        this.deviceModel = str;
    }

    @Deprecated
    public void setIssueCountry(String str) {
        this.issueCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    public void setRegCountry(String str) {
        this.regCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    public void setRomVersion(String str) {
        this.romVersion = str;
    }

    public void setSerCountry(String str) {
        this.serCountry = str != null ? str.toUpperCase(Locale.ENGLISH) : "";
    }

    @Deprecated
    public void setUid(String str) {
        this.uid = str;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public int uniqueCode() {
        return (this.appName + DictionaryFactory.SHARP + this.serCountry + DictionaryFactory.SHARP + this.regCountry + DictionaryFactory.SHARP + this.issueCountry).hashCode();
    }
}
