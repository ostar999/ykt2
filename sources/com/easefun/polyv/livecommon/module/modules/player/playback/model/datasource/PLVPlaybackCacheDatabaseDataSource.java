package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVPlaybackCacheDatabase;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheDatabaseDataSource {
    private static volatile PLVPlaybackCacheDatabaseDataSource INSTANCE;
    private PLVPlaybackCacheDatabase playbackCacheDatabase;

    private PLVPlaybackCacheDatabaseDataSource() {
    }

    public static PLVPlaybackCacheDatabaseDataSource getInstance(final PLVPlaybackCacheDatabase playbackCacheDatabase) {
        if (INSTANCE == null) {
            synchronized (PLVPlaybackCacheDatabaseDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVPlaybackCacheDatabaseDataSource();
                }
            }
        }
        INSTANCE.playbackCacheDatabase = playbackCacheDatabase;
        return INSTANCE;
    }

    private static void runAsync(final Runnable runnable) {
        Schedulers.single().scheduleDirect(runnable);
    }

    public void deleteCacheVideo(final PLVPlaybackCacheVideoVO vo) {
        runAsync(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheDatabaseDataSource.3
            @Override // java.lang.Runnable
            public void run() {
                PLVPlaybackCacheDatabaseDataSource.this.playbackCacheDatabase.getPlaybackCacheDAO().deletePlaybackCache(vo);
            }
        });
    }

    @Nullable
    @WorkerThread
    public PLVPlaybackCacheVideoVO getCacheVideoById(String id) {
        return this.playbackCacheDatabase.getPlaybackCacheDAO().getPlaybackCacheVideo(id);
    }

    public void insertCacheVideo(final PLVPlaybackCacheVideoVO vo) {
        runAsync(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheDatabaseDataSource.1
            @Override // java.lang.Runnable
            public void run() {
                PLVPlaybackCacheDatabaseDataSource.this.playbackCacheDatabase.getPlaybackCacheDAO().insertPlaybackCache(vo);
            }
        });
    }

    public Observable<List<PLVPlaybackCacheVideoVO>> listCacheVideos() {
        return this.playbackCacheDatabase.getPlaybackCacheDAO().listPlaybackCacheVideos().toObservable();
    }

    public void updateCacheVideo(final PLVPlaybackCacheVideoVO vo) {
        runAsync(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.PLVPlaybackCacheDatabaseDataSource.2
            @Override // java.lang.Runnable
            public void run() {
                PLVPlaybackCacheDatabaseDataSource.this.playbackCacheDatabase.getPlaybackCacheDAO().updatePlaybackCache(vo);
            }
        });
    }
}
