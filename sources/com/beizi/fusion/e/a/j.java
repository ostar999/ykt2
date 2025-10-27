package com.beizi.fusion.e.a;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes2.dex */
public class j {

    /* renamed from: b, reason: collision with root package name */
    private Context f5034b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f5035c = false;

    /* renamed from: a, reason: collision with root package name */
    String f5033a = null;

    public j(Context context) {
        this.f5034b = context;
    }

    public String a() {
        Cursor cursorQuery = this.f5034b.getContentResolver().query(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
        if (cursorQuery != null) {
            string = cursorQuery.moveToNext() ? cursorQuery.getString(cursorQuery.getColumnIndex("value")) : null;
            cursorQuery.close();
        }
        return string;
    }
}
