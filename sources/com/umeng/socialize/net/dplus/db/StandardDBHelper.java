package com.umeng.socialize.net.dplus.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;

/* loaded from: classes6.dex */
public class StandardDBHelper extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static Context f23800a;

    /* renamed from: b, reason: collision with root package name */
    private String f23801b;

    public StandardDBHelper(Context context) {
        super(context, DBConfig.DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.f23801b = null;
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f23801b = "create table if not exists stats (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL("create table if not exists stats (Id integer primary key,_json TEXT)");
        } catch (SQLException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f23801b = "create table if not exists s_e (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL("create table if not exists s_e (Id integer primary key,_json TEXT)");
        } catch (SQLException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
    }

    private void c(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f23801b = "create table if not exists auth (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL("create table if not exists auth (Id integer primary key,_json TEXT)");
        } catch (SQLException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
    }

    private void d(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f23801b = "create table if not exists userinfo (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL("create table if not exists userinfo (Id integer primary key,_json TEXT)");
        } catch (SQLException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
    }

    private void e(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f23801b = "create table if not exists dau (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL("create table if not exists dau (Id integer primary key,_json TEXT)");
        } catch (SQLException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        a(sQLiteDatabase);
        b(sQLiteDatabase);
        c(sQLiteDatabase);
        d(sQLiteDatabase);
        e(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
