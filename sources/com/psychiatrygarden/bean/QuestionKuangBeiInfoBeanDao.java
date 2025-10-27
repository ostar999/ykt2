package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.psychiatrygarden.utils.Constants;
import com.tencent.open.SocialConstants;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionKuangBeiInfoBeanDao extends AbstractDao<QuestionKuangBeiInfoBean, Long> {
    public static final String TABLENAME = "QUESTION_KUANG_BEI_INFO_BEAN";

    public static class Properties {
        public static final Property Recite_id = new Property(0, Long.class, "recite_id", true, "RECITE_ID");
        public static final Property Title = new Property(1, String.class, "title", false, "TITLE");
        public static final Property Title_title = new Property(2, String.class, "title_title", false, "TITLE_TITLE");
        public static final Property Level = new Property(3, String.class, DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, false, "LEVEL");
        public static final Property Book = new Property(4, String.class, "book", false, "BOOK");
        public static final Property Page = new Property(5, String.class, Constants.PARAMS_CONSTANTS.PARAMS_PAGE, false, "PAGE");
        public static final Property Chapter_id = new Property(6, String.class, "chapter_id", false, "CHAPTER_ID");
        public static final Property Chapter_parent_id = new Property(7, String.class, "chapter_parent_id", false, "CHAPTER_PARENT_ID");
        public static final Property Source = new Property(8, String.class, SocialConstants.PARAM_SOURCE, false, "SOURCE");
        public static final Property Number = new Property(9, Long.class, Constant.LOGIN_ACTIVITY_NUMBER, false, "NUMBER");
    }

    public QuestionKuangBeiInfoBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_KUANG_BEI_INFO_BEAN' ('RECITE_ID' INTEGER PRIMARY KEY ,'TITLE' TEXT,'TITLE_TITLE' TEXT,'LEVEL' TEXT,'BOOK' TEXT,'PAGE' TEXT,'CHAPTER_ID' TEXT,'CHAPTER_PARENT_ID' TEXT,'SOURCE' TEXT,'NUMBER' INTEGER);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_KUANG_BEI_INFO_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public QuestionKuangBeiInfoBeanDao(DaoConfig config, DaoSessionBei daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionKuangBeiInfoBean entity) {
        stmt.clearBindings();
        Long recite_id = entity.getRecite_id();
        if (recite_id != null) {
            stmt.bindLong(1, recite_id.longValue());
        }
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
        String title_title = entity.getTitle_title();
        if (title_title != null) {
            stmt.bindString(3, title_title);
        }
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(4, level);
        }
        String book = entity.getBook();
        if (book != null) {
            stmt.bindString(5, book);
        }
        String page = entity.getPage();
        if (page != null) {
            stmt.bindString(6, page);
        }
        String chapter_id = entity.getChapter_id();
        if (chapter_id != null) {
            stmt.bindString(7, chapter_id);
        }
        String chapter_parent_id = entity.getChapter_parent_id();
        if (chapter_parent_id != null) {
            stmt.bindString(8, chapter_parent_id);
        }
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(9, source);
        }
        Long number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(10, number.longValue());
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(QuestionKuangBeiInfoBean entity) {
        if (entity != null) {
            return entity.getRecite_id();
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
    public Long updateKeyAfterInsert(QuestionKuangBeiInfoBean entity, long rowId) {
        entity.setRecite_id(Long.valueOf(rowId));
        return Long.valueOf(rowId);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionKuangBeiInfoBean readEntity(Cursor cursor, int offset) {
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
        String string7 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = offset + 8;
        int i11 = offset + 9;
        return new QuestionKuangBeiInfoBean(lValueOf, string, string2, string3, string4, string5, string6, string7, cursor.isNull(i10) ? null : cursor.getString(i10), cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11)));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionKuangBeiInfoBean entity, int offset) {
        int i2 = offset + 0;
        entity.setRecite_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setTitle(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setTitle_title(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setLevel(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setBook(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setPage(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setChapter_id(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setChapter_parent_id(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setSource(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setNumber(cursor.isNull(i11) ? null : Long.valueOf(cursor.getLong(i11)));
    }
}
