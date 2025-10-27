package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.Log;

/* loaded from: classes6.dex */
public class DoctorModuleTest {
    private static final String TAG = "DoctorModuleTest";

    public static boolean testDoctorModuleIntegration(Context context) throws ClassNotFoundException {
        Log.i(TAG, "开始测试Doctor模块集成状态");
        boolean zIsModuleIntegrated = DoctorModuleManager.isModuleIntegrated(context);
        if (zIsModuleIntegrated) {
            Log.i(TAG, "Doctor模块集成成功");
            NewToast.showShort(context, "Doctor模块集成成功", 0).show();
        } else {
            Log.e(TAG, "Doctor模块集成失败，请检查AAR文件是否正确放置");
            NewToast.showShort(context, "Doctor模块集成失败，请检查AAR文件", 0).show();
        }
        return zIsModuleIntegrated;
    }
}
