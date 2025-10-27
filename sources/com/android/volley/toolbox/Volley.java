package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.android.volley.RequestQueue;
import java.io.File;

/* loaded from: classes2.dex */
public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static RequestQueue newRequestQueue(Context context, HttpStack httpStack) throws PackageManager.NameNotFoundException {
        File file = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
        try {
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            StringBuilder sb = new StringBuilder(String.valueOf(packageName));
            sb.append("/");
            sb.append(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (httpStack == null) {
            httpStack = new HurlStack();
        }
        RequestQueue requestQueue = new RequestQueue(new DiskBasedCache(file), new BasicNetwork(httpStack));
        requestQueue.start();
        return requestQueue;
    }

    public static RequestQueue newRequestQueueInDisk(Context context, String str, HttpStack httpStack) throws PackageManager.NameNotFoundException {
        File file = new File(str, DEFAULT_CACHE_DIR);
        try {
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            StringBuilder sb = new StringBuilder(String.valueOf(packageName));
            sb.append("/");
            sb.append(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (httpStack == null) {
            httpStack = new HurlStack();
        }
        RequestQueue requestQueue = new RequestQueue(new DiskBasedCache(file), new BasicNetwork(httpStack));
        requestQueue.start();
        return requestQueue;
    }

    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, null);
    }
}
