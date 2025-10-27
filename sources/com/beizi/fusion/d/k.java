package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.as;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.managers.setting.GlobalSetting;
import com.qq.e.comm.pi.IBidding;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4977a;

    public static void a(Context context, String str) {
        try {
            if (BeiZis.getCustomController() != null) {
                boolean zIsCanUsePhoneState = BeiZis.getCustomController().isCanUsePhoneState();
                GlobalSetting.setAgreeReadAndroidId(zIsCanUsePhoneState);
                GlobalSetting.setAgreeReadDeviceId(zIsCanUsePhoneState);
            }
            GDTAdSdk.init(context, str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void b(Context context, String str) {
        d(context, str);
    }

    public static String c(Context context, String str) {
        try {
            if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                return null;
            }
            b(context, str);
            return GDTAdSdk.getGDTAdManger().getBuyerId((Map) null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static void d(Context context, String str) {
        if (f4977a) {
            return;
        }
        try {
            a(context, str);
            f4977a = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void b(IBidding iBidding, int i2) {
        HashMap map = new HashMap();
        map.put("lossReason", Integer.valueOf(i2));
        map.put("adnId", 3);
        iBidding.sendWinNotification(map);
    }

    public static String a(Context context, String str, String str2) {
        try {
            if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                return null;
            }
            b(context, str);
            return GDTAdSdk.getGDTAdManger().getSDKInfo(str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void a(IBidding iBidding, int i2) {
        HashMap map = new HashMap();
        map.put("expectCostPrice", Integer.valueOf(i2));
        map.put("highestLossPrice", Integer.valueOf(i2 - 1));
        iBidding.sendWinNotification(map);
    }
}
