package com.xiaomi.push.providers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.analytics.pro.am;
import com.xiaomi.channel.commonutils.logger.b;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes6.dex */
public class a extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static int f25529a = 1;

    /* renamed from: a, reason: collision with other field name */
    public static final Object f940a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f941a = {"package_name", "TEXT", "message_ts", " LONG DEFAULT 0 ", HttpHeaderValues.BYTES, " LONG DEFAULT 0 ", am.T, " INT DEFAULT -1 ", "rcv", " INT DEFAULT -1 ", "imsi", "TEXT"};

    public a(Context context) {
        super(context, "traffic.db", (SQLiteDatabase.CursorFactory) null, f25529a);
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE traffic(_id INTEGER  PRIMARY KEY ,");
        int i2 = 0;
        while (true) {
            String[] strArr = f941a;
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
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        synchronized (f940a) {
            try {
                a(sQLiteDatabase);
            } catch (SQLException e2) {
                b.a(e2);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
