package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.provider.FontsContractCompat;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.mobile.auth.BuildConfig;
import com.plv.livescenes.download.PLVDownloadDirManager;
import com.psychiatrygarden.utils.MimeTypes;
import com.tencent.connect.common.Constants;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.sdk.TbsPrivacyAccess;
import com.tencent.smtt.sdk.stat.MttLoader;
import com.tencent.smtt.utils.Apn;
import com.tencent.smtt.utils.FileProvider;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.TbsLogClient;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
/* loaded from: classes6.dex */
public class QbSdk {
    private static int A = 170;
    private static String B = null;
    private static String C = null;
    public static final int CORE_VER_ENABLE_202112 = 45912;
    public static final int CORE_VER_ENABLE_202207 = 46110;
    private static boolean D = false;
    private static boolean E = false;
    public static final int EXTENSION_INIT_FAILURE = -99999;
    private static TbsListener F = null;
    public static final String FILERADER_MENUDATA = "menuData";
    private static TbsListener G = null;
    private static boolean H = false;
    private static boolean I = false;
    public static final String KEY_SET_SENDREQUEST_AND_UPLOAD = "SET_SENDREQUEST_AND_UPLOAD";
    public static final String KEY_THIRD_PARTY_TURING = "turing";
    public static final String LOGIN_TYPE_KEY_PARTNER_CALL_POS = "PosID";
    public static final String LOGIN_TYPE_KEY_PARTNER_ID = "ChannelID";
    public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
    public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
    public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
    public static final int QBMODE = 2;
    public static final String SHARE_PREFERENCES_NAME = "tbs_file_open_dialog_config";
    public static final String SVNVERSION = "jnizz";
    public static final int TBSMODE = 1;
    public static final String TID_QQNumber_Prefix = "QQ:";
    public static final int VERSION = 1;

    /* renamed from: a, reason: collision with root package name */
    static boolean f20817a = false;

    /* renamed from: b, reason: collision with root package name */
    static boolean f20818b = false;

    /* renamed from: c, reason: collision with root package name */
    static boolean f20819c = true;

    /* renamed from: d, reason: collision with root package name */
    static String f20820d = null;

    /* renamed from: e, reason: collision with root package name */
    static boolean f20821e = false;

    /* renamed from: f, reason: collision with root package name */
    static long f20822f = 0;

    /* renamed from: g, reason: collision with root package name */
    static long f20823g = 0;

    /* renamed from: i, reason: collision with root package name */
    static boolean f20825i = true;
    public static boolean isDefaultDialog = false;

    /* renamed from: j, reason: collision with root package name */
    static boolean f20826j = true;

    /* renamed from: k, reason: collision with root package name */
    static boolean f20827k = false;

    /* renamed from: l, reason: collision with root package name */
    static boolean f20828l = false;

    /* renamed from: m, reason: collision with root package name */
    static volatile boolean f20829m = false;
    public static boolean mDisableUseHostBackupCore = false;

    /* renamed from: p, reason: collision with root package name */
    private static int f20832p = 0;

    /* renamed from: q, reason: collision with root package name */
    private static String f20833q = "";

    /* renamed from: r, reason: collision with root package name */
    private static Class<?> f20834r = null;

    /* renamed from: s, reason: collision with root package name */
    private static Object f20835s = null;
    public static boolean sIsVersionPrinted = false;

    /* renamed from: t, reason: collision with root package name */
    private static boolean f20836t = false;

    /* renamed from: u, reason: collision with root package name */
    private static String[] f20837u = null;

    /* renamed from: v, reason: collision with root package name */
    private static String f20838v = "NULL";

    /* renamed from: w, reason: collision with root package name */
    private static String f20839w = "UNKNOWN";

    /* renamed from: x, reason: collision with root package name */
    private static boolean f20840x = false;

    /* renamed from: y, reason: collision with root package name */
    private static int f20841y = -1;

    /* renamed from: z, reason: collision with root package name */
    private static int f20842z;

    /* renamed from: h, reason: collision with root package name */
    static Object f20824h = new Object();

    /* renamed from: n, reason: collision with root package name */
    static TbsListener f20830n = new TbsListener() { // from class: com.tencent.smtt.sdk.QbSdk.7
        @Override // com.tencent.smtt.sdk.TbsListener
        public void onDownloadFinish(int i2) {
            TbsDownloader.f20927a = false;
            if (QbSdk.F != null) {
                QbSdk.F.onDownloadFinish(i2);
            }
            if (QbSdk.G != null) {
                QbSdk.G.onDownloadFinish(i2);
            }
        }

        @Override // com.tencent.smtt.sdk.TbsListener
        public void onDownloadProgress(int i2) {
            if (QbSdk.G != null) {
                QbSdk.G.onDownloadProgress(i2);
            }
            if (QbSdk.F != null) {
                QbSdk.F.onDownloadProgress(i2);
            }
        }

        @Override // com.tencent.smtt.sdk.TbsListener
        public void onInstallFinish(int i2) {
            if (i2 == 243 && QbSdk.G != null) {
                QbSdk.G.onInstallFinish(i2);
                return;
            }
            if (i2 != 200) {
            }
            TbsLog.i("QbSdk", "onInstallFinish errCode is " + i2, true);
            QbSdk.setTBSInstallingStatus(false);
            TbsDownloader.f20927a = false;
            if (QbSdk.F != null) {
                QbSdk.F.onInstallFinish(i2);
            }
            if (QbSdk.G != null) {
                QbSdk.G.onInstallFinish(i2);
            }
        }
    };
    private static boolean J = false;

    /* renamed from: o, reason: collision with root package name */
    static Map<String, Object> f20831o = null;

    @Deprecated
    public static final String USER_ID_FROM_APP_IMSI = TbsPrivacyAccess.ConfigurablePrivacy.IMSI.f20994a;

    @Deprecated
    public static final String USER_ID_FROM_APP_ANDROID_ID = TbsPrivacyAccess.ConfigurablePrivacy.ANDROID_ID.f20994a;

    @Deprecated
    public static final String USER_ID_FROM_APP_MAC = TbsPrivacyAccess.ConfigurablePrivacy.MAC.f20994a;

    @Deprecated
    public static final String USER_ID_FROM_APP_ANDROID_VERSION = TbsPrivacyAccess.ConfigurablePrivacy.ANDROID_VERSION.f20994a;

    @Deprecated
    public static final String USER_ID_FROM_APP_DEVICE_MODEL = TbsPrivacyAccess.ConfigurablePrivacy.DEVICE_MODEL.f20994a;
    public static final String USER_ID_FROM_APP_QIMEI36 = TbsPrivacyAccess.ConfigurablePrivacy.QIMEI36.f20994a;
    private static int K = -1;

    public interface PreInitCallback {
        void onCoreInitFinished();

        void onViewInitFinished(boolean z2);
    }

    public static Bundle a(Context context, Bundle bundle) throws Exception {
        TbsLogReport tbsLogReport;
        String str;
        if (a(context)) {
            Object objA = com.tencent.smtt.utils.j.a(f20835s, "incrUpdate", (Class<?>[]) new Class[]{Context.class, Bundle.class}, context, bundle);
            if (objA != null) {
                return (Bundle) objA;
            }
            tbsLogReport = TbsLogReport.getInstance(context);
            str = "incrUpdate return null!";
        } else {
            tbsLogReport = TbsLogReport.getInstance(context);
            str = "initForPatch return false!";
        }
        tbsLogReport.setInstallErrorCode(216, str);
        return null;
    }

