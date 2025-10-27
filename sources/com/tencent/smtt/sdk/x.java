package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5CoreServiceWorkerController;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.external.interfaces.IconListener;
import com.tencent.smtt.utils.TbsLog;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
class x {

    /* renamed from: a, reason: collision with root package name */
    private DexLoader f21396a;

    /* renamed from: b, reason: collision with root package name */
    private String f21397b = "";

    public x(DexLoader dexLoader) {
        this.f21396a = dexLoader;
    }

    public int a(Context context, String str, Map<String, String> map, String str2, android.webkit.ValueCallback<String> valueCallback) {
        if (TbsDownloader.getOverSea(context)) {
            return -103;
        }
        DexLoader dexLoader = this.f21396a;
        if (str2 != null) {
            Object objInvokeStaticMethod = dexLoader.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "startMiniQB", new Class[]{Context.class, String.class, String.class}, context, str, str2);
            return objInvokeStaticMethod == null ? TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION : ((Integer) objInvokeStaticMethod).intValue();
        }
        Object objInvokeStaticMethod2 = dexLoader.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "startMiniQB", new Class[]{Context.class, String.class, Map.class, android.webkit.ValueCallback.class}, context, str, map, valueCallback);
        if (objInvokeStaticMethod2 == null) {
            objInvokeStaticMethod2 = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "startMiniQB", new Class[]{Context.class, String.class, Map.class}, context, str, map);
        }
        if (objInvokeStaticMethod2 == null) {
            objInvokeStaticMethod2 = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "startMiniQB", new Class[]{Context.class, String.class}, context, str);
        }
        return objInvokeStaticMethod2 == null ? TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION : ((Integer) objInvokeStaticMethod2).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0075 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0076 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.tencent.smtt.export.external.interfaces.IX5WebViewBase a(android.content.Context r9) {
        /*
            r8 = this;
            com.tencent.smtt.export.external.DexLoader r0 = r8.f21396a
            r1 = 1
            java.lang.Class[] r2 = new java.lang.Class[r1]
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r4 = 0
            r2[r4] = r3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r4] = r9
            java.lang.String r3 = "com.tencent.tbs.tbsshell.WebCoreProxy"
            java.lang.String r5 = "createSDKWebview"
            java.lang.Object r0 = r0.invokeStaticMethod(r3, r5, r2, r1)
            r1 = 325(0x145, float:4.55E-43)
            r2 = 0
            if (r0 != 0) goto L50
            com.tencent.smtt.export.external.DexLoader r3 = r8.f21396a     // Catch: java.lang.Exception -> L6e
            java.lang.String r5 = "com.tencent.tbs.tbsshell.TBSShell"
            java.lang.String r6 = "getLoadFailureDetails"
            java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L6e
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L6e
            java.lang.Object r3 = r3.invokeStaticMethod(r5, r6, r7, r4)     // Catch: java.lang.Exception -> L6e
            if (r3 == 0) goto L39
            boolean r4 = r3 instanceof java.lang.Throwable     // Catch: java.lang.Exception -> L6e
            if (r4 == 0) goto L39
            com.tencent.smtt.sdk.TbsCoreLoadStat r4 = com.tencent.smtt.sdk.TbsCoreLoadStat.getInstance()     // Catch: java.lang.Exception -> L6e
            r5 = r3
            java.lang.Throwable r5 = (java.lang.Throwable) r5     // Catch: java.lang.Exception -> L6e
            r4.a(r9, r1, r5)     // Catch: java.lang.Exception -> L6e
        L39:
            if (r3 == 0) goto L4d
            boolean r4 = r3 instanceof java.lang.String     // Catch: java.lang.Exception -> L6e
            if (r4 == 0) goto L4d
            com.tencent.smtt.sdk.TbsCoreLoadStat r4 = com.tencent.smtt.sdk.TbsCoreLoadStat.getInstance()     // Catch: java.lang.Exception -> L6e
            java.lang.Throwable r5 = new java.lang.Throwable     // Catch: java.lang.Exception -> L6e
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Exception -> L6e
            r5.<init>(r3)     // Catch: java.lang.Exception -> L6e
            r4.a(r9, r1, r5)     // Catch: java.lang.Exception -> L6e
        L4d:
            r0 = r2
            r3 = r0
            goto L73
        L50:
            r3 = r0
            com.tencent.smtt.export.external.interfaces.IX5WebViewBase r3 = (com.tencent.smtt.export.external.interfaces.IX5WebViewBase) r3     // Catch: java.lang.Exception -> L6e
            if (r3 == 0) goto L73
            android.view.View r4 = r3.getView()     // Catch: java.lang.Exception -> L6c
            if (r4 != 0) goto L73
            com.tencent.smtt.sdk.TbsCoreLoadStat r4 = com.tencent.smtt.sdk.TbsCoreLoadStat.getInstance()     // Catch: java.lang.Exception -> L6c
            java.lang.Throwable r5 = new java.lang.Throwable     // Catch: java.lang.Exception -> L6c
            java.lang.String r6 = "x5webview.getView is null!"
            r5.<init>(r6)     // Catch: java.lang.Exception -> L6c
            r4.a(r9, r1, r5)     // Catch: java.lang.Exception -> L6c
            r0 = r2
            goto L73
        L6c:
            r9 = move-exception
            goto L70
        L6e:
            r9 = move-exception
            r3 = r2
        L70:
            r9.printStackTrace()
        L73:
            if (r0 != 0) goto L76
            return r2
        L76:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.x.a(android.content.Context):com.tencent.smtt.export.external.interfaces.IX5WebViewBase");
    }

    public InputStream a(String str, boolean z2) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getCacheFile", new Class[]{String.class, Boolean.TYPE}, str, Boolean.valueOf(z2));
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (InputStream) objInvokeStaticMethod;
    }

    public String a(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getCookie", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public String a(String str, String str2, String str3) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilComposeSearchUrl", new Class[]{String.class, String.class, String.class}, str, str2, str3);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public void a(Context context, boolean z2) {
        TbsLog.w("desktop", " tbsWizard clearAllX5Cache");
        if (z2) {
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "clearAllCache", new Class[]{Context.class}, context);
            return;
        }
        try {
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "clearAllCache", new Class[]{Context.class, Boolean.TYPE}, context, Boolean.valueOf(z2));
        } catch (Exception unused) {
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearUsernamePassword", new Class[]{Context.class}, context);
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearHttpAuthUsernamePassword", new Class[]{Context.class}, context);
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearFormData", new Class[]{Context.class}, context);
            this.f21396a.invokeStaticMethod("com.tencent.smtt.webkit.CacheManager", "removeAllCacheFiles", null, new Object[0]);
            this.f21396a.invokeStaticMethod("com.tencent.smtt.webkit.CacheManager", "clearLocalStorage", null, new Object[0]);
            Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.smtt.net.http.DnsManager", "getInstance", null, new Object[0]);
            if (objInvokeStaticMethod != null) {
                this.f21396a.invokeMethod(objInvokeStaticMethod, "com.tencent.smtt.net.http.DnsManager", "removeAllDns", null, new Object[0]);
            }
            Object objInvokeStaticMethod2 = this.f21396a.invokeStaticMethod("com.tencent.smtt.webkit.SmttPermanentPermissions", "getInstance", null, new Object[0]);
            if (objInvokeStaticMethod2 != null) {
                this.f21396a.invokeMethod(objInvokeStaticMethod2, "com.tencent.smtt.webkit.SmttPermanentPermissions", "clearAllPermanentPermission", null, new Object[0]);
            }
            this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "removeAllIcons", null, new Object[0]);
        }
    }

    public void a(android.webkit.ValueCallback<Map> valueCallback) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageGetOrigins", new Class[]{android.webkit.ValueCallback.class}, valueCallback);
    }

    public void a(String str, long j2) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageSetQuotaForOrigin", new Class[]{String.class, Long.TYPE}, str, Long.valueOf(j2));
    }

    public void a(String str, android.webkit.ValueCallback<Long> valueCallback) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageGetUsageForOrigin", new Class[]{String.class, android.webkit.ValueCallback.class}, str, valueCallback);
    }

    public void a(String str, IconListener iconListener) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "requestIconForPageUrl", new Class[]{String.class, IconListener.class}, str, iconListener);
    }

    public void a(boolean z2) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webview_setWebContentsDebuggingEnabled", new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
    }

    public boolean a() throws Throwable {
        try {
            Method method = this.f21396a.getClassLoader().loadClass("com.tencent.tbs.tbsshell.WebCoreProxy").getMethod("canUseX5", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, new Object[0]);
            if (objInvoke instanceof Boolean) {
                this.f21397b = "normal_" + ((Boolean) objInvoke);
                return ((Boolean) objInvoke).booleanValue();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("end_");
            Boolean bool = (Boolean) objInvoke;
            sb.append(bool);
            sb.append(StrPool.UNDERLINE);
            sb.append("notBoolean");
            this.f21397b = sb.toString();
            return bool.booleanValue();
        } catch (Throwable th) {
            this.f21397b = "Throwable";
            throw th;
        }
    }

    public boolean a(Context context, String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "canOpenFile", new Class[]{Context.class, String.class}, context, str);
        if (objInvokeStaticMethod instanceof Boolean) {
            return ((Boolean) objInvokeStaticMethod).booleanValue();
        }
        return false;
    }

    public boolean a(Map<String, String[]> map) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookies", new Class[]{Map.class}, map);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public byte[] a(byte[] bArr) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilDecode", new Class[]{String.class}, bArr);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (byte[]) objInvokeStaticMethod;
    }

    public Uri[] a(int i2, Intent intent) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "parseFileChooserResult", new Class[]{Integer.TYPE, Intent.class}, Integer.valueOf(i2), intent);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (Uri[]) objInvokeStaticMethod;
    }

    public DexLoader b() {
        return this.f21396a;
    }

    public String b(String str, String str2, String str3) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilGuessFileName", new Class[]{String.class, String.class, String.class}, str, str2, str3);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public void b(android.webkit.ValueCallback<Set<String>> valueCallback) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "geolocationPermissionsGetOrigins", new Class[]{android.webkit.ValueCallback.class}, valueCallback);
    }

    public void b(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "openIconDB", new Class[]{String.class}, str);
    }

    public void b(String str, android.webkit.ValueCallback<Long> valueCallback) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageGetQuotaForOrigin", new Class[]{String.class, android.webkit.ValueCallback.class}, str, valueCallback);
    }

    public boolean b(Context context) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseHasUsernamePassword", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public Object c() {
        return this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cacheDisabled", new Class[0], new Object[0]);
    }

    public void c(Context context) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearUsernamePassword", new Class[]{Context.class}, context);
    }

    public void c(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "retainIconForPageUrl", new Class[]{String.class}, str);
    }

    public void c(String str, android.webkit.ValueCallback<Boolean> valueCallback) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "geolocationPermissionsGetAllowed", new Class[]{String.class, android.webkit.ValueCallback.class}, str, valueCallback);
    }

    public void d(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "releaseIconForPageUrl", new Class[]{String.class}, str);
    }

    public boolean d() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_acceptCookie", new Class[0], new Object[0]);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean d(Context context) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseHasHttpAuthUsernamePassword", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public void e() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeAllCookie", new Class[0], new Object[0]);
    }

    public void e(Context context) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearHttpAuthUsernamePassword", new Class[]{Context.class}, context);
    }

    public void e(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageDeleteOrigin", new Class[]{String.class}, str);
    }

    public String f() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getMiniQBVersion", new Class[0], new Object[0]);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public void f(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "geolocationPermissionsClear", new Class[]{String.class}, str);
    }

    public boolean f(Context context) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseHasFormData", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public Object g() {
        return this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getCachFileBaseDir", new Class[0], new Object[0]);
    }

    public void g(Context context) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webViewDatabaseClearFormData", new Class[]{Context.class}, context);
    }

    public void g(String str) {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "geolocationPermissionsAllow", new Class[]{String.class}, str);
    }

    public IX5DateSorter h(Context context) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createDateSorter", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (IX5DateSorter) objInvokeStaticMethod;
    }

    public String h(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "mimeTypeMapGetFileExtensionFromUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public boolean h() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_hasCookies", new Class[0], new Object[0]);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public IX5WebChromeClient i() {
        Object objInvokeStaticMethod;
        DexLoader dexLoader = this.f21396a;
        if (dexLoader == null || (objInvokeStaticMethod = dexLoader.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createDefaultX5WebChromeClient", new Class[0], new Object[0])) == null) {
            return null;
        }
        return (IX5WebChromeClient) objInvokeStaticMethod;
    }

    public String i(Context context) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getDefaultUserAgent", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod instanceof String) {
            return (String) objInvokeStaticMethod;
        }
        return null;
    }

    public boolean i(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "mimeTypeMapHasMimeType", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public IX5WebViewClient j() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createDefaultX5WebViewClient", new Class[0], new Object[0]);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (IX5WebViewClient) objInvokeStaticMethod;
    }

    public String j(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "mimeTypeMapGetMimeTypeFromExtension", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public IX5WebViewClientExtension k() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createDefaultX5WebChromeClientExtension", new Class[0], new Object[0]);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (IX5WebViewClientExtension) objInvokeStaticMethod;
    }

    public boolean k(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "mimeTypeMapHasExtension", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public String l(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "mimeTypeMapGetMimeTypeFromExtension", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public void l() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "removeAllIcons", null, new Object[0]);
    }

    public String m(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilGuessUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }

    public void m() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "closeIconDB", null, new Object[0]);
    }

    public void n() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "webStorageDeleteAllData", null, new Object[0]);
    }

    public boolean n(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsAssetUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public void o() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "geolocationPermissionsClearAll", null, new Object[0]);
    }

    public boolean o(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsCookielessProxyUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public void p() {
        this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "closeFileReader", new Class[0], new Object[0]);
    }

    public boolean p(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsFileUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public IX5CoreServiceWorkerController q() {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getServiceWorkerController", new Class[0], new Object[0]);
        if (objInvokeStaticMethod instanceof IX5CoreServiceWorkerController) {
            return (IX5CoreServiceWorkerController) objInvokeStaticMethod;
        }
        return null;
    }

    public boolean q(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsAboutUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean r(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsDataUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean s(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsJavaScriptUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean t(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsHttpUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean u(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsHttpsUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean v(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsNetworkUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean w(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsContentUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public boolean x(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilIsValidUrl", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return false;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public String y(String str) {
        Object objInvokeStaticMethod = this.f21396a.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "urlUtilStripAnchor", new Class[]{String.class}, str);
        if (objInvokeStaticMethod == null) {
            return null;
        }
        return (String) objInvokeStaticMethod;
    }
}
