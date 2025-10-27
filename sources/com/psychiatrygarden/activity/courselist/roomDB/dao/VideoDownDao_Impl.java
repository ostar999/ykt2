package com.psychiatrygarden.activity.courselist.roomDB.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public final class VideoDownDao_Impl implements VideoDownDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfVideoDownBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteData;
    private final SharedSQLiteStatement __preparedStmtOfDeleteEmptyVideo;
    private final SharedSQLiteStatement __preparedStmtOfDeleteVideoParent;
    private final SharedSQLiteStatement __preparedStmtOfUpdataModel;
    private final SharedSQLiteStatement __preparedStmtOfUpdateCourseId;
    private final SharedSQLiteStatement __preparedStmtOfUpdateModel;
    private final SharedSQLiteStatement __preparedStmtOfUpdateSizeAndProgress;
    private final SharedSQLiteStatement __preparedStmtOfUpdateVideoStatus;

    public VideoDownDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfVideoDownBean = new EntityInsertionAdapter<VideoDownBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `video_down`(`cId`,`chapter_id`,`parent_id`,`vid`,`obj_id`,`mQuality`,`mProgress`,`mSavePath`,`mTitle`,`mStatus`,`mSize`,`mFormat`,`isEncripted`,`thumb`,`sort`,`isExpanded`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, VideoDownBean videoDownBean) {
                String str = videoDownBean.cId;
                if (str == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, str);
                }
                String str2 = videoDownBean.chapter_id;
                if (str2 == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, str2);
                }
                String str3 = videoDownBean.parent_id;
                if (str3 == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, str3);
                }
                String str4 = videoDownBean.vid;
                if (str4 == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, str4);
                }
                supportSQLiteStatement.bindLong(5, videoDownBean.obj_id);
                String str5 = videoDownBean.mQuality;
                if (str5 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, str5);
                }
                supportSQLiteStatement.bindLong(7, videoDownBean.mProgress);
                String str6 = videoDownBean.mSavePath;
                if (str6 == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, str6);
                }
                String str7 = videoDownBean.mTitle;
                if (str7 == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, str7);
                }
                supportSQLiteStatement.bindLong(10, videoDownBean.mStatus);
                supportSQLiteStatement.bindLong(11, videoDownBean.mSize);
                String str8 = videoDownBean.mFormat;
                if (str8 == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, str8);
                }
                supportSQLiteStatement.bindLong(13, videoDownBean.isEncripted);
                String str9 = videoDownBean.thumb;
                if (str9 == null) {
                    supportSQLiteStatement.bindNull(14);
                } else {
                    supportSQLiteStatement.bindString(14, str9);
                }
                supportSQLiteStatement.bindLong(15, videoDownBean.sort);
                supportSQLiteStatement.bindLong(16, videoDownBean.getIsExpanded() ? 1L : 0L);
            }
        };
        this.__preparedStmtOfUpdataModel = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update `video_down` set mQuality=?,mProgress=?,mSavePath=?,mStatus=?,mSize=?,mFormat=?,isEncripted=? where vid=?";
            }
        };
        this.__preparedStmtOfUpdateCourseId = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update video_down set cId =? where vid=?";
            }
        };
        this.__preparedStmtOfDeleteEmptyVideo = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from video_down where chapter_id= ? and vid is null or vid =''";
            }
        };
        this.__preparedStmtOfDeleteData = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete  from video_down where vid=?";
            }
        };
        this.__preparedStmtOfUpdateModel = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update `video_down` set mProgress=?,mSavePath=?,mStatus=? where vid=?";
            }
        };
        this.__preparedStmtOfUpdateVideoStatus = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update `video_down` set mStatus=? where vid = ?";
            }
        };
        this.__preparedStmtOfDeleteVideoParent = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.8
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from video_down where vid is null or vid='' and chapter_id=?";
            }
        };
        this.__preparedStmtOfUpdateSizeAndProgress = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.9
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "update `video_down` set mSize=?,mProgress = ? ,mStatus = ? where vid = ?";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void deleteAllData(String[] vids) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("delete from video_down where vid in (");
        StringUtil.appendPlaceholders(sbNewStringBuilder, vids.length);
        sbNewStringBuilder.append(")");
        SupportSQLiteStatement supportSQLiteStatementCompileStatement = this.__db.compileStatement(sbNewStringBuilder.toString());
        int i2 = 1;
        for (String str : vids) {
            if (str == null) {
                supportSQLiteStatementCompileStatement.bindNull(i2);
            } else {
                supportSQLiteStatementCompileStatement.bindString(i2, str);
            }
            i2++;
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementCompileStatement.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void deleteData(String vid) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteData.acquire();
        this.__db.beginTransaction();
        try {
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteData.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void deleteEmptyVideo(String chapterId) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteEmptyVideo.acquire();
        this.__db.beginTransaction();
        try {
            if (chapterId == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, chapterId);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteEmptyVideo.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void deleteVideoParent(String chapterId) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteVideoParent.acquire();
        this.__db.beginTransaction();
        try {
            if (chapterId == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, chapterId);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteVideoParent.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public VideoDownBean getChildVideoParent(String chapterId) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        VideoDownBean videoDownBean;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from video_down where vid is null or vid ='' and chapter_id = ?", 1);
        if (chapterId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, chapterId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
                int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
                if (cursorQuery.moveToFirst()) {
                    videoDownBean = new VideoDownBean();
                    videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                    videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                    videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                    videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                    videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                    videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                    videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                    videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                    videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                    videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                    videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                    videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                    videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                    videoDownBean.thumb = cursorQuery.getString(columnIndexOrThrow14);
                    videoDownBean.sort = cursorQuery.getInt(columnIndexOrThrow15);
                    videoDownBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow16) != 0);
                } else {
                    videoDownBean = null;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return videoDownBean;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<String> getCids() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select distinct cId from `video_down` where cId not like 'course%'", 0);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(cursorQuery.getString(0));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public LiveData<List<VideoDownBean>> getDownLoadInfo(String cId) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where cId=?", 1);
        if (cId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, cId);
        }
        return new ComputableLiveData<List<VideoDownBean>>(this.__db.getQueryExecutor()) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.10
            private InvalidationTracker.Observer _observer;

            public void finalize() {
                roomSQLiteQueryAcquire.release();
            }

            @Override // androidx.lifecycle.ComputableLiveData
            public List<VideoDownBean> compute() {
                boolean z2;
                if (this._observer == null) {
                    this._observer = new InvalidationTracker.Observer("video_down", new String[0]) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl.10.1
                        @Override // androidx.room.InvalidationTracker.Observer
                        public void onInvalidated(@NonNull Set<String> tables) {
                            invalidate();
                        }
                    };
                    VideoDownDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(this._observer);
                }
                Cursor cursorQuery = VideoDownDao_Impl.this.__db.query(roomSQLiteQueryAcquire);
                try {
                    int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
                    int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
                    int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
                    int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
                    int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
                    int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
                    int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
                    int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
                    int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
                    int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
                    int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
                    int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
                    int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
                    int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
                    int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
                    int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
                    int i2 = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        VideoDownBean videoDownBean = new VideoDownBean();
                        ArrayList arrayList2 = arrayList;
                        videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                        videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                        videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                        videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                        videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                        videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                        videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                        videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                        videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                        videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                        int i3 = columnIndexOrThrow;
                        videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                        videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                        videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                        int i4 = i2;
                        videoDownBean.thumb = cursorQuery.getString(i4);
                        i2 = i4;
                        int i5 = columnIndexOrThrow15;
                        videoDownBean.sort = cursorQuery.getInt(i5);
                        int i6 = columnIndexOrThrow16;
                        if (cursorQuery.getInt(i6) != 0) {
                            columnIndexOrThrow16 = i6;
                            z2 = true;
                        } else {
                            columnIndexOrThrow16 = i6;
                            z2 = false;
                        }
                        videoDownBean.setExpanded(z2);
                        arrayList2.add(videoDownBean);
                        columnIndexOrThrow15 = i5;
                        arrayList = arrayList2;
                        columnIndexOrThrow = i3;
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                }
            }
        }.getLiveData();
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getNotCompleteVideoList(String courseId) throws Throwable {
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
        int columnIndexOrThrow14;
        int i2;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where mStatus !=5 and cId =?", 1);
        if (courseId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, courseId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i3 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i4 = columnIndexOrThrow;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i3;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                i3 = i5;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i7 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i7) != 0) {
                    i2 = i7;
                    z2 = true;
                } else {
                    i2 = i7;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                columnIndexOrThrow16 = i2;
                columnIndexOrThrow15 = i6;
                arrayList = arrayList2;
                columnIndexOrThrow = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public VideoDownBean getVideoDownBean(String vid, String cId, String chapter_id, String parent_id) throws Throwable {
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
        int columnIndexOrThrow14;
        VideoDownBean videoDownBean;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where vid = ? and cId=? and chapter_id=? and parent_id=?", 4);
        if (vid == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, vid);
        }
        if (cId == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, cId);
        }
        if (chapter_id == null) {
            roomSQLiteQueryAcquire.bindNull(3);
        } else {
            roomSQLiteQueryAcquire.bindString(3, chapter_id);
        }
        if (parent_id == null) {
            roomSQLiteQueryAcquire.bindNull(4);
        } else {
            roomSQLiteQueryAcquire.bindString(4, parent_id);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            if (cursorQuery.moveToFirst()) {
                videoDownBean = new VideoDownBean();
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                videoDownBean.thumb = cursorQuery.getString(columnIndexOrThrow14);
                videoDownBean.sort = cursorQuery.getInt(columnIndexOrThrow15);
                videoDownBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow16) != 0);
            } else {
                videoDownBean = null;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return videoDownBean;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownListByChapterId(String chapterId, String courseId) throws Throwable {
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
        int columnIndexOrThrow14;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from video_down where chapter_id =? and cId = ?", 2);
        if (chapterId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, chapterId);
        }
        if (courseId == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, courseId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i2 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i3 = columnIndexOrThrow2;
                int i4 = columnIndexOrThrow3;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i2;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                int i7 = columnIndexOrThrow;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i8 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i8) != 0) {
                    columnIndexOrThrow16 = i8;
                    z2 = true;
                } else {
                    columnIndexOrThrow16 = i8;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                i2 = i5;
                columnIndexOrThrow2 = i3;
                arrayList = arrayList2;
                columnIndexOrThrow = i7;
                columnIndexOrThrow15 = i6;
                columnIndexOrThrow3 = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownLoadAllList() throws Throwable {
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
        int columnIndexOrThrow14;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down`", 0);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i2 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i3 = columnIndexOrThrow2;
                int i4 = columnIndexOrThrow3;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i2;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                int i7 = columnIndexOrThrow;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i8 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i8) != 0) {
                    columnIndexOrThrow16 = i8;
                    z2 = true;
                } else {
                    columnIndexOrThrow16 = i8;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                i2 = i5;
                columnIndexOrThrow2 = i3;
                arrayList = arrayList2;
                columnIndexOrThrow = i7;
                columnIndexOrThrow15 = i6;
                columnIndexOrThrow3 = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownLoadCourseInfo(String cId, String parent_id) throws Throwable {
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
        int columnIndexOrThrow14;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where cId=? and parent_id=?", 2);
        if (cId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, cId);
        }
        if (parent_id == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, parent_id);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i2 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i3 = columnIndexOrThrow2;
                int i4 = columnIndexOrThrow3;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i2;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                int i7 = columnIndexOrThrow;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i8 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i8) != 0) {
                    columnIndexOrThrow16 = i8;
                    z2 = true;
                } else {
                    columnIndexOrThrow16 = i8;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                i2 = i5;
                columnIndexOrThrow2 = i3;
                arrayList = arrayList2;
                columnIndexOrThrow = i7;
                columnIndexOrThrow15 = i6;
                columnIndexOrThrow3 = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownLoadInfo(String cId, String chapter_id, String parent_id) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where cId=? and chapter_id=? and parent_id=?", 3);
        if (cId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, cId);
        }
        if (chapter_id == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, chapter_id);
        }
        if (parent_id == null) {
            roomSQLiteQueryAcquire.bindNull(3);
        } else {
            roomSQLiteQueryAcquire.bindString(3, parent_id);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
                int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
                int i2 = columnIndexOrThrow14;
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    VideoDownBean videoDownBean = new VideoDownBean();
                    ArrayList arrayList2 = arrayList;
                    videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                    videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                    videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                    videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                    videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                    videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                    videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                    videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                    videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                    videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                    int i3 = columnIndexOrThrow2;
                    videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                    videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                    videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                    int i4 = i2;
                    videoDownBean.thumb = cursorQuery.getString(i4);
                    int i5 = columnIndexOrThrow15;
                    int i6 = columnIndexOrThrow;
                    videoDownBean.sort = cursorQuery.getInt(i5);
                    int i7 = columnIndexOrThrow16;
                    if (cursorQuery.getInt(i7) != 0) {
                        columnIndexOrThrow16 = i7;
                        z2 = true;
                    } else {
                        columnIndexOrThrow16 = i7;
                        z2 = false;
                    }
                    videoDownBean.setExpanded(z2);
                    arrayList2.add(videoDownBean);
                    i2 = i4;
                    arrayList = arrayList2;
                    columnIndexOrThrow = i6;
                    columnIndexOrThrow15 = i5;
                    columnIndexOrThrow2 = i3;
                }
                ArrayList arrayList3 = arrayList;
                cursorQuery.close();
                roomSQLiteQuery.release();
                return arrayList3;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownLoadInfoByStatus(int status, String courseId) throws Throwable {
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
        int columnIndexOrThrow14;
        int i2;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where mStatus=? and cId =?", 2);
        roomSQLiteQueryAcquire.bindLong(1, status);
        if (courseId == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, courseId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i3 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i4 = columnIndexOrThrow;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i3;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                i3 = i5;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i7 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i7) != 0) {
                    i2 = i7;
                    z2 = true;
                } else {
                    i2 = i7;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                columnIndexOrThrow16 = i2;
                columnIndexOrThrow15 = i6;
                arrayList = arrayList2;
                columnIndexOrThrow = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void insertChildVideos(List<VideoDownBean> list) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfVideoDownBean.insert((Iterable) list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void insertTopicList(VideoDownBean... videoDownBeans) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfVideoDownBean.insert((Object[]) videoDownBeans);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updataModel(String mQuality, String mProgress, String mSavePath, int mStatus, int mSize, String mFormat, int isEncripted, String vid) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfUpdataModel.acquire();
        this.__db.beginTransaction();
        try {
            if (mQuality == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, mQuality);
            }
            if (mProgress == null) {
                supportSQLiteStatementAcquire.bindNull(2);
            } else {
                supportSQLiteStatementAcquire.bindString(2, mProgress);
            }
            if (mSavePath == null) {
                supportSQLiteStatementAcquire.bindNull(3);
            } else {
                supportSQLiteStatementAcquire.bindString(3, mSavePath);
            }
            supportSQLiteStatementAcquire.bindLong(4, mStatus);
            supportSQLiteStatementAcquire.bindLong(5, mSize);
            if (mFormat == null) {
                supportSQLiteStatementAcquire.bindNull(6);
            } else {
                supportSQLiteStatementAcquire.bindString(6, mFormat);
            }
            supportSQLiteStatementAcquire.bindLong(7, isEncripted);
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(8);
            } else {
                supportSQLiteStatementAcquire.bindString(8, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdataModel.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updataModelStatus(int mStatus, String[] courseId) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("update `video_down` set mStatus=");
        sbNewStringBuilder.append("?");
        sbNewStringBuilder.append(" where vid in (");
        StringUtil.appendPlaceholders(sbNewStringBuilder, courseId.length);
        sbNewStringBuilder.append(")");
        SupportSQLiteStatement supportSQLiteStatementCompileStatement = this.__db.compileStatement(sbNewStringBuilder.toString());
        supportSQLiteStatementCompileStatement.bindLong(1, mStatus);
        int i2 = 2;
        for (String str : courseId) {
            if (str == null) {
                supportSQLiteStatementCompileStatement.bindNull(i2);
            } else {
                supportSQLiteStatementCompileStatement.bindString(i2, str);
            }
            i2++;
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementCompileStatement.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updateCourseId(String vid, String courseId) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfUpdateCourseId.acquire();
        this.__db.beginTransaction();
        try {
            if (courseId == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, courseId);
            }
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(2);
            } else {
                supportSQLiteStatementAcquire.bindString(2, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateCourseId.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updateModel(String mProgress, String mSavePath, int mStatus, String vid) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfUpdateModel.acquire();
        this.__db.beginTransaction();
        try {
            if (mProgress == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, mProgress);
            }
            if (mSavePath == null) {
                supportSQLiteStatementAcquire.bindNull(2);
            } else {
                supportSQLiteStatementAcquire.bindString(2, mSavePath);
            }
            supportSQLiteStatementAcquire.bindLong(3, mStatus);
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(4);
            } else {
                supportSQLiteStatementAcquire.bindString(4, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateModel.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updateSizeAndProgress(String vid, long size, int progress, int status) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfUpdateSizeAndProgress.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.bindLong(1, size);
            supportSQLiteStatementAcquire.bindLong(2, progress);
            supportSQLiteStatementAcquire.bindLong(3, status);
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(4);
            } else {
                supportSQLiteStatementAcquire.bindString(4, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateSizeAndProgress.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void updateVideoStatus(int mStatus, String vid) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfUpdateVideoStatus.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.bindLong(1, mStatus);
            if (vid == null) {
                supportSQLiteStatementAcquire.bindNull(2);
            } else {
                supportSQLiteStatementAcquire.bindString(2, vid);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateVideoStatus.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void insertChildVideos(VideoDownBean... videoDownBeans) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfVideoDownBean.insert((Object[]) videoDownBeans);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public void deleteData(String[] vids, String cid) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("delete from video_down where vid in (");
        int length = vids.length;
        StringUtil.appendPlaceholders(sbNewStringBuilder, length);
        sbNewStringBuilder.append(") and cId=");
        sbNewStringBuilder.append("?");
        SupportSQLiteStatement supportSQLiteStatementCompileStatement = this.__db.compileStatement(sbNewStringBuilder.toString());
        int i2 = 1;
        for (String str : vids) {
            if (str == null) {
                supportSQLiteStatementCompileStatement.bindNull(i2);
            } else {
                supportSQLiteStatementCompileStatement.bindString(i2, str);
            }
            i2++;
        }
        int i3 = length + 1;
        if (cid == null) {
            supportSQLiteStatementCompileStatement.bindNull(i3);
        } else {
            supportSQLiteStatementCompileStatement.bindString(i3, cid);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementCompileStatement.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public VideoDownBean getVideoDownBean(String vid) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        VideoDownBean videoDownBean;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where vid = ?", 1);
        if (vid == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, vid);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
            try {
                int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
                int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
                if (cursorQuery.moveToFirst()) {
                    videoDownBean = new VideoDownBean();
                    videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                    videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                    videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                    videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                    videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                    videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                    videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                    videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                    videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                    videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                    videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                    videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                    videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                    videoDownBean.thumb = cursorQuery.getString(columnIndexOrThrow14);
                    videoDownBean.sort = cursorQuery.getInt(columnIndexOrThrow15);
                    videoDownBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow16) != 0);
                } else {
                    videoDownBean = null;
                }
                cursorQuery.close();
                roomSQLiteQuery.release();
                return videoDownBean;
            } catch (Throwable th) {
                th = th;
                cursorQuery.close();
                roomSQLiteQuery.release();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao
    public List<VideoDownBean> getVideoDownLoadInfo(String cId) throws Throwable {
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
        int columnIndexOrThrow14;
        int i2;
        boolean z2;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `video_down` where cId=?", 1);
        if (cId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, cId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("cId");
            columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
            columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("parent_id");
            columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
            columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("obj_id");
            columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("mQuality");
            columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("mProgress");
            columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("mSavePath");
            columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("mTitle");
            columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("mStatus");
            columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("mSize");
            columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("mFormat");
            columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("isEncripted");
            columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("thumb");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            int i3 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                VideoDownBean videoDownBean = new VideoDownBean();
                ArrayList arrayList2 = arrayList;
                videoDownBean.cId = cursorQuery.getString(columnIndexOrThrow);
                videoDownBean.chapter_id = cursorQuery.getString(columnIndexOrThrow2);
                videoDownBean.parent_id = cursorQuery.getString(columnIndexOrThrow3);
                videoDownBean.vid = cursorQuery.getString(columnIndexOrThrow4);
                videoDownBean.obj_id = cursorQuery.getInt(columnIndexOrThrow5);
                videoDownBean.mQuality = cursorQuery.getString(columnIndexOrThrow6);
                videoDownBean.mProgress = cursorQuery.getInt(columnIndexOrThrow7);
                videoDownBean.mSavePath = cursorQuery.getString(columnIndexOrThrow8);
                videoDownBean.mTitle = cursorQuery.getString(columnIndexOrThrow9);
                videoDownBean.mStatus = cursorQuery.getInt(columnIndexOrThrow10);
                int i4 = columnIndexOrThrow;
                videoDownBean.mSize = cursorQuery.getLong(columnIndexOrThrow11);
                videoDownBean.mFormat = cursorQuery.getString(columnIndexOrThrow12);
                videoDownBean.isEncripted = cursorQuery.getInt(columnIndexOrThrow13);
                int i5 = i3;
                videoDownBean.thumb = cursorQuery.getString(i5);
                int i6 = columnIndexOrThrow15;
                i3 = i5;
                videoDownBean.sort = cursorQuery.getInt(i6);
                int i7 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i7) != 0) {
                    i2 = i7;
                    z2 = true;
                } else {
                    i2 = i7;
                    z2 = false;
                }
                videoDownBean.setExpanded(z2);
                arrayList2.add(videoDownBean);
                columnIndexOrThrow16 = i2;
                columnIndexOrThrow15 = i6;
                arrayList = arrayList2;
                columnIndexOrThrow = i4;
            }
            ArrayList arrayList3 = arrayList;
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList3;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }
}
