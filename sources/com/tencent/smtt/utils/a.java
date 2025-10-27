package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import cn.hutool.core.text.StrPool;
import com.tencent.smtt.sdk.TbsExtensionFunctionManager;
import com.tencent.smtt.sdk.TbsPVConfig;
import com.tencent.smtt.sdk.TbsShareManager;
import java.io.File;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class a {
    public static int a(Context context, File file) {
        return a(context, file, 0);
    }

    public static int a(Context context, File file, int i2) {
        try {
            return a(context, file, !TbsExtensionFunctionManager.getInstance().canUseFunction(context, TbsExtensionFunctionManager.DISABLE_GET_APK_VERSION_SWITCH_FILE_NAME), i2);
        } catch (Exception unused) {
            TbsLog.i("ApkUtil", "getApkVersion Failed");
            return 0;
        }
    }

    public static int a(Context context, File file, boolean z2, int i2) {
        if (file != null) {
            try {
                if (file.exists()) {
                    boolean zContains = file.getName().contains("tbs.org");
                    boolean zContains2 = file.getName().contains("x5.tbs.decouple");
                    if (zContains || zContains2) {
                        int iA = a(zContains2, file, i2);
                        if (iA > 0) {
                            return iA;
                        }
                        if (!TbsShareManager.isThirdPartyApp(context) && !file.getAbsolutePath().contains(context.getApplicationInfo().packageName)) {
                            return 0;
                        }
                    }
                    int i3 = Build.VERSION.SDK_INT;
                    boolean z3 = (i3 == 23 || i3 == 25) && Build.MANUFACTURER.toLowerCase().contains("mi");
                    TbsPVConfig.releaseInstance();
                    int readApk = TbsPVConfig.getInstance(context).getReadApk();
                    if (readApk == 1) {
                        z2 = false;
                        z3 = false;
                    } else if (readApk == 2) {
                        return 0;
                    }
                    if (z2 || z3) {
                        int iB = b(file);
                        if (iB > 0) {
                            return iB;
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (file == null || !file.exists()) {
            return 0;
        }
        try {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 1);
            if (packageArchiveInfo != null) {
                return packageArchiveInfo.versionCode;
            }
            return 0;
        } catch (Throwable th2) {
            th2.printStackTrace();
            return -1;
        }
    }

    private static int a(boolean z2, File file, int i2) {
        try {
            File parentFile = file.getParentFile();
            if (parentFile == null) {
                return -1;
            }
            File[] fileArrListFiles = parentFile.listFiles();
            Pattern patternCompile = Pattern.compile(a(z2, i2) + "(.*)");
            for (File file2 : fileArrListFiles) {
                if (patternCompile.matcher(file2.getName()).find() && file2.isFile() && file2.exists()) {
                    return Integer.parseInt(file2.getName().substring(file2.getName().lastIndexOf(StrPool.DOT) + 1));
                }
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0068: MOVE (r3 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:32:0x0068 */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.io.File r10) {
        /*
            r0 = 16
            char[] r1 = new char[r0]
            r1 = {x0074: FILL_ARRAY_DATA , data: [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102} // fill-array
            r2 = 32
            char[] r2 = new char[r2]
            r3 = 0
            java.lang.String r4 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r4)     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            r5.<init>(r10)     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            r10 = 8192(0x2000, float:1.148E-41)
            byte[] r10 = new byte[r10]     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
        L1b:
            int r6 = r5.read(r10)     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r7 = -1
            r8 = 0
            if (r6 == r7) goto L27
            r4.update(r10, r8, r6)     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            goto L1b
        L27:
            byte[] r10 = r4.digest()     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r4 = r8
        L2c:
            if (r8 >= r0) goto L45
            r6 = r10[r8]     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            int r7 = r4 + 1
            int r9 = r6 >>> 4
            r9 = r9 & 15
            char r9 = r1[r9]     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r2[r4] = r9     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            int r4 = r7 + 1
            r6 = r6 & 15
            char r6 = r1[r6]     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r2[r7] = r6     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            int r8 = r8 + 1
            goto L2c
        L45:
            java.lang.String r10 = new java.lang.String     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r10.<init>(r2)     // Catch: java.lang.Exception -> L53 java.lang.Throwable -> L67
            r5.close()     // Catch: java.io.IOException -> L4e
            goto L52
        L4e:
            r0 = move-exception
            r0.printStackTrace()
        L52:
            return r10
        L53:
            r10 = move-exception
            goto L59
        L55:
            r10 = move-exception
            goto L69
        L57:
            r10 = move-exception
            r5 = r3
        L59:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L67
            if (r5 == 0) goto L66
            r5.close()     // Catch: java.io.IOException -> L62
            goto L66
        L62:
            r10 = move-exception
            r10.printStackTrace()
        L66:
            return r3
        L67:
            r10 = move-exception
            r3 = r5
        L69:
            if (r3 == 0) goto L73
            r3.close()     // Catch: java.io.IOException -> L6f
            goto L73
        L6f:
            r0 = move-exception
            r0.printStackTrace()
        L73:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.a.a(java.io.File):java.lang.String");
    }

    public static final String a(boolean z2) {
        return a(z2, 0);
    }

    public static final String a(boolean z2, int i2) {
        return i2 == 64 ? true : i2 == 32 ? false : b.c() ? z2 ? "x5.64.decouple.backup" : "x5.64.backup" : z2 ? "x5.decouple.backup" : "x5.backup";
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
    
        r1 = r1[1].trim();
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "getApkVersionByReadFile version is " + r1);
        r1 = java.lang.Integer.parseInt(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0087, code lost:
    
        r7.close();
        r2.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c4 A[Catch: all -> 0x00b4, Exception -> 0x00c7, TRY_LEAVE, TryCatch #3 {, blocks: (B:16:0x0087, B:17:0x008d, B:19:0x008f, B:20:0x0092, B:40:0x00b9, B:34:0x00b0, B:45:0x00bf, B:47:0x00c4, B:48:0x00c7), top: B:56:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int b(java.io.File r7) {
        /*
            java.lang.String r0 = "TbsDownload"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "getApkVersionByReadFile"
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = "exists:"
            r1.append(r2)
            boolean r2 = r7.exists()
            r1.append(r2)
            java.lang.String r2 = ";canread:"
            r1.append(r2)
            boolean r2 = r7.canRead()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
            java.lang.Class<com.tencent.smtt.utils.a> r0 = com.tencent.smtt.utils.a.class
            monitor-enter(r0)
            r1 = 0
            java.util.jar.JarFile r2 = new java.util.jar.JarFile     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La7
            r2.<init>(r7)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La7
            java.lang.String r7 = "assets/webkit/tbs.conf"
            java.util.jar.JarEntry r7 = r2.getJarEntry(r7)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
            java.io.InputStream r7 = r2.getInputStream(r7)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
            r3.<init>(r7)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
            r7.<init>(r3)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9d
        L4b:
            java.lang.String r1 = r7.readLine()     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            if (r1 == 0) goto L8f
            java.lang.String r3 = "tbs_core_version"
            boolean r3 = r1.contains(r3)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            if (r3 == 0) goto L4b
            java.lang.String r3 = "="
            java.lang.String[] r1 = r1.split(r3)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            if (r1 == 0) goto L4b
            int r3 = r1.length     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            r4 = 2
            if (r3 != r4) goto L4b
            r3 = 1
            r1 = r1[r3]     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            java.lang.String r1 = r1.trim()     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            java.lang.String r3 = "TbsDownload"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            r4.<init>()     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            java.lang.String r5 = "getApkVersionByReadFile version is "
            r4.append(r5)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            r4.append(r1)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            com.tencent.smtt.utils.TbsLog.i(r3, r4)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Exception -> L96 java.lang.Throwable -> Lbc
            r7.close()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> Lb4
            r2.close()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> Lb4
        L8d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb4
            return r1
        L8f:
            r7.close()     // Catch: java.lang.Throwable -> Lb4 java.lang.Exception -> Lb9
        L92:
            r2.close()     // Catch: java.lang.Throwable -> Lb4 java.lang.Exception -> Lb9
            goto Lb9
        L96:
            r1 = move-exception
            goto Lab
        L98:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
            goto Lbd
        L9d:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
            goto Lab
        La2:
            r7 = move-exception
            r2 = r1
            r1 = r7
            r7 = r2
            goto Lbd
        La7:
            r7 = move-exception
            r2 = r1
            r1 = r7
            r7 = r2
        Lab:
            com.tencent.smtt.utils.TbsLog.i(r1)     // Catch: java.lang.Throwable -> Lbc
            if (r7 == 0) goto Lb6
            r7.close()     // Catch: java.lang.Throwable -> Lb4 java.lang.Exception -> Lb9
            goto Lb6
        Lb4:
            r7 = move-exception
            goto Lc8
        Lb6:
            if (r2 == 0) goto Lb9
            goto L92
        Lb9:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb4
            r7 = -1
            return r7
        Lbc:
            r1 = move-exception
        Lbd:
            if (r7 == 0) goto Lc2
            r7.close()     // Catch: java.lang.Throwable -> Lb4 java.lang.Exception -> Lc7
        Lc2:
            if (r2 == 0) goto Lc7
            r2.close()     // Catch: java.lang.Throwable -> Lb4 java.lang.Exception -> Lc7
        Lc7:
            throw r1     // Catch: java.lang.Throwable -> Lb4
        Lc8:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb4
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.a.b(java.io.File):int");
    }
}
