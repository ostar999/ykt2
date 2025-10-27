package com.tencent.bugly.crashreport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.h5.H5JavaScriptInterface;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.aq;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.bc;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.s;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.yikaobang.yixue.R2;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
public class CrashReport {

    /* renamed from: a, reason: collision with root package name */
    private static Context f17311a;

    public static class CrashHandleCallback extends BuglyStrategy.a {
    }

    public static class UserStrategy extends BuglyStrategy {

        /* renamed from: c, reason: collision with root package name */
        CrashHandleCallback f17313c;

        public UserStrategy(Context context) {
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized int getCallBackType() {
            return this.f17284a;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized boolean getCloseErrorCallback() {
            return this.f17285b;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized void setCallBackType(int i2) {
            this.f17284a = i2;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized void setCloseErrorCallback(boolean z2) {
            this.f17285b = z2;
        }

        public synchronized void setCrashHandleCallback(CrashHandleCallback crashHandleCallback) {
            this.f17313c = crashHandleCallback;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized CrashHandleCallback getCrashHandleCallback() {
            return this.f17313c;
        }
    }

    public interface a {
        String a();

        void a(H5JavaScriptInterface h5JavaScriptInterface, String str);

        void a(String str);

        void b();

        CharSequence c();
    }

    public static void closeBugly() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not close bugly because bugly is disable.");
            return;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return;
        }
        if (f17311a == null) {
            return;
        }
        aq aqVarA = aq.a();
        if (aqVarA != null) {
            aqVarA.b(f17311a);
        }
        closeCrashReport();
        s.a(f17311a);
        ak akVarA = ak.a();
        if (akVarA != null) {
            akVarA.b();
        }
    }

    public static void closeCrashReport() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not close crash report because bugly is disable.");
        } else if (CrashModule.getInstance().hasInitialized()) {
            at.a().c();
        } else {
            Log.w(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        }
    }

    public static void closeNativeReport() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not close native report because bugly is disable.");
        } else if (CrashModule.getInstance().hasInitialized()) {
            at.a().d();
        } else {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        }
    }

    public static void enableBugly(boolean z2) {
        p.f17849a = z2;
    }

    public static void enableObtainId(Context context, boolean z2) {
        setCollectPrivacyInfo(context, z2);
    }

