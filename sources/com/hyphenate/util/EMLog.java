package com.hyphenate.util;

import android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMAChatConfig;

/* loaded from: classes4.dex */
public class EMLog {
    public static boolean debugMode = false;

    public static void d(String str, String str2) {
        if (str2 != null && debugMode) {
            if (EMClient.getInstance().isSdkInited()) {
                EMAChatConfig.logD(str, str2);
            } else {
                Log.d(str, str2);
            }
        }
    }

    public static void e(String str, String str2) {
        if (str2 == null) {
            return;
        }
        if (EMClient.getInstance().isSdkInited()) {
            EMAChatConfig.logE(str, str2);
        } else {
            Log.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        if (str2 != null && debugMode) {
            if (EMClient.getInstance().isSdkInited()) {
                EMAChatConfig.logI(str, str2);
            } else {
                Log.i(str, str2);
            }
        }
    }

    public static void v(String str, String str2) {
        if (str2 != null && debugMode) {
            if (EMClient.getInstance().isSdkInited()) {
                EMAChatConfig.logV(str, str2);
            } else {
                Log.v(str, str2);
            }
        }
    }

    public static void w(String str, String str2) {
        if (str2 != null && debugMode) {
            if (EMClient.getInstance().isSdkInited()) {
                EMAChatConfig.logW(str, str2);
            } else {
                Log.w(str, str2);
            }
        }
    }
}
