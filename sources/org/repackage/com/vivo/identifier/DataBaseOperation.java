package org.repackage.com.vivo.identifier;

import android.content.Context;

/* loaded from: classes9.dex */
public class DataBaseOperation {

    /* renamed from: a, reason: collision with root package name */
    private static final String f28032a = "VMS_IDLG_SDK_DB";

    /* renamed from: b, reason: collision with root package name */
    private static final String f28033b = "content://com.vivo.vms.IdProvider/IdentifierId";

    /* renamed from: c, reason: collision with root package name */
    private static final String f28034c = "value";

    /* renamed from: d, reason: collision with root package name */
    private static final String f28035d = "OAID";

    /* renamed from: e, reason: collision with root package name */
    private static final String f28036e = "AAID";

    /* renamed from: f, reason: collision with root package name */
    private static final String f28037f = "VAID";

    /* renamed from: g, reason: collision with root package name */
    private static final String f28038g = "OAIDSTATUS";

    /* renamed from: h, reason: collision with root package name */
    private static final int f28039h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static final int f28040i = 1;

    /* renamed from: j, reason: collision with root package name */
    private static final int f28041j = 2;

    /* renamed from: k, reason: collision with root package name */
    private static final int f28042k = 4;

    /* renamed from: l, reason: collision with root package name */
    private Context f28043l;

    public DataBaseOperation(Context context) {
        this.f28043l = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(int r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 == 0) goto L41
            r1 = 1
            if (r8 == r1) goto L2b
            r1 = 2
            if (r8 == r1) goto L15
            r9 = 4
            if (r8 == r9) goto Le
            r2 = r0
            goto L48
        Le:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAIDSTATUS"
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L47
        L15:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/AAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L47
        L2b:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/VAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L47
        L41:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
            android.net.Uri r8 = android.net.Uri.parse(r8)
        L47:
            r2 = r8
        L48:
            android.content.Context r8 = r7.f28043l
            android.content.ContentResolver r1 = r8.getContentResolver()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)
            if (r8 == 0) goto L6d
            boolean r9 = r8.moveToNext()
            if (r9 == 0) goto L69
            java.lang.String r9 = "value"
            int r9 = r8.getColumnIndex(r9)
            java.lang.String r9 = r8.getString(r9)
            r0 = r9
        L69:
            r8.close()
            goto L74
        L6d:
            java.lang.String r8 = "VMS_IDLG_SDK_DB"
            java.lang.String r9 = "return cursor is null,return"
            android.util.Log.d(r8, r9)
        L74:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.repackage.com.vivo.identifier.DataBaseOperation.a(int, java.lang.String):java.lang.String");
    }
}
