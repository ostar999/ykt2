package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class WrongBeanBeiDao extends AbstractDao<WrongBeanBei, Long> {
    public static final String TABLENAME = "WRONG_BEAN_BEI";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Chapter_parent_id = new Property(1, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Chapter_id = new Property(2, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Year = new Property(3, String.class, "year", false, "YEAR");
        public static final Property Cate_num = new Property(4, Long.class, "cate_num", false, "CATE_NUM");
        public static final Property Year_num = new Property(5, Long.class, "year_num", false, "YEAR_NUM");
        public static final Property Media_url = new Property(6, String.class, "media_url", false, "MEDIA_URL");
        public static final Property Isxueshuo = new Property(7, String.class, "isxueshuo", false, "ISXUESHUO");
        public static final Property Iszhuanshuo = new Property(8, String.class, "iszhuanshuo", false, "ISZHUANSHUO");
        public static final Property Isgaopinkaodian = new Property(9, String.class, "isgaopinkaodian", false, "ISGAOPINKAODIAN");
        public static final Property Is_practice = new Property(10, String.class, "is_practice", false, "IS_PRACTICE");
        public static final Property Unit = new Property(11, String.class, "unit", false, "UNIT");
    }

    public WrongBeanBeiDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'WRONG_BEAN_BEI' ('QUESTION_ID' INTEGER PRIMARY KEY ,'CHAPTER_PARENT_ID' TEXT,'CHAPTER_ID' TEXT,'YEAR' TEXT,'CATE_NUM' INTEGER,'YEAR_NUM' INTEGER,'MEDIA_URL' TEXT,'ISXUESHUO' TEXT,'ISZHUANSHUO' TEXT,'ISGAOPINKAODIAN' TEXT,'IS_PRACTICE' TEXT,'UNIT' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'WRONG_BEAN_BEI'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public WrongBeanBeiDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, WrongBeanBei entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        String chapter_parent_id = entity.getChapter_parent_id();
        if (chapter_parent_id != null) {
            stmt.bindString(2, chapter_parent_id);
        }
        String chapter_id = entity.getChapter_id();
        if (chapter_id != null) {
            stmt.bindString(3, chapter_id);
        }
        String year = entity.getYear();
        if (year != null) {
            stmt.bindString(4, year);
        }
        Long cate_num = entity.getCate_num();
        if (cate_num != null) {
            stmt.bindLong(5, cate_num.longValue());
        }
        Long year_num = entity.getYear_num();
        if (year_num != null) {
            stmt.bindLong(6, year_num.longValue());
        }
        String media_url = entity.getMedia_url();
        if (media_url != null) {
            stmt.bindString(7, media_url);
        }
        String isxueshuo = entity.getIsxueshuo();
        if (isxueshuo != null) {
            stmt.bindString(8, isxueshuo);
        }
        String iszhuanshuo = entity.getIszhuanshuo();
        if (iszhuanshuo != null) {
            stmt.bindString(9, iszhuanshuo);
        }
        String isgaopinkaodian = entity.getIsgaopinkaodian();
        if (isgaopinkaodian != null) {
            stmt.bindString(10, isgaopinkaodian);
        }
        String is_practice = entity.getIs_practice();
        if (is_practice != null) {
            stmt.bindString(11, is_practice);
        }
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(12, unit);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(WrongBeanBei entity) {
        if (entity != null) {
            return entity.getQuestion_id();
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
    public Long updateKeyAfterInsert(WrongBeanBei entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public WrongBeanBei readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        int i4 = offset + 2;
        int i5 = offset + 3;
        int i6 = offset + 4;
        int i7 = offset + 5;
        int i8 = offset + 6;
        int i9 = offset + 7;
        int i10 = offset + 8;
        int i11 = offset + 9;
        int i12 = offset + 10;
        int i13 = offset + 11;
        return new WrongBeanBei(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : cursor.getString(i4), cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6)), cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7)), cursor.isNull(i8) ? null : cursor.getString(i8), cursor.isNull(i9) ? null : cursor.getString(i9), cursor.isNull(i10) ? null : cursor.getString(i10), cursor.isNull(i11) ? null : cursor.getString(i11), cursor.isNull(i12) ? null : cursor.getString(i12), cursor.isNull(i13) ? null : cursor.getString(i13));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, WrongBeanBei entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setChapter_parent_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setChapter_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setYear(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setCate_num(cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6)));
        int i7 = offset + 5;
        entity.setYear_num(cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7)));
        int i8 = offset + 6;
        entity.setMedia_url(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setIsxueshuo(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setIszhuanshuo(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setIsgaopinkaodian(cursor.isNull(i11) ? null : cursor.getString(i11));
        int i12 = offset + 10;
        entity.setIs_practice(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setUnit(cursor.isNull(i13) ? null : cursor.getString(i13));
    }
}
