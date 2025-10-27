package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import io.reactivex.Flowable;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVEmptyCacheDatabase extends PLVPlaybackCacheDatabase {
    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
    }

    @Override // androidx.room.RoomDatabase
    @NonNull
    public InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override // androidx.room.RoomDatabase
    @NonNull
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVPlaybackCacheDatabase
    public IPLVPlaybackCacheDAO getPlaybackCacheDAO() {
        return new IPLVPlaybackCacheDAO() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVEmptyCacheDatabase.1
            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
            public void deletePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
            public PLVPlaybackCacheVideoVO getPlaybackCacheVideo(String id) {
                return null;
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
            public void insertPlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
            public Flowable<List<PLVPlaybackCacheVideoVO>> listPlaybackCacheVideos() {
                return Flowable.empty();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
            public void updatePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
            }
        };
    }
}
