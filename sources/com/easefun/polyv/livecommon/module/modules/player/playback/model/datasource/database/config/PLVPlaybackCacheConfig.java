package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.config;

import android.content.Context;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheConfig {
    private static final String TAG = "PLVPlaybackCacheConfig";
    private Context applicationContext;
    private String databaseName;
    private File downloadRootDirectory;

    public static String defaultPlaybackCacheDownloadDirectory(@NonNull final Context context) {
        File[] externalFilesDirs = context.getExternalFilesDirs(null);
        ArrayList arrayList = new ArrayList();
        for (File file : externalFilesDirs) {
            if (file != null && "mounted".equals(Environment.getExternalStorageState(file))) {
                arrayList.add(file);
            }
        }
        if (arrayList.isEmpty()) {
            PLVCommonLog.e(TAG, "没有可用的存储设备,后续不能使用视频缓存功能");
            return "";
        }
        return ((File) arrayList.get(0)).getAbsolutePath() + File.separator + "playback_cache";
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public File getDownloadRootDirectory() {
        return this.downloadRootDirectory;
    }

    public PLVPlaybackCacheConfig setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    public PLVPlaybackCacheConfig setDatabaseNameByViewerId(String viewerId) {
        this.databaseName = "playback_cache_" + viewerId + ".db";
        return this;
    }

    public PLVPlaybackCacheConfig setDownloadRootDirectory(File downloadRootDirectory) {
        this.downloadRootDirectory = downloadRootDirectory;
        PLVCommonLog.i("PLVPlaybackCacheConfig", "setDownloadRootDirectory: " + downloadRootDirectory.getAbsolutePath());
        return this;
    }
}
