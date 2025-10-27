package com.plv.foundationsdk.download.utils;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.File;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVDownloadDirUtil {
    private static final String TAG = "PLVDownloadDirUtil";

    private static boolean executeMkdirs(File file) {
        if (file.exists() || !file.mkdirs()) {
            return file.exists();
        }
        return true;
    }

    public static boolean mkdirs(@NonNull File file) throws InterruptedException {
        if (file.exists()) {
            return true;
        }
        for (int i2 = 3; i2 > 0; i2--) {
            if (executeMkdirs(file)) {
                return true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(300L);
            } catch (InterruptedException e2) {
                PLVCommonLog.e(TAG, PLVDownloadErrorMessageUtils.getExceptionFullMessage(e2, -1));
            }
        }
        return false;
    }
}
