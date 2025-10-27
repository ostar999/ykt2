package com.psychiatrygarden.bean;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/* loaded from: classes5.dex */
public class DaoMasterBei extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            DaoMasterBei.dropAllTables(db, true);
            onCreate(db);
        }
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) throws SQLException {
            Log.i("greenDAO", "Creating tables for schema version 1");
            DaoMasterBei.createAllTables(db, false);
        }
    }

    public DaoMasterBei(SQLiteDatabase db) {
        super(db, 1);
        registerDaoClass(QuestionKuangBeiInfoBeanDao.class);
    }

    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        QuestionKuangBeiInfoBeanDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) throws SQLException {
        QuestionKuangBeiInfoBeanDao.dropTable(db, ifExists);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSessionBei newSession() {
        return new DaoSessionBei(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSessionBei newSession(IdentityScopeType type) {
        return new DaoSessionBei(this.db, type, this.daoConfigMap);
    }
}
