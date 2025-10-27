package com.tencent.open.a;

import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.g;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f20502a;

    /* renamed from: b, reason: collision with root package name */
    private OkHttpClient f20503b;

    /* renamed from: c, reason: collision with root package name */
    private g f20504c;

    /* renamed from: com.tencent.open.a.a$a, reason: collision with other inner class name */
    public static class C0348a implements Interceptor {

        /* renamed from: a, reason: collision with root package name */
        private final String f20508a;

        public C0348a(String str) {
            this.f20508a = str;
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder().header("User-Agent", this.f20508a).build());
        }
    }

    public a() {
        b();
    }

    public static a a() {
        if (f20502a == null) {
            synchronized (a.class) {
                if (f20502a == null) {
                    f20502a = new a();
                }
            }
        }
        f20502a.c();
        return f20502a;
    }

    private void a(OkHttpClient.Builder builder) {
    }

    private void b() {
        C0348a c0348a = new C0348a("AndroidSDK_" + Build.VERSION.SDK + StrPool.UNDERLINE + Build.DEVICE + StrPool.UNDERLINE + Build.VERSION.RELEASE);
        OkHttpClient.Builder builderConnectionSpecs = new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS));
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        OkHttpClient.Builder builderAddInterceptor = builderConnectionSpecs.connectTimeout(C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, timeUnit).readTimeout(30000L, timeUnit).writeTimeout(30000L, timeUnit).cache(null).addInterceptor(c0348a);
        a(builderAddInterceptor);
        this.f20503b = builderAddInterceptor.build();
    }

    private void c() {
        g gVar = this.f20504c;
        if (gVar == null) {
            return;
        }
        int iA = gVar.a("Common_HttpConnectionTimeout");
        if (iA == 0) {
            iA = 15000;
        }
        int iA2 = this.f20504c.a("Common_SocketConnectionTimeout");
        if (iA2 == 0) {
            iA2 = 30000;
        }
        a(iA, iA2);
    }

    public void a(g gVar) {
        this.f20504c = gVar;
        c();
    }

    public void a(long j2, long j3) {
        if (this.f20503b.connectTimeoutMillis() == j2 && this.f20503b.readTimeoutMillis() == j3) {
            return;
        }
        SLog.i("openSDK_LOG.OpenHttpService", "setTimeout changed.");
        OkHttpClient.Builder builderNewBuilder = this.f20503b.newBuilder();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.f20503b = builderNewBuilder.connectTimeout(j2, timeUnit).readTimeout(j3, timeUnit).writeTimeout(j3, timeUnit).build();
    }

    public b b(String str, Map<String, String> map) throws IOException {
        SLog.i("openSDK_LOG.OpenHttpService", "post data");
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null && map.size() > 0) {
            for (String str2 : map.keySet()) {
                String str3 = map.get(str2);
                if (str3 != null) {
                    builder.add(str2, str3);
                }
            }
        }
        FormBody formBodyBuild = builder.build();
        return new b(this.f20503b.newCall(new Request.Builder().url(str).post(formBodyBuild).build()).execute(), (int) formBodyBuild.contentLength());
    }

    public b a(String str, Map<String, String> map) throws IOException {
        if (map != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder("");
            for (String str2 : map.keySet()) {
                String str3 = map.get(str2);
                if (str3 != null) {
                    sb.append(URLEncoder.encode(str2, "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(str3, "UTF-8"));
                    sb.append("&");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return a(str, sb.toString());
        }
        return a(str, "");
    }

    public b a(String str, String str2) throws IOException {
        SLog.i("openSDK_LOG.OpenHttpService", "get.");
        if (!TextUtils.isEmpty(str2)) {
            int iIndexOf = str2.indexOf("?");
            if (iIndexOf == -1) {
                str = str + "?";
            } else if (iIndexOf != str.length() - 1) {
                str = str + "&";
            }
            str = str + str2;
        }
        return new b(this.f20503b.newCall(new Request.Builder().url(str).get().build()).execute(), str2.length());
    }

    public b a(String str, Map<String, String> map, Map<String, byte[]> map2) throws IOException {
        if (map2 != null && map2.size() != 0) {
            SLog.i("openSDK_LOG.OpenHttpService", "post data, has byte data");
            MultipartBody.Builder builder = new MultipartBody.Builder();
            if (map != null && map.size() > 0) {
                for (String str2 : map.keySet()) {
                    String str3 = map.get(str2);
                    if (str3 != null) {
                        builder.addFormDataPart(str2, str3);
                    }
                }
            }
            for (String str4 : map2.keySet()) {
                byte[] bArr = map2.get(str4);
                if (bArr != null && bArr.length > 0) {
                    builder.addFormDataPart(str4, str4, RequestBody.create(MediaType.get("content/unknown"), bArr));
                    SLog.w("openSDK_LOG.OpenHttpService", "post byte data.");
                }
            }
            MultipartBody multipartBodyBuild = builder.build();
            return new b(this.f20503b.newCall(new Request.Builder().url(str).post(multipartBodyBuild).build()).execute(), (int) multipartBodyBuild.contentLength());
        }
        return b(str, map);
    }
}
