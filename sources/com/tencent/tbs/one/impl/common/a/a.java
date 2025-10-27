package com.tencent.tbs.one.impl.common.a;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes6.dex */
public final class a extends com.tencent.tbs.one.impl.a.b<byte[]> {

    /* renamed from: b, reason: collision with root package name */
    public String f21955b;

    /* renamed from: c, reason: collision with root package name */
    public File f21956c;

    /* renamed from: d, reason: collision with root package name */
    public ZipEntry f21957d;

    /* renamed from: e, reason: collision with root package name */
    public ZipOutputStream f21958e;

    /* renamed from: f, reason: collision with root package name */
    public int f21959f = 0;

    public a(String str) throws IOException {
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            a(1001, "Log path not exist or not directory!", new Throwable());
            return;
        }
        this.f21955b = str;
        this.f21957d = new ZipEntry("onelog");
        File fileA = a("log_", ".zip.tmp", file);
        this.f21956c = fileA;
        fileA.getAbsolutePath();
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(this.f21956c)));
            this.f21958e = zipOutputStream;
            zipOutputStream.putNextEntry(this.f21957d);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static File a(String str, String str2, File file) {
        File[] fileArrListFiles = file.listFiles();
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            String name = fileArrListFiles[i2].getName();
            if (name.startsWith(str) && name.endsWith(str2)) {
                fileArrListFiles[i2].delete();
            }
        }
        return new File(file, str + System.currentTimeMillis() + str2);
    }

    private void a(File file) throws Throwable {
        if (file.getAbsolutePath().endsWith(".zip.tmp")) {
            file.getAbsolutePath();
            return;
        }
        int i2 = this.f21959f;
        this.f21959f = i2 + 1;
        a(i2);
        file.getAbsolutePath();
        byte[] bArr = new byte[4096];
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file), 4096);
                while (true) {
                    try {
                        int i3 = bufferedInputStream2.read(bArr, 0, 4096);
                        if (i3 == -1) {
                            this.f21958e.flush();
                            try {
                                bufferedInputStream2.close();
                                return;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                        this.f21958e.write(bArr, 0, i3);
                    } catch (IOException e3) {
                        e = e3;
                        bufferedInputStream = bufferedInputStream2;
                        e.printStackTrace();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                                return;
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e = e6;
        }
    }

    private void c() {
        File file = this.f21956c;
        if (file != null) {
            file.delete();
            this.f21956c = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0166 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x017b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x012d A[EDGE_INSN: B:146:0x012d->B:67:0x012d BREAK  A[LOOP:4: B:64:0x0122->B:66:0x0129], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0129 A[Catch: IOException -> 0x013d, all -> 0x0160, LOOP:4: B:64:0x0122->B:66:0x0129, LOOP_END, TryCatch #0 {all -> 0x0160, blocks: (B:63:0x0120, B:64:0x0122, B:66:0x0129, B:67:0x012d, B:78:0x0144), top: B:110:0x0117 }] */
    /* JADX WARN: Type inference failed for: r1v25, types: [int] */
    /* JADX WARN: Type inference failed for: r1v30, types: [int] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.RandomAccessFile] */
    @Override // com.tencent.tbs.one.impl.a.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.common.a.a.a():void");
    }
}