    private static Bundle a(Context context, Map<String, String> map) throws JSONException {
        if (map == null) {
            return null;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString(TtmlNode.TAG_STYLE, map.get(TtmlNode.TAG_STYLE) == null ? "0" : map.get(TtmlNode.TAG_STYLE));
            try {
                bundle.putInt("topBarBgColor", Color.parseColor(map.get("topBarBgColor")));
            } catch (Exception unused) {
            }
            if (map.containsKey(FILERADER_MENUDATA)) {
                JSONObject jSONObject = new JSONObject(map.get(FILERADER_MENUDATA));
                JSONArray jSONArray = jSONObject.getJSONArray("menuItems");
                if (jSONArray != null) {
                    ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                    for (int i2 = 0; i2 < jSONArray.length() && i2 < 5; i2++) {
                        try {
                            JSONObject jSONObject2 = (JSONObject) jSONArray.get(i2);
                            arrayList.add(i2, BitmapFactory.decodeResource(context.getResources(), jSONObject2.getInt("iconResId")));
                            jSONObject2.put("iconResId", i2);
                        } catch (Exception unused2) {
                        }
                    }
                    bundle.putParcelableArrayList("resArray", arrayList);
                }
                bundle.putString(FILERADER_MENUDATA, jSONObject.toString());
            }
            return bundle;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Object a(Context context, String str, Bundle bundle) {
        if (!a(context)) {
            return Integer.valueOf(EXTENSION_INIT_FAILURE);
        }
        Object objA = com.tencent.smtt.utils.j.a(f20835s, "miscCall", (Class<?>[]) new Class[]{String.class, Bundle.class}, str, bundle);
        if (objA != null) {
            return objA;
        }
        return null;
    }

    public static String a() {
        return f20833q;
    }

    public static void a(Context context, Integer num, Map<Integer, String> map) {
        if (a(context)) {
            com.tencent.smtt.utils.j.a(f20835s, "dispatchEmergencyCommand", (Class<?>[]) new Class[]{Integer.class, Map.class}, num, map);
        }
    }

    public static synchronized void a(Context context, String str) {
        if (f20818b) {
            TbsCoreLoadStat.getInstance().a(context, 402, new Throwable(f20838v));
            return;
        }
        if (f20817a) {
            return;
        }
        f20817a = true;
        f20839w = "forceSysWebViewInner: " + str;
        TbsLog.e("QbSdk", "forceSysWebViewInner", "Reason:" + f20839w);
        TbsLog.e("QbSdk", "forceSysWebViewInner", Log.getStackTraceString(new Throwable(DictionaryFactory.SHARP)));
        TbsCoreLoadStat.getInstance().a(context, 401);
    }

    public static boolean a(Context context) {
        try {
            if (f20834r != null) {
                return true;
            }
            File fileP = o.a().p(context);
            if (fileP == null) {
                TbsLog.e("QbSdk", "QbSdk initExtension (false) optDir == null");
                return false;
            }
            File file = new File(fileP, "tbs_sdk_extension_dex.jar");
            if (!file.exists()) {
                TbsLog.e("QbSdk", "QbSdk initExtension (false) dexFile.exists()=false", true);
                return false;
            }
            TbsLog.i("QbSdk", "new DexLoader #3 dexFile is " + file.getAbsolutePath());
            w.a().b(context);
            com.tencent.smtt.utils.n.a(context);
            f20834r = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, fileP.getAbsolutePath(), getSettings()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
            if (isEnableSensitiveApi() || com.tencent.smtt.utils.j.a(f20834r, "isSuiteableGetSensitative", (Class<?>[]) new Class[0], new Object[0]) != null) {
                loadTBSSDKExtension(context, file.getParent());
                return true;
            }
            TbsLog.e("QbSdk", "isSuiteableGetSensitative check failed,can not use x5");
            return false;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "initExtension sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    public static boolean a(Context context, int i2) {
        return a(context, i2, 20000);
    }

    public static boolean a(Context context, int i2, int i3) {
        Map<String, Object> map = f20831o;
        if (map != null && map.containsKey(KEY_SET_SENDREQUEST_AND_UPLOAD) && f20831o.get(KEY_SET_SENDREQUEST_AND_UPLOAD).equals(k.a.f27524v)) {
            TbsLog.i("QbSdk", "[QbSdk.isX5Disabled] -- SET_SENDREQUEST_AND_UPLOAD is false");
            return true;
        }
        if (!isEnableSensitiveApi() && com.tencent.smtt.utils.j.a(f20834r, "isSuiteableGetSensitative", (Class<?>[]) new Class[0], new Object[0]) == null) {
            TbsLog.e("QbSdk", "isSuiteableGetSensitative check failed,can not use x5");
            return false;
        }
        o.a().b(context, g.f21179a == 0);
        if (d(context)) {
            return TbsDownloader.isTbsCoreDisabledBySwitch(context.getApplicationContext(), i2);
        }
        return true;
    }

    @SuppressLint({"NewApi"})
    private static boolean a(Context context, boolean z2) {
        TbsLog.initIfNeed(context);
        if (!sIsVersionPrinted) {
            TbsLog.i("QbSdk", "svn revision: jnizz; SDK_VERSION_CODE: 44226; SDK_VERSION_NAME: 4.3.0.326");
            sIsVersionPrinted = true;
        }
        if (f20817a && !z2) {
            TbsCoreLoadStat.getInstance().a(context, 414);
            TbsLog.e("QbSdk", "init", "Force WebView Inner, " + f20839w);
            return false;
        }
        if (f20818b) {
            TbsCoreLoadStat.getInstance().a(context, 402, new Throwable(f20838v));
            TbsLog.e("QbSdk", "init", "QbSdk init mIsSysWebViewForcedByOuter = true, " + f20838v);
            return false;
        }
        if (!com.tencent.smtt.utils.b.b()) {
            TbsCoreLoadStat.getInstance().a(context, 326, new Throwable(com.tencent.smtt.utils.b.f21446c));
            TbsLog.i("QbSdk", "init", "cpu is invalid: " + com.tencent.smtt.utils.b.f21446c);
            return false;
        }
        if (TbsPVConfig.getInstance(context).isEnableProtection()) {
            e(context);
        }
        try {
            File fileP = o.a().p(context);
            if (fileP == null) {
                TbsCoreLoadStat.getInstance().a(context, 312, new Throwable("QbSdk.init (false) TbsCoreShareDir is null"));
                return false;
            }
            int iA = o.a().a(true, context);
            int i2 = f20832p;
            if (i2 != 0 && i2 != iA) {
                f20834r = null;
                f20835s = null;
                TbsCoreLoadStat.getInstance().a(context, 303, new Throwable("sTbsVersion: " + f20832p + "; tbsCoreInstalledVer: " + iA));
                return false;
            }
            f20832p = iA;
            if (TbsDownloader.a(context, iA)) {
                TbsLog.w("QbSdk", "init", "version " + f20832p + " is in blacklist,can not load! return");
                return false;
            }
            if (!isCoreVerEnabled(context, f20832p)) {
                TbsLog.w("QbSdk", "version " + f20832p + " is in below min-version that app set by QbSdk.setCoreMinVersion, reset and not load.");
                return false;
            }
            int i3 = f20841y;
            if (i3 == -1) {
                boolean zIsTbsCoreDisabledBySwitch = TbsDownloader.isTbsCoreDisabledBySwitch(context.getApplicationContext(), f20832p);
                f20841y = zIsTbsCoreDisabledBySwitch ? 0 : 1;
                if (zIsTbsCoreDisabledBySwitch) {
                    TbsLog.w("QbSdk", "init", "version " + f20832p + "is disable by remote switch");
                    TbsCoreLoadStat.getInstance().a(context, 309, new Throwable("version " + f20832p + "is disable by remote switch"));
                    return false;
                }
            } else if (i3 == 0) {
                TbsLog.w("QbSdk", "init", "version " + f20832p + "is disable by remote switch");
                return false;
            }
            if (f20834r != null && f20835s != null) {
                return true;
            }
            File fileP2 = o.a().p(context);
            File file = new File(fileP2, "tbs_sdk_extension_dex.jar");
            if (file.exists()) {
                String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : fileP.getAbsolutePath();
                TbsLog.i("QbSdk", "init", "optDirExtension #1 is " + hostCorePathAppDefined);
                TbsLog.i("QbSdk", "init", "new DexLoader #1 dexFile is " + file.getAbsolutePath());
                w.a().b(context);
                com.tencent.smtt.utils.n.a(context);
                f20834r = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, hostCorePathAppDefined, getSettings()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                if (!isEnableSensitiveApi() && com.tencent.smtt.utils.j.a(f20834r, "isSuiteableGetSensitative", (Class<?>[]) new Class[0], new Object[0]) == null) {
                    TbsLog.e("QbSdk", "init", "isSuiteableGetSensitative check failed, can not use x5");
                    return false;
                }
                loadTBSSDKExtension(context, file.getParent());
                com.tencent.smtt.utils.j.a(f20835s, "setClientVersion", (Class<?>[]) new Class[]{Integer.TYPE}, 1);
                return true;
            }
            try {
                int iH = o.a().h(context);
                if (iH > 0) {
                    File file2 = new File(file.getParentFile(), "tbs_jars_fusion_dex.jar");
                    TbsCoreLoadStat.getInstance().a(context, 403, new Throwable("[loadMsg] extension is miss, fusion exist is " + file2.exists() + ", core is " + iH + ", dexPath is " + file.getPath()));
                } else {
                    TbsCoreLoadStat.getInstance().a(context, 404, new Throwable("core path: " + fileP2));
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return false;
        } catch (Throwable th2) {
            TbsLog.e("QbSdk", "QbSdk init Throwable: " + Log.getStackTraceString(th2));
            TbsCoreLoadStat.getInstance().a(context, 306, th2);
            return false;
        }
    }

    public static boolean a(Context context, boolean z2, boolean z3) {
        boolean z4;
        Object objA;
        TbsLog.i("QbSdk", "canLoadX5", "#1# installTbsCoreIfNeeded");
        o.a().b(context, g.f21179a == 0);
        int disabledCoreVersion = TbsPVConfig.getInstance(context).getDisabledCoreVersion();
        int iH = o.a().h(context);
        TbsLog.i("QbSdk", "canLoadX5", "#1-1# disabledCoreVersion is " + disabledCoreVersion + " localCoreVersion is " + iH);
        if (disabledCoreVersion != 0 && disabledCoreVersion == iH) {
            TbsLog.e("QbSdk", "canLoadX5", "#2-1# force use sys by remote switch");
            return false;
        }
        if (!a(context, z2)) {
            TbsLog.e("QbSdk", "canLoadX5", "#2-4# QbSdk.init failure!");
            return false;
        }
        Object obj = f20835s;
        Class cls = Integer.TYPE;
        Object objA2 = com.tencent.smtt.utils.j.a(obj, "canLoadX5Core", (Class<?>[]) new Class[]{cls}, 44226);
        StringBuilder sb = new StringBuilder();
        sb.append("#3# invoke sExtensionObj canLoadX5Core and ret is ");
        sb.append(objA2 != null);
        TbsLog.i("QbSdk", "canLoadX5", sb.toString());
        if (objA2 == null) {
            Object objA3 = com.tencent.smtt.utils.j.a(f20835s, "canLoadX5", (Class<?>[]) new Class[]{cls}, Integer.valueOf(a.a()));
            if (objA3 == null) {
                TbsCoreLoadStat.getInstance().a(context, 308, new Throwable());
                return false;
            }
            if (((objA3 instanceof String) && ((String) objA3).equalsIgnoreCase("AuthenticationFail")) || !(objA3 instanceof Boolean)) {
                return false;
            }
            f20832p = g.d();
            boolean zA = a(context, g.d());
            z4 = ((Boolean) objA3).booleanValue() && !zA;
            if (!z4) {
                TbsCoreLoadStat.getInstance().a(context, 318, new Throwable("isX5Disable:" + zA + "(Boolean) ret:" + objA3));
            }
            return z4;
        }
        if ((objA2 instanceof String) && ((String) objA2).equalsIgnoreCase("AuthenticationFail")) {
            TbsLog.e("QbSdk", "canLoadX5", "[LoadError] authenticationFail");
            return false;
        }
        if (!(objA2 instanceof Bundle)) {
            TbsCoreLoadStat.getInstance().a(context, 323, new Throwable("" + objA2));
            TbsLog.e("QbSdk", "canLoadX5", "[LoadError] ret not instance of bundle, ret:" + objA2);
            return false;
        }
        Bundle bundle = (Bundle) objA2;
        if (bundle.isEmpty()) {
            TbsCoreLoadStat.getInstance().a(context, 324, new Throwable("" + objA2));
            TbsLog.e("QbSdk", "canLoadX5", "[LoadError] empty bundle" + objA2);
            return false;
        }
        try {
            int i2 = bundle.getInt(FontsContractCompat.Columns.RESULT_CODE, -1);
            z4 = i2 == 0;
            if (!z4) {
                try {
                    objA = com.tencent.smtt.utils.j.a(f20835s, "getErrorCodeForLogReport", (Class<?>[]) new Class[0], new Object[0]);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    objA = null;
                }
                if (objA instanceof Integer) {
                    TbsCoreLoadStat.getInstance().a(context, ((Integer) objA).intValue(), new Throwable("detail: " + objA));
                } else {
                    TbsCoreLoadStat.getInstance().a(context, 319, new Throwable("retcode:" + i2 + "; detail: " + objA));
                }
                return false;
            }
            try {
                f20833q = bundle.getString("tbs_core_version", "0");
            } catch (Exception unused) {
                f20833q = "0";
            }
            try {
                f20832p = Integer.parseInt(f20833q);
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
                TbsLog.e("QbSdk", "core version string to num error: " + f20833q);
                f20832p = 0;
            }
            g.a(f20832p);
            if (f20832p == 0) {
                TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("sTbsVersion is 0, string is " + f20833q));
                TbsLog.e("QbSdk", "canLoadX5", "[LoadError] sTbsVersion is 0, version string is " + f20833q);
                return false;
            }
            try {
                String[] stringArray = bundle.getStringArray("tbs_jarfiles");
                f20837u = stringArray;
                if (stringArray != null) {
                    try {
                        f20820d = bundle.getString("tbs_librarypath");
                        return z4;
                    } catch (Exception e4) {
                        TbsCoreLoadStat.getInstance().a(context, 307, e4);
                        return false;
                    }
                }
                TbsCoreLoadStat.getInstance().a(context, 307, new Throwable("sJarFiles not instanceof String[]: " + f20837u));
                TbsLog.e("QbSdk", "canLoadX5", "sJarFiles not instanceof String[]: " + f20837u);
                return false;
            } catch (Throwable th) {
                TbsCoreLoadStat.getInstance().a(context, 322, th);
                return false;
            }
        } catch (Exception e5) {
            TbsCoreLoadStat.getInstance().a(context, 319, e5);
            return false;
        }
    }

    public static String b() {
        Object objInvokeStaticMethod;
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b() || (objInvokeStaticMethod = wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getGUID", new Class[0], new Object[0])) == null || !(objInvokeStaticMethod instanceof String)) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public static boolean b(Context context) {
        SharedPreferences sharedPreferences;
        if (context == null) {
            return false;
        }
        try {
            if (!context.getApplicationInfo().packageName.contains("com.tencent.portfolio")) {
                return true;
            }
            TbsLog.i("QbSdk", "clearPluginConfigFile #1");
            String string = TbsDownloadConfig.getInstance(context).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONNAME, null);
            String str = context.getPackageManager().getPackageInfo("com.tencent.portfolio", 0).versionName;
            TbsLog.i("QbSdk", "clearPluginConfigFile oldAppVersionName is " + string + " newAppVersionName is " + str);
            if (string == null || string.contains(str) || (sharedPreferences = context.getSharedPreferences("plugin_setting", 0)) == null) {
                return true;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.commit();
            TbsLog.i("QbSdk", "clearPluginConfigFile done");
            return true;
        } catch (Throwable th) {
            TbsLog.i("QbSdk", "clearPluginConfigFile error is " + th.getMessage());
            return false;
        }
    }

    public static boolean canDownloadWithoutWifi() {
        return H;
    }

    @Deprecated
    public static void canGetAndroidId(boolean z2) {
        TbsPrivacyAccess.AndroidId.setEnabled(z2);
    }

    @Deprecated
    public static void canGetDeviceId(boolean z2) {
        TbsPrivacyAccess.DeviceId.setEnabled(z2);
    }

    @Deprecated
    public static void canGetSubscriberId(boolean z2) {
        TbsPrivacyAccess.Imsi.setEnabled(z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean canLoadVideo(android.content.Context r5) {
        /*
            java.lang.Object r0 = com.tencent.smtt.sdk.QbSdk.f20835s
            r1 = 1
            java.lang.Class[] r2 = new java.lang.Class[r1]
            java.lang.Class r3 = java.lang.Integer.TYPE
            r4 = 0
            r2[r4] = r3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            r1[r4] = r3
            java.lang.String r3 = "canLoadVideo"
            java.lang.Object r0 = com.tencent.smtt.utils.j.a(r0, r3, r2, r1)
            if (r0 == 0) goto L2a
            r1 = r0
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L33
            com.tencent.smtt.sdk.TbsCoreLoadStat r1 = com.tencent.smtt.sdk.TbsCoreLoadStat.getInstance()
            r2 = 313(0x139, float:4.39E-43)
            goto L30
        L2a:
            com.tencent.smtt.sdk.TbsCoreLoadStat r1 = com.tencent.smtt.sdk.TbsCoreLoadStat.getInstance()
            r2 = 314(0x13a, float:4.4E-43)
        L30:
            r1.a(r5, r2)
        L33:
            if (r0 != 0) goto L36
            goto L3c
        L36:
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r4 = r0.booleanValue()
        L3c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.canLoadVideo(android.content.Context):boolean");
    }

    @Deprecated
    public static boolean canLoadX5(Context context) {
        return a(context, false, false);
    }

    @Deprecated
    public static boolean canLoadX5FirstTimeThirdApp(Context context) {
        return false;
    }

    public static void canOpenFile(final Context context, final String str, final ValueCallback<Boolean> valueCallback) {
        new Thread() { // from class: com.tencent.smtt.sdk.QbSdk.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                w wVarA = w.a();
                wVarA.a(context);
                final boolean zA = wVarA.b() ? wVarA.c().a(context, str) : false;
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.smtt.sdk.QbSdk.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        valueCallback.onReceiveValue(Boolean.valueOf(zA));
                    }
                });
            }
        }.start();
    }

    public static boolean canOpenMimeFileType(Context context, String str) {
        a(context, false);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.util.Properties] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.FileInputStream, java.io.InputStream] */
    public static boolean canOpenWebPlus(Context context) throws IOException {
        BufferedInputStream bufferedInputStream;
        ?? fileInputStream;
        if (f20842z == 0) {
            f20842z = a.a();
        }
        TbsLog.i("QbSdk", "canOpenWebPlus - totalRAM: " + f20842z);
        boolean z2 = false;
        if (f20842z < A || context == null) {
            return false;
        }
        BufferedInputStream bufferedInputStream2 = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(o.a().p(context), "tbs.conf")));
        } catch (Throwable th) {
            th = th;
        }
        try {
            Properties properties = new Properties();
            properties.load(bufferedInputStream);
            String property = properties.getProperty("android_sdk_max_supported");
            String property2 = properties.getProperty("android_sdk_min_supported");
            int i2 = Integer.parseInt(property);
            int i3 = Integer.parseInt(property2);
            int i4 = Integer.parseInt(Build.VERSION.SDK);
            if (i4 <= i2 && i4 >= i3) {
                int i5 = Integer.parseInt(properties.getProperty("tbs_core_version"));
                try {
                    bufferedInputStream.close();
                } catch (Exception unused) {
                }
                try {
                    fileInputStream = new FileInputStream(new File(o.r(context), "tbs_extension.conf"));
                } catch (Throwable unused2) {
                }
                try {
                    ?? properties2 = new Properties();
                    properties2.load(fileInputStream);
                    int i6 = Integer.parseInt(properties2.getProperty("tbs_local_version"));
                    int i7 = Integer.parseInt(properties2.getProperty(TbsDownloadConfig.TbsConfigKey.KEY_APP_VERSIONCODE_FOR_SWITCH));
                    if (i5 != 88888888 && i6 != 88888888 && i5 <= i6 && i5 == i6 && ((i7 <= 0 || i7 == com.tencent.smtt.utils.b.e(context)) && Boolean.parseBoolean(properties2.getProperty("x5_disabled")))) {
                        if (!TbsDownloadConfig.getInstance(context.getApplicationContext()).mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_SWITCH_BACKUPCORE_ENABLE, false)) {
                            z2 = true;
                        }
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception unused3) {
                    }
                } catch (Throwable unused4) {
                    bufferedInputStream2 = fileInputStream;
                    try {
                        TbsLog.i("QbSdk", "canOpenWebPlus - isX5Disabled Exception");
                        if (bufferedInputStream2 != null) {
                            try {
                                bufferedInputStream2.close();
                            } catch (Exception unused5) {
                            }
                        }
                        z2 = true;
                        return !z2;
                    } catch (Throwable th2) {
                        if (bufferedInputStream2 != null) {
                            try {
                                bufferedInputStream2.close();
                            } catch (Exception unused6) {
                            }
                        }
                        throw th2;
                    }
                }
                return !z2;
            }
            TbsLog.i("QbSdk", "canOpenWebPlus - sdkVersion: " + i4);
            try {
                bufferedInputStream.close();
            } catch (Exception unused7) {
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream2 = bufferedInputStream;
            try {
                th.printStackTrace();
                TbsLog.i("QbSdk", "canOpenWebPlus - canLoadX5 Exception");
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (Exception unused8) {
                    }
                }
                return false;
            } catch (Throwable th4) {
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (Exception unused9) {
                    }
                }
                throw th4;
            }
        }
    }

    public static boolean canUseVideoFeatrue(Context context, int i2) {
        Object objA = com.tencent.smtt.utils.j.a(f20835s, "canUseVideoFeatrue", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i2));
        if (objA == null || !(objA instanceof Boolean)) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public static boolean checkApkExist(Context context, String str) throws PackageManager.NameNotFoundException {
        if (str != null && !"".equals(str)) {
            try {
                context.getPackageManager().getApplicationInfo(str, 8192);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean checkContentProviderPrivilage(Context context) {
        if (context == null || context.getApplicationInfo().targetSdkVersion < 24 || Build.VERSION.SDK_INT < 24 || "com.tencent.mobileqq".equals(context.getApplicationInfo().packageName)) {
            return true;
        }
        try {
            if (!TextUtils.isEmpty(context.getPackageManager().getProviderInfo(new ComponentName(context.getPackageName(), "androidx.core.content.FileProvider"), 0).authority)) {
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ProviderInfo providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(context.getApplicationInfo().packageName + ".provider", 128);
        if (providerInfoResolveContentProvider == null) {
            Log.e("QbSdk", "Must declare com.tencent.smtt.utils.FileProvider in AndroidManifest above Android 7.0,please view document in x5.tencent.com");
        }
        return providerInfoResolveContentProvider != null;
    }

    public static void checkTbsValidity(Context context) {
        if (context == null || com.tencent.smtt.utils.n.b(context)) {
            return;
        }
        TbsLog.e("QbSdk", "sys WebView: SysWebViewForcedBy checkTbsValidity");
        TbsCoreLoadStat.getInstance().a(context, 419);
        forceSysWebView();
    }

    @Deprecated
    public static void clear(Context context) {
    }

    public static void clearAllDefaultBrowser(Context context) {
        context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0).edit().clear().commit();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void clearAllWebViewCache(android.content.Context r7, boolean r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "clearAllWebViewCache("
            r0.append(r1)
            r0.append(r7)
            java.lang.String r1 = ", "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "QbSdk"
            com.tencent.smtt.utils.TbsLog.i(r1, r0)
            r0 = 1
            r2 = 0
            com.tencent.smtt.sdk.WebView r3 = new com.tencent.smtt.sdk.WebView     // Catch: java.lang.Throwable -> L48
            r3.<init>(r7)     // Catch: java.lang.Throwable -> L48
            com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension r3 = r3.getWebViewClientExtension()     // Catch: java.lang.Throwable -> L48
            if (r3 == 0) goto L65
            com.tencent.smtt.sdk.w r2 = com.tencent.smtt.sdk.w.a()     // Catch: java.lang.Throwable -> L45
            if (r2 == 0) goto L43
            boolean r3 = r2.b()     // Catch: java.lang.Throwable -> L45
            if (r3 == 0) goto L43
            com.tencent.smtt.sdk.x r2 = r2.c()     // Catch: java.lang.Throwable -> L45
            r2.a(r7, r8)     // Catch: java.lang.Throwable -> L45
        L43:
            r2 = r0
            goto L65
        L45:
            r2 = move-exception
            r3 = r0
            goto L4c
        L48:
            r3 = move-exception
            r6 = r3
            r3 = r2
            r2 = r6
        L4c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "clearAllWebViewCache exception 2 -- "
            r4.append(r5)
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            com.tencent.smtt.utils.TbsLog.e(r1, r2)
            r2 = r3
        L65:
            if (r2 == 0) goto L6d
            java.lang.String r7 = "is_in_x5_mode --> no need to clear system webview!"
            com.tencent.smtt.utils.TbsLog.i(r1, r7)
            return
        L6d:
            android.webkit.WebView r2 = new android.webkit.WebView     // Catch: java.lang.Throwable -> Lb5
            r2.<init>(r7)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r3 = "searchBoxJavaBridge_"
            r2.removeJavascriptInterface(r3)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r3 = "accessibility"
            r2.removeJavascriptInterface(r3)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r3 = "accessibilityTraversal"
            r2.removeJavascriptInterface(r3)     // Catch: java.lang.Throwable -> Lb5
            r2.clearCache(r0)     // Catch: java.lang.Throwable -> Lb5
            if (r8 == 0) goto L91
            android.webkit.CookieSyncManager.createInstance(r7)     // Catch: java.lang.Throwable -> Lb5
            android.webkit.CookieManager r8 = android.webkit.CookieManager.getInstance()     // Catch: java.lang.Throwable -> Lb5
            r8.removeAllCookie()     // Catch: java.lang.Throwable -> Lb5
        L91:
            android.webkit.WebViewDatabase r8 = android.webkit.WebViewDatabase.getInstance(r7)     // Catch: java.lang.Throwable -> Lb5
            r8.clearUsernamePassword()     // Catch: java.lang.Throwable -> Lb5
            android.webkit.WebViewDatabase r8 = android.webkit.WebViewDatabase.getInstance(r7)     // Catch: java.lang.Throwable -> Lb5
            r8.clearHttpAuthUsernamePassword()     // Catch: java.lang.Throwable -> Lb5
            android.webkit.WebViewDatabase r7 = android.webkit.WebViewDatabase.getInstance(r7)     // Catch: java.lang.Throwable -> Lb5
            r7.clearFormData()     // Catch: java.lang.Throwable -> Lb5
            android.webkit.WebStorage r7 = android.webkit.WebStorage.getInstance()     // Catch: java.lang.Throwable -> Lb5
            r7.deleteAllData()     // Catch: java.lang.Throwable -> Lb5
            android.webkit.WebIconDatabase r7 = android.webkit.WebIconDatabase.getInstance()     // Catch: java.lang.Throwable -> Lb5
            r7.removeAllIcons()     // Catch: java.lang.Throwable -> Lb5
            goto Lce
        Lb5:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "clearAllWebViewCache exception 1 -- "
            r8.append(r0)
            java.lang.String r7 = android.util.Log.getStackTraceString(r7)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            com.tencent.smtt.utils.TbsLog.e(r1, r7)
        Lce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.QbSdk.clearAllWebViewCache(android.content.Context, boolean):void");
    }

    public static void clearDefaultBrowser(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        String strD = com.tencent.smtt.sdk.ui.dialog.e.d(str);
        if (TextUtils.isEmpty(strD)) {
            strD = MimeTypes.ANY_TYPE;
        }
        sharedPreferences.edit().remove("key_tbs_picked_default_browser_" + strD).commit();
    }

    @Deprecated
    public static void closeFileReader(Context context) {
        w wVarA = w.a();
        wVarA.a(context);
        if (wVarA.b()) {
            wVarA.c().p();
        }
    }

    public static String closeNetLogAndSavaToLocal() {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            try {
                Object objInvokeStaticMethod = wVarA.c().b().invokeStaticMethod("com.tencent.smtt.livelog.NetLogManager", "closeNetLogAndSavaToLocal", new Class[0], new Object[0]);
                if (objInvokeStaticMethod != null && (objInvokeStaticMethod instanceof String)) {
                    return (String) objInvokeStaticMethod;
                }
            } catch (Exception unused) {
            }
        }
        return "";
    }

    public static void configurePrivacy(Context context, TbsPrivacyAccess.ConfigurablePrivacy configurablePrivacy, String str) {
        TbsPrivacyAccess.configurePrivacy(context, configurablePrivacy, str);
    }

    public static boolean createMiniQBShortCut(Context context, String str, String str2, Drawable drawable) {
        w wVarA;
        if (context != null && !TbsDownloader.getOverSea(context) && !isMiniQBShortCutExist(context, str, str2) && (wVarA = w.a()) != null && wVarA.b()) {
            Bitmap bitmap = drawable instanceof BitmapDrawable ? ((BitmapDrawable) drawable).getBitmap() : null;
            DexLoader dexLoaderB = wVarA.c().b();
            TbsLog.e("QbSdk", "qbsdk createMiniQBShortCut");
            Object objInvokeStaticMethod = dexLoaderB.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createMiniQBShortCut", new Class[]{Context.class, String.class, String.class, Bitmap.class}, context, str, str2, bitmap);
            TbsLog.e("QbSdk", "qbsdk after createMiniQBShortCut ret: " + objInvokeStaticMethod);
            if (objInvokeStaticMethod != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean d(Context context) {
        try {
            if (f20834r != null) {
                return true;
            }
            File fileP = o.a().p(context);
            if (fileP == null) {
                TbsLog.e("QbSdk", "QbSdk initForX5DisableConfig (false) optDir == null");
                return false;
            }
            File file = new File(o.a().p(context), "tbs_sdk_extension_dex.jar");
            if (!file.exists()) {
                TbsCoreLoadStat.getInstance().a(context, 406, new Exception("initForX5DisableConfig failure -- tbs_sdk_extension_dex.jar is not exist!"));
                return false;
            }
            String hostCorePathAppDefined = TbsShareManager.getHostCorePathAppDefined() != null ? TbsShareManager.getHostCorePathAppDefined() : fileP.getAbsolutePath();
            TbsLog.i("QbSdk", "QbSdk init optDirExtension #3 is " + hostCorePathAppDefined);
            TbsLog.i("QbSdk", "new DexLoader #4 dexFile is " + file.getAbsolutePath());
            w.a().b(context);
            com.tencent.smtt.utils.n.a(context);
            f20834r = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, hostCorePathAppDefined, getSettings()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
            if (!isEnableSensitiveApi() && com.tencent.smtt.utils.j.a(f20834r, "isSuiteableGetSensitative", (Class<?>[]) new Class[0], new Object[0]) == null) {
                TbsLog.e("QbSdk", "isSuiteableGetSensitative check failed,can not use x5");
                return false;
            }
            loadTBSSDKExtension(context, file.getParent());
            com.tencent.smtt.utils.j.a(f20835s, "setClientVersion", (Class<?>[]) new Class[]{Integer.TYPE}, 1);
            return true;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "initForX5DisableConfig sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    public static boolean deleteMiniQBShortCut(Context context, String str, String str2) {
        w wVarA;
        return (context == null || TbsDownloader.getOverSea(context) || (wVarA = w.a()) == null || !wVarA.b() || wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "deleteMiniQBShortCut", new Class[]{Context.class, String.class, String.class}, context, str, str2) == null) ? false : true;
    }

    @Deprecated
    public static void deleteStableCore(Context context, int i2) {
    }

    public static void disAllowThirdAppDownload() {
        f20819c = false;
    }

    public static void disableAutoCreateX5Webview() {
        f20826j = false;
    }

    @Deprecated
    public static void disableSensitiveApi() {
        TbsPrivacyAccess.disableSensitiveApi();
    }

    private static void e(Context context) {
        if (E || !Thread.currentThread().getName().equals("tbs_preinit")) {
            return;
        }
        E = true;
        TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- process:" + context.getApplicationInfo().processName + "; thread:" + Thread.currentThread().getName());
        int iH = o.a().h(context);
        if (iH <= 0) {
            return;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4);
            int i2 = sharedPreferences.getInt("tbs_preload_x5_recorder", 0) + 1;
            if (i2 > 3) {
                TbsCoreLoadStat.getInstance().a(context, 327, new Throwable("thread " + Thread.currentThread().getName()));
                f(context);
                g(context);
                TbsLogReport tbsLogReport = TbsLogReport.getInstance(context);
                TbsLogReport.TbsLogInfo tbsLogInfo = tbsLogReport.tbsLogInfo();
                tbsLogInfo.setErrorCode(iH);
                tbsLogReport.eventReport(TbsLogReport.EventType.TYPE_CORE_PROTECT_RESET, tbsLogInfo);
            } else {
                sharedPreferences.edit().putInt("tbs_preload_x5_recorder", i2).commit();
            }
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "tbs_preload_x5_counter Inc exception:" + Log.getStackTraceString(th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(Context context) {
        if (Thread.currentThread().getName().equals("tbs_preinit")) {
            context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4).edit().putInt("tbs_preload_x5_recorder", 0).commit();
        }
    }

    public static void fileInfoDetect(Context context, String str, android.webkit.ValueCallback<String> valueCallback) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        try {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "fileInfoDetect", new Class[]{Context.class, String.class, android.webkit.ValueCallback.class}, context, str, valueCallback);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void forceSysWebView() {
        f20818b = true;
        f20838v = "SysWebViewForcedByOuter: " + Log.getStackTraceString(new Throwable());
        TbsLog.e("QbSdk", "sys WebView: SysWebViewForcedByOuter");
    }

    private static void g(Context context) {
        TbsLog.e("QbSdk", "QbSdk reset!", true);
        try {
            TbsDownloader.stopDownload();
            TbsDownloader.c(context);
            FileUtil.a(getTbsFolderDir(context), false, "core_share_decouple");
            TbsLog.i("QbSdk", "delete downloaded apk success", true);
            o.b();
            File file = new File(context.getFilesDir(), TbsExtensionFunctionManager.BUGLY_SWITCH_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "QbSdk reset exception:" + Log.getStackTraceString(th));
        }
    }

    public static long getApkFileSize(Context context) {
        if (context != null) {
            return TbsDownloadConfig.getInstance(context.getApplicationContext()).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPKFILESIZE, 0L);
        }
        return 0L;
    }

    public static String getCurrentProcessName(Context context) throws IOException {
        FileInputStream fileInputStream;
        byte[] bArr;
        int i2;
        try {
            try {
                fileInputStream = new FileInputStream("/proc/self/cmdline");
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            bArr = new byte[256];
            i2 = 0;
            while (true) {
                int i3 = fileInputStream.read();
                if (i3 <= 0 || i2 >= 256) {
                    break;
                }
                bArr[i2] = (byte) i3;
                i2++;
            }
        } catch (Throwable th2) {
            th = th2;
            try {
                th.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return null;
            } catch (Throwable th3) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th3;
            }
        }
        if (i2 <= 0) {
            fileInputStream.close();
            return null;
        }
        String str = new String(bArr, 0, i2, "UTF-8");
        try {
            fileInputStream.close();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return str;
    }

    public static String[] getDexLoaderFileList(Context context, Context context2, String str) {
        String[] strArr = f20837u;
        if (!(strArr instanceof String[])) {
            Object objA = com.tencent.smtt.utils.j.a(f20835s, "getJarFiles", (Class<?>[]) new Class[]{Context.class, Context.class, String.class}, context, context2, str);
            if (!(objA instanceof String[])) {
                objA = new String[]{""};
            }
            return (String[]) objA;
        }
        int length = strArr.length;
        String[] strArr2 = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            strArr2[i2] = str + f20837u[i2];
        }
        return strArr2;
    }

    @Deprecated
    public static boolean getDownloadWithoutWifi() {
        return H;
    }

    public static boolean getIsInitX5Environment() {
        return f20827k;
    }

    public static boolean getIsSysWebViewForcedByOuter() {
        return f20818b;
    }

    public static boolean getJarFilesAndLibraryPath(Context context) {
        String str;
        Object obj = f20835s;
        if (obj == null) {
            str = "getJarFilesAndLibraryPath sExtensionObj is null";
        } else {
            Bundle bundle = (Bundle) com.tencent.smtt.utils.j.a(obj, "canLoadX5CoreAndNotLoadSo", (Class<?>[]) new Class[]{Integer.TYPE}, 44226);
            if (bundle != null) {
                f20837u = bundle.getStringArray("tbs_jarfiles");
                f20820d = bundle.getString("tbs_librarypath");
                return true;
            }
            str = "getJarFilesAndLibraryPath bundle is null and coreverison is " + o.a().a(true, context);
        }
        TbsLog.i("QbSdk", str);
        return false;
    }

    public static String getMiniQBVersion(Context context) {
        w wVarA = w.a();
        wVarA.a(context);
        if (wVarA.b()) {
            return wVarA.c().f();
        }
        return null;
    }

    public static boolean getOnlyDownload() {
        return f20828l;
    }

    public static String getQQBuildNumber() {
        return C;
    }

    public static Map<String, Object> getSettings() {
        return f20831o;
    }

    public static boolean getTBSInstalling() {
        return I;
    }

    public static String getTID() {
        return B;
    }

    public static File getTbsFolderDir(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (com.tencent.smtt.utils.b.c()) {
                return context.getDir("tbs_64", 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return context.getDir("tbs", 0);
    }

    public static String getTbsResourcesPath(Context context) {
        return TbsShareManager.a(context);
    }

    public static int getTbsSdkVersion() {
        return 44226;
    }

    public static int getTbsVersion(Context context) {
        return o.a().h(context);
    }

    public static int getTbsVersionForCrash(Context context) {
        if (f20817a) {
            return 0;
        }
        int i2 = o.a().i(context);
        if (i2 == 0 && m.a(context).c() == 3) {
            g(context);
        }
        return i2;
    }

    public static int getTmpDirTbsVersion(Context context) {
        if (m.a(context).c() == 2) {
            return o.a().d(context, 0);
        }
        if (m.a(context).b("copy_status") == 1) {
            return o.a().d(context, 1);
        }
        return 0;
    }

    public static String getX5CoreLoadHelp(Context context) {
        String str;
        if (context == null) {
            return "context is null";
        }
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        int iH = o.a().h(context);
        if (iH > 0) {
            int loadErrorCode = TbsCoreLoadStat.getLoadErrorCode();
            if (loadErrorCode == -1 && w.a().b()) {
                str = "core load success";
            } else {
                str = "Core Exist version is " + iH + ", error code: " + loadErrorCode + ", detail: " + TbsCoreLoadStat.getLoadErrorMessage() + ", suggestion: " + com.tencent.smtt.utils.f.a().a(loadErrorCode);
            }
        } else {
            int nextPostInterval = TbsDownloader.getNextPostInterval(context);
            if (TbsDownloader.f20927a) {
                str = "core is downloading, please check TbsListener.onDownloadProgress.";
            } else if (Apn.getApnType(context) != 3 && !canDownloadWithoutWifi()) {
                str = "Mobile Network will not download as default. If you want, you can set QbSdk.setDownloadWithoutWifi(true) in the beginning of your app.";
            } else if (nextPostInterval > 0) {
                str = "Perhaps server is busy, try to restart your app in " + nextPostInterval + "s later or (uninstall + reinstall).";
            } else {
                str = "Core not exist, unknown network problem.";
            }
        }
        return str + "If it doesn't help, contact with x5 with your tbslog.txt, site: x5.tencent.com";
    }

    public static void initBuglyAsync(boolean z2) {
        f20825i = z2;
    }

    public static void initForinitAndNotLoadSo(Context context) {
        String str;
        if (f20834r == null) {
            File fileP = o.a().p(context);
            if (fileP == null) {
                str = "QbSdk initForinitAndNotLoadSo optDir == null";
            } else {
                File file = new File(fileP, "tbs_sdk_extension_dex.jar");
                if (file.exists()) {
                    String absolutePath = fileP.getAbsolutePath();
                    w.a().b(context);
                    com.tencent.smtt.utils.n.a(context);
                    f20834r = new DexLoader(file.getParent(), context, new String[]{file.getAbsolutePath()}, absolutePath, getSettings()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                    return;
                }
                str = "QbSdk initForinitAndNotLoadSo dexFile.exists()=false";
            }
            Log.e("QbSdk", str);
        }
    }

    public static void initTbsSettings(Map<String, Object> map) {
        Map<String, Object> map2 = f20831o;
        if (map2 == null) {
            f20831o = map;
            return;
        }
        try {
            map2.putAll(map);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void initX5Environment(final Context context, final PreInitCallback preInitCallback) {
        com.tencent.smtt.utils.r.a().a("init_tbs_Start");
        TbsLog.initIfNeed(context);
        if (context == null) {
            TbsLog.e("QbSdk", "initX5Environment,context=null");
            return;
        }
        b(context);
        G = new TbsListener() { // from class: com.tencent.smtt.sdk.QbSdk.5
            @Override // com.tencent.smtt.sdk.TbsListener
            public void onDownloadFinish(int i2) {
            }

            @Override // com.tencent.smtt.sdk.TbsListener
            public void onDownloadProgress(int i2) {
            }

            @Override // com.tencent.smtt.sdk.TbsListener
            public void onInstallFinish(int i2) {
                QbSdk.preInit(context, preInitCallback);
            }
        };
        if (TbsShareManager.isThirdPartyApp(context)) {
            o.a().b(context, g.f21179a == 0);
        }
        TbsDownloader.needDownload(context, false, false, true, new TbsDownloader.TbsDownloaderCallback() { // from class: com.tencent.smtt.sdk.QbSdk.6
            @Override // com.tencent.smtt.sdk.TbsDownloader.TbsDownloaderCallback
            public void onNeedDownloadFinish(boolean z2, int i2) {
                if (TbsShareManager.isThirdPartyApp(context)) {
                    QbSdk.f20830n.onDownloadFinish(TbsDownloadConfig.getInstance(context).getCurrentDownloadInterruptCode());
                    if (QbSdk.f20825i) {
                        TbsExtensionFunctionManager.getInstance().initTbsBuglyIfNeed(context);
                    }
                }
                QbSdk.preInit(context, preInitCallback);
            }
        });
        f20827k = true;
    }

    public static boolean installLocalQbApk(Context context, String str, String str2, Bundle bundle) {
        g gVarA = g.a(true);
        gVarA.a(context, false, false);
        if (gVarA.b()) {
            return gVarA.a().a(context, str, str2, bundle);
        }
        return false;
    }

    @Deprecated
    public static void installLocalTbsCore(Context context, int i2, String str) {
        o.a().a(context, str, i2);
    }

    public static boolean intentDispatch(WebView webView, Intent intent, String str, String str2) {
        String str3;
        if (webView == null) {
            return false;
        }
        if (str.startsWith("mttbrowser://miniqb/ch=icon?")) {
            Context context = webView.getContext();
            int iIndexOf = str.indexOf("url=");
            str = iIndexOf > 0 ? str.substring(iIndexOf + 4) : null;
            HashMap map = new HashMap();
            try {
                str3 = context.getApplicationInfo().packageName;
            } catch (Exception e2) {
                e2.printStackTrace();
                str3 = "unknown";
            }
            map.put("ChannelID", str3);
            map.put("PosID", "14004");
            if (MttLoader.loadUrl(context, "miniqb://home".equals(str) ? "qb://navicard/addCard?cardId=168&cardName=168" : str, map, "QbSdk.startMiniQBToLoadUrl", null) != 0) {
                w wVarA = w.a();
                if (wVarA != null && wVarA.b() && wVarA.c().a(context, str, null, str2, null) == 0) {
                    return true;
                }
                webView.loadUrl(str);
            }
        } else {
            webView.loadUrl(str);
        }
        return false;
    }

    public static boolean isCoreVerEnabled(Context context, int i2) {
        int i3 = K;
        if (i2 >= i3 || i2 == 0 || i3 == -1 || !TbsShareManager.isThirdPartyApp(context)) {
            return true;
        }
        g(context);
        TbsCoreLoadStat.getInstance().a(context, 178, new Throwable("QbSdk.setCoreMinVersion: " + K));
        return false;
    }

    @Deprecated
    public static boolean isEnableCanGetSubscriberId() {
        return TbsPrivacyAccess.Imsi.isEnabled();
    }

    @Deprecated
    public static boolean isEnableGetAndroidID() {
        return TbsPrivacyAccess.AndroidId.isEnabled();
    }

    @Deprecated
    public static boolean isEnableGetDeviceID() {
        return TbsPrivacyAccess.DeviceId.isEnabled();
    }

    @Deprecated
    public static boolean isEnableSensitiveApi() {
        return TbsPrivacyAccess.isEnableSensitiveApi();
    }

    public static boolean isInDefaultBrowser(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        String strD = com.tencent.smtt.sdk.ui.dialog.e.d(str);
        if (TextUtils.isEmpty(strD)) {
            strD = MimeTypes.ANY_TYPE;
        }
        return sharedPreferences.contains("key_tbs_picked_default_browser_" + strD);
    }

    public static boolean isMiniQBShortCutExist(Context context, String str, String str2) {
        w wVarA;
        Object objInvokeStaticMethod;
        if (context == null || TbsDownloader.getOverSea(context) || (wVarA = w.a()) == null || !wVarA.b() || (objInvokeStaticMethod = wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "isMiniQBShortCutExist", new Class[]{Context.class, String.class}, context, str)) == null) {
            return false;
        }
        Boolean bool = Boolean.FALSE;
        if (objInvokeStaticMethod instanceof Boolean) {
            bool = (Boolean) objInvokeStaticMethod;
        }
        return bool.booleanValue();
    }

    public static boolean isNeedInitX5FirstTime() {
        return f20840x;
    }

    public static boolean isSuportOpenFile(String str, int i2) {
        List listAsList;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArr = {"rar", "zip", ArchiveStreamFactory.TAR, "bz2", CompressorStreamFactory.GZIP, ArchiveStreamFactory.SEVEN_Z, "doc", "docx", "ppt", "pptx", "xls", "xlsx", "txt", "pdf", "epub", "chm", "html", "htm", AliyunVodHttpCommon.Format.FORMAT_XML, "mht", "url", "ini", BuildConfig.FLAVOR_type, "bat", "php", PLVDownloadDirManager.JS_PREFIX, "lrc", "jpg", "jpeg", "png", ImgUtil.IMAGE_TYPE_GIF, ImgUtil.IMAGE_TYPE_BMP, "tiff", "webp", "mp3", "m4a", "aac", "amr", "wav", "ogg", "mid", "ra", "wma", "mpga", "ape", "flac", "RTSP", "RTP", "SDP", "RTMP", "mp4", "flv", "avi", "3gp", "3gpp", "webm", "ts", "ogv", "m3u8", "asf", "wmv", "rmvb", "rm", "f4v", "dat", "mov", "mpg", "mkv", "mpeg", "mpeg1", "mpeg2", "xvid", "dvd", "vcd", "vob", "divx"};
        String[] strArr2 = {"doc", "docx", "ppt", "pptx", "xls", "xlsx", "txt", "pdf", "epub"};
        if (i2 == 1) {
            listAsList = Arrays.asList(strArr2);
        } else {
            if (i2 != 2) {
                return false;
            }
            listAsList = Arrays.asList(strArr);
        }
        return listAsList.contains(str.toLowerCase());
    }

    public static boolean isTbsCoreInited() {
        g gVarA = g.a(false);
        return gVarA != null && gVarA.h();
    }

    public static boolean isX5Core() {
        if (getIsSysWebViewForcedByOuter()) {
            return false;
        }
        return w.a().b();
    }

    public static boolean isX5DisabledSync(Context context) {
        if (m.a(context).c() == 2) {
            return false;
        }
        if (!d(context)) {
            return true;
        }
        int iH = o.a().h(context);
        Object obj = f20835s;
        Class cls = Integer.TYPE;
        Object objA = com.tencent.smtt.utils.j.a(obj, "isX5DisabledSync", (Class<?>[]) new Class[]{cls, cls}, Integer.valueOf(iH), 44226);
        if (objA != null) {
            return ((Boolean) objA).booleanValue();
        }
        return true;
    }

    public static void loadTBSSDKExtension(Context context, String str) {
        Constructor<?> constructor;
        boolean z2;
        Object objNewInstance;
        if (f20835s != null) {
            return;
        }
        synchronized (QbSdk.class) {
            if (f20835s != null) {
                return;
            }
            if (f20834r == null) {
                TbsLog.i("QbSdk", "QbSdk loadTBSSDKExtension sExtensionClass is null");
            }
            try {
                constructor = f20834r.getConstructor(Context.class, Context.class, String.class, String.class, String.class);
                z2 = true;
            } catch (Throwable unused) {
                constructor = null;
                z2 = false;
            }
            try {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                if (z2) {
                    objNewInstance = constructor.newInstance(context, context, null, str, (!"com.tencent.mm".equals(getCurrentProcessName(context)) || WebView.mWebViewCreated) ? null : "notLoadSo");
                } else {
                    objNewInstance = f20834r.getConstructor(Context.class, Context.class).newInstance(context, context);
                }
                f20835s = objNewInstance;
            } catch (Throwable th) {
                TbsCoreLoadStat.getInstance().a(context, 310, th);
                TbsLog.e("QbSdk", "throwable" + Log.getStackTraceString(th));
            }
        }
    }

    public static void openBrowserList(Context context, String str, Bundle bundle, final ValueCallback<String> valueCallback) {
        String str2;
        if (context == null) {
            return;
        }
        String string = bundle != null ? bundle.getString("ChannelId") : "";
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse(str));
        String strD = com.tencent.smtt.sdk.ui.dialog.e.d(str);
        isDefaultDialog = false;
        com.tencent.smtt.sdk.ui.dialog.d dVar = new com.tencent.smtt.sdk.ui.dialog.d(context, "选择其它应用打开", intent, bundle, valueCallback, strD, string);
        String strA = dVar.a();
        if (strA != null && !TextUtils.isEmpty(strA)) {
            if (TbsConfig.APP_QB.equals(strA)) {
                intent.putExtra("ChannelID", context.getApplicationContext().getPackageName());
                intent.putExtra("PosID", "4");
            }
            intent.setPackage(strA);
            intent.putExtra("big_brother_source_key", string);
            context.startActivity(intent);
            if (valueCallback == null) {
                return;
            }
            str2 = "default browser:" + strA;
        } else if (!isDefaultDialog) {
            dVar.show();
            dVar.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tencent.smtt.sdk.QbSdk.2
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    ValueCallback valueCallback2 = valueCallback;
                    if (valueCallback2 != null) {
                        valueCallback2.onReceiveValue("TbsReaderDialogClosed");
                    }
                }
            });
            return;
        } else {
            new AlertDialog.Builder(context).setTitle("提示").setMessage("此文件无法打开").setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.tencent.smtt.sdk.QbSdk.10
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                }
            }).show();
            if (valueCallback == null) {
                return;
            } else {
                str2 = "can not open";
            }
        }
        valueCallback.onReceiveValue(str2);
    }

    public static void openBrowserList(Context context, String str, ValueCallback<String> valueCallback) {
        openBrowserList(context, str, null, valueCallback);
    }

    @Deprecated
    public static int openFileReader(Context context, String str, HashMap<String, String> map, ValueCallback<String> valueCallback) {
        String str2;
        TbsLog.i("QbSdk", "Open file reader filepath = " + str);
        if (map == null) {
            map = new HashMap<>();
        }
        if (!checkContentProviderPrivilage(context)) {
            TbsLog.e("QbSdk", "Got an unexpected result when check content provider privilege above api 24");
            return -5;
        }
        if (TextUtils.isEmpty(str)) {
            if (valueCallback != null) {
                valueCallback.onReceiveValue("filepath error");
            }
            TbsLog.e("QbSdk", "open openFileReader filepath error ret -1");
            return -1;
        }
        String lowerCase = str.substring(str.lastIndexOf(StrPool.DOT) + 1).toLowerCase();
        String str3 = map.get("fileext");
        String str4 = !TextUtils.isEmpty(str3) ? str3 : lowerCase;
        if (str4 == null) {
            TbsLog.e("QbSdk", "open openFileReader null file type");
        }
        if ("apk".equalsIgnoreCase(str4)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (context != null && context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(1);
            }
            Uri uriA = FileProvider.a(context, str);
            if (uriA == null) {
                valueCallback.onReceiveValue("uri failed");
                return -6;
            }
            intent.setDataAndType(uriA, "application/vnd.android.package-archive");
            context.startActivity(intent);
            TbsLog.e("QbSdk", "open openFileReader ret = 4");
            return 4;
        }
        String packageName = context.getPackageName();
        if (packageName.equals("com.tencent.mm") || packageName.equals("com.tencent.moblieqq")) {
            if (!MttLoader.isBrowserInstalled(context)) {
                str2 = "openFileReader QQ browser not installed";
            } else {
                if (!isSuportOpenFile(str4, 2)) {
                    TbsLog.e("QbSdk", "Open file reader in qq browser, isQBSupport: false  ret = 3");
                    openFileReaderListWithQBDownload(context, str, valueCallback);
                    return 3;
                }
                if (startQBForDoc(context, str, 4, 0, str4, a(context, map))) {
                    if (valueCallback != null) {
                        valueCallback.onReceiveValue("open QB");
                    }
                    TbsLog.e("QbSdk", "open openFileReader open QB ret = 1");
                    return 1;
                }
                str2 = "openFileReader startQBForDoc return false";
            }
            TbsLog.i("QbSdk", str2);
        }
        map.put(ImagesContract.LOCAL, k.a.f27523u);
        TbsLog.setWriteLogJIT(true);
        int iStartMiniQBToLoadUrl = startMiniQBToLoadUrl(context, str, map, valueCallback);
        if (iStartMiniQBToLoadUrl == 0) {
            TbsLog.i("QbSdk", "openFileReader open in MiniQB");
            return 2;
        }
        openFileReaderListWithQBDownload(context, str, valueCallback);
        TbsLog.i("QbSdk", "openFileReader open filereader list startMiniQBToLoadUrl ret = " + iStartMiniQBToLoadUrl);
        return 3;
    }

    public static void openFileReaderListWithQBDownload(Context context, String str, Bundle bundle, final ValueCallback<String> valueCallback) {
        String str2;
        if (context == null || context.getApplicationInfo().packageName.equals(Constants.PACKAGE_QIM) || context.getApplicationInfo().packageName.equals("com.tencent.androidqqmail")) {
            return;
        }
        String string = bundle != null ? bundle.getString("ChannelId") : "";
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        String strD = com.tencent.smtt.sdk.ui.dialog.e.d(str);
        if (context.getApplicationInfo().targetSdkVersion >= 24 && Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(1);
        }
        Uri uriA = FileProvider.a(context, str);
        if (uriA == null) {
            TbsLog.i("QbSdk", "openFileReaderListWithQBDownload,uri failed");
            valueCallback.onReceiveValue("uri failed");
            return;
        }
        TbsLog.i("QbSdk", "openFileReaderListWithQBDownload,fileUri:" + str + ",mimeType:" + strD);
        intent.setDataAndType(uriA, strD);
        isDefaultDialog = false;
        com.tencent.smtt.sdk.ui.dialog.d dVar = new com.tencent.smtt.sdk.ui.dialog.d(context, "选择其它应用打开", intent, bundle, valueCallback, strD, string);
        String strA = dVar.a();
        TbsLog.i("QbSdk", "openFileReaderListWithQBDownload,defaultBrowser:" + strA);
        if (strA != null && !TextUtils.isEmpty(strA) && strA.startsWith("extraMenuEvent:")) {
            TbsLog.i("QbSdk", "openFileReaderListWithQBDownload, is default extra menu action");
            valueCallback.onReceiveValue(strA);
            return;
        }
        if (strA != null && !TextUtils.isEmpty(strA) && checkApkExist(context, strA)) {
            TbsLog.i("QbSdk", "openFileReaderListWithQBDownload, is default normal menu action");
            if (TbsConfig.APP_QB.equals(strA)) {
                intent.putExtra("ChannelID", context.getApplicationContext().getPackageName());
                intent.putExtra("PosID", "4");
            }
            if (!TextUtils.isEmpty(string)) {
                intent.putExtra("big_brother_source_key", string);
            }
            intent.setPackage(strA);
            context.startActivity(intent);
            if (valueCallback != null) {
                valueCallback.onReceiveValue("default browser:" + strA);
                return;
            }
            return;
        }
        if ("com.tencent.rtxlite".equalsIgnoreCase(context.getApplicationContext().getPackageName()) && isDefaultDialog) {
            new AlertDialog.Builder(context).setTitle("提示").setMessage("此文件无法打开").setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.tencent.smtt.sdk.QbSdk.8
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                }
            }).show();
            return;
        }
        if (isDefaultDialog) {
            TbsLog.i("QbSdk", "isDefaultDialog=true");
            if (valueCallback != null) {
                TbsLog.i("QbSdk", "isDefaultDialog=true, can not open");
                str2 = "can not open";
                valueCallback.onReceiveValue(str2);
            }
        } else {
            try {
                TbsLog.i("QbSdk", "isDefaultDialog=false,try to open dialog");
                dVar.show();
                dVar.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tencent.smtt.sdk.QbSdk.9
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        ValueCallback valueCallback2 = valueCallback;
                        if (valueCallback2 != null) {
                            valueCallback2.onReceiveValue("TbsReaderDialogClosed");
                        }
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
                TbsLog.i("QbSdk", "isDefaultDialog=false,try to open dialog, but failed");
                str2 = "TbsReaderDialogClosed";
            }
        }
        TbsLog.i("QbSdk", "unexpected return, dialogBuilder not show!");
    }

    public static void openFileReaderListWithQBDownload(Context context, String str, ValueCallback<String> valueCallback) {
        openFileReaderListWithQBDownload(context, str, null, valueCallback);
    }

    public static int openFileWithQB(Context context, String str, String str2) {
        TbsLog.i("QbSdk", "open openFileReader startMiniQBToLoadUrl filepath = " + str);
        if (!checkContentProviderPrivilage(context)) {
            return -1;
        }
        if (str == null) {
            TbsLog.i("QbSdk", "open openFileReader filepath error");
            return -5;
        }
        String lowerCase = str.substring(str.lastIndexOf(StrPool.DOT) + 1).toLowerCase();
        if (!MttLoader.isBrowserInstalled(context)) {
            TbsLog.i("QbSdk", "openFileReader QQ browser not installed");
            return -4;
        }
        if (!isSuportOpenFile(lowerCase, 2)) {
            TbsLog.i("QbSdk", "openFileReader open in QB isQBSupport: false");
            return -2;
        }
        HashMap map = new HashMap();
        map.put("ChannelID", context.getApplicationContext().getApplicationInfo().processName);
        map.put("PosID", Integer.toString(4));
        if (MttLoader.openDocWithQb(context, str, 0, lowerCase, str2, map, null)) {
            return 0;
        }
        TbsLog.i("QbSdk", "openFileReader startQBForDoc return false");
        return -3;
    }

    public static void openNetLog(String str) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        try {
            wVarA.c().b().invokeStaticMethod("com.tencent.smtt.livelog.NetLogManager", "openNetLog", new Class[]{String.class}, str);
        } catch (Exception unused) {
        }
    }

    public static void pauseDownload() {
        TbsDownloader.pauseDownload();
    }

    public static synchronized void preInit(Context context) {
        preInit(context, null);
    }

    public static synchronized void preInit(final Context context, final PreInitCallback preInitCallback) {
        com.tencent.smtt.utils.r.a().a("preinit_start");
        TbsLog.initIfNeed(context);
        TbsLog.i("QbSdk", "preInit", "processName: " + getCurrentProcessName(context));
        TbsLog.i("QbSdk", "preInit", "stack trace: " + Log.getStackTraceString(new Throwable("PreInit Detect:")));
        f20829m = f20817a;
        if (!f20836t) {
            TbsLog.i("QbSdk", "preInit", "#1# Start new thread to preInit");
            final Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.tencent.smtt.sdk.QbSdk.3
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    PreInitCallback preInitCallback2;
                    int i2 = message.what;
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 == 3 && (preInitCallback2 = preInitCallback) != null) {
                                preInitCallback2.onCoreInitFinished();
                                return;
                            }
                            return;
                        }
                        com.tencent.smtt.utils.r.a().a("preinit_finish");
                        PreInitCallback preInitCallback3 = preInitCallback;
                        if (preInitCallback3 != null) {
                            preInitCallback3.onViewInitFinished(false);
                            com.tencent.smtt.utils.r.a().a("init_tbs_end");
                        }
                        TbsLog.writeLogToDisk();
                        com.tencent.smtt.utils.r.a().a(context);
                        return;
                    }
                    com.tencent.smtt.utils.r.a().a("preinit_finish");
                    boolean unused = QbSdk.D = TbsExtensionFunctionManager.getInstance().canUseFunction(context, TbsExtensionFunctionManager.DISABLE_UNPREINIT);
                    if (QbSdk.f20826j) {
                        com.tencent.smtt.utils.r.a().a("create_webview_start");
                        x xVarC = w.a().c();
                        if (xVarC != null) {
                            xVarC.a(context);
                        }
                        com.tencent.smtt.utils.r.a().a("create_webview_end");
                    }
                    PreInitCallback preInitCallback4 = preInitCallback;
                    if (preInitCallback4 != null) {
                        preInitCallback4.onViewInitFinished(true);
                        com.tencent.smtt.utils.r.a().a("init_tbs_end");
                    }
                    com.tencent.smtt.utils.r.a().a(context);
                    TbsLog.writeLogToDisk();
                }
            };
            Thread thread = new Thread() { // from class: com.tencent.smtt.sdk.QbSdk.4
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    int iA = o.a().a(true, context);
                    TbsDownloader.setAppContext(context);
                    TbsLog.i("QbSdk", "QbSdk preinit ver is " + iA);
                    o.a().b(context, g.f21179a == 0);
                    TbsLog.i("QbSdk", "preInit", "#2-1# prepare initAndLoadSo");
                    w wVarA = w.a();
                    wVarA.a(context);
                    QbSdk.f(context);
                    TbsLog.i("QbSdk", "preInit", "#2-3# X5CoreEngine init finish");
                    boolean zB = wVarA.b();
                    handler.sendEmptyMessage(3);
                    if (zB) {
                        handler.sendEmptyMessage(1);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                }
            };
            thread.setName("tbs_preinit");
            thread.setPriority(10);
            thread.start();
            f20836t = true;
        }
    }

    public static void reset(Context context) {
        if (J) {
            TbsLog.w("QbSdk", "[warning] TBS only allow reset 1 times each process.");
        } else {
            J = true;
            g(context);
        }
    }

    @Deprecated
    public static void reset(Context context, boolean z2) {
        if (J) {
            TbsLog.w("QbSdk", "[warning] TBS only allow reset 1 times each process.");
        } else {
            J = true;
            g(context);
        }
    }

    @Deprecated
    public static void resetDecoupleCore(Context context) {
    }

    public static void resumeDownload() {
        TbsDownloader.resumeDownload();
    }

    public static void setAppList(Context context, List<String> list) {
        if (list == null) {
            return;
        }
        TbsPrivacyAccess.configureAllPrivacy(context, TextUtils.join(",", list));
    }

    public static void setCoreMinVersion(int i2) {
        TbsLog.i("QbSdk", "setCoreMinVersion: " + i2);
        K = i2;
    }

    public static void setCurrentID(String str) {
        if (str != null && str.startsWith(TID_QQNumber_Prefix)) {
            String strSubstring = str.substring(3);
            B = "0000000000000000".substring(strSubstring.length()) + strSubstring;
        }
    }

    public static void setDisableUnpreinitBySwitch(boolean z2) {
        D = z2;
        TbsLog.i("QbSdk", "setDisableUnpreinitBySwitch -- mDisableUnpreinitBySwitch is " + D);
    }

    public static void setDisableUseHostBackupCoreBySwitch(boolean z2) {
        mDisableUseHostBackupCore = z2;
        TbsLog.i("QbSdk", "setDisableUseHostBackupCoreBySwitch -- mDisableUseHostBackupCore is " + mDisableUseHostBackupCore);
    }

    public static void setDownloadWithoutWifi(boolean z2) {
        H = z2;
    }

    public static void setEnableForThirdParty(Context context, Bundle bundle) {
        com.tencent.smtt.utils.s.a(context, bundle);
    }

    public static void setNeedInitX5FirstTime(boolean z2) {
        f20840x = z2;
    }

    public static void setNetLogEncryptionKey(String str) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        try {
            wVarA.c().b().invokeStaticMethod("com.tencent.smtt.livelog.NetLogManager", "setNetLogEncryptionKey", new Class[]{String.class}, str);
        } catch (Exception unused) {
        }
    }

    public static void setNewDnsHostList(String str) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        try {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "setNewDnsHostList", new Class[]{String.class}, str);
        } catch (Exception unused) {
        }
    }

    public static void setOnlyDownload(boolean z2) {
        f20828l = z2;
    }

    public static void setQQBuildNumber(String str) {
        C = str;
    }

    public static void setTBSInstallingStatus(boolean z2) {
        I = z2;
    }

    public static void setTbsListener(TbsListener tbsListener) {
        F = tbsListener;
    }

    public static void setTbsLogClient(TbsLogClient tbsLogClient) {
        TbsLog.setTbsLogClient(tbsLogClient);
    }

    @Deprecated
    public static void setUploadCode(Context context, int i2) {
    }

    public static void setUserID(Context context, Bundle bundle) {
        TbsPrivacyAccess.configureAllPrivacy(context, bundle);
    }

    public static int startMiniQBToLoadUrl(Context context, String str, HashMap<String, String> map, android.webkit.ValueCallback<String> valueCallback) {
        TbsCoreLoadStat.getInstance().a(context, 501);
        if (context == null) {
            return -100;
        }
        w wVarA = w.a();
        wVarA.a(context);
        if (!wVarA.b()) {
            TbsCoreLoadStat.getInstance().a(context, 502);
            Log.e("QbSdk", "startMiniQBToLoadUrl  ret = -102");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        }
        if (context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") && getTbsVersion(context) < 25487) {
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        }
        int iA = wVarA.c().a(context, str, map, null, valueCallback);
        TbsLog.i("QbSdk", "startMiniQBToLoadUrl  ret = " + iA);
        return iA;
    }

    public static boolean startQBForDoc(Context context, String str, int i2, int i3, String str2, Bundle bundle) {
        HashMap map = new HashMap();
        map.put("ChannelID", context.getApplicationContext().getApplicationInfo().processName);
        map.put("PosID", Integer.toString(i2));
        return MttLoader.openDocWithQb(context, str, i3, str2, map, bundle);
    }

    public static boolean startQBForVideo(Context context, String str, int i2) {
        HashMap map = new HashMap();
        map.put("ChannelID", context.getApplicationInfo().processName);
        map.put("PosID", Integer.toString(i2));
        return MttLoader.openVideoWithQb(context, str, map);
    }

    public static boolean startQBToLoadurl(Context context, String str, int i2, WebView webView) {
        w wVarA;
        Object objInvokeStaticMethod;
        HashMap map = new HashMap();
        map.put("ChannelID", context.getApplicationInfo().processName);
        map.put("PosID", Integer.toString(i2));
        if (webView == null) {
            try {
                String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
                if ((str2 == "com.tencent.mm" || str2 == "com.tencent.mobileqq") && (wVarA = w.a()) != null && wVarA.b() && (objInvokeStaticMethod = wVarA.c().b().invokeStaticMethod("com.tencent.smtt.webkit.WebViewList", "getCurrentMainWebviewJustForQQandWechat", new Class[0], new Object[0])) != null) {
                    webView = (WebView) ((IX5WebViewBase) objInvokeStaticMethod).getView().getParent();
                }
            } catch (Exception unused) {
            }
        }
        return MttLoader.loadUrl(context, str, map, "QbSdk.startQBToLoadurl", webView) == 0;
    }

    public static boolean startQBWithBrowserlist(Context context, String str, int i2) {
        boolean zStartQBToLoadurl = startQBToLoadurl(context, str, i2, null);
        if (!zStartQBToLoadurl) {
            openBrowserList(context, str, null);
        }
        return zStartQBToLoadurl;
    }

    public static boolean startQbOrMiniQBToLoadUrl(Context context, String str, HashMap<String, String> map, ValueCallback<String> valueCallback) {
        if (context == null) {
            return false;
        }
        w wVarA = w.a();
        wVarA.a(context);
        if (map != null && "5".equals(map.get("PosID")) && wVarA.b()) {
        }
        if (MttLoader.loadUrl(context, str, map, "QbSdk.startMiniQBToLoadUrl", null) != 0) {
            return wVarA.b() && (!context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") || getTbsVersion(context) >= 25487) && wVarA.c().a(context, str, map, null, valueCallback) == 0;
        }
        return true;
    }

    public static void unForceSysWebView() {
        f20818b = false;
        TbsLog.e("QbSdk", "sys WebView: unForceSysWebView called");
    }

    public static void uploadNetLog(String str) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        try {
            wVarA.c().b().invokeStaticMethod("com.tencent.smtt.livelog.NetLogManager", "uploadNetLog", new Class[]{String.class}, str);
        } catch (Exception unused) {
        }
    }

    public static boolean useSoftWare() {
        Object obj = f20835s;
        if (obj == null) {
            return false;
        }
        Object objA = com.tencent.smtt.utils.j.a(obj, "useSoftWare", (Class<?>[]) new Class[0], new Object[0]);
        if (objA == null) {
            objA = com.tencent.smtt.utils.j.a(f20835s, "useSoftWare", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(a.a()));
        }
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }
}
