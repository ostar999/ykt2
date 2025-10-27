package com.aliyun.vod.qupaiokhttp;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* loaded from: classes2.dex */
class ProgressRequestBody extends RequestBody {
    protected ProgressCallback callback;
    protected CountingSink countingSink;
    protected RequestBody delegate;
    private long previousTime;

    public final class CountingSink extends ForwardingSink {
        private long bytesWritten;
        long contentLength;

        public CountingSink(Sink sink) {
            super(sink);
            this.bytesWritten = 0L;
            this.contentLength = 0L;
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            super.write(buffer, j2);
            if (this.contentLength == 0) {
                this.contentLength = ProgressRequestBody.this.contentLength();
            }
            this.bytesWritten += j2;
            if (ProgressRequestBody.this.callback != null) {
                long jCurrentTimeMillis = (System.currentTimeMillis() - ProgressRequestBody.this.previousTime) / 1000;
                if (jCurrentTimeMillis == 0) {
                    jCurrentTimeMillis++;
                }
                long j3 = this.bytesWritten;
                long j4 = j3 / jCurrentTimeMillis;
                long j5 = this.contentLength;
                ProgressRequestBody.this.callback.updateProgress((int) ((100 * j3) / j5), j4, j3 == j5);
            }
        }
    }

    public ProgressRequestBody(RequestBody requestBody, ProgressCallback progressCallback) {
        this.delegate = requestBody;
        this.callback = progressCallback;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        try {
            return this.delegate.contentLength();
        } catch (IOException e2) {
            e2.printStackTrace();
            return -1L;
        }
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentType */
    public MediaType getContentType() {
        return this.delegate.getContentType();
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        this.previousTime = System.currentTimeMillis();
        CountingSink countingSink = new CountingSink(bufferedSink);
        this.countingSink = countingSink;
        BufferedSink bufferedSinkBuffer = Okio.buffer(countingSink);
        this.delegate.writeTo(bufferedSinkBuffer);
        bufferedSinkBuffer.flush();
    }
}
