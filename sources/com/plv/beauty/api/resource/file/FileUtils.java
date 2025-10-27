package com.plv.beauty.api.resource.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipInputStream;

/* loaded from: classes4.dex */
public class FileUtils {
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static boolean checkFileMD5(String str, String str2) {
        try {
            return getHash(str2) == str;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String getHash(String str) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(str);
        byte[] bArr = new byte[1024];
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        while (true) {
            int i2 = fileInputStream.read(bArr);
            if (i2 <= 0) {
                fileInputStream.close();
                return toHexString(messageDigest.digest());
            }
            messageDigest.update(bArr, 0, i2);
        }
    }

    public static boolean prepareFilePath(String str) {
        File file = new File(str);
        if (!file.exists() || file.delete()) {
            return file.getParentFile().exists() || file.getParentFile().mkdirs();
        }
        return false;
    }

    public static String removeFileNameExtension(String str) {
        int iLastIndexOf;
        return (str == null || str.length() <= 0 || (iLastIndexOf = str.lastIndexOf(46)) <= -1 || iLastIndexOf >= str.length()) ? str : str.substring(0, iLastIndexOf);
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append(hexChar[(bArr[i2] & 240) >>> 4]);
            sb.append(hexChar[bArr[i2] & 15]);
        }
        return sb.toString();
    }

    public static boolean unzipFile(String str, File file) {
        try {
            return unzipFile(new ZipInputStream(new FileInputStream(new File(str))), file);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean validateFileMD5(String str, String str2) throws NoSuchAlgorithmException, IOException {
        File file = new File(str2);
        if (!str.isEmpty() && file.exists()) {
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
                                e2.printStackTrace();
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                return false;
                            }
                        } finally {
                            try {
                                fileInputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                    }
                    String strReplace = String.format("%32s", new BigInteger(1, messageDigest.digest()).toString(16)).replace(' ', '0');
                    if (strReplace.isEmpty()) {
                        return false;
                    }
                    return strReplace.equalsIgnoreCase(str);
                } catch (FileNotFoundException e5) {
                    e5.printStackTrace();
                    return false;
                }
            } catch (NoSuchAlgorithmException e6) {
                e6.printStackTrace();
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006c, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
    
        r7.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean unzipFile(java.util.zip.ZipInputStream r7, java.io.File r8) throws java.io.IOException {
        /*
            r0 = 0
            boolean r1 = r8.exists()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            if (r1 == 0) goto La
            r8.delete()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
        La:
            r8.mkdirs()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            if (r7 != 0) goto L1a
            if (r7 == 0) goto L19
            r7.close()     // Catch: java.io.IOException -> L15
            goto L19
        L15:
            r7 = move-exception
            r7.printStackTrace()
        L19:
            return r0
        L1a:
            java.util.zip.ZipEntry r1 = r7.getNextEntry()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r2 = 1
            if (r1 == 0) goto L66
            java.lang.String r3 = r1.getName()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            boolean r4 = r1.isDirectory()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            if (r4 == 0) goto L3d
            int r4 = r3.length()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            int r4 = r4 - r2
            java.lang.String r3 = r3.substring(r0, r4)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            java.io.File r4 = new java.io.File     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r4.<init>(r8, r3)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r4.mkdirs()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            goto L66
        L3d:
            java.io.File r4 = new java.io.File     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r4.<init>(r8, r3)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            java.io.File r3 = r4.getParentFile()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r3.mkdirs()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r4.createNewFile()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
        L55:
            int r5 = r7.read(r4)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r6 = -1
            if (r5 == r6) goto L63
            r3.write(r4, r0, r5)     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            r3.flush()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
            goto L55
        L63:
            r3.close()     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L73
        L66:
            if (r1 != 0) goto L1a
            r7.close()     // Catch: java.io.IOException -> L6c
            goto L70
        L6c:
            r7 = move-exception
            r7.printStackTrace()
        L70:
            return r2
        L71:
            r8 = move-exception
            goto L82
        L73:
            r8 = move-exception
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L71
            if (r7 == 0) goto L81
            r7.close()     // Catch: java.io.IOException -> L7d
            goto L81
        L7d:
            r7 = move-exception
            r7.printStackTrace()
        L81:
            return r0
        L82:
            if (r7 == 0) goto L8c
            r7.close()     // Catch: java.io.IOException -> L88
            goto L8c
        L88:
            r7 = move-exception
            r7.printStackTrace()
        L8c:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.beauty.api.resource.file.FileUtils.unzipFile(java.util.zip.ZipInputStream, java.io.File):boolean");
    }
}
