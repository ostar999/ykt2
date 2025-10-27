package com.meizu.cloud.pushsdk.notification.a;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MessageV4;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.c.e;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.io.File;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class d extends c {
    public d(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    public void a(Notification.Builder builder, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
            bigTextStyle.setBigContentTitle(messageV3.getTitle());
            bigTextStyle.bigText(messageV3.getmNotificationStyle().getExpandableText());
            builder.setStyle(bigTextStyle);
        }
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    public void a(Notification notification, MessageV3 messageV3) throws JSONException {
        super.a(notification, messageV3);
        MessageV4 messageV4 = MessageV4.parse(messageV3);
        if (messageV4.getActVideoSetting() == null || (messageV4.getActVideoSetting().isWifiDisplay() && !com.meizu.cloud.pushsdk.util.a.b(this.f9473a))) {
            DebugLogger.e("AbstractPushNotification", "only wifi can download act");
            return;
        }
        final String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdkAct/" + messageV3.getUploadDataPackageName();
        String strValueOf = String.valueOf(System.currentTimeMillis());
        String actUrl = messageV4.getActVideoSetting().getActUrl();
        if (!TextUtils.isEmpty(actUrl) && com.meizu.cloud.pushsdk.b.a.a(actUrl, str, strValueOf).a().c().b()) {
            DebugLogger.i("AbstractPushNotification", "down load " + actUrl + " success");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            String str2 = File.separator;
            sb.append(str2);
            sb.append("ACT-");
            sb.append(strValueOf);
            String string = sb.toString();
            boolean zA = new e(str + str2 + strValueOf, string).a();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("zip file ");
            sb2.append(zA);
            DebugLogger.i("AbstractPushNotification", sb2.toString());
            if (zA) {
                Bundle bundle = new Bundle();
                bundle.putString("path", string);
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("big", bundle);
                if (MinSdkChecker.isSupportVideoNotification()) {
                    notification.extras.putBundle("flyme.active", bundle2);
                }
            }
        }
        com.meizu.cloud.pushsdk.c.b.a.b.a(new Runnable() { // from class: com.meizu.cloud.pushsdk.notification.a.d.1
            @Override // java.lang.Runnable
            public void run() {
                for (File file : com.meizu.cloud.pushsdk.notification.c.a.b(str, String.valueOf(System.currentTimeMillis() - 86400000))) {
                    com.meizu.cloud.pushsdk.notification.c.a.b(file.getPath());
                    DebugLogger.i("AbstractPushNotification", "Delete file directory " + file.getName() + "\n");
                }
            }
        });
    }
}
