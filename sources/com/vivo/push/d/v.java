package com.vivo.push.d;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
final class v implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24340a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Map f24341b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ u f24342c;

    public v(u uVar, Context context, Map map) {
        this.f24342c = uVar;
        this.f24340a = context;
        this.f24341b = map;
    }

    @Override // java.lang.Runnable
    public final void run() throws SecurityException {
        String packageName = this.f24340a.getPackageName();
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.f24340a.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(100);
            if (runningTasks != null) {
                Iterator<ActivityManager.RunningTaskInfo> it = runningTasks.iterator();
                while (it.hasNext()) {
                    ComponentName componentName = it.next().topActivity;
                    if (componentName.getPackageName().equals(packageName)) {
                        com.vivo.push.util.p.d("OnNotificationClickTask", "topClassName=" + componentName.getClassName());
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        intent.setFlags(335544320);
                        u.b(intent, this.f24341b);
                        this.f24340a.startActivity(intent);
                        return;
                    }
                }
            }
        } catch (Exception e2) {
            com.vivo.push.util.p.a("OnNotificationClickTask", "start recentIntent is error", e2);
        }
        Intent launchIntentForPackage = this.f24340a.getPackageManager().getLaunchIntentForPackage(this.f24340a.getPackageName());
        if (launchIntentForPackage == null) {
            com.vivo.push.util.p.a("OnNotificationClickTask", "LaunchIntent is null");
            return;
        }
        launchIntentForPackage.setFlags(268435456);
        u.b(launchIntentForPackage, this.f24341b);
        this.f24340a.startActivity(launchIntentForPackage);
    }
}
