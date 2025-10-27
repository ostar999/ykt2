package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import java.util.Objects;

/* loaded from: classes2.dex */
class p implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5310a;

    public p(Context context) {
        this.f5310a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return com.beizi.fusion.sm.a.g.a("persist.sys.identifierid.supported", "0").equals("1");
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5310a == null || cVar == null) {
            return;
        }
        try {
            Cursor cursorQuery = this.f5310a.getContentResolver().query(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
            try {
                Objects.requireNonNull(cursorQuery);
                cursorQuery.moveToFirst();
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                if (string != null && string.length() != 0) {
                    com.beizi.fusion.sm.a.f.a("OAID query success: " + string);
                    cVar.a(string);
                    cursorQuery.close();
                    return;
                }
                throw new com.beizi.fusion.sm.a.e("OAID query failed");
            } finally {
            }
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            cVar.a(e2);
        }
    }
}
