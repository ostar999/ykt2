package com.tencent.smtt.sdk;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/* loaded from: classes6.dex */
class m {

    /* renamed from: a, reason: collision with root package name */
    private static m f21238a;

    /* renamed from: b, reason: collision with root package name */
    private static Context f21239b;

    private m() {
    }

    public static m a(Context context) {
        if (f21238a == null) {
            synchronized (m.class) {
                if (f21238a == null) {
                    f21238a = new m();
                }
            }
        }
        f21239b = context.getApplicationContext();
        return f21238a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x004f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0059 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.io.FileInputStream, java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Properties e() throws java.lang.Throwable {
        /*
            r5 = this;
            r0 = 0
            java.io.File r1 = r5.a()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.util.Properties r2 = new java.util.Properties     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r2.<init>()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            if (r1 == 0) goto L2a
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L27 java.lang.Throwable -> L40
            r3.<init>(r1)     // Catch: java.lang.Exception -> L27 java.lang.Throwable -> L40
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L22
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L22
            r2.load(r1)     // Catch: java.lang.Exception -> L1b java.lang.Throwable -> L62
            r0 = r1
            goto L2b
        L1b:
            r0 = move-exception
            goto L4a
        L1d:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L63
        L22:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L4a
        L27:
            r1 = move-exception
            r3 = r0
            goto L48
        L2a:
            r3 = r0
        L2b:
            if (r0 == 0) goto L35
            r0.close()     // Catch: java.io.IOException -> L31
            goto L35
        L31:
            r0 = move-exception
            r0.printStackTrace()
        L35:
            if (r3 == 0) goto L3f
            r3.close()     // Catch: java.io.IOException -> L3b
            goto L3f
        L3b:
            r0 = move-exception
            r0.printStackTrace()
        L3f:
            return r2
        L40:
            r1 = move-exception
            r3 = r0
            r0 = r1
            r1 = r3
            goto L63
        L45:
            r1 = move-exception
            r2 = r0
            r3 = r2
        L48:
            r0 = r1
            r1 = r3
        L4a:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L62
            if (r1 == 0) goto L57
            r1.close()     // Catch: java.io.IOException -> L53
            goto L57
        L53:
            r0 = move-exception
            r0.printStackTrace()
        L57:
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.io.IOException -> L5d
            goto L61
        L5d:
            r0 = move-exception
            r0.printStackTrace()
        L61:
            return r2
        L62:
            r0 = move-exception
        L63:
            if (r1 == 0) goto L6d
            r1.close()     // Catch: java.io.IOException -> L69
            goto L6d
        L69:
            r1 = move-exception
            r1.printStackTrace()
        L6d:
            if (r3 == 0) goto L77
            r3.close()     // Catch: java.io.IOException -> L73
            goto L77
        L73:
            r1 = move-exception
            r1.printStackTrace()
        L77:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.m.e():java.util.Properties");
    }

    public File a() throws IOException {
        o.a();
        File file = new File(o.r(f21239b), "tbscoreinstall.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return file;
    }

    public void a(int i2) {
        a("dexopt_retry_num", i2);
    }

    public void a(int i2, int i3) {
        a("copy_core_ver", i2);
        a("copy_status", i3);
    }

    public void a(String str) {
        a("install_apk_path", str);
    }

    public void a(String str, int i2) {
        a(str, String.valueOf(i2));
    }

    public void a(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    Properties propertiesE = e();
                    if (propertiesE != null) {
                        propertiesE.setProperty(str, str2);
                        File fileA = a();
                        if (fileA != null) {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(fileA);
                            try {
                                propertiesE.store(fileOutputStream2, "update " + str + " and status!");
                                fileOutputStream = fileOutputStream2;
                            } catch (Exception e2) {
                                e = e2;
                                fileOutputStream = fileOutputStream2;
                                e.printStackTrace();
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                return;
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    public int b() {
        return c("install_core_ver");
    }

    public int b(String str) throws Throwable {
        Properties propertiesE = e();
        if (propertiesE != null && propertiesE.getProperty(str) != null) {
            try {
                return Integer.parseInt(propertiesE.getProperty(str));
            } catch (Throwable unused) {
            }
        }
        return -1;
    }

    public void b(int i2) {
        a("unzip_retry_num", i2);
    }

    public void b(int i2, int i3) {
        a("tpatch_ver", i2);
        a("tpatch_status", i3);
    }

    public int c() {
        return b("install_status");
    }

    public int c(String str) throws Throwable {
        Properties propertiesE = e();
        if (propertiesE != null && propertiesE.getProperty(str) != null) {
            try {
                return Integer.parseInt(propertiesE.getProperty(str));
            } catch (Throwable unused) {
            }
        }
        return 0;
    }

    public void c(int i2) {
        a("incrupdate_status", i2);
    }

    public void c(int i2, int i3) {
        a("install_core_ver", i2);
        a("install_status", i3);
    }

    public int d() {
        return b("incrupdate_status");
    }

    public String d(String str) throws Throwable {
        Properties propertiesE = e();
        if (propertiesE == null || propertiesE.getProperty(str) == null) {
            return null;
        }
        return propertiesE.getProperty(str);
    }

    public void d(int i2) {
        a("unlzma_status", i2);
    }
}
