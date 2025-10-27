package com.tencent.smtt.sdk;

/* loaded from: classes6.dex */
public class MimeTypeMap {

    /* renamed from: a, reason: collision with root package name */
    private static MimeTypeMap f20816a;

    private MimeTypeMap() {
    }

    public static String getFileExtensionFromUrl(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.MimeTypeMap.getFileExtensionFromUrl(str) : wVarA.c().h(str);
    }

    public static synchronized MimeTypeMap getSingleton() {
        if (f20816a == null) {
            f20816a = new MimeTypeMap();
        }
        return f20816a;
    }

    public String getExtensionFromMimeType(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str) : wVarA.c().l(str);
    }

    public String getMimeTypeFromExtension(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str) : wVarA.c().j(str);
    }

    public boolean hasExtension(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.MimeTypeMap.getSingleton().hasExtension(str) : wVarA.c().k(str);
    }

    public boolean hasMimeType(String str) {
        w wVarA = w.a();
        return (wVarA == null || !wVarA.b()) ? android.webkit.MimeTypeMap.getSingleton().hasMimeType(str) : wVarA.c().i(str);
    }
}
