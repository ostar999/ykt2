package com.tencent.smtt.sdk;

import android.util.Pair;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.export.external.interfaces.UrlRequest;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes6.dex */
public class UrlRequestBuilderImpl extends UrlRequest.Builder {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21035a = "UrlRequestBuilderImpl";

    /* renamed from: b, reason: collision with root package name */
    private final String f21036b;

    /* renamed from: c, reason: collision with root package name */
    private final UrlRequest.Callback f21037c;

    /* renamed from: d, reason: collision with root package name */
    private final Executor f21038d;

    /* renamed from: e, reason: collision with root package name */
    private String f21039e;

    /* renamed from: g, reason: collision with root package name */
    private boolean f21041g;

    /* renamed from: i, reason: collision with root package name */
    private String f21043i;

    /* renamed from: j, reason: collision with root package name */
    private byte[] f21044j;

    /* renamed from: k, reason: collision with root package name */
    private String f21045k;

    /* renamed from: l, reason: collision with root package name */
    private String f21046l;

    /* renamed from: f, reason: collision with root package name */
    private final ArrayList<Pair<String, String>> f21040f = new ArrayList<>();

    /* renamed from: h, reason: collision with root package name */
    private int f21042h = 3;

    public UrlRequestBuilderImpl(String str, UrlRequest.Callback callback, Executor executor) {
        if (str == null) {
            throw new NullPointerException("URL is required.");
        }
        if (callback == null) {
            throw new NullPointerException("Callback is required.");
        }
        if (executor == null) {
            throw new NullPointerException("Executor is required.");
        }
        this.f21036b = str;
        this.f21037c = callback;
        this.f21038d = executor;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequestBuilderImpl addHeader(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("Invalid header name.");
        }
        if (str2 == null) {
            throw new NullPointerException("Invalid header value.");
        }
        if ("Accept-Encoding".equalsIgnoreCase(str)) {
            return this;
        }
        this.f21040f.add(Pair.create(str, str2));
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequest build() throws NullPointerException {
        int i2;
        w wVarA = w.a();
        if (wVarA == null || !wVarA.b()) {
            return null;
        }
        DexLoader dexLoaderB = wVarA.c().b();
        Class<?> cls = Integer.TYPE;
        Class<?> cls2 = Boolean.TYPE;
        UrlRequest urlRequest = (UrlRequest) dexLoaderB.invokeStaticMethod("com.tencent.smtt.net.X5UrlRequestProvider", "GetX5UrlRequestProvider", new Class[]{String.class, cls, UrlRequest.Callback.class, Executor.class, cls2, String.class, ArrayList.class, String.class, byte[].class, String.class, String.class}, this.f21036b, Integer.valueOf(this.f21042h), this.f21037c, this.f21038d, Boolean.valueOf(this.f21041g), this.f21039e, this.f21040f, this.f21043i, this.f21044j, this.f21045k, this.f21046l);
        if (urlRequest == null) {
            i2 = 7;
            urlRequest = (UrlRequest) dexLoaderB.invokeStaticMethod("com.tencent.smtt.net.X5UrlRequestProvider", "GetX5UrlRequestProvider", new Class[]{String.class, cls, UrlRequest.Callback.class, Executor.class, cls2, String.class, ArrayList.class, String.class}, this.f21036b, Integer.valueOf(this.f21042h), this.f21037c, this.f21038d, Boolean.valueOf(this.f21041g), this.f21039e, this.f21040f, this.f21043i);
        } else {
            i2 = 7;
        }
        if (urlRequest == null) {
            Class<?>[] clsArr = new Class[i2];
            clsArr[0] = String.class;
            clsArr[1] = cls;
            clsArr[2] = UrlRequest.Callback.class;
            clsArr[3] = Executor.class;
            clsArr[4] = cls2;
            clsArr[5] = String.class;
            clsArr[6] = ArrayList.class;
            Object[] objArr = new Object[i2];
            objArr[0] = this.f21036b;
            objArr[1] = Integer.valueOf(this.f21042h);
            objArr[2] = this.f21037c;
            objArr[3] = this.f21038d;
            objArr[4] = Boolean.valueOf(this.f21041g);
            objArr[5] = this.f21039e;
            objArr[6] = this.f21040f;
            urlRequest = (UrlRequest) dexLoaderB.invokeStaticMethod("com.tencent.smtt.net.X5UrlRequestProvider", "GetX5UrlRequestProvider", clsArr, objArr);
        }
        if (urlRequest == null) {
            urlRequest = (UrlRequest) dexLoaderB.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "UrlRequest_getX5UrlRequestProvider", new Class[]{String.class, cls, UrlRequest.Callback.class, Executor.class, cls2, String.class, ArrayList.class, String.class, byte[].class, String.class, String.class}, this.f21036b, Integer.valueOf(this.f21042h), this.f21037c, this.f21038d, Boolean.valueOf(this.f21041g), this.f21039e, this.f21040f, this.f21043i, this.f21044j, this.f21045k, this.f21046l);
        }
        if (urlRequest != null) {
            return urlRequest;
        }
        throw new NullPointerException("UrlRequest build fail");
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequestBuilderImpl disableCache() {
        this.f21041g = true;
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequestBuilderImpl setDns(String str, String str2) {
        if (str == null || str2 == null) {
            throw new NullPointerException("host and address are required.");
        }
        this.f21045k = str;
        this.f21046l = str2;
        try {
            w wVarA = w.a();
            if (wVarA != null && wVarA.b()) {
                wVarA.c().b().invokeStaticMethod("com.tencent.smtt.net.X5UrlRequestProvider", "setDns", new Class[]{String.class, String.class}, this.f21045k, this.f21046l);
            }
        } catch (Exception unused) {
        }
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequest.Builder setHttpMethod(String str) {
        if (str == null) {
            throw new NullPointerException("Method is required.");
        }
        this.f21039e = str;
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequestBuilderImpl setPriority(int i2) {
        this.f21042h = i2;
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequest.Builder setRequestBody(String str) {
        if (str == null) {
            throw new NullPointerException("Body is required.");
        }
        this.f21043i = str;
        return this;
    }

    @Override // com.tencent.smtt.export.external.interfaces.UrlRequest.Builder
    public UrlRequest.Builder setRequestBodyBytes(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("Body is required.");
        }
        this.f21044j = bArr;
        return this;
    }
}
