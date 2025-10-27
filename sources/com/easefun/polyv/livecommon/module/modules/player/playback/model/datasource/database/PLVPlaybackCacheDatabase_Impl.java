package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database;

import android.database.SQLException;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO_Impl;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.tencent.connect.share.QzonePublish;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes3.dex */
public final class PLVPlaybackCacheDatabase_Impl extends PLVPlaybackCacheDatabase {
    private volatile IPLVPlaybackCacheDAO _iPLVPlaybackCacheDAO;

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() throws SQLException {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `playback_cache_video_table`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, "playback_cache_video_table");
    }

    @Override // androidx.room.RoomDatabase
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVPlaybackCacheDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `playback_cache_video_table` (`videoPoolId` TEXT NOT NULL, `videoId` TEXT, `title` TEXT, `firstImageUrl` TEXT, `videoDuration` TEXT, `liveType` TEXT, `channelSessionId` TEXT, `originSessionId` TEXT, `enableDownload` INTEGER, `progress` INTEGER, `downloadedBytes` INTEGER, `totalBytes` INTEGER, `downloadStatusEnum` TEXT, `videoPath` TEXT, `pptPath` TEXT, `jsPath` TEXT, `channelId` TEXT, `channelType` TEXT, `vid` TEXT, `viewerId` TEXT, `viewerName` TEXT, `viewerAvatar` TEXT, `playbackListType` TEXT, PRIMARY KEY(`videoPoolId`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"566328438a36dedc27cf80d74f1037dd\")");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("DROP TABLE IF EXISTS `playback_cache_video_table`");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(SupportSQLiteDatabase _db) {
                if (((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks != null) {
                    int size = ((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ((RoomDatabase.Callback) ((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks.get(i2)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                ((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mDatabase = _db;
                PLVPlaybackCacheDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks != null) {
                    int size = ((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ((RoomDatabase.Callback) ((RoomDatabase) PLVPlaybackCacheDatabase_Impl.this).mCallbacks.get(i2)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void validateMigration(SupportSQLiteDatabase _db) {
                HashMap map = new HashMap(23);
                map.put("videoPoolId", new TableInfo.Column("videoPoolId", "TEXT", true, 1));
                map.put("videoId", new TableInfo.Column("videoId", "TEXT", false, 0));
                map.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map.put("firstImageUrl", new TableInfo.Column("firstImageUrl", "TEXT", false, 0));
                map.put("videoDuration", new TableInfo.Column("videoDuration", "TEXT", false, 0));
                map.put("liveType", new TableInfo.Column("liveType", "TEXT", false, 0));
                map.put("channelSessionId", new TableInfo.Column("channelSessionId", "TEXT", false, 0));
                map.put("originSessionId", new TableInfo.Column("originSessionId", "TEXT", false, 0));
                map.put("enableDownload", new TableInfo.Column("enableDownload", "INTEGER", false, 0));
                map.put("progress", new TableInfo.Column("progress", "INTEGER", false, 0));
                map.put("downloadedBytes", new TableInfo.Column("downloadedBytes", "INTEGER", false, 0));
                map.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", false, 0));
                map.put("downloadStatusEnum", new TableInfo.Column("downloadStatusEnum", "TEXT", false, 0));
                map.put(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, new TableInfo.Column(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, "TEXT", false, 0));
                map.put("pptPath", new TableInfo.Column("pptPath", "TEXT", false, 0));
                map.put("jsPath", new TableInfo.Column("jsPath", "TEXT", false, 0));
                map.put("channelId", new TableInfo.Column("channelId", "TEXT", false, 0));
                map.put("channelType", new TableInfo.Column("channelType", "TEXT", false, 0));
                map.put("vid", new TableInfo.Column("vid", "TEXT", false, 0));
                map.put(PLVLinkMicManager.VIEWER_ID, new TableInfo.Column(PLVLinkMicManager.VIEWER_ID, "TEXT", false, 0));
                map.put("viewerName", new TableInfo.Column("viewerName", "TEXT", false, 0));
                map.put("viewerAvatar", new TableInfo.Column("viewerAvatar", "TEXT", false, 0));
                map.put("playbackListType", new TableInfo.Column("playbackListType", "TEXT", false, 0));
                TableInfo tableInfo = new TableInfo("playback_cache_video_table", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(_db, "playback_cache_video_table");
                if (tableInfo.equals(tableInfo2)) {
                    return;
                }
                throw new IllegalStateException("Migration didn't properly handle playback_cache_video_table(com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
            }
        }, "566328438a36dedc27cf80d74f1037dd", "d6040adc535b8fb89b17c0efe840ecd9")).build());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.PLVPlaybackCacheDatabase
    public IPLVPlaybackCacheDAO getPlaybackCacheDAO() {
        IPLVPlaybackCacheDAO iPLVPlaybackCacheDAO;
        if (this._iPLVPlaybackCacheDAO != null) {
            return this._iPLVPlaybackCacheDAO;
        }
        synchronized (this) {
            if (this._iPLVPlaybackCacheDAO == null) {
                this._iPLVPlaybackCacheDAO = new IPLVPlaybackCacheDAO_Impl(this);
            }
            iPLVPlaybackCacheDAO = this._iPLVPlaybackCacheDAO;
        }
        return iPLVPlaybackCacheDAO;
    }
}
