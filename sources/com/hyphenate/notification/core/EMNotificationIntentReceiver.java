package com.hyphenate.notification.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.notification.EMNotificationMessage;
import com.hyphenate.util.EMLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.d;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMNotificationIntentReceiver extends BroadcastReceiver {
    private static final String TAG = "em_notification";

    /* JADX WARN: Removed duplicated region for block: B:23:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onNotificationClick(android.content.Context r5, com.hyphenate.notification.EMNotificationMessage r6) {
        /*
            r4 = this;
            int r0 = r6.getOpenType()     // Catch: java.lang.Exception -> Ld0
            r1 = 1
            java.lang.String r2 = "android.intent.action.VIEW"
            if (r0 != r1) goto L39
            java.lang.String r0 = r6.getOpenUrl()     // Catch: java.lang.Exception -> Ld0
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Exception -> Ld0
            if (r0 != 0) goto L8a
            java.lang.String r0 = r6.getOpenUrl()     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = "http:"
            boolean r0 = r0.startsWith(r1)     // Catch: java.lang.Exception -> Ld0
            if (r0 != 0) goto L2b
            java.lang.String r0 = r6.getOpenUrl()     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = "https:"
            boolean r0 = r0.startsWith(r1)     // Catch: java.lang.Exception -> Ld0
            if (r0 == 0) goto L8a
        L2b:
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = r6.getOpenUrl()     // Catch: java.lang.Exception -> Ld0
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch: java.lang.Exception -> Ld0
            r0.<init>(r2, r1)     // Catch: java.lang.Exception -> Ld0
            goto L98
        L39:
            int r0 = r6.getOpenType()     // Catch: java.lang.Exception -> Ld0
            r1 = 2
            if (r0 != r1) goto L8c
            java.lang.String r0 = r6.getOpenAction()     // Catch: java.lang.Exception -> Ld0
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Exception -> Ld0
            if (r0 != 0) goto L6e
            java.lang.String r0 = r6.getOpenAction()     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = "://"
            boolean r0 = r0.contains(r1)     // Catch: java.lang.Exception -> Ld0
            if (r0 == 0) goto L64
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = r6.getOpenAction()     // Catch: java.lang.Exception -> Ld0
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch: java.lang.Exception -> Ld0
            r0.<init>(r2, r1)     // Catch: java.lang.Exception -> Ld0
            goto L98
        L64:
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = r6.getOpenAction()     // Catch: java.lang.Exception -> Ld0
            r0.<init>(r1)     // Catch: java.lang.Exception -> Ld0
            goto L98
        L6e:
            java.lang.String r0 = r6.getOpenActivity()     // Catch: java.lang.Exception -> Ld0
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Exception -> Ld0
            if (r0 != 0) goto L8a
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> Ld0
            r0.<init>()     // Catch: java.lang.Exception -> Ld0
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Exception -> Ld0
            java.lang.String r2 = r6.getOpenActivity()     // Catch: java.lang.Exception -> Ld0
            r1.<init>(r5, r2)     // Catch: java.lang.Exception -> Ld0
            r0.setComponent(r1)     // Catch: java.lang.Exception -> Ld0
            goto L98
        L8a:
            r0 = 0
            goto L98
        L8c:
            android.content.pm.PackageManager r0 = r5.getPackageManager()     // Catch: java.lang.Exception -> Ld0
            java.lang.String r1 = r5.getPackageName()     // Catch: java.lang.Exception -> Ld0
            android.content.Intent r0 = r0.getLaunchIntentForPackage(r1)     // Catch: java.lang.Exception -> Ld0
        L98:
            java.lang.String r1 = r6.getExtras()     // Catch: java.lang.Exception -> Ld0
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Exception -> Ld0
            if (r1 != 0) goto Lc5
            if (r0 == 0) goto Lc5
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: java.lang.Exception -> Ld0
            java.lang.String r6 = r6.getExtras()     // Catch: java.lang.Exception -> Ld0
            r1.<init>(r6)     // Catch: java.lang.Exception -> Ld0
            java.util.Iterator r6 = r1.keys()     // Catch: java.lang.Exception -> Ld0
        Lb1:
            boolean r2 = r6.hasNext()     // Catch: java.lang.Exception -> Ld0
            if (r2 == 0) goto Lc5
            java.lang.Object r2 = r6.next()     // Catch: java.lang.Exception -> Ld0
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Exception -> Ld0
            java.lang.String r3 = r1.getString(r2)     // Catch: java.lang.Exception -> Ld0
            r0.putExtra(r2, r3)     // Catch: java.lang.Exception -> Ld0
            goto Lb1
        Lc5:
            if (r0 == 0) goto Leb
            r6 = 337641472(0x14200000, float:8.077936E-27)
            r0.setFlags(r6)     // Catch: java.lang.Exception -> Ld0
            r5.startActivity(r0)     // Catch: java.lang.Exception -> Ld0
            goto Leb
        Ld0:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "onNotificationClick:"
            r6.append(r0)
            java.lang.String r5 = r5.getMessage()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            java.lang.String r6 = "em_notification"
            com.hyphenate.util.EMLog.d(r6, r5)
        Leb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.notification.core.EMNotificationIntentReceiver.onNotificationClick(android.content.Context, com.hyphenate.notification.EMNotificationMessage):void");
    }

    public void onNotifyMessageArrived(Context context, EMNotificationMessage eMNotificationMessage) {
        EMLog.d(TAG, "onNotifyMessageArrived");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) throws JSONException {
        EMLog.d(TAG, "onReceive");
        if (intent == null) {
            return;
        }
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int i2 = extras.getInt("event_type");
                String string = extras.getString(PushConstants.TASK_ID);
                EMNotificationMessage eMNotificationMessage = (EMNotificationMessage) extras.getParcelable("message");
                if (i2 == 0) {
                    onNotifyMessageArrived(context, eMNotificationMessage);
                }
                if (i2 == 1) {
                    JSONObject jSONObject = new JSONObject();
                    if (!string.isEmpty()) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(PushConstants.TASK_ID, string);
                        jSONObject.put("report", jSONObject2);
                    }
                    jSONObject.put(d.M, "EASEMOB");
                    EMClient.getInstance().pushManager().reportPushAction(jSONObject, EMPushManager.EMPushAction.CLICK, new EMCallBack() { // from class: com.hyphenate.notification.core.EMNotificationIntentReceiver.1
                        @Override // com.hyphenate.EMCallBack
                        public void onError(int i3, String str) {
                            EMLog.d(EMNotificationIntentReceiver.TAG, "report failed: " + i3 + " : " + str);
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onProgress(int i3, String str) {
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onSuccess() {
                            EMLog.d(EMNotificationIntentReceiver.TAG, "report success");
                        }
                    });
                    onNotificationClick(context, eMNotificationMessage);
                }
            }
        } catch (Exception e2) {
            EMLog.d(TAG, e2.getMessage());
        }
    }
}
