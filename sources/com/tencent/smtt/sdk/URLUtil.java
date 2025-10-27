package com.tencent.smtt.sdk;

/* loaded from: classes6.dex */
public final class URLUtil {
    public static String composeSearchUrl(String str, String str2, String str3) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.composeSearchUrl(str, str2, str3) : wVarA.c().a(str, str2, str3);
    }

    public static byte[] decode(byte[] bArr) throws IllegalArgumentException {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.decode(bArr) : wVarA.c().a(bArr);
    }

    public static final String guessFileName(String str, String str2, String str3) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.guessFileName(str, str2, str3) : wVarA.c().b(str, str2, str3);
    }

    public static String guessUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.guessUrl(str) : wVarA.c().m(str);
    }

    public static boolean isAboutUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isAboutUrl(str) : wVarA.c().q(str);
    }

    public static boolean isAssetUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isAssetUrl(str) : wVarA.c().n(str);
    }

    public static boolean isContentUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isContentUrl(str) : wVarA.c().w(str);
    }

    @Deprecated
    public static boolean isCookielessProxyUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isCookielessProxyUrl(str) : wVarA.c().o(str);
    }

    public static boolean isDataUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isDataUrl(str) : wVarA.c().r(str);
    }

    public static boolean isFileUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isFileUrl(str) : wVarA.c().p(str);
    }

    public static boolean isHttpUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isHttpUrl(str) : wVarA.c().t(str);
    }

    public static boolean isHttpsUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isHttpsUrl(str) : wVarA.c().u(str);
    }

    public static boolean isJavaScriptUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isJavaScriptUrl(str) : wVarA.c().s(str);
    }

    public static boolean isNetworkUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isNetworkUrl(str) : wVarA.c().v(str);
    }

    public static boolean isValidUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.isValidUrl(str) : wVarA.c().x(str);
    }

    public static String stripAnchor(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.URLUtil.stripAnchor(str) : wVarA.c().y(str);
    }
}
