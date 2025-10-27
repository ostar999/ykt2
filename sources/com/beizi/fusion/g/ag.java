package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.mobads.sdk.api.MobadsPermissionSettings;
import com.beizi.ad.BeiZi;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.model.Manager;
import com.beizi.fusion.model.RequestInfo;
import com.beizi.fusion.model.ResponseInfo;
import com.kwad.sdk.api.KsAdSDK;
import com.qq.e.comm.managers.setting.GlobalSetting;

/* loaded from: classes2.dex */
public class ag {
    public static void a(boolean z2) {
        try {
            if (z2) {
                try {
                    GlobalSetting.setPersonalizedState(0);
                } catch (Throwable unused) {
                }
                try {
                    com.beizi.fusion.d.v.a(false);
                } catch (Throwable unused2) {
                }
                try {
                    KsAdSDK.setPersonalRecommend(true);
                } catch (Throwable unused3) {
                }
                try {
                    MobadsPermissionSettings.setLimitPersonalAds(false);
                } catch (Throwable unused4) {
                }
                try {
                    BeiZi.setLimitPersonalAds(false);
                } catch (Throwable unused5) {
                }
                BeiZis.setLimitPersonalAds(false);
            } else {
                try {
                    GlobalSetting.setPersonalizedState(1);
                } catch (Throwable unused6) {
                }
                try {
                    com.beizi.fusion.d.v.a(true);
                } catch (Throwable unused7) {
                }
                try {
                    KsAdSDK.setPersonalRecommend(false);
                } catch (Throwable unused8) {
                }
                try {
                    MobadsPermissionSettings.setLimitPersonalAds(true);
                } catch (Throwable unused9) {
                }
                try {
                    BeiZi.setLimitPersonalAds(true);
                } catch (Throwable unused10) {
                }
                BeiZis.setLimitPersonalAds(true);
                if (com.beizi.fusion.d.b.a().e() != null && b() && RequestInfo.getInstance(com.beizi.fusion.d.b.a().e()).getDevInfo() != null && !TextUtils.isEmpty(RequestInfo.getInstance(com.beizi.fusion.d.b.a().e()).getDevInfo().getOaid())) {
                    RequestInfo.getInstance(com.beizi.fusion.d.b.a().e()).getDevInfo().setOaid("");
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static boolean b() {
        int iC = c();
        return iC == 0 || iC == 2;
    }

    private static int c() {
        Context contextE = com.beizi.fusion.d.b.a().e();
        if (contextE != null) {
            if (!ResponseInfo.getInstance(contextE).isInit()) {
                ResponseInfo.getInstance(contextE).init();
            }
            Manager manager = ResponseInfo.getInstance(contextE).getManager();
            if (manager != null) {
                return manager.getPersonalRecommend();
            }
        }
        return 0;
    }

    public static boolean a() {
        int iC = c();
        boolean zIsLimitPersonalAds = BeiZis.isLimitPersonalAds();
        if (iC == 0 || iC == 2) {
            return zIsLimitPersonalAds;
        }
        return false;
    }
}
