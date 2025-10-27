package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

/* loaded from: classes2.dex */
public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static int DEFAULT_POOL_SIZE = 4096;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    private void addCacheHeaders(Map<String, String> map, Cache.Entry entry) {
        if (entry == null) {
            return;
        }
        String str = entry.etag;
        if (str != null) {
            map.put("If-None-Match", str);
        }
        if (entry.serverDate > 0) {
            map.put("If-Modified-Since", DateUtils.formatDate(new Date(entry.serverDate)));
        }
    }

    private static void attemptRetryOnException(String str, Request<?> request, VolleyError volleyError) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int timeoutMs = request.getTimeoutMs();
        try {
            retryPolicy.retry(volleyError);
            request.addMarker(String.format("%s-retry [timeout=%s]", str, Integer.valueOf(timeoutMs)));
        } catch (VolleyError e2) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", str, Integer.valueOf(timeoutMs)));
            throw e2;
        }
    }

    private static Map<String, String> convertHeaders(Header[] headerArr) {
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < headerArr.length; i2++) {
            map.put(headerArr[i2].getName(), headerArr[i2].getValue());
        }
        return map;
    }

    private byte[] entityToBytes(HttpEntity httpEntity) throws ServerError, IOException {
        PoolingByteArrayOutputStream poolingByteArrayOutputStream = new PoolingByteArrayOutputStream(this.mPool, (int) httpEntity.getContentLength());
        try {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                throw new ServerError();
            }
            byte[] buf = this.mPool.getBuf(1024);
            while (true) {
                int i2 = content.read(buf);
                if (i2 == -1) {
                    break;
                }
                poolingByteArrayOutputStream.write(buf, 0, i2);
            }
            byte[] byteArray = poolingByteArrayOutputStream.toByteArray();
            try {
                httpEntity.consumeContent();
            } catch (IOException unused) {
                VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
            }
            this.mPool.returnBuf(buf);
            poolingByteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                httpEntity.consumeContent();
            } catch (IOException unused2) {
                VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
            }
            this.mPool.returnBuf(null);
            poolingByteArrayOutputStream.close();
            throw th;
        }
    }

    private void logSlowRequests(long j2, Request<?> request, byte[] bArr, StatusLine statusLine) {
        if (DEBUG || j2 > SLOW_REQUEST_THRESHOLD_MS) {
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(j2);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", objArr);
        }
    }

    public void logError(String str, String str2, long j2) {
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", str, Long.valueOf(SystemClock.elapsedRealtime() - j2), str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x007c, code lost:
    
        throw new java.io.IOException();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.android.volley.toolbox.BasicNetwork] */
    /* JADX WARN: Type inference failed for: r2v11, types: [long] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    @Override // com.android.volley.Network
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.volley.NetworkResponse performRequest(com.android.volley.Request<?> r18) throws java.io.IOException, com.android.volley.VolleyError {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.performRequest(com.android.volley.Request):com.android.volley.NetworkResponse");
    }

    public BasicNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        this.mPool = byteArrayPool;
    }
}
