package com.beizi.ad.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.ad.BeiZi;
import com.beizi.ad.internal.utilities.SPUtils;

/* loaded from: classes2.dex */
public class j {
    public static String a(Context context) {
        String stringFromFusionSdk;
        if (context == null) {
            return "";
        }
        String devOaid = BeiZi.getCustomController() != null ? BeiZi.getCustomController().getDevOaid() : null;
        return !TextUtils.isEmpty(devOaid) ? devOaid : ((BeiZi.getCustomController() == null || (!BeiZi.isLimitPersonalAds() && BeiZi.getCustomController().isCanUseOaid())) && (stringFromFusionSdk = SPUtils.getStringFromFusionSdk(context, "__OAID__")) != null) ? stringFromFusionSdk : "";
    }

    public static String b(Context context) {
        String stringFromFusionSdk;
        return context == null ? "" : ((BeiZi.getCustomController() == null || (!BeiZi.isLimitPersonalAds() && BeiZi.getCustomController().isCanUseGaid())) && (stringFromFusionSdk = SPUtils.getStringFromFusionSdk(context, "__GAID__")) != null) ? stringFromFusionSdk : "";
    }
}
