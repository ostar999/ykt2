package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.util.Objects;

/* loaded from: classes2.dex */
class i implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5297a;

    public i(Context context) {
        this.f5297a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5297a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().resolveContentProvider("com.meizu.flyme.openidsdk", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5297a == null || cVar == null) {
            return;
        }
        try {
            Cursor cursorQuery = this.f5297a.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{"oaid"}, null);
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
