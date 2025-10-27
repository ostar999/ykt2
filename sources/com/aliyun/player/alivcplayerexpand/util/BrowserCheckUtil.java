package com.aliyun.player.alivcplayerexpand.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.just.agentweb.DefaultWebClient;
import java.util.List;

/* loaded from: classes2.dex */
public class BrowserCheckUtil {
    @SuppressLint({"WrongConstant"})
    public static List<ResolveInfo> checkBrowserList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(DefaultWebClient.HTTP_SCHEME));
        return packageManager.queryIntentActivities(intent, 32);
    }
}
