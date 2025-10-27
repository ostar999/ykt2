package com.tencent.bugly;

import com.tencent.bugly.proguard.aa;
import java.util.Map;

/* loaded from: classes6.dex */
public class BuglyStrategy {

    /* renamed from: c, reason: collision with root package name */
    private String f17286c;

    /* renamed from: d, reason: collision with root package name */
    private String f17287d;

    /* renamed from: e, reason: collision with root package name */
    private String f17288e;

    /* renamed from: f, reason: collision with root package name */
    private long f17289f;

    /* renamed from: g, reason: collision with root package name */
    private String f17290g;

    /* renamed from: h, reason: collision with root package name */
    private String f17291h;

    /* renamed from: i, reason: collision with root package name */
    private String f17292i;

    /* renamed from: u, reason: collision with root package name */
    private a f17304u;

    /* renamed from: j, reason: collision with root package name */
    private boolean f17293j = true;

    /* renamed from: k, reason: collision with root package name */
    private boolean f17294k = true;

    /* renamed from: l, reason: collision with root package name */
    private boolean f17295l = true;

    /* renamed from: m, reason: collision with root package name */
    private boolean f17296m = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f17297n = true;

    /* renamed from: o, reason: collision with root package name */
    private Class<?> f17298o = null;

    /* renamed from: p, reason: collision with root package name */
    private boolean f17299p = true;

    /* renamed from: q, reason: collision with root package name */
    private boolean f17300q = true;

    /* renamed from: r, reason: collision with root package name */
    private boolean f17301r = true;

    /* renamed from: s, reason: collision with root package name */
    private boolean f17302s = true;

    /* renamed from: t, reason: collision with root package name */
    private boolean f17303t = false;

    /* renamed from: a, reason: collision with root package name */
    protected int f17284a = 31;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f17285b = false;

    /* renamed from: v, reason: collision with root package name */
    private boolean f17305v = false;

    public static class a {
        public static final int CRASHTYPE_ANR = 4;
        public static final int CRASHTYPE_BLOCK = 7;
        public static final int CRASHTYPE_COCOS2DX_JS = 5;
        public static final int CRASHTYPE_COCOS2DX_LUA = 6;
        public static final int CRASHTYPE_JAVA_CATCH = 1;
        public static final int CRASHTYPE_JAVA_CRASH = 0;
        public static final int CRASHTYPE_NATIVE = 2;
        public static final int CRASHTYPE_U3D = 3;
        public static final int MAX_USERDATA_KEY_LENGTH = 100;
        public static final int MAX_USERDATA_VALUE_LENGTH = 100000;

        public synchronized Map<String, String> onCrashHandleStart(int i2, String str, String str2, String str3) {
            return null;
        }

        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int i2, String str, String str2, String str3) {
            return null;
        }
    }

    public synchronized String getAppChannel() {
        String str = this.f17287d;
        if (str != null) {
            return str;
        }
        return aa.b().f17433s;
    }

    public synchronized String getAppPackageName() {
        String str = this.f17288e;
        if (str != null) {
            return str;
        }
        return aa.b().f17417c;
    }

    public synchronized long getAppReportDelay() {
        return this.f17289f;
    }

    public synchronized String getAppVersion() {
        String str = this.f17286c;
        if (str != null) {
            return str;
        }
        return aa.b().f17429o;
    }

    public synchronized int getCallBackType() {
        return this.f17284a;
    }

    public synchronized boolean getCloseErrorCallback() {
        return this.f17285b;
    }

    public synchronized a getCrashHandleCallback() {
        return this.f17304u;
    }

    public synchronized String getDeviceID() {
        return this.f17291h;
    }

    public synchronized String getDeviceModel() {
        return this.f17292i;
    }

    public synchronized String getLibBuglySOFilePath() {
        return this.f17290g;
    }

    public synchronized Class<?> getUserInfoActivity() {
        return this.f17298o;
    }

    public synchronized boolean isBuglyLogUpload() {
        return this.f17299p;
    }

    public synchronized boolean isEnableANRCrashMonitor() {
        return this.f17294k;
    }

    public synchronized boolean isEnableCatchAnrTrace() {
        return this.f17295l;
    }

    public synchronized boolean isEnableNativeCrashMonitor() {
        return this.f17293j;
    }

    public boolean isEnableRecordAnrMainStack() {
        return this.f17296m;
    }

    public synchronized boolean isEnableUserInfo() {
        return this.f17297n;
    }

    public boolean isMerged() {
        return this.f17305v;
    }

    public boolean isReplaceOldChannel() {
        return this.f17300q;
    }

    public synchronized boolean isUploadProcess() {
        return this.f17301r;
    }

    public synchronized boolean isUploadSpotCrash() {
        return this.f17302s;
    }

    public synchronized boolean recordUserInfoOnceADay() {
        return this.f17303t;
    }

    public synchronized BuglyStrategy setAppChannel(String str) {
        this.f17287d = str;
        return this;
    }

    public synchronized BuglyStrategy setAppPackageName(String str) {
        this.f17288e = str;
        return this;
    }

    public synchronized BuglyStrategy setAppReportDelay(long j2) {
        this.f17289f = j2;
        return this;
    }

    public synchronized BuglyStrategy setAppVersion(String str) {
        this.f17286c = str;
        return this;
    }

    public synchronized BuglyStrategy setBuglyLogUpload(boolean z2) {
        this.f17299p = z2;
        return this;
    }

    public synchronized void setCallBackType(int i2) {
        this.f17284a = i2;
    }

    public synchronized void setCloseErrorCallback(boolean z2) {
        this.f17285b = z2;
    }

    public synchronized BuglyStrategy setCrashHandleCallback(a aVar) {
        this.f17304u = aVar;
        return this;
    }

    public synchronized BuglyStrategy setDeviceID(String str) {
        this.f17291h = str;
        return this;
    }

    public synchronized BuglyStrategy setDeviceModel(String str) {
        this.f17292i = str;
        return this;
    }

    public synchronized BuglyStrategy setEnableANRCrashMonitor(boolean z2) {
        this.f17294k = z2;
        return this;
    }

    public void setEnableCatchAnrTrace(boolean z2) {
        this.f17295l = z2;
    }

    public synchronized BuglyStrategy setEnableNativeCrashMonitor(boolean z2) {
        this.f17293j = z2;
        return this;
    }

    public void setEnableRecordAnrMainStack(boolean z2) {
        this.f17296m = z2;
    }

    public synchronized BuglyStrategy setEnableUserInfo(boolean z2) {
        this.f17297n = z2;
        return this;
    }

    public synchronized BuglyStrategy setLibBuglySOFilePath(String str) {
        this.f17290g = str;
        return this;
    }

    @Deprecated
    public void setMerged(boolean z2) {
        this.f17305v = z2;
    }

    public synchronized BuglyStrategy setRecordUserInfoOnceADay(boolean z2) {
        this.f17303t = z2;
        return this;
    }

    public void setReplaceOldChannel(boolean z2) {
        this.f17300q = z2;
    }

    public synchronized BuglyStrategy setUploadProcess(boolean z2) {
        this.f17301r = z2;
        return this;
    }

    public synchronized void setUploadSpotCrash(boolean z2) {
        this.f17302s = z2;
    }

    public synchronized BuglyStrategy setUserInfoActivity(Class<?> cls) {
        this.f17298o = cls;
        return this;
    }
}
