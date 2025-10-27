package com.tencent.tbs.one.impl.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes6.dex */
public final class d {
    public static InputStream a(Context context, String str, String str2) {
        if (a(str2)) {
            return context.getResources().getAssets().open(b(str2));
        }
        if (new File(str2).isAbsolute()) {
            return new FileInputStream(str2);
        }
        String absolutePath = new File(str, str2).getAbsolutePath();
        if (!a(absolutePath)) {
            return new FileInputStream(absolutePath);
        }
        return context.getResources().getAssets().open(b(absolutePath));
    }

    public static String a(InputStream inputStream, String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(inputStream, byteArrayOutputStream);
        return new String(byteArrayOutputStream.toByteArray(), str);
    }

    public static String a(InputStream inputStream, String str, File file) throws Throwable {
        i iVar;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File file2 = new File(file.getPath() + ".tmp");
        try {
            iVar = new i(new OutputStream[]{byteArrayOutputStream, new FileOutputStream(file2)});
            try {
                a(inputStream, iVar);
                a(iVar);
                if (file2.renameTo(file)) {
                    return new String(byteArrayOutputStream.toByteArray(), str);
                }
                throw new IOException();
            } catch (Throwable th) {
                th = th;
                a(iVar);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            iVar = null;
        }
    }

    public static void a(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e2) {
            g.c("Failed to close %s", closeable, e2);
        }
    }

