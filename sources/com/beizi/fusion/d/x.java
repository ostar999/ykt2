package com.beizi.fusion.d;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.ad.BeiZi;
import com.beizi.ad.BeiZiAdSdkController;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.ag;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdPlusConfig;
import com.beizi.fusion.model.RequestInfo;
import com.beizi.fusion.model.ResponseInfo;

/* loaded from: classes2.dex */
public class x {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4998a;

    public static void a(Context context, String str) {
        try {
            b(context.getApplicationContext(), str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void b(final Context context, String str) {
        if (f4998a) {
            return;
        }
        try {
            a(context);
            BeiZi.init(context, str, new BeiZiAdSdkController() { // from class: com.beizi.fusion.d.x.1
                @Override // com.beizi.ad.BeiZiAdSdkController
                public String getDevOaid() {
                    try {
                        if (RequestInfo.getInstance(context).getDevInfo() != null) {
                            String oaid = RequestInfo.getInstance(context).getDevInfo().getOaid();
                            if (!TextUtils.isEmpty(oaid)) {
                                return oaid;
                            }
                            String customOaid = RequestInfo.getInstance(context).getCustomOaid();
                            if (!TextUtils.isEmpty(customOaid)) {
                                return customOaid;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return super.getDevOaid();
                }

                @Override // com.beizi.ad.BeiZiAdSdkController
                public boolean isCanUseGaid() {
                    return BeiZis.getCustomController() != null ? !ag.a() && BeiZis.getCustomController().isCanUseGaid() : super.isCanUseGaid();
                }

                @Override // com.beizi.ad.BeiZiAdSdkController
                public boolean isCanUseLocation() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseLocation() : super.isCanUseLocation();
                }

                @Override // com.beizi.ad.BeiZiAdSdkController
                public boolean isCanUseOaid() {
                    return BeiZis.getCustomController() != null ? !ag.a() && BeiZis.getCustomController().isCanUseOaid() : super.isCanUseOaid();
                }

                @Override // com.beizi.ad.BeiZiAdSdkController
                public boolean isCanUsePhoneState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUsePhoneState() : super.isCanUsePhoneState();
                }

                @Override // com.beizi.ad.BeiZiAdSdkController
                public boolean isCanUseWifiState() {
                    return BeiZis.getCustomController() != null ? BeiZis.getCustomController().isCanUseWifiState() : super.isCanUseWifiState();
                }
            });
            f4998a = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(Context context) {
        String strA;
        try {
            if (as.a("com.beizi.ad.BeiZi")) {
                AdPlusConfig adPlusConfig = ResponseInfo.getInstance(context).getAdPlusConfig();
                if (adPlusConfig != null && !TextUtils.isEmpty(adPlusConfig.getAdUrl()) && adPlusConfig.getAdUrl().startsWith("http")) {
                    strA = adPlusConfig.getAdUrl();
                } else {
                    strA = com.beizi.fusion.g.f.a(BeiZis.getTransferProtocol() ? "aHR0cHM6Ly9hcGktaHRwLmJlaXppLmJpei9tYi9zZGswL2pzb24=" : "aHR0cDovL2FwaS5odHAuYWQtc2NvcGUuY29tLmNuOjQ1NjAwL21iL3NkazAvanNvbg==");
                    if (TextUtils.isEmpty(strA)) {
                        return;
                    }
                }
                if (TextUtils.isEmpty(strA)) {
                    return;
                }
                BeiZi.setAdRequestUrl(strA);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
