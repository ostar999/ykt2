package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class CourseVideoLocationBeanDao extends AbstractDao<CourseVideoLocationBean, Long> {
    public static final String TABLENAME = "COURSE_VIDEO_LOCATION_BEAN";

    public static class Properties {
        public static final Property Media_id = new Property(0, Long.class, "media_id", false, "MEDIA_ID");
        public static final Property Id = new Property(1, String.class, "id", false, "ID");
        public static final Property Title = new Property(2, String.class, "title", false, "TITLE");
        public static final Property Parent_id = new Property(3, String.class, "parent_id", false, "PARENT_ID");
        public static final Property Video_count = new Property(4, String.class, "video_count", false, "VIDEO_COUNT");
        public static final Property Sort = new Property(5, Long.class, "sort", false, "SORT");
        public static final Property Is_select = new Property(6, String.class, "is_select", false, "IS_SELECT");
        public static final Property Is_have = new Property(7, String.class, "is_have", false, "IS_HAVE");
    }

    public CourseVideoLocationBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'COURSE_VIDEO_LOCATION_BEAN' ('MEDIA_ID' INTEGER,'ID' TEXT,'TITLE' TEXT,'PARENT_ID' TEXT,'VIDEO_COUNT' TEXT,'SORT' INTEGER,'IS_SELECT' TEXT,'IS_HAVE' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'COURSE_VIDEO_LOCATION_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(CourseVideoLocationBean entity) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public Long readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long updateKeyAfterInsert(CourseVideoLocationBean entity, long rowId) {
        return null;
    }

    public CourseVideoLocationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CourseVideoLocationBean entity) {
        stmt.clearBindings();
        Long media_id = entity.getMedia_id();
        if (media_id != null) {
            stmt.bindLong(1, media_id.longValue());
        }
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
        String parent_id = entity.getParent_id();
        if (parent_id != null) {
            stmt.bindString(4, parent_id);
        }
        String video_count = entity.getVideo_count();
        if (video_count != null) {
            stmt.bindString(5, video_count);
        }
        Long sort = entity.getSort();
        if (sort != null) {
            stmt.bindLong(6, sort.longValue());
        }
        String is_select = entity.getIs_select();
        if (is_select != null) {
            stmt.bindString(7, is_select);
        }
        String is_have = entity.getIs_have();
        if (is_have != null) {
            stmt.bindString(8, is_have);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CourseVideoLocationBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 1;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = offset + 2;
        String string2 = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 3;
        String string3 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 4;
        String string4 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 5;
        Long lValueOf = cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6));
        int i7 = offset + 6;
        int i8 = offset + 7;
        return new CourseVideoLocationBean(string, string2, string3, string4, lValueOf, cursor.isNull(i7) ? null : cursor.getString(i7), cursor.isNull(i8) ? null : cursor.getString(i8));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CourseVideoLocationBean entity, int offset) {
        int i2 = offset + 1;
        entity.setId(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = offset + 2;
        entity.setTitle(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 3;
        entity.setParent_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 4;
        entity.setVideo_count(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 5;
        entity.setSort(cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6)));
        int i7 = offset + 6;
        entity.setIs_select(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 7;
        entity.setIs_have(cursor.isNull(i8) ? null : cursor.getString(i8));
    }
}
