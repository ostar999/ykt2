package com.psychiatrygarden.service;

import com.huawei.hms.push.HmsMessageService;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;

/* loaded from: classes6.dex */
public class HMSPushService extends HmsMessageService {
    @Override // com.huawei.hms.push.HmsMessageService
    public void onNewToken(String token) {
        if (token == null || token.equals("")) {
            EMLog.e("HWHMSPush", "service register huawei hms push token fail!");
            return;
        }
        EMLog.d("HWHMSPush", "service register huawei hms push token success token:" + token);
        EMClient.getInstance().sendHMSPushTokenToServer(token);
    }

    @Override // com.huawei.hms.push.HmsMessageService
    public void onTokenError(Exception e2) {
        super.onTokenError(e2);
        e2.printStackTrace();
    }
}
