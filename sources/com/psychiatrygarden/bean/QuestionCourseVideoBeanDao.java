package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionCourseVideoBeanDao extends AbstractDao<QuestionCourseVideoBean, Long> {
    public static final String TABLENAME = "QUESTION_COURSE_VIDEO_BEAN";

    public static class Properties {
        public static final Property Media_id = new Property(0, Long.class, "media_id", true, "MEDIA_ID");
        public static final Property Question_id = new Property(1, Long.class, "question_id", false, "QUESTION_ID");
        public static final Property Media_url = new Property(2, String.class, "media_url", false, "MEDIA_URL");
        public static final Property Media_img = new Property(3, String.class, "media_img", false, "MEDIA_IMG");
    }

    public QuestionCourseVideoBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_COURSE_VIDEO_BEAN' ('MEDIA_ID' INTEGER PRIMARY KEY ,'QUESTION_ID' INTEGER,'MEDIA_URL' TEXT,'MEDIA_IMG' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_COURSE_VIDEO_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public QuestionCourseVideoBeanDao(DaoConfig config, DaoSessionTiKu daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionCourseVideoBean entity) {
        stmt.clearBindings();
        Long media_id = entity.getMedia_id();
        if (media_id != null) {
            stmt.bindLong(1, media_id.longValue());
        }
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(2, question_id.longValue());
        }
        String media_url = entity.getMedia_url();
        if (media_url != null) {
            stmt.bindString(3, media_url);
        }
        String media_img = entity.getMedia_img();
        if (media_img != null) {
            stmt.bindString(4, media_img);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionCourseVideoBean entity) {
        if (entity != null) {
            return entity.getMedia_id();
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
    public Long updateKeyAfterInsert(QuestionCourseVideoBean entity, long rowId) {
        entity.setMedia_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionCourseVideoBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        int i3 = offset + 1;
        int i4 = offset + 2;
        int i5 = offset + 3;
        return new QuestionCourseVideoBean(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)), cursor.isNull(i4) ? null : cursor.getString(i4), cursor.isNull(i5) ? null : cursor.getString(i5));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionCourseVideoBean entity, int offset) {
        int i2 = offset + 0;
        entity.setMedia_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setQuestion_id(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = offset + 2;
        entity.setMedia_url(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setMedia_img(cursor.isNull(i5) ? null : cursor.getString(i5));
    }
}
