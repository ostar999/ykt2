package com.tencent.connect.auth;

import com.tencent.tauth.IUiListener;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static b f18018a = null;

    /* renamed from: d, reason: collision with root package name */
    static final /* synthetic */ boolean f18019d = true;

    /* renamed from: e, reason: collision with root package name */
    private static int f18020e;

    /* renamed from: b, reason: collision with root package name */
    public HashMap<String, a> f18021b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    public final String f18022c = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public IUiListener f18023a;

        /* renamed from: b, reason: collision with root package name */
        public com.tencent.connect.auth.a f18024b;

        /* renamed from: c, reason: collision with root package name */
        public String f18025c;
    }

    public static b a() {
        if (f18018a == null) {
            f18018a = new b();
        }
        return f18018a;
    }

    public static int b() {
        int i2 = f18020e + 1;
        f18020e = i2;
        return i2;
    }

    public String c() {
        int iCeil = (int) Math.ceil((Math.random() * 20.0d) + 3.0d);
        char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = charArray.length;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < iCeil; i2++) {
            stringBuffer.append(charArray[(int) (Math.random() * length)]);
        }
        return stringBuffer.toString();
    }

    public String a(a aVar) {
        int iB = b();
        try {
            this.f18021b.put("" + iB, aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return "" + iB;
    }
}
