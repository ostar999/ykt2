package com.aliyun.aio_stat;

import android.os.Build;
import android.os.Process;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private float f3501a;

    /* renamed from: b, reason: collision with root package name */
    private int f3502b;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            b.this.f();
        }
    }

    /* renamed from: com.aliyun.aio_stat.b$b, reason: collision with other inner class name */
    public class C0028b implements FileFilter {
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return Pattern.matches("cpu[0-9]", file.getName());
        }
    }

    public class c implements f {

        /* renamed from: a, reason: collision with root package name */
        int f3504a = -1;

        public c() {
        }

        @Override // com.aliyun.aio_stat.b.f
        public void a(String str) {
            LinkedList linkedListB;
            if (!str.contains(Process.myPid() + "") || (linkedListB = b.b(str.split(" "))) == null) {
                return;
            }
            if (this.f3504a < 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= linkedListB.size()) {
                        break;
                    }
                    if (((String) linkedListB.get(i2)).contains("%")) {
                        this.f3504a = i2;
                        break;
                    }
                    i2++;
                }
            }
            int i3 = this.f3504a;
            if (i3 >= 0) {
                String strSubstring = (String) linkedListB.get(i3);
                if (strSubstring.contains("%")) {
                    strSubstring = strSubstring.substring(0, strSubstring.indexOf("%"));
                }
                try {
                    b.this.f3501a = Float.parseFloat(strSubstring);
                } catch (Exception unused) {
                }
            }
        }
    }

    public class d implements f {
        public d() {
        }

        @Override // com.aliyun.aio_stat.b.f
        public void a(String str) throws NumberFormatException {
            try {
                float f2 = Float.parseFloat(str);
                if (b.this.f3502b <= 0) {
                    b.this.f3502b = b.a();
                }
                b.this.f3501a = (f2 * 1.0f) / r0.f3502b;
            } catch (Exception unused) {
            }
        }
    }

    public static class e {

        /* renamed from: a, reason: collision with root package name */
        private static b f3507a = new b(null);
    }

    public interface f {
        void a(String str);
    }

    private b() {
        this.f3502b = 0;
        new Thread(new a()).start();
    }

    public /* synthetic */ b(a aVar) {
        this();
    }

    public static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new C0028b()).length;
        } catch (Exception unused) {
            return 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0043 A[Catch: all -> 0x004b, TryCatch #4 {all -> 0x004b, blocks: (B:27:0x003e, B:29:0x0043, B:31:0x0048), top: B:46:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0048 A[Catch: all -> 0x004b, TRY_LEAVE, TryCatch #4 {all -> 0x004b, blocks: (B:27:0x003e, B:29:0x0043, B:31:0x0048), top: B:46:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0050 A[Catch: all -> 0x005b, TRY_ENTER, TryCatch #5 {all -> 0x005b, blocks: (B:11:0x0023, B:12:0x0029, B:36:0x0050, B:38:0x0055), top: B:48:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0055 A[Catch: all -> 0x005b, TRY_LEAVE, TryCatch #5 {all -> 0x005b, blocks: (B:11:0x0023, B:12:0x0029, B:36:0x0050, B:38:0x0055), top: B:48:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x005b A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r4, com.aliyun.aio_stat.b.f r5) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L4c
            java.lang.Process r4 = r1.exec(r4)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L4c
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L4d
            java.io.InputStream r2 = r4.getInputStream()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L4d
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L4d
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L33
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L33
        L17:
            java.lang.String r0 = r2.readLine()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
            if (r0 == 0) goto L23
            if (r5 == 0) goto L17
            r5.a(r0)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
            goto L17
        L23:
            r1.close()     // Catch: java.lang.Throwable -> L5b
            r2.close()     // Catch: java.lang.Throwable -> L5b
        L29:
            r4.destroy()     // Catch: java.lang.Throwable -> L5b
            goto L5b
        L2d:
            r5 = move-exception
            goto L31
        L2f:
            r5 = move-exception
            r2 = r0
        L31:
            r0 = r1
            goto L3c
        L33:
            r2 = r0
        L34:
            r0 = r1
            goto L4e
        L36:
            r5 = move-exception
            goto L3b
        L38:
            r4 = move-exception
            r5 = r4
            r4 = r0
        L3b:
            r2 = r0
        L3c:
            if (r0 == 0) goto L41
            r0.close()     // Catch: java.lang.Throwable -> L4b
        L41:
            if (r2 == 0) goto L46
            r2.close()     // Catch: java.lang.Throwable -> L4b
        L46:
            if (r4 == 0) goto L4b
            r4.destroy()     // Catch: java.lang.Throwable -> L4b
        L4b:
            throw r5
        L4c:
            r4 = r0
        L4d:
            r2 = r0
        L4e:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.lang.Throwable -> L5b
        L53:
            if (r2 == 0) goto L58
            r2.close()     // Catch: java.lang.Throwable -> L5b
        L58:
            if (r4 == 0) goto L5b
            goto L29
        L5b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.aio_stat.b.a(java.lang.String, com.aliyun.aio_stat.b$f):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedList<String> b(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        LinkedList<String> linkedList = new LinkedList<>();
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (!strArr[i2].trim().equals("")) {
                linkedList.add(strArr[i2]);
            }
        }
        return linkedList;
    }

    private void b() throws Throwable {
        a("top -p " + Process.myPid() + " -o %CPU", new d());
    }

    private void c() throws Throwable {
        a(PLVDanmakuInfo.FONTMODE_TOP, new c());
    }

    public static b d() {
        return e.f3507a;
    }

    public float e() {
        return this.f3501a;
    }

    public void f() throws Throwable {
        if (Build.VERSION.SDK_INT > 25) {
            b();
        } else {
            c();
        }
    }
}
