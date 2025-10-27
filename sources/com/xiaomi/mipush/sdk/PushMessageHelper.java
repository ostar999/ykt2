package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.push.iq;
import com.xiaomi.push.ji;
import java.util.List;

/* loaded from: classes6.dex */
public class PushMessageHelper {
    public static final String ERROR_MESSAGE = "error_message";
    public static final String ERROR_TYPE = "error_type";
    public static final String ERROR_TYPE_NEED_PERMISSION = "error_lack_of_permission";
    public static final String KEY_COMMAND = "key_command";
    public static final String KEY_MESSAGE = "key_message";
    public static final int MESSAGE_COMMAND = 3;
    public static final int MESSAGE_ERROR = 5;
    public static final int MESSAGE_QUIT = 4;
    public static final int MESSAGE_RAW = 1;
    public static final int MESSAGE_SENDMESSAGE = 2;
    public static final String MESSAGE_TYPE = "message_type";
    public static final int PUSH_MODE_BROADCAST = 2;
    public static final int PUSH_MODE_CALLBACK = 1;
    private static int pushMode;

    public static MiPushCommandMessage generateCommandMessage(String str, List<String> list, long j2, String str2, String str3) {
        MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
        miPushCommandMessage.setCommand(str);
        miPushCommandMessage.setCommandArguments(list);
        miPushCommandMessage.setResultCode(j2);
        miPushCommandMessage.setReason(str2);
        miPushCommandMessage.setCategory(str3);
        return miPushCommandMessage;
    }

    public static MiPushMessage generateMessage(ji jiVar, iq iqVar, boolean z2) {
        MiPushMessage miPushMessage = new MiPushMessage();
        miPushMessage.setMessageId(jiVar.m630a());
        if (!TextUtils.isEmpty(jiVar.d())) {
            miPushMessage.setMessageType(1);
            miPushMessage.setAlias(jiVar.d());
        } else if (!TextUtils.isEmpty(jiVar.c())) {
            miPushMessage.setMessageType(2);
            miPushMessage.setTopic(jiVar.c());
        } else if (TextUtils.isEmpty(jiVar.f())) {
            miPushMessage.setMessageType(0);
        } else {
            miPushMessage.setMessageType(3);
            miPushMessage.setUserAccount(jiVar.f());
        }
        miPushMessage.setCategory(jiVar.e());
        if (jiVar.a() != null) {
            miPushMessage.setContent(jiVar.a().c());
        }
        if (iqVar != null) {
            if (TextUtils.isEmpty(miPushMessage.getMessageId())) {
                miPushMessage.setMessageId(iqVar.m554a());
            }
            if (TextUtils.isEmpty(miPushMessage.getTopic())) {
                miPushMessage.setTopic(iqVar.m559b());
            }
            miPushMessage.setDescription(iqVar.d());
            miPushMessage.setTitle(iqVar.m562c());
            miPushMessage.setNotifyType(iqVar.a());
            miPushMessage.setNotifyId(iqVar.c());
            miPushMessage.setPassThrough(iqVar.b());
            miPushMessage.setExtra(iqVar.m555a());
        }
        miPushMessage.setNotified(z2);
        return miPushMessage;
    }

    public static iq generateMessage(MiPushMessage miPushMessage) {
        iq iqVar = new iq();
        iqVar.a(miPushMessage.getMessageId());
        iqVar.b(miPushMessage.getTopic());
        iqVar.d(miPushMessage.getDescription());
        iqVar.c(miPushMessage.getTitle());
        iqVar.c(miPushMessage.getNotifyId());
        iqVar.a(miPushMessage.getNotifyType());
        iqVar.b(miPushMessage.getPassThrough());
        iqVar.a(miPushMessage.getExtra());
        return iqVar;
    }

    public static int getPushMode(Context context) {
        if (pushMode == 0) {
            setPushMode(isUseCallbackPushMode(context) ? 1 : 2);
        }
        return pushMode;
    }

    private static boolean isIntentAvailable(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (listQueryBroadcastReceivers != null) {
                if (!listQueryBroadcastReceivers.isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean isUseCallbackPushMode(Context context) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setClassName(context.getPackageName(), "com.xiaomi.mipush.sdk.PushServiceReceiver");
        return isIntentAvailable(context, intent);
    }

    public static void sendCommandMessageBroadcast(Context context, MiPushCommandMessage miPushCommandMessage) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        intent.putExtra("message_type", 3);
        intent.putExtra(KEY_COMMAND, miPushCommandMessage);
        new PushServiceReceiver().onReceive(context, intent);
    }

    public static void sendQuitMessageBroadcast(Context context) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        intent.putExtra("message_type", 4);
        new PushServiceReceiver().onReceive(context, intent);
    }

    private static void setPushMode(int i2) {
        pushMode = i2;
    }
}
