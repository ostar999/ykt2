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
public class QuestionCacheVideoBeanDao extends AbstractDao<QuestionCacheVideoBean, Long> {
    public static final String TABLENAME = "QUESTION_CACHE_VIDEO_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, String.class, "id", false, "ID");
        public static final Property Title = new Property(1, String.class, "title", false, "TITLE");
        public static final Property Discription = new Property(2, String.class, "discription", false, "DISCRIPTION");
        public static final Property Thumb = new Property(3, String.class, "thumb", false, "THUMB");
        public static final Property Category_id = new Property(4, String.class, "category_id", false, "CATEGORY_ID");
        public static final Property Chapter_id = new Property(5, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Vid = new Property(6, String.class, "vid", false, "VID");
        public static final Property Ctime = new Property(7, String.class, "ctime", false, "CTIME");
        public static final Property Is_del = new Property(8, String.class, "is_del", false, "IS_DEL");
        public static final Property Sort = new Property(9, Long.class, "sort", false, "SORT");
        public static final Property Duration = new Property(10, String.class, "duration", false, "DURATION");
        public static final Property Share_thumb = new Property(11, String.class, "share_thumb", false, "SHARE_THUMB");
        public static final Property Lecturer = new Property(12, String.class, "lecturer", false, "LECTURER");
        public static final Property Comment_count = new Property(13, Long.class, "comment_count", false, CommonParameter.COMMENT_COUNT);
        public static final Property Count = new Property(14, String.class, "count", false, "COUNT");
        public static final Property Is_see = new Property(15, String.class, "is_see", false, "IS_SEE");
        public static final Property MQuality = new Property(16, String.class, "mQuality", false, "MQUALITY");
        public static final Property MFormat = new Property(17, String.class, "mFormat", false, "MFORMAT");
        public static final Property IsEncripted = new Property(18, Long.class, "isEncripted", false, "ISENCRIPTED");
        public static final Property Download_state = new Property(19, String.class, "Download_state", false, "DOWNLOAD_STATE");
        public static final Property Service_id = new Property(20, String.class, "service_id", false, "SERVICE_ID");
        public static final Property Goods_id = new Property(21, String.class, "goods_id", false, "GOODS_ID");
        public static final Property Is_select = new Property(22, String.class, "is_select", false, "IS_SELECT");
        public static final Property Savepath = new Property(23, String.class, "savepath", false, "SAVEPATH");
        public static final Property Videsize = new Property(24, String.class, "savepath", false, "VIDESIZE");
    }

    public QuestionCacheVideoBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_CACHE_VIDEO_BEAN' ('ID' TEXT,'TITLE' TEXT,'DISCRIPTION' TEXT,'THUMB' TEXT,'CATEGORY_ID' TEXT,'CHAPTER_ID' TEXT,'VID' TEXT  UNIQUE,'CTIME' TEXT,'IS_DEL' TEXT,'SORT' INTEGER,'DURATION' TEXT,'SHARE_THUMB' TEXT,'LECTURER' TEXT,'COMMENT_COUNT' TEXT,'COUNT' TEXT,'IS_SEE' TEXT,'MQUALITY' TEXT,'MFORMAT' TEXT,'ISENCRIPTED' INTEGER,'DOWNLOAD_STATE' TEXT,'SERVICE_ID' TEXT,'GOODS_ID' TEXT,'IS_SELECT' TEXT,'SAVEPATH' TEXT,'VIDESIZE' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_CACHE_VIDEO_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionCacheVideoBean entity) {
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
    public Long updateKeyAfterInsert(QuestionCacheVideoBean entity, long rowId) {
        return null;
    }

