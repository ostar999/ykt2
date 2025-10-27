package com.psychiatrygarden.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.push.platform.vivo.EMVivoMsgReceiver;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.bean.GroupChatListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.vivo.push.model.UPSNotificationMessage;
import java.util.Map;

/* loaded from: classes6.dex */
public class VivoReceiver extends EMVivoMsgReceiver {
    @Override // com.hyphenate.push.platform.vivo.EMVivoMsgReceiver, com.vivo.push.sdk.OpenClientPushMessageReceiver, com.vivo.push.sdk.PushMessageCallback
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        Map<String, String> params = upsNotificationMessage.getParams();
        if (!params.isEmpty()) {
            String str = params.get("t");
            String str2 = params.get("f");
            String str3 = params.get("m");
            String str4 = params.get("g");
            String str5 = params.get(AliyunLogKey.KEY_EVENT);
            Log.e("mengdepeng", "vivoonCreate:  t:" + str + " f:" + str2 + " m:" + str3 + " g:" + str4);
            try {
                if (!TextUtils.isEmpty(str4)) {
                    Intent intent = new Intent(context, (Class<?>) ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
                    GroupChatListBean groupChatListBean = (GroupChatListBean) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.MY_GROUP_CHAT_LIST, context, ""), GroupChatListBean.class);
                    if (groupChatListBean.getCode().equals("200")) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= groupChatListBean.getData().size()) {
                                break;
                            }
                            if (groupChatListBean.getData().get(i2).getCommunity_id().equals(str4)) {
                                intent.putExtra("group_img", groupChatListBean.getData().get(i2).getLogo());
                                intent.putExtra("name", groupChatListBean.getData().get(i2).getName());
                                break;
                            }
                            i2++;
                        }
                    }
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                } else if (!TextUtils.isEmpty(str2)) {
                    Intent intent2 = new Intent(context, (Class<?>) ChatActivity.class);
                    intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
                    intent2.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, str2);
                    intent2.setFlags(268435456);
                    context.startActivity(intent2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (str5 != null) {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(str5));
            }
        }
        LogUtils.e("mengdepeng", "onNotificationMessageClicked: VivoReceiver" + upsNotificationMessage.getParams());
    }

    @Override // com.hyphenate.push.platform.vivo.EMVivoMsgReceiver, com.vivo.push.sdk.OpenClientPushMessageReceiver, com.vivo.push.sdk.PushMessageCallback
    public void onReceiveRegId(Context context, String s2) {
        LogUtils.e("mengdepeng", "onNotificationMessageClicked: onReceiveRegId" + s2);
    }
}
