package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.as;
import com.bytedance.msdk.api.TTAdConfig;
import com.bytedance.msdk.api.TTMediationAdSdk;
import com.bytedance.msdk.api.TTPrivacyConfig;

/* loaded from: classes2.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    public static TTPrivacyConfig f4978a = new TTPrivacyConfig() { // from class: com.beizi.fusion.d.l.1
        public boolean isCanUseLocation() {
            return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.isCanUseLocation();
        }

        public boolean isCanUsePhoneState() {
            return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.isCanUsePhoneState();
        }
    };

    /* renamed from: b, reason: collision with root package name */
    private static boolean f4979b;

    public static void a(Context context, String str, int i2) {
        b(context, str, i2);
    }

    private static void b(Context context, String str, int i2) {
        if (f4979b) {
            return;
        }
        TTMediationAdSdk.initialize(context, c(context, str, i2));
        f4979b = true;
    }

    private static TTAdConfig c(Context context, String str, int i2) {
        ac.a("BeiZis", "TTAdConfig.Builder appId：" + str + "  appName：" + as.f(context) + " directDownloadType:" + i2);
        if (com.beizi.fusion.g.n.a(i2)) {
            ac.a("BeiZis", k.a.f27523u);
            return new TTAdConfig.Builder().appId(str).appName(as.f(context)).openAdnTest(false).isPanglePaid(false).setPublisherDid(a(context)).usePangleTextureView(true).setPangleTitleBarTheme(1).allowPangleShowNotify(true).allowPangleShowPageWhenScreenLock(true).setPangleDirectDownloadNetworkType(new int[]{4, 3}).needPangleClearTaskReset(new String[0]).setPrivacyConfig(f4978a).build();
        }
        ac.a("BeiZis", k.a.f27524v);
        return new TTAdConfig.Builder().appId(str).appName(as.f(context)).openAdnTest(false).isPanglePaid(false).setPublisherDid(a(context)).usePangleTextureView(true).setPangleTitleBarTheme(1).allowPangleShowNotify(true).allowPangleShowPageWhenScreenLock(true).setPangleDirectDownloadNetworkType(new int[0]).needPangleClearTaskReset(new String[0]).setPrivacyConfig(f4978a).build();
    }

    public static String a(Context context) {
        try {
            return aq.a(context, "SDK_UID_KEY_NEW");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
