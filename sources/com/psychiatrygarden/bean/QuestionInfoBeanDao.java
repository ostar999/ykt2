package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.mobile.auth.gatewayauth.Constant;
import com.tencent.open.SocialConstants;
import com.uuzuche.lib_zxing.decoding.Intents;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionInfoBeanDao extends AbstractDao<QuestionInfoBean, Long> {
    public static final String TABLENAME = "QUESTION_INFO_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Chapter_parent_id = new Property(1, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Chapter_id = new Property(2, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Title = new Property(3, String.class, "title", false, "TITLE");
        public static final Property Title_img = new Property(4, String.class, "title_img", false, "TITLE_IMG");
        public static final Property S_num = new Property(5, Long.class, "s_num", false, "S_NUM");
        public static final Property S_num_xue = new Property(6, Long.class, "s_num_xue", false, "S_NUM_XUE");
        public static final Property Number = new Property(7, String.class, Constant.LOGIN_ACTIVITY_NUMBER, false, "NUMBER");
        public static final Property Year = new Property(8, String.class, "year", false, "YEAR");
        public static final Property Number_number = new Property(9, Long.class, "number_number", false, "NUMBER_NUMBER");
        public static final Property Number_type = new Property(10, String.class, "number_type", false, "NUMBER_TYPE");
        public static final Property Restore = new Property(11, String.class, RequestParameters.X_OSS_RESTORE, false, "RESTORE");
        public static final Property Explain = new Property(12, String.class, "explain", false, "EXPLAIN");
        public static final Property Type = new Property(13, String.class, "type", false, Intents.WifiConnect.TYPE);
        public static final Property Answer = new Property(14, String.class, "answer", false, "ANSWER");
        public static final Property Media_url = new Property(15, String.class, "media_url", false, "MEDIA_URL");
        public static final Property Media_img = new Property(16, String.class, "media_img", false, "MEDIA_IMG");
        public static final Property Media_id = new Property(17, String.class, "media_id", false, "MEDIA_ID");
        public static final Property Subject_name = new Property(18, String.class, "subject_name", false, "SUBJECT_NAME");
        public static final Property Xuehsuodagang = new Property(19, String.class, "xuehsuodagang", false, "XUEHSUODAGANG");
        public static final Property Zhuanshuodagang = new Property(20, String.class, "zhuanshuodagang", false, "ZHUANSHUODAGANG");
        public static final Property Isxueshuo = new Property(21, String.class, "isxueshuo", false, "ISXUESHUO");
        public static final Property Iszhuanshuo = new Property(22, String.class, "iszhuanshuo", false, "ISZHUANSHUO");
        public static final Property Isgaopinkaodian = new Property(23, String.class, "isgaopinkaodian", false, "ISGAOPINKAODIAN");
        public static final Property Unit = new Property(24, String.class, "unit", false, "UNIT");
        public static final Property Question_type = new Property(25, String.class, "question_type", false, "QUESTION_TYPE");
        public static final Property Is_practice = new Property(26, String.class, "is_practice", false, "IS_PRACTICE");
        public static final Property Restore_img = new Property(27, String.class, "restore_img", false, "RESTORE_IMG");
        public static final Property Explain_img = new Property(28, String.class, "explain_img", false, "EXPLAIN_IMG");
        public static final Property Part_id = new Property(29, Long.class, "part_id", false, "PART_ID");
        public static final Property Part_parent_id = new Property(30, Long.class, "part_parent_id", false, "PART_PARENT_ID");
        public static final Property Part_num_am = new Property(31, Long.class, "part_num_am", false, "PART_NUM_AM");
        public static final Property Part_num_pm = new Property(32, Long.class, "part_num_pm", false, "PART_NUM_PM");
        public static final Property Source = new Property(33, String.class, SocialConstants.PARAM_SOURCE, false, "SOURCE");
        public static final Property Is_hard = new Property(34, String.class, "is_hard", false, "IS_HARD");
        public static final Property Is_example = new Property(35, String.class, "is_example", false, "IS_EXAMPLE");
        public static final Property Source_filter = new Property(36, String.class, "source_filter", false, "SOURCE_FILTER");
    }

    public QuestionInfoBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_INFO_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'CHAPTER_PARENT_ID' TEXT,'CHAPTER_ID' TEXT,'TITLE' TEXT,'TITLE_IMG' TEXT,'S_NUM' INTEGER,'S_NUM_XUE' INTEGER,'NUMBER' TEXT,'YEAR' TEXT,'NUMBER_NUMBER' INTEGER,'NUMBER_TYPE' TEXT,'RESTORE' TEXT,'EXPLAIN' TEXT,'TYPE' TEXT,'ANSWER' TEXT,'MEDIA_URL' TEXT,'MEDIA_IMG' TEXT,'MEDIA_ID' TEXT,'SUBJECT_NAME' TEXT,'XUEHSUODAGANG' TEXT,'ZHUANSHUODAGANG' TEXT,'ISXUESHUO' TEXT,'ISZHUANSHUO' TEXT,'ISGAOPINKAODIAN' TEXT,'UNIT' TEXT,'QUESTION_TYPE' TEXT,'IS_PRACTICE' TEXT,'RESTORE_IMG' TEXT,'EXPLAIN_IMG' TEXT,'PART_ID' INTEGER,'PART_PARENT_ID' INTEGER,'PART_NUM_AM' INTEGER,'PART_NUM_PM' INTEGER,'SOURCE' TEXT,'IS_HARD' TEXT,'IS_EXAMPLE' TEXT,'SOURCE_FILTER' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_INFO_BEAN'");
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

    public QuestionInfoBeanDao(DaoConfig config, DaoSessionTiKu daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionInfoBean entity) {
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
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
        String title_img = entity.getTitle_img();
        if (title_img != null) {
            stmt.bindString(5, title_img);
        }
        Long s_num = entity.getS_num();
        if (s_num != null) {
            stmt.bindLong(6, s_num.longValue());
        }
        Long s_num_xue = entity.getS_num_xue();
        if (s_num_xue != null) {
            stmt.bindLong(7, s_num_xue.longValue());
        }
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(8, number);
        }
        String year = entity.getYear();
        if (year != null) {
            stmt.bindString(9, year);
        }
        Long number_number = entity.getNumber_number();
        if (number_number != null) {
            stmt.bindLong(10, number_number.longValue());
        }
        String number_type = entity.getNumber_type();
        if (number_type != null) {
            stmt.bindString(11, number_type);
        }
        String restore = entity.getRestore();
        if (restore != null) {
            stmt.bindString(12, restore);
        }
        String explain = entity.getExplain();
        if (explain != null) {
            stmt.bindString(13, explain);
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(14, type);
        }
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(15, answer);
        }
        String media_url = entity.getMedia_url();
        if (media_url != null) {
            stmt.bindString(16, media_url);
        }
        String media_img = entity.getMedia_img();
        if (media_img != null) {
            stmt.bindString(17, media_img);
        }
        String media_id = entity.getMedia_id();
        if (media_id != null) {
            stmt.bindString(18, media_id);
        }
        String subject_name = entity.getSubject_name();
        if (subject_name != null) {
            stmt.bindString(19, subject_name);
        }
        String xuehsuodagang = entity.getXuehsuodagang();
        if (xuehsuodagang != null) {
            stmt.bindString(20, xuehsuodagang);
        }
        String zhuanshuodagang = entity.getZhuanshuodagang();
        if (zhuanshuodagang != null) {
            stmt.bindString(21, zhuanshuodagang);
        }
        String isxueshuo = entity.getIsxueshuo();
        if (isxueshuo != null) {
            stmt.bindString(22, isxueshuo);
        }
        String iszhuanshuo = entity.getIszhuanshuo();
        if (iszhuanshuo != null) {
            stmt.bindString(23, iszhuanshuo);
        }
        String isgaopinkaodian = entity.getIsgaopinkaodian();
        if (isgaopinkaodian != null) {
            stmt.bindString(24, isgaopinkaodian);
        }
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(25, unit);
        }
        String question_type = entity.getQuestion_type();
        if (question_type != null) {
            stmt.bindString(26, question_type);
        }
        String is_practice = entity.getIs_practice();
        if (is_practice != null) {
            stmt.bindString(27, is_practice);
        }
        String restore_img = entity.getRestore_img();
        if (restore_img != null) {
            stmt.bindString(28, restore_img);
        }
        String explain_img = entity.getExplain_img();
        if (explain_img != null) {
            stmt.bindString(29, explain_img);
        }
        Long part_id = entity.getPart_id();
        if (part_id != null) {
            stmt.bindLong(30, part_id.longValue());
        }
        Long part_parent_id = entity.getPart_parent_id();
        if (part_parent_id != null) {
            stmt.bindLong(31, part_parent_id.longValue());
        }
        Long part_num_am = entity.getPart_num_am();
        if (part_num_am != null) {
            stmt.bindLong(32, part_num_am.longValue());
        }
        Long part_num_pm = entity.getPart_num_pm();
        if (part_num_pm != null) {
            stmt.bindLong(33, part_num_pm.longValue());
        }
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(34, source);
        }
        String is_hard = entity.getIs_hard();
        if (is_hard != null) {
            stmt.bindString(35, is_hard);
        }
        String is_example = entity.getIs_example();
        if (is_example != null) {
            stmt.bindString(36, is_example);
        }
        String source_filter = entity.getSource_filter();
        if (source_filter != null) {
            stmt.bindString(37, source_filter);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionInfoBean entity) {
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
    public Long updateKeyAfterInsert(QuestionInfoBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionInfoBean readEntity(Cursor cursor, int offset) {
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
        Long lValueOf2 = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = offset + 6;
        Long lValueOf3 = cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8));
        int i9 = offset + 7;
        String string5 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = offset + 8;
        String string6 = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = offset + 9;
        Long lValueOf4 = cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11));
        int i12 = offset + 10;
        String string7 = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = offset + 11;
        String string8 = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = offset + 12;
        String string9 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = offset + 13;
        String string10 = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = offset + 14;
        String string11 = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = offset + 15;
        String string12 = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = offset + 16;
        String string13 = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = offset + 17;
        String string14 = cursor.isNull(i19) ? null : cursor.getString(i19);
        int i20 = offset + 18;
        String string15 = cursor.isNull(i20) ? null : cursor.getString(i20);
        int i21 = offset + 19;
        String string16 = cursor.isNull(i21) ? null : cursor.getString(i21);
        int i22 = offset + 20;
        String string17 = cursor.isNull(i22) ? null : cursor.getString(i22);
        int i23 = offset + 21;
        String string18 = cursor.isNull(i23) ? null : cursor.getString(i23);
        int i24 = offset + 22;
        String string19 = cursor.isNull(i24) ? null : cursor.getString(i24);
        int i25 = offset + 23;
        String string20 = cursor.isNull(i25) ? null : cursor.getString(i25);
        int i26 = offset + 24;
        String string21 = cursor.isNull(i26) ? null : cursor.getString(i26);
        int i27 = offset + 25;
        String string22 = cursor.isNull(i27) ? null : cursor.getString(i27);
        int i28 = offset + 26;
        String string23 = cursor.isNull(i28) ? null : cursor.getString(i28);
        int i29 = offset + 27;
        String string24 = cursor.isNull(i29) ? null : cursor.getString(i29);
        int i30 = offset + 28;
        String string25 = cursor.isNull(i30) ? null : cursor.getString(i30);
        int i31 = offset + 29;
        Long lValueOf5 = cursor.isNull(i31) ? null : Long.valueOf(cursor.getLong(i31));
        int i32 = offset + 30;
        Long lValueOf6 = cursor.isNull(i32) ? null : Long.valueOf(cursor.getLong(i32));
        int i33 = offset + 31;
        Long lValueOf7 = cursor.isNull(i33) ? null : Long.valueOf(cursor.getLong(i33));
        int i34 = offset + 32;
        Long lValueOf8 = cursor.isNull(i34) ? null : Long.valueOf(cursor.getLong(i34));
        int i35 = offset + 33;
        String string26 = cursor.isNull(i35) ? null : cursor.getString(i35);
        int i36 = offset + 34;
        String string27 = cursor.isNull(i36) ? null : cursor.getString(i36);
        int i37 = offset + 35;
        String string28 = cursor.isNull(i37) ? null : cursor.getString(i37);
        int i38 = offset + 36;
        return new QuestionInfoBean(lValueOf, string, string2, string3, string4, lValueOf2, lValueOf3, string5, string6, lValueOf4, string7, string8, string9, string10, string11, string12, string13, string14, string15, string16, string17, string18, string19, string20, string21, string22, string23, string24, string25, lValueOf5, lValueOf6, lValueOf7, lValueOf8, string26, string27, string28, cursor.isNull(i38) ? null : cursor.getString(i38));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionInfoBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setChapter_parent_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setChapter_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setTitle(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setTitle_img(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setS_num(cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7)));
        int i8 = offset + 6;
        entity.setS_num_xue(cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8)));
        int i9 = offset + 7;
        entity.setNumber(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setYear(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setNumber_number(cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11)));
        int i12 = offset + 10;
        entity.setNumber_type(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setRestore(cursor.isNull(i13) ? null : cursor.getString(i13));
        int i14 = offset + 12;
        entity.setExplain(cursor.isNull(i14) ? null : cursor.getString(i14));
        int i15 = offset + 13;
        entity.setType(cursor.isNull(i15) ? null : cursor.getString(i15));
        int i16 = offset + 14;
        entity.setAnswer(cursor.isNull(i16) ? null : cursor.getString(i16));
        int i17 = offset + 15;
        entity.setMedia_url(cursor.isNull(i17) ? null : cursor.getString(i17));
        int i18 = offset + 16;
        entity.setMedia_img(cursor.isNull(i18) ? null : cursor.getString(i18));
        int i19 = offset + 17;
        entity.setMedia_id(cursor.isNull(i19) ? null : cursor.getString(i19));
        int i20 = offset + 18;
        entity.setSubject_name(cursor.isNull(i20) ? null : cursor.getString(i20));
        int i21 = offset + 19;
        entity.setXuehsuodagang(cursor.isNull(i21) ? null : cursor.getString(i21));
        int i22 = offset + 20;
        entity.setZhuanshuodagang(cursor.isNull(i22) ? null : cursor.getString(i22));
        int i23 = offset + 21;
        entity.setIsxueshuo(cursor.isNull(i23) ? null : cursor.getString(i23));
        int i24 = offset + 22;
        entity.setIszhuanshuo(cursor.isNull(i24) ? null : cursor.getString(i24));
        int i25 = offset + 23;
        entity.setIsgaopinkaodian(cursor.isNull(i25) ? null : cursor.getString(i25));
        int i26 = offset + 24;
        entity.setUnit(cursor.isNull(i26) ? null : cursor.getString(i26));
        int i27 = offset + 25;
        entity.setQuestion_type(cursor.isNull(i27) ? null : cursor.getString(i27));
        int i28 = offset + 26;
        entity.setIs_practice(cursor.isNull(i28) ? null : cursor.getString(i28));
        int i29 = offset + 27;
        entity.setRestore_img(cursor.isNull(i29) ? null : cursor.getString(i29));
        int i30 = offset + 28;
        entity.setExplain_img(cursor.isNull(i30) ? null : cursor.getString(i30));
        int i31 = offset + 29;
        entity.setPart_id(cursor.isNull(i31) ? null : Long.valueOf(cursor.getLong(i31)));
        int i32 = offset + 30;
        entity.setPart_parent_id(cursor.isNull(i32) ? null : Long.valueOf(cursor.getLong(i32)));
        int i33 = offset + 31;
        entity.setPart_num_am(cursor.isNull(i33) ? null : Long.valueOf(cursor.getLong(i33)));
        int i34 = offset + 32;
        entity.setPart_num_pm(cursor.isNull(i34) ? null : Long.valueOf(cursor.getLong(i34)));
        int i35 = offset + 33;
        entity.setSource(cursor.isNull(i35) ? null : cursor.getString(i35));
        int i36 = offset + 34;
        entity.setIs_hard(cursor.isNull(i36) ? null : cursor.getString(i36));
        int i37 = offset + 35;
        entity.setIs_example(cursor.isNull(i37) ? null : cursor.getString(i37));
        int i38 = offset + 36;
        entity.setSource_filter(cursor.isNull(i38) ? null : cursor.getString(i38));
    }
}
