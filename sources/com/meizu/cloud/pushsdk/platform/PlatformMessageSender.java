package com.meizu.cloud.pushsdk.platform;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.a.b.d;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.c;
import com.tencent.connect.common.Constants;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class PlatformMessageSender {

    public interface a {
        String a();

        BasicPushStatus b();

        String c();
    }

    public static void a(Context context, int i2, boolean z2, String str) {
        String appVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.i("PlatformMessageSender", context.getPackageName() + " switchPushMessageSetting cloudVersion_name " + appVersionName);
        if (TextUtils.isEmpty(appVersionName) || !appVersionName.startsWith(Constants.VIA_SHARE_TYPE_INFO)) {
            return;
        }
        Intent intent = new Intent(PushConstants.MZ_PUSH_ON_MESSAGE_SWITCH_SETTING);
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_TYPE, i2);
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_STATUS, z2);
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_PACKAGE_NAME, str);
        intent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
        context.startService(intent);
    }

    private static void a(Context context, String str, a aVar) {
        Intent intent = new Intent();
        intent.addCategory(str);
        intent.setPackage(str);
        intent.putExtra("method", aVar.a());
        intent.putExtra(aVar.c(), aVar.b());
        MzSystemUtils.sendMessageFromBroadcast(context, intent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, str);
        MzSystemUtils.sendMessageFromBroadcast(context, new Intent("com.meizu.cloud.pushservice.action.PUSH_SERVICE_START"), null, str);
    }

    public static void a(Context context, String str, final PushSwitchStatus pushSwitchStatus) {
        a(context, str, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.1
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return pushSwitchStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS;
            }
        });
    }

    public static void a(Context context, String str, final RegisterStatus registerStatus) {
        a(context, str, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.2
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_REGISTER_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return registerStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_REGISTER_STATUS;
            }
        });
    }

    public static void a(Context context, String str, final SubAliasStatus subAliasStatus) {
        a(context, str, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.5
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBALIAS_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return subAliasStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SUBALIAS_STATUS;
            }
        });
    }

    public static void a(Context context, String str, final SubTagsStatus subTagsStatus) {
        a(context, str, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.4
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBTAGS_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return subTagsStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_SUBTAGS_STATUS;
            }
        });
    }

    public static void a(Context context, String str, final UnRegisterStatus unRegisterStatus) {
        a(context, str, new a() { // from class: com.meizu.cloud.pushsdk.platform.PlatformMessageSender.3
            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String a() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_UNREGISTER_STATUS;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public BasicPushStatus b() {
                return unRegisterStatus;
            }

            @Override // com.meizu.cloud.pushsdk.platform.PlatformMessageSender.a
            public String c() {
                return PushConstants.EXTRA_APP_PUSH_UNREGISTER_STATUS;
            }
        });
    }

    public static void launchStartActivity(Context context, String str, String str2, String str3) throws JSONException {
        d dVarA = c.a(str3);
        MessageV3 messageV3 = MessageV3.parse(str, str, dVarA.e(), dVarA.f(), dVarA.c(), dVarA.d(), str2);
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        if (!TextUtils.isEmpty(str)) {
            intent.setPackage(str);
            intent.setClassName(str, "com.meizu.cloud.pushsdk.NotificationService");
        }
        intent.putExtra("command_type", "reflect_receiver");
        DebugLogger.i("PlatformMessageSender", "start notification service " + messageV3);
        try {
            context.startService(intent);
        } catch (Exception e2) {
            DebugLogger.e("PlatformMessageSender", "launchStartActivity error " + e2.getMessage());
        }
    }

    public static void showQuickNotification(Context context, String str, String str2) {
        d dVarA = c.a(str2);
        Intent intent = new Intent();
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID, dVarA.d());
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID, dVarA.c());
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_TASK_TIMES_TAMP, dVarA.e());
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SERVICE_DEFAULT_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, str);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY, dVarA.f());
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW_V3);
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        intent.setClassName(context.getPackageName(), "com.meizu.cloud.pushsdk.NotificationService");
        intent.putExtra("command_type", "reflect_receiver");
        try {
            DebugLogger.e("PlatformMessageSender", "start noficationservice to show notification");
            context.startService(intent);
        } catch (Exception e2) {
            DebugLogger.e("PlatformMessageSender", "showNotification error " + e2.getMessage());
        }
    }
}
