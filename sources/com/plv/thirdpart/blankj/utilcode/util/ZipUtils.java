package com.plv.thirdpart.blankj.utilcode.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* loaded from: classes5.dex */
public final class ZipUtils {
    private static final int BUFFER_LEN = 8192;

    private ZipUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    private static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static List<String> getComments(String str) throws IOException {
        return getComments(getFileByPath(str));
    }

    private static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    public static List<String> getFilesPath(String str) throws IOException {
        return getFilesPath(getFileByPath(str));
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    private static boolean unzipChildFile(File file, List<File> list, ZipFile zipFile, ZipEntry zipEntry, String str) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        File file2 = new File(file + File.separator + str);
        list.add(file2);
        if (zipEntry.isDirectory()) {
            if (!createOrExistsDir(file2)) {
                return false;
            }
        } else {
            if (!createOrExistsFile(file2)) {
                return false;
            }
            BufferedInputStream bufferedInputStream2 = null;
            try {
                bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = null;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedOutputStream = null;
            }
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int i2 = bufferedInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, i2);
                }
                CloseUtils.closeIO(bufferedInputStream, bufferedOutputStream);
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream2 = bufferedInputStream;
                CloseUtils.closeIO(bufferedInputStream2, bufferedOutputStream);
                throw th;
            }
        }
        return true;
    }

    public static List<File> unzipFile(String str, String str2) throws IOException {
        return unzipFileByKeyword(str, str2, (String) null);
    }

    public static List<File> unzipFileByKeyword(String str, String str2, String str3) throws IOException {
        return unzipFileByKeyword(getFileByPath(str), getFileByPath(str2), str3);
    }

    public static boolean zipFile(String str, String str2) throws IOException {
        return zipFile(str, str2, (String) null);
    }

    public static List<String> getComments(File file) throws IOException {
        if (file == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(file).entries();
        while (enumerationEntries.hasMoreElements()) {
            arrayList.add(enumerationEntries.nextElement().getComment());
        }
        return arrayList;
    }

    public static List<String> getFilesPath(File file) throws IOException {
        if (file == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(file).entries();
        while (enumerationEntries.hasMoreElements()) {
            arrayList.add(enumerationEntries.nextElement().getName());
        }
        return arrayList;
    }

    public static List<File> unzipFile(File file, File file2) throws IOException {
        return unzipFileByKeyword(file, file2, (String) null);
    }

    public static List<File> unzipFileByKeyword(File file, File file2, String str) throws IOException {
        if (file == null || file2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
        if (!isSpace(str)) {
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                String name = zipEntryNextElement.getName();
                if (name.contains(str) && !unzipChildFile(file2, arrayList, zipFile, zipEntryNextElement, name)) {
                    break;
                }
            }
        } else {
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement2 = enumerationEntries.nextElement();
                if (!unzipChildFile(file2, arrayList, zipFile, zipEntryNextElement2, zipEntryNextElement2.getName())) {
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    public static boolean zipFile(String str, String str2, String str3) throws IOException {
        return zipFile(getFileByPath(str), getFileByPath(str2), str3);
    }

    public static boolean zipFile(File file, File file2) throws IOException {
        return zipFile(file, file2, (String) null);
    }

    public static boolean zipFile(File file, File file2, String str) throws Throwable {
        ZipOutputStream zipOutputStream;
        if (file == null || file2 == null) {
            return false;
        }
        ZipOutputStream zipOutputStream2 = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
        } catch (Throwable th) {
            th = th;
        }
        try {
            boolean zZipFile = zipFile(file, "", zipOutputStream, str);
            CloseUtils.closeIO(zipOutputStream);
            return zZipFile;
        } catch (Throwable th2) {
            th = th2;
            zipOutputStream2 = zipOutputStream;
            if (zipOutputStream2 != null) {
                CloseUtils.closeIO(zipOutputStream2);
            }
            throw th;
        }
    }

    private static boolean zipFile(File file, String str, ZipOutputStream zipOutputStream, String str2) throws Throwable {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(isSpace(str) ? "" : File.separator);
        sb.append(file.getName());
        String string = sb.toString();
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file2 : fileArrListFiles) {
                    if (!zipFile(file2, string, zipOutputStream, str2)) {
                        return false;
                    }
                }
            } else {
                ZipEntry zipEntry = new ZipEntry(string + '/');
                if (!isSpace(str2)) {
                    zipEntry.setComment(str2);
                }
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            }
        } else {
            BufferedInputStream bufferedInputStream = null;
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    ZipEntry zipEntry2 = new ZipEntry(string);
                    if (!isSpace(str2)) {
                        zipEntry2.setComment(str2);
                    }
                    zipOutputStream.putNextEntry(zipEntry2);
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i2 = bufferedInputStream2.read(bArr, 0, 8192);
                        if (i2 == -1) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, i2);
                    }
                    zipOutputStream.closeEntry();
                    CloseUtils.closeIO(bufferedInputStream2);
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    CloseUtils.closeIO(bufferedInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return true;
    }
}