    public QuestionCacheVideoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionCacheVideoBean entity) {
        stmt.clearBindings();
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
        String discription = entity.getDiscription();
        if (discription != null) {
            stmt.bindString(3, discription);
        }
        String thumb = entity.getThumb();
        if (thumb != null) {
            stmt.bindString(4, thumb);
        }
        String category_id = entity.getCategory_id();
        if (category_id != null) {
            stmt.bindString(5, category_id);
        }
        String chapter_id = entity.getChapter_id();
        if (chapter_id != null) {
            stmt.bindString(6, chapter_id);
        }
        String vid = entity.getVid();
        if (vid != null) {
            stmt.bindString(7, vid);
        }
        String ctime = entity.getCtime();
        if (ctime != null) {
            stmt.bindString(8, ctime);
        }
        String is_del = entity.getIs_del();
        if (is_del != null) {
            stmt.bindString(9, is_del);
        }
        Long sort = entity.getSort();
        if (sort != null) {
            stmt.bindLong(10, sort.longValue());
        }
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(11, duration);
        }
        String share_thumb = entity.getShare_thumb();
        if (share_thumb != null) {
            stmt.bindString(12, share_thumb);
        }
        String lecturer = entity.getLecturer();
        if (lecturer != null) {
            stmt.bindString(13, lecturer);
        }
        String comment_count = entity.getComment_count();
        if (comment_count != null) {
            stmt.bindString(14, comment_count);
        }
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(15, count);
        }
        String is_see = entity.getIs_see();
        if (is_see != null) {
            stmt.bindString(16, is_see);
        }
        String str = entity.getmQuality();
        if (str != null) {
            stmt.bindString(17, str);
        }
        String str2 = entity.getmFormat();
        if (str2 != null) {
            stmt.bindString(18, str2);
        }
        Long isEncripted = entity.getIsEncripted();
        if (isEncripted != null) {
            stmt.bindLong(19, isEncripted.longValue());
        }
        String download_state = entity.getDownload_state();
        if (download_state != null) {
            stmt.bindString(20, download_state);
        }
        String service_id = entity.getService_id();
        if (service_id != null) {
            stmt.bindString(21, service_id);
        }
        String goods_id = entity.getGoods_id();
        if (goods_id != null) {
            stmt.bindString(22, goods_id);
        }
        String is_select = entity.getIs_select();
        if (is_select != null) {
            stmt.bindString(23, is_select);
        }
        String savepath = entity.getSavepath();
        if (savepath != null) {
            stmt.bindString(24, savepath);
        }
        String videsize = entity.getVidesize();
        if (videsize != null) {
            stmt.bindString(25, videsize);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionCacheVideoBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = offset + 1;
        String string2 = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string3 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string4 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        String string5 = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = offset + 5;
        String string6 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = offset + 6;
        String string7 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = offset + 7;
        String string8 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = offset + 8;
        String string9 = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = offset + 9;
        Long lValueOf = cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11));
        int i12 = offset + 10;
        String string10 = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = offset + 11;
        String string11 = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = offset + 12;
        String string12 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = offset + 13;
        String string13 = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = offset + 14;
        String string14 = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = offset + 15;
        String string15 = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = offset + 16;
        String string16 = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = offset + 17;
        String string17 = cursor.isNull(i19) ? null : cursor.getString(i19);
        int i20 = offset + 18;
        Long lValueOf2 = cursor.isNull(i20) ? null : Long.valueOf(cursor.getLong(i20));
        int i21 = offset + 19;
        String string18 = cursor.isNull(i21) ? null : cursor.getString(i21);
        int i22 = offset + 20;
        String string19 = cursor.isNull(i22) ? null : cursor.getString(i22);
        int i23 = offset + 21;
        String string20 = cursor.isNull(i23) ? null : cursor.getString(i23);
        int i24 = offset + 22;
        String string21 = cursor.isNull(i24) ? null : cursor.getString(i24);
        int i25 = offset + 23;
        String string22 = cursor.isNull(i25) ? null : cursor.getString(i25);
        int i26 = offset + 24;
        return new QuestionCacheVideoBean(string, string2, string3, string4, string5, string6, string7, string8, string9, lValueOf, string10, string11, string12, string13, string14, string15, string16, string17, lValueOf2, string18, string19, string20, string21, string22, cursor.isNull(i26) ? null : cursor.getString(i26));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionCacheVideoBean entity, int offset) {
        int i2 = offset + 0;
        entity.setId(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = offset + 1;
        entity.setTitle(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setDiscription(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setThumb(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setCategory_id(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setChapter_id(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setVid(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setCtime(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setIs_del(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setSort(cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11)));
        int i12 = offset + 10;
        entity.setDuration(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setShare_thumb(cursor.isNull(i13) ? null : cursor.getString(i13));
        int i14 = offset + 12;
        entity.setLecturer(cursor.isNull(i14) ? null : cursor.getString(i14));
        int i15 = offset + 13;
        entity.setComment_count(cursor.isNull(i15) ? null : cursor.getString(i15));
        int i16 = offset + 14;
        entity.setCount(cursor.isNull(i16) ? null : cursor.getString(i16));
        int i17 = offset + 15;
        entity.setIs_see(cursor.isNull(i17) ? null : cursor.getString(i17));
        int i18 = offset + 16;
        entity.setmQuality(cursor.isNull(i18) ? null : cursor.getString(i18));
        int i19 = offset + 17;
        entity.setmFormat(cursor.isNull(i19) ? null : cursor.getString(i19));
        int i20 = offset + 18;
        entity.setIsEncripted(cursor.isNull(i20) ? null : Long.valueOf(cursor.getLong(i20)));
        int i21 = offset + 19;
        entity.setDownload_state(cursor.isNull(i21) ? null : cursor.getString(i21));
        int i22 = offset + 20;
        entity.setService_id(cursor.isNull(i22) ? null : cursor.getString(i22));
        int i23 = offset + 21;
        entity.setGoods_id(cursor.isNull(i23) ? null : cursor.getString(i23));
        int i24 = offset + 22;
        entity.setIs_select(cursor.isNull(i24) ? null : cursor.getString(i24));
        int i25 = offset + 23;
        entity.setSavepath(cursor.isNull(i25) ? null : cursor.getString(i25));
        int i26 = offset + 24;
        entity.setVidesize(cursor.isNull(i26) ? null : cursor.getString(i26));
    }
}
