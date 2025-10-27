package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class CircleCategoryBeanDao extends AbstractDao<CircleCategoryBean, Long> {
    public static final String TABLENAME = "CIRCLE_CATEGORY_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", false, "ID");
        public static final Property Cover = new Property(1, String.class, AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, false, "COVER");
        public static final Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public static final Property Name = new Property(3, String.class, "name", false, "NAME");
        public static final Property Parent_cate = new Property(4, String.class, "parent_cate", false, "PARENT_CATE");
        public static final Property Province_id = new Property(5, String.class, "province_id", false, "PROVINCE_ID");
        public static final Property School_id = new Property(6, String.class, "school_id", false, "SCHOOL_ID");
        public static final Property Sort = new Property(7, Long.class, "sort", false, "SORT");
        public static final Property Status = new Property(8, String.class, "status", false, "STATUS");
        public static final Property Today_topic_num = new Property(9, String.class, "today_topic_num", false, "TODAY_TOPIC_NUM");
        public static final Property IsCollect = new Property(10, Boolean.class, "isCollect", false, "IS_COLLECT");
    }

    public CircleCategoryBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'CIRCLE_CATEGORY_BEAN' ('ID' INTEGER,'COVER' TEXT,'DESCRIPTION' TEXT,'NAME' TEXT,'PARENT_CATE' TEXT,'PROVINCE_ID' TEXT,'SCHOOL_ID' TEXT,'SORT' INTEGER,'STATUS' TEXT,'TODAY_TOPIC_NUM' TEXT,'IS_COLLECT' INTEGER);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'CIRCLE_CATEGORY_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public CircleCategoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CircleCategoryBean entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id.longValue());
        }
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(2, cover);
        }
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        String parent_cate = entity.getParent_cate();
        if (parent_cate != null) {
            stmt.bindString(5, parent_cate);
        }
        String province_id = entity.getProvince_id();
        if (province_id != null) {
            stmt.bindString(6, province_id);
        }
        String school_id = entity.getSchool_id();
        if (school_id != null) {
            stmt.bindString(7, school_id);
        }
        Long sort = entity.getSort();
        if (sort != null) {
            stmt.bindLong(8, sort.longValue());
        }
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(9, status);
        }
        String today_topic_num = entity.getToday_topic_num();
        if (today_topic_num != null) {
            stmt.bindString(10, today_topic_num);
        }
        Boolean isCollect = entity.getIsCollect();
        if (isCollect != null) {
            stmt.bindLong(11, isCollect.booleanValue() ? 1L : 0L);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(CircleCategoryBean entity) {
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
    public Long updateKeyAfterInsert(CircleCategoryBean entity, long rowId) {
        entity.setId(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CircleCategoryBean readEntity(Cursor cursor, int offset) {
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
        String string4 = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = offset + 5;
        String string5 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = offset + 6;
        String string6 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = offset + 7;
        Long lValueOf2 = cursor.isNull(i9) ? null : Long.valueOf(cursor.getLong(i9));
        int i10 = offset + 8;
        String string7 = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = offset + 9;
        String string8 = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = offset + 10;
        if (cursor.isNull(i12)) {
            boolValueOf = null;
        } else {
            boolValueOf = Boolean.valueOf(cursor.getShort(i12) != 0);
        }
        return new CircleCategoryBean(lValueOf, string, string2, string3, string4, string5, string6, lValueOf2, string7, string8, boolValueOf);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CircleCategoryBean entity, int offset) {
        int i2 = offset + 0;
        Boolean boolValueOf = null;
        entity.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setCover(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setDescription(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setName(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setParent_cate(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setProvince_id(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setSchool_id(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setSort(cursor.isNull(i9) ? null : Long.valueOf(cursor.getLong(i9)));
        int i10 = offset + 8;
        entity.setStatus(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setToday_topic_num(cursor.isNull(i11) ? null : cursor.getString(i11));
        int i12 = offset + 10;
        if (!cursor.isNull(i12)) {
            boolValueOf = Boolean.valueOf(cursor.getShort(i12) != 0);
        }
        entity.setIsCollect(boolValueOf);
    }
}
