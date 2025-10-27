package com.meizu.cloud.pushsdk.notification.c;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class a {
    public static void a(String str, String str2) throws IOException {
        try {
            new File(str2).mkdirs();
            String[] list = new File(str).list();
            for (int i2 = 0; i2 < list.length; i2++) {
                String str3 = File.separator;
                File file = str.endsWith(str3) ? new File(str + list[i2]) : new File(str + str3 + list[i2]);
                if (file.isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + file.getName().toString());
                    byte[] bArr = new byte[R2.color.m3_ref_palette_dynamic_tertiary10];
                    while (true) {
                        int i3 = fileInputStream.read(bArr);
                        if (i3 == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i3);
                        }
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    fileInputStream.close();
                } else if (file.isDirectory()) {
                    a(str + "/" + list[i2], str2 + "/" + list[i2]);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean a(String str) {
        File file = new File(str);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean b(String str) {
        String str2 = File.separator;
        if (!str.endsWith(str2)) {
            str = str + str2;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        boolean zB = true;
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            if (fileArrListFiles[i2].isFile()) {
                zB = a(fileArrListFiles[i2].getAbsolutePath());
                if (!zB) {
                    break;
                }
            } else {
                zB = b(fileArrListFiles[i2].getAbsolutePath());
                if (!zB) {
                    break;
                }
            }
        }
        if (zB) {
            return file.delete();
        }
        return false;
    }

    public static File[] b(String str, final String str2) {
        File file = new File(str);
        return file.isDirectory() ? file.listFiles(new FileFilter() { // from class: com.meizu.cloud.pushsdk.notification.c.a.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                try {
                    return Long.valueOf(str2).longValue() > Long.valueOf(file2.getName().split("-")[1]).longValue();
                } catch (Exception e2) {
                    DebugLogger.e("FileUtil", "filters file error " + e2.getMessage());
                    return true;
                }
            }
        }) : new File[0];
    }
}
