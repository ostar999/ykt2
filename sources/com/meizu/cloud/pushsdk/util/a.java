package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* loaded from: classes4.dex */
public class a {
    public static NetworkInfo a(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean b(Context context) {
        NetworkInfo networkInfoA = a(context);
        return networkInfoA != null && networkInfoA.isConnected() && networkInfoA.getType() == 1;
    }
}
