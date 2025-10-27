package com.psychiatrygarden.activity.courselist.roomDB.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class CourseCoverDao_Impl implements CourseCoverDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfCourseCoverBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleSignleData;

    public CourseCoverDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfCourseCoverBean = new EntityInsertionAdapter<CourseCoverBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `course_cover`(`id`,`cover`,`title`,`activity_id`,`sort`,`isExpanded`) VALUES (?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, CourseCoverBean courseCoverBean) {
                supportSQLiteStatement.bindLong(1, courseCoverBean.id);
                String str = courseCoverBean.cover;
                if (str == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, str);
                }
                String str2 = courseCoverBean.title;
                if (str2 == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, str2);
                }
                String str3 = courseCoverBean.activity_id;
                if (str3 == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, str3);
                }
                supportSQLiteStatement.bindLong(5, courseCoverBean.sort);
                supportSQLiteStatement.bindLong(6, courseCoverBean.getIsExpanded() ? 1L : 0L);
            }
        };
        this.__preparedStmtOfDeleSignleData = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from `course_cover` where id=?";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao
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

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao
    public void deleteAllData(String[] ids) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("delete from `course_cover` where id in (");
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

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao
    public CourseCoverBean findCourse(int id) {
        CourseCoverBean courseCoverBean;
        boolean z2 = true;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_cover where id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, id);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            if (cursorQuery.moveToFirst()) {
                courseCoverBean = new CourseCoverBean();
                courseCoverBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseCoverBean.cover = cursorQuery.getString(columnIndexOrThrow2);
                courseCoverBean.title = cursorQuery.getString(columnIndexOrThrow3);
                courseCoverBean.activity_id = cursorQuery.getString(columnIndexOrThrow4);
                courseCoverBean.sort = cursorQuery.getInt(columnIndexOrThrow5);
                if (cursorQuery.getInt(columnIndexOrThrow6) == 0) {
                    z2 = false;
                }
                courseCoverBean.setExpanded(z2);
            } else {
                courseCoverBean = null;
            }
            return courseCoverBean;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao
    public List<CourseCoverBean> getList() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from `course_cover`", 0);
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("isExpanded");
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                CourseCoverBean courseCoverBean = new CourseCoverBean();
                courseCoverBean.id = cursorQuery.getInt(columnIndexOrThrow);
                courseCoverBean.cover = cursorQuery.getString(columnIndexOrThrow2);
                courseCoverBean.title = cursorQuery.getString(columnIndexOrThrow3);
                courseCoverBean.activity_id = cursorQuery.getString(columnIndexOrThrow4);
                courseCoverBean.sort = cursorQuery.getInt(columnIndexOrThrow5);
                courseCoverBean.setExpanded(cursorQuery.getInt(columnIndexOrThrow6) != 0);
                arrayList.add(courseCoverBean);
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao
    public void insertTopic(CourseCoverBean mTList) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfCourseCoverBean.insert((EntityInsertionAdapter) mTList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}
