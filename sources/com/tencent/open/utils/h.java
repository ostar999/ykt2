package com.tencent.open.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.open.log.SLog;
import java.lang.ref.WeakReference;
import java.net.URL;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static h f20678a;

    /* renamed from: b, reason: collision with root package name */
    private volatile WeakReference<SharedPreferences> f20679b = null;

    public static synchronized h a() {
        if (f20678a == null) {
            f20678a = new h();
        }
        return f20678a;
    }

    public String a(Context context, String str) {
        if (this.f20679b == null || this.f20679b.get() == null) {
            this.f20679b = new WeakReference<>(context.getSharedPreferences("ServerPrefs", 0));
        }
        try {
            String host = new URL(str).getHost();
            if (host == null) {
                SLog.e("openSDK_LOG.ServerSetting", "Get host error. url=" + str);
                return str;
            }
            String string = this.f20679b.get().getString(host, null);
            if (string != null && !host.equals(string)) {
                String strReplace = str.replace(host, string);
                SLog.v("openSDK_LOG.ServerSetting", "return environment url : " + strReplace);
                return strReplace;
            }
            SLog.v("openSDK_LOG.ServerSetting", "host=" + host + ", envHost=" + string);
            return str;
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.ServerSetting", "getEnvUrl url=" + str + "error.: " + e2.getMessage());
            return str;
        }
    }
}
