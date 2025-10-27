package org.wrtca.util;

import android.os.Environment;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes9.dex */
public class FileUtils {
    public static String calculateMD5(File file) throws NoSuchAlgorithmException, IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        try {
                            int i2 = fileInputStream.read(bArr);
                            if (i2 <= 0) {
                                break;
                            }
                            messageDigest.update(bArr, 0, i2);
                        } catch (IOException e2) {
                            throw new RuntimeException("Unable to process file for MD5", e2);
                        }
                    } finally {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            Log.e("FileUtils", "Exception on closing MD5 input stream", e3);
                        }
                    }
                }
                return String.format("%32s", new BigInteger(1, messageDigest.digest()).toString(16)).replace(' ', '0');
            } catch (FileNotFoundException e4) {
                Log.e("FileUtils", "Exception while getting FileInputStream", e4);
                return null;
            }
        } catch (NoSuchAlgorithmException e5) {
            Log.e("FileUtils", "Exception while getting digest", e5);
            return null;
        }
    }

    public static boolean checkFile(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return false;
        }
        if (file.isDirectory()) {
            return true;
        }
        return file.isFile() && file.length() > 0;
    }

    public static String concatPath(String... strArr) {
        StringBuilder sb = new StringBuilder();
        if (strArr != null) {
            for (String str : strArr) {
                if (str != null && str.length() > 0) {
                    int length = sb.length();
                    boolean z2 = length > 0 && sb.charAt(length + (-1)) == File.separatorChar;
                    char cCharAt = str.charAt(0);
                    char c3 = File.separatorChar;
                    boolean z3 = cCharAt == c3;
                    if (z2 && z3) {
                        sb.append(str.substring(1));
                    } else if (z2 || z3) {
                        sb.append(str);
                    } else {
                        sb.append(c3);
                        sb.append(str);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void deleteCacheFile(String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (!file2.isDirectory() && (file2.getName().contains(".ts") || file2.getName().contains("temp"))) {
                    file2.delete();
                }
            }
        }
    }

    public static void deleteCacheFile2TS(String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (!file2.isDirectory() && file2.getName().contains(".ts")) {
                    file2.delete();
                }
            }
        }
    }

    public static void deleteDir(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    deleteDir(file2);
                }
                file2.delete();
            }
            file.delete();
        }
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0050, code lost:
    
        if (r4 == 0) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005d, code lost:
    
        if (r4 == 0) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x006a, code lost:
    
        if (r4 == 0) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x006c, code lost:
    
        r4.close();
        r1 = r1;
        r4 = r4;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0075 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean fileCopy(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L46 java.io.IOException -> L53 java.io.FileNotFoundException -> L60
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L46 java.io.IOException -> L53 java.io.FileNotFoundException -> L60
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33 java.io.IOException -> L38 java.io.FileNotFoundException -> L3d
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33 java.io.IOException -> L38 java.io.FileNotFoundException -> L3d
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2a java.io.IOException -> L2c java.io.FileNotFoundException -> L2e
        L10:
            int r1 = r2.read(r5)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2a java.io.IOException -> L2c java.io.FileNotFoundException -> L2e
            r3 = -1
            if (r1 == r3) goto L1b
            r4.write(r5, r0, r1)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2a java.io.IOException -> L2c java.io.FileNotFoundException -> L2e
            goto L10
        L1b:
            r4.flush()     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2a java.io.IOException -> L2c java.io.FileNotFoundException -> L2e
            r2.close()     // Catch: java.io.IOException -> L21
        L21:
            r0 = 1
            r4.close()     // Catch: java.io.IOException -> L6f
            goto L6f
        L27:
            r5 = move-exception
            goto L72
        L2a:
            r5 = move-exception
            goto L36
        L2c:
            r5 = move-exception
            goto L3b
        L2e:
            r5 = move-exception
            goto L40
        L30:
            r4 = move-exception
            r5 = r4
            goto L73
        L33:
            r4 = move-exception
            r5 = r4
            r4 = r1
        L36:
            r1 = r2
            goto L48
        L38:
            r4 = move-exception
            r5 = r4
            r4 = r1
        L3b:
            r1 = r2
            goto L55
        L3d:
            r4 = move-exception
            r5 = r4
            r4 = r1
        L40:
            r1 = r2
            goto L62
        L42:
            r4 = move-exception
            r5 = r4
            r4 = r1
            goto L71
        L46:
            r5 = move-exception
            r4 = r1
        L48:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L70
            if (r1 == 0) goto L50
            r1.close()     // Catch: java.io.IOException -> L50
        L50:
            if (r4 == 0) goto L6f
            goto L6c
        L53:
            r5 = move-exception
            r4 = r1
        L55:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L70
            if (r1 == 0) goto L5d
            r1.close()     // Catch: java.io.IOException -> L5d
        L5d:
            if (r4 == 0) goto L6f
            goto L6c
        L60:
            r5 = move-exception
            r4 = r1
        L62:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L70
            if (r1 == 0) goto L6a
            r1.close()     // Catch: java.io.IOException -> L6a
        L6a:
            if (r4 == 0) goto L6f
        L6c:
            r4.close()     // Catch: java.io.IOException -> L6f
        L6f:
            return r0
        L70:
            r5 = move-exception
        L71:
            r2 = r1
        L72:
            r1 = r4
        L73:
            if (r2 == 0) goto L78
            r2.close()     // Catch: java.io.IOException -> L78
        L78:
            if (r1 == 0) goto L7d
            r1.close()     // Catch: java.io.IOException -> L7d
        L7d:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.util.FileUtils.fileCopy(java.lang.String, java.lang.String):boolean");
    }

    public static String getExternalStorageDirectory() {
        String path = Environment.getExternalStorageDirectory().getPath();
        return DeviceUtils.isZte() ? path.replace("/sdcard", "/sdcard-ext") : path;
    }

    public static String getFileExtension(String str) {
        int iLastIndexOf;
        return ((str == null || (iLastIndexOf = str.lastIndexOf(StrPool.DOT)) < 0 || iLastIndexOf >= str.length() + (-1)) ? "" : str.substring(iLastIndexOf + 1)).toLowerCase();
    }

    public static long getFileSize(String str) {
        long length;
        try {
            length = new File(str).length();
        } catch (Exception e2) {
            e2.printStackTrace();
            length = 0;
        }
        return (length < 0 ? null : Long.valueOf(length)).longValue();
    }

    public static String getFileType(String str, String str2) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(str);
        return contentTypeFor == null ? str2 : contentTypeFor;
    }

    public static String readFile(File file, String str) throws Throwable {
        IOException e2;
        Throwable th;
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return sb.toString();
        }
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), str));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            bufferedReader.close();
                            try {
                                bufferedReader.close();
                                return sb.toString();
                            } catch (IOException e3) {
                                throw new RuntimeException("IOException occurred. ", e3);
                            }
                        }
                        if (!sb.toString().equals("")) {
                            sb.append("\r\n");
                        }
                        sb.append(line);
                    } catch (IOException e4) {
                        e2 = e4;
                        throw new RuntimeException("IOException occurred. ", e2);
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e5) {
                                throw new RuntimeException("IOException occurred. ", e5);
                            }
                        }
                        throw th;
                    }
                }
            } catch (IOException e6) {
                e2 = e6;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    public static boolean checkFile(String str) {
        if (!StringUtils.isNotEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        if (file.isDirectory()) {
            return true;
        }
        return file.isFile() && file.length() > 0;
    }

    public static boolean deleteFile(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        return deleteFile(new File(str));
    }

    public static String getFileType(String str) {
        return getFileType(str, "application/octet-stream");
    }

    public static long getFileSize(File file) {
        if (file == null) {
            return 0L;
        }
        return file.length();
    }

    public static void deleteDir(String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        deleteDir(new File(str));
    }

    public static String calculateMD5(File file, int i2, int i3) throws NoSuchAlgorithmException, IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[8192];
                if (i2 > 0) {
                    try {
                        try {
                            fileInputStream.skip(i2);
                        } finally {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                                Log.e("FileUtils", "Exception on closing MD5 input stream", e2);
                            }
                        }
                    } catch (IOException e3) {
                        throw new RuntimeException("Unable to process file for MD5", e3);
                    }
                }
                int iMin = Math.min(8192, i3);
                int i4 = 0;
                while (true) {
                    int i5 = fileInputStream.read(bArr, 0, iMin);
                    if (i5 <= 0 || i4 >= i3) {
                        break;
                    }
                    messageDigest.update(bArr, 0, i5);
                    i4 += i5;
                    if (i4 + 8192 > i3) {
                        iMin = i3 - i4;
                    }
                }
                return String.format("%32s", new BigInteger(1, messageDigest.digest()).toString(16)).replace(' ', '0');
            } catch (FileNotFoundException e4) {
                Log.e("FileUtils", "Exception while getting FileInputStream", e4);
                return null;
            }
        } catch (NoSuchAlgorithmException e5) {
            Log.e("FileUtils", "Exception while getting digest", e5);
            return null;
        }
    }

    public static String readFile(String str, String str2) {
        return readFile(new File(str), str2);
    }

    public static String readFile(File file) {
        return readFile(file, "utf-8");
    }
}
