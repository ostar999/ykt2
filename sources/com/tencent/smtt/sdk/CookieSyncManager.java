package com.tencent.smtt.sdk;

import android.content.Context;
import java.lang.reflect.Field;

@Deprecated
/* loaded from: classes6.dex */
public class CookieSyncManager {

    /* renamed from: a, reason: collision with root package name */
    private static android.webkit.CookieSyncManager f20792a = null;

    /* renamed from: b, reason: collision with root package name */
    private static CookieSyncManager f20793b = null;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f20794c = false;

    private CookieSyncManager(Context context) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return;
        }
        wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieSyncManager_createInstance", new Class[]{Context.class}, context);
        f20794c = true;
    }

    public static synchronized CookieSyncManager createInstance(Context context) {
        f20792a = android.webkit.CookieSyncManager.createInstance(context);
        if (f20793b == null || !f20794c) {
            f20793b = new CookieSyncManager(context.getApplicationContext());
        }
        return f20793b;
    }

    public static synchronized CookieSyncManager getInstance() {
        CookieSyncManager cookieSyncManager;
        cookieSyncManager = f20793b;
        if (cookieSyncManager == null) {
            throw new IllegalStateException("CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()");
        }
        return cookieSyncManager;
    }

    public void startSync() {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieSyncManager_startSync", new Class[0], new Object[0]);
            return;
        }
        f20792a.startSync();
        try {
            Field declaredField = Class.forName("android.webkit.WebSyncManager").getDeclaredField("mSyncThread");
            declaredField.setAccessible(true);
            ((Thread) declaredField.get(f20792a)).setUncaughtExceptionHandler(new h());
        } catch (Exception unused) {
        }
    }

    public void stopSync() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            f20792a.stopSync();
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieSyncManager_stopSync", new Class[0], new Object[0]);
        }
    }

    public void sync() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            f20792a.sync();
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieSyncManager_Sync", new Class[0], new Object[0]);
        }
    }
}
