package com.thin.downloadmanager;

import android.util.Log;
import com.thin.downloadmanager.DownloadRequestQueue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/* loaded from: classes6.dex */
public class DownloadDispatcher extends Thread {
    public static final String TAG = "ThinDownloadManager";
    private long mContentLength;
    private long mCurrentBytes;
    private DownloadRequestQueue.CallBackDelivery mDelivery;
    private final BlockingQueue<DownloadRequest> mQueue;
    private DownloadRequest mRequest;
    Timer mTimer;
    private volatile boolean mQuit = false;
    public final int BUFFER_SIZE = 4096;
    private int mRedirectionCount = 0;
    public final int MAX_REDIRECTS = 5;
    private final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private final int HTTP_TEMP_REDIRECT = 307;
    boolean shouldAllowRedirects = true;

    public DownloadDispatcher(BlockingQueue<DownloadRequest> blockingQueue, DownloadRequestQueue.CallBackDelivery callBackDelivery) {
        this.mQueue = blockingQueue;
        this.mDelivery = callBackDelivery;
    }

    private void attemptRetryOnTimeOutException() {
        updateDownloadState(128);
        try {
            this.mRequest.getRetryPolicy().retry();
            this.mTimer.schedule(new TimerTask() { // from class: com.thin.downloadmanager.DownloadDispatcher.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() throws Throwable {
                    DownloadDispatcher downloadDispatcher = DownloadDispatcher.this;
                    downloadDispatcher.executeDownload(downloadDispatcher.mRequest.getUri().toString());
                }
            }, r0.getCurrentTimeout());
        } catch (RetryError unused) {
            updateDownloadFailed(1009, "Connection time out after maximum retires attempted");
        }
    }

    private void cleanupDestination() {
        Log.d(TAG, "cleanupDestination() deleting " + this.mRequest.getDestinationURI().getPath());
        File file = new File(this.mRequest.getDestinationURI().getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.net.URL] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.net.HttpURLConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void executeDownload(java.lang.String r7) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thin.downloadmanager.DownloadDispatcher.executeDownload(java.lang.String):void");
    }

    private int readFromResponse(byte[] bArr, InputStream inputStream) {
        try {
            return inputStream.read(bArr);
        } catch (IOException e2) {
            if ("unexpected end of stream".equals(e2.getMessage())) {
                return -1;
            }
            updateDownloadFailed(1004, "IOException: Failed reading response");
            return Integer.MIN_VALUE;
        }
    }

    private int readResponseHeaders(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("Transfer-Encoding");
        this.mContentLength = -1L;
        if (headerField == null) {
            this.mContentLength = getHeaderFieldLong(httpURLConnection, "Content-Length", -1L);
        } else {
            Log.v(TAG, "Ignoring Content-Length since Transfer-Encoding is also defined for Downloaded Id " + this.mRequest.getDownloadId());
        }
        if (this.mContentLength != -1) {
            return 1;
        }
        return (headerField == null || !headerField.equalsIgnoreCase("chunked")) ? -1 : 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005c A[Catch: all -> 0x0062, TRY_ENTER, TryCatch #0 {all -> 0x0062, blocks: (B:31:0x005c, B:35:0x006c, B:36:0x0072), top: B:104:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0093 A[Catch: all -> 0x008d, IOException -> 0x008f, TRY_LEAVE, TryCatch #4 {IOException -> 0x008f, blocks: (B:47:0x0089, B:53:0x0093), top: B:110:0x0089, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ad A[Catch: IOException -> 0x00b1, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x00b1, blocks: (B:66:0x00ad, B:57:0x009c, B:47:0x0089, B:53:0x0093), top: B:106:0x0087, inners: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void transferData(java.net.HttpURLConnection r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thin.downloadmanager.DownloadDispatcher.transferData(java.net.HttpURLConnection):void");
    }

    private boolean writeDataToDestination(byte[] bArr, int i2, OutputStream outputStream) throws IOException {
        try {
            outputStream.write(bArr, 0, i2);
            return true;
        } catch (IOException unused) {
            updateDownloadFailed(1001, "IOException when writing download contents to the destination file");
            return false;
        } catch (Exception unused2) {
            updateDownloadFailed(1001, "Exception when writing download contents to the destination file");
            return false;
        }
    }

    public long getHeaderFieldLong(URLConnection uRLConnection, String str, long j2) {
        try {
            return Long.parseLong(uRLConnection.getHeaderField(str));
        } catch (NumberFormatException unused) {
            return j2;
        }
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004f  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 10
            android.os.Process.setThreadPriority(r0)
            java.util.Timer r0 = new java.util.Timer
            r0.<init>()
            r3.mTimer = r0
        Lc:
            java.util.concurrent.BlockingQueue<com.thin.downloadmanager.DownloadRequest> r0 = r3.mQueue     // Catch: java.lang.InterruptedException -> L47
            java.lang.Object r0 = r0.take()     // Catch: java.lang.InterruptedException -> L47
            com.thin.downloadmanager.DownloadRequest r0 = (com.thin.downloadmanager.DownloadRequest) r0     // Catch: java.lang.InterruptedException -> L47
            r3.mRequest = r0     // Catch: java.lang.InterruptedException -> L47
            r0 = 0
            r3.mRedirectionCount = r0     // Catch: java.lang.InterruptedException -> L47
            java.lang.String r0 = "ThinDownloadManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.InterruptedException -> L47
            r1.<init>()     // Catch: java.lang.InterruptedException -> L47
            java.lang.String r2 = "Download initiated for "
            r1.append(r2)     // Catch: java.lang.InterruptedException -> L47
            com.thin.downloadmanager.DownloadRequest r2 = r3.mRequest     // Catch: java.lang.InterruptedException -> L47
            int r2 = r2.getDownloadId()     // Catch: java.lang.InterruptedException -> L47
            r1.append(r2)     // Catch: java.lang.InterruptedException -> L47
            java.lang.String r1 = r1.toString()     // Catch: java.lang.InterruptedException -> L47
            android.util.Log.v(r0, r1)     // Catch: java.lang.InterruptedException -> L47
            r0 = 2
            r3.updateDownloadState(r0)     // Catch: java.lang.InterruptedException -> L47
            com.thin.downloadmanager.DownloadRequest r0 = r3.mRequest     // Catch: java.lang.InterruptedException -> L47
            android.net.Uri r0 = r0.getUri()     // Catch: java.lang.InterruptedException -> L47
            java.lang.String r0 = r0.toString()     // Catch: java.lang.InterruptedException -> L47
            r3.executeDownload(r0)     // Catch: java.lang.InterruptedException -> L47
            goto Lc
        L47:
            boolean r0 = r3.mQuit
            if (r0 == 0) goto Lc
            com.thin.downloadmanager.DownloadRequest r0 = r3.mRequest
            if (r0 == 0) goto L5e
            r0.finish()
            r0 = 1008(0x3f0, float:1.413E-42)
            java.lang.String r1 = "Download cancelled"
            r3.updateDownloadFailed(r0, r1)
            java.util.Timer r0 = r3.mTimer
            r0.cancel()
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thin.downloadmanager.DownloadDispatcher.run():void");
    }

    public void updateDownloadComplete() {
        this.mDelivery.postDownloadComplete(this.mRequest);
        this.mRequest.setDownloadState(16);
        this.mRequest.finish();
    }

    public void updateDownloadFailed(int i2, String str) {
        this.shouldAllowRedirects = false;
        this.mRequest.setDownloadState(32);
        if (this.mRequest.getDeleteDestinationFileOnFailure()) {
            cleanupDestination();
        }
        this.mDelivery.postDownloadFailed(this.mRequest, i2, str);
        this.mRequest.finish();
    }

    public void updateDownloadProgress(int i2, long j2) {
        this.mDelivery.postProgressUpdate(this.mRequest, this.mContentLength, j2, i2);
    }

    public void updateDownloadState(int i2) {
        this.mRequest.setDownloadState(i2);
    }

    private void transferData(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        this.mCurrentBytes = 0L;
        this.mRequest.setDownloadState(8);
        Log.v(TAG, "Content Length: " + this.mContentLength + " for Download Id " + this.mRequest.getDownloadId());
        while (!this.mRequest.isCancelled()) {
            int fromResponse = readFromResponse(bArr, inputStream);
            long j2 = this.mContentLength;
            if (j2 != -1 && j2 > 0) {
                long j3 = this.mCurrentBytes;
                updateDownloadProgress((int) ((100 * j3) / j2), j3);
            }
            if (fromResponse == -1) {
                updateDownloadComplete();
                return;
            } else {
                if (fromResponse == Integer.MIN_VALUE) {
                    return;
                }
                if (writeDataToDestination(bArr, fromResponse, outputStream)) {
                    this.mCurrentBytes += fromResponse;
                }
            }
        }
        Log.v(TAG, "Stopping the download as Download Request is cancelled for Downloaded Id " + this.mRequest.getDownloadId());
        this.mRequest.finish();
        updateDownloadFailed(1008, "Download cancelled");
    }
}
