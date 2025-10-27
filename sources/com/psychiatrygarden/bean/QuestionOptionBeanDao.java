package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.uuzuche.lib_zxing.decoding.Intents;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class QuestionOptionBeanDao extends AbstractDao<QuestionOptionBean, Void> {
    public static final String TABLENAME = "QUESTION_OPTION_BEAN";

    public static class Properties {
        public static final Property Question_id = new Property(0, Long.class, "question_id", false, "QUESTION_ID");
        public static final Property Img = new Property(1, String.class, "img", false, "IMG");
        public static final Property Value = new Property(2, String.class, "value", false, "VALUE");
        public static final Property Key = new Property(3, String.class, "key", false, "KEY");
        public static final Property Type = new Property(4, String.class, "type", false, Intents.WifiConnect.TYPE);
        public static final Property Isanswer = new Property(5, String.class, "isanswer", false, "ISANSWER");
    }

    public QuestionOptionBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'QUESTION_OPTION_BEAN' ('QUESTION_ID' INTEGER,'IMG' TEXT,'VALUE' TEXT,'KEY' TEXT,'TYPE' TEXT,'ISANSWER' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'QUESTION_OPTION_BEAN'");
        db.execSQL(sb.toString());
    }

    public void detachAll() {
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != 0) {
            identityScope.clear();
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void getKey(QuestionOptionBean entity) {
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
    public Void updateKeyAfterInsert(QuestionOptionBean entity, long rowId) {
        return null;
    }

    public QuestionOptionBeanDao(DaoConfig config, DaoSessionTiKu daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, QuestionOptionBean entity) {
        stmt.clearBindings();
        Long question_id = entity.getQuestion_id();
        if (question_id != null) {
            stmt.bindLong(1, question_id.longValue());
        }
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(2, img);
        }
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(3, value);
        }
        String key = entity.getKey();
        if (key != null) {
            stmt.bindString(4, key);
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
        String isanswer = entity.getIsanswer();
        if (isanswer != null) {
            stmt.bindString(6, isanswer);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public QuestionOptionBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        int i7 = offset + 5;
        return new QuestionOptionBean(lValueOf, string, string2, string3, cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, QuestionOptionBean entity, int offset) {
        int i2 = offset + 0;
        entity.setQuestion_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setImg(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setValue(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setKey(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setType(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setIsanswer(cursor.isNull(i7) ? null : cursor.getString(i7));
    }
}
