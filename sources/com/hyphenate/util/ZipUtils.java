package com.hyphenate.util;

import cn.hutool.core.text.StrPool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes4.dex */
public class ZipUtils {
    private static final int BUFF_SIZE = 1048576;

    public static void zip(File file, File file2) throws Throwable {
        if (file.exists()) {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file2), 1048576));
            zipFiles(file, zipOutputStream, "");
            zipOutputStream.flush();
            zipOutputStream.close();
        }
    }

    public static void zipFile(File file, ZipOutputStream zipOutputStream, String str) throws Throwable {
        byte[] bArr = new byte[1048576];
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file), 1048576);
                try {
                    "".equals(str);
                    file.getName();
                    zipOutputStream.putNextEntry(new ZipEntry(str));
                    while (true) {
                        int i2 = bufferedInputStream2.read(bArr);
                        if (i2 == -1) {
                            zipOutputStream.flush();
                            zipOutputStream.closeEntry();
                            try {
                                bufferedInputStream2.close();
                                return;
                            } catch (Exception unused) {
                                return;
                            }
                        }
                        zipOutputStream.write(bArr, 0, i2);
                    }
                } catch (Exception e2) {
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (Exception unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            throw e3;
        }
    }

    public static void zipFiles(File file, ZipOutputStream zipOutputStream, String str) throws Throwable {
        if (file.exists()) {
            if (!file.isDirectory()) {
                zipFile(file, zipOutputStream, str);
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    zipFiles(file2, zipOutputStream, str + StrPool.BACKSLASH + file2.getName());
                }
            }
        }
    }
}
