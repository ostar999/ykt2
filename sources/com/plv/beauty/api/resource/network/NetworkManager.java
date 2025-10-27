package com.plv.beauty.api.resource.network;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class NetworkManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient mClient;

    /* renamed from: com.plv.beauty.api.resource.network.NetworkManager$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$ContentType;
        static final /* synthetic */ int[] $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$Method;

        static {
            int[] iArr = new int[ContentType.values().length];
            $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$ContentType = iArr;
            try {
                iArr[ContentType.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$ContentType[ContentType.URL_ENCODED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[Method.values().length];
            $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$Method = iArr2;
            try {
                iArr2[Method.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$Method[Method.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum ContentType {
        JSON,
        URL_ENCODED
    }

    public interface DownloadCallback {
        void onDownloadError(Call call, int i2, String str);

        void onDownloadProgressChanged(Call call, float f2);

        void onDownloadSuccess(Call call, File file);
    }

    public enum Method {
        GET,
        POST
    }

    public NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.mClient = builder.readTimeout(5L, timeUnit).writeTimeout(5L, timeUnit).build();
    }

    private Request createRequest(String str, Method method, ContentType contentType, Map<String, String> map) {
        Request.Builder builder = new Request.Builder();
        if (paramsInUrl(method)) {
            builder.url(urlWithParams(str, map)).method(stringOfMethod(method), null).addHeader("Accept-Encoding", "identity");
        } else {
            builder.url(str).method(stringOfMethod(method), requestBodyWithParams(contentType, map));
        }
        return builder.build();
    }

    private boolean paramsInUrl(Method method) {
        return method == Method.GET;
    }

    private RequestBody requestBodyWithParams(ContentType contentType, Map<String, String> map) {
        int i2 = AnonymousClass2.$SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$ContentType[contentType.ordinal()];
        if (i2 == 1) {
            return RequestBody.create(JSON, new JSONObject(map).toString());
        }
        if (i2 != 2) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private String stringOfMethod(Method method) {
        int i2 = AnonymousClass2.$SwitchMap$com$plv$beauty$api$resource$network$NetworkManager$Method[method.ordinal()];
        if (i2 == 1) {
            return "GET";
        }
        if (i2 != 2) {
            return null;
        }
        return "POST";
    }

    private String urlWithParams(String str, Map<String, String> map) {
        if (map == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append('?');
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        return sb.substring(0, sb.length() - 1);
    }

    public Call createDownloadTask(final String str, String str2, Method method, ContentType contentType, Map<String, String> map, final DownloadCallback downloadCallback) {
        Call callNewCall = this.mClient.newCall(createRequest(str2, method, contentType, map));
        callNewCall.enqueue(new Callback() { // from class: com.plv.beauty.api.resource.network.NetworkManager.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                downloadCallback.onDownloadError(call, -1, iOException.getMessage());
            }

            /* JADX WARN: Code restructure failed: missing block: B:10:0x0037, code lost:
            
                r1.close();
                r5.close();
             */
            /* JADX WARN: Code restructure failed: missing block: B:11:0x0041, code lost:
            
                if (r13.isCanceled() == false) goto L54;
             */
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0043, code lost:
            
                r2.delete();
             */
            /* JADX WARN: Code restructure failed: missing block: B:13:0x0046, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:54:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:9:0x0032, code lost:
            
                r2.onDownloadError(r13, 1, "download cancelled");
             */
            @Override // okhttp3.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onResponse(okhttp3.Call r13, okhttp3.Response r14) throws java.lang.Throwable {
                /*
                    r12 = this;
                    java.lang.String r0 = "download cancelled"
                    okhttp3.ResponseBody r1 = r14.body()
                    java.io.InputStream r1 = r1.byteStream()
                    java.io.File r2 = new java.io.File
                    java.lang.String r3 = r3
                    r2.<init>(r3)
                    r3 = 1
                    r4 = 0
                    java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
                    r5.<init>(r2)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L75
                    okhttp3.ResponseBody r14 = r14.body()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    long r6 = r14.contentLength()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    r14 = 8192(0x2000, float:1.148E-41)
                    byte[] r14 = new byte[r14]     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    r8 = 0
                L26:
                    int r4 = r1.read(r14)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    if (r4 <= 0) goto L59
                    boolean r10 = r13.isCanceled()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    if (r10 == 0) goto L47
                    com.plv.beauty.api.resource.network.NetworkManager$DownloadCallback r14 = r2     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    r14.onDownloadError(r13, r3, r0)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    r1.close()
                    r5.close()
                    boolean r13 = r13.isCanceled()
                    if (r13 == 0) goto L46
                    r2.delete()
                L46:
                    return
                L47:
                    r10 = 0
                    r5.write(r14, r10, r4)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    long r10 = (long) r4     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    long r8 = r8 + r10
                    com.plv.beauty.api.resource.network.NetworkManager$DownloadCallback r4 = r2     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    r10 = 1065353216(0x3f800000, float:1.0)
                    float r11 = (float) r8     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    float r11 = r11 * r10
                    float r10 = (float) r6     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    float r11 = r11 / r10
                    r4.onDownloadProgressChanged(r13, r11)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71
                    goto L26
                L59:
                    r1.close()
                    r5.close()
                    boolean r14 = r13.isCanceled()
                    if (r14 == 0) goto L68
                    r2.delete()
                L68:
                    com.plv.beauty.api.resource.network.NetworkManager$DownloadCallback r14 = r2
                    r14.onDownloadSuccess(r13, r2)
                    return
                L6e:
                    r14 = move-exception
                    r4 = r5
                    goto L9d
                L71:
                    r4 = r5
                    goto L75
                L73:
                    r14 = move-exception
                    goto L9d
                L75:
                    boolean r14 = r13.isCanceled()     // Catch: java.lang.Throwable -> L73
                    if (r14 == 0) goto L81
                    com.plv.beauty.api.resource.network.NetworkManager$DownloadCallback r14 = r2     // Catch: java.lang.Throwable -> L73
                    r14.onDownloadError(r13, r3, r0)     // Catch: java.lang.Throwable -> L73
                    goto L89
                L81:
                    com.plv.beauty.api.resource.network.NetworkManager$DownloadCallback r14 = r2     // Catch: java.lang.Throwable -> L73
                    java.lang.String r0 = "resource download fail"
                    r3 = -1
                    r14.onDownloadError(r13, r3, r0)     // Catch: java.lang.Throwable -> L73
                L89:
                    if (r1 == 0) goto L8e
                    r1.close()
                L8e:
                    if (r4 == 0) goto L93
                    r4.close()
                L93:
                    boolean r13 = r13.isCanceled()
                    if (r13 == 0) goto L9c
                    r2.delete()
                L9c:
                    return
                L9d:
                    if (r1 == 0) goto La2
                    r1.close()
                La2:
                    if (r4 == 0) goto La7
                    r4.close()
                La7:
                    boolean r13 = r13.isCanceled()
                    if (r13 == 0) goto Lb0
                    r2.delete()
                Lb0:
                    throw r14
                */
                throw new UnsupportedOperationException("Method not decompiled: com.plv.beauty.api.resource.network.NetworkManager.AnonymousClass1.onResponse(okhttp3.Call, okhttp3.Response):void");
            }
        });
        return callNewCall;
    }

    public String postJson(String str, Map<String, String> map) throws IOException {
        ResponseBody responseBodyBody = this.mClient.newCall(createRequest(str, Method.POST, ContentType.JSON, map)).execute().body();
        if (responseBodyBody == null) {
            return null;
        }
        return responseBodyBody.string();
    }
}