    public static Set<String> getAllUserDataKeys(Context context) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get all keys of user data because bugly is disable.");
            return new HashSet();
        }
        if (context != null) {
            return aa.a(context).w();
        }
        Log.e(al.f17513b, "getAllUserDataKeys args context should not be null");
        return new HashSet();
    }

    public static String getAppChannel() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get App channel because bugly is disable.");
            return "unknown";
        }
        if (CrashModule.getInstance().hasInitialized()) {
            return aa.a(f17311a).f17433s;
        }
        Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        return "unknown";
    }

    public static String getAppID() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get App ID because bugly is disable.");
            return "unknown";
        }
        if (CrashModule.getInstance().hasInitialized()) {
            return aa.a(f17311a).e();
        }
        Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        return "unknown";
    }

    public static String getAppVer() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get app version because bugly is disable.");
            return "unknown";
        }
        if (CrashModule.getInstance().hasInitialized()) {
            return aa.a(f17311a).f17429o;
        }
        Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        return "unknown";
    }

    public static String getBuglyVersion(Context context) {
        if (context != null) {
            return aa.a(context).f17422h;
        }
        al.d("Please call with context.", new Object[0]);
        return "unknown";
    }

    public static Context getContext() {
        return f17311a;
    }

    public static String getDeviceID(Context context) {
        return aa.a(context).g();
    }

    public static Proxy getHttpProxy() {
        return an.f17517a;
    }

    public static Map<String, String> getSdkExtraData() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        }
        if (CrashModule.getInstance().hasInitialized()) {
            return aa.a(f17311a).K;
        }
        Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        return null;
    }

    public static String getUserData(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get user data because bugly is disable.");
            return "unknown";
        }
        if (context == null) {
            Log.e(al.f17513b, "getUserDataValue args context should not be null");
            return "unknown";
        }
        if (ap.b(str)) {
            return null;
        }
        return aa.a(context).g(str);
    }

    public static int getUserDatasSize(Context context) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get size of user data because bugly is disable.");
            return -1;
        }
        if (context != null) {
            return aa.a(context).v();
        }
        Log.e(al.f17513b, "getUserDatasSize args context should not be null");
        return -1;
    }

    public static String getUserId() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get user ID because bugly is disable.");
            return "unknown";
        }
        if (CrashModule.getInstance().hasInitialized()) {
            return aa.a(f17311a).f();
        }
        Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        return "unknown";
    }

    public static int getUserSceneTagId(Context context) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get user scene tag because bugly is disable.");
            return -1;
        }
        if (context != null) {
            return aa.a(context).z();
        }
        Log.e(al.f17513b, "getUserSceneTagId args context should not be null");
        return -1;
    }

    public static void initCrashReport(Context context) {
        if (context == null) {
            return;
        }
        f17311a = context;
        p.a(CrashModule.getInstance());
        p.a(context);
    }

    public static boolean isLastSessionCrash() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "The info 'isLastSessionCrash' is not accurate because bugly is disable.");
            return false;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return false;
        }
        at atVarA = at.a();
        Boolean bool = atVarA.A;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = aa.b().f17418d;
        List<y> listA = w.a().a(1);
        ArrayList arrayList = new ArrayList();
        if (listA == null || listA.size() <= 0) {
            atVarA.A = Boolean.FALSE;
            return false;
        }
        for (y yVar : listA) {
            if (str.equals(yVar.f17929c)) {
                atVarA.A = Boolean.TRUE;
                arrayList.add(yVar);
            }
        }
        if (arrayList.size() > 0) {
            w.a().a(arrayList);
        }
        return true;
    }

    public static void postCatchedException(Throwable th) {
        postCatchedException(th, Thread.currentThread());
    }

    public static void postException(Thread thread, int i2, String str, String str2, String str3, Map<String, String> map) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not post crash caught because bugly is disable.");
        } else if (CrashModule.getInstance().hasInitialized()) {
            au.a(thread, i2, str, str2, str3, map);
        } else {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        }
    }

    private static void putSdkData(Context context, String str, String str2) {
        if (context == null || ap.b(str) || ap.b(str2)) {
            return;
        }
        String strReplace = str.replace("[a-zA-Z[0-9]]+", "");
        if (strReplace.length() > 100) {
            Log.w(al.f17513b, String.format("putSdkData key length over limit %d, will be cutted.", 50));
            strReplace = strReplace.substring(0, 50);
        }
        if (str2.length() > 500) {
            Log.w(al.f17513b, String.format("putSdkData value length over limit %d, will be cutted!", 200));
            str2 = str2.substring(0, 200);
        }
        aa.a(context).b(strReplace, str2);
        al.b(String.format("[param] putSdkData data: %s - %s", strReplace, str2), new Object[0]);
    }

    public static void putUserData(Context context, String str, String str2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not put user data because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(al.f17513b, "putUserData args context should not be null");
            return;
        }
        if (str == null) {
            al.d("putUserData args key should not be null or empty", new Object[0]);
            return;
        }
        if (str2 == null) {
            al.d("putUserData args value should not be null", new Object[0]);
            return;
        }
        if (str2.length() > 200) {
            al.d("user data value length over limit %d, it will be cutted!", 200);
            str2 = str2.substring(0, 200);
        }
        aa aaVarA = aa.a(context);
        if (aaVarA.w().contains(str)) {
            NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
            if (nativeCrashHandler != null) {
                nativeCrashHandler.putKeyValueToNative(str, str2);
            }
            aa.a(context).a(str, str2);
            al.c("replace KV %s %s", str, str2);
            return;
        }
        if (aaVarA.v() >= 50) {
            al.d("user data size is over limit %d, it will be cutted!", 50);
            return;
        }
        if (str.length() > 50) {
            al.d("user data key length over limit %d , will drop this new key %s", 50, str);
            str = str.substring(0, 50);
        }
        NativeCrashHandler nativeCrashHandler2 = NativeCrashHandler.getInstance();
        if (nativeCrashHandler2 != null) {
            nativeCrashHandler2.putKeyValueToNative(str, str2);
        }
        aa.a(context).a(str, str2);
        al.b("[param] set user data: %s - %s", str, str2);
    }

    public static String removeUserData(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not remove user data because bugly is disable.");
            return "unknown";
        }
        if (context == null) {
            Log.e(al.f17513b, "removeUserData args context should not be null");
            return "unknown";
        }
        if (ap.b(str)) {
            return null;
        }
        al.b("[param] remove user data: %s", str);
        return aa.a(context).f(str);
    }

    public static void setAllThreadStackEnable(Context context, boolean z2, boolean z3) {
        aa aaVarA = aa.a(context);
        aaVarA.Q = z2;
        aaVarA.R = z3;
    }

    public static void setAppChannel(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App channel because Bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(al.f17513b, "setAppChannel args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(al.f17513b, "App channel is null, will not set");
            return;
        }
        aa.a(context).f17433s = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppChannel(str);
        }
    }

    public static void setAppPackage(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App package because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(al.f17513b, "setAppPackage args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(al.f17513b, "App package is null, will not set");
            return;
        }
        aa.a(context).f17417c = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppPackage(str);
        }
    }

    public static void setAppVersion(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App version because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(al.f17513b, "setAppVersion args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(al.f17513b, "App version is null, will not set");
            return;
        }
        aa.a(context).f17429o = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppVersion(str);
        }
    }

    public static void setBuglyDbName(String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set DB name because bugly is disable.");
        } else {
            Log.i(al.f17513b, "Set Bugly DB name: ".concat(String.valueOf(str)));
            x.f17923a = str;
        }
    }

    public static void setCollectPrivacyInfo(Context context, boolean z2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set collect privacy info enable because bugly is disable.");
        } else if (context == null) {
            Log.w(al.f17513b, "setCollectPrivacyInfo args context should not be null");
        } else {
            Log.i(al.f17513b, "setCollectPrivacyInfo: ".concat(String.valueOf(z2)));
            aa.a(context).f17428n = z2;
        }
    }

    public static void setContext(Context context) {
        f17311a = context;
    }

    public static void setCrashFilter(String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App package because bugly is disable.");
        } else {
            Log.i(al.f17513b, "Set crash stack filter: ".concat(String.valueOf(str)));
            at.f17592q = str;
        }
    }

    public static void setCrashRegularFilter(String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App package because bugly is disable.");
        } else {
            Log.i(al.f17513b, "Set crash stack filter: ".concat(String.valueOf(str)));
            at.f17593r = str;
        }
    }

    public static void setDeviceId(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        aa.a(context).a(str);
    }

    public static void setDeviceModel(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        aa.a(context).b(str);
    }

    public static void setDumpFilePath(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App version because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(al.f17513b, "setTombPath args context should not be null");
        } else if (str == null) {
            Log.w(al.f17513b, "tombstone path is null, will not set");
        } else {
            Log.i(al.f17513b, "user set tombstone path: ".concat(str));
            NativeCrashHandler.setDumpFilePath(str);
        }
    }

    public static void setHandleNativeCrashInJava(boolean z2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set App package because bugly is disable.");
        } else {
            Log.i(al.f17513b, "Should handle native crash in Java profile after handled in native profile: ".concat(String.valueOf(z2)));
            NativeCrashHandler.setShouldHandleInJava(z2);
        }
    }

    public static void setHttpProxy(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            an.f17517a = null;
        } else {
            an.f17517a = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i2));
        }
    }

    @Deprecated
    public static void setIsAppForeground(Context context, boolean z2) {
        al.a("App fore and back status are no longer supported", new Object[0]);
    }

    public static void setIsDevelopmentDevice(Context context, boolean z2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set 'isDevelopmentDevice' because bugly is disable.");
            return;
        }
        if (context == null) {
            al.d("Context should not be null.", new Object[0]);
            return;
        }
        if (z2) {
            al.c("This is a development device.", new Object[0]);
        } else {
            al.c("This is not a development device.", new Object[0]);
        }
        aa.a(context).I = z2;
    }

    public static boolean setJavascriptMonitor(WebView webView, boolean z2) {
        return setJavascriptMonitor(webView, z2, false);
    }

    public static void setSdkExtraData(Context context, String str, String str2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not put SDK extra data because bugly is disable.");
            return;
        }
        if (context == null || ap.b(str) || ap.b(str2)) {
            return;
        }
        aa aaVarA = aa.a(context);
        if (str == null || str2 == null) {
            return;
        }
        synchronized (aaVarA.T) {
            aaVarA.K.put(str, str2);
        }
    }

    public static void setServerUrl(String str) {
        if (ap.b(str) || !ap.d(str)) {
            Log.i(al.f17513b, "URL is invalid.");
            return;
        }
        ac.a(str);
        StrategyBean.f17336a = str;
        StrategyBean.f17337b = str;
    }

    public static void setSessionIntervalMills(long j2) {
        if (p.f17849a) {
            s.a(j2);
        } else {
            Log.w(al.f17513b, "Can not set 'SessionIntervalMills' because bugly is disable.");
        }
    }

    public static void setUserId(String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set user ID because bugly is disable.");
        } else if (CrashModule.getInstance().hasInitialized()) {
            setUserId(f17311a, str);
        } else {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        }
    }

    public static void setUserSceneTag(Context context, int i2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set tag caught because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.e(al.f17513b, "setTag args context should not be null");
            return;
        }
        if (i2 <= 0) {
            al.d("setTag args tagId should > 0", new Object[0]);
        }
        aa aaVarA = aa.a(context);
        synchronized (aaVarA.U) {
            int i3 = aaVarA.f17437w;
            if (i3 != i2) {
                aaVarA.f17437w = i2;
                al.a("user scene tag %d changed to tag %d", Integer.valueOf(i3), Integer.valueOf(aaVarA.f17437w));
            }
        }
        al.b("[param] set user scene tag: %d", Integer.valueOf(i2));
    }

    public static void startCrashReport() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not start crash report because bugly is disable.");
        } else if (CrashModule.getInstance().hasInitialized()) {
            at.a().b();
        } else {
            Log.w(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        }
    }

    public static void testANRCrash() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not test ANR crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            al.a("start to create a anr crash for test!", new Object[0]);
            at.a().h();
        }
    }

    public static void testJavaCrash() {
        int i2;
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not test Java crash because bugly is disable.");
            return;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return;
        }
        aa aaVarB = aa.b();
        if (aaVarB != null && (i2 = aaVarB.f17438x) != 24096) {
            aaVarB.f17438x = R2.string.login_tip1;
            al.a("server scene tag %d changed to tag %d", Integer.valueOf(i2), Integer.valueOf(aaVarB.f17438x));
        }
        throw new RuntimeException("This Crash create for Test! You can go to Bugly see more detail!");
    }

    public static void testNativeCrash() {
        testNativeCrash(true, true, false);
    }

    public static void uploadUserInfo() {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not upload user info because bugly is disable.");
            return;
        }
        r rVar = s.f17870b;
        if (rVar == null) {
            Log.w(al.f17513b, "Can not upload user info because bugly is not init.");
        } else {
            rVar.b();
        }
    }

    public static void postCatchedException(Throwable th, Thread thread) {
        postCatchedException(th, thread, false);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static boolean setJavascriptMonitor(final WebView webView, boolean z2, boolean z3) {
        if (webView == null) {
            Log.w(al.f17513b, "WebView is null.");
            return false;
        }
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setAllowFileAccess(false);
        return setJavascriptMonitor(new a() { // from class: com.tencent.bugly.crashreport.CrashReport.1
            @Override // com.tencent.bugly.crashreport.CrashReport.a
            public final String a() {
                return webView.getUrl();
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.a
            public final void b() {
                WebSettings settings = webView.getSettings();
                if (settings.getJavaScriptEnabled()) {
                    return;
                }
                settings.setJavaScriptEnabled(true);
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.a
            public final CharSequence c() {
                return webView.getContentDescription();
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.a
            public final void a(String str) {
                webView.loadUrl(str);
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.a
            public final void a(H5JavaScriptInterface h5JavaScriptInterface, String str) {
                webView.addJavascriptInterface(h5JavaScriptInterface, str);
            }
        }, z2, z3);
    }

    public static void testNativeCrash(boolean z2, boolean z3, boolean z4) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not test native crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            al.a("start to create a native crash for test!", new Object[0]);
            at.a().a(z2, z3, z4);
        }
    }

    public static void postCatchedException(final Throwable th, final Thread thread, final boolean z2) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not post crash caught because bugly is disable.");
            return;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(al.f17513b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return;
        }
        if (th == null) {
            al.d("throwable is null, just return", new Object[0]);
            return;
        }
        if (thread == null) {
            thread = Thread.currentThread();
        }
        final at atVarA = at.a();
        atVarA.f17599w.a(new Runnable() { // from class: com.tencent.bugly.proguard.at.3

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ boolean f17605a = false;

            /* renamed from: d, reason: collision with root package name */
            final /* synthetic */ String f17608d = null;

            /* renamed from: e, reason: collision with root package name */
            final /* synthetic */ byte[] f17609e = null;

            /* renamed from: f, reason: collision with root package name */
            final /* synthetic */ boolean f17610f = true;

            @Override // java.lang.Runnable
            public final void run() {
                try {
                    al.c("post a throwable %b", Boolean.valueOf(this.f17605a));
                    at.this.f17596t.a(thread, th, false, this.f17608d, this.f17609e, this.f17610f);
                    if (z2) {
                        al.a("clear user datas", new Object[0]);
                        aa.a(at.this.f17594c).u();
                    }
                } catch (Throwable th2) {
                    if (!al.b(th2)) {
                        th2.printStackTrace();
                    }
                    al.e("java catch error: %s", th.toString());
                }
            }
        });
    }

    public static void initCrashReport(Context context, UserStrategy userStrategy) {
        if (context == null) {
            return;
        }
        f17311a = context;
        p.a(CrashModule.getInstance());
        p.a(context, userStrategy);
    }

    public static void setHttpProxy(InetAddress inetAddress, int i2) {
        if (inetAddress == null) {
            an.f17517a = null;
        } else {
            an.f17517a = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i2));
        }
    }

    public static void postException(int i2, String str, String str2, String str3, Map<String, String> map) {
        postException(Thread.currentThread(), i2, str, str2, str3, map);
    }

    public static void setUserId(Context context, String str) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set user ID because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.e(al.f17513b, "Context should not be null when bugly has not been initialed!");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            al.d("userId should not be null", new Object[0]);
            return;
        }
        if (str.length() > 100) {
            String strSubstring = str.substring(0, 100);
            al.d("userId %s length is over limit %d substring to %s", str, 100, strSubstring);
            str = strSubstring;
        }
        if (str.equals(aa.a(context).f())) {
            return;
        }
        aa aaVarA = aa.a(context);
        synchronized (aaVarA.V) {
            aaVarA.f17426l = str;
        }
        al.b("[user] set userId : %s", str);
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeUserId(str);
        }
        if (CrashModule.getInstance().hasInitialized()) {
            s.a();
        }
    }

    public static Map<String, String> getSdkExtraData(Context context) {
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        }
        if (context == null) {
            al.d("Context should not be null.", new Object[0]);
            return null;
        }
        return aa.a(context).K;
    }

    public static void initCrashReport(Context context, String str, boolean z2) {
        initCrashReport(context, str, z2, null);
    }

    public static boolean setJavascriptMonitor(a aVar, boolean z2) {
        return setJavascriptMonitor(aVar, z2, false);
    }

    public static void initCrashReport(Context context, String str, boolean z2, UserStrategy userStrategy) {
        if (context == null) {
            return;
        }
        f17311a = context;
        p.a(CrashModule.getInstance());
        p.a(context, str, z2, userStrategy);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static boolean setJavascriptMonitor(a aVar, boolean z2, boolean z3) {
        if (aVar == null) {
            Log.w(al.f17513b, "WebViewInterface is null.");
            return false;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            al.e("CrashReport has not been initialed! please to call method 'initCrashReport' first!", new Object[0]);
            return false;
        }
        al.a("Set Javascript exception monitor of webview.", new Object[0]);
        if (!p.f17849a) {
            Log.w(al.f17513b, "Can not set JavaScript monitor because bugly is disable.");
            return false;
        }
        al.c("URL of webview is %s", aVar.a());
        al.a("Enable the javascript needed by webview monitor.", new Object[0]);
        aVar.b();
        H5JavaScriptInterface h5JavaScriptInterface = H5JavaScriptInterface.getInstance(aVar);
        if (h5JavaScriptInterface != null) {
            al.a("Add a secure javascript interface to the webview.", new Object[0]);
            aVar.a(h5JavaScriptInterface, "exceptionUploader");
        }
        if (z2) {
            al.a("Inject bugly.js(v%s) to the webview.", bc.b());
            String strA = bc.a();
            if (strA == null) {
                al.e("Failed to inject Bugly.js.", bc.b());
                return false;
            }
            aVar.a(BridgeUtil.JAVASCRIPT_STR.concat(strA));
        }
        return true;
    }
}
