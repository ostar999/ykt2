package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/* loaded from: classes6.dex */
public class TbsShareManager {

    /* renamed from: a, reason: collision with root package name */
    private static Context f21014a = null;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f21015b = false;

    /* renamed from: c, reason: collision with root package name */
    private static String f21016c = null;

    /* renamed from: d, reason: collision with root package name */
    private static String f21017d = "";

    /* renamed from: e, reason: collision with root package name */
    private static String f21018e = null;

    /* renamed from: f, reason: collision with root package name */
    private static int f21019f = 0;

    /* renamed from: g, reason: collision with root package name */
    private static String f21020g = null;

    /* renamed from: h, reason: collision with root package name */
    private static boolean f21021h = false;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f21022i = false;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f21023j = false;

    /* renamed from: k, reason: collision with root package name */
    private static String f21024k = null;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f21025l = false;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f21026m = false;
    public static boolean mHasQueryed = false;

    private static int a(Context context, String str, int i2) {
        File file;
        try {
            file = new File(new File(FileUtil.a(getPackageContext(context, str, false), i2)), TbsDownloader.getBackupFileName(false));
        } catch (Throwable unused) {
            TbsLog.i("TbsShareManager", "getSDCoreVersion exception,pkg=" + str + ", SDCardStatus: " + com.tencent.smtt.utils.b.l(context));
        }
        if (file.exists() && file.canRead()) {
            return com.tencent.smtt.utils.a.b(file);
        }
        TbsLog.i("TbsShareManager", "getSDCoreVersion,file not exist" + file);
        return 0;
    }

    public static String a(Context context) {
        try {
            b(context);
            String str = f21018e;
            if (str != null && !TextUtils.isEmpty(str)) {
                return f21018e + File.separator + "res.apk";
            }
            return null;
        } catch (Throwable th) {
            Log.e("", "getTbsResourcesPath exception: " + Log.getStackTraceString(th));
            return null;
        }
    }

