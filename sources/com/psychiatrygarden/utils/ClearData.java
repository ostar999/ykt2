package com.psychiatrygarden.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.math.BigDecimal;

/* loaded from: classes6.dex */
public class ClearData {
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String str : filepath) {
            cleanCustomCache(str);
        }
    }

    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    public static void clearIntExtCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
        if (Environment.getExternalStorageState().equals("mounted")) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    private static boolean deleteFilesByDirectory(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String str : dir.list()) {
                if (!deleteFilesByDirectory(new File(dir, str))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    deleteFolderFile(file2.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else if (file.listFiles().length == 0) {
                    file.delete();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String getCacheSize(Context context) {
        long folderSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals("mounted")) {
            folderSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(folderSize);
    }

    public static long getCacheSizeInt(Context context) {
        long folderSize = getFolderSize(context.getCacheDir());
        return Environment.getExternalStorageState().equals("mounted") ? folderSize + getFolderSize(context.getExternalCacheDir()) : folderSize;
    }

    public static long getFolderSize(File file) {
        long folderSize = 0;
        try {
            File[] fileArrListFiles = file.listFiles();
            for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                folderSize += fileArrListFiles[i2].isDirectory() ? getFolderSize(fileArrListFiles[i2]) : fileArrListFiles[i2].length();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return (int) folderSize;
    }

    public static String getFormatSize(double size) {
        double d3 = size / 1024.0d;
        if (d3 < 1.0d) {
            if (d3 == 0.0d) {
                return "0B";
            }
            return size + "B";
        }
        double d4 = d3 / 1024.0d;
        if (d4 < 1.0d) {
            return new BigDecimal(Double.toString(d3)).setScale(2, 4).toPlainString() + "KB";
        }
        double d5 = d4 / 1024.0d;
        if (d5 < 1.0d) {
            return new BigDecimal(Double.toString(d4)).setScale(2, 4).toPlainString() + "MB";
        }
        double d6 = d5 / 1024.0d;
        if (d6 < 1.0d) {
            return new BigDecimal(Double.toString(d5)).setScale(2, 4).toPlainString() + "GB";
        }
        return new BigDecimal(d6).setScale(2, 4).toPlainString() + "TB";
    }

    public void clearInternalOrExCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
    }
}
