package com.aliyun.player.alivcplayerexpand.util.download;

import android.content.Context;
import com.alibaba.sdk.android.oss.common.OSSConstants;

/* loaded from: classes2.dex */
public class DownloadUtils {
    public static String getSaveDir(Context context) {
        return AliyunDownloadManager.getInstance(context).getDownloadDir();
    }

    public static boolean isStorageAlarm(Context context, AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        long availableExternalMemorySize = StorageUtil.isExternalMemoryPath(getSaveDir(context)) ? StorageUtil.getAvailableExternalMemorySize() : StorageUtil.getAvailableInternalMemorySize();
        return availableExternalMemorySize > 0 && availableExternalMemorySize - ((((long) (100 - aliyunDownloadMediaInfo.getProgress())) * aliyunDownloadMediaInfo.getSize()) / OSSConstants.MIN_PART_SIZE_LIMIT) > StorageUtil.MINIST_STORAGE_SIZE;
    }

    public static boolean isStorageAlarm(Context context) {
        long availableInternalMemorySize;
        if (StorageUtil.isExternalMemoryPath(getSaveDir(context))) {
            availableInternalMemorySize = StorageUtil.getAvailableExternalMemorySize();
        } else {
            availableInternalMemorySize = StorageUtil.getAvailableInternalMemorySize();
        }
        return availableInternalMemorySize > 0 && availableInternalMemorySize < StorageUtil.MIN_STORAGE_SIZE;
    }
}
