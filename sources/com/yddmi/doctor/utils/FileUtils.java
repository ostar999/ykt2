package com.yddmi.doctor.utils;

import android.util.Log;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public class FileUtils {
    public static boolean isExist(String str) {
        return new File(str).exists();
    }

    public static File makeFilePath(String str, String str2) throws IOException {
        makeRootDirectory(str);
        File file = null;
        try {
            File file2 = new File(str + str2);
            try {
                if (file2.exists()) {
                    return file2;
                }
                file2.createNewFile();
                return file2;
            } catch (Exception e2) {
                e = e2;
                file = file2;
                e.printStackTrace();
                return file;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static void makeRootDirectory(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return;
            }
            file.mkdir();
        } catch (Exception e2) {
            Log.i("error:", e2 + "");
        }
    }
}
