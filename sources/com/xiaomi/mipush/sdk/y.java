package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.dp;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes6.dex */
final class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24584a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f171a;

    public y(Context context, boolean z2) {
        this.f24584a = context;
        this.f171a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        HashMap<String, String> mapA;
        String absolutePath;
        File logFile;
        File file = null;
        try {
            mapA = al.a(this.f24584a, "");
            if (this.f171a) {
                absolutePath = this.f24584a.getFilesDir().getAbsolutePath();
            } else {
                absolutePath = this.f24584a.getExternalFilesDir(null).getAbsolutePath() + dp.f329a;
            }
            logFile = Logger.getLogFile(absolutePath);
        } catch (Throwable th) {
            th = th;
        }
        if (logFile == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("log file null");
            return;
        }
        File file2 = new File(absolutePath, this.f24584a.getPackageName() + ".zip");
        try {
            com.xiaomi.push.y.a(file2, logFile);
            if (file2.exists()) {
                com.xiaomi.push.as.a((this.f171a ? "https://api.xmpush.xiaomi.com/upload/xmsf_log?file=" : "https://api.xmpush.xiaomi.com/upload/app_log?file=") + file2.getName(), mapA, file2, "file");
            } else {
                com.xiaomi.channel.commonutils.logger.b.m117a("zip log file failed");
            }
        } catch (Throwable th2) {
            th = th2;
            file = file2;
            com.xiaomi.channel.commonutils.logger.b.a(th);
            file2 = file;
            if (file2 == null) {
            } else {
                return;
            }
        }
        if (file2 == null && file2.exists()) {
            file2.delete();
        }
    }
}
