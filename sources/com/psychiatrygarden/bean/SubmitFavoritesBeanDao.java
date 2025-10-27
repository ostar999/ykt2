package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class SubmitFavoritesBeanDao extends AbstractDao<SubmitFavoritesBean, Void> {
    public static final String TABLENAME = "SUBMIT_FAVORITES_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", false, "QUESTION_ID");
        public static final Property App_id = new Property(1, String.class, "app_id", false, "APP_ID");
        public static final Property Question_app_id = new Property(2, String.class, "question_app_id", false, "QUESTION_APP_ID");
    }

    public SubmitFavoritesBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'SUBMIT_FAVORITES_BEAN' ('QUESTION_ID' INTEGER,'APP_ID' TEXT,'QUESTION_APP_ID' TEXT UNIQUE );");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'SUBMIT_FAVORITES_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void getKey(SubmitFavoritesBean entity) {
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
    public Void updateKeyAfterInsert(SubmitFavoritesBean entity, long rowId) {
        return null;
    }

    public SubmitFavoritesBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, SubmitFavoritesBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        String app_id = entity.getApp_id();
        if (app_id != null) {
            stmt.bindString(2, app_id);
        }
        String question_app_id = entity.getQuestion_app_id();
        if (question_app_id != null) {
            stmt.bindString(3, question_app_id);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public SubmitFavoritesBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        int i4 = offset + 2;
        return new SubmitFavoritesBean(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : cursor.getString(i4));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, SubmitFavoritesBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setApp_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setQuestion_app_id(cursor.isNull(i4) ? null : cursor.getString(i4));
    }
}
