package com.plv.beauty.api.resource.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/* loaded from: classes4.dex */
public class DatabaseManager {
    private static Context mContext;
    private static volatile DatabaseManager mDatabaseManager;
    SQLiteDatabase db;
    private final DatabaseHelper mDatabaseHelper;

    public static class DownloadResourceItem {
        public String hash;
        public String name;
        public String path;

        public DownloadResourceItem(String str, String str2, String str3) {
            this.name = str;
            this.hash = str2;
            this.path = str3;
        }
    }

    private DatabaseManager(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context.getApplicationContext(), "RemoteResConfig.db", null, 1);
        this.mDatabaseHelper = databaseHelper;
        this.db = databaseHelper.getWritableDatabase();
    }

    public static DatabaseManager getInstance() {
        if (mDatabaseManager == null) {
            if (mContext == null) {
                throw new IllegalStateException("must call init(Context) first");
            }
            synchronized (DatabaseManager.class) {
                if (mDatabaseManager == null) {
                    mDatabaseManager = new DatabaseManager(mContext);
                    mContext = null;
                }
            }
        }
        return mDatabaseManager;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public void addResourceItem(String str, String str2, String str3) {
        this.mDatabaseHelper.updateItem(this.db, str, str2, str3);
    }

    public void deleteResourceItem(String str) {
        this.mDatabaseHelper.deleteDownloadResourceItem(this.db, str);
    }

    public void removeAllResourceItem() {
        this.mDatabaseHelper.removeAllResource(this.db);
    }

    public synchronized DownloadResourceItem resourceItem(String str) {
        return this.mDatabaseHelper.queryDownloadResourceItem(this.db, str);
    }
}
