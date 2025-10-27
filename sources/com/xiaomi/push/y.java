package com.xiaomi.push;

import android.text.TextUtils;
import cn.hutool.core.img.ImgUtil;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes6.dex */
public class y {

    /* renamed from: a, reason: collision with root package name */
    public static final String[] f25731a = {"jpg", "png", ImgUtil.IMAGE_TYPE_BMP, ImgUtil.IMAGE_TYPE_GIF, "webp"};

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0063: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:22:0x0063 */
    public static String a(File file) throws Throwable {
        InputStreamReader inputStreamReader;
        Closeable closeable;
        StringWriter stringWriter = new StringWriter();
        Closeable closeable2 = null;
        try {
            try {
                inputStreamReader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)));
            } catch (IOException e2) {
                e = e2;
                inputStreamReader = null;
            } catch (Throwable th) {
                th = th;
                a(closeable2);
                a(stringWriter);
                throw th;
            }
            try {
                char[] cArr = new char[2048];
                while (true) {
                    int i2 = inputStreamReader.read(cArr);
                    if (i2 == -1) {
                        String string = stringWriter.toString();
                        a(inputStreamReader);
                        a(stringWriter);
                        return string;
                    }
                    stringWriter.write(cArr, 0, i2);
                }
            } catch (IOException e3) {
                e = e3;
                com.xiaomi.channel.commonutils.logger.b.c("read file :" + file.getAbsolutePath() + " failure :" + e.getMessage());
                a(inputStreamReader);
                a(stringWriter);
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable2 = closeable;
            a(closeable2);
            a(stringWriter);
            throw th;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m773a(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                m773a(file2);
            }
        } else if (!file.exists()) {
            return;
        }
        file.delete();
    }

    public static void a(File file, File file2) throws Throwable {
        ZipOutputStream zipOutputStream;
        ZipOutputStream zipOutputStream2 = null;
        try {
            try {
                zipOutputStream = new ZipOutputStream(new FileOutputStream(file, false));
            } catch (FileNotFoundException unused) {
            } catch (IOException e2) {
                e = e2;
            }
            try {
                a(zipOutputStream, file2, null, null);
                a(zipOutputStream);
            } catch (FileNotFoundException unused2) {
                zipOutputStream2 = zipOutputStream;
                a(zipOutputStream2);
            } catch (IOException e3) {
                e = e3;
                zipOutputStream2 = zipOutputStream;
                com.xiaomi.channel.commonutils.logger.b.m117a("zip file failure + " + e.getMessage());
                a(zipOutputStream2);
            } catch (Throwable th) {
                th = th;
                zipOutputStream2 = zipOutputStream;
                a(zipOutputStream2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void a(File file, String str) throws Throwable {
        if (!file.exists()) {
            com.xiaomi.channel.commonutils.logger.b.c("mkdir " + file.getAbsolutePath());
            file.getParentFile().mkdirs();
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                try {
                    bufferedWriter2.write(str);
                    a(bufferedWriter2);
                } catch (IOException e2) {
                    e = e2;
                    bufferedWriter = bufferedWriter2;
                    com.xiaomi.channel.commonutils.logger.b.c("write file :" + file.getAbsolutePath() + " failure :" + e.getMessage());
                    a(bufferedWriter);
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    a(bufferedWriter);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void a(ZipOutputStream zipOutputStream, File file, String str, FileFilter fileFilter) throws Throwable {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                if (file.isDirectory()) {
                    File[] fileArrListFiles = fileFilter != null ? file.listFiles(fileFilter) : file.listFiles();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    String str3 = File.separator;
                    sb.append(str3);
                    zipOutputStream.putNextEntry(new ZipEntry(sb.toString()));
                    if (!TextUtils.isEmpty(str)) {
                        str2 = str + str3;
                    }
                    for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                        a(zipOutputStream, fileArrListFiles[i2], str2 + fileArrListFiles[i2].getName(), null);
                    }
                    File[] fileArrListFiles2 = file.listFiles(new z());
                    if (fileArrListFiles2 != null) {
                        for (File file2 : fileArrListFiles2) {
                            a(zipOutputStream, file2, str2 + File.separator + file2.getName(), fileFilter);
                        }
                    }
                } else {
                    zipOutputStream.putNextEntry(TextUtils.isEmpty(str) ? new ZipEntry(String.valueOf(new Date().getTime()) + ".txt") : new ZipEntry(str));
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i3 = fileInputStream2.read(bArr);
                            if (i3 == -1) {
                                break;
                            } else {
                                zipOutputStream.write(bArr, 0, i3);
                            }
                        }
                        fileInputStream = fileInputStream2;
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        com.xiaomi.channel.commonutils.logger.b.d("zipFiction failed with exception:" + e.toString());
                        a(fileInputStream);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        a(fileInputStream);
                        throw th;
                    }
                }
            } catch (IOException e3) {
                e = e3;
            }
            a(fileInputStream);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m774a(File file) {
        try {
            if (file.isDirectory()) {
                return false;
            }
            if (file.exists()) {
                return true;
            }
            File parentFile = file.getParentFile();
            if (parentFile.exists() || parentFile.mkdirs()) {
                return file.createNewFile();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static byte[] a(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception unused) {
            return bArr;
        }
    }

    public static void b(File file, File file2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        if (file.getAbsolutePath().equals(file2.getAbsolutePath())) {
            return;
        }
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 < 0) {
                    fileInputStream.close();
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream.write(bArr, 0, i2);
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }
}
