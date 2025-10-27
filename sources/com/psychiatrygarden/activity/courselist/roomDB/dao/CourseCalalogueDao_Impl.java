package com.psychiatrygarden.activity.courselist.roomDB.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.roomDB.TypeConverters;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class CourseCalalogueDao_Impl implements CourseCalalogueDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfDataBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteCourseCalalogue;

    public CourseCalalogueDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfDataBean = new EntityInsertionAdapter<CourseCalalogueBean.DataNewBean.DataBean>(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `course_calaogue`(`userAndAppId`,`id`,`title`,`category_id`,`student_type`,`count`,`sort`,`watch_permission`,`expire_str`,`verify_goods_id`,`activity_id`,`courseList`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, CourseCalalogueBean.DataNewBean.DataBean value) {
                String str = value.userAndAppId;
                if (str == null) {
                    stmt.bindNull(1);
                } else {
                    stmt.bindString(1, str);
                }
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getTitle() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getTitle());
                }
                if (value.getCategory_id() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getCategory_id());
                }
                if (value.getStudent_type() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getStudent_type());
                }
                if (value.getCount() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getCount());
                }
                stmt.bindLong(7, value.getSort());
                if (value.getWatch_permission() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getWatch_permission());
                }
                if (value.getExpire_str() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getExpire_str());
                }
                if (value.getVerify_goods_id() == null) {
                    stmt.bindNull(10);
                } else {
                    stmt.bindString(10, value.getVerify_goods_id());
                }
                if (value.getActivity_id() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getActivity_id());
                }
                String strFromCourseCalalogueArrayList = TypeConverters.fromCourseCalalogueArrayList(value.getCourseList());
                if (strFromCourseCalalogueArrayList == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, strFromCourseCalalogueArrayList);
                }
            }
        };
        this.__preparedStmtOfDeleteCourseCalalogue = new SharedSQLiteStatement(__db) { // from class: com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete from course_calaogue where userAndAppId = ? and category_id = ?";
            }
        };
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao
    public void deleteCourseCalalogue(String userAndAppId, String category_id) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteCourseCalalogue.acquire();
        this.__db.beginTransaction();
        try {
            if (userAndAppId == null) {
                supportSQLiteStatementAcquire.bindNull(1);
            } else {
                supportSQLiteStatementAcquire.bindString(1, userAndAppId);
            }
            if (category_id == null) {
                supportSQLiteStatementAcquire.bindNull(2);
            } else {
                supportSQLiteStatementAcquire.bindString(2, category_id);
            }
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteCourseCalalogue.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao
    public List<CourseCalalogueBean.DataNewBean.DataBean> getCourseCalalogue(String userAndAppId, String category_id) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("select * from course_calaogue where userAndAppId = ? and category_id = ? order by sort asc", 2);
        if (userAndAppId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userAndAppId);
        }
        if (category_id == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, category_id);
        }
        Cursor cursorQuery = this.__db.query(roomSQLiteQueryAcquire);
        try {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("userAndAppId");
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("id");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("category_id");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("student_type");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("count");
            int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("sort");
            int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("watch_permission");
            int columnIndexOrThrow9 = cursorQuery.getColumnIndexOrThrow("expire_str");
            int columnIndexOrThrow10 = cursorQuery.getColumnIndexOrThrow("verify_goods_id");
            int columnIndexOrThrow11 = cursorQuery.getColumnIndexOrThrow(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
            int columnIndexOrThrow12 = cursorQuery.getColumnIndexOrThrow("courseList");
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                CourseCalalogueBean.DataNewBean.DataBean dataBean = new CourseCalalogueBean.DataNewBean.DataBean();
                roomSQLiteQuery = roomSQLiteQueryAcquire;
                try {
                    dataBean.userAndAppId = cursorQuery.getString(columnIndexOrThrow);
                    dataBean.setId(cursorQuery.getString(columnIndexOrThrow2));
                    dataBean.setTitle(cursorQuery.getString(columnIndexOrThrow3));
                    dataBean.setCategory_id(cursorQuery.getString(columnIndexOrThrow4));
                    dataBean.setStudent_type(cursorQuery.getString(columnIndexOrThrow5));
                    dataBean.setCount(cursorQuery.getString(columnIndexOrThrow6));
                    dataBean.setSort(cursorQuery.getInt(columnIndexOrThrow7));
                    dataBean.setWatch_permission(cursorQuery.getString(columnIndexOrThrow8));
                    dataBean.setExpire_str(cursorQuery.getString(columnIndexOrThrow9));
                    dataBean.setVerify_goods_id(cursorQuery.getString(columnIndexOrThrow10));
                    dataBean.setActivity_id(cursorQuery.getString(columnIndexOrThrow11));
                    dataBean.setCourseList(TypeConverters.fromCourseCalalogueString(cursorQuery.getString(columnIndexOrThrow12)));
                    arrayList.add(dataBean);
                    roomSQLiteQueryAcquire = roomSQLiteQuery;
                } catch (Throwable th) {
                    th = th;
                    cursorQuery.close();
                    roomSQLiteQuery.release();
                    throw th;
                }
            }
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao
    public void insertCourseCalalogueData(List<CourseCalalogueBean.DataNewBean.DataBean> dataBeans) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDataBean.insert((Iterable) dataBeans);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }
}
