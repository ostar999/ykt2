package com.beizi.ad.a.a;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.io.File;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    public static final File f3706a = b(AliyunLogCommon.SubModule.download);

    public static final File a(Context context) {
        File fileB = b(context);
        if (fileB == null) {
            return null;
        }
        File file = new File(fileB.getPath() + "/Beizi/download/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File b(String str) {
        File file = new File(a(), str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File c(Context context) {
        File externalCacheDir = ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) ? context.getExternalCacheDir() : null;
        return externalCacheDir == null ? context.getCacheDir() : externalCacheDir;
    }

    public static void b(Context context, String str) {
        try {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
            if (launchIntentForPackage != null) {
                launchIntentForPackage.setFlags(268435456);
                context.startActivity(launchIntentForPackage);
            }
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, "启动失败:" + str, 1).show();
        }
    }

    private static File a() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return null;
        }
        File file = new File(Environment.getExternalStorageDirectory(), "Beizi");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            File externalFilesDir = ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) ? context.getExternalFilesDir(null) : null;
            return externalFilesDir == null ? context.getFilesDir() : externalFilesDir;
        } catch (Exception unused) {
            return context.getFilesDir();
        }
    }

    public static boolean a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return context.getPackageManager().getApplicationInfo(str, 0) != null;
    }

    public static void a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }
}
