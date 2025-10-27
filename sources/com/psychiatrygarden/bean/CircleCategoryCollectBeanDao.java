package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class CircleCategoryCollectBeanDao extends AbstractDao<CircleCategoryCollectBean, Long> {
    public static final String TABLENAME = "CIRCLE_CATEGORY_COLLECT_BEAN";

    public static class Properties {
        public static final Property User_id = new Property(0, Long.class, "user_id", false, "USER_ID");
        public static final Property Parent_cate = new Property(1, String.class, "parent_cate", false, "PARENT_CATE");
        public static final Property Cate_id = new Property(2, Long.class, "cate_id", true, "CATE_ID");
        public static final Property Today_topic_num = new Property(3, String.class, "today_topic_num", false, "TODAY_TOPIC_NUM");
    }

    public CircleCategoryCollectBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'CIRCLE_CATEGORY_COLLECT_BEAN' ('USER_ID' INTEGER,'PARENT_CATE' TEXT,'CATE_ID' INTEGER PRIMARY KEY ,'TODAY_TOPIC_NUM' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'CIRCLE_CATEGORY_COLLECT_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public CircleCategoryCollectBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CircleCategoryCollectBean entity) {
        stmt.clearBindings();
        Long user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindLong(1, user_id.longValue());
        }
        String parent_cate = entity.getParent_cate();
        if (parent_cate != null) {
            stmt.bindString(2, parent_cate);
        }
        Long cate_id = entity.getCate_id();
        if (cate_id != null) {
            stmt.bindLong(3, cate_id.longValue());
        }
        String today_topic_num = entity.getToday_topic_num();
        if (today_topic_num != null) {
            stmt.bindString(4, today_topic_num);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(CircleCategoryCollectBean entity) {
        if (entity != null) {
            return entity.getCate_id();
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public Long readKey(Cursor cursor, int offset) {
        int i2 = offset + 2;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long updateKeyAfterInsert(CircleCategoryCollectBean entity, long rowId) {
        entity.setCate_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CircleCategoryCollectBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        int i4 = offset + 2;
        int i5 = offset + 3;
        return new CircleCategoryCollectBean(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)), cursor.isNull(i5) ? null : cursor.getString(i5));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CircleCategoryCollectBean entity, int offset) {
        int i2 = offset + 0;
        entity.setUser_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setParent_cate(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setCate_id(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
        int i5 = offset + 3;
        entity.setToday_topic_num(cursor.isNull(i5) ? null : cursor.getString(i5));
    }
}
