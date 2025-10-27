package com.tencent.tbs.one;

import android.content.Context;
import android.os.Bundle;
import com.tencent.tbs.one.impl.a;
import com.tencent.tbs.one.impl.common.f;
import com.tencent.tbs.one.impl.d;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes6.dex */
public abstract class TBSOneManager {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f21683a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public static final Object f21684b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public static TBSOneManager f21685c;

    /* renamed from: d, reason: collision with root package name */
    public static Map<String, TBSOneManager> f21686d;

    public enum Policy {
        AUTO,
        BUILTIN_ONLY,
        BUILTIN_FIRST,
        BUILTIN_ASSETS_ONLY,
        BUILTIN_ASSETS_FIRST,
        LOCAL_ONLY,
        LOCAL_FIRST,
        ONLINE
    }

    public static synchronized TBSOneManager getDefaultInstance(Context context) {
        TBSOneManager tBSOneManager;
        synchronized (f21683a) {
            if (f21685c == null) {
                f21685c = new d(context, ServletHandler.__DEFAULT_SERVLET);
            }
            tBSOneManager = f21685c;
        }
        return tBSOneManager;
    }

    public static int getInstalledComponentVersion(Context context, String str, String str2) {
        if (context == null) {
            return -2;
        }
        return f.a(context, str, str2);
    }

    public static TBSOneManager getInstance(Context context, String str) {
        TBSOneManager dVar;
        if (str.equals(ServletHandler.__DEFAULT_SERVLET)) {
            return getDefaultInstance(context);
        }
        synchronized (f21684b) {
            if (f21686d == null) {
                f21686d = new HashMap();
            }
            dVar = f21686d.get(str);
            if (dVar == null) {
                dVar = new d(context, str);
                f21686d.put(str, dVar);
            }
        }
        return dVar;
    }

    public static void setNeedReportEvent(boolean z2) {
        a.a(z2);
    }

    public abstract void cancelComponent(String str);

    public abstract void configure(String str, Object obj);

    public abstract String getBuildNumber();

    public abstract TBSOneDebugger getDebugger();

    public abstract File getInstalledComponentPath(String str);

    public abstract int[] getInstalledVersionCodesOfComponent(String str);

    public abstract TBSOneComponent getLoadedComponent(String str);

    public abstract TBSOneOnlineService getOnlineService();

    public abstract int getVersionCode();

    public abstract String getVersionName();

    public abstract void installComponent(String str, Bundle bundle, TBSOneCallback<File> tBSOneCallback);

    public abstract void installComponent(String str, TBSOneCallback<File> tBSOneCallback);

    public abstract File installComponentSync(String str, Bundle bundle, long j2);

    public abstract boolean isComponentInstalled(String str);

    public abstract void loadComponentAsync(String str, Bundle bundle, TBSOneCallback<TBSOneComponent> tBSOneCallback);

    public abstract void loadComponentAsync(String str, TBSOneCallback<TBSOneComponent> tBSOneCallback);

    public abstract TBSOneComponent loadComponentSync(String str, long j2);

    public abstract TBSOneComponent loadComponentSync(String str, Bundle bundle, long j2);

    public abstract void setAutoUpdateEnabled(boolean z2);

    public abstract void setDelegate(TBSOneDelegate tBSOneDelegate);

    public abstract void setPolicy(Policy policy);
}
