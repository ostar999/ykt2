package com.tencent.liteav.basic.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes6.dex */
public class TXHttpRequest {
    private static final int CON_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static final String TAG = "TXHttpRequest";
    private long mNativeHttps;
    private d mSock5ProxyConfig = new d();

    public static class a extends Authenticator {

        /* renamed from: a, reason: collision with root package name */
        private String f18674a;

        /* renamed from: b, reason: collision with root package name */
        private String f18675b;

        public a(String str, String str2) {
            this.f18674a = str;
            this.f18675b = str2;
        }

        @Override // java.net.Authenticator
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.f18674a, this.f18675b.toCharArray());
        }
    }

    public static class b extends AsyncTask<byte[], Void, c> {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<TXHttpRequest> f18676a;

        /* renamed from: b, reason: collision with root package name */
        private Handler f18677b;

        /* renamed from: c, reason: collision with root package name */
        private Map<String, String> f18678c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f18679d;

        /* renamed from: e, reason: collision with root package name */
        private long f18680e;

        /* renamed from: f, reason: collision with root package name */
        private d f18681f;

        public b(TXHttpRequest tXHttpRequest, Map<String, String> map, boolean z2, long j2, d dVar) {
            this.f18677b = null;
            this.f18679d = false;
            this.f18680e = 0L;
            this.f18678c = map;
            this.f18676a = new WeakReference<>(tXHttpRequest);
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper != null) {
                this.f18677b = new Handler(looperMyLooper);
            } else {
                this.f18677b = null;
            }
            this.f18679d = z2;
            this.f18680e = j2;
            this.f18681f = dVar;
        }

        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c doInBackground(byte[]... bArr) {
            c cVar = new c();
            try {
                if (new String(bArr[0]).startsWith("https")) {
                    cVar = TXHttpRequest.getHttpsPostRsp(this.f18678c, new String(bArr[0]), bArr[1], this.f18679d, this.f18681f);
                    cVar.f18685a = 0;
                } else {
                    cVar.f18685a = 1;
                    cVar.f18686b = "http request not support";
                }
            } catch (Exception e2) {
                cVar.f18686b = e2.toString();
                cVar.f18685a = 1;
                TXCLog.i(TXHttpRequest.TAG, "doInBackground Exception e=" + cVar.f18685a + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + cVar.f18686b);
            }
            TXCLog.i(TXHttpRequest.TAG, "TXPostRequest->result: " + cVar.f18685a + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + cVar.f18686b);
            return cVar;
        }

        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(final c cVar) {
            super.onPostExecute(cVar);
            final TXHttpRequest tXHttpRequest = this.f18676a.get();
            if (tXHttpRequest == null || tXHttpRequest.mNativeHttps == 0) {
                return;
            }
            Handler handler = this.f18677b;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.tencent.liteav.basic.util.TXHttpRequest.b.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCLog.i(TXHttpRequest.TAG, "TXPostRequest->recvMsg: " + cVar.f18685a + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + cVar.f18686b);
                        TXHttpRequest tXHttpRequest2 = tXHttpRequest;
                        long j2 = tXHttpRequest2.mNativeHttps;
                        c cVar2 = cVar;
                        tXHttpRequest2.nativeOnRecvMessage(j2, cVar2.f18685a, cVar2.f18687c, cVar2.f18688d, b.this.f18680e);
                    }
                });
                return;
            }
            TXCLog.i(TXHttpRequest.TAG, "TXPostRequest->recvMsg: " + cVar.f18685a + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + cVar.f18686b);
            tXHttpRequest.nativeOnRecvMessage(tXHttpRequest.mNativeHttps, cVar.f18685a, cVar.f18687c, cVar.f18688d, this.f18680e);
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public int f18685a = 1;

        /* renamed from: b, reason: collision with root package name */
        public String f18686b = "";

        /* renamed from: c, reason: collision with root package name */
        public byte[] f18687c = "".getBytes();

        /* renamed from: d, reason: collision with root package name */
        public Map<String, String> f18688d;
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        public String f18689a;

        /* renamed from: b, reason: collision with root package name */
        public int f18690b;

        /* renamed from: c, reason: collision with root package name */
        public String f18691c;

        /* renamed from: d, reason: collision with root package name */
        public String f18692d;

        private d() {
            this.f18689a = "";
            this.f18690b = 0;
            this.f18691c = "";
            this.f18692d = "";
        }
    }

    public TXHttpRequest(long j2) {
        this.mNativeHttps = 0L;
        this.mNativeHttps = j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.tencent.liteav.basic.util.TXHttpRequest.c downloadFileInternal(java.lang.String r7, java.lang.String r8, com.tencent.liteav.basic.util.TXHttpRequest.d r9) {
        /*
            com.tencent.liteav.basic.util.TXHttpRequest$c r0 = new com.tencent.liteav.basic.util.TXHttpRequest$c
            r0.<init>()
            r1 = 0
            if (r9 == 0) goto L36
            java.lang.String r2 = r9.f18689a     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            if (r2 != 0) goto L36
            int r2 = r9.f18690b     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            if (r2 <= 0) goto L36
            java.net.Proxy r2 = new java.net.Proxy     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.net.Proxy$Type r3 = java.net.Proxy.Type.SOCKS     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.net.InetSocketAddress r4 = new java.net.InetSocketAddress     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.String r5 = r9.f18689a     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            int r6 = r9.f18690b     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r4.<init>(r5, r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            com.tencent.liteav.basic.util.TXHttpRequest$a r3 = new com.tencent.liteav.basic.util.TXHttpRequest$a     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.String r4 = r9.f18691c     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.String r9 = r9.f18692d     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r3.<init>(r4, r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.net.Authenticator.setDefault(r3)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            goto L37
        L31:
            r7 = move-exception
            goto Lb3
        L34:
            r8 = move-exception
            goto L91
        L36:
            r2 = r1
        L37:
            if (r2 == 0) goto L45
            java.net.URL r9 = new java.net.URL     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r9.<init>(r7)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.net.URLConnection r9 = r9.openConnection(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            javax.net.ssl.HttpsURLConnection r9 = (javax.net.ssl.HttpsURLConnection) r9     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            goto L50
        L45:
            java.net.URL r9 = new java.net.URL     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r9.<init>(r7)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.net.URLConnection r9 = r9.openConnection()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            javax.net.ssl.HttpsURLConnection r9 = (javax.net.ssl.HttpsURLConnection) r9     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
        L50:
            r1 = r9
            java.lang.String r9 = "GET"
            r1.setRequestMethod(r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r9 = 0
            r1.setUseCaches(r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r2 = 5000(0x1388, float:7.006E-42)
            r1.setConnectTimeout(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r1.setReadTimeout(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            int r2 = r1.getResponseCode()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L7a
            java.util.Map r2 = getHeaders(r1)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r0.f18688d = r2     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.io.InputStream r2 = r1.getInputStream()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            com.tencent.liteav.basic.util.d.a(r2, r8)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r0.f18685a = r9     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            goto Laf
        L7a:
            java.io.IOException r8 = new java.io.IOException     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r9.<init>()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.String r3 = "download file failed with "
            r9.append(r3)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r9.append(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            throw r8     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
        L91:
            java.lang.String r9 = r8.getMessage()     // Catch: java.lang.Throwable -> L31
            r0.f18686b = r9     // Catch: java.lang.Throwable -> L31
            java.lang.String r9 = "TXHttpRequest"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31
            r2.<init>()     // Catch: java.lang.Throwable -> L31
            java.lang.String r3 = "download file failed. "
            r2.append(r3)     // Catch: java.lang.Throwable -> L31
            r2.append(r7)     // Catch: java.lang.Throwable -> L31
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> L31
            com.tencent.liteav.basic.log.TXCLog.e(r9, r7, r8)     // Catch: java.lang.Throwable -> L31
            if (r1 == 0) goto Lb2
        Laf:
            r1.disconnect()
        Lb2:
            return r0
        Lb3:
            if (r1 == 0) goto Lb8
            r1.disconnect()
        Lb8:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.TXHttpRequest.downloadFileInternal(java.lang.String, java.lang.String, com.tencent.liteav.basic.util.TXHttpRequest$d):com.tencent.liteav.basic.util.TXHttpRequest$c");
    }

    private static Map<String, String> getHeaders(HttpsURLConnection httpsURLConnection) {
        HashMap map = new HashMap();
        for (Map.Entry<String, List<String>> entry : httpsURLConnection.getHeaderFields().entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey())) {
                map.put(entry.getKey(), entry.getValue().get(0));
            }
        }
        return map;
    }

    public static c getHttpsPostRsp(Map<String, String> map, String str, byte[] bArr, boolean z2, d dVar) throws Exception {
        Proxy proxy;
        TXCLog.i(TAG, "getHttpsPostRsp->request: " + str);
        TXCLog.i(TAG, "getHttpsPostRsp->data: " + bArr.length);
        if (dVar == null || TextUtils.isEmpty(dVar.f18689a) || dVar.f18690b <= 0) {
            proxy = null;
        } else {
            proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(dVar.f18689a, dVar.f18690b));
            Authenticator.setDefault(new a(dVar.f18691c, dVar.f18692d));
        }
        URL url = new URL(str.replace(" ", "%20"));
        HttpsURLConnection httpsURLConnection = proxy != null ? (HttpsURLConnection) url.openConnection(proxy) : (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setConnectTimeout(5000);
        httpsURLConnection.setReadTimeout(5000);
        httpsURLConnection.setRequestMethod("POST");
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        if (z2) {
            httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
        }
        DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
        dataOutputStream.write(bArr);
        dataOutputStream.flush();
        dataOutputStream.close();
        int responseCode = httpsURLConnection.getResponseCode();
        c cVar = new c();
        if (responseCode < 200 || responseCode >= 300) {
            TXCLog.i(TAG, "getHttpsPostRsp->response code: " + responseCode);
            throw new Exception("response: " + responseCode);
        }
        InputStream inputStream = httpsURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = inputStream.read(bArr, 0, bArr.length);
            if (i2 == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, i2);
        }
        byteArrayOutputStream.flush();
        cVar.f18688d = new HashMap();
        for (Map.Entry<String, List<String>> entry2 : httpsURLConnection.getHeaderFields().entrySet()) {
            if (!TextUtils.isEmpty(entry2.getKey())) {
                cVar.f18688d.put(entry2.getKey(), entry2.getValue().get(0));
            }
        }
        inputStream.close();
        if (!z2) {
            httpsURLConnection.disconnect();
        }
        cVar.f18687c = byteArrayOutputStream.toByteArray();
        cVar.f18685a = 0;
        TXCLog.i(TAG, "getHttpsPostRsp->rsp size: " + byteArrayOutputStream.size());
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnRecvMessage(long j2, int i2, byte[] bArr, Map<String, String> map, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyResult(Handler handler, final c cVar, final long j2) {
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.basic.util.TXHttpRequest.2
                @Override // java.lang.Runnable
                public void run() {
                    TXHttpRequest tXHttpRequest = TXHttpRequest.this;
                    long j3 = tXHttpRequest.mNativeHttps;
                    c cVar2 = cVar;
                    tXHttpRequest.nativeOnRecvMessage(j3, cVar2.f18685a, cVar2.f18687c, cVar2.f18688d, j2);
                }
            });
        } else {
            nativeOnRecvMessage(this.mNativeHttps, cVar.f18685a, cVar.f18687c, cVar.f18688d, j2);
        }
    }

    public void asyncPostRequest(Map<String, String> map, byte[] bArr, byte[] bArr2, boolean z2, long j2, d dVar) {
        new b(this, map, z2, j2, dVar).execute(bArr, bArr2);
    }

    public int downloadFile(final String str, final String str2, final long j2) {
        final ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Looper looperMyLooper = Looper.myLooper();
        final Handler handler = looperMyLooper == null ? null : new Handler(looperMyLooper);
        executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.tencent.liteav.basic.util.TXHttpRequest.1
            @Override // java.lang.Runnable
            public void run() {
                TXHttpRequest.this.notifyResult(handler, TXHttpRequest.downloadFileInternal(str, str2, TXHttpRequest.this.mSock5ProxyConfig), j2);
                executorServiceNewSingleThreadExecutor.shutdown();
            }
        });
        return 0;
    }

    public int sendHttpsRequest(Map<String, String> map, String str, byte[] bArr, boolean z2, long j2) {
        TXCLog.i(TAG, "sendHttpsRequest->enter action: " + str + ", data size: " + bArr.length);
        asyncPostRequest(map, str.getBytes(), bArr, z2, j2, this.mSock5ProxyConfig);
        return 0;
    }

    public void setSocks5Proxy(String str, int i2, String str2, String str3) {
        d dVar = this.mSock5ProxyConfig;
        dVar.f18689a = str;
        dVar.f18690b = i2;
        dVar.f18691c = str2;
        dVar.f18692d = str3;
    }
}
