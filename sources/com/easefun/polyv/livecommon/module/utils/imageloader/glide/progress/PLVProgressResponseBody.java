package com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* loaded from: classes3.dex */
public class PLVProgressResponseBody extends ResponseBody {
    public static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private BufferedSource bufferedSource;
    private InternalProgressListener internalProgressListener;
    private ResponseBody responseBody;
    private String url;

    /* renamed from: com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVProgressResponseBody$1, reason: invalid class name */
    public class AnonymousClass1 extends ForwardingSource {
        long lastTotalBytesRead;
        long totalBytesRead;

        public AnonymousClass1(Source delegate) {
            super(delegate);
        }

        @Override // okio.ForwardingSource, okio.Source
        public long read(@NonNull Buffer sink, long byteCount) throws IOException {
            long j2 = super.read(sink, byteCount);
            this.totalBytesRead += j2 == -1 ? 0L : j2;
            if (PLVProgressResponseBody.this.internalProgressListener != null) {
                long j3 = this.lastTotalBytesRead;
                long j4 = this.totalBytesRead;
                if (j3 != j4) {
                    this.lastTotalBytesRead = j4;
                    PLVProgressResponseBody.mainThreadHandler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.imageloader.glide.progress.PLVProgressResponseBody.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            InternalProgressListener internalProgressListener = PLVProgressResponseBody.this.internalProgressListener;
                            String str = PLVProgressResponseBody.this.url;
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            internalProgressListener.onProgress(str, anonymousClass1.totalBytesRead, PLVProgressResponseBody.this.getContentLength());
                        }
                    });
                }
            }
            return j2;
        }
    }

    public interface InternalProgressListener {
        void onProgress(String url, long bytesRead, long totalBytes);
    }

    public PLVProgressResponseBody(String url, InternalProgressListener internalProgressListener, ResponseBody responseBody) {
        this.url = url;
        this.internalProgressListener = internalProgressListener;
        this.responseBody = responseBody;
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentLength */
    public long getContentLength() {
        return this.responseBody.getContentLength();
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentType */
    public MediaType get$contentType() {
        return this.responseBody.get$contentType();
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: source */
    public BufferedSource getSource() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.getSource()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new AnonymousClass1(source);
    }
}
