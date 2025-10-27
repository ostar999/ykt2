package com.beizi.fusion.g;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.webkit.WebView;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes2.dex */
public class av {
    public static void a(Context context) {
        if (context != null) {
            String packageName = context.getPackageName();
            if (Build.VERSION.SDK_INT >= 28) {
                try {
                    try {
                        int iMyPid = Process.myPid();
                        String str = context.getPackageName() + iMyPid;
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                            if (runningAppProcessInfo.pid == iMyPid) {
                                str = runningAppProcessInfo.processName;
                            }
                        }
                        WebView.setDataDirectorySuffix(str);
                    } catch (Exception unused) {
                        WebView.setDataDirectorySuffix(packageName);
                    }
                } catch (Exception unused2) {
                }
            }
        }
    }
}
