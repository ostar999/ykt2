package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class SectionBeanDao extends AbstractDao<SectionBean, Long> {
    public static final String TABLENAME = "SECTION_BEAN";

    public static class Properties {
        public static final Property Chapter_id = new Property(0, Long.class, "chapter_id", true, "CHAPTER_ID");
        public static final Property Chapter_parent_id = new Property(1, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Title = new Property(2, String.class, "title", false, "TITLE");
        public static final Property Sort = new Property(3, Long.class, "sort", false, "SORT");
        public static final Property Isxueshuo = new Property(4, String.class, "isxueshuo", false, "ISXUESHUO");
        public static final Property Iszhuanshuo = new Property(5, String.class, "iszhuanshuo", false, "ISZHUANSHUO");
        public static final Property School_year = new Property(6, String.class, "school_year", false, "SCHOOL_YEAR");
    }

    public SectionBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'SECTION_BEAN' ('CHAPTER_ID' INTEGER PRIMARY KEY ,'CHAPTER_PARENT_ID' TEXT,'TITLE' TEXT,'SORT' INTEGER,'ISXUESHUO' TEXT,'ISZHUANSHUO' TEXT,'SCHOOL_YEAR' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'SECTION_BEAN'");
        db.execSQL(sb.toString());
    }

    public void detachAll() {
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != 0) {
            identityScope.clear();
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public SectionBeanDao(DaoConfig config, DaoSessionTiKu daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, SectionBean entity) {
        stmt.clearBindings();
        Long chapter_id = entity.getChapter_id();
        if (chapter_id != null) {
            stmt.bindLong(1, chapter_id.longValue());
        }
        String chapter_parent_id = entity.getChapter_parent_id();
        if (chapter_parent_id != null) {
            stmt.bindString(2, chapter_parent_id);
        }
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
        Long sort = entity.getSort();
        if (sort != null) {
            stmt.bindLong(4, sort.longValue());
        }
        String isxueshuo = entity.getIsxueshuo();
        if (isxueshuo != null) {
            stmt.bindString(5, isxueshuo);
        }
        String iszhuanshuo = entity.getIszhuanshuo();
        if (iszhuanshuo != null) {
            stmt.bindString(6, iszhuanshuo);
        }
        String school_year = entity.getSchool_year();
        if (school_year != null) {
            stmt.bindString(7, school_year);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(SectionBean entity) {
        if (entity != null) {
            return entity.getChapter_id();
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
    public Long updateKeyAfterInsert(SectionBean entity, long rowId) {
        entity.setChapter_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public SectionBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        Long lValueOf2 = cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5));
        int i6 = offset + 4;
        String string3 = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = offset + 5;
        int i8 = offset + 6;
        return new SectionBean(lValueOf, string, string2, lValueOf2, string3, cursor.isNull(i7) ? null : cursor.getString(i7), cursor.isNull(i8) ? null : cursor.getString(i8));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, SectionBean entity, int offset) {
        int i2 = offset + 0;
        entity.setChapter_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setChapter_parent_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setTitle(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setSort(cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5)));
        int i6 = offset + 4;
        entity.setIsxueshuo(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setIszhuanshuo(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setSchool_year(cursor.isNull(i8) ? null : cursor.getString(i8));
    }
}
