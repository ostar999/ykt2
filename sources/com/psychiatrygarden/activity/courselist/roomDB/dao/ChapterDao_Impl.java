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
import androidx.sqlite.db.SupportSQLiteStatement;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.activity.courselist.roomDB.TypeConverters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public final class ChapterDao_Impl implements ChapterDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfDataBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteData;

    public ChapterDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfDataBean = new EntityInsertionAdapter<CourseListChapterBean.DataBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `course_chapter`(`userAndAppId`,`chapter_id`,`vidteaching_id`,`parent_id`,`title`,`have`,`total`,`watched`,`isdown`,`children`,`type`,`sort`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, CourseListChapterBean.DataBean value) {
                String str = value.userAndAppId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                stmt.bindLong(2, value.chapter_id);
                stmt.bindLong(3, value.vidteaching_id);
                stmt.bindLong(4, value.parent_id);
                String str2 = value.title;
                if (str2 == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, str2);
                }
                String str3 = value.have;
                if (str3 == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, str3);
                }
                stmt.bindLong(7, value.total);
                stmt.bindLong(8, value.watched);
                String str4 = value.isdown;
                if (str4 == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, str4);
                }
                String strFromMediaArrayList = TypeConverters.fromMediaArrayList(value.children);
                if (strFromMediaArrayList == null) {
                    stmt.bindNull(10);
                } else {
                    stmt.bindString(10, strFromMediaArrayList);
                }
                String str5 = value.type;
                if (str5 == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, str5);
                }
                stmt.bindLong(12, value.sort);
            }
        };
        this.__preparedStmtOfDeleteData = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from course_chapter";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao
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

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao
    public LiveData<List<CourseListChapterBean.DataBean>> getChapterList(String vidteaching_id, String type) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_chapter where vidteaching_id = ? and type = ?", 2);
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
        return new ComputableLiveData<List<CourseListChapterBean.DataBean>>(this.__db.getQueryExecutor()) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao_Impl.3
            private InvalidationTracker.Observer _observer;

            public void finalize() {
                roomSQLiteQueryAcquire.release();
            }

            @Override // androidx.lifecycle.ComputableLiveData
            public List<CourseListChapterBean.DataBean> compute() {
                if (this._observer == null) {
                    this._observer = new InvalidationTracker.Observer("course_chapter", new String[0]) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao_Impl.3.1
                        @Override // androidx.room.InvalidationTracker.Observer
                        public void onInvalidated(@NonNull Set<String> tables) {
                            invalidate();
                        }
                    };
                    ChapterDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(this._observer);
                }
                Cursor cursorQuery = ChapterDao_Impl.this.__db.query(roomSQLiteQueryAcquire);
                try {
                    int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("userAndAppId");
                    int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("chapter_id");
                    int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("vidteaching_id");
                    int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("parent_id");
                    int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("title");
                    int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("have");
                    int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("total");
                    int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow(DatabaseManager.WATCHED);
                    int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("isdown");
                    int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("children");
                    int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow("type");
                    int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("sort");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        CourseListChapterBean.DataBean dataBean = new CourseListChapterBean.DataBean();
                        dataBean.userAndAppId = cursorQuery.getString(columnIndexOrThrow);
                        dataBean.chapter_id = cursorQuery.getInt(columnIndexOrThrow2);
                        dataBean.vidteaching_id = cursorQuery.getInt(columnIndexOrThrow3);
                        dataBean.parent_id = cursorQuery.getInt(columnIndexOrThrow4);
                        dataBean.title = cursorQuery.getString(columnIndexOrThrow5);
                        dataBean.have = cursorQuery.getString(columnIndexOrThrow6);
                        dataBean.total = cursorQuery.getInt(columnIndexOrThrow7);
                        dataBean.watched = cursorQuery.getInt(columnIndexOrThrow8);
                        dataBean.isdown = cursorQuery.getString(columnIndexOrThrow9);
                        dataBean.children = TypeConverters.fromMediaString(cursorQuery.getString(columnIndexOrThrow10));
                        dataBean.type = cursorQuery.getString(columnIndexOrThrow11);
                        dataBean.sort = cursorQuery.getInt(columnIndexOrThrow12);
                        arrayList.add(dataBean);
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                }
            }
        }.getLiveData();
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao
    public void insertTopicList(List<CourseListChapterBean.DataBean> mTList) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDataBean.insert((Iterable) mTList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}
