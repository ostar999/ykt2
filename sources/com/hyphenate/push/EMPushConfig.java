package com.hyphenate.push;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.hyphenate.util.EMLog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class EMPushConfig {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8827a = "EMPushConfig";

    /* renamed from: b, reason: collision with root package name */
    private String f8828b;

    /* renamed from: c, reason: collision with root package name */
    private String f8829c;

    /* renamed from: d, reason: collision with root package name */
    private String f8830d;

    /* renamed from: e, reason: collision with root package name */
    private String f8831e;

    /* renamed from: f, reason: collision with root package name */
    private String f8832f;

    /* renamed from: g, reason: collision with root package name */
    private String f8833g;

    /* renamed from: h, reason: collision with root package name */
    private String f8834h;

    /* renamed from: i, reason: collision with root package name */
    private String f8835i;

    /* renamed from: j, reason: collision with root package name */
    private String f8836j;

    /* renamed from: k, reason: collision with root package name */
    private String f8837k;

    /* renamed from: l, reason: collision with root package name */
    private ArrayList<EMPushType> f8838l;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        private Context f8839a;

        /* renamed from: b, reason: collision with root package name */
        private String f8840b;

        /* renamed from: c, reason: collision with root package name */
        private String f8841c;

        /* renamed from: d, reason: collision with root package name */
        private String f8842d;

        /* renamed from: e, reason: collision with root package name */
        private String f8843e;

        /* renamed from: f, reason: collision with root package name */
        private String f8844f;

        /* renamed from: g, reason: collision with root package name */
        private String f8845g;

        /* renamed from: h, reason: collision with root package name */
        private String f8846h;

        /* renamed from: i, reason: collision with root package name */
        private String f8847i;

        /* renamed from: j, reason: collision with root package name */
        private String f8848j;

        /* renamed from: k, reason: collision with root package name */
        private String f8849k;

        /* renamed from: l, reason: collision with root package name */
        private ArrayList<EMPushType> f8850l;

        public Builder(Context context) {
            this.f8850l = new ArrayList<>();
            this.f8839a = context.getApplicationContext();
        }

        public Builder(Context context, EMPushConfig eMPushConfig) throws PackageManager.NameNotFoundException {
            this(context);
            if (eMPushConfig == null) {
                return;
            }
            if (eMPushConfig.f8838l.contains(EMPushType.MIPUSH)) {
                enableMiPush(eMPushConfig.f8830d, eMPushConfig.f8831e);
            }
            if (eMPushConfig.f8838l.contains(EMPushType.HMSPUSH)) {
                enableHWPush();
            }
            if (eMPushConfig.f8838l.contains(EMPushType.VIVOPUSH)) {
                enableVivoPush();
            }
            if (eMPushConfig.f8838l.contains(EMPushType.OPPOPUSH)) {
                enableOppoPush(eMPushConfig.f8834h, eMPushConfig.f8835i);
            }
            if (eMPushConfig.f8838l.contains(EMPushType.MEIZUPUSH)) {
                enableMeiZuPush(eMPushConfig.f8832f, eMPushConfig.f8833g);
            }
            if (eMPushConfig.f8838l.contains(EMPushType.FCM)) {
                enableFCM(eMPushConfig.f8828b);
            }
        }

        public EMPushConfig build() {
            EMPushConfig eMPushConfig = new EMPushConfig();
            eMPushConfig.f8828b = this.f8840b;
            eMPushConfig.f8829c = this.f8841c;
            eMPushConfig.f8830d = this.f8842d;
            eMPushConfig.f8831e = this.f8843e;
            eMPushConfig.f8832f = this.f8844f;
            eMPushConfig.f8833g = this.f8845g;
            eMPushConfig.f8834h = this.f8846h;
            eMPushConfig.f8835i = this.f8847i;
            eMPushConfig.f8836j = this.f8848j;
            eMPushConfig.f8837k = this.f8849k;
            eMPushConfig.f8838l = this.f8850l;
            return eMPushConfig;
        }

        public Builder enableFCM(String str) {
            if (TextUtils.isEmpty(str)) {
                EMLog.e(EMPushConfig.f8827a, "senderId can't be empty when enable FCM push !");
                return this;
            }
            this.f8840b = str;
            this.f8850l.add(EMPushType.FCM);
            return this;
        }

        public Builder enableHWPush() {
            String str;
            try {
                ApplicationInfo applicationInfo = this.f8839a.getPackageManager().getApplicationInfo(this.f8839a.getPackageName(), 128);
                String string = applicationInfo.metaData.getString(Constants.HUAWEI_HMS_CLIENT_APPID);
                this.f8841c = string;
                this.f8841c = (string == null || !string.contains("=")) ? String.valueOf(applicationInfo.metaData.getInt(Constants.HUAWEI_HMS_CLIENT_APPID)) : this.f8841c.split("=")[1];
                this.f8850l.add(EMPushType.HMSPUSH);
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException unused) {
                str = "Huawei push meta-data: com.huawei.hms.client.appid value must be like this 'appid=xxxxxx'.";
                EMLog.e(EMPushConfig.f8827a, str);
            } catch (NullPointerException unused2) {
                str = "Huawei push must config meta-data: com.huawei.hms.client.appid in AndroidManifest.xml.";
                EMLog.e(EMPushConfig.f8827a, str);
            }
            return this;
        }

        public Builder enableMeiZuPush(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                EMLog.e(EMPushConfig.f8827a, "appId or appKey can't be empty when enable MEIZU push !");
                return this;
            }
            this.f8844f = str;
            this.f8845g = str2;
            this.f8850l.add(EMPushType.MEIZUPUSH);
            return this;
        }

        public Builder enableMiPush(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                EMLog.e(EMPushConfig.f8827a, "appId or appKey can't be empty when enable MI push !");
                return this;
            }
            this.f8842d = str;
            this.f8843e = str2;
            this.f8850l.add(EMPushType.MIPUSH);
            return this;
        }

        public Builder enableOppoPush(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                EMLog.e(EMPushConfig.f8827a, "appKey or appSecret can't be empty when enable OPPO push !");
                return this;
            }
            this.f8846h = str;
            this.f8847i = str2;
            this.f8850l.add(EMPushType.OPPOPUSH);
            return this;
        }

        public Builder enableVivoPush() throws PackageManager.NameNotFoundException {
            try {
                ApplicationInfo applicationInfo = this.f8839a.getPackageManager().getApplicationInfo(this.f8839a.getPackageName(), 128);
                this.f8848j = applicationInfo.metaData.getInt("com.vivo.push.app_id") + "";
                this.f8849k = applicationInfo.metaData.getString("com.vivo.push.api_key");
                this.f8850l.add(EMPushType.VIVOPUSH);
            } catch (PackageManager.NameNotFoundException e2) {
                EMLog.e(EMPushConfig.f8827a, "NameNotFoundException: " + e2.getMessage());
            }
            return this;
        }
    }

    private EMPushConfig() {
    }

    public ArrayList<EMPushType> getEnabledPushTypes() {
        return this.f8838l;
    }

    public String getFcmSenderId() {
        return this.f8828b;
    }

    public String getHwAppId() {
        return this.f8829c;
    }

    public String getMiAppId() {
        return this.f8830d;
    }

    public String getMiAppKey() {
        return this.f8831e;
    }

    public String getMzAppId() {
        return this.f8832f;
    }

    public String getMzAppKey() {
        return this.f8833g;
    }

    public String getOppoAppKey() {
        return this.f8834h;
    }

    public String getOppoAppSecret() {
        return this.f8835i;
    }

    public String getVivoAppId() {
        return this.f8836j;
    }

    public String getVivoAppKey() {
        return this.f8837k;
    }

    public String toString() {
        return "EMPushConfig{fcmSenderId='" + this.f8828b + CharPool.SINGLE_QUOTE + ", hwAppId='" + this.f8829c + CharPool.SINGLE_QUOTE + ", miAppId='" + this.f8830d + CharPool.SINGLE_QUOTE + ", miAppKey='" + this.f8831e + CharPool.SINGLE_QUOTE + ", mzAppId='" + this.f8832f + CharPool.SINGLE_QUOTE + ", mzAppKey='" + this.f8833g + CharPool.SINGLE_QUOTE + ", oppoAppKey='" + this.f8834h + CharPool.SINGLE_QUOTE + ", oppoAppSecret='" + this.f8835i + CharPool.SINGLE_QUOTE + ", vivoAppId='" + this.f8836j + CharPool.SINGLE_QUOTE + ", vivoAppKey='" + this.f8837k + CharPool.SINGLE_QUOTE + ", enabledPushTypes=" + this.f8838l + '}';
    }
}
