package com.aliyun.svideo.common.okhttp;

import android.os.Handler;
import android.os.Looper;
import com.aliyun.svideo.common.okhttp.interceptor.LoggingIntcepetor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AlivcOkHttpClient {
    private static AlivcOkHttpClient alivcOkHttpClient;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().addNetworkInterceptor(new LoggingIntcepetor());
    private Handler handler = new Handler(Looper.getMainLooper());

    public interface HttpCallBack {
        void onError(Request request, IOException iOException);

        void onSuccess(Request request, String str);
    }

    public class StringCallBack implements Callback {
        private HttpCallBack httpCallBack;
        private Request request;

        public StringCallBack(Request request, HttpCallBack httpCallBack) {
            this.request = request;
            this.httpCallBack = httpCallBack;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, final IOException iOException) {
            if (this.httpCallBack != null) {
                AlivcOkHttpClient.this.handler.post(new Runnable() { // from class: com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.StringCallBack.1
                    @Override // java.lang.Runnable
                    public void run() {
                        StringCallBack.this.httpCallBack.onError(StringCallBack.this.request, iOException);
                    }
                });
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            final String strString = response.body().string();
            try {
                final JSONObject jSONObject = new JSONObject(strString);
                if ("200".equals(jSONObject.getString("code"))) {
                    if (this.httpCallBack != null) {
                        AlivcOkHttpClient.this.handler.post(new Runnable() { // from class: com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.StringCallBack.2
                            @Override // java.lang.Runnable
                            public void run() {
                                StringCallBack.this.httpCallBack.onSuccess(StringCallBack.this.request, strString);
                            }
                        });
                    } else {
                        AlivcOkHttpClient.this.handler.post(new Runnable() { // from class: com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.StringCallBack.3
                            @Override // java.lang.Runnable
                            public void run() {
                                StringCallBack.this.httpCallBack.onError(StringCallBack.this.request, new IOException("json error"));
                            }
                        });
                    }
                } else if (this.httpCallBack != null) {
                    AlivcOkHttpClient.this.handler.post(new Runnable() { // from class: com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.StringCallBack.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                StringCallBack.this.httpCallBack.onError(StringCallBack.this.request, new IOException(jSONObject.getString("message")));
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private AlivcOkHttpClient() {
        build();
    }

    private void build() {
        OkHttpClient.Builder builder = this.okHttpBuilder;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        builder.connectTimeout(10L, timeUnit);
        this.okHttpBuilder.writeTimeout(10L, timeUnit);
        this.okHttpBuilder.readTimeout(10L, timeUnit);
        this.okHttpClient = this.okHttpBuilder.build();
    }

    public static AlivcOkHttpClient getInstance() {
        if (alivcOkHttpClient == null) {
            synchronized (AlivcOkHttpClient.class) {
                if (alivcOkHttpClient == null) {
                    alivcOkHttpClient = new AlivcOkHttpClient();
                }
            }
        }
        return alivcOkHttpClient;
    }

    public FormBody formBody(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            Set<String> setKeySet = map.keySet();
            if (!setKeySet.isEmpty()) {
                for (String str : setKeySet) {
                    String str2 = map.get(str);
                    if (str2 != null) {
                        builder.add(str, str2);
                    }
                }
            }
        }
        return builder.build();
    }

    public void get(String str, HttpCallBack httpCallBack) {
        Request requestBuild = new Request.Builder().url(str).build();
        this.okHttpClient.newCall(requestBuild).enqueue(new StringCallBack(requestBuild, httpCallBack));
    }

    public void post(String str, Map<String, String> map, HttpCallBack httpCallBack) {
        Request requestBuild = new Request.Builder().url(str).post(formBody(map)).build();
        this.okHttpClient.newCall(requestBuild).enqueue(new StringCallBack(requestBuild, httpCallBack));
    }

    public String urlWithParam(String str, Map<String, String> map) {
        if (map == null) {
            return str;
        }
        Set<String> setKeySet = map.keySet();
        if (setKeySet.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        for (String str2 : setKeySet) {
            if (z2) {
                sb.append("&");
            }
            sb.append(str2);
            sb.append("=");
            sb.append(map.get(str2));
            z2 = true;
        }
        return str + "?" + sb.toString();
    }

    public void get(String str, HashMap<String, String> map, HttpCallBack httpCallBack) {
        Request requestBuild = new Request.Builder().url(urlWithParam(str, map)).build();
        this.okHttpClient.newCall(requestBuild).enqueue(new StringCallBack(requestBuild, httpCallBack));
    }
}
