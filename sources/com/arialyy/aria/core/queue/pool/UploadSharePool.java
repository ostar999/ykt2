package com.arialyy.aria.core.queue.pool;

/* loaded from: classes2.dex */
public class UploadSharePool {
    private static volatile UploadSharePool INSTANCE;
    public UploadExecutePool executePool = new UploadExecutePool();
    public BaseCachePool cachePool = new BaseCachePool();

    private UploadSharePool() {
    }

    public static UploadSharePool getInstance() {
        if (INSTANCE == null) {
            synchronized (UploadSharePool.class) {
                INSTANCE = new UploadSharePool();
            }
        }
        return INSTANCE;
    }
}
