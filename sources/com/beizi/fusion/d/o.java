package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.as;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsCustomController;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.SdkConfig;

/* loaded from: classes2.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4984a;

    public static void a(Context context, String str) {
        c(context, str);
    }

    public static String b(Context context, String str) {
        try {
            if (!as.a("com.kwad.sdk.api.KsAdSDK")) {
                return null;
            }
            a(context, str);
            KsScene ksSceneBuild = new KsScene.Builder(Long.parseLong(str)).build();
            KsLoadManager loadManager = KsAdSDK.getLoadManager();
            if (loadManager == null) {
                return null;
            }
            return loadManager.getBidRequestToken(ksSceneBuild);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static void c(Context context, String str) {
        if (f4984a) {
            return;
        }
        try {
            KsAdSDK.init(context, new SdkConfig.Builder().appId(str).canReadNearbyWifiList(BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : true).customController(new KsCustomController() { // from class: com.beizi.fusion.d.o.1
                public boolean canReadLocation() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.canReadLocation();
                }

                public boolean canUseMacAddress() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : super.canUseMacAddress();
                }

                public boolean canUseNetworkState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : super.canUseNetworkState();
                }

                public boolean canUseOaid() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseOaid() : super.canUseOaid();
                }

                public boolean canUsePhoneState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.canUsePhoneState();
                }
            }).showNotification(true).build());
            f4984a = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
