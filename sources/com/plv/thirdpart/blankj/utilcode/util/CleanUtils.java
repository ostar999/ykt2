package com.plv.thirdpart.blankj.utilcode.util;

import android.os.Environment;
import java.io.File;

/* loaded from: classes5.dex */
public final class CleanUtils {
    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean cleanCustomCache(String str) {
        return deleteFilesInDir(str);
    }

    public static boolean cleanExternalCache() {
        return "mounted".equals(Environment.getExternalStorageState()) && deleteFilesInDir(Utils.getApp().getExternalCacheDir());
    }

    public static boolean cleanInternalCache() {
        return deleteFilesInDir(Utils.getApp().getCacheDir());
    }

    public static boolean cleanInternalDbByName(String str) {
        return Utils.getApp().deleteDatabase(str);
    }

    public static boolean cleanInternalDbs() {
        return deleteFilesInDir(Utils.getApp().getFilesDir().getParent() + File.separator + "databases");
    }

    public static boolean cleanInternalFiles() {
        return deleteFilesInDir(Utils.getApp().getFilesDir());
    }

    public static boolean cleanInternalSP() {
        return deleteFilesInDir(Utils.getApp().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    private static boolean deleteDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    private static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean cleanCustomCache(File file) {
        return deleteFilesInDir(file);
    }

    private static boolean deleteFilesInDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
