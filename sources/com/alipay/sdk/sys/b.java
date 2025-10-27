package com.alipay.sdk.sys;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.ta.utdid2.device.UTDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f3332b;

    /* renamed from: a, reason: collision with root package name */
    public Context f3333a;

    private b() {
    }

    public static b a() {
        if (f3332b == null) {
            f3332b = new b();
        }
        return f3332b;
    }

    public static boolean b() {
        String[] strArr = {"/system/xbin/", "/system/bin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i2 = 0; i2 < 5; i2++) {
            try {
                String str = strArr[i2] + "su";
                if (new File(str).exists()) {
                    String strA = a(new String[]{"ls", "-l", str});
                    if (!TextUtils.isEmpty(strA)) {
                        if (strA.indexOf("root") != strA.lastIndexOf("root")) {
                            return true;
                        }
                    }
                    return false;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private Context d() {
        return this.f3333a;
    }

    private static c e() {
        return c.a();
    }

    public final String c() {
        try {
            return UTDevice.getUtdid(this.f3333a);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3115e, com.alipay.sdk.app.statistic.c.f3120j, th);
            return "";
        }
    }

    public final void a(Context context) {
        this.f3333a = context.getApplicationContext();
    }

    private static String a(String[] strArr) {
        Process processStart;
        String line = "";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(strArr);
            processBuilder.redirectErrorStream(false);
            processStart = processBuilder.start();
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(processStart.getOutputStream());
                line = new DataInputStream(processStart.getInputStream()).readLine();
                dataOutputStream.writeBytes(c.c.f2220n);
                dataOutputStream.flush();
                processStart.waitFor();
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            processStart = null;
        }
        try {
            processStart.destroy();
        } catch (Exception unused3) {
        }
        return line;
    }
}
