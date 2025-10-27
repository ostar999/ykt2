package com.nirvana.tools.logger.upload;

import android.content.Context;
import com.nirvana.tools.logger.cache.ACMCacheManager;
import com.nirvana.tools.logger.cache.db.AbstractDatabase;
import com.nirvana.tools.logger.cache.db.DbException;
import com.nirvana.tools.logger.executor.AbstractSafeRunnable;
import com.nirvana.tools.logger.executor.ReentrantSingleThreadExecutor;
import com.nirvana.tools.logger.model.ACMLimitConfig;
import com.nirvana.tools.logger.model.ACMRecord;
import com.nirvana.tools.logger.upload.inteceptor.BaseInterceptor;
import com.nirvana.tools.logger.upload.inteceptor.EnableInterceptor;
import com.nirvana.tools.logger.upload.inteceptor.LimitInterceptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class AbstractACMUploadManager<T extends ACMRecord> {
    protected static final long MAX_TURNS_FOR_SINGLE_UPLOAD = 100;
    protected static final int PAGE_SIZE = 20;
    protected static final long TIME_INTERVAL = 12000;
    private static final int UPLOAD_RETRY_MAX_COUNT = 1;
    private boolean isUploadingFailed;
    protected ACMCacheManager<T, ? extends AbstractDatabase<T>> mCacheManager;
    protected Context mContext;
    private EnableInterceptor mEnableInterceptor;
    protected ReentrantSingleThreadExecutor mExecutor;
    protected ACMUpload<T> mUploader;
    protected long maxUploadRetry;
    protected int maxUploadSize;
    private int retryCount;
    protected Map<Class, BaseInterceptor> mInterceptors = new HashMap();
    private Object mFailedLock = new Object();

    public AbstractACMUploadManager(Context context, ACMUpload<T> aCMUpload, ACMCacheManager<T, ? extends AbstractDatabase<T>> aCMCacheManager, ReentrantSingleThreadExecutor reentrantSingleThreadExecutor) {
        this.maxUploadSize = 0;
        this.maxUploadRetry = 0L;
        this.retryCount = 0;
        this.mContext = context;
        this.mUploader = aCMUpload;
        this.mCacheManager = aCMCacheManager;
        this.mExecutor = reentrantSingleThreadExecutor;
        this.mInterceptors.put(LimitInterceptor.class, LimitInterceptor.getInstance(context));
        this.maxUploadSize = 20;
        this.maxUploadRetry = 100L;
        this.retryCount = 1;
    }

    public void clearLimitConfig() {
        LimitInterceptor limitInterceptor = (LimitInterceptor) this.mInterceptors.remove(LimitInterceptor.class);
        if (limitInterceptor != null) {
            limitInterceptor.clearLimitInfo();
        }
    }

    public void deleteRecordsByFlag(int i2) {
        List<T> allFailedRecords = i2 == 1 ? this.mCacheManager.getAllFailedRecords() : i2 == 2 ? this.mCacheManager.getUnUploadRecords() : this.mCacheManager.getAllRecords();
        if (allFailedRecords == null || allFailedRecords.size() <= 0) {
            return;
        }
        try {
            this.mCacheManager.deleteRecords(allFailedRecords);
        } catch (DbException e2) {
            e2.printStackTrace();
        }
    }

    public boolean doUploadRecords(List<T> list) throws DbException {
        if (list != null && list.size() > 0) {
            int i2 = 0;
            boolean zUpload = false;
            while (true) {
                if (i2 > this.retryCount) {
                    break;
                }
                if (!isAllowUploading()) {
                    return true;
                }
                LimitInterceptor.getInstance(this.mContext).addLimitCount();
                zUpload = this.mUploader.upload(list);
                if (zUpload) {
                    this.mCacheManager.deleteRecords(list);
                    break;
                }
                i2++;
            }
            if (!zUpload) {
                processUploadingFailed(list);
                return false;
            }
        }
        return true;
    }

    public boolean isAllowUploading() {
        Map<Class, BaseInterceptor> map = this.mInterceptors;
        if (map != null && map.size() != 0) {
            Iterator<BaseInterceptor> it = this.mInterceptors.values().iterator();
            while (it.hasNext()) {
                if (!it.next().isAllowUploading()) {
                    return false;
                }
            }
        }
        return true;
    }

    public abstract void processUploadingFailed(List<T> list) throws DbException;

    public void setLimitConfig(ACMLimitConfig aCMLimitConfig) {
        LimitInterceptor limitInterceptor = LimitInterceptor.getInstance(this.mContext);
        limitInterceptor.setConfig(aCMLimitConfig);
        this.mInterceptors.put(LimitInterceptor.class, limitInterceptor);
    }

    public void setMaxUploadRetry(long j2) {
        this.maxUploadRetry = j2;
    }

    public void setMaxUploadSize(int i2) {
        this.maxUploadSize = i2;
    }

    public void setRetryCount(int i2) {
        this.retryCount = i2;
    }

    public void setUploadEnable(boolean z2) {
        if (this.mEnableInterceptor == null) {
            this.mEnableInterceptor = new EnableInterceptor();
        }
        this.mEnableInterceptor.setEnabled(z2);
        this.mInterceptors.put(EnableInterceptor.class, this.mEnableInterceptor);
    }

    public void uploadFailed() {
        synchronized (this.mFailedLock) {
            if (this.isUploadingFailed) {
                return;
            }
            this.isUploadingFailed = true;
            this.mExecutor.execute(new AbstractSafeRunnable() { // from class: com.nirvana.tools.logger.upload.AbstractACMUploadManager.1
                @Override // com.nirvana.tools.logger.executor.AbstractSafeRunnable
                public void safeRun() {
                    if (AbstractACMUploadManager.this.mCacheManager.hasFailedRecords()) {
                        AbstractACMUploadManager.this.mExecutor.execute(new AbstractSafeRunnable() { // from class: com.nirvana.tools.logger.upload.AbstractACMUploadManager.1.1
                            @Override // com.nirvana.tools.logger.executor.AbstractSafeRunnable
                            public void safeRun() {
                                List<T> failedRecords;
                                long failedMaxID = AbstractACMUploadManager.this.mCacheManager.getFailedMaxID();
                                long id = 0;
                                int i2 = 0;
                                while (AbstractACMUploadManager.this.isAllowUploading()) {
                                    long j2 = i2;
                                    AbstractACMUploadManager abstractACMUploadManager = AbstractACMUploadManager.this;
                                    if (j2 >= abstractACMUploadManager.maxUploadRetry || (failedRecords = abstractACMUploadManager.mCacheManager.getFailedRecords(id, failedMaxID, abstractACMUploadManager.maxUploadSize)) == null || failedRecords.size() <= 0) {
                                        break;
                                    }
                                    try {
                                        AbstractACMUploadManager.this.doUploadRecords(failedRecords);
                                        id = failedRecords.get(failedRecords.size() - 1).getId() + 1;
                                        i2++;
                                    } catch (DbException unused) {
                                    }
                                }
                                AbstractACMUploadManager.this.isUploadingFailed = false;
                            }
                        });
                    }
                }
            });
        }
    }
}
