package com.huawei.hms.opendevice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.android.SystemUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.Util;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.pro.am;
import java.util.TimeZone;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class n {

    public enum a {
        MOBILE("1"),
        PC("2"),
        TABLET("3"),
        TV("4"),
        SOUNDBOX("5"),
        GLASS(Constants.VIA_SHARE_TYPE_INFO),
        WATCH("7"),
        VEHICLE(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO),
        OFFICE_DEVICE(Constants.VIA_SHARE_TYPE_MINI_PROGRAM),
        IOT_DEVICES(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ),
        HEALTHY(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE),
        ENTERTAINMENT(Constants.VIA_REPORT_TYPE_SET_AVATAR),
        TRANSPORT_DEVICES(Constants.VIA_REPORT_TYPE_JOININ_GROUP);


        /* renamed from: o, reason: collision with root package name */
        public String f7934o;

        a(String str) {
            this.f7934o = str;
        }

        public String a() {
            return this.f7934o;
        }
    }

    public enum b {
        IOS("ios"),
        ANDROID("android"),
        HARMONY("harmony"),
        WINDOWS("windows"),
        EMBED("embed"),
        OTHERS("others");


        /* renamed from: h, reason: collision with root package name */
        public String f7942h;

        b(String str) {
            this.f7942h = str;
        }

        public String a() {
            return this.f7942h;
        }
    }

    public static String c(Context context, String str, String str2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(am.M, TimeZone.getDefault().getID());
            jSONObject2.put(am.O, SystemUtils.getLocalCountry());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("agent_version", new PackageManagerHelper(context).getPackageVersionName("com.huawei.android.pushagent"));
            jSONObject3.put("hms_version", String.valueOf(Util.getHmsVersion(context)));
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("dev_type", a.MOBILE.a());
            jSONObject4.put("dev_sub_type", AliyunLogCommon.TERMINAL_TYPE);
            jSONObject4.put("os_type", b.ANDROID.a());
            jSONObject4.put("os_version", String.valueOf(HwBuildEx.VERSION.EMUI_SDK_INT));
            jSONObject.put("id", UUID.randomUUID().toString());
            jSONObject.put("global", jSONObject2);
            jSONObject.put("push_agent", jSONObject3);
            jSONObject.put("hardware", jSONObject4);
            jSONObject.put("aaid", str);
            jSONObject.put("token", str2);
            jSONObject.put("app_id", AGConnectServicesConfig.fromContext(context).getString("client/app_id"));
            jSONObject.put(TtmlNode.TAG_REGION, AGConnectServicesConfig.fromContext(context).getString(TtmlNode.TAG_REGION));
            return jSONObject.toString();
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Catch JSONException.");
            return null;
        }
    }

    public static boolean d(Context context, String str, String str2) {
        i iVarA = i.a(context);
        if (!iVarA.containsKey("reportAaidAndToken")) {
            HMSLog.d("ReportAaidToken", "It hasn't been reported, this time needs report.");
            return true;
        }
        if (TextUtils.isEmpty(iVarA.getString("reportAaidAndToken"))) {
            HMSLog.e("ReportAaidToken", "It has been reported, but sp file is empty, this time needs report.");
            return true;
        }
        return !r4.equals(r.a(str2 + str, "SHA-256"));
    }

    public static boolean b(Context context) throws PackageManager.NameNotFoundException {
        int packageVersionCode = new PackageManagerHelper(context).getPackageVersionCode("com.huawei.android.pushagent");
        HMSLog.d("ReportAaidToken", "NC version code: " + packageVersionCode);
        return (90101400 <= packageVersionCode && packageVersionCode < 100000000) || packageVersionCode >= 100001301;
    }

    public static void a(Context context, String str) {
        new m(context, str).start();
    }

    public static void b(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            HMSLog.e("ReportAaidToken", "Https response is empty.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("ret", 256);
            if (iOptInt == 0) {
                boolean zSaveString = i.a(context).saveString("reportAaidAndToken", r.a(str3 + str2, "SHA-256"));
                StringBuilder sb = new StringBuilder();
                sb.append("Report success ");
                sb.append(zSaveString ? "and save success." : "but save failure.");
                HMSLog.d("ReportAaidToken", sb.toString());
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Https response body's ret code: ");
            sb2.append(iOptInt);
            sb2.append(", error message: ");
            sb2.append(jSONObject.optString("msg"));
            HMSLog.e("ReportAaidToken", sb2.toString());
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Has JSONException.");
        } catch (Exception unused2) {
            HMSLog.e("ReportAaidToken", "Exception occur.");
        }
    }
}
