package com.psychiatrygarden.widget.glideUtil.progress;

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

/* loaded from: classes6.dex */
public class ProgressResponseBody extends ResponseBody {
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private BufferedSource bufferedSource;
    private InternalProgressListener internalProgressListener;
    private ResponseBody responseBody;
    private String url;

    /* renamed from: com.psychiatrygarden.widget.glideUtil.progress.ProgressResponseBody$1, reason: invalid class name */
    public class AnonymousClass1 extends ForwardingSource {
        long lastTotalBytesRead;
        long totalBytesRead;

        public AnonymousClass1(Source delegate) {
            super(delegate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$read$0() {
            ProgressResponseBody.this.internalProgressListener.onProgress(ProgressResponseBody.this.url, this.totalBytesRead, ProgressResponseBody.this.contentLength());
        }

        @Override // okio.ForwardingSource, okio.Source
        public long read(@NonNull Buffer sink, long byteCount) throws IOException {
            long j2 = super.read(sink, byteCount);
            this.totalBytesRead += j2 == -1 ? 0L : j2;
            if (ProgressResponseBody.this.internalProgressListener != null) {
                long j3 = this.lastTotalBytesRead;
                long j4 = this.totalBytesRead;
                if (j3 != j4) {
                    this.lastTotalBytesRead = j4;
                    ProgressResponseBody.mainThreadHandler.post(new Runnable() { // from class: com.psychiatrygarden.widget.glideUtil.progress.c
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16531c.lambda$read$0();
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

    public ProgressResponseBody(String url, InternalProgressListener internalProgressListener, ResponseBody responseBody) {
        this.url = url;
        this.internalProgressListener = internalProgressListener;
        this.responseBody = responseBody;
    }

    @Override // okhttp3.ResponseBody
    public long contentLength() {
        return this.responseBody.contentLength();
    }

    @Override // okhttp3.ResponseBody
    public MediaType contentType() {
        return this.responseBody.contentType();
    }

    @Override // okhttp3.ResponseBody
    public BufferedSource source() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new AnonymousClass1(source);
    }
}
