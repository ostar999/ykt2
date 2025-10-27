package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionSelfStatisticsBeanDao extends AbstractDao<QuestionSelfStatisticsBean, Long> {
    public static final String TABLENAME = "QUESTION_SELF_STATISTICS_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Selfanswernum = new Property(1, Integer.class, "selfanswernum", false, "SELFANSWERNUM");
        public static final Property Selfwrongnum = new Property(2, Integer.class, "selfwrongnum", false, "SELFWRONGNUM");
    }

    public QuestionSelfStatisticsBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_SELF_STATISTICS_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'SELFANSWERNUM' INTEGER,'SELFWRONGNUM' INTEGER);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_SELF_STATISTICS_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public QuestionSelfStatisticsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionSelfStatisticsBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        if (entity.getSelfanswernum() != null) {
            stmt.bindLong(2, r0.intValue());
        }
        if (entity.getSelfwrongnum() != null) {
            stmt.bindLong(3, r6.intValue());
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionSelfStatisticsBean entity) {
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
    public Long updateKeyAfterInsert(QuestionSelfStatisticsBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionSelfStatisticsBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        int i4 = offset + 2;
        return new QuestionSelfStatisticsBean(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3)), cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4)));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionSelfStatisticsBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setSelfanswernum(cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3)));
        int i4 = offset + 2;
        entity.setSelfwrongnum(cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4)));
    }
}
