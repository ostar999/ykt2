package com.ta.utdid2.device;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.ta.a.e.h;
import com.ta.utdid2.device.c;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f17254a = new a();

    /* renamed from: c, reason: collision with root package name */
    private static long f17255c = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;

    /* renamed from: e, reason: collision with root package name */
    private String f17256e = "";

    private a() {
    }

    public static a a() {
        return f17254a;
    }

    private void h() {
        h.f();
        if (TextUtils.isEmpty(this.f17256e)) {
            return;
        }
        try {
            final Context context = com.ta.a.a.a().getContext();
            if (com.ta.a.e.a.m106d(context)) {
                new Thread(new Runnable() { // from class: com.ta.utdid2.device.a.4
                    @Override // java.lang.Runnable
                    public void run() throws InterruptedException {
                        try {
                            Thread.sleep(a.f17255c);
                        } catch (Exception unused) {
                        }
                        if (com.ta.a.d.e.c(context)) {
                            new com.ta.a.d.h(context).run();
                        } else {
                            h.m109a("", "unable upload!");
                        }
                    }
                }).start();
            }
        } catch (Throwable th) {
            h.m109a("", th);
        }
    }

    private String q() {
        final Context context = com.ta.a.a.a().getContext();
        if (context == null) {
            return "";
        }
        final String strE = com.ta.a.d.e.e();
        if (c.c(strE)) {
            h.m109a("AppUtdid", "read utdid from V5AppFile");
            c.setType(7);
            c.a(new c.a() { // from class: com.ta.utdid2.device.a.1
                @Override // com.ta.utdid2.device.c.a
                public void i() {
                    com.ta.a.a.b bVarA = com.ta.a.a.a.a(strE);
                    String strB = com.ta.a.d.e.b(context);
                    if (TextUtils.isEmpty(strB)) {
                        com.ta.a.d.e.a(context, strE);
                    } else {
                        com.ta.a.a.b bVarA2 = com.ta.a.a.a.a(strB);
                        if (!bVarA2.isValid() || bVarA2.getTimestamp() < bVarA.getTimestamp()) {
                            com.ta.a.d.e.a(context, strE);
                        }
                    }
                    String strG = com.ta.a.d.e.g();
                    if (TextUtils.isEmpty(strG)) {
                        com.ta.a.d.e.b(strE);
                        return;
                    }
                    com.ta.a.a.b bVarA3 = com.ta.a.a.a.a(strG);
                    if (!bVarA3.isValid() || bVarA3.getTimestamp() < bVarA.getTimestamp()) {
                        com.ta.a.d.e.b(strE);
                    }
                }
            });
            return strE;
        }
        final String strB = com.ta.a.d.e.b(context);
        if (c.c(strB)) {
            h.m109a("AppUtdid", "read utdid from V5Settings");
            c.setType(8);
            c.a(new c.a() { // from class: com.ta.utdid2.device.a.2
                @Override // com.ta.utdid2.device.c.a
                public void i() {
                    com.ta.a.d.e.a(strB);
                    String strG = com.ta.a.d.e.g();
                    if (TextUtils.isEmpty(strG)) {
                        com.ta.a.d.e.b(strB);
                        return;
                    }
                    com.ta.a.a.b bVarA = com.ta.a.a.a.a(strB);
                    com.ta.a.a.b bVarA2 = com.ta.a.a.a.a(strG);
                    if (!bVarA2.isValid() || bVarA2.getTimestamp() < bVarA.getTimestamp()) {
                        com.ta.a.d.e.b(strB);
                    }
                }
            });
            return strB;
        }
        final String strG = com.ta.a.d.e.g();
        if (!c.c(strG)) {
            return null;
        }
        h.m109a("AppUtdid", "read utdid from V5Sdcard");
        c.setType(9);
        c.a(new c.a() { // from class: com.ta.utdid2.device.a.3
            @Override // com.ta.utdid2.device.c.a
            public void i() {
                com.ta.a.d.e.a(strG);
                com.ta.a.d.e.a(context, strG);
            }
        });
        return strG;
    }

    public synchronized String getUtdid(Context context) {
        if (!TextUtils.isEmpty(this.f17256e)) {
            return this.f17256e;
        }
        try {
            com.ta.a.e.e.c();
            String strQ = q();
            if (TextUtils.isEmpty(strQ)) {
                strQ = c.a(context).getValue();
            }
            if (TextUtils.isEmpty(strQ)) {
                return "ffffffffffffffffffffffff";
            }
            this.f17256e = strQ;
            h();
            return this.f17256e;
        } catch (Throwable th) {
            try {
                h.a("AppUtdid", th, new Object[0]);
                return "ffffffffffffffffffffffff";
            } finally {
                com.ta.a.e.e.d();
            }
        }
    }

    public synchronized String r() {
        return this.f17256e;
    }
}
