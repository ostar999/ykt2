package com.plv.foundationsdk;

import android.text.TextUtils;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class PLVUAClient {

    /* renamed from: a, reason: collision with root package name */
    private static String f10768a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f10769b = "";

    @NonNull
    public static String generateUserAgent(String str, String str2) {
        f10769b = str2;
        String str3 = str2 + "/";
        if (!TextUtils.isEmpty(str)) {
            str3 = str3 + str;
        }
        String str4 = str3 + " " + System.getProperty("http.agent");
        f10768a = str4;
        return str4;
    }

    public static String getUserAgent() {
        return TextUtils.isEmpty(f10768a) ? generateUserAgent(null, f10769b) : f10768a;
    }
}
