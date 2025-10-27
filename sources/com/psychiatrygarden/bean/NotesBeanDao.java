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
public class NotesBeanDao extends AbstractDao<NotesBean, Long> {
    public static final String TABLENAME = "NOTES_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Chapter_parent_id = new Property(1, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Chapter_id = new Property(2, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Year = new Property(3, String.class, "year", false, "YEAR");
        public static final Property Cate_num = new Property(4, Long.class, "cate_num", false, "CATE_NUM");
        public static final Property Year_num = new Property(5, Long.class, "year_num", false, "YEAR_NUM");
        public static final Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public static final Property Media_url = new Property(7, String.class, "media_url", false, "MEDIA_URL");
        public static final Property Isxueshuo = new Property(8, String.class, "isxueshuo", false, "ISXUESHUO");
        public static final Property Iszhuanshuo = new Property(9, String.class, "iszhuanshuo", false, "ISZHUANSHUO");
        public static final Property Isgaopinkaodian = new Property(10, String.class, "isgaopinkaodian", false, "ISGAOPINKAODIAN");
        public static final Property Is_practice = new Property(11, String.class, "is_practice", false, "IS_PRACTICE");
        public static final Property Unit = new Property(12, String.class, "unit", false, "UNIT");
        public static final Property Part_id = new Property(13, Long.class, "part_id", false, "PART_ID");
        public static final Property Part_parent_id = new Property(14, Long.class, "part_parent_id", false, "PART_PARENT_ID");
        public static final Property Part_num_am = new Property(15, Long.class, "part_num_am", false, "PART_NUM_AM");
        public static final Property Part_num_pm = new Property(16, Long.class, "part_num_pm", false, "PART_NUM_PM");
        public static final Property Type = new Property(17, String.class, "type", false, Intents.WifiConnect.TYPE);
    }

    public NotesBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'NOTES_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'CHAPTER_PARENT_ID' TEXT,'CHAPTER_ID' TEXT,'YEAR' TEXT,'CATE_NUM' INTEGER,'YEAR_NUM' INTEGER,'CONTENT' TEXT,'MEDIA_URL' TEXT,'ISXUESHUO' TEXT,'ISZHUANSHUO' TEXT,'ISGAOPINKAODIAN' TEXT,'IS_PRACTICE' TEXT,'UNIT' TEXT,'PART_ID' INTEGER,'PART_PARENT_ID' INTEGER,'PART_NUM_AM' INTEGER,'PART_NUM_PM' INTEGER,'TYPE' TEXT );");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'NOTES_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public NotesBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, NotesBean entity) {
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
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        String media_url = entity.getMedia_url();
        if (media_url != null) {
            stmt.bindString(8, media_url);
        }
        String isxueshuo = entity.getIsxueshuo();
        if (isxueshuo != null) {
            stmt.bindString(9, isxueshuo);
        }
        String iszhuanshuo = entity.getIszhuanshuo();
        if (iszhuanshuo != null) {
            stmt.bindString(10, iszhuanshuo);
        }
        String isgaopinkaodian = entity.getIsgaopinkaodian();
        if (isgaopinkaodian != null) {
            stmt.bindString(11, isgaopinkaodian);
        }
        String is_practice = entity.getIs_practice();
        if (is_practice != null) {
            stmt.bindString(12, is_practice);
        }
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(13, unit);
        }
        Long part_id = entity.getPart_id();
        if (part_id != null) {
            stmt.bindLong(14, part_id.longValue());
        }
        Long part_parent_id = entity.getPart_parent_id();
        if (part_parent_id != null) {
            stmt.bindLong(15, part_parent_id.longValue());
        }
        Long part_num_am = entity.getPart_num_am();
        if (part_num_am != null) {
            stmt.bindLong(16, part_num_am.longValue());
        }
        Long part_num_pm = entity.getPart_num_pm();
        if (part_num_pm != null) {
            stmt.bindLong(17, part_num_pm.longValue());
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(18, type);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(NotesBean entity) {
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
    public Long updateKeyAfterInsert(NotesBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public NotesBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        Long lValueOf2 = cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6));
        int i7 = offset + 5;
        Long lValueOf3 = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = offset + 6;
        String string4 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = offset + 7;
        String string5 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = offset + 8;
        String string6 = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = offset + 9;
        String string7 = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = offset + 10;
        String string8 = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = offset + 11;
        String string9 = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = offset + 12;
        String string10 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = offset + 13;
        Long lValueOf4 = cursor.isNull(i15) ? null : Long.valueOf(cursor.getLong(i15));
        int i16 = offset + 14;
        Long lValueOf5 = cursor.isNull(i16) ? null : Long.valueOf(cursor.getLong(i16));
        int i17 = offset + 15;
        Long lValueOf6 = cursor.isNull(i17) ? null : Long.valueOf(cursor.getLong(i17));
        int i18 = offset + 16;
        Long lValueOf7 = cursor.isNull(i18) ? null : Long.valueOf(cursor.getLong(i18));
        int i19 = offset + 17;
        return new NotesBean(lValueOf, string, string2, string3, lValueOf2, lValueOf3, string4, string5, string6, string7, string8, string9, string10, lValueOf4, lValueOf5, lValueOf6, lValueOf7, cursor.isNull(i19) ? null : cursor.getString(i19));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, NotesBean entity, int offset) {
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
        entity.setContent(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setMedia_url(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setIsxueshuo(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setIszhuanshuo(cursor.isNull(i11) ? null : cursor.getString(i11));
        int i12 = offset + 10;
        entity.setIsgaopinkaodian(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setIs_practice(cursor.isNull(i13) ? null : cursor.getString(i13));
        int i14 = offset + 12;
        entity.setUnit(cursor.isNull(i14) ? null : cursor.getString(i14));
        int i15 = offset + 13;
        entity.setPart_id(cursor.isNull(i15) ? null : Long.valueOf(cursor.getLong(i15)));
        int i16 = offset + 14;
        entity.setPart_parent_id(cursor.isNull(i16) ? null : Long.valueOf(cursor.getLong(i16)));
        int i17 = offset + 15;
        entity.setPart_num_am(cursor.isNull(i17) ? null : Long.valueOf(cursor.getLong(i17)));
        int i18 = offset + 16;
        entity.setPart_num_pm(cursor.isNull(i18) ? null : Long.valueOf(cursor.getLong(i18)));
        int i19 = offset + 17;
        entity.setType(cursor.isNull(i19) ? null : cursor.getString(i19));
    }
}
