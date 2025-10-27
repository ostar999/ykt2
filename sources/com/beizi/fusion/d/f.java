package com.beizi.fusion.d;

import android.content.Context;
import com.baidu.mobads.sdk.api.BDAdConfig;
import com.baidu.mobads.sdk.api.BDDialogParams;
import com.baidu.mobads.sdk.api.MobadsPermissionSettings;
import com.beizi.fusion.BeiZis;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f4959a;

    public static void a(Context context, String str) {
        b(context, str);
    }

    private static void b(Context context, String str) {
        if (f4959a) {
            return;
        }
        try {
            if (BeiZis.getCustomController() != null) {
                MobadsPermissionSettings.setPermissionReadDeviceID(BeiZis.getCustomController().isCanUsePhoneState());
                MobadsPermissionSettings.setPermissionLocation(BeiZis.getCustomController().isCanUseLocation());
            }
            new BDAdConfig.Builder().setAppsid(str).setDialogParams(new BDDialogParams.Builder().setDlDialogType(0).setDlDialogAnimStyle(0).build()).build(context).init();
            f4959a = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
