package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.mobile.auth.gatewayauth.Constant;
import com.uuzuche.lib_zxing.decoding.Intents;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class AnsweredQuestionBeanDao extends AbstractDao<AnsweredQuestionBean, Long> {
    public static final String TABLENAME = "ANSWERED_QUESTION_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Answer = new Property(1, String.class, "answer", false, "ANSWER");
        public static final Property Chapter_parent_id = new Property(2, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Chapter_id = new Property(3, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Is_right = new Property(4, String.class, "is_right", false, "IS_RIGHT");
        public static final Property Right_answer = new Property(5, String.class, "right_answer", false, "RIGHT_ANSWER");
        public static final Property Number = new Property(6, String.class, Constant.LOGIN_ACTIVITY_NUMBER, false, "NUMBER");
        public static final Property Year_num = new Property(7, Long.class, "year_num", false, "YEAR_NUM");
        public static final Property Parent_name = new Property(8, String.class, "parent_name", false, "PARENT_NAME");
        public static final Property Media_url = new Property(9, String.class, "media_url", false, "MEDIA_URL");
        public static final Property Year = new Property(10, String.class, "year", false, "YEAR");
        public static final Property Isxueshuo = new Property(11, String.class, "isxueshuo", false, "ISXUESHUO");
        public static final Property Iszhuanshuo = new Property(12, String.class, "iszhuanshuo", false, "ISZHUANSHUO");
        public static final Property Isgaopinkaodian = new Property(13, String.class, "isgaopinkaodian", false, "ISGAOPINKAODIAN");
        public static final Property Answer_year = new Property(14, String.class, "answer_year", false, "ANSWER_YEAR");
        public static final Property Answer_month = new Property(15, String.class, "answer_month", false, "ANSWER_MONTH");
        public static final Property Answer_day = new Property(16, String.class, "answer_day", false, "ANSWER_DAY");
        public static final Property Is_practice = new Property(17, String.class, "is_practice", false, "IS_PRACTICE");
        public static final Property Unit = new Property(18, String.class, "unit", false, "UNIT");
        public static final Property Part_id = new Property(19, Long.class, "part_id", false, "PART_ID");
        public static final Property Part_parent_id = new Property(20, Long.class, "part_parent_id", false, "PART_PARENT_ID");
        public static final Property Part_num_am = new Property(21, Long.class, "part_num_am", false, "PART_NUM_AM");
        public static final Property Part_num_pm = new Property(22, Long.class, "part_num_pm", false, "PART_NUM_PM");
        public static final Property Type = new Property(23, String.class, "type", false, Intents.WifiConnect.TYPE);
    }

    public AnsweredQuestionBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'ANSWERED_QUESTION_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'ANSWER' TEXT,'CHAPTER_PARENT_ID' TEXT,'CHAPTER_ID' TEXT,'IS_RIGHT' TEXT,'RIGHT_ANSWER' TEXT,'NUMBER' TEXT,'YEAR_NUM' INTEGER,'PARENT_NAME' TEXT,'MEDIA_URL' TEXT,'YEAR' TEXT,'ISXUESHUO' TEXT,'ISZHUANSHUO' TEXT,'ISGAOPINKAODIAN' TEXT,'ANSWER_YEAR' TEXT,'ANSWER_MONTH' TEXT,'ANSWER_DAY' TEXT,'IS_PRACTICE' TEXT,'UNIT' TEXT,'PART_ID' INTEGER,'PART_PARENT_ID' INTEGER,'PART_NUM_AM' INTEGER,'PART_NUM_PM' INTEGER,'TYPE' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'ANSWERED_QUESTION_BEAN'");
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

    public AnsweredQuestionBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, AnsweredQuestionBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(2, answer);
        }
        String chapter_parent_id = entity.getChapter_parent_id();
        if (chapter_parent_id != null) {
            stmt.bindString(3, chapter_parent_id);
        }
        String chapter_id = entity.getChapter_id();
        if (chapter_id != null) {
            stmt.bindString(4, chapter_id);
        }
        String is_right = entity.getIs_right();
        if (is_right != null) {
            stmt.bindString(5, is_right);
        }
        String right_answer = entity.getRight_answer();
        if (right_answer != null) {
            stmt.bindString(6, right_answer);
        }
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(7, number);
        }
        Long year_num = entity.getYear_num();
        if (year_num != null) {
            stmt.bindLong(8, year_num.longValue());
        }
        String parent_name = entity.getParent_name();
        if (parent_name != null) {
            stmt.bindString(9, parent_name);
        }
        String media_url = entity.getMedia_url();
        if (media_url != null) {
            stmt.bindString(10, media_url);
        }
        String year = entity.getYear();
        if (year != null) {
            stmt.bindString(11, year);
        }
        String isxueshuo = entity.getIsxueshuo();
        if (isxueshuo != null) {
            stmt.bindString(12, isxueshuo);
        }
        String iszhuanshuo = entity.getIszhuanshuo();
        if (iszhuanshuo != null) {
            stmt.bindString(13, iszhuanshuo);
        }
        String isgaopinkaodian = entity.getIsgaopinkaodian();
        if (isgaopinkaodian != null) {
            stmt.bindString(14, isgaopinkaodian);
        }
        String answer_year = entity.getAnswer_year();
        if (answer_year != null) {
            stmt.bindString(15, answer_year);
        }
        String answer_month = entity.getAnswer_month();
        if (answer_month != null) {
            stmt.bindString(16, answer_month);
        }
        String answer_day = entity.getAnswer_day();
        if (answer_day != null) {
            stmt.bindString(17, answer_day);
        }
        String is_practice = entity.getIs_practice();
        if (is_practice != null) {
            stmt.bindString(18, is_practice);
        }
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(19, unit);
        }
        Long part_id = entity.getPart_id();
        if (part_id != null) {
            stmt.bindLong(20, part_id.longValue());
        }
        Long part_parent_id = entity.getPart_parent_id();
        if (part_parent_id != null) {
            stmt.bindLong(21, part_parent_id.longValue());
        }
        Long part_num_am = entity.getPart_num_am();
        if (part_num_am != null) {
            stmt.bindLong(22, part_num_am.longValue());
        }
        Long part_num_pm = entity.getPart_num_pm();
        if (part_num_pm != null) {
            stmt.bindLong(23, part_num_pm.longValue());
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(24, type);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(AnsweredQuestionBean entity) {
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
    public Long updateKeyAfterInsert(AnsweredQuestionBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public AnsweredQuestionBean readEntity(Cursor cursor, int offset) {
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
        String string9 = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = offset + 11;
        String string10 = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = offset + 12;
        String string11 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = offset + 13;
        String string12 = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = offset + 14;
        String string13 = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = offset + 15;
        String string14 = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = offset + 16;
        String string15 = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = offset + 17;
        String string16 = cursor.isNull(i19) ? null : cursor.getString(i19);
        int i20 = offset + 18;
        String string17 = cursor.isNull(i20) ? null : cursor.getString(i20);
        int i21 = offset + 19;
        Long lValueOf3 = cursor.isNull(i21) ? null : Long.valueOf(cursor.getLong(i21));
        int i22 = offset + 20;
        Long lValueOf4 = cursor.isNull(i22) ? null : Long.valueOf(cursor.getLong(i22));
        int i23 = offset + 21;
        Long lValueOf5 = cursor.isNull(i23) ? null : Long.valueOf(cursor.getLong(i23));
        int i24 = offset + 22;
        Long lValueOf6 = cursor.isNull(i24) ? null : Long.valueOf(cursor.getLong(i24));
        int i25 = offset + 23;
        return new AnsweredQuestionBean(lValueOf, string, string2, string3, string4, string5, string6, lValueOf2, string7, string8, string9, string10, string11, string12, string13, string14, string15, string16, string17, lValueOf3, lValueOf4, lValueOf5, lValueOf6, cursor.isNull(i25) ? null : cursor.getString(i25));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, AnsweredQuestionBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setAnswer(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setChapter_parent_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setChapter_id(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setIs_right(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setRight_answer(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setNumber(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setYear_num(cursor.isNull(i9) ? null : Long.valueOf(cursor.getLong(i9)));
        int i10 = offset + 8;
        entity.setParent_name(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setMedia_url(cursor.isNull(i11) ? null : cursor.getString(i11));
        int i12 = offset + 10;
        entity.setYear(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setIsxueshuo(cursor.isNull(i13) ? null : cursor.getString(i13));
        int i14 = offset + 12;
        entity.setIszhuanshuo(cursor.isNull(i14) ? null : cursor.getString(i14));
        int i15 = offset + 13;
        entity.setIsgaopinkaodian(cursor.isNull(i15) ? null : cursor.getString(i15));
        int i16 = offset + 14;
        entity.setAnswer_year(cursor.isNull(i16) ? null : cursor.getString(i16));
        int i17 = offset + 15;
        entity.setAnswer_month(cursor.isNull(i17) ? null : cursor.getString(i17));
        int i18 = offset + 16;
        entity.setAnswer_day(cursor.isNull(i18) ? null : cursor.getString(i18));
        int i19 = offset + 17;
        entity.setIs_practice(cursor.isNull(i19) ? null : cursor.getString(i19));
        int i20 = offset + 18;
        entity.setUnit(cursor.isNull(i20) ? null : cursor.getString(i20));
        int i21 = offset + 19;
        entity.setPart_id(cursor.isNull(i21) ? null : Long.valueOf(cursor.getLong(i21)));
        int i22 = offset + 20;
        entity.setPart_parent_id(cursor.isNull(i22) ? null : Long.valueOf(cursor.getLong(i22)));
        int i23 = offset + 21;
        entity.setPart_num_am(cursor.isNull(i23) ? null : Long.valueOf(cursor.getLong(i23)));
        int i24 = offset + 22;
        entity.setPart_num_pm(cursor.isNull(i24) ? null : Long.valueOf(cursor.getLong(i24)));
        int i25 = offset + 23;
        entity.setType(cursor.isNull(i25) ? null : cursor.getString(i25));
    }
}
