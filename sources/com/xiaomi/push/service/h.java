package com.xiaomi.push.service;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class h extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static h f25683a;

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f1057a = {"name", "TEXT NOT NULL", "appId", "INTEGER NOT NULL", "package_name", "TEXT NOT NULL", "create_time", "INTEGER NOT NULL", "type", "TEXT NOT NULL", "center_longtitude", "TEXT", "center_lantitude", "TEXT", "circle_radius", "REAL", "polygon_point", "TEXT", "coordinate_provider", "TEXT NOT NULL", "current_status", "TEXT NOT NULL"};

    /* renamed from: b, reason: collision with root package name */
    private static final String[] f25684b = {MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID, "TEXT NOT NULL", "geo_id", "TEXT NOT NULL", "content", "BLOB NOT NULL", "action", "INTEGER NOT NULL", "deadline", "INTEGER NOT NULL"};

    /* renamed from: a, reason: collision with other field name */
    private SQLiteDatabase f1058a;

    /* renamed from: a, reason: collision with other field name */
    public final Object f1059a;

    /* renamed from: a, reason: collision with other field name */
    private final String f1060a;

    /* renamed from: a, reason: collision with other field name */
    private AtomicInteger f1061a;

    public h(Context context) {
        super(context, "geofencing.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.f1060a = "GeoFenceDatabaseHelper.";
        this.f1059a = new Object();
        this.f1061a = new AtomicInteger();
    }

    public static h a(Context context) {
        if (f25683a == null) {
            synchronized (h.class) {
                if (f25683a == null) {
                    f25683a = new h(context);
                }
            }
        }
        return f25683a;
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            StringBuilder sb = new StringBuilder("CREATE TABLE geofence(id TEXT PRIMARY KEY ,");
            int i2 = 0;
            while (true) {
                String[] strArr = f1057a;
                if (i2 >= strArr.length - 1) {
                    sb.append(");");
                    sQLiteDatabase.execSQL(sb.toString());
                    return;
                }
                if (i2 != 0) {
                    sb.append(",");
                }
                sb.append(strArr[i2]);
                sb.append(" ");
                sb.append(strArr[i2 + 1]);
                i2 += 2;
            }
        } catch (SQLException e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            StringBuilder sb = new StringBuilder("CREATE TABLE geoMessage(");
            int i2 = 0;
            while (true) {
                String[] strArr = f25684b;
                if (i2 >= strArr.length - 1) {
                    sb.append(",PRIMARY KEY(message_id,geo_id));");
                    sQLiteDatabase.execSQL(sb.toString());
                    return;
                }
                if (i2 != 0) {
                    sb.append(",");
                }
                sb.append(strArr[i2]);
                sb.append(" ");
                sb.append(f1057a[i2 + 1]);
                i2 += 2;
            }
        } catch (SQLException e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
        }
    }

    public synchronized SQLiteDatabase a() {
        if (this.f1061a.incrementAndGet() == 1) {
            this.f1058a = getWritableDatabase();
        }
        return this.f1058a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m743a() {
        if (this.f1061a.decrementAndGet() == 0) {
            this.f1058a.close();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        synchronized (this.f1059a) {
            try {
                a(sQLiteDatabase);
                b(sQLiteDatabase);
                com.xiaomi.channel.commonutils.logger.b.c("GeoFenceDatabaseHelper. create tables");
            } catch (SQLException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
