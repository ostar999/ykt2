package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes6.dex */
public class DoctorModuleManager {
    private static final String DOCTOR_HOME_ACTIVITY = "com.yddmi.doctor.pages.home.HomeActivity";

    public static boolean isModuleIntegrated(Context context) throws ClassNotFoundException {
        try {
            Class.forName(DOCTOR_HOME_ACTIVITY);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static boolean startDoctorHome(Context context, String phoneNumber) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context, DOCTOR_HOME_ACTIVITY);
            intent.putExtra("phoneNumber", phoneNumber);
            context.startActivity(intent);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            NewToast.showShort(context, "跳转失败，请确认doctor模块已正确集成", 0).show();
            return false;
        }
    }
}
