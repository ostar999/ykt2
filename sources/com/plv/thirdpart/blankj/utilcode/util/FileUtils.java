package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.SuppressLint;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class FileUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public interface OnReplaceListener {
        boolean onReplace();
    }

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressLint({"DefaultLocale"})
    private static String byte2FitMemorySize(long j2) {
        return j2 < 0 ? "shouldn't be less than zero!" : j2 < 1024 ? String.format("%.3fB", Double.valueOf(j2)) : j2 < 1048576 ? String.format("%.3fKB", Double.valueOf(j2 / 1024.0d)) : j2 < 1073741824 ? String.format("%.3fMB", Double.valueOf(j2 / 1048576.0d)) : String.format("%.3fGB", Double.valueOf(j2 / 1.073741824E9d));
    }

    private static String bytes2HexString(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        char[] cArr = new char[length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = hexDigits;
            cArr[i2] = cArr2[(b3 >>> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    public static boolean copyDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean copyFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    private static boolean copyOrMoveDir(String str, String str2, OnReplaceListener onReplaceListener, boolean z2) {
        return copyOrMoveDir(getFileByPath(str), getFileByPath(str2), onReplaceListener, z2);
    }

    private static boolean copyOrMoveFile(String str, String str2, OnReplaceListener onReplaceListener, boolean z2) {
        return copyOrMoveFile(getFileByPath(str), getFileByPath(str2), onReplaceListener, z2);
    }

    public static boolean createFileByDeleteOldFile(String str) {
        return createFileByDeleteOldFile(getFileByPath(str));
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    public static boolean deleteAllInDir(String str) {
        return deleteAllInDir(getFileByPath(str));
    }

    public static boolean deleteDir(String str) {
        return deleteDir(getFileByPath(str));
    }

    public static boolean deleteFile(String str) {
        return deleteFile(getFileByPath(str));
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    public static boolean deleteFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    public static long getDirLength(String str) {
        return getDirLength(getFileByPath(str));
    }

    public static String getDirName(File file) {
        if (file == null) {
            return null;
        }
        return getDirName(file.getPath());
    }

    public static String getDirSize(String str) {
        return getDirSize(getFileByPath(str));
    }

    public static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    public static String getFileCharsetSimple(String str) {
        return getFileCharsetSimple(getFileByPath(str));
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileExtension(file.getPath());
    }

    public static long getFileLastModified(String str) {
        return getFileLastModified(getFileByPath(str));
    }

    public static long getFileLength(String str) {
        return getFileLength(getFileByPath(str));
    }

    public static int getFileLines(String str) {
        return getFileLines(getFileByPath(str));
    }

    public static byte[] getFileMD5(String str) {
        return getFileMD5(getFileByPath(str));
    }

    public static String getFileMD5ToString(String str) {
        return getFileMD5ToString(isSpace(str) ? null : new File(str));
    }

    public static String getFileName(File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getPath());
    }

    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileSize(String str) {
        return getFileSize(getFileByPath(str));
    }

    public static boolean isDir(String str) {
        return isDir(getFileByPath(str));
    }

    public static boolean isFile(String str) {
        return isFile(getFileByPath(str));
    }

    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
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

    public static List<File> listFilesInDir(String str) {
        return listFilesInDir(str, false);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, false);
    }

    public static boolean moveDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean moveFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean rename(String str, String str2) {
        return rename(getFileByPath(str), str2);
    }

    public static boolean copyDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, false);
    }

    public static boolean copyFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, false);
    }

    private static boolean copyOrMoveDir(File file, File file2, OnReplaceListener onReplaceListener, boolean z2) {
        if (file == null || file2 == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        String str = File.separator;
        sb.append(str);
        String string = sb.toString();
        String str2 = file2.getPath() + str;
        if (str2.contains(string) || !file.exists() || !file.isDirectory()) {
            return false;
        }
        if (file2.exists()) {
            if (!onReplaceListener.onReplace()) {
                return true;
            }
            if (!deleteAllInDir(file2)) {
                return false;
            }
        }
        if (!createOrExistsDir(file2)) {
            return false;
        }
        for (File file3 : file.listFiles()) {
            File file4 = new File(str2 + file3.getName());
            if (file3.isFile()) {
                if (!copyOrMoveFile(file3, file4, onReplaceListener, z2)) {
                    return false;
                }
            } else if (file3.isDirectory() && !copyOrMoveDir(file3, file4, onReplaceListener, z2)) {
                return false;
            }
        }
        return !z2 || deleteDir(file);
    }

    private static boolean copyOrMoveFile(File file, File file2, OnReplaceListener onReplaceListener, boolean z2) {
        if (file != null && file2 != null && !file.equals(file2) && file.exists() && file.isFile()) {
            if (file2.exists()) {
                if (!onReplaceListener.onReplace()) {
                    return true;
                }
                if (!file2.delete()) {
                    return false;
                }
            }
            if (!createOrExistsDir(file2.getParentFile())) {
                return false;
            }
            try {
                if (!FileIOUtils.writeFileFromIS(file2, (InputStream) new FileInputStream(file), false)) {
                    return false;
                }
                if (z2) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                }
                return true;
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    public static boolean createOrExistsFile(File file) {
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

    public static boolean deleteAllInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.plv.thirdpart.blankj.utilcode.util.FileUtils.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        });
    }

    public static boolean deleteDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean deleteFilesInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.plv.thirdpart.blankj.utilcode.util.FileUtils.2
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(File file, FileFilter fileFilter) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    if (file2.isFile()) {
                        if (!file2.delete()) {
                            return false;
                        }
                    } else if (file2.isDirectory() && !deleteDir(file2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static long getDirLength(File file) {
        if (!isDir(file)) {
            return -1L;
        }
        File[] fileArrListFiles = file.listFiles();
        long dirLength = 0;
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                dirLength += file2.isDirectory() ? getDirLength(file2) : file2.length();
            }
        }
        return dirLength;
    }

    public static String getDirName(String str) {
        if (isSpace(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf + 1);
    }

    public static String getDirSize(File file) {
        long dirLength = getDirLength(file);
        return dirLength == -1 ? "" : byte2FitMemorySize(dirLength);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getFileCharsetSimple(java.io.File r5) throws java.lang.Throwable {
        /*
            r0 = 1
            r1 = 0
            r2 = 0
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L27 java.io.IOException -> L29
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L27 java.io.IOException -> L29
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L27 java.io.IOException -> L29
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L27 java.io.IOException -> L29
            int r5 = r3.read()     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L24
            int r5 = r5 << 8
            int r2 = r3.read()     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L24
            int r5 = r5 + r2
            java.io.Closeable[] r0 = new java.io.Closeable[r0]
            r0[r1] = r3
            com.plv.thirdpart.blankj.utilcode.util.CloseUtils.closeIO(r0)
            r1 = r5
            goto L34
        L21:
            r5 = move-exception
            r2 = r3
            goto L4f
        L24:
            r5 = move-exception
            r2 = r3
            goto L2a
        L27:
            r5 = move-exception
            goto L4f
        L29:
            r5 = move-exception
        L2a:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L27
            java.io.Closeable[] r5 = new java.io.Closeable[r0]
            r5[r1] = r2
            com.plv.thirdpart.blankj.utilcode.util.CloseUtils.closeIO(r5)
        L34:
            r5 = 61371(0xefbb, float:8.5999E-41)
            if (r1 == r5) goto L4c
            r5 = 65279(0xfeff, float:9.1475E-41)
            if (r1 == r5) goto L49
            r5 = 65534(0xfffe, float:9.1833E-41)
            if (r1 == r5) goto L46
            java.lang.String r5 = "GBK"
            return r5
        L46:
            java.lang.String r5 = "Unicode"
            return r5
        L49:
            java.lang.String r5 = "UTF-16BE"
            return r5
        L4c:
            java.lang.String r5 = "UTF-8"
            return r5
        L4f:
            java.io.Closeable[] r0 = new java.io.Closeable[r0]
            r0[r1] = r2
            com.plv.thirdpart.blankj.utilcode.util.CloseUtils.closeIO(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.blankj.utilcode.util.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String");
    }

    public static String getFileExtension(String str) {
        if (isSpace(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return (iLastIndexOf == -1 || str.lastIndexOf(File.separator) >= iLastIndexOf) ? "" : str.substring(iLastIndexOf + 1);
    }

    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1L;
        }
        return file.lastModified();
    }

    public static long getFileLength(File file) {
        if (isFile(file)) {
            return file.length();
        }
        return -1L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int getFileLines(File file) throws Throwable {
        int i2;
        int i3;
        BufferedInputStream bufferedInputStream;
        int i4;
        int i5;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e2) {
            e = e2;
            i2 = 1;
        }
        try {
            try {
                byte[] bArr = new byte[1024];
                boolean zEndsWith = LINE_SEP.endsWith("\n");
                try {
                    if (zEndsWith == 0) {
                        i5 = 1;
                        while (true) {
                            int i6 = bufferedInputStream.read(bArr, 0, 1024);
                            if (i6 == -1) {
                                break;
                            }
                            for (int i7 = 0; i7 < i6; i7++) {
                                i5 = i5;
                                if (bArr[i7] == 13) {
                                    i5++;
                                }
                            }
                        }
                    } else {
                        i5 = 1;
                        while (true) {
                            int i8 = bufferedInputStream.read(bArr, 0, 1024);
                            if (i8 == -1) {
                                break;
                            }
                            for (int i9 = 0; i9 < i8; i9++) {
                                i5 = i5;
                                if (bArr[i9] == 10) {
                                    i5++;
                                }
                            }
                        }
                    }
                    CloseUtils.closeIO(bufferedInputStream);
                    i3 = i5;
                } catch (IOException e3) {
                    e = e3;
                    i4 = zEndsWith;
                    bufferedInputStream2 = bufferedInputStream;
                    i2 = i4;
                    e.printStackTrace();
                    CloseUtils.closeIO(bufferedInputStream2);
                    i3 = i2;
                    return i3;
                }
            } catch (IOException e4) {
                e = e4;
                i4 = 1;
            }
            return i3;
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream2 = bufferedInputStream;
            CloseUtils.closeIO(bufferedInputStream2);
            throw th;
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0046: MOVE (r0 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:28:0x0046 */
    public static byte[] getFileMD5(File file) throws Throwable {
        DigestInputStream digestInputStream;
        Closeable closeable;
        Closeable closeable2 = null;
        if (file == null) {
            return null;
        }
        try {
            try {
                digestInputStream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            } catch (IOException e2) {
                e = e2;
                digestInputStream = null;
                e.printStackTrace();
                CloseUtils.closeIO(digestInputStream);
                return null;
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                digestInputStream = null;
                e.printStackTrace();
                CloseUtils.closeIO(digestInputStream);
                return null;
            } catch (Throwable th) {
                th = th;
                CloseUtils.closeIO(closeable2);
                throw th;
            }
            try {
                while (digestInputStream.read(new byte[262144]) > 0) {
                }
                byte[] bArrDigest = digestInputStream.getMessageDigest().digest();
                CloseUtils.closeIO(digestInputStream);
                return bArrDigest;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                CloseUtils.closeIO(digestInputStream);
                return null;
            } catch (NoSuchAlgorithmException e5) {
                e = e5;
                e.printStackTrace();
                CloseUtils.closeIO(digestInputStream);
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable2 = closeable;
            CloseUtils.closeIO(closeable2);
            throw th;
        }
    }

    public static String getFileName(String str) {
        int iLastIndexOf;
        return (isSpace(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? str : str.substring(iLastIndexOf + 1);
    }

    public static String getFileNameNoExtension(String str) {
        if (isSpace(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        return iLastIndexOf2 == -1 ? iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf) : (iLastIndexOf == -1 || iLastIndexOf2 > iLastIndexOf) ? str.substring(iLastIndexOf2 + 1) : str.substring(iLastIndexOf2 + 1, iLastIndexOf);
    }

    public static String getFileSize(File file) {
        long fileLength = getFileLength(file);
        return fileLength == -1 ? "" : byte2FitMemorySize(fileLength);
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static List<File> listFilesInDir(File file) {
        return listFilesInDir(file, false);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter) {
        return listFilesInDirWithFilter(file, fileFilter, false);
    }

    public static boolean moveDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, true);
    }

    public static boolean moveFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, true);
    }

    public static boolean rename(File file, String str) {
        if (file == null || !file.exists() || isSpace(str)) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        return !file2.exists() && file.renameTo(file2);
    }

    public static String getFileMD5ToString(File file) {
        return bytes2HexString(getFileMD5(file));
    }

    public static List<File> listFilesInDir(String str, boolean z2) {
        return listFilesInDir(getFileByPath(str), z2);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z2) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z2);
    }

    public static List<File> listFilesInDir(File file, boolean z2) {
        return listFilesInDirWithFilter(file, new FileFilter() { // from class: com.plv.thirdpart.blankj.utilcode.util.FileUtils.3
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        }, z2);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z2) {
        if (!isDir(file)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
                if (z2 && file2.isDirectory()) {
                    arrayList.addAll(listFilesInDirWithFilter(file2, fileFilter, true));
                }
            }
        }
        return arrayList;
    }
}
