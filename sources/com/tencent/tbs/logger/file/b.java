package com.tencent.tbs.logger.file;

import android.content.Context;
import com.tencent.tbs.logger.d;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes6.dex */
public class b implements d {

    /* renamed from: a, reason: collision with root package name */
    public boolean f21663a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f21664b;

    /* renamed from: c, reason: collision with root package name */
    public c f21665c;

    /* renamed from: d, reason: collision with root package name */
    public volatile RunnableC0358b f21666d;

    /* renamed from: e, reason: collision with root package name */
    public com.tencent.tbs.logger.file.naming.b f21667e;

    /* renamed from: f, reason: collision with root package name */
    public com.tencent.tbs.logger.file.clean.a f21668f;

    /* renamed from: g, reason: collision with root package name */
    public com.tencent.tbs.logger.file.backup.a f21669g;

    /* renamed from: h, reason: collision with root package name */
    public Context f21670h;

    /* renamed from: com.tencent.tbs.logger.file.b$b, reason: collision with other inner class name */
    public class RunnableC0358b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public BlockingQueue<com.tencent.tbs.logger.b> f21671a = new LinkedBlockingQueue();

        /* renamed from: b, reason: collision with root package name */
        public volatile boolean f21672b;

        public /* synthetic */ RunnableC0358b(a aVar) {
        }

