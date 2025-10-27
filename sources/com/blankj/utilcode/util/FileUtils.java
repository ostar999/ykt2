package com.blankj.utilcode.util;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes2.dex */
public final class FileUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");

    public interface OnReplaceListener {
        boolean onReplace(File file, File file2);
    }

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean copy(String str, String str2) {
        return copy(getFileByPath(str), getFileByPath(str2), (OnReplaceListener) null);
    }

    private static boolean copyDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, false);
    }

    private static boolean copyFile(File file, File file2, OnReplaceListener onReplaceListener) {
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
        if (str2.contains(string) || !file.exists() || !file.isDirectory() || !createOrExistsDir(file2)) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            for (File file3 : fileArrListFiles) {
                File file4 = new File(str2 + file3.getName());
                if (file3.isFile()) {
                    if (!copyOrMoveFile(file3, file4, onReplaceListener, z2)) {
                        return false;
                    }
                } else if (file3.isDirectory() && !copyOrMoveDir(file3, file4, onReplaceListener, z2)) {
                    return false;
                }
            }
        }
        return !z2 || deleteDir(file);
    }

    private static boolean copyOrMoveFile(File file, File file2, OnReplaceListener onReplaceListener, boolean z2) {
        if (file != null && file2 != null && !file.equals(file2) && file.exists() && file.isFile()) {
            if (file2.exists()) {
                if (onReplaceListener != null && !onReplaceListener.onReplace(file, file2)) {
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
                if (!UtilsBridge.writeFileFromIS(file2.getAbsolutePath(), new FileInputStream(file))) {
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

    public static boolean createFileByDeleteOldFile(String str) {
        return createFileByDeleteOldFile(getFileByPath(str));
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    public static boolean delete(String str) {
        return delete(getFileByPath(str));
    }

    public static boolean deleteAllInDir(String str) {
        return deleteAllInDir(getFileByPath(str));
    }

    private static boolean deleteDir(File file) {
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
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
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

    private static boolean deleteFile(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    public static boolean deleteFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    private static long getDirLength(File file) {
        long dirLength = 0;
        if (!isDir(file)) {
            return 0L;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            for (File file2 : fileArrListFiles) {
                dirLength += file2.isDirectory() ? getDirLength(file2) : file2.length();
            }
        }
        return dirLength;
    }

    public static String getDirName(File file) {
        return file == null ? "" : getDirName(file.getAbsolutePath());
    }

    private static String getDirSize(File file) {
        long dirLength = getDirLength(file);
        return dirLength == -1 ? "" : UtilsBridge.byte2FitMemorySize(dirLength);
    }

    public static File getFileByPath(String str) {
        if (UtilsBridge.isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    public static String getFileCharsetSimple(String str) {
        return getFileCharsetSimple(getFileByPath(str));
    }

    public static String getFileExtension(File file) {
        return file == null ? "" : getFileExtension(file.getPath());
    }

    public static long getFileLastModified(String str) {
        return getFileLastModified(getFileByPath(str));
    }

    public static long getFileLength(String str) throws IOException {
        if (str.matches("[a-zA-z]+://[^\\s]*")) {
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                httpsURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpsURLConnection.connect();
                if (httpsURLConnection.getResponseCode() == 200) {
                    return httpsURLConnection.getContentLength();
                }
                return -1L;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(str));
    }

    public static int getFileLines(String str) {
        return getFileLines(getFileByPath(str));
    }

    public static byte[] getFileMD5(String str) {
        return getFileMD5(getFileByPath(str));
    }

    public static String getFileMD5ToString(String str) {
        return getFileMD5ToString(UtilsBridge.isSpace(str) ? null : new File(str));
    }

    public static String getFileName(File file) {
        return file == null ? "" : getFileName(file.getAbsolutePath());
    }

    public static String getFileNameNoExtension(File file) {
        return file == null ? "" : getFileNameNoExtension(file.getPath());
    }

    private static String getFileSize(File file) {
        long fileLength = getFileLength(file);
        return fileLength == -1 ? "" : UtilsBridge.byte2FitMemorySize(fileLength);
    }

    public static long getFsAvailableSize(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        StatFs statFs = new StatFs(str);
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    public static long getFsTotalSize(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        StatFs statFs = new StatFs(str);
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }

    public static long getLength(String str) {
        return getLength(getFileByPath(str));
    }

    public static String getSize(String str) {
        return getSize(getFileByPath(str));
    }

    public static boolean isDir(String str) {
        return isDir(getFileByPath(str));
    }

    public static boolean isFile(String str) {
        return isFile(getFileByPath(str));
    }

    public static boolean isFileExists(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return isFileExists(file.getAbsolutePath());
    }

    private static boolean isFileExistsApi29(String str) throws IOException {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = Utils.getApp().getContentResolver().openAssetFileDescriptor(Uri.parse(str), "r");
                if (assetFileDescriptorOpenAssetFileDescriptor == null) {
                    return false;
                }
                try {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                    return true;
                } catch (IOException unused) {
                    return true;
                }
            } catch (FileNotFoundException unused2) {
            }
        }
        return false;
    }

    public static boolean isUtf8(String str) {
        return isUtf8(getFileByPath(str));
    }

    public static List<File> listFilesInDir(String str) {
        return listFilesInDir(str, (Comparator<File>) null);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    private static List<File> listFilesInDirWithFilterInner(File file, FileFilter fileFilter, boolean z2) {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        if (isDir(file) && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
                if (z2 && file2.isDirectory()) {
                    arrayList.addAll(listFilesInDirWithFilterInner(file2, fileFilter, true));
                }
            }
        }
        return arrayList;
    }

    public static boolean move(String str, String str2) {
        return move(getFileByPath(str), getFileByPath(str2), (OnReplaceListener) null);
    }

    public static boolean moveDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, true);
    }

    public static boolean moveFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, true);
    }

    public static void notifySystemToScan(String str) {
        notifySystemToScan(getFileByPath(str));
    }

    public static boolean rename(String str, String str2) {
        return rename(getFileByPath(str), str2);
    }

    public static boolean copy(String str, String str2, OnReplaceListener onReplaceListener) {
        return copy(getFileByPath(str), getFileByPath(str2), onReplaceListener);
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

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        return file.isDirectory() ? deleteDir(file) : deleteFile(file);
    }

    public static boolean deleteAllInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.2
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(File file, FileFilter fileFilter) {
        if (file == null || fileFilter == null) {
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

    public static String getDirName(String str) {
        int iLastIndexOf;
        return (UtilsBridge.isSpace(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? "" : str.substring(0, iLastIndexOf + 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0054 A[RETURN] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getFileCharsetSimple(java.io.File r3) throws java.lang.Throwable {
        /*
            if (r3 != 0) goto L5
            java.lang.String r3 = ""
            return r3
        L5:
            boolean r0 = isUtf8(r3)
            if (r0 == 0) goto Le
            java.lang.String r3 = "UTF-8"
            return r3
        Le:
            r0 = 0
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            int r3 = r1.read()     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30
            int r3 = r3 << 8
            int r0 = r1.read()     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30
            int r3 = r3 + r0
            r1.close()     // Catch: java.io.IOException -> L28
            goto L44
        L28:
            r0 = move-exception
            r0.printStackTrace()
            goto L44
        L2d:
            r3 = move-exception
            r0 = r1
            goto L57
        L30:
            r3 = move-exception
            r0 = r1
            goto L36
        L33:
            r3 = move-exception
            goto L57
        L35:
            r3 = move-exception
        L36:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L33
            if (r0 == 0) goto L43
            r0.close()     // Catch: java.io.IOException -> L3f
            goto L43
        L3f:
            r3 = move-exception
            r3.printStackTrace()
        L43:
            r3 = 0
        L44:
            r0 = 65279(0xfeff, float:9.1475E-41)
            if (r3 == r0) goto L54
            r0 = 65534(0xfffe, float:9.1833E-41)
            if (r3 == r0) goto L51
            java.lang.String r3 = "GBK"
            return r3
        L51:
            java.lang.String r3 = "Unicode"
            return r3
        L54:
            java.lang.String r3 = "UTF-16BE"
            return r3
        L57:
            if (r0 == 0) goto L61
            r0.close()     // Catch: java.io.IOException -> L5d
            goto L61
        L5d:
            r0 = move-exception
            r0.printStackTrace()
        L61:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String");
    }

    public static String getFileExtension(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0049 -> B:45:0x005e). Please report as a decompilation issue!!! */
    public static int getFileLines(File file) throws Throwable {
        int i2 = 1;
        ?? r12 = 0;
        r12 = 0;
        r12 = 0;
        try {
            try {
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    try {
                        byte[] bArr = new byte[1024];
                        if (!LINE_SEP.endsWith("\n")) {
                            while (true) {
                                int i3 = bufferedInputStream.read(bArr, 0, 1024);
                                if (i3 == -1) {
                                    break;
                                }
                                for (int i4 = 0; i4 < i3; i4++) {
                                    if (bArr[i4] == 13) {
                                        i2++;
                                    }
                                }
                            }
                        } else {
                            while (true) {
                                int i5 = bufferedInputStream.read(bArr, 0, 1024);
                                if (i5 == -1) {
                                    break;
                                }
                                for (int i6 = 0; i6 < i5; i6++) {
                                    if (bArr[i6] == 10) {
                                        i2++;
                                    }
                                }
                            }
                        }
                        bufferedInputStream.close();
                        r12 = bArr;
                    } catch (IOException e2) {
                        e = e2;
                        r12 = bufferedInputStream;
                        e.printStackTrace();
                        if (r12 != 0) {
                            r12.close();
                            r12 = r12;
                        }
                        return i2;
                    } catch (Throwable th) {
                        th = th;
                        r12 = bufferedInputStream;
                        if (r12 != 0) {
                            try {
                                r12.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
                r12 = r12;
            }
            return i2;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0048: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:34:0x0048 */
    /* JADX WARN: Removed duplicated region for block: B:44:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getFileMD5(java.io.File r3) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.lang.String r3 = "MD5"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.security.DigestInputStream r2 = new java.security.DigestInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r3 = 262144(0x40000, float:3.67342E-40)
            byte[] r3 = new byte[r3]     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
        L18:
            int r1 = r2.read(r3)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            if (r1 > 0) goto L18
            java.security.MessageDigest r3 = r2.getMessageDigest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            byte[] r3 = r3.digest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r2.close()     // Catch: java.io.IOException -> L2a
            goto L2e
        L2a:
            r0 = move-exception
            r0.printStackTrace()
        L2e:
            return r3
        L2f:
            r3 = move-exception
            goto L39
        L31:
            r3 = move-exception
            goto L39
        L33:
            r3 = move-exception
            goto L49
        L35:
            r3 = move-exception
            goto L38
        L37:
            r3 = move-exception
        L38:
            r2 = r0
        L39:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r2 == 0) goto L46
            r2.close()     // Catch: java.io.IOException -> L42
            goto L46
        L42:
            r3 = move-exception
            r3.printStackTrace()
        L46:
            return r0
        L47:
            r3 = move-exception
            r0 = r2
        L49:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r0 = move-exception
            r0.printStackTrace()
        L53:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileMD5(java.io.File):byte[]");
    }

    public static String getFileName(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf == -1 ? str : str.substring(iLastIndexOf + 1);
    }

    public static String getFileNameNoExtension(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        return iLastIndexOf2 == -1 ? iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf) : (iLastIndexOf == -1 || iLastIndexOf2 > iLastIndexOf) ? str.substring(iLastIndexOf2 + 1) : str.substring(iLastIndexOf2 + 1, iLastIndexOf);
    }

    public static long getLength(File file) {
        if (file == null) {
            return 0L;
        }
        return file.isDirectory() ? getDirLength(file) : getFileLength(file);
    }

    public static String getSize(File file) {
        return file == null ? "" : file.isDirectory() ? getDirSize(file) : getFileSize(file);
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean isUtf8(File file) throws Throwable {
        if (file == null) {
            return false;
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                byte[] bArr = new byte[24];
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    int i2 = bufferedInputStream2.read(bArr);
                    if (i2 == -1) {
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        return false;
                    }
                    byte[] bArr2 = new byte[i2];
                    System.arraycopy(bArr, 0, bArr2, 0, i2);
                    boolean z2 = isUtf8(bArr2) == 100;
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return z2;
                } catch (IOException e4) {
                    e = e4;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e = e7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<File> listFilesInDir(File file) {
        return listFilesInDir(file, (Comparator<File>) null);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter) {
        return listFilesInDirWithFilter(file, fileFilter, false, (Comparator<File>) null);
    }

    public static boolean move(String str, String str2, OnReplaceListener onReplaceListener) {
        return move(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static void notifySystemToScan(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.parse("file://" + file.getAbsolutePath()));
        Utils.getApp().sendBroadcast(intent);
    }

    public static boolean rename(File file, String str) {
        if (file == null || !file.exists() || UtilsBridge.isSpace(str)) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        return !file2.exists() && file.renameTo(file2);
    }

    public static boolean copy(File file, File file2) {
        return copy(file, file2, (OnReplaceListener) null);
    }

    public static String getFileMD5ToString(File file) {
        return UtilsBridge.bytes2HexString(getFileMD5(file));
    }

    public static boolean isFileExists(String str) {
        File fileByPath = getFileByPath(str);
        if (fileByPath == null) {
            return false;
        }
        if (fileByPath.exists()) {
            return true;
        }
        return isFileExistsApi29(str);
    }

    public static List<File> listFilesInDir(String str, Comparator<File> comparator) {
        return listFilesInDir(getFileByPath(str), false, comparator);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, Comparator<File> comparator) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, comparator);
    }

    public static boolean move(File file, File file2) {
        return move(file, file2, (OnReplaceListener) null);
    }

    public static boolean copy(File file, File file2, OnReplaceListener onReplaceListener) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return copyDir(file, file2, onReplaceListener);
        }
        return copyFile(file, file2, onReplaceListener);
    }

    public static List<File> listFilesInDir(File file, Comparator<File> comparator) {
        return listFilesInDir(file, false, comparator);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, Comparator<File> comparator) {
        return listFilesInDirWithFilter(file, fileFilter, false, comparator);
    }

    public static boolean move(File file, File file2, OnReplaceListener onReplaceListener) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return moveDir(file, file2, onReplaceListener);
        }
        return moveFile(file, file2, onReplaceListener);
    }

    public static List<File> listFilesInDir(String str, boolean z2) {
        return listFilesInDir(getFileByPath(str), z2);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z2) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z2);
    }

    public static List<File> listFilesInDir(File file, boolean z2) {
        return listFilesInDir(file, z2, (Comparator<File>) null);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z2) {
        return listFilesInDirWithFilter(file, fileFilter, z2, (Comparator<File>) null);
    }

    public static List<File> listFilesInDir(String str, boolean z2, Comparator<File> comparator) {
        return listFilesInDir(getFileByPath(str), z2, comparator);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z2, Comparator<File> comparator) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z2, comparator);
    }

    public static List<File> listFilesInDir(File file, boolean z2, Comparator<File> comparator) {
        return listFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.3
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        }, z2, comparator);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z2, Comparator<File> comparator) {
        List<File> listListFilesInDirWithFilterInner = listFilesInDirWithFilterInner(file, fileFilter, z2);
        if (comparator != null) {
            Collections.sort(listListFilesInDirWithFilterInner, comparator);
        }
        return listListFilesInDirWithFilterInner;
    }

    private static long getFileLength(File file) {
        if (isFile(file)) {
            return file.length();
        }
        return -1L;
    }

    private static int isUtf8(byte[] bArr) {
        if (bArr.length > 3 && bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) {
            return 100;
        }
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int length2 = 0;
        while (i2 < length) {
            byte b3 = bArr[i2];
            if ((b3 & (-1)) == -1 || (b3 & (-2)) == -2) {
                return 0;
            }
            if (length2 == 0) {
                if ((b3 & 127) == b3 && b3 != 0) {
                    i3++;
                } else if ((b3 & (-64)) == -64) {
                    int i5 = length2;
                    for (int i6 = 0; i6 < 8; i6++) {
                        byte b4 = (byte) (128 >> i6);
                        if ((bArr[i2] & b4) != b4) {
                            break;
                        }
                        i5 = i6;
                    }
                    i4++;
                    length2 = i5;
                }
                i2++;
            } else {
                if (bArr.length - i2 <= length2) {
                    length2 = bArr.length - i2;
                }
                boolean z2 = false;
                for (int i7 = 0; i7 < length2; i7++) {
                    byte b5 = bArr[i2 + i7];
                    if ((b5 & (-128)) != -128) {
                        if ((b5 & 127) == b5 && bArr[i2] != 0) {
                            i3++;
                        }
                        z2 = true;
                    }
                }
                if (z2) {
                    i4--;
                    i2++;
                } else {
                    i4 += length2;
                    i2 += length2;
                }
                length2 = 0;
            }
        }
        if (i3 == length) {
            return 100;
        }
        return (int) (((i4 + i3) / length) * 100.0f);
    }
}
