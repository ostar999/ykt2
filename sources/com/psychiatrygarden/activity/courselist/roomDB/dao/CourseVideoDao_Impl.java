package com.psychiatrygarden.activity.courselist.roomDB.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public final class CourseVideoDao_Impl implements CourseVideoDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDataBean;
    private final EntityInsertionAdapter __insertionAdapterOfDataBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteData;
    private final SharedSQLiteStatement __preparedStmtOfDeleteVideo;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfDataBean;

    public CourseVideoDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfDataBean = new EntityInsertionAdapter<CourseVideoListBean.DataBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `course_video`(`userAndAppId`,`id`,`thumb`,`vid`,`duration`,`lecturer`,`sort`,`description`,`chapter_id`,`vidteaching_id`,`app_id`,`watched`,`title`,`duration_text`,`comment_count`,`count`,`stutas`,`progress`,`type`,`collection`,`note`,`is_see`,`sign_time`,`free_watch_time`,`watch_permission`,`expire_str`,`activity_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, CourseVideoListBean.DataBean value) {
                String str = value.userAndAppId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                stmt.bindLong(2, value.getId());
                if (value.getThumb() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getThumb());
                }
                if (value.getVid() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getVid());
                }
                if (value.getDuration() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getDuration());
                }
                if (value.getLecturer() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getLecturer());
                }
                stmt.bindLong(7, value.getSort());
                if (value.getDescription() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getDescription());
                }
                if (value.getChapter_id() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getChapter_id());
                }
                if (value.getVidteaching_id() == null) {
                    stmt.bindNull(10);
                } else {
                    stmt.bindString(10, value.getVidteaching_id());
                }
                if (value.getApp_id() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getApp_id());
                }
                if (value.getWatched() == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, value.getWatched());
                }
                if (value.getTitle() == null) {
                    stmt.bindNull(13);
                } else {
                    stmt.bindString(13, value.getTitle());
                }
                if (value.getDuration_text() == null) {
                    stmt.bindNull(14);
                } else {
                    stmt.bindString(14, value.getDuration_text());
                }
                if (value.getComment_count() == null) {
                    stmt.bindNull(15);
                } else {
                    stmt.bindString(15, value.getComment_count());
                }
                if (value.getCount() == null) {
                    stmt.bindNull(16);
                } else {
                    stmt.bindString(16, value.getCount());
                }
                stmt.bindLong(17, value.getStutas());
                stmt.bindLong(18, value.getProgress());
                if (value.getType() == null) {
                    stmt.bindNull(19);
                } else {
                    stmt.bindString(19, value.getType());
                }
                String str2 = value.collection;
                if (str2 == null) {
                    stmt.bindNull(20);
                } else {
                    stmt.bindString(20, str2);
                }
                String str3 = value.note;
                if (str3 == null) {
                    stmt.bindNull(21);
                } else {
                    stmt.bindString(21, str3);
                }
                String str4 = value.is_see;
                if (str4 == null) {
                    stmt.bindNull(22);
                } else {
                    stmt.bindString(22, str4);
                }
                String str5 = value.sign_time;
                if (str5 == null) {
                    stmt.bindNull(23);
                } else {
                    stmt.bindString(23, str5);
                }
                String str6 = value.free_watch_time;
                if (str6 == null) {
                    stmt.bindNull(24);
                } else {
                    stmt.bindString(24, str6);
                }
                String str7 = value.watch_permission;
                if (str7 == null) {
                    stmt.bindNull(25);
                } else {
                    stmt.bindString(25, str7);
                }
                String str8 = value.expire_str;
                if (str8 == null) {
                    stmt.bindNull(26);
                } else {
                    stmt.bindString(26, str8);
                }
                String str9 = value.activity_id;
                if (str9 == null) {
                    stmt.bindNull(27);
                } else {
                    stmt.bindString(27, str9);
                }
            }
        };
        this.__deletionAdapterOfDataBean = new EntityDeletionOrUpdateAdapter<CourseVideoListBean.DataBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM `course_video` WHERE `userAndAppId` = ? AND `vid` = ? AND `chapter_id` = ? AND `type` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, CourseVideoListBean.DataBean value) {
                String str = value.userAndAppId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                if (value.getVid() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getVid());
                }
                if (value.getChapter_id() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getChapter_id());
                }
                if (value.getType() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getType());
                }
            }
        };
        this.__updateAdapterOfDataBean = new EntityDeletionOrUpdateAdapter<CourseVideoListBean.DataBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.3
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `course_video` SET `userAndAppId` = ?,`id` = ?,`thumb` = ?,`vid` = ?,`duration` = ?,`lecturer` = ?,`sort` = ?,`description` = ?,`chapter_id` = ?,`vidteaching_id` = ?,`app_id` = ?,`watched` = ?,`title` = ?,`duration_text` = ?,`comment_count` = ?,`count` = ?,`stutas` = ?,`progress` = ?,`type` = ?,`collection` = ?,`note` = ?,`is_see` = ?,`sign_time` = ?,`free_watch_time` = ?,`watch_permission` = ?,`expire_str` = ?,`activity_id` = ? WHERE `userAndAppId` = ? AND `vid` = ? AND `chapter_id` = ? AND `type` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, CourseVideoListBean.DataBean value) {
                String str = value.userAndAppId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                stmt.bindLong(2, value.getId());
                if (value.getThumb() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getThumb());
                }
                if (value.getVid() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getVid());
                }
                if (value.getDuration() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getDuration());
                }
                if (value.getLecturer() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getLecturer());
                }
                stmt.bindLong(7, value.getSort());
                if (value.getDescription() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getDescription());
                }
                if (value.getChapter_id() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getChapter_id());
                }
                if (value.getVidteaching_id() == null) {
                    stmt.bindNull(10);
                } else {
                    stmt.bindString(10, value.getVidteaching_id());
                }
                if (value.getApp_id() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getApp_id());
                }
                if (value.getWatched() == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, value.getWatched());
                }
                if (value.getTitle() == null) {
                    stmt.bindNull(13);
                } else {
                    stmt.bindString(13, value.getTitle());
                }
                if (value.getDuration_text() == null) {
                    stmt.bindNull(14);
                } else {
                    stmt.bindString(14, value.getDuration_text());
                }
                if (value.getComment_count() == null) {
                    stmt.bindNull(15);
                } else {
                    stmt.bindString(15, value.getComment_count());
                }
                if (value.getCount() == null) {
                    stmt.bindNull(16);
                } else {
                    stmt.bindString(16, value.getCount());
                }
                stmt.bindLong(17, value.getStutas());
                stmt.bindLong(18, value.getProgress());
                if (value.getType() == null) {
                    stmt.bindNull(19);
                } else {
                    stmt.bindString(19, value.getType());
                }
                String str2 = value.collection;
                if (str2 == null) {
                    stmt.bindNull(20);
                } else {
                    stmt.bindString(20, str2);
                }
                String str3 = value.note;
                if (str3 == null) {
                    stmt.bindNull(21);
                } else {
                    stmt.bindString(21, str3);
                }
                String str4 = value.is_see;
                if (str4 == null) {
                    stmt.bindNull(22);
                } else {
                    stmt.bindString(22, str4);
                }
                String str5 = value.sign_time;
                if (str5 == null) {
                    stmt.bindNull(23);
                } else {
                    stmt.bindString(23, str5);
                }
                String str6 = value.free_watch_time;
                if (str6 == null) {
                    stmt.bindNull(24);
                } else {
                    stmt.bindString(24, str6);
                }
                String str7 = value.watch_permission;
                if (str7 == null) {
                    stmt.bindNull(25);
                } else {
                    stmt.bindString(25, str7);
                }
                String str8 = value.expire_str;
                if (str8 == null) {
                    stmt.bindNull(26);
                } else {
                    stmt.bindString(26, str8);
                }
                String str9 = value.activity_id;
                if (str9 == null) {
                    stmt.bindNull(27);
                } else {
                    stmt.bindString(27, str9);
                }
                String str10 = value.userAndAppId;
                if (str10 == null) {
                    stmt.bindNull(28);
                } else {
                    stmt.bindString(28, str10);
                }
                if (value.getVid() == null) {
                    stmt.bindNull(29);
                } else {
                    stmt.bindString(29, value.getVid());
                }
                if (value.getChapter_id() == null) {
                    stmt.bindNull(30);
                } else {
                    stmt.bindString(30, value.getChapter_id());
                }
                if (value.getType() == null) {
                    stmt.bindNull(31);
                } else {
                    stmt.bindString(31, value.getType());
                }
            }
        };
        this.__preparedStmtOfDeleteData = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from course_video";
            }
        };
        this.__preparedStmtOfDeleteVideo = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from course_video where vid=? ";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public void deleteData() {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteData.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteData.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public void deleteVideo(CourseVideoListBean.DataBean... dataBean) {
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfDataBean.handleMultiple(dataBean);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public LiveData<List<CourseVideoListBean.DataBean>> getChapterVideoList(String vidteaching_id, String type, String userAndAppId, String chapter_id) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_video where vidteaching_id = ? and type = ? and userAndAppId=? and chapter_id=? order by sort,id", 4);
        if (vidteaching_id == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, vidteaching_id);
        }
        if (type == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, type);
        }
        if (userAndAppId == null) {
            roomSQLiteQueryAcquire.bindNull(3);
        } else {
            roomSQLiteQueryAcquire.bindString(3, userAndAppId);
        }
        if (chapter_id == null) {
            roomSQLiteQueryAcquire.bindNull(4);
        } else {
            roomSQLiteQueryAcquire.bindString(4, chapter_id);
        }
        return new ComputableLiveData<List<CourseVideoListBean.DataBean>>(this.__db.getQueryExecutor()) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.6
            private InvalidationTracker.Observer _observer;

            public void finalize() {
                roomSQLiteQueryAcquire.release();
            }

            @Override // androidx.lifecycle.ComputableLiveData
            public List<CourseVideoListBean.DataBean> compute() {
                if (this._observer == null) {
                    this._observer = new InvalidationTracker.Observer("course_video", new String[0]) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl.6.1
                        @Override // androidx.room.InvalidationTracker.Observer
                        public void onInvalidated(@NonNull Set<String> tables) {
                            invalidate();
                        }
                    };
                    CourseVideoDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(this._observer);
                }
                Cursor cursorQuery = CourseVideoDao_Impl.this.__db.query(roomSQLiteQueryAcquire);
                try {
                    int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("userAndAppId");
                    int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("id");
                    int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("thumb");
                    int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("vid");
                    int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("duration");
                    int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("lecturer");
                    int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("sort");
                    int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("description");
                    int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("chapter_id");
                    int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("vidteaching_id");
                    int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("app_id");
                    int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow(DatabaseManager.WATCHED);
                    int columnIndexOrThrow13 = cursorQuery.getColumnIndexOrThrow("title");
                    int columnIndexOrThrow14 = cursorQuery.getColumnIndexOrThrow("duration_text");
                    int columnIndexOrThrow15 = cursorQuery.getColumnIndexOrThrow("comment_count");
                    int columnIndexOrThrow16 = cursorQuery.getColumnIndexOrThrow("count");
                    int columnIndexOrThrow17 = cursorQuery.getColumnIndexOrThrow("stutas");
                    int columnIndexOrThrow18 = cursorQuery.getColumnIndexOrThrow("progress");
                    int columnIndexOrThrow19 = cursorQuery.getColumnIndexOrThrow("type");
                    int columnIndexOrThrow20 = cursorQuery.getColumnIndexOrThrow("collection");
                    int columnIndexOrThrow21 = cursorQuery.getColumnIndexOrThrow("note");
                    int columnIndexOrThrow22 = cursorQuery.getColumnIndexOrThrow("is_see");
                    int columnIndexOrThrow23 = cursorQuery.getColumnIndexOrThrow("sign_time");
                    int columnIndexOrThrow24 = cursorQuery.getColumnIndexOrThrow("free_watch_time");
                    int columnIndexOrThrow25 = cursorQuery.getColumnIndexOrThrow("watch_permission");
                    int columnIndexOrThrow26 = cursorQuery.getColumnIndexOrThrow("expire_str");
                    int columnIndexOrThrow27 = cursorQuery.getColumnIndexOrThrow(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
                    int i2 = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        CourseVideoListBean.DataBean dataBean = new CourseVideoListBean.DataBean();
                        ArrayList arrayList2 = arrayList;
                        dataBean.userAndAppId = cursorQuery.getString(columnIndexOrThrow);
                        dataBean.setId(cursorQuery.getInt(columnIndexOrThrow2));
                        dataBean.setThumb(cursorQuery.getString(columnIndexOrThrow3));
                        dataBean.setVid(cursorQuery.getString(columnIndexOrThrow4));
                        dataBean.setDuration(cursorQuery.getString(columnIndexOrThrow5));
                        dataBean.setLecturer(cursorQuery.getString(columnIndexOrThrow6));
                        dataBean.setSort(cursorQuery.getInt(columnIndexOrThrow7));
                        dataBean.setDescription(cursorQuery.getString(columnIndexOrThrow8));
                        dataBean.setChapter_id(cursorQuery.getString(columnIndexOrThrow9));
                        dataBean.setVidteaching_id(cursorQuery.getString(columnIndexOrThrow10));
                        dataBean.setApp_id(cursorQuery.getString(columnIndexOrThrow11));
                        dataBean.setWatched(cursorQuery.getString(columnIndexOrThrow12));
                        dataBean.setTitle(cursorQuery.getString(columnIndexOrThrow13));
                        int i3 = i2;
                        int i4 = columnIndexOrThrow;
                        dataBean.setDuration_text(cursorQuery.getString(i3));
                        int i5 = columnIndexOrThrow15;
                        dataBean.setComment_count(cursorQuery.getString(i5));
                        int i6 = columnIndexOrThrow16;
                        dataBean.setCount(cursorQuery.getString(i6));
                        int i7 = columnIndexOrThrow17;
                        dataBean.setStutas(cursorQuery.getInt(i7));
                        int i8 = columnIndexOrThrow18;
                        dataBean.setProgress(cursorQuery.getInt(i8));
                        int i9 = columnIndexOrThrow19;
                        dataBean.setType(cursorQuery.getString(i9));
                        int i10 = columnIndexOrThrow20;
                        dataBean.collection = cursorQuery.getString(i10);
                        int i11 = columnIndexOrThrow21;
                        dataBean.note = cursorQuery.getString(i11);
                        int i12 = columnIndexOrThrow22;
                        dataBean.is_see = cursorQuery.getString(i12);
                        int i13 = columnIndexOrThrow23;
                        dataBean.sign_time = cursorQuery.getString(i13);
                        int i14 = columnIndexOrThrow24;
                        dataBean.free_watch_time = cursorQuery.getString(i14);
                        int i15 = columnIndexOrThrow25;
                        dataBean.watch_permission = cursorQuery.getString(i15);
                        int i16 = columnIndexOrThrow26;
                        dataBean.expire_str = cursorQuery.getString(i16);
                        int i17 = columnIndexOrThrow27;
                        dataBean.activity_id = cursorQuery.getString(i17);
                        arrayList = arrayList2;
                        arrayList.add(dataBean);
                        columnIndexOrThrow27 = i17;
                        columnIndexOrThrow = i4;
                        i2 = i3;
                        columnIndexOrThrow15 = i5;
                        columnIndexOrThrow16 = i6;
                        columnIndexOrThrow17 = i7;
                        columnIndexOrThrow18 = i8;
                        columnIndexOrThrow19 = i9;
                        columnIndexOrThrow20 = i10;
                        columnIndexOrThrow21 = i11;
                        columnIndexOrThrow22 = i12;
                        columnIndexOrThrow23 = i13;
                        columnIndexOrThrow24 = i14;
                        columnIndexOrThrow25 = i15;
                        columnIndexOrThrow26 = i16;
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                }
            }
        }.getLiveData();
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public void insertTopicList(List<CourseVideoListBean.DataBean> mTList) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDataBean.insert((Iterable) mTList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public void updateVideoStatus(CourseVideoListBean.DataBean dataBean) {
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfDataBean.handle(dataBean);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao
    public void deleteVideo(String vid) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteVideo.acquire();
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
            this.__preparedStmtOfDeleteVideo.release(supportSQLiteStatementAcquire);
        }
    }
}
