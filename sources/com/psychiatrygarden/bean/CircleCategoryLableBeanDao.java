package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class CircleCategoryLableBeanDao extends AbstractDao<CircleCategoryLableBean, Void> {
    public static final String TABLENAME = "CIRCLE_CATEGORY_LABLE_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", false, "ID");
        public static final Property Name = new Property(1, String.class, "name", false, "NAME");
        public static final Property Cate_id = new Property(2, String.class, "cate_id", false, "CATE_ID");
        public static final Property Parent_cate = new Property(3, String.class, "parent_cate", false, "PARENT_CATE");
        public static final Property IsCheck = new Property(4, Boolean.class, "isCheck", false, "IS_CHECK");
    }

    public CircleCategoryLableBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'CIRCLE_CATEGORY_LABLE_BEAN' ('ID' INTEGER,'NAME' TEXT,'CATE_ID' TEXT,'PARENT_CATE' TEXT,'IS_CHECK' INTEGER);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'CIRCLE_CATEGORY_LABLE_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void getKey(CircleCategoryLableBean entity) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void updateKeyAfterInsert(CircleCategoryLableBean entity, long rowId) {
        return null;
    }

    public CircleCategoryLableBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CircleCategoryLableBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        String cate_id = entity.getCate_id();
        if (cate_id != null) {
            stmt.bindString(3, cate_id);
        }
        String parent_cate = entity.getParent_cate();
        if (parent_cate != null) {
            stmt.bindString(4, parent_cate);
        }
        Boolean isCheck = entity.getIsCheck();
        if (isCheck != null) {
            stmt.bindLong(5, isCheck.booleanValue() ? 1L : 0L);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CircleCategoryLableBean readEntity(Cursor cursor, int offset) {
        Boolean boolValueOf;
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        if (cursor.isNull(i6)) {
            boolValueOf = null;
        } else {
            boolValueOf = Boolean.valueOf(cursor.getShort(i6) != 0);
        }
        return new CircleCategoryLableBean(lValueOf, string, string2, string3, boolValueOf);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CircleCategoryLableBean entity, int offset) {
        int i2 = offset + 0;
        Boolean boolValueOf = null;
        entity.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setName(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setCate_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setParent_cate(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        if (!cursor.isNull(i6)) {
            boolValueOf = Boolean.valueOf(cursor.getShort(i6) != 0);
        }
        entity.setIsCheck(boolValueOf);
    }
}
