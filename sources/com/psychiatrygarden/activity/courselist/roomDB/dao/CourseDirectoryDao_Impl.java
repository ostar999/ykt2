package com.psychiatrygarden.activity.courselist.roomDB.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class CourseDirectoryDao_Impl implements CourseDirectoryDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfCourseDirectoryBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleSignleData;

    public CourseDirectoryDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfCourseDirectoryBean = new EntityInsertionAdapter<CourseDirectoryBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `course_directory`(`id`,`title`,`pid`,`sort`,`isExpanded`) VALUES (?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, CourseDirectoryBean courseDirectoryBean) {
                supportSQLiteStatement.bindLong(1, courseDirectoryBean.id);
                String str = courseDirectoryBean.title;
                if (str == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, str);
                }
                String str2 = courseDirectoryBean.pid;
                if (str2 == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, str2);
                }
                supportSQLiteStatement.bindLong(4, courseDirectoryBean.sort);
                supportSQLiteStatement.bindLong(5, courseDirectoryBean.getIsExpanded() ? 1L : 0L);
            }
        };
        this.__preparedStmtOfDeleSignleData = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from course_directory where id=?";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public void deleSignleData(String id) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleSignleData.acquire();
        this.__db.beginTransaction();
        try {
            if (id == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, id);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleSignleData.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public void deleteAllData(String[] ids) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("delete from course_directory where id in (");
        StringUtil.appendPlaceholders(sbNewStringBuilder, ids.length);
        sbNewStringBuilder.append(")");
        SupportSQLiteStatement supportSQLiteStatementCompileStatement = this.__db.compileStatement(sbNewStringBuilder.toString());
        int i2 = 1;
        for (String str : ids) {
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

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public CourseDirectoryBean findCourseDirectory(int chapterId) {
        CourseDirectoryBean courseDirectoryBean;
        boolean z2 = true;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_directory where id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, chapterId);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("pid");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            if (cursorQuery.moveToFirst()) {
                courseDirectoryBean = new CourseDirectoryBean();
                courseDirectoryBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseDirectoryBean.title = cursorQuery.getString(columnIndexOrThrow2);
                courseDirectoryBean.pid = cursorQuery.getString(columnIndexOrThrow3);
                courseDirectoryBean.sort = cursorQuery.getInt(columnIndexOrThrow4);
                if (cursorQuery.getInt(columnIndexOrThrow5) == 0) {
                    z2 = false;
                }
                courseDirectoryBean.setExpanded(z2);
            } else {
                courseDirectoryBean = null;
            }
            return courseDirectoryBean;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public List<CourseDirectoryBean> findDirectoryByVideoId(int id) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_directory where id =?", 1);
        roomSQLiteQueryAcquire.bindLong(1, id);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("pid");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                courseDirectoryBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseDirectoryBean.title = cursorQuery.getString(columnIndexOrThrow2);
                courseDirectoryBean.pid = cursorQuery.getString(columnIndexOrThrow3);
                courseDirectoryBean.sort = cursorQuery.getInt(columnIndexOrThrow4);
                courseDirectoryBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow5) != 0);
                arrayList.add(courseDirectoryBean);
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public List<CourseDirectoryBean> getCourseDirectoryByCourseId(String courseId) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_directory where pid = ?", 1);
        if (courseId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, courseId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("pid");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                courseDirectoryBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseDirectoryBean.title = cursorQuery.getString(columnIndexOrThrow2);
                courseDirectoryBean.pid = cursorQuery.getString(columnIndexOrThrow3);
                courseDirectoryBean.sort = cursorQuery.getInt(columnIndexOrThrow4);
                courseDirectoryBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow5) != 0);
                arrayList.add(courseDirectoryBean);
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public List<CourseDirectoryBean> getList() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_directory", 0);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("pid");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                courseDirectoryBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseDirectoryBean.title = cursorQuery.getString(columnIndexOrThrow2);
                courseDirectoryBean.pid = cursorQuery.getString(columnIndexOrThrow3);
                courseDirectoryBean.sort = cursorQuery.getInt(columnIndexOrThrow4);
                courseDirectoryBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow5) != 0);
                arrayList.add(courseDirectoryBean);
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public void insertTopicList(List<CourseDirectoryBean> mTList) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfCourseDirectoryBean.insert((Iterable) mTList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao
    public CourseDirectoryBean findCourseDirectory(int chapterId, String courseId) {
        CourseDirectoryBean courseDirectoryBean;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_directory where id = ? and pid=?", 2);
        long j2 = chapterId;
        boolean z2 = true;
        roomSQLiteQueryAcquire.bindLong(1, j2);
        if (courseId == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, courseId);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("pid");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            if (cursorQuery.moveToFirst()) {
                courseDirectoryBean = new CourseDirectoryBean();
                courseDirectoryBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseDirectoryBean.title = cursorQuery.getString(columnIndexOrThrow2);
                courseDirectoryBean.pid = cursorQuery.getString(columnIndexOrThrow3);
                courseDirectoryBean.sort = cursorQuery.getInt(columnIndexOrThrow4);
                if (cursorQuery.getInt(columnIndexOrThrow5) == 0) {
                    z2 = false;
                }
                courseDirectoryBean.setExpanded(z2);
            } else {
                courseDirectoryBean = null;
            }
            return courseDirectoryBean;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }
}
