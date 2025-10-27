package net.tsz.afinal.http;

import android.os.SystemClock;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import net.tsz.afinal.core.AsyncTask;
import net.tsz.afinal.http.entityhandler.EntityCallBack;
import net.tsz.afinal.http.entityhandler.FileEntityHandler;
import net.tsz.afinal.http.entityhandler.StringEntityHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes9.dex */
public class HttpHandler<T> extends AsyncTask<Object, Object, Object> implements EntityCallBack {
    private static final int UPDATE_FAILURE = 3;
    private static final int UPDATE_LOADING = 2;
    private static final int UPDATE_START = 1;
    private static final int UPDATE_SUCCESS = 4;
    private final AjaxCallBack<T> callback;
    private String charset;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private long time;
    private final StringEntityHandler mStrEntityHandler = new StringEntityHandler();
    private final FileEntityHandler mFileEntityHandler = new FileEntityHandler();
    private int executionCount = 0;
    private String targetUrl = null;
    private boolean isResume = false;

    public HttpHandler(AbstractHttpClient abstractHttpClient, HttpContext httpContext, AjaxCallBack<T> ajaxCallBack, String str) {
        this.client = abstractHttpClient;
        this.context = httpContext;
        this.callback = ajaxCallBack;
        this.charset = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c3, code lost:
    
        r4 = r14.getValue().split("filename=");
        r5 = r4[1].split("\\.")[0];
        r2 = r4[1].split("\\.")[1];
        r4 = new java.lang.String(android.util.Base64.decode(r5.getBytes(), 8));
        r5 = new java.lang.StringBuilder();
        r8 = r16.targetUrl;
        r5.append(r8.substring(0, r8.lastIndexOf("/")));
        r5.append("/");
        r5.append(r4);
        r5.append(cn.hutool.core.text.StrPool.DOT);
        r5.append(r2);
        r16.targetUrl = r5.toString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleResponse(org.apache.http.HttpResponse r17) throws java.lang.IllegalStateException, org.apache.http.ParseException {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.tsz.afinal.http.HttpHandler.handleResponse(org.apache.http.HttpResponse):void");
    }

    private void makeRequestWithRetries(HttpUriRequest httpUriRequest) throws IOException {
        IOException iOException;
        boolean zRetryRequest;
        if (this.isResume && this.targetUrl != null) {
            File file = new File(this.targetUrl);
            long length = (file.isFile() && file.exists()) ? file.length() : 0L;
            if (length > 0) {
                httpUriRequest.setHeader("RANGE", "bytes=" + length + "-");
            }
        }
        HttpRequestRetryHandler httpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
        IOException e2 = null;
        boolean zRetryRequest2 = true;
        while (zRetryRequest2) {
            try {
                if (isCancelled()) {
                    return;
                }
                HttpResponse httpResponseExecute = this.client.execute(httpUriRequest, this.context);
                if (isCancelled()) {
                    return;
                }
                handleResponse(httpResponseExecute);
                return;
            } catch (IOException e3) {
                e2 = e3;
                int i2 = this.executionCount + 1;
                this.executionCount = i2;
                zRetryRequest2 = httpRequestRetryHandler.retryRequest(e2, i2, this.context);
            } catch (NullPointerException e4) {
                iOException = new IOException("NPE in HttpClient" + e4.getMessage());
                int i3 = this.executionCount + 1;
                this.executionCount = i3;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i3, this.context);
                IOException iOException2 = iOException;
                zRetryRequest2 = zRetryRequest;
                e2 = iOException2;
            } catch (UnknownHostException e5) {
                publishProgress(3, e5, 0, "unknownHostException：can't resolve host");
                return;
            } catch (Exception e6) {
                iOException = new IOException("Exception" + e6.getMessage());
                int i4 = this.executionCount + 1;
                this.executionCount = i4;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i4, this.context);
                IOException iOException22 = iOException;
                zRetryRequest2 = zRetryRequest;
                e2 = iOException22;
            }
        }
        if (e2 == null) {
            throw new IOException("未知网络错误");
        }
        throw e2;
    }

    @Override // net.tsz.afinal.http.entityhandler.EntityCallBack
    public void callBack(long j2, long j3, boolean z2) {
        AjaxCallBack<T> ajaxCallBack = this.callback;
        if (ajaxCallBack == null || !ajaxCallBack.isProgress()) {
            return;
        }
        if (z2) {
            publishProgress(2, Long.valueOf(j2), Long.valueOf(j3));
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (jUptimeMillis - this.time >= this.callback.getRate()) {
            this.time = jUptimeMillis;
            publishProgress(2, Long.valueOf(j2), Long.valueOf(j3));
        }
    }

    @Override // net.tsz.afinal.core.AsyncTask
    public Object doInBackground(Object... objArr) {
        if (objArr != null && objArr.length == 3) {
            this.targetUrl = String.valueOf(objArr[1]);
            this.isResume = ((Boolean) objArr[2]).booleanValue();
        }
        try {
            publishProgress(1);
            makeRequestWithRetries((HttpUriRequest) objArr[0]);
            return null;
        } catch (IOException e2) {
            publishProgress(3, e2, 0, e2.getMessage());
            return null;
        }
    }

    public boolean isStop() {
        return this.mFileEntityHandler.isStop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.tsz.afinal.core.AsyncTask
    public void onProgressUpdate(Object... objArr) {
        AjaxCallBack<T> ajaxCallBack;
        int iIntValue = Integer.valueOf(String.valueOf(objArr[0])).intValue();
        if (iIntValue == 1) {
            AjaxCallBack<T> ajaxCallBack2 = this.callback;
            if (ajaxCallBack2 != null) {
                ajaxCallBack2.onStart();
            }
        } else if (iIntValue == 2) {
            AjaxCallBack<T> ajaxCallBack3 = this.callback;
            if (ajaxCallBack3 != null) {
                ajaxCallBack3.onLoading(Long.valueOf(String.valueOf(objArr[1])).longValue(), Long.valueOf(String.valueOf(objArr[2])).longValue());
            }
        } else if (iIntValue == 3) {
            AjaxCallBack<T> ajaxCallBack4 = this.callback;
            if (ajaxCallBack4 != null) {
                ajaxCallBack4.onFailure((Throwable) objArr[1], ((Integer) objArr[2]).intValue(), (String) objArr[3]);
            }
        } else if (iIntValue == 4 && (ajaxCallBack = this.callback) != 0) {
            ajaxCallBack.onSuccess(objArr[1]);
        }
        super.onProgressUpdate(objArr);
    }

    public void stop() {
        this.mFileEntityHandler.setStop(true);
    }
}
