package com.aliyun.player.alivcplayerexpand.util.download;

import android.os.Environment;
import android.os.StatFs;

/* loaded from: classes2.dex */
public class StorageUtil {
    public static long MINIST_STORAGE_SIZE = 102400;
    public static long MIN_STORAGE_SIZE = 204800;

    public static boolean externalMemoryAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String formatSize(long j2) {
        String str;
        if (j2 >= 1024) {
            j2 /= 1024;
            if (j2 >= 1024) {
                j2 /= 1024;
                str = "MB";
            } else {
                str = "KB";
            }
        } else {
            str = null;
        }
        StringBuilder sb = new StringBuilder(Long.toString(j2));
        for (int length = sb.length() - 3; length > 0; length -= 3) {
            sb.insert(length, ',');
        }
        if (str != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static long getAvailableExternalMemorySize() {
        if (!externalMemoryAvailable()) {
            return -1L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong()) / 1024;
    }

    public static long getAvailableInternalMemorySize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return (statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong()) / 1024;
    }

    public static long getTotalExternalMemorySize() {
        if (!externalMemoryAvailable()) {
            return -1L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (statFs.getBlockSizeLong() * statFs.getBlockCountLong()) / 1024;
    }

    public static String getTotalExternalMemorySizeStr() {
        if (!externalMemoryAvailable()) {
            return null;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return formatSize(statFs.getBlockCountLong() * statFs.getBlockSizeLong());
    }

    public static boolean isExternalMemoryPath(String str) {
        return str.contains(Environment.getExternalStorageDirectory().getAbsolutePath());
    }
}
