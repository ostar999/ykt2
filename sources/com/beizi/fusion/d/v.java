package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTCustomController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class v {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4995a = false;

    public static TTAdManager a() {
        if (!TTAdSdk.isInitSuccess()) {
            ac.c("BeiZis", "TTAdSdk is not init or init fail, please check.");
        }
        return TTAdSdk.getAdManager();
    }

    private static void b(final com.beizi.fusion.work.a aVar, Context context, String str, int i2) {
        try {
            TTAdSdk.init(context, a(context, str, i2), new TTAdSdk.InitCallback() { // from class: com.beizi.fusion.d.v.1
                public void fail(int i3, String str2) {
                    com.beizi.fusion.work.a aVar2 = aVar;
                    if (aVar2 != null) {
                        aVar2.aE();
                    }
                    ac.c("BeiZis", "TTAdManagerHolder.doInit().init() i:" + i3 + " s:" + str2);
                }

                public void success() {
                    ac.c("BeiZis", "TTAdManagerHolder.doInit().init() success ");
                    com.beizi.fusion.work.a aVar2 = aVar;
                    if (aVar2 != null) {
                        aVar2.aF();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static String b(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", "personal_ads_type");
            jSONObject.put("value", str);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            return jSONArray.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static void a(com.beizi.fusion.work.a aVar, Context context, String str, int i2) {
        b(aVar, context, str, i2);
    }

    private static TTAdConfig a(Context context, String str, int i2) {
        ac.b("BeiZis", "isDownloadDirect = " + com.beizi.fusion.g.n.a(i2));
        String str2 = f4995a ? "[{\"name\":\"personal_ads_type\",\"value\":\"0\"}]" : "[{\"name\":\"personal_ads_type\",\"value\":\"1\"}]";
        if (com.beizi.fusion.g.n.a(i2)) {
            return new TTAdConfig.Builder().appId(str).useTextureView(true).appName(as.f(context)).asyncInit(true).titleBarTheme(1).allowShowNotify(true).directDownloadNetworkType(new int[]{4, 3, 5}).needClearTaskReset(new String[0]).data(str2).customController(new TTCustomController() { // from class: com.beizi.fusion.d.v.2
                public boolean isCanUseLocation() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.isCanUseLocation();
                }

                public boolean isCanUsePhoneState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.isCanUsePhoneState();
                }

                public boolean isCanUseWifiState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : super.isCanUseWifiState();
                }
            }).build();
        }
        return new TTAdConfig.Builder().appId(str).useTextureView(true).appName(as.f(context)).asyncInit(true).titleBarTheme(1).allowShowNotify(true).allowShowPageWhenScreenLock(true).directDownloadNetworkType(new int[0]).needClearTaskReset(new String[0]).data(str2).customController(new TTCustomController() { // from class: com.beizi.fusion.d.v.3
            public boolean isCanUseLocation() {
                return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.isCanUseLocation();
            }

            public boolean isCanUsePhoneState() {
                return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.isCanUsePhoneState();
            }

            public boolean isCanUseWifiState() {
                return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : super.isCanUseWifiState();
            }
        }).build();
    }

    public static void a(boolean z2) {
        f4995a = z2;
        if (z2) {
            a("0");
        } else {
            a("1");
        }
    }

    private static void a(String str) {
        TTAdSdk.updateAdConfig(new TTAdConfig.Builder().data(b(str)).build());
    }
}
