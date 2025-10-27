package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionVideoLocationBeanDao extends AbstractDao<QuestionVideoLocationBean, Long> {
    public static final String TABLENAME = "QUESTION_VIDEO_LOCATION_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", true, "QUESTION_ID");
        public static final Property Video_location = new Property(1, Long.class, "video_location", false, "VIDEO_LOCATION");
    }

    public QuestionVideoLocationBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_VIDEO_LOCATION_BEAN' ('QUESTION_ID' INTEGER PRIMARY KEY ,'VIDEO_LOCATION' INTEGER);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_VIDEO_LOCATION_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public QuestionVideoLocationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionVideoLocationBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        Long video_location = entity.getVideo_location();
        if (video_location != null) {
            stmt.bindLong(2, video_location.longValue());
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionVideoLocationBean entity) {
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
    public Long updateKeyAfterInsert(QuestionVideoLocationBean entity, long rowId) {
        entity.setQuestion_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionVideoLocationBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        return new QuestionVideoLocationBean(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionVideoLocationBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setVideo_location(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
    }
}
