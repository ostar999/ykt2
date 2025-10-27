package com.thin.downloadmanager;

import android.net.Uri;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class DownloadRequest implements Comparable<DownloadRequest> {
    private HashMap<String, String> mCustomHeader;
    private Uri mDestinationURI;
    private Object mDownloadContext;
    private int mDownloadId;
    private DownloadStatusListener mDownloadListener;
    private int mDownloadState;
    private DownloadStatusListenerV1 mDownloadStatusListenerV1;
    private DownloadRequestQueue mRequestQueue;
    private RetryPolicy mRetryPolicy;
    private Uri mUri;
    private boolean mCancelled = false;
    private boolean mDeleteDestinationFileOnFailure = true;
    private Priority mPriority = Priority.NORMAL;

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public DownloadRequest(Uri uri) {
        uri.getClass();
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equals("http") || scheme.equals("https"))) {
            throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
        }
        this.mCustomHeader = new HashMap<>();
        this.mDownloadState = 1;
        this.mUri = uri;
    }

    public DownloadRequest addCustomHeader(String str, String str2) {
        this.mCustomHeader.put(str, str2);
        return this;
    }

    public void cancel() {
        this.mCancelled = true;
    }

    public void finish() {
        this.mRequestQueue.finish(this);
    }

    public HashMap<String, String> getCustomHeaders() {
        return this.mCustomHeader;
    }

    public boolean getDeleteDestinationFileOnFailure() {
        return this.mDeleteDestinationFileOnFailure;
    }

    public Uri getDestinationURI() {
        return this.mDestinationURI;
    }

    public Object getDownloadContext() {
        return this.mDownloadContext;
    }

    public final int getDownloadId() {
        return this.mDownloadId;
    }

    public DownloadStatusListener getDownloadListener() {
        return this.mDownloadListener;
    }

    public int getDownloadState() {
        return this.mDownloadState;
    }

    public Priority getPriority() {
        return this.mPriority;
    }

    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy = this.mRetryPolicy;
        return retryPolicy == null ? new DefaultRetryPolicy() : retryPolicy;
    }

    public DownloadStatusListenerV1 getStatusListener() {
        return this.mDownloadStatusListenerV1;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isCancelled() {
        return this.mCancelled;
    }

    public DownloadRequest setDeleteDestinationFileOnFailure(boolean z2) {
        this.mDeleteDestinationFileOnFailure = z2;
        return this;
    }

    public DownloadRequest setDestinationURI(Uri uri) {
        this.mDestinationURI = uri;
        return this;
    }

    public DownloadRequest setDownloadContext(Object obj) {
        this.mDownloadContext = obj;
        return this;
    }

    public final void setDownloadId(int i2) {
        this.mDownloadId = i2;
    }

    @Deprecated
    public DownloadRequest setDownloadListener(DownloadStatusListener downloadStatusListener) {
        this.mDownloadListener = downloadStatusListener;
        return this;
    }

    public void setDownloadRequestQueue(DownloadRequestQueue downloadRequestQueue) {
        this.mRequestQueue = downloadRequestQueue;
    }

    public void setDownloadState(int i2) {
        this.mDownloadState = i2;
    }

    public DownloadRequest setPriority(Priority priority) {
        this.mPriority = priority;
        return this;
    }

    public DownloadRequest setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public DownloadRequest setStatusListener(DownloadStatusListenerV1 downloadStatusListenerV1) {
        this.mDownloadStatusListenerV1 = downloadStatusListenerV1;
        return this;
    }

    public DownloadRequest setUri(Uri uri) {
        this.mUri = uri;
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(DownloadRequest downloadRequest) {
        Priority priority = getPriority();
        Priority priority2 = downloadRequest.getPriority();
        return priority == priority2 ? this.mDownloadId - downloadRequest.mDownloadId : priority2.ordinal() - priority.ordinal();
    }
}
