package com.tencent.smtt.utils;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.sdk.QbSdk;
import java.io.File;
import java.io.FileFilter;

/* loaded from: classes6.dex */
public class n {
    public static boolean a(Context context) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return b(context);
        }
        return true;
    }

    private static boolean a(File file) {
        try {
            return !e.b(file);
        } catch (Throwable th) {
            Log.e("TbsCheckUtils", "isOatFileBroken exception: " + th);
            return false;
        }
    }

    public static boolean b(Context context) {
        File fileC;
        try {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 25 || (fileC = c(context)) == null) {
            return true;
        }
        for (File file : fileC.listFiles(new FileFilter() { // from class: com.tencent.smtt.utils.n.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                String name = file2.getName();
                return !TextUtils.isEmpty(name) && name.endsWith(".dex");
            }
        })) {
            if (file.isFile() && file.exists()) {
                if (a(file)) {
                    TbsLog.w("TbsCheckUtils", "" + file + " is invalid --> check failed!");
                    file.delete();
                    return false;
                }
                TbsLog.i("TbsCheckUtils", "" + file + " #4 check success!");
            }
        }
        TbsLog.i("TbsCheckUtils", "checkTbsValidity -->#5 check ok!");
        return true;
    }

    private static File c(Context context) {
        File file = new File(QbSdk.getTbsFolderDir(context), "core_share");
        if (file.isDirectory() && file.exists()) {
            return file;
        }
        return null;
    }
}
