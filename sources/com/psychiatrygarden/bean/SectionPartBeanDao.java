package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.internal.DaoConfig;

/* loaded from: classes5.dex */
public class SectionPartBeanDao extends AbstractDao<SectionPartBean, Long> {
    public static final String TABLENAME = "SECTION_PART_BEAN";

    public static class Properties {
        public static final Property Part_id = new Property(0, Long.class, "part_id", true, "PART_ID");
        public static final Property Parent_id = new Property(1, String.class, "parent_id", false, "PARENT_ID");
        public static final Property Title = new Property(2, String.class, "title", false, "TITLE");
        public static final Property Sort = new Property(3, Long.class, "sort", false, "SORT");
        public static final Property Am = new Property(4, String.class, CommonNetImpl.AM, false, "AM");
        public static final Property Pm = new Property(5, String.class, "pm", false, "PM");
    }

    public SectionPartBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'SECTION_PART_BEAN' ('PART_ID' INTEGER PRIMARY KEY ,'PARENT_ID' TEXT,'TITLE' TEXT,'SORT' INTEGER,'AM' TEXT,'PM' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'SECTION_PART_BEAN'");
        db.execSQL(sb.toString());
    }

    public void detachAll() {
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != 0) {
            identityScope.clear();
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    public SectionPartBeanDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement sqLiteStatement, SectionPartBean sectionPartBean) {
        sqLiteStatement.clearBindings();
        Long part_id = sectionPartBean.getPart_id();
        if (part_id != null) {
            sqLiteStatement.bindLong(1, part_id.longValue());
        }
        String parent_id = sectionPartBean.getParent_id();
        if (parent_id != null) {
            sqLiteStatement.bindString(2, parent_id);
        }
        String title = sectionPartBean.getTitle();
        if (title != null) {
            sqLiteStatement.bindString(3, title);
        }
        Long sort = sectionPartBean.getSort();
        if (sort != null) {
            sqLiteStatement.bindLong(4, sort.longValue());
        }
        String am = sectionPartBean.getAm();
        if (am != null) {
            sqLiteStatement.bindString(5, am);
        }
        String pm = sectionPartBean.getPm();
        if (pm != null) {
            sqLiteStatement.bindString(6, pm);
        }
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long getKey(SectionPartBean sectionPartBean) {
        if (sectionPartBean != null) {
            return sectionPartBean.getPart_id();
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public Long readKey(Cursor cursor, int i2) {
        int i3 = i2 + 0;
        if (cursor.isNull(i3)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i3));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Long updateKeyAfterInsert(SectionPartBean sectionPartBean, long l2) {
        sectionPartBean.setPart_id(Long.valueOf(l2));
        return Long.valueOf(l2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public SectionPartBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = offset + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        Long lValueOf2 = cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5));
        int i6 = offset + 4;
        int i7 = offset + 5;
        return new SectionPartBean(lValueOf, string, string2, lValueOf2, cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, SectionPartBean entity, int offset) {
        int i2 = offset + 0;
        entity.setPart_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = offset + 1;
        entity.setParent_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setTitle(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setSort(cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5)));
        int i6 = offset + 4;
        entity.setAm(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setPm(cursor.isNull(i7) ? null : cursor.getString(i7));
    }
}