    public static void a(File file, File file2) throws Throwable {
        FileChannel channel;
        FileChannel channel2 = null;
        try {
            channel = new FileInputStream(file).getChannel();
        } catch (Throwable th) {
            th = th;
            channel = null;
        }
        try {
            channel2 = new FileOutputStream(file2).getChannel();
            channel2.transferFrom(channel, 0L, channel.size());
            a(channel);
            a(channel2);
        } catch (Throwable th2) {
            th = th2;
            a(channel);
            a(channel2);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.io.InputStream r6, java.io.File r7, java.io.File r8) throws java.lang.Throwable {
        /*
            r0 = 0
            java.util.zip.ZipInputStream r1 = new java.util.zip.ZipInputStream     // Catch: java.lang.Throwable -> Lb7
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> Lb7
            r2.<init>(r6)     // Catch: java.lang.Throwable -> Lb7
            r1.<init>(r2)     // Catch: java.lang.Throwable -> Lb7
            if (r8 == 0) goto L1c
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L18
            r2.<init>(r8)     // Catch: java.lang.Throwable -> L18
            java.util.zip.ZipOutputStream r8 = new java.util.zip.ZipOutputStream     // Catch: java.lang.Throwable -> L18
            r8.<init>(r2)     // Catch: java.lang.Throwable -> L18
            goto L1d
        L18:
            r6 = move-exception
            r8 = r0
            goto Lba
        L1c:
            r8 = r0
        L1d:
            java.util.zip.ZipEntry r2 = r1.getNextEntry()     // Catch: java.lang.Throwable -> L93
            if (r2 == 0) goto L98
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L93
            java.lang.String r4 = r2.getName()     // Catch: java.lang.Throwable -> L93
            r3.<init>(r7, r4)     // Catch: java.lang.Throwable -> L93
            boolean r2 = r2.isDirectory()     // Catch: java.lang.Throwable -> L93
            java.lang.String r4 = "Failed to create directory "
            if (r2 == 0) goto L57
            boolean r2 = r3.exists()     // Catch: java.lang.Throwable -> L93
            if (r2 != 0) goto L8f
            boolean r2 = r3.mkdirs()     // Catch: java.lang.Throwable -> L93
            if (r2 == 0) goto L41
            goto L8f
        L41:
            java.io.IOException r6 = new java.io.IOException     // Catch: java.lang.Throwable -> L93
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L93
            r7.<init>(r4)     // Catch: java.lang.Throwable -> L93
            java.lang.String r2 = r3.getAbsolutePath()     // Catch: java.lang.Throwable -> L93
            r7.append(r2)     // Catch: java.lang.Throwable -> L93
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L93
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L93
            throw r6     // Catch: java.lang.Throwable -> L93
        L57:
            java.io.File r2 = r3.getParentFile()     // Catch: java.lang.Throwable -> L93
            boolean r5 = r2.exists()     // Catch: java.lang.Throwable -> L93
            if (r5 != 0) goto L7e
            boolean r5 = r2.mkdirs()     // Catch: java.lang.Throwable -> L93
            if (r5 == 0) goto L68
            goto L7e
        L68:
            java.io.IOException r6 = new java.io.IOException     // Catch: java.lang.Throwable -> L93
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L93
            r7.<init>(r4)     // Catch: java.lang.Throwable -> L93
            java.lang.String r2 = r2.getAbsolutePath()     // Catch: java.lang.Throwable -> L93
            r7.append(r2)     // Catch: java.lang.Throwable -> L93
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L93
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L93
            throw r6     // Catch: java.lang.Throwable -> L93
        L7e:
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L93
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L93
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L93
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L93
            b(r1, r2)     // Catch: java.lang.Throwable -> L95
            r2.close()     // Catch: java.lang.Throwable -> L95
            r0 = r2
        L8f:
            r1.closeEntry()     // Catch: java.lang.Throwable -> L93
            goto L1d
        L93:
            r6 = move-exception
            goto Lba
        L95:
            r6 = move-exception
            r0 = r2
            goto Lba
        L98:
            java.lang.String r7 = "FileUtils unzipStream  last  entry"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L93
            com.tencent.tbs.one.impl.a.g.a(r7, r2)     // Catch: java.lang.Throwable -> L93
            r7 = 8192(0x2000, float:1.148E-41)
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L93
        La4:
            int r2 = r6.read(r7)     // Catch: java.lang.Throwable -> L93
            r3 = -1
            if (r2 != r3) goto La4
            if (r0 == 0) goto Lb0
            r0.close()
        Lb0:
            a(r1)
            a(r8)
            return
        Lb7:
            r6 = move-exception
            r8 = r0
            r1 = r8
        Lba:
            if (r0 == 0) goto Lbf
            r0.close()
        Lbf:
            a(r1)
            a(r8)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.a.d.a(java.io.InputStream, java.io.File, java.io.File):void");
    }

    public static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i2);
            }
        }
    }

    public static void a(String str, String str2, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        try {
            fileOutputStream.write(str.getBytes(str2));
            a(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            a(fileOutputStream);
            throw th;
        }
    }

    public static boolean a(File file) {
        File[] fileArrListFiles;
        try {
            if (!file.exists()) {
                return true;
            }
            if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    a(file2);
                }
            }
            return file.delete();
        } catch (Exception e2) {
            g.c("recursivelyDeleteFile failed,current file is %s,exception is %s", file.getAbsolutePath(), Log.getStackTraceString(e2));
            return false;
        }
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("/android_asset/") || str.startsWith("file:///android_asset/");
    }

    public static int b(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int i2 = 0;
        while (true) {
            int i3 = inputStream.read(bArr);
            if (i3 == -1) {
                return i2;
            }
            outputStream.write(bArr, 0, i3);
            i2 += i3;
        }
    }

    public static String b(String str) {
        int i2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("/android_asset/")) {
            i2 = 15;
        } else {
            if (!str.startsWith("file:///android_asset/")) {
                return null;
            }
            i2 = 22;
        }
        return str.substring(i2);
    }

    public static void b(File file) {
        try {
            if (file.createNewFile()) {
                return;
            }
            g.c("Failed to create file %s, file already exists", file.getAbsolutePath());
        } catch (IOException e2) {
            g.c("Failed to create file %s", file.getAbsolutePath(), e2);
        }
    }

    public static void b(File file, File file2) throws Throwable {
        if (file.renameTo(file2)) {
            return;
        }
        c(file, file2);
        a(file);
    }

    public static void c(File file) {
        if (a(file)) {
            return;
        }
        g.c("Failed to delete file %s", file.getAbsolutePath());
    }

    public static void c(File file, File file2) throws Throwable {
        if (!file.isDirectory()) {
            if (file.canRead()) {
                a(file, file2);
            }
        } else {
            if (!file2.exists() && !file2.mkdirs()) {
                throw new IOException("Failed to create directory " + file2.getAbsolutePath());
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file3 : fileArrListFiles) {
                    c(file3, new File(file2, file3.getName()));
                }
            }
        }
    }

    public static void d(File file) {
        if (file.mkdirs()) {
            return;
        }
        g.c("Failed to create directory %s", file.getAbsolutePath());
    }

    public static void e(File file) {
        if (!file.exists()) {
            if (file.mkdirs()) {
                return;
            }
            g.c("Failed to create non-exist directory %s", file.getAbsolutePath());
        } else {
            if (file.isDirectory()) {
                return;
            }
            if (file.delete() && file.mkdirs()) {
                return;
            }
            g.c("Failed to create namesake directory %s", file.getAbsolutePath());
        }
    }
}
