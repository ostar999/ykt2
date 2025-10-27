package com.tencent.smtt.sdk;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

@Deprecated
/* loaded from: classes6.dex */
public final class CacheManager {
    @Deprecated
    public static boolean cacheDisabled() {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            return ((Boolean) wVarA.c().c()).booleanValue();
        }
        Object objA = com.tencent.smtt.utils.j.a("android.webkit.CacheManager", "cacheDisabled");
        if (objA == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public static InputStream getCacheFile(String str, boolean z2) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return null;
        }
        return wVarA.c().a(str, z2);
    }

    public static Object getCacheFile(String str, Map<String, String> map) {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            return wVarA.c().g();
        }
        try {
            return com.tencent.smtt.utils.j.a(Class.forName("android.webkit.CacheManager"), "getCacheFile", (Class<?>[]) new Class[]{String.class, Map.class}, str, map);
        } catch (Exception unused) {
            return null;
        }
    }

    @Deprecated
    public static File getCacheFileBaseDir() {
        w wVarA = w.a();
        return (File) ((wVarA == null || !wVarA.b()) ? com.tencent.smtt.utils.j.a("android.webkit.CacheManager", "getCacheFileBaseDir") : wVarA.c().g());
    }
}
