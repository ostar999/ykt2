package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.config.PLVPlaybackCacheConfig;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter.PLVLiveChannelTypeConverter;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter.PLVPlaybackListTypeConverter;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.plv.foundationsdk.log.PLVCommonLog;

@TypeConverters({PLVPlaybackCacheDownloadStatusEnum.Converter.class, PLVLiveChannelTypeConverter.class, PLVPlaybackListTypeConverter.class})
@Database(entities = {PLVPlaybackCacheVideoVO.class}, exportSchema = false, version = 1)
/* loaded from: classes3.dex */
public abstract class PLVPlaybackCacheDatabase extends RoomDatabase {
    public static PLVPlaybackCacheDatabase getInstance(PLVPlaybackCacheConfig config) {
        try {
            return (PLVPlaybackCacheDatabase) Room.databaseBuilder(config.getApplicationContext(), PLVPlaybackCacheDatabase.class, config.getDatabaseName()).build();
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return new PLVEmptyCacheDatabase();
        }
    }

    public abstract IPLVPlaybackCacheDAO getPlaybackCacheDAO();
}
