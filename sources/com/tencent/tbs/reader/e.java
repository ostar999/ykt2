package com.tencent.tbs.reader;

import android.content.Context;
import com.tencent.tbs.reader.ITbsReader;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public ITbsReader f22285a = null;

    /* renamed from: b, reason: collision with root package name */
    public d f22286b = null;

    public boolean a(Context context, ITbsReaderCallback iTbsReaderCallback) {
        c cVarD = c.d();
        Context applicationContext = context.getApplicationContext();
        ITbsReaderEntry iTbsReaderEntry = cVarD.f22276a;
        ITbsReader iTbsReader = iTbsReaderEntry != null ? (ITbsReader) iTbsReaderEntry.createTbsReader(applicationContext, 3, iTbsReaderCallback) : null;
        this.f22285a = iTbsReader;
        d dVar = new d(iTbsReader);
        this.f22286b = dVar;
        ITbsReader.IReaderCore iReaderCore = dVar.f22284a;
        return iReaderCore != null ? iReaderCore.init(context) : false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int a(android.content.Context r4, android.os.Bundle r5, android.widget.FrameLayout r6) {
        /*
            r3 = this;
            r0 = 0
            if (r5 != 0) goto L4
            goto L1b
        L4:
            java.lang.String r1 = "filePath"
            java.lang.String r1 = r5.getString(r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L11
            goto L1b
        L11:
            java.lang.String r2 = "65b46dc5-21ad-4098-bf53-9b2fc9edf259.xlsx"
            int r1 = r1.lastIndexOf(r2)
            if (r1 <= 0) goto L1b
            r1 = 1
            goto L1c
        L1b:
            r1 = r0
        L1c:
            if (r1 == 0) goto L35
            com.tencent.tbs.reader.c r1 = com.tencent.tbs.reader.c.d()
            android.view.View r1 = r1.b(r4)
            if (r1 == 0) goto L36
            android.app.AlertDialog$Builder r2 = new android.app.AlertDialog$Builder     // Catch: java.lang.Throwable -> L36
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L36
            android.app.AlertDialog$Builder r2 = r2.setView(r1)     // Catch: java.lang.Throwable -> L36
            r2.show()     // Catch: java.lang.Throwable -> L36
            return r0
        L35:
            r1 = 0
        L36:
            com.tencent.tbs.reader.d r0 = r3.f22286b
            com.tencent.tbs.reader.ITbsReader$IReaderCore r0 = r0.f22284a
            java.lang.String r2 = "TbsReaderCore"
            if (r0 == 0) goto L54
            if (r5 != 0) goto L41
            goto L54
        L41:
            int r4 = r0.openFile(r4, r5, r6, r1)
            if (r4 == 0) goto L4c
            java.lang.String r5 = "OpenFile failed!"
            android.util.Log.e(r2, r5)
        L4c:
            com.tencent.tbs.reader.c r5 = com.tencent.tbs.reader.c.d()
            r5.c()
            goto L5a
        L54:
            java.lang.String r4 = "init failed!"
            android.util.Log.e(r2, r4)
            r4 = -1
        L5a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.reader.e.a(android.content.Context, android.os.Bundle, android.widget.FrameLayout):int");
    }
}
