package com.psychiatrygarden.bean;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.psychiatrygarden.db.MigrationHelper;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/* loaded from: classes5.dex */
public class DaoMasterTiKu extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
            SectionPartBeanDao.createTable(db, true);
            QuestionInfoBeanDao.createTable(db, true);
            QuestionOptionBeanDao.createTable(db, true);
            SectionBeanDao.createTable(db, true);
            QuestionCourseVideoBeanDao.createTable(db, true);
            MigrationHelper.getInstance().migrate(2, db, SectionPartBeanDao.class, QuestionInfoBeanDao.class, QuestionOptionBeanDao.class, SectionBeanDao.class, QuestionCourseVideoBeanDao.class);
        }
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 3);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            DaoMasterTiKu.createAllTables(db, false);
        }
    }

    public DaoMasterTiKu(SQLiteDatabase db) {
        super(db, 3);
        registerDaoClass(QuestionInfoBeanDao.class);
        registerDaoClass(QuestionOptionBeanDao.class);
        registerDaoClass(SectionBeanDao.class);
        registerDaoClass(QuestionCourseVideoBeanDao.class);
        registerDaoClass(SectionPartBeanDao.class);
    }

    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        QuestionInfoBeanDao.createTable(db, ifNotExists);
        QuestionOptionBeanDao.createTable(db, ifNotExists);
        SectionBeanDao.createTable(db, ifNotExists);
        QuestionCourseVideoBeanDao.createTable(db, ifNotExists);
        SectionPartBeanDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        QuestionInfoBeanDao.dropTable(db, ifExists);
        QuestionOptionBeanDao.dropTable(db, ifExists);
        SectionBeanDao.dropTable(db, ifExists);
        QuestionCourseVideoBeanDao.dropTable(db, ifExists);
        SectionPartBeanDao.dropTable(db, ifExists);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSessionTiKu newSession() {
        return new DaoSessionTiKu(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSessionTiKu newSession(IdentityScopeType type) {
        return new DaoSessionTiKu(this.db, type, this.daoConfigMap);
    }
}
