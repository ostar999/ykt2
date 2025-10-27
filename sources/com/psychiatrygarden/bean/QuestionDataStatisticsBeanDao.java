package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.psychiatrygarden.utils.CommonParameter;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionDataStatisticsBeanDao extends AbstractDao<QuestionDataStatisticsBean, Long> {
    public static final String TABLENAME = "QUESTION_DATA_STATISTICS_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property All_right_count = new Property(1, Integer.class, "all_right_count", false, "ALL_RIGHT_COUNT");
        public static final Property All_wrong_count = new Property(2, Integer.class, "all_wrong_count", false, "ALL_WRONG_COUNT");
        public static final Property Comment_count = new Property(3, Integer.class, "comment_count", false, CommonParameter.COMMENT_COUNT);
        public static final Property Right_count = new Property(4, Integer.class, "right_count", false, "RIGHT_COUNT");
        public static final Property Wrong_count = new Property(5, Integer.class, "wrong_count", false, "WRONG_COUNT");
        public static final Property Year_num = new Property(6, Long.class, "year_num", false, "YEAR_NUM");
        public static final Property Collection = new Property(7, Long.class, "collection", false, "COLLECTION");
        public static final Property Is_comment = new Property(8, String.class, "is_comment", false, "IS_COMMENT");
        public static final Property Is_praise = new Property(9, String.class, "is_praise", false, "IS_PRAISR");
    }

    public QuestionDataStatisticsBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_DATA_STATISTICS_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'ALL_RIGHT_COUNT' INTEGER,'ALL_WRONG_COUNT' INTEGER,'COMMENT_COUNT' INTEGER,'RIGHT_COUNT' INTEGER,'WRONG_COUNT' INTEGER,'YEAR_NUM' INTEGER,'COLLECTION' INTEGER,'IS_COMMENT' TEXT,'IS_PRAISR' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_DATA_STATISTICS_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public QuestionDataStatisticsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionDataStatisticsBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        if (entity.getAll_right_count() != null) {
            stmt.bindLong(2, r0.intValue());
        }
        if (entity.getAll_wrong_count() != null) {
            stmt.bindLong(3, r0.intValue());
        }
        if (entity.getComment_count() != null) {
            stmt.bindLong(4, r0.intValue());
        }
        if (entity.getRight_count() != null) {
            stmt.bindLong(5, r0.intValue());
        }
        if (entity.getWrong_count() != null) {
            stmt.bindLong(6, r0.intValue());
        }
        Long year_num = entity.getYear_num();
        if (year_num != null) {
            stmt.bindLong(7, year_num.longValue());
        }
        Long collection = entity.getCollection();
        if (collection != null) {
            stmt.bindLong(8, collection.longValue());
        }
        String is_comment = entity.getIs_comment();
        if (is_comment != null) {
            stmt.bindString(9, is_comment);
        }
        String is_praise = entity.getIs_praise();
        if (is_praise != null) {
            stmt.bindString(10, is_praise);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionDataStatisticsBean entity) {
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
    public Long updateKeyAfterInsert(QuestionDataStatisticsBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionDataStatisticsBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        Integer numValueOf = cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3));
        int i4 = offset + 2;
        Integer numValueOf2 = cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4));
        int i5 = offset + 3;
        Integer numValueOf3 = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = offset + 4;
        Integer numValueOf4 = cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6));
        int i7 = offset + 5;
        Integer numValueOf5 = cursor.isNull(i7) ? null : Integer.valueOf(cursor.getInt(i7));
        int i8 = offset + 6;
        Long lValueOf2 = cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8));
        int i9 = offset + 7;
        Long lValueOf3 = cursor.isNull(i9) ? null : Long.valueOf(cursor.getLong(i9));
        int i10 = offset + 8;
        int i11 = offset + 9;
        return new QuestionDataStatisticsBean(lValueOf, numValueOf, numValueOf2, numValueOf3, numValueOf4, numValueOf5, lValueOf2, lValueOf3, cursor.isNull(i10) ? null : cursor.getString(i10), cursor.isNull(i11) ? null : cursor.getString(i11));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionDataStatisticsBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setAll_right_count(cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3)));
        int i4 = offset + 2;
        entity.setAll_wrong_count(cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4)));
        int i5 = offset + 3;
        entity.setComment_count(cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5)));
        int i6 = offset + 4;
        entity.setRight_count(cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6)));
        int i7 = offset + 5;
        entity.setWrong_count(cursor.isNull(i7) ? null : Integer.valueOf(cursor.getInt(i7)));
        int i8 = offset + 6;
        entity.setYear_num(cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8)));
        int i9 = offset + 7;
        entity.setCollection(cursor.isNull(i9) ? null : Long.valueOf(cursor.getLong(i9)));
        int i10 = offset + 8;
        entity.setIs_comment(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setIs_praise(cursor.isNull(i11) ? null : cursor.getString(i11));
    }
}
