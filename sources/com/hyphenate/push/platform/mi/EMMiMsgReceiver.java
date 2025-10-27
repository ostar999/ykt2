package com.hyphenate.push.platform.mi;

import android.content.Context;
import android.content.Intent;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.util.EMLog;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import java.util.List;

/* loaded from: classes4.dex */
public class EMMiMsgReceiver extends PushMessageReceiver {
    private static final String TAG = "EMMiMsgReceiver";

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        EMLog.i(TAG, "onCommandResult is called. " + miPushCommandMessage.toString());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        EMLog.i(TAG, "onNotificationMessageArrived is called. " + miPushMessage.toString());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        EMLog.i(TAG, "onNotificationMessageClicked is called. " + miPushMessage.toString());
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntentForPackage.addFlags(268435456);
        context.startActivity(launchIntentForPackage);
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        EMLog.i(TAG, "onReceivePassThroughMessage is called. " + miPushMessage.toString());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String command = miPushCommandMessage.getCommand();
        List<String> commandArguments = miPushCommandMessage.getCommandArguments();
        String str = null;
        String str2 = (commandArguments == null || commandArguments.size() <= 0) ? null : commandArguments.get(0);
        if (commandArguments != null && commandArguments.size() > 1) {
            str = commandArguments.get(1);
        }
        EMLog.i(TAG, "onReceiveRegisterResult. cmdArg1: " + str2 + "; cmdArg2:" + str);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                EMPushHelper.getInstance().onReceiveToken(EMPushType.MIPUSH, str2);
            } else {
                EMPushHelper.getInstance().onErrorResponse(EMPushType.MIPUSH, miPushCommandMessage.getResultCode());
            }
        }
    }
}
