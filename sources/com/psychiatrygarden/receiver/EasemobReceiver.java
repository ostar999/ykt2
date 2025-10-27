package com.psychiatrygarden.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hyphenate.notification.EMNotificationMessage;
import com.hyphenate.notification.core.EMNotificationIntentReceiver;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActivityLifecycleCallbacks;
import com.psychiatrygarden.activity.ExitLoginDialogActivity;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import de.greenrobot.event.EventBus;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class EasemobReceiver extends EMNotificationIntentReceiver {
    private void getToken(final Context context, final String content) {
        HashMap map = new HashMap();
        map.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(context, NetworkRequestsURL.mGetTokenDataUrl, map, new Response.Listener() { // from class: com.psychiatrygarden.receiver.a
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                EasemobReceiver.lambda$getToken$0(context, content, (String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.receiver.b
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                EasemobReceiver.lambda$getToken$1(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getToken$0(Context context, String str, String str2, String str3) {
        try {
            JSONObject jSONObject = new JSONObject(str2);
            String secret = UserConfig.getInstance().getUser().getSecret();
            if (jSONObject.optString("code").equals("200")) {
                String strOptString = jSONObject.optJSONObject("data").optString("encryption");
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                JSONObject jSONObject2 = new JSONObject(DesUtil.decode(CommonParameter.DES_KEY_VERIFYS, strOptString));
                if (jSONObject2.optString("secret").equals(secret)) {
                    return;
                }
                Intent intent = new Intent(context, (Class<?>) ExitLoginDialogActivity.class);
                intent.putExtra("message", str);
                intent.putExtra("secret", jSONObject2.optString("secret"));
                intent.setFlags(270532608);
                context.startActivity(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getToken$1(VolleyError volleyError, String str) {
    }

    private void processCustomMessage(Context context, EMNotificationMessage notificationMessage) {
        String extras = notificationMessage.getExtras();
        try {
            JSONObject jSONObject = new JSONObject(extras);
            LogUtils.d("json_str", "" + extras);
            int iOptInt = jSONObject.optInt(PushConstants.PUSH_TYPE);
            if (iOptInt == 1) {
                getToken(context, notificationMessage.getNotificationContent());
            } else if (iOptInt != 8) {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.SYStem_Red_Dot, true, context);
                EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.notification.core.EMNotificationIntentReceiver
    public void onNotificationClick(Context context, EMNotificationMessage notificationMessage) {
        LogUtils.d("mengdepeng", "onNotificationClick 用户点击打开了通知" + notificationMessage.getExtras());
        ActivityLifecycleCallbacks activityLifecycleCallbacks = (ActivityLifecycleCallbacks) ((ProjectApp) context.getApplicationContext()).activityLifecycleCallbacks;
        try {
            List<Activity> list = activityLifecycleCallbacks.resumeActivity;
            if (list != null) {
                if (list.size() == 1 && activityLifecycleCallbacks.resumeActivity.get(0).getLocalClassName().equals("com.psychiatrygarden.activity.WelcomeActivity")) {
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_HX_PUSH_DATA, notificationMessage.getExtras()));
                } else {
                    PublicMethodActivity.getInstance().mToActivity(notificationMessage.getExtras());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.notification.core.EMNotificationIntentReceiver
    public void onNotifyMessageArrived(Context context, EMNotificationMessage notificationMessage) {
        LogUtils.e("mengdepeng", notificationMessage.toString() + "onNotifyMessageArrived: " + notificationMessage.getExtras());
        processCustomMessage(context, notificationMessage);
    }
}
