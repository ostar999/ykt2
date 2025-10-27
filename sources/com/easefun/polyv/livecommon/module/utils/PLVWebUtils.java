package com.easefun.polyv.livecommon.module.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.just.agentweb.DefaultWebClient;

/* loaded from: classes3.dex */
public class PLVWebUtils {
    public static void openWebLink(String action, Context activity) {
        Intent intent = new Intent();
        if (!action.startsWith("http")) {
            action = DefaultWebClient.HTTPS_SCHEME + action;
        }
        intent.setData(Uri.parse(action));
        intent.setAction("android.intent.action.VIEW");
        activity.startActivity(intent);
    }
}
