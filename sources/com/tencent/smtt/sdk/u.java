package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.export.external.libwebp;
import com.tencent.smtt.utils.TbsLog;
import java.util.Map;

/* loaded from: classes6.dex */
class u {

    /* renamed from: a, reason: collision with root package name */
    private Context f21298a;

    /* renamed from: b, reason: collision with root package name */
    private Context f21299b;

    /* renamed from: c, reason: collision with root package name */
    private String f21300c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f21301d;

    /* renamed from: e, reason: collision with root package name */
    private DexLoader f21302e;

    /* renamed from: f, reason: collision with root package name */
    private String f21303f;

    /* renamed from: g, reason: collision with root package name */
    private String f21304g = null;

    public u(Context context, Context context2, String str, String str2, String[] strArr, String str3) throws Exception {
        boolean tbsCoreSandboxModeEnable;
        boolean zEquals;
        this.f21298a = null;
        this.f21299b = null;
        this.f21300c = null;
        this.f21301d = null;
        this.f21302e = null;
        this.f21303f = "TbsDexOpt";
        TbsLog.i("TbsWizard", "construction start...");
        if (context == null || ((context2 == null && TbsShareManager.getHostCorePathAppDefined() == null) || TextUtils.isEmpty(str) || strArr == null || strArr.length == 0)) {
            throw new Exception("TbsWizard paramter error:-1callerContext:" + context + "hostcontext" + context2 + "isEmpty" + TextUtils.isEmpty(str) + "dexfileList" + strArr);
        }
        this.f21298a = context.getApplicationContext();
        if (context2.getApplicationContext() != null) {
            this.f21299b = context2.getApplicationContext();
        } else {
            this.f21299b = context2;
        }
        this.f21300c = str;
        this.f21301d = strArr;
        this.f21303f = str2;
        for (int i2 = 0; i2 < this.f21301d.length; i2++) {
            TbsLog.i("TbsWizard", "#2 mDexFileList[" + i2 + "]: " + this.f21301d[i2]);
        }
        TbsLog.i("TbsWizard", "new DexLoader #2 libraryPath is " + str3 + " mCallerAppContext is " + this.f21298a + " dexOutPutDir is " + str2);
        this.f21302e = new DexLoader(str3, this.f21298a, this.f21301d, str2, QbSdk.f20831o);
        System.currentTimeMillis();
        a(context);
        libwebp.loadWepLibraryIfNeed(context2, this.f21300c);
        if ("com.nd.android.pandahome2".equals(this.f21298a.getApplicationInfo().packageName)) {
            this.f21302e.invokeStaticMethod("com.tencent.tbs.common.beacon.X5CoreBeaconUploader", "getInstance", new Class[]{Context.class}, this.f21298a);
        }
        if (QbSdk.f20831o != null) {
            try {
                tbsCoreSandboxModeEnable = TbsPVConfig.getInstance(this.f21298a).getTbsCoreSandboxModeEnable();
            } catch (Throwable unused) {
                tbsCoreSandboxModeEnable = false;
            }
            try {
                zEquals = k.a.f27523u.equals(String.valueOf(QbSdk.f20831o.get(TbsCoreSettings.TBS_SETTINGS_USE_SANDBOX)));
            } catch (Throwable th) {
                th.printStackTrace();
                zEquals = false;
            }
            QbSdk.f20831o.put(TbsCoreSettings.TBS_SETTINGS_USE_SANDBOX, Boolean.valueOf(tbsCoreSandboxModeEnable && zEquals));
            this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "initTbsSettings", new Class[]{Map.class}, QbSdk.f20831o);
            b(context);
        }
        int iC = c(context);
        if (iC >= 0) {
            TbsLog.i("TbsWizard", "construction end...");
            return;
        }
        throw new Exception("TbsWizard init error: " + iC + "; msg: " + this.f21304g);
    }

    private void b(Context context) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("tbss", 0).edit();
            for (Map.Entry<String, Object> entry : QbSdk.f20831o.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    editorEdit.putString(key, (String) value);
                    TbsLog.i("TbsWizard", "initTbsSettingsBySP key is " + key + " value is " + ((String) value));
                }
            }
            editorEdit.commit();
        } catch (Throwable th) {
            TbsLog.i("TbsWizard", "stack is " + Log.getStackTraceString(th));
        }
    }

    private int c(Context context) {
        Object objInvokeStaticMethod;
        int iIntValue;
        String str;
        if (this.f21299b != null || TbsShareManager.getHostCorePathAppDefined() == null) {
            TbsLog.i("TbsWizard", "initTesRuntimeEnvironment callerContext is " + context + " mHostContext is " + this.f21299b + " mDexLoader is " + this.f21302e + " mtbsInstallLocation is " + this.f21300c + " mDexOptPath is " + this.f21303f);
            DexLoader dexLoader = this.f21302e;
            objInvokeStaticMethod = dexLoader.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "initTesRuntimeEnvironment", new Class[]{Context.class, Context.class, DexLoader.class, String.class, String.class, String.class, Integer.TYPE, String.class}, context, this.f21299b, dexLoader, this.f21300c, this.f21303f, TbsConfig.TBS_SDK_VERSIONNAME, 44226, QbSdk.a());
        } else {
            DexLoader dexLoader2 = this.f21302e;
            objInvokeStaticMethod = dexLoader2.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "initTesRuntimeEnvironment", new Class[]{Context.class, Context.class, DexLoader.class, String.class, String.class, String.class, Integer.TYPE, String.class, String.class}, context, this.f21299b, dexLoader2, this.f21300c, this.f21303f, TbsConfig.TBS_SDK_VERSIONNAME, 44226, QbSdk.a(), TbsShareManager.getHostCorePathAppDefined());
        }
        if (objInvokeStaticMethod == null) {
            d();
            e();
            DexLoader dexLoader3 = this.f21302e;
            objInvokeStaticMethod = dexLoader3.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "initTesRuntimeEnvironment", new Class[]{Context.class, Context.class, DexLoader.class, String.class, String.class}, context, this.f21299b, dexLoader3, this.f21300c, this.f21303f);
        }
        if (objInvokeStaticMethod == null) {
            iIntValue = -3;
        } else if (objInvokeStaticMethod instanceof Integer) {
            iIntValue = ((Integer) objInvokeStaticMethod).intValue();
        } else if (objInvokeStaticMethod instanceof Throwable) {
            TbsCoreLoadStat.getInstance().a(this.f21298a, 321, (Throwable) objInvokeStaticMethod);
            iIntValue = -5;
        } else {
            iIntValue = -4;
        }
        if (iIntValue < 0) {
            Object objInvokeStaticMethod2 = this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "getLoadFailureDetails", new Class[0], new Object[0]);
            if (objInvokeStaticMethod2 instanceof Throwable) {
                Throwable th = (Throwable) objInvokeStaticMethod2;
                this.f21304g = DictionaryFactory.SHARP + th.getMessage() + "; cause: " + th.getCause() + "; th: " + th;
            }
            str = objInvokeStaticMethod2 instanceof String ? (String) objInvokeStaticMethod2 : null;
            return iIntValue;
        }
        this.f21304g = str;
        return iIntValue;
    }

    private void d() {
        this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "setTesSdkVersionName", new Class[]{String.class}, TbsConfig.TBS_SDK_VERSIONNAME);
    }

    private void e() {
        this.f21302e.setStaticField("com.tencent.tbs.tbsshell.TBSShell", "VERSION", 44226);
    }

    public String a() {
        String str = null;
        Object objInvokeStaticMethod = this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "invokeStaticMethod", new Class[]{Boolean.TYPE, String.class, String.class, Class[].class, Object[].class}, Boolean.TRUE, "com.tencent.smtt.util.CrashTracker", "getCrashExtraInfo", null, new Object[0]);
        if (objInvokeStaticMethod == null) {
            objInvokeStaticMethod = this.f21302e.invokeStaticMethod("com.tencent.smtt.util.CrashTracker", "getCrashExtraInfo", null, new Object[0]);
        }
        if (objInvokeStaticMethod != null) {
            str = String.valueOf(objInvokeStaticMethod) + " ReaderPackName=" + TbsReaderView.gReaderPackName + " ReaderPackVersion=" + TbsReaderView.gReaderPackVersion;
        }
        return str == null ? "X5 core get nothing..." : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(android.content.Context r3) {
        /*
            r2 = this;
            java.util.Map<java.lang.String, java.lang.Object> r0 = com.tencent.smtt.sdk.QbSdk.f20831o
            if (r0 == 0) goto L15
            java.lang.String r1 = "check_tbs_validity"
            java.lang.Object r0 = r0.get(r1)
            boolean r1 = r0 instanceof java.lang.Boolean
            if (r1 == 0) goto L15
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            goto L16
        L15:
            r0 = 1
        L16:
            if (r0 == 0) goto L1b
            com.tencent.smtt.utils.n.b(r3)
        L1b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.u.a(android.content.Context):void");
    }

    public void a(Context context, Context context2, String str, String str2, String[] strArr, String str3) throws Exception {
        this.f21298a = context.getApplicationContext();
        if (this.f21299b.getApplicationContext() != null) {
            this.f21299b = this.f21299b.getApplicationContext();
        }
        this.f21300c = str;
        this.f21301d = strArr;
        this.f21303f = str2;
        libwebp.loadWepLibraryIfNeed(context2, str);
        Map<String, Object> map = QbSdk.f20831o;
        if (map != null) {
            this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "initTbsSettings", new Class[]{Map.class}, map);
        }
        int iC = c(context);
        if (iC >= 0) {
            return;
        }
        throw new Exception("continueInit init error: " + iC + "; msg: " + this.f21304g);
    }

    public boolean a(Context context, String str, String str2, Bundle bundle) {
        Object objInvokeStaticMethod = this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "installLocalQbApk", new Class[]{Context.class, String.class, String.class, Bundle.class}, context, str, str2, bundle);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public String b() {
        String str = null;
        Object objInvokeStaticMethod = this.f21302e.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "invokeStaticMethod", new Class[]{Boolean.TYPE, String.class, String.class, Class[].class, Object[].class}, Boolean.TRUE, "com.tencent.smtt.util.CrashTracker", "getCrashExtraCacheInfo", null, new Object[0]);
        if (objInvokeStaticMethod == null) {
            objInvokeStaticMethod = this.f21302e.invokeStaticMethod("com.tencent.smtt.util.CrashTracker", "getCrashExtraCacheInfo", null, new Object[0]);
        }
        if (objInvokeStaticMethod != null) {
            str = String.valueOf(objInvokeStaticMethod) + " ReaderPackName=" + TbsReaderView.gReaderPackName + " ReaderPackVersion=" + TbsReaderView.gReaderPackVersion;
        }
        return str == null ? "X5 core get cache nothing..." : str;
    }

    public DexLoader c() {
        return this.f21302e;
    }
}
