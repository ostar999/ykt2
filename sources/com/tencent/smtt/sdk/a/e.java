package com.tencent.smtt.sdk.a;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.tencent.smtt.utils.TbsLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static String f21130a = "EmergencyManager";

    /* renamed from: f, reason: collision with root package name */
    private static final Object f21131f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static HandlerThread f21132g;

    /* renamed from: h, reason: collision with root package name */
    private static Handler f21133h;

    /* renamed from: b, reason: collision with root package name */
    private String f21134b;

    /* renamed from: c, reason: collision with root package name */
    private String f21135c;

    /* renamed from: d, reason: collision with root package name */
    private String f21136d;

    /* renamed from: e, reason: collision with root package name */
    private Handler f21137e;

    public interface a {
        void a(String str);
    }

    public e(Context context, String str, String str2) {
        this(context, str, str2, "POST");
    }

    public e(Context context, String str, String str2, String str3) {
        this.f21134b = str;
        this.f21135c = str2;
        this.f21136d = str3;
        this.f21137e = new Handler(context.getMainLooper());
    }

    private static Handler b() {
        Handler handler;
        synchronized (f21131f) {
            if (f21133h == null) {
                HandlerThread handlerThread = new HandlerThread("HttpThread");
                f21132g = handlerThread;
                handlerThread.start();
                f21133h = new Handler(f21132g.getLooper());
            }
            handler = f21133h;
        }
        return handler;
    }

    public String a(String str) throws IOException {
        TbsLog.e(f21130a, "Request url: " + this.f21134b + ",params: " + this.f21135c);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str.trim()).openConnection();
            httpURLConnection.setRequestMethod(this.f21136d);
            httpURLConnection.setRequestProperty("Content-Type", FastJsonJsonView.DEFAULT_CONTENT_TYPE);
            httpURLConnection.setRequestProperty("Content-Length", this.f21135c.length() + "");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(this.f21135c.getBytes());
            int responseCode = httpURLConnection.getResponseCode();
            if (200 != responseCode) {
                TbsLog.e(f21130a, "Bad http request, code: " + responseCode);
                return null;
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (-1 == i2) {
                    return byteArrayOutputStream.toString("utf-8");
                }
                byteArrayOutputStream.write(bArr, 0, i2);
                byteArrayOutputStream.flush();
            }
        } catch (Exception e2) {
            TbsLog.e(f21130a, "Http exception: " + e2.getMessage());
            return null;
        }
    }

    public void a(final a aVar) {
        b().post(new Runnable() { // from class: com.tencent.smtt.sdk.a.e.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                e eVar = e.this;
                final String strA = eVar.a(eVar.f21134b);
                if (strA != null) {
                    e.this.f21137e.post(new Runnable() { // from class: com.tencent.smtt.sdk.a.e.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a aVar2 = aVar;
                            if (aVar2 != null) {
                                aVar2.a(strA);
                            }
                        }
                    });
                    return;
                }
                TbsLog.e(e.f21130a, "Unexpected result for an empty http response: " + e.this.f21134b);
            }
        });
    }
}
