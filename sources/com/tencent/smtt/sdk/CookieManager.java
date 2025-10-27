package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import io.socket.engineio.client.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class CookieManager {
    public static String LOGTAG = "CookieManager";

    /* renamed from: c, reason: collision with root package name */
    private static CookieManager f20779c;

    /* renamed from: a, reason: collision with root package name */
    CopyOnWriteArrayList<b> f20780a;

    /* renamed from: b, reason: collision with root package name */
    a f20781b = a.MODE_NONE;

    /* renamed from: d, reason: collision with root package name */
    private boolean f20782d = false;

    public enum a {
        MODE_NONE,
        MODE_KEYS,
        MODE_ALL
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        int f20787a;

        /* renamed from: b, reason: collision with root package name */
        String f20788b;

        /* renamed from: c, reason: collision with root package name */
        String f20789c;

        /* renamed from: d, reason: collision with root package name */
        ValueCallback<Boolean> f20790d;

        public b() {
        }
    }

    private CookieManager() {
    }

    public static CookieManager getInstance() {
        if (f20779c == null) {
            synchronized (CookieManager.class) {
                if (f20779c == null) {
                    f20779c = new CookieManager();
                }
            }
        }
        return f20779c;
    }

    public static int getROMCookieDBVersion(Context context) {
        return context.getSharedPreferences("cookiedb_info", 4).getInt("db_version", -1);
    }

    public static void setROMCookieDBVersion(Context context, int i2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("cookiedb_info", 4).edit();
        editorEdit.putInt("db_version", i2);
        editorEdit.commit();
    }

    public synchronized void a() {
        CopyOnWriteArrayList<b> copyOnWriteArrayList = this.f20780a;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() != 0) {
            w wVarA = w.a();
            if (wVarA == null || !wVarA.b()) {
                Iterator<b> it = this.f20780a.iterator();
                while (it.hasNext()) {
                    b next = it.next();
                    int i2 = next.f20787a;
                    if (i2 == 1) {
                        com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "setCookie", (Class<?>[]) new Class[]{String.class, String.class, android.webkit.ValueCallback.class}, next.f20788b, next.f20789c, next.f20790d);
                    } else if (i2 == 2) {
                        android.webkit.CookieManager.getInstance().setCookie(next.f20788b, next.f20789c);
                    }
                }
            } else {
                Iterator<b> it2 = this.f20780a.iterator();
                while (it2.hasNext()) {
                    b next2 = it2.next();
                    int i3 = next2.f20787a;
                    if (i3 == 1) {
                        setCookie(next2.f20788b, next2.f20789c, next2.f20790d);
                    } else if (i3 == 2) {
                        setCookie(next2.f20788b, next2.f20789c);
                    }
                }
            }
            this.f20780a.clear();
        }
    }

    public boolean acceptCookie() {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.CookieManager.getInstance().acceptCookie() : wVarA.c().d();
    }

    public synchronized boolean acceptThirdPartyCookies(WebView webView) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            Object objA = com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "acceptThirdPartyCookies", (Class<?>[]) new Class[]{android.webkit.WebView.class}, webView.getView());
            if (objA == null) {
                return false;
            }
            return ((Boolean) objA).booleanValue();
        }
        Object objInvokeStaticMethod = wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_acceptThirdPartyCookies", new Class[]{Object.class}, webView.getView());
        if (objInvokeStaticMethod == null) {
            return true;
        }
        return ((Boolean) objInvokeStaticMethod).booleanValue();
    }

    public void flush() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), Socket.EVENT_FLUSH, (Class<?>[]) new Class[0], new Object[0]);
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_flush", new Class[0], new Object[0]);
        }
    }

    public String getCookie(String str) {
        w wVarA = w.a();
        if (wVarA != null && wVarA.b()) {
            return wVarA.c().a(str);
        }
        try {
            return android.webkit.CookieManager.getInstance().getCookie(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public boolean hasCookies() {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.CookieManager.getInstance().hasCookies() : wVarA.c().h();
    }

    @Deprecated
    public void removeAllCookie() {
        CopyOnWriteArrayList<b> copyOnWriteArrayList = this.f20780a;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.clear();
        }
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.CookieManager.getInstance().removeAllCookie();
        } else {
            wVarA.c().e();
        }
    }

    public void removeAllCookies(ValueCallback<Boolean> valueCallback) {
        CopyOnWriteArrayList<b> copyOnWriteArrayList = this.f20780a;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.clear();
        }
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "removeAllCookies", (Class<?>[]) new Class[]{android.webkit.ValueCallback.class}, valueCallback);
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeAllCookies", new Class[]{android.webkit.ValueCallback.class}, valueCallback);
        }
    }

    @Deprecated
    public void removeExpiredCookie() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.CookieManager.getInstance().removeExpiredCookie();
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeExpiredCookie", new Class[0], new Object[0]);
        }
    }

    @Deprecated
    public void removeSessionCookie() {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            android.webkit.CookieManager.getInstance().removeSessionCookie();
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookie", new Class[0], new Object[0]);
        }
    }

    public void removeSessionCookies(ValueCallback<Boolean> valueCallback) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "removeSessionCookies", (Class<?>[]) new Class[]{android.webkit.ValueCallback.class}, valueCallback);
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookies", new Class[]{android.webkit.ValueCallback.class}, valueCallback);
        }
    }

    public synchronized void setAcceptCookie(boolean z2) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            try {
                android.webkit.CookieManager.getInstance().setAcceptCookie(z2);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptCookie", new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public synchronized void setAcceptThirdPartyCookies(WebView webView, boolean z2) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "setAcceptThirdPartyCookies", (Class<?>[]) new Class[]{android.webkit.WebView.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z2));
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptThirdPartyCookies", new Class[]{Object.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z2));
        }
    }

    public synchronized void setCookie(String str, String str2) {
        setCookie(str, str2, false);
    }

    public synchronized void setCookie(String str, String str2, ValueCallback<Boolean> valueCallback) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            if (!wVarA.d()) {
                b bVar = new b();
                bVar.f20787a = 1;
                bVar.f20788b = str;
                bVar.f20789c = str2;
                bVar.f20790d = valueCallback;
                if (this.f20780a == null) {
                    this.f20780a = new CopyOnWriteArrayList<>();
                }
                this.f20780a.add(bVar);
            }
            com.tencent.smtt.utils.j.a(android.webkit.CookieManager.getInstance(), "setCookie", (Class<?>[]) new Class[]{String.class, String.class, android.webkit.ValueCallback.class}, str, str2, valueCallback);
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class, android.webkit.ValueCallback.class}, str, str2, valueCallback);
        }
    }

    public synchronized void setCookie(String str, String str2, boolean z2) {
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            if (!w.a().d()) {
                b bVar = new b();
                bVar.f20787a = 2;
                bVar.f20788b = str;
                bVar.f20789c = str2;
                bVar.f20790d = null;
                if (this.f20780a == null) {
                    this.f20780a = new CopyOnWriteArrayList<>();
                }
                this.f20780a.add(bVar);
            }
            android.webkit.CookieManager.getInstance().setCookie(str, str2);
        } else {
            wVarA.c().b().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class}, str, str2);
        }
    }

    public void setCookies(Map<String, String[]> map) {
        w wVarA = w.a();
        if ((wVarA == null || !wVarA.b()) ? false : wVarA.c().a(map)) {
            return;
        }
        for (String str : map.keySet()) {
            for (String str2 : map.get(str)) {
                setCookie(str, str2);
            }
        }
    }
}
