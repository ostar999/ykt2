package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
public enum TbsPrivacyAccess {
    DeviceId(false),
    Imsi(false),
    AndroidId(false),
    MacAddress(false),
    AndroidVersion(true),
    DeviceModel(true),
    AppList(true);


    /* renamed from: a, reason: collision with root package name */
    @Deprecated
    private static boolean f20987a = true;

    /* renamed from: c, reason: collision with root package name */
    private static String f20988c = "";

    /* renamed from: d, reason: collision with root package name */
    private static boolean f20989d = false;

    /* renamed from: e, reason: collision with root package name */
    private static String[] f20990e = null;

    /* renamed from: b, reason: collision with root package name */
    private boolean f20992b;

    public enum ConfigurablePrivacy {
        IMSI("imsi"),
        ANDROID_ID(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID),
        MAC(SocializeProtocolConstants.PROTOCOL_KEY_MAC),
        ANDROID_VERSION("android_version"),
        DEVICE_MODEL("device_model"),
        APP_LIST("app_list"),
        QIMEI36("q36"),
        MODEL("model"),
        OAID("oaid"),
        SERIAL("serial"),
        ACTION("action"),
        QB_INSTALLED("qb_installed");


        /* renamed from: a, reason: collision with root package name */
        String f20994a;

        ConfigurablePrivacy(String str) {
            this.f20994a = str;
        }
    }

    TbsPrivacyAccess(boolean z2) {
        this.f20992b = z2;
    }

    private static void a(Context context, SharedPreferences.Editor editor, ConfigurablePrivacy configurablePrivacy, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TbsLog.i("TbsPrivacy", "doConfigPrivacy  " + configurablePrivacy.f20994a + " is " + str);
        if (!TextUtils.isEmpty(configurablePrivacy.f20994a) && configurablePrivacy.f20994a.equals("action")) {
            a(context, str);
            return;
        }
        editor.putString(configurablePrivacy.f20994a, str);
        TbsLog.i("TbsPrivacy", "configurePrivacy " + configurablePrivacy.f20994a + " is " + str);
    }

    private static void a(Context context, String str) {
        try {
            if (!TextUtils.isEmpty(str) && FileUtil.a(context) && str.equals("deleteQBApk")) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                String str2 = File.separator;
                sb.append(str2);
                String string = sb.toString();
                if (!string.equals("")) {
                    string = string + "Android" + str2 + "data" + str2 + f20988c + str2 + "files" + str2 + "Download";
                }
                File file = new File(string);
                TbsLog.i("TbsPrivacy", "doActionByApp QbApkDir is " + file.getAbsolutePath());
                FileUtil.b(file);
            }
        } catch (Throwable th) {
            TbsLog.i("TbsPrivacy", "doActionByApp stack is " + Log.getStackTraceString(th));
        }
    }

    public static void configureAllPrivacy(Context context, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            String strI = com.tencent.smtt.utils.s.i(context);
            TbsLog.i("TbsPrivacy", "configureAllPrivacy state is " + strI);
            if (bundle.containsKey("qimei36") && !strI.contains("qimei36")) {
                bundle.putString(ConfigurablePrivacy.QIMEI36.f20994a, bundle.getString("qimei36"));
            }
            Iterator<String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                TbsLog.i("TbsPrivacy", "configureAllPrivacy bundle key is " + it.next());
            }
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("uifa", 0).edit();
            f20988c = context.getApplicationInfo().packageName;
            for (ConfigurablePrivacy configurablePrivacy : ConfigurablePrivacy.values()) {
                if (bundle.containsKey(configurablePrivacy.f20994a) && !strI.contains(configurablePrivacy.f20994a)) {
                    a(context, editorEdit, configurablePrivacy, bundle.getString(configurablePrivacy.f20994a));
                }
            }
            editorEdit.putString("app_call", "done");
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    public static void configureAllPrivacy(Context context, String str) {
        if (str == null) {
            return;
        }
        try {
            String strI = com.tencent.smtt.utils.s.i(context);
            TbsLog.i("TbsPrivacy", "configureAllPrivacy state is " + strI);
            if (strI.contains("app_list")) {
                return;
            }
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("uifa", 0).edit();
            editorEdit.putString("app_list", str);
            editorEdit.putString("app_call", "done");
            editorEdit.commit();
        } catch (Throwable unused) {
        }
    }

    public static void configurePrivacy(Context context, ConfigurablePrivacy configurablePrivacy, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("uifa", 0).edit();
        a(context, editorEdit, configurablePrivacy, str);
        editorEdit.commit();
    }

    @Deprecated
    public static void disableSensitiveApi() {
        f20987a = false;
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.NO_SENSITIVE_API, Boolean.TRUE);
        QbSdk.initTbsSettings(map);
    }

    public static String getConfigurePrivacy(Context context, ConfigurablePrivacy configurablePrivacy, String str) {
        return context.getSharedPreferences("uifa", 0).getString(configurablePrivacy.f20994a, str);
    }

    public static String[] getItemToRmPrivacy() {
        return f20990e;
    }

    @Deprecated
    public static boolean isEnableSensitiveApi() {
        return f20987a;
    }

    public static void rmPrivacyItemIfNeeded(Context context) {
        try {
            TbsLog.i("TbsPrivacy", "mRmPrivacyItemChecked is " + f20989d);
            if (f20989d) {
                return;
            }
            f20989d = true;
            String strI = com.tencent.smtt.utils.s.i(context);
            TbsLog.i("TbsPrivacy", "rmPrivacyItemIfNeeded state is " + strI);
            if (strI.equals("removenone")) {
                f20990e = null;
                return;
            }
            f20990e = strI.split("\\|");
            TbsLog.i("TbsPrivacy", "rmPrivacyItemIfNeeded mItemToRmPrivacy is " + f20990e);
            SharedPreferences sharedPreferences = context.getSharedPreferences("uifa", 0);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            for (String str : f20990e) {
                if (sharedPreferences.contains(str)) {
                    editorEdit.remove(str);
                }
            }
            editorEdit.commit();
        } catch (Throwable th) {
            TbsLog.i("TbsPrivacy", "stack is " + Log.getStackTraceString(th));
        }
    }

    public boolean isDisabled() {
        return !this.f20992b;
    }

    public boolean isEnabled() {
        return this.f20992b;
    }

    public void setEnabled(boolean z2) {
        this.f20992b = z2;
        TbsLog.i("TbsPrivacy", name() + " is " + z2);
    }
}
