package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final long SLOW_REQUEST_THRESHOLD_MS = 3000;
    private Cache.Entry mCacheEntry;
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private final Response.ErrorListener mErrorListener;
    private final VolleyLog.MarkerLog mEventLog;
    private final int mMethod;
    private long mRequestBirthTime;
    private RequestQueue mRequestQueue;
    private String mRequestType;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private Object mTag;
    private final String mUrl;

    public interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int POST = 1;
        public static final int PUT = 2;
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE;

        /* renamed from: values, reason: to resolve conflict with enum method */
        public static Priority[] valuesCustom() {
            Priority[] priorityArrValuesCustom = values();
            int length = priorityArrValuesCustom.length;
            Priority[] priorityArr = new Priority[length];
            System.arraycopy(priorityArrValuesCustom, 0, priorityArr, 0, length);
            return priorityArr;
        }
    }

    public Request(String str, Response.ErrorListener errorListener) {
        this(-1, str, errorListener);
    }

    private byte[] encodeParameters(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString().getBytes(str);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Encoding not supported: " + str, e2);
        }
    }

    public void addMarker(String str) {
        if (VolleyLog.MarkerLog.ENABLED) {
            this.mEventLog.add(str, Thread.currentThread().getId());
        } else if (this.mRequestBirthTime == 0) {
            this.mRequestBirthTime = SystemClock.elapsedRealtime();
        }
    }

    public void cancel() {
        this.mCanceled = true;
    }

    public void deliverError(VolleyError volleyError, String str) {
        Response.ErrorListener errorListener = this.mErrorListener;
        if (errorListener != null) {
            errorListener.onErrorResponse(volleyError, str);
        }
    }

    public abstract void deliverResponse(T t2, String str);

    public void finish(final String str) {
        RequestQueue requestQueue = this.mRequestQueue;
        if (requestQueue != null) {
            requestQueue.finish(this);
        }
        if (!VolleyLog.MarkerLog.ENABLED) {
            long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mRequestBirthTime;
            if (jElapsedRealtime >= 3000) {
                VolleyLog.d("%d ms: %s", Long.valueOf(jElapsedRealtime), toString());
                return;
            }
            return;
        }
        final long id = Thread.currentThread().getId();
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.volley.Request.1
                @Override // java.lang.Runnable
                public void run() {
                    Request.this.mEventLog.add(str, id);
                    Request.this.mEventLog.finish(toString());
                }
            });
        } else {
            this.mEventLog.add(str, id);
            this.mEventLog.finish(toString());
        }
    }

    public byte[] getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params == null || params.size() <= 0) {
            return null;
        }
        return encodeParameters(params, getParamsEncoding());
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public Cache.Entry getCacheEntry() {
        return this.mCacheEntry;
    }

    public String getCacheKey() {
        return getUrl();
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return Collections.emptyMap();
    }

    public int getMethod() {
        return this.mMethod;
    }

    public Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    public String getParamsEncoding() {
        return "UTF-8";
    }

    public byte[] getPostBody() throws AuthFailureError {
        Map<String, String> postParams = getPostParams();
        if (postParams == null || postParams.size() <= 0) {
            return null;
        }
        return encodeParameters(postParams, getPostParamsEncoding());
    }

    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    public Map<String, String> getPostParams() throws AuthFailureError {
        return getParams();
    }

    public String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public String getRequestType() {
        return this.mRequestType;
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public final int getSequence() {
        Integer num = this.mSequence;
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public Object getTag() {
        return this.mTag;
    }

    public final int getTimeoutMs() {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean hasHadResponseDelivered() {
        return this.mResponseDelivered;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public void markDelivered() {
        this.mResponseDelivered = true;
    }

    public VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    public abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    public void setCacheEntry(Cache.Entry entry) {
        this.mCacheEntry = entry;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    public void setRequestType(String str) {
        this.mRequestType = str;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
    }

    public final void setSequence(int i2) {
        this.mSequence = Integer.valueOf(i2);
    }

    public final void setShouldCache(boolean z2) {
        this.mShouldCache = z2;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    public String toString() {
        return (this.mCanceled ? "[X] " : "[ ] ") + getUrl() + " " + ("0x" + Integer.toHexString(getTrafficStatsTag())) + " " + getPriority() + " " + this.mSequence;
    }

    public Request(int i2, String str, Response.ErrorListener errorListener) {
        this.mEventLog = VolleyLog.MarkerLog.ENABLED ? new VolleyLog.MarkerLog() : null;
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mRequestBirthTime = 0L;
        this.mCacheEntry = null;
        this.mMethod = i2;
        this.mUrl = str;
        this.mErrorListener = errorListener;
        setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = TextUtils.isEmpty(str) ? 0 : Uri.parse(str).getHost().hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Request<T> request) {
        Priority priority = getPriority();
        Priority priority2 = request.getPriority();
        return priority == priority2 ? this.mSequence.intValue() - request.mSequence.intValue() : priority2.ordinal() - priority.ordinal();
    }
}
