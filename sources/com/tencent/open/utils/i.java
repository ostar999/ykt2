package com.tencent.open.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Environment;
import android.webkit.WebSettings;
import cn.hutool.core.text.StrPool;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class i {
    public static String a(int i2) {
        if (i2 == 10103) {
            return "shareToQQ";
        }
        if (i2 == 10104) {
            return "shareToQzone";
        }
        if (i2 == 10105) {
            return "addToQQFavorites";
        }
        if (i2 == 10106) {
            return "sendToMyComputer";
        }
        if (i2 == 10107) {
            return "shareToTroopBar";
        }
        if (i2 == 11101) {
            return "action_login";
        }
        if (i2 == 10100) {
            return "action_request";
        }
        return null;
    }

    public static String a(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static String b(Context context, String str) throws NoSuchAlgorithmException {
        SLog.v("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString");
        String strA = "";
        try {
            String packageName = context.getPackageName();
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(signatureArr[0].toByteArray());
            String strA2 = k.a(messageDigest.digest());
            messageDigest.reset();
            SLog.v("openSDK_LOG.SystemUtils", "-->sign: " + strA2);
            messageDigest.update(k.j(packageName + StrPool.UNDERLINE + strA2 + StrPool.UNDERLINE + str + ""));
            strA = k.a(messageDigest.digest());
            messageDigest.reset();
            StringBuilder sb = new StringBuilder();
            sb.append("-->signEncryped: ");
            sb.append(strA);
            SLog.v("openSDK_LOG.SystemUtils", sb.toString());
            return strA;
        } catch (Exception e2) {
            e2.printStackTrace();
            SLog.e("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString error", e2);
            return strA;
        }
    }

    public static int c(Context context, String str) {
        return a(a(context, "com.tencent.mobileqq"), str);
    }

    public static int d(Context context, String str) {
        return a(a(context, Constants.PACKAGE_TIM), str);
    }

    public static int e(Context context, String str) {
        return a(a(context, Constants.PACKAGE_QQ_SPEED), str);
    }

    private static boolean f(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e2) {
            SLog.e("openSDK_LOG.SystemUtils", "PackageManager.NameNotFoundException " + str, e2);
            return false;
        } catch (Exception e3) {
            SLog.e("openSDK_LOG.SystemUtils", "Exception", e3);
            return false;
        }
    }

    public static boolean c(Context context) throws PackageManager.NameNotFoundException {
        boolean zF = f(context, "com.tencent.mobileqq");
        boolean zF2 = f(context, Constants.PACKAGE_TIM);
        boolean zF3 = f(context, Constants.PACKAGE_QQ_PAD);
        boolean zF4 = f(context, Constants.PACKAGE_QQ_SPEED);
        SLog.i("openSDK_LOG.SystemUtils", "isQQBranchInstalled: qq=" + zF + ", tim=" + zF2 + ", pad=" + zF3 + ", speed=" + zF4);
        return zF || zF2 || zF3 || zF4;
    }

    public static boolean d(Context context) {
        return context != null && context.getApplicationInfo().targetSdkVersion >= 29 && Build.VERSION.SDK_INT >= 29 && !a();
    }

    public static int a(String str, String str2) throws NumberFormatException {
        if (str == null && str2 == null) {
            return 0;
        }
        if (str != null && str2 == null) {
            return 1;
        }
        if (str == null && str2 != null) {
            return -1;
        }
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int i2 = 0;
        while (i2 < strArrSplit.length && i2 < strArrSplit2.length) {
            try {
                int i3 = Integer.parseInt(strArrSplit[i2]);
                int i4 = Integer.parseInt(strArrSplit2[i2]);
                if (i3 < i4) {
                    return -1;
                }
                if (i3 > i4) {
                    return 1;
                }
                i2++;
            } catch (NumberFormatException unused) {
                return str.compareTo(str2);
            }
        }
        if (strArrSplit.length > i2) {
            return 1;
        }
        return strArrSplit2.length > i2 ? -1 : 0;
    }

    public static boolean a(Context context, String str, String str2) {
        SLog.v("openSDK_LOG.SystemUtils", "OpenUi, validateAppSignatureForPackage");
        try {
            for (Signature signature : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (k.g(signature.toCharsString()).equals(str2)) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static String a(Activity activity, String str) {
        if (activity == null) {
            SLog.e("openSDK_LOG.SystemUtils", "getEncryptPkgName activity==null !!!!!!");
            return "";
        }
        try {
            byte[] bArrA = e.a(str);
            if (bArrA == null) {
                SLog.e("openSDK_LOG.SystemUtils", "getEncryptPkgName shaBytes==null !!!!!!");
                return "";
            }
            byte[] bArr = new byte[8];
            System.arraycopy(bArrA, 5, bArr, 0, 8);
            byte[] bArr2 = new byte[16];
            System.arraycopy(bArrA, 8, bArr2, 0, 16);
            return e.a(activity.getPackageName(), e.a(bArr2), bArr);
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.SystemUtils", "getEncryptPkgName", e2);
            return "";
        }
    }

    public static boolean b(Context context) throws PackageManager.NameNotFoundException {
        boolean zF = f(context, "com.tencent.mobileqq");
        SLog.i("openSDK_LOG.SystemUtils", "isQQInstalled " + zF);
        return zF;
    }

    public static boolean a(Context context, Intent intent) {
        return (context == null || intent == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) ? false : true;
    }

    public static String a(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[SYNTHETIC] */
    @android.annotation.SuppressLint({"SdCardPath"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(java.lang.String r10, java.lang.String r11, int r12) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "-->extractSecureLib, libName: "
            r0.append(r1)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "openSDK_LOG.SystemUtils"
            com.tencent.open.log.SLog.i(r1, r0)
            android.content.Context r0 = com.tencent.open.utils.f.a()
            r2 = 0
            if (r0 != 0) goto L23
            java.lang.String r10 = "-->extractSecureLib, global context is null. "
            com.tencent.open.log.SLog.i(r1, r10)
            return r2
        L23:
            java.lang.String r3 = "secure_lib"
            android.content.SharedPreferences r3 = r0.getSharedPreferences(r3, r2)
            java.io.File r4 = new java.io.File
            java.io.File r5 = r0.getFilesDir()
            r4.<init>(r5, r11)
            boolean r5 = r4.exists()
            r6 = 1
            java.lang.String r7 = "version"
            if (r5 != 0) goto L52
            java.io.File r5 = r4.getParentFile()
            if (r5 == 0) goto L75
            boolean r5 = r5.mkdirs()
            if (r5 == 0) goto L75
            r4.createNewFile()     // Catch: java.io.IOException -> L4d
            goto L75
        L4d:
            r4 = move-exception
            r4.printStackTrace()
            goto L75
        L52:
            int r4 = r3.getInt(r7, r2)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "-->extractSecureLib, libVersion: "
            r5.append(r8)
            r5.append(r12)
            java.lang.String r8 = " | oldVersion: "
            r5.append(r8)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            com.tencent.open.log.SLog.i(r1, r5)
            if (r12 != r4) goto L75
            return r6
        L75:
            r4 = 0
            android.content.res.AssetManager r5 = r0.getAssets()     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La7
            java.io.InputStream r10 = r5.open(r10)     // Catch: java.lang.Throwable -> La4 java.lang.Exception -> La7
            java.io.FileOutputStream r4 = r0.openFileOutput(r11, r2)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9f
            a(r10, r4)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9f
            android.content.SharedPreferences$Editor r11 = r3.edit()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9f
            r11.putInt(r7, r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9f
            r11.commit()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9f
            if (r10 == 0) goto L94
            r10.close()     // Catch: java.io.IOException -> L94
        L94:
            if (r4 == 0) goto L99
            r4.close()     // Catch: java.io.IOException -> L99
        L99:
            return r6
        L9a:
            r11 = move-exception
            r9 = r4
            r4 = r10
            r10 = r9
            goto Lba
        L9f:
            r11 = move-exception
            r9 = r4
            r4 = r10
            r10 = r9
            goto La9
        La4:
            r11 = move-exception
            r10 = r4
            goto Lba
        La7:
            r11 = move-exception
            r10 = r4
        La9:
            java.lang.String r12 = "-->extractSecureLib, when copy lib execption."
            com.tencent.open.log.SLog.e(r1, r12, r11)     // Catch: java.lang.Throwable -> Lb9
            if (r4 == 0) goto Lb3
            r4.close()     // Catch: java.io.IOException -> Lb3
        Lb3:
            if (r10 == 0) goto Lb8
            r10.close()     // Catch: java.io.IOException -> Lb8
        Lb8:
            return r2
        Lb9:
            r11 = move-exception
        Lba:
            if (r4 == 0) goto Lbf
            r4.close()     // Catch: java.io.IOException -> Lbf
        Lbf:
            if (r10 == 0) goto Lc4
            r10.close()     // Catch: java.io.IOException -> Lc4
        Lc4:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.i.a(java.lang.String, java.lang.String, int):boolean");
    }

    private static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr, 0, 8192);
            if (i2 != -1) {
                outputStream.write(bArr, 0, i2);
                j2 += i2;
            } else {
                SLog.i("openSDK_LOG.SystemUtils", "-->copy, copyed size is: " + j2);
                return j2;
            }
        }
    }

    public static int a(String str) {
        if ("shareToQQ".equals(str)) {
            return 10103;
        }
        if ("shareToQzone".equals(str)) {
            return 10104;
        }
        if ("addToQQFavorites".equals(str)) {
            return 10105;
        }
        if ("sendToMyComputer".equals(str)) {
            return 10106;
        }
        if ("shareToTroopBar".equals(str)) {
            return 10107;
        }
        if ("action_login".equals(str)) {
            return 11101;
        }
        return "action_request".equals(str) ? 10100 : -1;
    }

    public static String a(Activity activity) throws PackageManager.NameNotFoundException {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo(activity.getApplicationContext().getPackageName(), 128);
            SLog.i("openSDK_LOG.SystemUtils", "apkPath=" + applicationInfo.sourceDir);
            return applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e2) {
            SLog.e("openSDK_LOG.SystemUtils", "NameNotFoundException", e2);
            return null;
        } catch (Exception e3) {
            SLog.e("openSDK_LOG.SystemUtils", "Exception", e3);
            return null;
        }
    }

    private static boolean a() {
        try {
            return ((Boolean) Environment.class.getMethod("isExternalStorageLegacy", new Class[0]).invoke(Environment.class, new Object[0])).booleanValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }

    public static void a(WebSettings webSettings) {
        try {
            webSettings.setSavePassword(false);
            webSettings.setAllowFileAccess(false);
            webSettings.setAllowFileAccessFromFileURLs(false);
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.SystemUtils", "Exception", e2);
        }
    }
}
