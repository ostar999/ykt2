package com.tencent.smtt.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsPrivacyAccess;
import com.tencent.smtt.sdk.TbsShareManager;
import com.tencent.smtt.sdk.c;
import java.io.File;

/* loaded from: classes6.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    private static File f21608a = null;

    /* renamed from: b, reason: collision with root package name */
    private static String f21609b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f21610c = "";

    /* renamed from: d, reason: collision with root package name */
    private static int f21611d = -1;

    /* renamed from: e, reason: collision with root package name */
    private static String f21612e = "";

    /* renamed from: f, reason: collision with root package name */
    private static String f21613f = null;

    /* renamed from: g, reason: collision with root package name */
    private static int f21614g = 0;

    /* renamed from: h, reason: collision with root package name */
    private static String f21615h = null;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f21616i = false;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f21617j = false;

    /* renamed from: k, reason: collision with root package name */
    private static String f21618k = "unknown";

    /* renamed from: l, reason: collision with root package name */
    private static int f21619l = -1;

    /* renamed from: m, reason: collision with root package name */
    private static int f21620m = -1;

    /* renamed from: n, reason: collision with root package name */
    private static int f21621n = -1;

    /* renamed from: o, reason: collision with root package name */
    private static int f21622o = -1;

    /* renamed from: p, reason: collision with root package name */
    private static int f21623p = -1;

    public static long a() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

    public static void a(Context context, Bundle bundle) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("sai", 0).edit();
            if (bundle.containsKey(QbSdk.KEY_THIRD_PARTY_TURING)) {
                boolean z2 = bundle.getBoolean(QbSdk.KEY_THIRD_PARTY_TURING);
                editorEdit.putBoolean("itge", z2);
                TbsLog.e("TbsUtils", "setEnableForThirdParty key is itge value is " + z2);
            }
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context, String str, String str2) {
        String str3;
        try {
            if (TbsShareManager.isThirdPartyApp(context) && g(context)) {
                String str4 = "thirdapp_" + str;
                if (TextUtils.isEmpty(str2)) {
                    str3 = "";
                } else {
                    str3 = str2 + StrPool.UNDERLINE;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append("mycpuis");
                sb.append(b.c() ? 64 : 32);
                String str5 = (sb.toString() + StrPool.UNDERLINE + "64scoreis" + TbsShareManager.getTbsStableCoreVersion(context, 64)) + StrPool.UNDERLINE + "32scoreis" + TbsShareManager.getTbsStableCoreVersion(context, 32);
                com.tencent.smtt.sdk.stat.b.a(context, str4, str5);
                TbsLog.i("uploadThirdAppCoreUpdate", "action is " + str4 + " p1 is " + str5);
            }
        } catch (Throwable th) {
            TbsLog.i("uploadThirdAppCoreUpdate", "stack is  " + Log.getStackTraceString(th));
        }
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!TextUtils.isEmpty(f21609b)) {
            str = f21609b + "&" + str;
        }
        f21609b = str;
    }

    @TargetApi(9)
    public static boolean a(Context context) {
        File tbsFolderDir;
        if (context == null) {
            return false;
        }
        if (f21608a != null) {
            return true;
        }
        try {
            if (context.getApplicationInfo().processName.contains("com.tencent.mm") && (tbsFolderDir = QbSdk.getTbsFolderDir(context)) != null && tbsFolderDir.isDirectory()) {
                File file = new File(tbsFolderDir, "share");
                if (!file.isDirectory() && !file.mkdir()) {
                    return false;
                }
                f21608a = file;
                file.setExecutable(true, false);
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void b() {
        f21609b = "";
    }

    public static void b(String str) {
        f21612e = str;
    }

    public static boolean b(Context context) {
        boolean z2 = false;
        try {
            if (TbsShareManager.isThirdPartyApp(context)) {
                TbsLog.i("TbsUtils", "isStableCoreForHostEnable app is thirdapp return is false ");
                return false;
            }
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
        }
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("scfh", true);
            TbsLog.i("TbsUtils", "isStableCoreForHostEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1003, new c.a() { // from class: com.tencent.smtt.utils.s.1
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("scfh", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1003](" + str + ")");
                }
            });
            cVarA.a(context, (Integer) 1009, new c.a() { // from class: com.tencent.smtt.utils.s.7
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("scfh", false);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1009](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th2) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th2));
            return z2;
        }
    }

    public static String c(Context context) {
        SharedPreferences sharedPreferences;
        String string;
        if (!TextUtils.isEmpty(f21610c)) {
            return f21610c;
        }
        try {
            sharedPreferences = context.getSharedPreferences("sai", 0);
            string = sharedPreferences.getString("bmo", "");
            f21610c = string;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
        }
        if (!TextUtils.isEmpty(string)) {
            return f21610c;
        }
        TbsPrivacyAccess.ConfigurablePrivacy configurablePrivacy = TbsPrivacyAccess.ConfigurablePrivacy.MODEL;
        f21610c = TbsPrivacyAccess.getConfigurePrivacy(context, configurablePrivacy, "");
        TbsLog.i("TbsUtils", "getBuildModel from sp is " + f21610c);
        if (!TextUtils.isEmpty(f21610c)) {
            return f21610c;
        }
        if (TextUtils.isEmpty(f21610c) && !context.getApplicationInfo().packageName.contains("com.tencent.mobileqq")) {
            f21610c = Build.MODEL;
        }
        TbsLog.i("TbsUtils", "getBuildModel is " + f21610c);
        if (!TextUtils.isEmpty(f21610c)) {
            TbsPrivacyAccess.configurePrivacy(context, configurablePrivacy, f21610c);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString("bmo", f21610c);
            editorEdit.commit();
        }
        return f21610c;
    }

    public static boolean d(Context context) {
        boolean z2 = false;
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("rrff", false);
            TbsLog.i("TbsUtils", "isReadResponseFromFileEnable is " + z2);
            com.tencent.smtt.sdk.c.a().a(context, (Integer) 1006, new c.a() { // from class: com.tencent.smtt.utils.s.8
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("rrff", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1006](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static boolean e(Context context) {
        boolean z2 = false;
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("fwdn", false);
            TbsLog.i("TbsUtils", "isReadResponseFromFileEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1007, new c.a() { // from class: com.tencent.smtt.utils.s.9
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("fwdn", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command 1007(" + str + ")");
                }
            });
            cVarA.a(context, (Integer) 1008, new c.a() { // from class: com.tencent.smtt.utils.s.10
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("fwdn", false);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command 1008(" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static boolean f(Context context) {
        boolean z2 = true;
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("cbau", true);
            TbsLog.i("TbsUtils", "isClearBackupEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1013, new c.a() { // from class: com.tencent.smtt.utils.s.11
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("cbau", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1013](" + str + ")");
                }
            });
            cVarA.a(context, (Integer) 1014, new c.a() { // from class: com.tencent.smtt.utils.s.12
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("cbau", false);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1014](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static boolean g(Context context) {
        boolean z2 = false;
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("utcu", false);
            TbsLog.i("TbsUtils", "isUploadThirdAppCoreUpdateEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1019, new c.a() { // from class: com.tencent.smtt.utils.s.2
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("utcu", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1019](" + str + ")");
                }
            });
            cVarA.a(context, (Integer) 1020, new c.a() { // from class: com.tencent.smtt.utils.s.3
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("utcu", false);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1020](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static boolean h(Context context) {
        boolean z2 = true;
        try {
            final Context applicationContext = context.getApplicationContext();
            z2 = applicationContext.getSharedPreferences("sai", 0).getBoolean("itge", true);
            TbsLog.i("TbsUtils", "isTuringEnable is " + z2);
            com.tencent.smtt.sdk.c cVarA = com.tencent.smtt.sdk.c.a();
            cVarA.a(context, (Integer) 1021, new c.a() { // from class: com.tencent.smtt.utils.s.4
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("itge", true);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1021](" + str + ")");
                }
            });
            cVarA.a(context, (Integer) 1022, new c.a() { // from class: com.tencent.smtt.utils.s.5
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putBoolean("itge", false);
                    editorEdit.commit();
                    TbsLog.e("TbsUtils", "Execute command [1022](" + str + ")");
                }
            });
            return z2;
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
            return z2;
        }
    }

    public static String i(Context context) {
        String string = "removenone";
        try {
            final Context applicationContext = context.getApplicationContext();
            string = context.getSharedPreferences("sai", 0).getString("grpis", "removenone");
            com.tencent.smtt.sdk.c.a().a(context, (Integer) 1027, new c.a() { // from class: com.tencent.smtt.utils.s.6
                @Override // com.tencent.smtt.sdk.c.a
                public void a(String str) {
                    TbsLog.i("TbsUtils", "getRmPrivacyItemState callback is " + str);
                    SharedPreferences.Editor editorEdit = applicationContext.getSharedPreferences("sai", 0).edit();
                    editorEdit.putString("grpis", str);
                    editorEdit.commit();
                }
            });
        } catch (Throwable th) {
            TbsLog.i("TbsUtils", "stack is " + Log.getStackTraceString(th));
        }
        TbsLog.i("TbsUtils", "getRmPrivacyItemState result is " + string);
        return string;
    }
}
