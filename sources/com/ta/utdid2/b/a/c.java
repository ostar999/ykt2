package com.ta.utdid2.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.a.e.h;
import com.ta.utdid2.a.a.f;
import java.io.File;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private SharedPreferences f17242a;

    /* renamed from: a, reason: collision with other field name */
    private b f85a;

    public c(Context context, String str, String str2) {
        String externalStorageState = null;
        this.f17242a = null;
        this.f85a = null;
        if (context == null) {
            return;
        }
        this.f17242a = context.getSharedPreferences(str2, 0);
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e2) {
            h.a("PersistentConfiguration", e2, new Object[0]);
        }
        boolean z2 = !f.b(externalStorageState) && (externalStorageState.equals("mounted") || externalStorageState.equals("mounted_ro"));
        h.m109a("PersistentConfiguration", "PersistentConfiguration canRead", Boolean.valueOf(z2));
        if (!z2 || f.b(str)) {
            return;
        }
        try {
            d dVarA = a(str);
            if (dVarA != null) {
                this.f85a = dVarA.a(str2, 0);
            }
        } catch (Exception unused) {
        }
    }

    public int a() {
        SharedPreferences sharedPreferences = this.f17242a;
        int i2 = sharedPreferences != null ? sharedPreferences.getInt("type", 0) : 0;
        h.m109a("PersistentConfiguration", "getTypeFromSp type", Integer.valueOf(i2));
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean d() {
        /*
            r9 = this;
            android.content.SharedPreferences r0 = r9.f17242a
            r1 = 1
            java.lang.String r2 = "copySPToMySP"
            java.lang.String r3 = "PersistentConfiguration"
            r4 = 0
            if (r0 == 0) goto L40
            com.ta.utdid2.b.a.b r0 = r9.f85a
            if (r0 == 0) goto L40
            java.lang.Object[] r0 = new java.lang.Object[r1]
            r0[r4] = r2
            com.ta.a.e.h.m109a(r3, r0)
            com.ta.utdid2.b.a.b r0 = r9.f85a
            com.ta.utdid2.b.a.b$a r0 = r0.a()
            if (r0 == 0) goto L40
            r0.b()
            android.content.SharedPreferences r5 = r9.f17242a
            java.lang.String r6 = ""
            java.lang.String r7 = "UTDID2"
            java.lang.String r5 = r5.getString(r7, r6)
            r0.a(r7, r5)
            android.content.SharedPreferences r5 = r9.f17242a
            r6 = 0
            java.lang.String r8 = "t2"
            long r5 = r5.getLong(r8, r6)
            r0.a(r8, r5)
            boolean r0 = r0.commit()     // Catch: java.lang.Exception -> L40
            goto L41
        L40:
            r0 = r4
        L41:
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r4] = r2
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)
            r5[r1] = r2
            com.ta.a.e.h.m109a(r3, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.c.d():boolean");
    }

    public void e(String str) {
        if (this.f17242a != null) {
            h.m109a("PersistentConfiguration", "updateUtdidToSp utdid", str);
            SharedPreferences.Editor editorEdit = this.f17242a.edit();
            editorEdit.putString("UTDID2", str);
            if (this.f17242a.getLong("t2", 0L) == 0) {
                editorEdit.putLong("t2", System.currentTimeMillis());
            }
            try {
                editorEdit.commit();
            } catch (Exception unused) {
            }
        }
    }

    public String o() {
        SharedPreferences sharedPreferences = this.f17242a;
        String string = sharedPreferences != null ? sharedPreferences.getString("UTDID2", "") : "";
        h.m109a("PersistentConfiguration", "getUtdidFromSp utdid", string);
        return string;
    }

    public String p() {
        b bVar = this.f85a;
        String string = bVar != null ? bVar.getString("UTDID2", "") : "";
        h.m109a("PersistentConfiguration", "getUtdidFromMySp utdid", string);
        return string;
    }

    private d a(String str) {
        File fileM110a = m110a(str);
        if (fileM110a != null) {
            return new d(fileM110a.getAbsolutePath());
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    private File m110a(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public void a(String str, int i2) {
        if (this.f17242a != null) {
            h.m109a("PersistentConfiguration", "writeUtdidToSp utdid", str);
            SharedPreferences.Editor editorEdit = this.f17242a.edit();
            editorEdit.putString("UTDID2", str);
            editorEdit.putInt("type", i2);
            if (this.f17242a.getLong("t2", 0L) == 0) {
                editorEdit.putLong("t2", System.currentTimeMillis());
            }
            try {
                editorEdit.commit();
            } catch (Exception unused) {
            }
        }
    }

    public boolean a(int i2) {
        boolean zCommit;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editorEdit;
        if (this.f85a == null || (sharedPreferences = this.f17242a) == null || (editorEdit = sharedPreferences.edit()) == null) {
            zCommit = false;
        } else {
            editorEdit.clear();
            editorEdit.putString("UTDID2", this.f85a.getString("UTDID2", ""));
            editorEdit.putInt("type", i2);
            editorEdit.putLong("t2", this.f85a.getLong("t2", 0L));
            try {
                zCommit = editorEdit.commit();
            } catch (Exception unused) {
            }
        }
        h.m109a("PersistentConfiguration", "copyMySPToSP", Boolean.valueOf(zCommit));
        return zCommit;
    }
}
