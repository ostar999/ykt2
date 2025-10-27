package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;

/* loaded from: classes6.dex */
public class hs {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f25078a = false;

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f25079a;

        /* renamed from: a, reason: collision with other field name */
        private hv f547a;

        public a(Context context, hv hvVar) {
            this.f547a = hvVar;
            this.f25079a = context;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            hs.c(this.f25079a, this.f547a);
        }
    }

    private static void a(Context context) {
        File file = new File(context.getFilesDir() + "/tdReadTemp");
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static void a(Context context, hv hvVar) {
        ai.a(context).a(new a(context, hvVar));
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ba, code lost:
    
        r15 = "TinyData read from cache file failed cause lengthBuffer < 1 || too big. length:" + r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(android.content.Context r12, com.xiaomi.push.hv r13, java.io.File r14, byte[] r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hs.a(android.content.Context, com.xiaomi.push.hv, java.io.File, byte[]):void");
    }

    private static void b(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 4).edit();
        editorEdit.putLong("last_tiny_data_upload_timestamp", System.currentTimeMillis() / 1000);
        editorEdit.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r11, com.xiaomi.push.hv r12) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "/"
            java.lang.String r1 = "/tdReadTemp"
            boolean r2 = com.xiaomi.push.hs.f25078a
            if (r2 != 0) goto Le1
            r2 = 1
            com.xiaomi.push.hs.f25078a = r2
            java.io.File r2 = new java.io.File
            java.io.File r3 = r11.getFilesDir()
            java.lang.String r4 = "tiny_data.data"
            r2.<init>(r3, r4)
            boolean r3 = r2.exists()
            java.lang.String r5 = "TinyData no ready file to get data."
            if (r3 != 0) goto L23
            com.xiaomi.channel.commonutils.logger.b.m117a(r5)
            return
        L23:
            a(r11)
            byte[] r3 = com.xiaomi.push.service.bm.a(r11)
            r6 = 0
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            java.io.File r8 = r11.getFilesDir()     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            java.lang.String r9 = "tiny_data.lock"
            r7.<init>(r8, r9)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            com.xiaomi.push.y.m774a(r7)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            java.io.RandomAccessFile r8 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            java.lang.String r9 = "rw"
            r8.<init>(r7, r9)     // Catch: java.lang.Throwable -> L81 java.lang.Exception -> L84
            java.nio.channels.FileChannel r7 = r8.getChannel()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            java.nio.channels.FileLock r6 = r7.lock()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            java.io.File r7 = new java.io.File     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r9.<init>()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            java.io.File r10 = r11.getFilesDir()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r9.append(r10)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r9.append(r1)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r9.append(r0)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r9.append(r4)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r7.<init>(r9)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            r2.renameTo(r7)     // Catch: java.lang.Exception -> L7f java.lang.Throwable -> Lcc
            if (r6 == 0) goto L7b
            boolean r2 = r6.isValid()
            if (r2 == 0) goto L7b
            r6.release()     // Catch: java.io.IOException -> L77
            goto L7b
        L77:
            r2 = move-exception
        L78:
            com.xiaomi.channel.commonutils.logger.b.a(r2)
        L7b:
            com.xiaomi.push.y.a(r8)
            goto L97
        L7f:
            r2 = move-exception
            goto L86
        L81:
            r11 = move-exception
            r8 = r6
            goto Lcd
        L84:
            r2 = move-exception
            r8 = r6
        L86:
            com.xiaomi.channel.commonutils.logger.b.a(r2)     // Catch: java.lang.Throwable -> Lcc
            if (r6 == 0) goto L7b
            boolean r2 = r6.isValid()
            if (r2 == 0) goto L7b
            r6.release()     // Catch: java.io.IOException -> L95
            goto L7b
        L95:
            r2 = move-exception
            goto L78
        L97:
            java.io.File r2 = new java.io.File
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.File r7 = r11.getFilesDir()
            r6.append(r7)
            r6.append(r1)
            r6.append(r0)
            r6.append(r4)
            java.lang.String r0 = r6.toString()
            r2.<init>(r0)
            boolean r0 = r2.exists()
            if (r0 != 0) goto Lbf
            com.xiaomi.channel.commonutils.logger.b.m117a(r5)
            return
        Lbf:
            a(r11, r12, r2, r3)
            r12 = 0
            com.xiaomi.push.hr.a(r12)
            b(r11)
            com.xiaomi.push.hs.f25078a = r12
            return
        Lcc:
            r11 = move-exception
        Lcd:
            if (r6 == 0) goto Ldd
            boolean r12 = r6.isValid()
            if (r12 == 0) goto Ldd
            r6.release()     // Catch: java.io.IOException -> Ld9
            goto Ldd
        Ld9:
            r12 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r12)
        Ldd:
            com.xiaomi.push.y.a(r8)
            throw r11
        Le1:
            java.lang.String r11 = "TinyData extractTinyData is running"
            com.xiaomi.channel.commonutils.logger.b.m117a(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hs.c(android.content.Context, com.xiaomi.push.hv):void");
    }
}