        public void a(com.tencent.tbs.logger.b bVar) throws InterruptedException {
            try {
                this.f21671a.put(bVar);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }

        public boolean a() {
            boolean z2;
            synchronized (this) {
                z2 = this.f21672b;
            }
            return z2;
        }

        public void b() {
            synchronized (this) {
                try {
                    new Thread(this).start();
                    this.f21672b = true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException, IOException {
            while (true) {
                try {
                    com.tencent.tbs.logger.b bVarTake = this.f21671a.take();
                    if (bVarTake == null) {
                        return;
                    } else {
                        b.this.b(bVarTake);
                    }
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    synchronized (this) {
                        this.f21672b = false;
                        return;
                    }
                }
            }
        }
    }

    public class c {

        /* renamed from: a, reason: collision with root package name */
        public File f21674a;

        /* renamed from: b, reason: collision with root package name */
        public OutputStream f21675b;

        /* renamed from: c, reason: collision with root package name */
        public String f21676c;

        public /* synthetic */ c(a aVar) {
        }

        public boolean a() {
            OutputStream outputStream = this.f21675b;
            if (outputStream == null) {
                return true;
            }
            try {
                try {
                    outputStream.close();
                    return true;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    this.f21675b = null;
                    this.f21674a = null;
                    return false;
                }
            } finally {
                this.f21675b = null;
                this.f21674a = null;
            }
        }

        public boolean a(String str) throws IOException {
            this.f21676c = str;
            File file = new File(b.this.a(), str);
            this.f21674a = file;
            if (file.exists()) {
                this.f21675b = new BufferedOutputStream(new FileOutputStream(this.f21674a, true));
                return true;
            }
            try {
                File parentFile = this.f21674a.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.f21674a.createNewFile();
                try {
                    this.f21675b = new BufferedOutputStream(new FileOutputStream(this.f21674a, true));
                    return true;
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (IOException e3) {
                e = e3;
            }
            e.printStackTrace();
            this.f21674a = null;
            return false;
        }
    }

    public b(Context context, boolean z2, boolean z3, com.tencent.tbs.logger.file.naming.b bVar, com.tencent.tbs.logger.file.clean.a aVar, com.tencent.tbs.logger.file.backup.a aVar2) {
        a aVar3 = null;
        this.f21665c = new c(aVar3);
        this.f21666d = new RunnableC0358b(aVar3);
        this.f21663a = z2;
        this.f21664b = z3;
        this.f21667e = bVar;
        this.f21668f = aVar;
        this.f21669g = aVar2;
        this.f21670h = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a() {
        /*
            r6 = this;
            android.content.Context r0 = r6.f21670h
            java.lang.String r1 = com.tencent.tbs.logger.file.a.f21662a
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto Lb
            goto L54
        Lb:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 28
            r3 = 0
            if (r1 < r2) goto L17
            java.lang.String r1 = o0.a.a()
            goto L18
        L17:
            r1 = r3
        L18:
            com.tencent.tbs.logger.file.a.f21662a = r1
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L21
            goto L54
        L21:
            java.lang.String r1 = "android.app.ActivityThread"
            java.lang.Class<android.app.Application> r2 = android.app.Application.class
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch: java.lang.Throwable -> L47
            r4 = 0
            java.lang.Class r1 = java.lang.Class.forName(r1, r4, r2)     // Catch: java.lang.Throwable -> L47
            java.lang.String r2 = "currentProcessName"
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: java.lang.Throwable -> L47
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r2, r5)     // Catch: java.lang.Throwable -> L47
            r2 = 1
            r1.setAccessible(r2)     // Catch: java.lang.Throwable -> L47
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L47
            java.lang.Object r1 = r1.invoke(r3, r2)     // Catch: java.lang.Throwable -> L47
            boolean r2 = r1 instanceof java.lang.String     // Catch: java.lang.Throwable -> L47
            if (r2 == 0) goto L4b
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Throwable -> L47
            goto L4c
        L47:
            r1 = move-exception
            r1.printStackTrace()
        L4b:
            r1 = r3
        L4c:
            com.tencent.tbs.logger.file.a.f21662a = r1
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L57
        L54:
            java.lang.String r0 = com.tencent.tbs.logger.file.a.f21662a
            goto L87
        L57:
            if (r0 != 0) goto L5a
            goto L84
        L5a:
            int r1 = android.os.Process.myPid()
            java.lang.String r2 = "activity"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            if (r0 == 0) goto L84
            java.util.List r0 = r0.getRunningAppProcesses()
            if (r0 == 0) goto L84
            java.util.Iterator r0 = r0.iterator()
        L72:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L84
            java.lang.Object r2 = r0.next()
            android.app.ActivityManager$RunningAppProcessInfo r2 = (android.app.ActivityManager.RunningAppProcessInfo) r2
            int r4 = r2.pid
            if (r4 != r1) goto L72
            java.lang.String r3 = r2.processName
        L84:
            com.tencent.tbs.logger.file.a.f21662a = r3
            r0 = r3
        L87:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L9a
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r1 = "."
            java.lang.String r2 = "_"
            java.lang.String r0 = r0.replace(r1, r2)
            goto La2
        L9a:
            int r0 = android.os.Process.myPid()
            java.lang.String r0 = java.lang.String.valueOf(r0)
        La2:
            java.io.File r1 = new java.io.File
            java.lang.String r2 = com.tencent.tbs.logger.f.f21659f
            r1.<init>(r2, r0)
            boolean r0 = r1.exists()
            if (r0 != 0) goto Lb2
            r1.mkdirs()
        Lb2:
            java.lang.String r0 = r1.getAbsolutePath()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.logger.file.b.a():java.lang.String");
    }

    @Override // com.tencent.tbs.logger.d
    public void a(com.tencent.tbs.logger.b bVar) throws InterruptedException, IOException {
        if (!this.f21663a) {
            b(bVar);
            return;
        }
        if (!this.f21666d.a()) {
            this.f21666d.b();
        }
        this.f21666d.a(bVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0063 A[Catch: Exception -> 0x00b8, TryCatch #4 {Exception -> 0x00b8, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000b, B:8:0x000e, B:10:0x0012, B:12:0x0018, B:14:0x0022, B:16:0x0029, B:21:0x0034, B:22:0x0037, B:24:0x0046, B:26:0x004a, B:28:0x0054, B:29:0x0057, B:30:0x005a, B:33:0x0063, B:35:0x0069, B:37:0x0071, B:39:0x00a1, B:40:0x00a4, B:42:0x00b0, B:43:0x00b7), top: B:88:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void b(com.tencent.tbs.logger.b r11) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 313
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.logger.file.b.b(com.tencent.tbs.logger.b):void");
    }
}
