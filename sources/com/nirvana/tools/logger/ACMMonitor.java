package com.nirvana.tools.logger;

import android.content.Context;
import com.nirvana.tools.logger.cache.ACMMonitorCacheManager;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.nirvana.tools.logger.cache.db.DbException;
import com.nirvana.tools.logger.env.ACMComponent;
import com.nirvana.tools.logger.env.ACMComponentImpl;
import com.nirvana.tools.logger.executor.ReentrantSingleThreadExecutor;
import com.nirvana.tools.logger.model.ACMLimitConfig;
import com.nirvana.tools.logger.model.ACMMonitorRecord;
import com.nirvana.tools.logger.upload.ACMMonitorUploadManager;
import com.nirvana.tools.logger.upload.ACMUpload;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ACMMonitor implements ACMComponent {
    private static final AtomicInteger MONITOR_COUNT = new AtomicInteger();
    public static final int UPLOAD_TYPE_MANUAL = 1;
    public static final int UPLOAD_TYPE_TIMER = 2;
    private ACMMonitorCacheManager mCacheManager;
    private ACMComponentImpl mEnv;
    private ACMMonitorUploadManager mUploadManager;

    public ACMMonitor(Context context, ACMUpload<ACMMonitorRecord> aCMUpload) {
        this(context, aCMUpload, null);
    }

    public ACMMonitor(Context context, ACMUpload<ACMMonitorRecord> aCMUpload, String str) {
        String str2;
        ReentrantSingleThreadExecutor reentrantSingleThreadExecutor = new ReentrantSingleThreadExecutor("ACMMonitor" + MONITOR_COUNT.getAndAdd(1));
        if (str == null) {
            str2 = DBHelpTool.TABLE_NAME_MONITOR;
        } else {
            str2 = str + "_alitx_monitor";
        }
        this.mCacheManager = new ACMMonitorCacheManager(context.getApplicationContext(), reentrantSingleThreadExecutor, str2, str);
        ACMMonitorUploadManager aCMMonitorUploadManager = new ACMMonitorUploadManager(context.getApplicationContext(), this.mCacheManager, aCMUpload, reentrantSingleThreadExecutor);
        this.mUploadManager = aCMMonitorUploadManager;
        this.mEnv = new ACMComponentImpl(aCMMonitorUploadManager);
    }

    private void cacheMonitor(String str, int i2) {
        ACMMonitorRecord aCMMonitorRecord = new ACMMonitorRecord(i2);
        aCMMonitorRecord.setContent(str);
        try {
            this.mCacheManager.cacheRecord(aCMMonitorRecord);
        } catch (DbException e2) {
            e2.printStackTrace();
        }
    }

    private void cacheMonitorRecords(List<ACMMonitorRecord> list) {
        try {
            this.mCacheManager.cacheRecords(list);
        } catch (DbException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void clearLimitConfig() {
        this.mEnv.clearLimitConfig();
    }

    public void deleteRecordsByFlag(int i2) {
        this.mUploadManager.deleteRecordsByFlag(i2);
    }

    public void monitor(String str, int i2) {
        cacheMonitor(str, i2);
    }

    public void monitor(Map<String, String> map, int i2) {
        cacheMonitor(new JSONObject(map).toString(), i2);
    }

    public void monitorRecords(List<ACMMonitorRecord> list) {
        cacheMonitorRecords(list);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setLimitConfig(ACMLimitConfig aCMLimitConfig) {
        this.mEnv.setLimitConfig(aCMLimitConfig);
    }

    public void setMaxUploadRetry(long j2) {
        this.mUploadManager.setMaxUploadRetry(j2);
    }

    public void setMaxUploadSize(int i2) {
        this.mUploadManager.setMaxUploadSize(i2);
    }

    public void setRetryCount(int i2) {
        this.mUploadManager.setRetryCount(i2);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setUploadEnabled(boolean z2) {
        this.mEnv.setUploadEnabled(z2);
    }

    public void setUploadType(int i2) {
        if (i2 == 1 || i2 == 2) {
            this.mUploadManager.setUploadType(i2);
        }
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void uploadFailed() {
        this.mEnv.uploadFailed();
    }

    public void uploadManual() {
        this.mUploadManager.uploadManual();
    }
}
