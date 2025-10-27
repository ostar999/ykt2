package com.sina.weibo.sdk.auth;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: f, reason: collision with root package name */
    private Map<String, WbAuthListener> f17174f;

    public static class a {

        /* renamed from: g, reason: collision with root package name */
        private static final b f17175g = new b(0);
    }

    public /* synthetic */ b(byte b3) {
        this();
    }

    public static synchronized b d() {
        return a.f17175g;
    }

    public final synchronized void a(String str, WbAuthListener wbAuthListener) {
        if (!TextUtils.isEmpty(str) && wbAuthListener != null) {
            this.f17174f.put(str, wbAuthListener);
        }
    }

    public final synchronized WbAuthListener b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f17174f.get(str);
    }

    public final synchronized void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f17174f.remove(str);
    }

    private b() {
        this.f17174f = new HashMap();
    }
}