    private static void b(Context context) {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        File tbsShareFile;
        Context context2;
        TbsLog.i("TbsShareManager", "loadProperties -- core_info_already_read " + f21026m);
        if (f21026m) {
            return;
        }
        synchronized (TbsShareManager.class) {
            if (f21026m) {
                return;
            }
            try {
                tbsShareFile = getTbsShareFile(context, "core_info");
                TbsLog.i("TbsShareManager", "loadProperties -- propFile: " + tbsShareFile);
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = null;
            }
            if (tbsShareFile == null) {
                return;
            }
            bufferedInputStream = new BufferedInputStream(new FileInputStream(tbsShareFile));
            try {
                Properties properties = new Properties();
                properties.load(bufferedInputStream);
                String property = properties.getProperty("core_version", "");
                TbsLog.i("TbsShareManager", "loadProperties -- tmp core version : " + property);
                if (!"".equals(property)) {
                    f21019f = Math.max(Integer.parseInt(property), 0);
                    TbsLog.i("TbsShareManager", "loadProperties -- mAvailableCoreVersion: " + f21019f);
                }
                String property2 = properties.getProperty("core_packagename", "");
                if (!"".equals(property2)) {
                    f21020g = property2;
                }
                String str = f21020g;
                if (str != null && (context2 = f21014a) != null) {
                    if (str.equals(context2.getPackageName())) {
                        f21025l = true;
                    } else {
                        f21025l = false;
                    }
                }
                String property3 = properties.getProperty("core_path", "");
                if (!"".equals(property3)) {
                    f21018e = property3;
                }
                String property4 = properties.getProperty("app_version", "");
                if (!"".equals(property4)) {
                    f21024k = property4;
                }
                f21021h = Boolean.parseBoolean(properties.getProperty("core_disabled", k.a.f27524v));
                f21026m = true;
                try {
                    bufferedInputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    th.printStackTrace();
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                        }
                    }
                } finally {
                }
            }
        }
    }

    @Deprecated
    public static int findCoreForThirdPartyApp(Context context) {
        return -1;
    }

    public static boolean forceLoadX5FromTBSDemo(Context context) throws IOException {
        int sharedTbsCoreVersion;
        if (context == null || o.a().a(context, (File[]) null) || (sharedTbsCoreVersion = getSharedTbsCoreVersion(context, TbsConfig.APP_DEMO)) <= 0) {
            return false;
        }
        writeProperties(context, Integer.toString(sharedTbsCoreVersion), TbsConfig.APP_DEMO, o.a().p(getPackageContext(context, TbsConfig.APP_DEMO, true)).getAbsolutePath(), "1");
        return true;
    }

    public static File getBackupCoreFile(Context context, String str) {
        return getSDCoreFile(context, str, 3);
    }

    public static int getBackupCoreVersion(Context context, String str) {
        return a(context, str, 3);
    }

    public static File getBackupDecoupleCoreFile(Context context, String str) {
        try {
            File file = new File(new File(FileUtil.a(getPackageContext(context, str, false), 4)), TbsDownloader.getBackupFileName(true));
            if (file.exists()) {
                return file;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static int getBackupDecoupleCoreVersion(Context context, String str) {
        try {
            File file = new File(new File(FileUtil.a(getPackageContext(context, str, false), 4)), TbsDownloader.getBackupFileName(true));
            if (file.exists() && file.canRead()) {
                return com.tencent.smtt.utils.a.b(file);
            }
        } catch (Throwable unused) {
            TbsLog.i("TbsShareManager", "getBackupDecoupleCoreVersion exception,pkg=" + str + ",package not found.");
        }
        return 0;
    }

    public static boolean getCoreDisabled() {
        return f21021h;
    }

    public static boolean getCoreFormOwn() {
        return f21025l;
    }

    @Deprecated
    public static String[] getCoreProviderAppList() {
        return new String[0];
    }

    public static int getCoreShareDecoupleCoreVersion(Context context, String str) {
        Context packageContext = getPackageContext(context, str, true);
        if (packageContext != null) {
            return o.a().g(packageContext);
        }
        return 0;
    }

    public static String getHostCorePathAppDefined() {
        return f21016c;
    }

    public static Context getPackageContext(Context context, String str, boolean z2) {
        if (context != null && context.getPackageName().equals(str)) {
            return context;
        }
        if (context == null) {
            TbsLog.e("TbsShareManager", "getPackageContext appContext is null!!");
            return null;
        }
        if (z2) {
            try {
                if (!context.getPackageName().equals(str) && (TbsPVConfig.getInstance(context).isEnableNoCoreGray() || Build.VERSION.SDK_INT >= 29)) {
                    return null;
                }
            } catch (Throwable unused) {
                return null;
            }
        }
        return context.createPackageContext(str, 2);
    }

    public static File getSDCoreFile(Context context, String str, int i2) {
        try {
            File file = new File(new File(FileUtil.a(getPackageContext(context, str, false), i2)), TbsDownloader.getBackupFileName(false));
            if (!file.exists()) {
                return null;
            }
            if (file.canRead()) {
                return file;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static int getSharedTbsCoreVersion(Context context, String str) {
        Context packageContext = getPackageContext(context, str, true);
        if (packageContext != null) {
            return o.a().h(packageContext);
        }
        return 0;
    }

    public static File getStableCoreFile(Context context, String str) {
        return getSDCoreFile(context, str, 4);
    }

    public static int getStableCoreVersion(Context context, String str) {
        return a(context, str, 4);
    }

    public static String getStableCoreZeroReason() {
        return f21017d;
    }

    public static File getTbsShareFile(Context context, String str) throws IOException {
        File fileQ = o.a().q(context);
        if (fileQ == null) {
            return null;
        }
        File file = new File(fileQ, str);
        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static int getTbsStableCoreVersion(Context context, int i2) {
        try {
        } catch (Throwable th) {
            TbsLog.i(TbsDownloader.LOGTAG, "getTbsStableCoreVersion stack is " + Log.getStackTraceString(th));
            f21017d = Log.getStackTraceString(th);
        }
        if (!context.getApplicationInfo().packageName.contains("com.tencent.mm")) {
            return 0;
        }
        Context packageContext = getPackageContext(context, "com.tencent.mm", false);
        File file = new File(packageContext == null ? new File(FileUtil.a(context, "com.tencent.mm", 4, true)) : new File(FileUtil.a(packageContext, 4)), TbsDownloader.getBackupFileName(false, i2));
        TbsLog.i(TbsDownloader.LOGTAG, "getTbsStableCoreVersion, coreStable is " + file.getAbsolutePath());
        f21017d = "none";
        if (file.exists() && file.canRead()) {
            int iB = com.tencent.smtt.utils.a.b(file);
            return iB <= 0 ? com.tencent.smtt.utils.a.a(context, file, i2) : iB;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "getTbsStableCoreVersion,core stable not exist" + file);
        f21017d = file.getAbsolutePath() + " exist is " + file.exists() + " canRead is " + file.canRead();
        return 0;
    }

    public static boolean isThirdPartyApp(Context context) {
        Context context2;
        try {
            context2 = f21014a;
        } catch (Throwable th) {
            TbsLog.i(th);
        }
        if (context2 != null && context2.equals(context.getApplicationContext())) {
            return f21015b;
        }
        Context applicationContext = context.getApplicationContext();
        f21014a = applicationContext;
        String packageName = applicationContext.getPackageName();
        String[] strArr = {TbsConfig.APP_DEMO, "com.tencent.mm", "com.tencent.mobileqq", "com.qzone", Constants.PACKAGE_QQ_SPEED};
        for (int i2 = 0; i2 < 5; i2++) {
            if (packageName.equals(strArr[i2])) {
                f21015b = false;
                return false;
            }
        }
        f21015b = true;
        return true;
    }

    public static void setHostCorePathAppDefined(String str) {
        f21016c = str;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x00e3 -> B:72:0x00e6). Please report as a decompilation issue!!! */
    public static void writeProperties(Context context, String str, String str2, String str3, String str4) throws IOException {
        BufferedOutputStream bufferedOutputStream;
        File tbsShareFile;
        Properties properties;
        int i2;
        BufferedOutputStream bufferedOutputStream2;
        TbsLog.i("TbsShareManager", "writeProperties coreVersion is " + str + " corePackageName is " + str2 + " corePath is " + str3);
        StringBuilder sb = new StringBuilder();
        sb.append("writeProperties -- stack: ");
        sb.append(Log.getStackTraceString(new Throwable(DictionaryFactory.SHARP)));
        TbsLog.i("TbsShareManager", sb.toString());
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                tbsShareFile = getTbsShareFile(context, "core_info");
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (tbsShareFile == null) {
            TbsDownloadConfig.getInstance(f21014a).setDownloadInterruptCode(-405);
            return;
        }
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(tbsShareFile));
        try {
            properties = new Properties();
            properties.load(bufferedInputStream2);
            try {
                i2 = Integer.parseInt(str);
            } catch (Exception unused) {
                i2 = 0;
            }
            if (i2 != 0) {
                properties.setProperty("core_version", str);
                properties.setProperty("core_disabled", String.valueOf(false));
                properties.setProperty("core_packagename", str2);
                properties.setProperty("core_path", str3);
                properties.setProperty("app_version", str4);
            } else {
                properties.setProperty("core_disabled", String.valueOf(true));
            }
            bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(tbsShareFile));
        } catch (Throwable th2) {
            th = th2;
            bufferedOutputStream = null;
            bufferedInputStream = bufferedInputStream2;
        }
        try {
            properties.store(bufferedOutputStream2, (String) null);
            f21026m = false;
            TbsDownloadConfig.getInstance(f21014a).setDownloadInterruptCode(-406);
            try {
                bufferedInputStream2.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            bufferedOutputStream2.close();
        } catch (Throwable th3) {
            bufferedInputStream = bufferedInputStream2;
            bufferedOutputStream = bufferedOutputStream2;
            th = th3;
            try {
                th.printStackTrace();
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } finally {
            }
        }
    }
}
