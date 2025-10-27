package com.psychiatrygarden.service;

import android.app.Activity;
import android.text.TextUtils;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;

/* loaded from: classes6.dex */
public class HMSPushHelper {
    private static HMSPushHelper instance;

    private HMSPushHelper() {
    }

    public static HMSPushHelper getInstance() {
        if (instance == null) {
            instance = new HMSPushHelper();
        }
        return instance;
    }

    public void getHMSToken(final Activity activity) {
        if (EMClient.getInstance().isFCMAvailable()) {
            return;
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            if (TextUtils.isEmpty((String) cls.getDeclaredMethod("get", String.class).invoke(cls, "ro.build.version.emui"))) {
                EMLog.d("HWHMSPush", "huawei hms push is unavailable!");
            } else {
                EMLog.d("HWHMSPush", "huawei hms push is available!");
                new Thread() { // from class: com.psychiatrygarden.service.HMSPushHelper.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            String token = HmsInstanceId.getInstance(activity).getToken(AGConnectServicesConfig.fromContext(activity).getString("client/app_id"), HmsMessaging.DEFAULT_TOKEN_SCOPE);
                            EMLog.d("HWHMSPush", "get huawei hms push token:" + token);
                            if (token == null || token.equals("")) {
                                EMLog.e("HWHMSPush", "register huawei hms push token fail!");
                            } else {
                                EMLog.d("HWHMSPush", "register huawei hms push token success token:" + token);
                                EMClient.getInstance().sendHMSPushTokenToServer(token);
                            }
                        } catch (ApiException e2) {
                            EMLog.e("HWHMSPush", "get huawei hms push token failed, " + e2);
                        }
                    }
                }.start();
            }
        } catch (Exception unused) {
            EMLog.d("HWHMSPush", "no huawei hms push sdk or mobile is not a huawei phone");
        }
    }
}
