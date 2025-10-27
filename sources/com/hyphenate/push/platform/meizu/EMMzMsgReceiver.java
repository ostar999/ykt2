package com.hyphenate.push.platform.meizu;

import android.content.Context;
import android.util.Log;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/* loaded from: classes4.dex */
public class EMMzMsgReceiver extends MzPushMessageReceiver {
    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        Log.i("MeizuPush", "onNotificationClicked: " + mzPushMessage.toString());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
        Log.i("MeizuPush", "onNotificationDeleted: " + mzPushMessage.toString());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegister(Context context, String str) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        String pushId = registerStatus.getPushId();
        Log.i("MeizuPush", "pushId: " + pushId);
        EMPushHelper.getInstance().onReceiveToken(EMPushType.MEIZUPUSH, pushId);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegister(Context context, boolean z2) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
    }
}
