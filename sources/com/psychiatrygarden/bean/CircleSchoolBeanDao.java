package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.uuzuche.lib_zxing.decoding.Intents;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class CircleSchoolBeanDao extends AbstractDao<CircleSchoolBean, Long> {
    public static final String TABLENAME = "CIRCLE_SCHOOL_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "ID");
        public static final Property Name = new Property(1, String.class, "name", false, "NAME");
        public static final Property Province_id = new Property(2, String.class, "province_id", false, "PROVINCE_ID");
        public static final Property Type = new Property(3, String.class, "type", false, Intents.WifiConnect.TYPE);
        public static final Property Status = new Property(4, String.class, "status", false, "STATUS");
        public static final Property Updated = new Property(5, String.class, "updated", false, "UPDATED");
    }

    public CircleSchoolBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'CIRCLE_SCHOOL_BEAN' ('ID' INTEGER PRIMARY KEY ,'NAME' TEXT,'PROVINCE_ID' TEXT,'TYPE' TEXT,'STATUS' TEXT,'UPDATED' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'CIRCLE_SCHOOL_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public CircleSchoolBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CircleSchoolBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        String province_id = entity.getProvince_id();
        if (province_id != null) {
            stmt.bindString(3, province_id);
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(5, status);
        }
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(6, updated);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(CircleSchoolBean entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public Long readKey(Cursor cursor, int offset) {
        int i2 = offset + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long updateKeyAfterInsert(CircleSchoolBean entity, long rowId) {
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CircleSchoolBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        int i7 = offset + 5;
        return new CircleSchoolBean(lValueOf, string, string2, string3, cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CircleSchoolBean entity, int offset) {
        int i2 = offset + 0;
        entity.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setName(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setProvince_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setType(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setStatus(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setUpdated(cursor.isNull(i7) ? null : cursor.getString(i7));
    }
}
