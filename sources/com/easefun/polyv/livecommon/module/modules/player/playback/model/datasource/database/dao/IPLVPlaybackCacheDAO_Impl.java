package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter.PLVLiveChannelTypeConverter;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter.PLVPlaybackListTypeConverter;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheViewerInfoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.tencent.connect.share.QzonePublish;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class IPLVPlaybackCacheDAO_Impl implements IPLVPlaybackCacheDAO {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPLVPlaybackCacheVideoVO;
    private final EntityInsertionAdapter __insertionAdapterOfPLVPlaybackCacheVideoVO;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfPLVPlaybackCacheVideoVO;
    private final PLVPlaybackCacheDownloadStatusEnum.Converter __converter = new PLVPlaybackCacheDownloadStatusEnum.Converter();
    private final PLVLiveChannelTypeConverter __pLVLiveChannelTypeConverter = new PLVLiveChannelTypeConverter();
    private final PLVPlaybackListTypeConverter __pLVPlaybackListTypeConverter = new PLVPlaybackListTypeConverter();

    public IPLVPlaybackCacheDAO_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPLVPlaybackCacheVideoVO = new EntityInsertionAdapter<PLVPlaybackCacheVideoVO>(__db) { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `playback_cache_video_table`(`videoPoolId`,`videoId`,`title`,`firstImageUrl`,`videoDuration`,`liveType`,`channelSessionId`,`originSessionId`,`enableDownload`,`progress`,`downloadedBytes`,`totalBytes`,`downloadStatusEnum`,`videoPath`,`pptPath`,`jsPath`,`channelId`,`channelType`,`vid`,`viewerId`,`viewerName`,`viewerAvatar`,`playbackListType`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
                if (pLVPlaybackCacheVideoVO.getVideoPoolId() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, pLVPlaybackCacheVideoVO.getVideoPoolId());
                }
                if (pLVPlaybackCacheVideoVO.getVideoId() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, pLVPlaybackCacheVideoVO.getVideoId());
                }
                if (pLVPlaybackCacheVideoVO.getTitle() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, pLVPlaybackCacheVideoVO.getTitle());
                }
                if (pLVPlaybackCacheVideoVO.getFirstImageUrl() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, pLVPlaybackCacheVideoVO.getFirstImageUrl());
                }
                if (pLVPlaybackCacheVideoVO.getVideoDuration() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, pLVPlaybackCacheVideoVO.getVideoDuration());
                }
                if (pLVPlaybackCacheVideoVO.getLiveType() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, pLVPlaybackCacheVideoVO.getLiveType());
                }
                if (pLVPlaybackCacheVideoVO.getChannelSessionId() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, pLVPlaybackCacheVideoVO.getChannelSessionId());
                }
                if (pLVPlaybackCacheVideoVO.getOriginSessionId() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, pLVPlaybackCacheVideoVO.getOriginSessionId());
                }
                if ((pLVPlaybackCacheVideoVO.isEnableDownload() == null ? null : Integer.valueOf(pLVPlaybackCacheVideoVO.isEnableDownload().booleanValue() ? 1 : 0)) == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindLong(9, r0.intValue());
                }
                if (pLVPlaybackCacheVideoVO.getProgress() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindLong(10, pLVPlaybackCacheVideoVO.getProgress().intValue());
                }
                if (pLVPlaybackCacheVideoVO.getDownloadedBytes() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindLong(11, pLVPlaybackCacheVideoVO.getDownloadedBytes().longValue());
                }
                if (pLVPlaybackCacheVideoVO.getTotalBytes() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindLong(12, pLVPlaybackCacheVideoVO.getTotalBytes().longValue());
                }
                String strSerialize = IPLVPlaybackCacheDAO_Impl.this.__converter.serialize(pLVPlaybackCacheVideoVO.getDownloadStatusEnum());
                if (strSerialize == null) {
                    supportSQLiteStatement.bindNull(13);
                } else {
                    supportSQLiteStatement.bindString(13, strSerialize);
                }
                if (pLVPlaybackCacheVideoVO.getVideoPath() == null) {
                    supportSQLiteStatement.bindNull(14);
                } else {
                    supportSQLiteStatement.bindString(14, pLVPlaybackCacheVideoVO.getVideoPath());
                }
                if (pLVPlaybackCacheVideoVO.getPptPath() == null) {
                    supportSQLiteStatement.bindNull(15);
                } else {
                    supportSQLiteStatement.bindString(15, pLVPlaybackCacheVideoVO.getPptPath());
                }
                if (pLVPlaybackCacheVideoVO.getJsPath() == null) {
                    supportSQLiteStatement.bindNull(16);
                } else {
                    supportSQLiteStatement.bindString(16, pLVPlaybackCacheVideoVO.getJsPath());
                }
                PLVPlaybackCacheViewerInfoVO viewerInfoVO = pLVPlaybackCacheVideoVO.getViewerInfoVO();
                if (viewerInfoVO == null) {
                    supportSQLiteStatement.bindNull(17);
                    supportSQLiteStatement.bindNull(18);
                    supportSQLiteStatement.bindNull(19);
                    supportSQLiteStatement.bindNull(20);
                    supportSQLiteStatement.bindNull(21);
                    supportSQLiteStatement.bindNull(22);
                    supportSQLiteStatement.bindNull(23);
                    return;
                }
                if (viewerInfoVO.getChannelId() == null) {
                    supportSQLiteStatement.bindNull(17);
                } else {
                    supportSQLiteStatement.bindString(17, viewerInfoVO.getChannelId());
                }
                String strSerialize2 = IPLVPlaybackCacheDAO_Impl.this.__pLVLiveChannelTypeConverter.serialize(viewerInfoVO.getChannelType());
                if (strSerialize2 == null) {
                    supportSQLiteStatement.bindNull(18);
                } else {
                    supportSQLiteStatement.bindString(18, strSerialize2);
                }
                if (viewerInfoVO.getVid() == null) {
                    supportSQLiteStatement.bindNull(19);
                } else {
                    supportSQLiteStatement.bindString(19, viewerInfoVO.getVid());
                }
                if (viewerInfoVO.getViewerId() == null) {
                    supportSQLiteStatement.bindNull(20);
                } else {
                    supportSQLiteStatement.bindString(20, viewerInfoVO.getViewerId());
                }
                if (viewerInfoVO.getViewerName() == null) {
                    supportSQLiteStatement.bindNull(21);
                } else {
                    supportSQLiteStatement.bindString(21, viewerInfoVO.getViewerName());
                }
                if (viewerInfoVO.getViewerAvatar() == null) {
                    supportSQLiteStatement.bindNull(22);
                } else {
                    supportSQLiteStatement.bindString(22, viewerInfoVO.getViewerAvatar());
                }
                String strSerialize3 = IPLVPlaybackCacheDAO_Impl.this.__pLVPlaybackListTypeConverter.serialize(viewerInfoVO.getPlaybackListType());
                if (strSerialize3 == null) {
                    supportSQLiteStatement.bindNull(23);
                } else {
                    supportSQLiteStatement.bindString(23, strSerialize3);
                }
            }
        };
        this.__deletionAdapterOfPLVPlaybackCacheVideoVO = new EntityDeletionOrUpdateAdapter<PLVPlaybackCacheVideoVO>(__db) { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM `playback_cache_video_table` WHERE `videoPoolId` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, PLVPlaybackCacheVideoVO value) {
                if (value.getVideoPoolId() == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, value.getVideoPoolId());
                }
            }
        };
        this.__updateAdapterOfPLVPlaybackCacheVideoVO = new EntityDeletionOrUpdateAdapter<PLVPlaybackCacheVideoVO>(__db) { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO_Impl.3
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `playback_cache_video_table` SET `videoPoolId` = ?,`videoId` = ?,`title` = ?,`firstImageUrl` = ?,`videoDuration` = ?,`liveType` = ?,`channelSessionId` = ?,`originSessionId` = ?,`enableDownload` = ?,`progress` = ?,`downloadedBytes` = ?,`totalBytes` = ?,`downloadStatusEnum` = ?,`videoPath` = ?,`pptPath` = ?,`jsPath` = ?,`channelId` = ?,`channelType` = ?,`vid` = ?,`viewerId` = ?,`viewerName` = ?,`viewerAvatar` = ?,`playbackListType` = ? WHERE `videoPoolId` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
                if (pLVPlaybackCacheVideoVO.getVideoPoolId() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, pLVPlaybackCacheVideoVO.getVideoPoolId());
                }
                if (pLVPlaybackCacheVideoVO.getVideoId() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, pLVPlaybackCacheVideoVO.getVideoId());
                }
                if (pLVPlaybackCacheVideoVO.getTitle() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, pLVPlaybackCacheVideoVO.getTitle());
                }
                if (pLVPlaybackCacheVideoVO.getFirstImageUrl() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, pLVPlaybackCacheVideoVO.getFirstImageUrl());
                }
                if (pLVPlaybackCacheVideoVO.getVideoDuration() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, pLVPlaybackCacheVideoVO.getVideoDuration());
                }
                if (pLVPlaybackCacheVideoVO.getLiveType() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, pLVPlaybackCacheVideoVO.getLiveType());
                }
                if (pLVPlaybackCacheVideoVO.getChannelSessionId() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, pLVPlaybackCacheVideoVO.getChannelSessionId());
                }
                if (pLVPlaybackCacheVideoVO.getOriginSessionId() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, pLVPlaybackCacheVideoVO.getOriginSessionId());
                }
                if ((pLVPlaybackCacheVideoVO.isEnableDownload() == null ? null : Integer.valueOf(pLVPlaybackCacheVideoVO.isEnableDownload().booleanValue() ? 1 : 0)) == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindLong(9, r0.intValue());
                }
                if (pLVPlaybackCacheVideoVO.getProgress() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindLong(10, pLVPlaybackCacheVideoVO.getProgress().intValue());
                }
                if (pLVPlaybackCacheVideoVO.getDownloadedBytes() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindLong(11, pLVPlaybackCacheVideoVO.getDownloadedBytes().longValue());
                }
                if (pLVPlaybackCacheVideoVO.getTotalBytes() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindLong(12, pLVPlaybackCacheVideoVO.getTotalBytes().longValue());
                }
                String strSerialize = IPLVPlaybackCacheDAO_Impl.this.__converter.serialize(pLVPlaybackCacheVideoVO.getDownloadStatusEnum());
                if (strSerialize == null) {
                    supportSQLiteStatement.bindNull(13);
                } else {
                    supportSQLiteStatement.bindString(13, strSerialize);
                }
                if (pLVPlaybackCacheVideoVO.getVideoPath() == null) {
                    supportSQLiteStatement.bindNull(14);
                } else {
                    supportSQLiteStatement.bindString(14, pLVPlaybackCacheVideoVO.getVideoPath());
                }
                if (pLVPlaybackCacheVideoVO.getPptPath() == null) {
                    supportSQLiteStatement.bindNull(15);
                } else {
                    supportSQLiteStatement.bindString(15, pLVPlaybackCacheVideoVO.getPptPath());
                }
                if (pLVPlaybackCacheVideoVO.getJsPath() == null) {
                    supportSQLiteStatement.bindNull(16);
                } else {
                    supportSQLiteStatement.bindString(16, pLVPlaybackCacheVideoVO.getJsPath());
                }
                PLVPlaybackCacheViewerInfoVO viewerInfoVO = pLVPlaybackCacheVideoVO.getViewerInfoVO();
                if (viewerInfoVO != null) {
                    if (viewerInfoVO.getChannelId() == null) {
                        supportSQLiteStatement.bindNull(17);
                    } else {
                        supportSQLiteStatement.bindString(17, viewerInfoVO.getChannelId());
                    }
                    String strSerialize2 = IPLVPlaybackCacheDAO_Impl.this.__pLVLiveChannelTypeConverter.serialize(viewerInfoVO.getChannelType());
                    if (strSerialize2 == null) {
                        supportSQLiteStatement.bindNull(18);
                    } else {
                        supportSQLiteStatement.bindString(18, strSerialize2);
                    }
                    if (viewerInfoVO.getVid() == null) {
                        supportSQLiteStatement.bindNull(19);
                    } else {
                        supportSQLiteStatement.bindString(19, viewerInfoVO.getVid());
                    }
                    if (viewerInfoVO.getViewerId() == null) {
                        supportSQLiteStatement.bindNull(20);
                    } else {
                        supportSQLiteStatement.bindString(20, viewerInfoVO.getViewerId());
                    }
                    if (viewerInfoVO.getViewerName() == null) {
                        supportSQLiteStatement.bindNull(21);
                    } else {
                        supportSQLiteStatement.bindString(21, viewerInfoVO.getViewerName());
                    }
                    if (viewerInfoVO.getViewerAvatar() == null) {
                        supportSQLiteStatement.bindNull(22);
                    } else {
                        supportSQLiteStatement.bindString(22, viewerInfoVO.getViewerAvatar());
                    }
                    String strSerialize3 = IPLVPlaybackCacheDAO_Impl.this.__pLVPlaybackListTypeConverter.serialize(viewerInfoVO.getPlaybackListType());
                    if (strSerialize3 == null) {
                        supportSQLiteStatement.bindNull(23);
                    } else {
                        supportSQLiteStatement.bindString(23, strSerialize3);
                    }
                } else {
                    supportSQLiteStatement.bindNull(17);
                    supportSQLiteStatement.bindNull(18);
                    supportSQLiteStatement.bindNull(19);
                    supportSQLiteStatement.bindNull(20);
                    supportSQLiteStatement.bindNull(21);
                    supportSQLiteStatement.bindNull(22);
                    supportSQLiteStatement.bindNull(23);
                }
                if (pLVPlaybackCacheVideoVO.getVideoPoolId() == null) {
                    supportSQLiteStatement.bindNull(24);
                } else {
                    supportSQLiteStatement.bindString(24, pLVPlaybackCacheVideoVO.getVideoPoolId());
                }
            }
        };
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
    public void deletePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfPLVPlaybackCacheVideoVO.handle(cacheVideoVO);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
    public PLVPlaybackCacheVideoVO getPlaybackCacheVideo(String str) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int i2;
        PLVPlaybackCacheViewerInfoVO pLVPlaybackCacheViewerInfoVO;
        Boolean boolValueOf;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM playback_cache_video_table WHERE videoPoolId = ? LIMIT 1", 1);
        if (str == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, str);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("videoPoolId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("videoId");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("title");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("firstImageUrl");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("videoDuration");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("liveType");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("channelSessionId");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("originSessionId");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("enableDownload");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("progress");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("downloadedBytes");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("totalBytes");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("downloadStatusEnum");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH);
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("pptPath");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("jsPath");
            int columnIndexOrThrow17 = cursorQuery.getColumnIndexOrThrow("channelId");
            int columnIndexOrThrow18 = cursorQuery.getColumnIndexOrThrow("channelType");
            int columnIndexOrThrow19 = cursorQuery.getColumnIndexOrThrow("vid");
            int columnIndexOrThrow20 = cursorQuery.getColumnIndexOrThrow(PLVLinkMicManager.VIEWER_ID);
            int columnIndexOrThrow21 = cursorQuery.getColumnIndexOrThrow("viewerName");
            int columnIndexOrThrow22 = cursorQuery.getColumnIndexOrThrow("viewerAvatar");
            int columnIndexOrThrow23 = cursorQuery.getColumnIndexOrThrow("playbackListType");
            PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = null;
            if (cursorQuery.moveToFirst()) {
                if (cursorQuery.isNull(columnIndexOrThrow17) && cursorQuery.isNull(columnIndexOrThrow18) && cursorQuery.isNull(columnIndexOrThrow19) && cursorQuery.isNull(columnIndexOrThrow20) && cursorQuery.isNull(columnIndexOrThrow21) && cursorQuery.isNull(columnIndexOrThrow22) && cursorQuery.isNull(columnIndexOrThrow23)) {
                    i2 = columnIndexOrThrow7;
                    pLVPlaybackCacheViewerInfoVO = null;
                } else {
                    i2 = columnIndexOrThrow7;
                    pLVPlaybackCacheViewerInfoVO = new PLVPlaybackCacheViewerInfoVO();
                    pLVPlaybackCacheViewerInfoVO.setChannelId(cursorQuery.getString(columnIndexOrThrow17));
                    pLVPlaybackCacheViewerInfoVO.setChannelType(this.__pLVLiveChannelTypeConverter.deserialize(cursorQuery.getString(columnIndexOrThrow18)));
                    pLVPlaybackCacheViewerInfoVO.setVid(cursorQuery.getString(columnIndexOrThrow19));
                    pLVPlaybackCacheViewerInfoVO.setViewerId(cursorQuery.getString(columnIndexOrThrow20));
                    pLVPlaybackCacheViewerInfoVO.setViewerName(cursorQuery.getString(columnIndexOrThrow21));
                    pLVPlaybackCacheViewerInfoVO.setViewerAvatar(cursorQuery.getString(columnIndexOrThrow22));
                    pLVPlaybackCacheViewerInfoVO.setPlaybackListType(this.__pLVPlaybackListTypeConverter.deserialize(cursorQuery.getString(columnIndexOrThrow23)));
                }
                PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO2 = new PLVPlaybackCacheVideoVO();
                pLVPlaybackCacheVideoVO2.setVideoPoolId(cursorQuery.getString(columnIndexOrThrow));
                pLVPlaybackCacheVideoVO2.setVideoId(cursorQuery.getString(columnIndexOrThrow2));
                pLVPlaybackCacheVideoVO2.setTitle(cursorQuery.getString(columnIndexOrThrow3));
                pLVPlaybackCacheVideoVO2.setFirstImageUrl(cursorQuery.getString(columnIndexOrThrow4));
                pLVPlaybackCacheVideoVO2.setVideoDuration(cursorQuery.getString(columnIndexOrThrow5));
                pLVPlaybackCacheVideoVO2.setLiveType(cursorQuery.getString(columnIndexOrThrow6));
                pLVPlaybackCacheVideoVO2.setChannelSessionId(cursorQuery.getString(i2));
                pLVPlaybackCacheVideoVO2.setOriginSessionId(cursorQuery.getString(columnIndexOrThrow8));
                Integer numValueOf = cursorQuery.isNull(columnIndexOrThrow9) ? null : Integer.valueOf(cursorQuery.getInt(columnIndexOrThrow9));
                if (numValueOf == null) {
                    boolValueOf = null;
                } else {
                    boolValueOf = Boolean.valueOf(numValueOf.intValue() != 0);
                }
                pLVPlaybackCacheVideoVO2.setEnableDownload(boolValueOf);
                pLVPlaybackCacheVideoVO2.setProgress(cursorQuery.isNull(columnIndexOrThrow10) ? null : Integer.valueOf(cursorQuery.getInt(columnIndexOrThrow10)));
                pLVPlaybackCacheVideoVO2.setDownloadedBytes(cursorQuery.isNull(columnIndexOrThrow11) ? null : Long.valueOf(cursorQuery.getLong(columnIndexOrThrow11)));
                pLVPlaybackCacheVideoVO2.setTotalBytes(cursorQuery.isNull(columnIndexOrThrow12) ? null : Long.valueOf(cursorQuery.getLong(columnIndexOrThrow12)));
                pLVPlaybackCacheVideoVO2.setDownloadStatusEnum(this.__converter.deserialize(cursorQuery.getString(columnIndexOrThrow13)));
                pLVPlaybackCacheVideoVO2.setVideoPath(cursorQuery.getString(columnIndexOrThrow14));
                pLVPlaybackCacheVideoVO2.setPptPath(cursorQuery.getString(columnIndexOrThrow15));
                pLVPlaybackCacheVideoVO2.setJsPath(cursorQuery.getString(columnIndexOrThrow16));
                pLVPlaybackCacheVideoVO2.setViewerInfoVO(pLVPlaybackCacheViewerInfoVO);
                pLVPlaybackCacheVideoVO = pLVPlaybackCacheVideoVO2;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return pLVPlaybackCacheVideoVO;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
    public void insertPlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfPLVPlaybackCacheVideoVO.insert((EntityInsertionAdapter) cacheVideoVO);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
    public Flowable<List<PLVPlaybackCacheVideoVO>> listPlaybackCacheVideos() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM playback_cache_video_table", 0);
        return RxRoom.createFlowable(this.__db, new String[]{"playback_cache_video_table"}, new Callable<List<PLVPlaybackCacheVideoVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO_Impl.4
            public void finalize() {
                roomSQLiteQueryAcquire.release();
            }

            @Override // java.util.concurrent.Callable
            public List<PLVPlaybackCacheVideoVO> call() throws Exception {
                PLVPlaybackCacheViewerInfoVO pLVPlaybackCacheViewerInfoVO;
                ArrayList arrayList;
                int i2;
                int i3;
                Boolean boolValueOf;
                int i4;
                Integer numValueOf;
                int i5;
                Long lValueOf;
                AnonymousClass4 anonymousClass4 = this;
                Cursor cursorQuery = IPLVPlaybackCacheDAO_Impl.this.__db.query(roomSQLiteQueryAcquire);
                try {
                    int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("videoPoolId");
                    int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("videoId");
                    int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("title");
                    int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("firstImageUrl");
                    int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("videoDuration");
                    int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("liveType");
                    int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("channelSessionId");
                    int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("originSessionId");
                    int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("enableDownload");
                    int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("progress");
                    int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("downloadedBytes");
                    int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("totalBytes");
                    int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("downloadStatusEnum");
                    int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH);
                    int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("pptPath");
                    int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("jsPath");
                    int columnIndexOrThrow17 = cursorQuery.getColumnIndexOrThrow("channelId");
                    int i6 = columnIndexOrThrow13;
                    int columnIndexOrThrow18 = cursorQuery.getColumnIndexOrThrow("channelType");
                    int i7 = columnIndexOrThrow12;
                    int columnIndexOrThrow19 = cursorQuery.getColumnIndexOrThrow("vid");
                    int i8 = columnIndexOrThrow11;
                    int columnIndexOrThrow20 = cursorQuery.getColumnIndexOrThrow(PLVLinkMicManager.VIEWER_ID);
                    int i9 = columnIndexOrThrow10;
                    int columnIndexOrThrow21 = cursorQuery.getColumnIndexOrThrow("viewerName");
                    int i10 = columnIndexOrThrow9;
                    int columnIndexOrThrow22 = cursorQuery.getColumnIndexOrThrow("viewerAvatar");
                    int i11 = columnIndexOrThrow8;
                    int columnIndexOrThrow23 = cursorQuery.getColumnIndexOrThrow("playbackListType");
                    int i12 = columnIndexOrThrow7;
                    int i13 = columnIndexOrThrow6;
                    ArrayList arrayList2 = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        if (cursorQuery.isNull(columnIndexOrThrow17) && cursorQuery.isNull(columnIndexOrThrow18) && cursorQuery.isNull(columnIndexOrThrow19) && cursorQuery.isNull(columnIndexOrThrow20) && cursorQuery.isNull(columnIndexOrThrow21) && cursorQuery.isNull(columnIndexOrThrow22) && cursorQuery.isNull(columnIndexOrThrow23)) {
                            arrayList = arrayList2;
                            i2 = columnIndexOrThrow18;
                            pLVPlaybackCacheViewerInfoVO = null;
                        } else {
                            pLVPlaybackCacheViewerInfoVO = new PLVPlaybackCacheViewerInfoVO();
                            arrayList = arrayList2;
                            pLVPlaybackCacheViewerInfoVO.setChannelId(cursorQuery.getString(columnIndexOrThrow17));
                            i2 = columnIndexOrThrow18;
                            pLVPlaybackCacheViewerInfoVO.setChannelType(IPLVPlaybackCacheDAO_Impl.this.__pLVLiveChannelTypeConverter.deserialize(cursorQuery.getString(columnIndexOrThrow18)));
                            pLVPlaybackCacheViewerInfoVO.setVid(cursorQuery.getString(columnIndexOrThrow19));
                            pLVPlaybackCacheViewerInfoVO.setViewerId(cursorQuery.getString(columnIndexOrThrow20));
                            pLVPlaybackCacheViewerInfoVO.setViewerName(cursorQuery.getString(columnIndexOrThrow21));
                            pLVPlaybackCacheViewerInfoVO.setViewerAvatar(cursorQuery.getString(columnIndexOrThrow22));
                            pLVPlaybackCacheViewerInfoVO.setPlaybackListType(IPLVPlaybackCacheDAO_Impl.this.__pLVPlaybackListTypeConverter.deserialize(cursorQuery.getString(columnIndexOrThrow23)));
                        }
                        PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = new PLVPlaybackCacheVideoVO();
                        pLVPlaybackCacheVideoVO.setVideoPoolId(cursorQuery.getString(columnIndexOrThrow));
                        pLVPlaybackCacheVideoVO.setVideoId(cursorQuery.getString(columnIndexOrThrow2));
                        pLVPlaybackCacheVideoVO.setTitle(cursorQuery.getString(columnIndexOrThrow3));
                        pLVPlaybackCacheVideoVO.setFirstImageUrl(cursorQuery.getString(columnIndexOrThrow4));
                        pLVPlaybackCacheVideoVO.setVideoDuration(cursorQuery.getString(columnIndexOrThrow5));
                        int i14 = i13;
                        int i15 = columnIndexOrThrow;
                        pLVPlaybackCacheVideoVO.setLiveType(cursorQuery.getString(i14));
                        int i16 = i12;
                        int i17 = columnIndexOrThrow2;
                        pLVPlaybackCacheVideoVO.setChannelSessionId(cursorQuery.getString(i16));
                        int i18 = i11;
                        pLVPlaybackCacheVideoVO.setOriginSessionId(cursorQuery.getString(i18));
                        int i19 = i10;
                        Integer numValueOf2 = cursorQuery.isNull(i19) ? null : Integer.valueOf(cursorQuery.getInt(i19));
                        if (numValueOf2 == null) {
                            i3 = i19;
                            boolValueOf = null;
                        } else {
                            i3 = i19;
                            boolValueOf = Boolean.valueOf(numValueOf2.intValue() != 0);
                        }
                        pLVPlaybackCacheVideoVO.setEnableDownload(boolValueOf);
                        int i20 = i9;
                        if (cursorQuery.isNull(i20)) {
                            i4 = i20;
                            numValueOf = null;
                        } else {
                            i4 = i20;
                            numValueOf = Integer.valueOf(cursorQuery.getInt(i20));
                        }
                        pLVPlaybackCacheVideoVO.setProgress(numValueOf);
                        int i21 = i8;
                        if (cursorQuery.isNull(i21)) {
                            i5 = i21;
                            lValueOf = null;
                        } else {
                            i5 = i21;
                            lValueOf = Long.valueOf(cursorQuery.getLong(i21));
                        }
                        pLVPlaybackCacheVideoVO.setDownloadedBytes(lValueOf);
                        int i22 = i7;
                        i7 = i22;
                        pLVPlaybackCacheVideoVO.setTotalBytes(cursorQuery.isNull(i22) ? null : Long.valueOf(cursorQuery.getLong(i22)));
                        int i23 = i6;
                        pLVPlaybackCacheVideoVO.setDownloadStatusEnum(IPLVPlaybackCacheDAO_Impl.this.__converter.deserialize(cursorQuery.getString(i23)));
                        int i24 = columnIndexOrThrow14;
                        pLVPlaybackCacheVideoVO.setVideoPath(cursorQuery.getString(i24));
                        columnIndexOrThrow14 = i24;
                        int i25 = columnIndexOrThrow15;
                        pLVPlaybackCacheVideoVO.setPptPath(cursorQuery.getString(i25));
                        int i26 = columnIndexOrThrow16;
                        pLVPlaybackCacheVideoVO.setJsPath(cursorQuery.getString(i26));
                        pLVPlaybackCacheVideoVO.setViewerInfoVO(pLVPlaybackCacheViewerInfoVO);
                        ArrayList arrayList3 = arrayList;
                        arrayList3.add(pLVPlaybackCacheVideoVO);
                        columnIndexOrThrow16 = i26;
                        arrayList2 = arrayList3;
                        columnIndexOrThrow15 = i25;
                        columnIndexOrThrow2 = i17;
                        columnIndexOrThrow = i15;
                        anonymousClass4 = this;
                        i13 = i14;
                        i12 = i16;
                        columnIndexOrThrow18 = i2;
                        i11 = i18;
                        i6 = i23;
                        i8 = i5;
                        i9 = i4;
                        i10 = i3;
                    }
                    return arrayList2;
                } finally {
                    cursorQuery.close();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.dao.IPLVPlaybackCacheDAO
    public void updatePlaybackCache(PLVPlaybackCacheVideoVO cacheVideoVO) {
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfPLVPlaybackCacheVideoVO.handle(cacheVideoVO);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}
