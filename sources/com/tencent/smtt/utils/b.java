package com.tencent.smtt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.hjq.permissions.Permission;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsPrivacyAccess;
import com.tencent.smtt.sdk.c;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f21444a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f21445b = "";

    /* renamed from: c, reason: collision with root package name */
    public static String f21446c = "";

    /* renamed from: d, reason: collision with root package name */
    public static String f21447d = "";

    /* renamed from: e, reason: collision with root package name */
    public static String f21448e = "";

    /* renamed from: f, reason: collision with root package name */
    public static String f21449f = "";

    /* renamed from: g, reason: collision with root package name */
    private static String f21450g = "";

    /* renamed from: h, reason: collision with root package name */
    private static boolean f21451h = false;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f21452i = false;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f21453j = false;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f21454k = false;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f21455l = false;

    /* JADX WARN: Can't wrap try/catch for region: R(6:4|(3:55|5|(2:51|6))|(7:47|7|(1:9)(1:11)|10|(1:14)|61|15)|57|16|31) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a() {
        /*
            java.lang.String r0 = "os.arch"
            boolean r1 = com.tencent.smtt.utils.b.f21453j
            if (r1 != 0) goto L76
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L48
            java.lang.String r3 = "getprop ro.product.cpu.abi"
            java.lang.Process r2 = r2.exec(r3)     // Catch: java.lang.Throwable -> L48
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L48
            java.io.InputStream r2 = r2.getInputStream()     // Catch: java.lang.Throwable -> L48
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L48
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L45
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L45
            java.lang.String r4 = r2.readLine()     // Catch: java.lang.Throwable -> L43
            java.lang.String r5 = "x86"
            boolean r4 = r4.contains(r5)     // Catch: java.lang.Throwable -> L43
            if (r4 == 0) goto L33
            java.lang.String r4 = "i686"
        L2e:
            java.lang.String r0 = b(r4)     // Catch: java.lang.Throwable -> L43
            goto L38
        L33:
            java.lang.String r4 = java.lang.System.getProperty(r0)     // Catch: java.lang.Throwable -> L43
            goto L2e
        L38:
            if (r0 == 0) goto L3c
            com.tencent.smtt.utils.b.f21446c = r0
        L3c:
            r2.close()     // Catch: java.io.IOException -> L3f
        L3f:
            r3.close()     // Catch: java.io.IOException -> L62
            goto L62
        L43:
            r4 = move-exception
            goto L4b
        L45:
            r4 = move-exception
            r2 = r1
            goto L4b
        L48:
            r4 = move-exception
            r2 = r1
            r3 = r2
        L4b:
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch: java.lang.Throwable -> L66
            java.lang.String r1 = b(r0)     // Catch: java.lang.Throwable -> L66
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L5a
            com.tencent.smtt.utils.b.f21446c = r1
        L5a:
            if (r2 == 0) goto L5f
            r2.close()     // Catch: java.io.IOException -> L5f
        L5f:
            if (r3 == 0) goto L62
            goto L3f
        L62:
            r0 = 1
            com.tencent.smtt.utils.b.f21453j = r0
            goto L76
        L66:
            r0 = move-exception
            if (r1 == 0) goto L6b
            com.tencent.smtt.utils.b.f21446c = r1
        L6b:
            if (r2 == 0) goto L70
            r2.close()     // Catch: java.io.IOException -> L70
        L70:
            if (r3 == 0) goto L75
            r3.close()     // Catch: java.io.IOException -> L75
        L75:
            throw r0
        L76:
            java.lang.String r0 = com.tencent.smtt.utils.b.f21446c
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.b.a():java.lang.String");
    }

    public static String a(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception unused) {
            return null;
        }
    }

    private static String a(Context context, File file) {
        Signature signature;
        try {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 65);
            if (packageArchiveInfo == null) {
                signature = null;
            } else {
                Signature[] signatureArr = packageArchiveInfo.signatures;
                if (signatureArr == null || signatureArr.length <= 0) {
                    TbsLog.w("AppUtil", "[getSignatureFromApk] pkgInfo is not null BUT signatures is null!");
                    signature = null;
                } else {
                    signature = signatureArr[0];
                }
            }
            if (signature != null) {
                return signature.toCharsString();
            }
            return null;
        } catch (Exception unused) {
            TbsLog.i("AppUtil", "getSign " + file + com.alipay.sdk.util.e.f3365b);
            return null;
        }
    }

    public static String a(Context context, String str) {
        try {
            String strValueOf = String.valueOf(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(str));
            try {
                return String.valueOf(Integer.toHexString(Integer.parseInt(strValueOf)));
            } catch (Exception unused) {
                return strValueOf;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0067 A[Catch: all -> 0x0078, TryCatch #2 {all -> 0x0078, blocks: (B:36:0x0057, B:38:0x0067, B:40:0x0072), top: B:51:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r5, boolean r6, java.io.File r7) {
        /*
            java.lang.String r0 = "AppUtil"
            java.lang.String r1 = ""
            if (r7 == 0) goto Lb5
            boolean r2 = r7.exists()
            if (r2 != 0) goto Le
            goto Lb5
        Le:
            if (r6 == 0) goto L57
            r6 = 2
            r2 = 0
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.lang.String r4 = "r"
            r3.<init>(r7, r4)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r3.read(r6)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            java.lang.String r6 = "PK"
            boolean r6 = r2.equalsIgnoreCase(r6)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            if (r6 != 0) goto L35
            r3.close()     // Catch: java.io.IOException -> L30
            goto L34
        L30:
            r5 = move-exception
            r5.printStackTrace()
        L34:
            return r1
        L35:
            r3.close()     // Catch: java.io.IOException -> L49
            goto L57
        L39:
            r5 = move-exception
            r2 = r3
            goto L4e
        L3c:
            r6 = move-exception
            r2 = r3
            goto L42
        L3f:
            r5 = move-exception
            goto L4e
        L41:
            r6 = move-exception
        L42:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L3f
            r2.close()     // Catch: java.io.IOException -> L49
            goto L57
        L49:
            r6 = move-exception
            r6.printStackTrace()
            goto L57
        L4e:
            r2.close()     // Catch: java.io.IOException -> L52
            goto L56
        L52:
            r6 = move-exception
            r6.printStackTrace()
        L56:
            throw r5
        L57:
            android.content.Context r6 = r5.getApplicationContext()     // Catch: java.lang.Throwable -> L78
            java.lang.String r6 = r6.getPackageName()     // Catch: java.lang.Throwable -> L78
            java.lang.String r1 = "com.jd.jrapp"
            boolean r6 = r6.contains(r1)     // Catch: java.lang.Throwable -> L78
            if (r6 == 0) goto L7d
            java.lang.String r6 = "[AppUtil.getSignatureFromApk]  #1"
            com.tencent.smtt.utils.TbsLog.i(r0, r6)     // Catch: java.lang.Throwable -> L78
            java.lang.String r6 = a(r7)     // Catch: java.lang.Throwable -> L78
            if (r6 == 0) goto L7d
            java.lang.String r1 = "[AppUtil.getSignatureFromApk]  #2"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)     // Catch: java.lang.Throwable -> L78
            return r6
        L78:
            java.lang.String r6 = "[AppUtil.getSignatureFromApk]  #3"
            com.tencent.smtt.utils.TbsLog.i(r0, r6)
        L7d:
            java.lang.String r6 = "[AppUtil.getSignatureFromApk]  #4"
            com.tencent.smtt.utils.TbsLog.i(r0, r6)
            java.lang.String r5 = a(r5, r7)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r1 = "[AppUtil.getSignatureFromApk]  android api signature="
            r6.append(r1)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r6)
            if (r5 != 0) goto Lb4
            java.lang.String r5 = a(r7)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "[AppUtil.getSignatureFromApk]  java get signature="
            r6.append(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r6)
        Lb4:
            return r5
        Lb5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.b.a(android.content.Context, boolean, java.io.File):java.lang.String");
    }

    private static String a(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            byte[] bArr = new byte[8192];
            String strA = a(a(jarFile, jarFile.getJarEntry("AndroidManifest.xml"), bArr)[0].getEncoded());
            Enumeration<JarEntry> enumerationEntries = jarFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                JarEntry jarEntryNextElement = enumerationEntries.nextElement();
                String name = jarEntryNextElement.getName();
                if (name != null) {
                    Certificate[] certificateArrA = a(jarFile, jarEntryNextElement, bArr);
                    String strA2 = certificateArrA != null ? a(certificateArrA[0].getEncoded()) : null;
                    if (strA2 == null) {
                        if (!name.startsWith("META-INF/")) {
                            return null;
                        }
                    } else if (!strA2.equals(strA)) {
                        return null;
                    }
                }
            }
            return strA;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            byte b3 = bArr[i2];
            int i3 = (b3 >> 4) & 15;
            int i4 = i2 * 2;
            cArr[i4] = (char) (i3 >= 10 ? (i3 + 97) - 10 : i3 + 48);
            int i5 = b3 & 15;
            cArr[i4 + 1] = (char) (i5 >= 10 ? (i5 + 97) - 10 : i5 + 48);
        }
        return new String(cArr);
    }

    public static boolean a(String str) {
        Matcher matcher;
        try {
            matcher = Pattern.compile("i686|mips|x86_64|x86").matcher(str);
        } catch (Exception unused) {
            matcher = null;
        }
        return matcher == null || !matcher.find();
    }

    private static Certificate[] a(JarFile jarFile, JarEntry jarEntry, byte[] bArr) throws Exception {
        InputStream inputStream = jarFile.getInputStream(jarEntry);
        while (inputStream.read(bArr, 0, bArr.length) != -1) {
        }
        inputStream.close();
        if (jarEntry != null) {
            return jarEntry.getCertificates();
        }
        return null;
    }

    public static int b(Context context) {
        if (TbsPrivacyAccess.AndroidVersion.isDisabled()) {
            return 0;
        }
        return Build.VERSION.SDK_INT;
    }

    private static String b(String str) {
        return str == null ? "" : str;
    }

    public static void b(Context context, String str) {
        try {
            TbsDownloadConfig tbsDownloadConfig = TbsDownloadConfig.getInstance(context);
            tbsDownloadConfig.mSyncMap.put(TbsDownloadConfig.TbsConfigKey.KEY_GUID, str);
            tbsDownloadConfig.commit();
        } catch (Exception unused) {
        }
    }

    public static boolean b() {
        Matcher matcher;
        try {
            matcher = Pattern.compile("i686|mips|x86_64|x86").matcher(f21446c);
        } catch (Exception unused) {
            matcher = null;
        }
        return matcher == null || !matcher.find();
    }

    public static String c(Context context) {
        if (TbsPrivacyAccess.DeviceModel.isDisabled()) {
            return "";
        }
        if (!f21455l) {
            String strC = s.c(context);
            try {
                f21449f = new String(strC.getBytes("UTF-8"), "ISO8859-1");
            } catch (Exception unused) {
                f21449f = strC;
            }
            f21455l = true;
        }
        return f21449f;
    }

    public static boolean c() {
        Class<?> cls;
        Method declaredMethod;
        Object objInvoke;
        Method declaredMethod2;
        try {
            cls = Class.forName("dalvik.system.VMRuntime");
            declaredMethod = cls.getDeclaredMethod("getRuntime", new Class[0]);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (declaredMethod == null || (objInvoke = declaredMethod.invoke(null, new Object[0])) == null || (declaredMethod2 = cls.getDeclaredMethod("is64Bit", new Class[0])) == null) {
            return false;
        }
        Object objInvoke2 = declaredMethod2.invoke(objInvoke, new Object[0]);
        if (objInvoke2 instanceof Boolean) {
            return ((Boolean) objInvoke2).booleanValue();
        }
        return false;
    }

    public static String d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            return null;
        }
    }

    public static int e(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String f(Context context) {
        try {
            return TbsDownloadConfig.getInstance(context).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_GUID, "");
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean g(final Context context) {
        boolean z2 = false;
        try {
            z2 = context.getSharedPreferences("sai", 0).getBoolean("gi", false);
            TbsLog.i("AppUtil", "getImeiEnable is " + z2);
            com.tencent.smtt.sdk.c.a().a(context, (Integer) 1001, new c.a() { // from class: com.tencent.smtt.utils.b.1
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("gi", true);
                    editorEdit.commit();
                    TbsLog.e("TBSEmergency", "Execute command [1001](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("AppUtil", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static String h(Context context) {
        if (!f21451h) {
            try {
                f21444a = "";
                if (TextUtils.isEmpty("")) {
                    f21444a = k.a(context);
                }
            } catch (Exception e2) {
                TbsLog.i(e2);
            }
            f21451h = true;
        }
        return f21444a;
    }

    public static String i(Context context) {
        String str;
        if (!QbSdk.isEnableSensitiveApi()) {
            str = "getImsi isEnableSensitiveApi = false";
        } else {
            if (QbSdk.isEnableCanGetSubscriberId()) {
                if (!f21452i) {
                    try {
                    } catch (Exception e2) {
                        TbsLog.i(e2);
                    }
                    if (context.getApplicationInfo().packageName.contains("com.tencent.mobileqq")) {
                        return "";
                    }
                    f21445b = "";
                    f21452i = true;
                }
                return f21445b;
            }
            str = "getImsi isEnableCanGetSubscriberId is false";
        }
        TbsLog.i("AppUtil", str);
        return "";
    }

    public static String j(Context context) {
        return "02:00:00:00:00:00";
    }

    public static String k(Context context) {
        String str;
        if (!QbSdk.isEnableSensitiveApi()) {
            str = "getAndroidID isEnableSensitiveApi = false";
        } else if (!QbSdk.isEnableGetAndroidID()) {
            str = "getAndroidID isEnableGetAndroidID is false";
        } else {
            if (m(context)) {
                if (!f21454k) {
                    try {
                        f21448e = Settings.Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    f21454k = true;
                }
                TbsLog.i("AppUtil", "getAndroidID mAndroidID is " + f21448e);
                return f21448e;
            }
            str = "getAndroidID isAndroidIDEnable is false";
        }
        TbsLog.i("AppUtil", str);
        return "";
    }

    public static boolean l(Context context) {
        return context != null && context.checkCallingOrSelfPermission(Permission.READ_EXTERNAL_STORAGE) == 0;
    }

    private static boolean m(final Context context) {
        boolean z2 = false;
        try {
            z2 = context.getSharedPreferences("sai", 0).getBoolean("gpai", false);
            TbsLog.i("AppUtil", "isAndroidIDEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1012, new c.a() { // from class: com.tencent.smtt.utils.b.2
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("gpai", false);
                    editorEdit.commit();
                    TbsLog.e("TBSEmergency", "Execute command [1012](+extra+)");
                }
            });
            cVarA.a(context, (Integer) 1011, new c.a() { // from class: com.tencent.smtt.utils.b.3
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("gpai", true);
                    editorEdit.commit();
                    TbsLog.e("TBSEmergency", "Execute command [1011](+extra+)");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("AppUtil", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }
}
