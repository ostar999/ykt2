package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class SubmitAnsweredQuestionBeanDao extends AbstractDao<SubmitAnsweredQuestionBean, Void> {
    public static final String TABLENAME = "SUBMIT_ANSWERED_QUESTION_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", false, "QUESTION_ID");
        public static final Property Answer = new Property(1, String.class, "answer", false, "ANSWER");
        public static final Property Is_right = new Property(2, String.class, "is_right", false, "IS_RIGHT");
        public static final Property App_id = new Property(3, String.class, "app_id", false, "APP_ID");
        public static final Property Question_app_id = new Property(4, String.class, "question_app_id", false, "QUESTION_APP_ID");
    }

    public SubmitAnsweredQuestionBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'SUBMIT_ANSWERED_QUESTION_BEAN' ('QUESTION_ID' INTEGER,'ANSWER' TEXT,'IS_RIGHT' TEXT,'APP_ID' TEXT,'QUESTION_APP_ID' TEXT UNIQUE );");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'SUBMIT_ANSWERED_QUESTION_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void getKey(SubmitAnsweredQuestionBean entity) {
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
    public Void updateKeyAfterInsert(SubmitAnsweredQuestionBean entity, long rowId) {
        return null;
    }

    public SubmitAnsweredQuestionBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, SubmitAnsweredQuestionBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(2, answer);
        }
        String is_right = entity.getIs_right();
        if (is_right != null) {
            stmt.bindString(3, is_right);
        }
        String app_id = entity.getApp_id();
        if (app_id != null) {
            stmt.bindString(4, app_id);
        }
        String question_app_id = entity.getQuestion_app_id();
        if (question_app_id != null) {
            stmt.bindString(5, question_app_id);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public SubmitAnsweredQuestionBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        int i6 = offset + 4;
        return new SubmitAnsweredQuestionBean(lValueOf, string, string2, cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : cursor.getString(i6));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, SubmitAnsweredQuestionBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setAnswer(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setIs_right(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setApp_id(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setQuestion_app_id(cursor.isNull(i6) ? null : cursor.getString(i6));
    }
}
