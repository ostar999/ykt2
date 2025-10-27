package com.beizi.fusion.e.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import com.beizi.fusion.e.a.b;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private Context f5015a;

    public e(Context context) {
        this.f5015a = context;
    }

    public void a(b.a aVar) throws PackageManager.NameNotFoundException {
        try {
            this.f5015a.getPackageManager().getPackageInfo("com.meizu.flyme.openidsdk", 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            Cursor cursorQuery = this.f5015a.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{"oaid"}, null);
            String strA = a(cursorQuery);
            if (aVar != null) {
                aVar.a(strA);
            }
            cursorQuery.close();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private String a(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("value");
        String string = columnIndex > 0 ? cursor.getString(columnIndex) : null;
        int columnIndex2 = cursor.getColumnIndex("code");
        if (columnIndex2 > 0) {
            cursor.getInt(columnIndex2);
        }
        int columnIndex3 = cursor.getColumnIndex("expired");
        if (columnIndex3 > 0) {
            cursor.getLong(columnIndex3);
        }
        return string;
    }
}
