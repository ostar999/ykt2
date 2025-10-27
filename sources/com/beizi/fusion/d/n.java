package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.aq;
import com.jd.ad.sdk.bl.initsdk.JADPrivateController;
import com.jd.ad.sdk.bl.initsdk.JADYunSdk;
import com.jd.ad.sdk.bl.initsdk.JADYunSdkConfig;

/* loaded from: classes2.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4982a;

    public static void a(Context context, String str) {
        b(context, str);
    }

    private static void b(Context context, String str) {
        if (f4982a) {
            return;
        }
        try {
            JADYunSdk.init(context, new JADYunSdkConfig.Builder().setAppId(str).setEnableLog(true).setPrivateController(a(context)).build());
            f4982a = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static JADPrivateController a(final Context context) {
        return new JADPrivateController() { // from class: com.beizi.fusion.d.n.1
            public String getIP() {
                return "";
            }

            public String getImei() {
                return "";
            }

            public String getOaid() {
                return (String) aq.b(context, "__OAID__", "");
            }

            public boolean isCanUseIP() {
                return super.isCanUseIP();
            }

            public boolean isCanUseLocation() {
                return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.isCanUseLocation();
            }

            public boolean isCanUsePhoneState() {
                return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.isCanUsePhoneState();
            }
        };
    }
}
