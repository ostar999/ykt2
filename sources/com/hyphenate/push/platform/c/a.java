package com.hyphenate.push.platform.c;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.hyphenate.chat.EMChatService;
import com.hyphenate.chat.EMJobService;
import com.hyphenate.easeui.utils.RomUtils;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.Utils;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8874a = "EMNormalPush";

    /* renamed from: b, reason: collision with root package name */
    private static final int f8875b = 11;

    /* renamed from: c, reason: collision with root package name */
    private static final int f8876c = 60000;

    public static void c(Context context) {
        EMLog.e(f8874a, "startChatService");
        try {
            context.startService(new Intent(context, (Class<?>) EMChatService.class));
        } catch (Exception e2) {
            EMLog.e(f8874a, e2.getMessage());
        }
    }

    public static void d(Context context) {
        EMLog.e(f8874a, "stopChatService");
        try {
            context.stopService(new Intent(context, (Class<?>) EMChatService.class));
        } catch (Exception e2) {
            EMLog.e(f8874a, e2.getMessage());
        }
    }

    @TargetApi(21)
    public static void e(Context context) {
        EMLog.e(f8874a, "scheduleJob");
        String str = Build.MANUFACTURER;
        if (!str.toUpperCase().equals(RomUtils.ROM_OPPO)) {
            str.toUpperCase().equals(RomUtils.ROM_VIVO);
        }
        try {
            context.startService(new Intent(context, (Class<?>) EMJobService.class));
            JobInfo.Builder builder = new JobInfo.Builder(11, new ComponentName(context, (Class<?>) EMJobService.class));
            builder.setPeriodic(60000L);
            builder.setRequiredNetworkType(1);
            builder.setPersisted(true);
            ((JobScheduler) context.getSystemService("jobscheduler")).schedule(builder.build());
        } catch (Exception e2) {
            EMLog.e(f8874a, e2.getMessage());
        }
    }

    @TargetApi(21)
    public static void f(Context context) {
        EMLog.e(f8874a, "cancelJob");
        if (Utils.isSdk21()) {
            String str = Build.MANUFACTURER;
            if (!str.toUpperCase().equals(RomUtils.ROM_OPPO)) {
                str.toUpperCase().equals(RomUtils.ROM_VIVO);
            }
            try {
                ((JobScheduler) context.getSystemService("jobscheduler")).cancel(11);
                context.stopService(new Intent(context, (Class<?>) EMJobService.class));
            } catch (Exception e2) {
                EMLog.e(f8874a, e2.getMessage());
            }
        }
    }

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return null;
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.NORMAL;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
        d(context);
        f(context);
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        c(context);
        e(context);
    }
}
