package com.umeng.socialize.net.dplus.cache;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes6.dex */
public class CacheApi {

    /* renamed from: a, reason: collision with root package name */
    private static String f23762a = "CacheApi";

    /* renamed from: e, reason: collision with root package name */
    private static CacheApi f23763e;

    /* renamed from: b, reason: collision with root package name */
    private Handler f23764b;

    /* renamed from: c, reason: collision with root package name */
    private CacheExector f23765c = new CacheExector(a());

    /* renamed from: d, reason: collision with root package name */
    private Context f23766d;

    private CacheApi(Context context) {
        this.f23766d = context;
    }

    private String a() {
        Context context = this.f23766d;
        if (context == null) {
            return null;
        }
        return context.getFilesDir().getPath();
    }

    public static CacheApi get(Context context) {
        if (f23763e == null) {
            f23763e = new CacheApi(context);
        }
        return f23763e;
    }

    public double checkSize(String str) {
        CacheExector cacheExector = this.f23765c;
        if (cacheExector == null) {
            return 0.0d;
        }
        return cacheExector.checkSize(str);
    }

    public boolean delete(String str) {
        CacheExector cacheExector = this.f23765c;
        if (cacheExector == null) {
            return false;
        }
        return cacheExector.deleteFile(str);
    }

    public IReader read(String str, Class cls) {
        CacheExector cacheExector = this.f23765c;
        if (cacheExector == null) {
            return null;
        }
        return cacheExector.readFile(str, cls);
    }

    public boolean save(String str, String str2) {
        CacheExector cacheExector = this.f23765c;
        if (cacheExector == null) {
            return false;
        }
        return cacheExector.save(str, str2);
    }
}
