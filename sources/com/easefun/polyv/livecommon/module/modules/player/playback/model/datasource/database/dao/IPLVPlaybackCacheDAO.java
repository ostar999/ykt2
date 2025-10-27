package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import io.reactivex.Flowable;
import java.util.List;

@Dao
/* loaded from: classes3.dex */
public interface IPLVPlaybackCacheDAO {
    @Delete
    void deletePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO);

    @Query("SELECT * FROM playback_cache_video_table WHERE videoPoolId = :id LIMIT 1")
    PLVPlaybackCacheVideoVO getPlaybackCacheVideo(String id);

    @Insert(onConflict = 1)
    void insertPlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO);

    @Query("SELECT * FROM playback_cache_video_table")
    Flowable<List<PLVPlaybackCacheVideoVO>> listPlaybackCacheVideos();

    @Update
    void updatePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO);
}
