package com.psychiatrygarden.receiver;

import android.content.Context;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.hyphenate.push.platform.mi.EMMiMsgReceiver;
import com.hyphenate.util.EMLog;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.xiaomi.mipush.sdk.MiPushMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class MiMsgReceiver extends EMMiMsgReceiver {
    private static String TAG = "MiMsgReceiver";

    @Override // com.hyphenate.push.platform.mi.EMMiMsgReceiver, com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageClicked(Context context, MiPushMessage message) throws JSONException {
        EMLog.i(TAG, "onNotificationMessageClicked is called. " + message.toString());
        String content = message.getContent();
        EMLog.i(TAG, "onReceivePassThroughMessage get extras: " + content);
        try {
            JSONObject jSONObject = new JSONObject(content);
            EMLog.i(TAG, "onReceivePassThroughMessage get extras: " + jSONObject.toString());
            JSONObject jSONObject2 = jSONObject.getJSONObject(AliyunLogKey.KEY_EVENT);
            if (jSONObject2 != null) {
                PushUtils.isRtcCall = jSONObject2.getBoolean("isRtcCall");
                PushUtils.type = jSONObject2.getInt("callType");
                EMLog.i(TAG, "onReceivePassThroughMessage get type: " + PushUtils.type);
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(jSONObject2));
            }
        } catch (Exception e2) {
            e2.getStackTrace();
        }
        super.onNotificationMessageClicked(context, message);
    }
}
