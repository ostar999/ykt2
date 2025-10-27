package com.plv.foundationsdk.net;

import java.io.IOException;
import java.lang.ref.WeakReference;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public class PLVCountingRequestBody extends RequestBody {
    private CountingSink countingSink;
    private WeakReference<PLVRfProgressListener> mProgressListener;
    private RequestBody mRequestBody;

    public class CountingSink extends ForwardingSink {
        private long bytesWritten;

        public CountingSink(Sink sink) {
            super(sink);
            this.bytesWritten = 0L;
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            super.write(buffer, j2);
            this.bytesWritten += j2;
            if (PLVCountingRequestBody.this.mProgressListener.get() != null) {
                ((PLVRfProgressListener) PLVCountingRequestBody.this.mProgressListener.get()).onProgress(this.bytesWritten, PLVCountingRequestBody.this.contentLength());
            }
        }
    }

    public PLVCountingRequestBody(RequestBody requestBody, WeakReference<PLVRfProgressListener> weakReference) {
        this.mRequestBody = requestBody;
        this.mProgressListener = weakReference;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.mRequestBody.contentLength();
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentType */
    public MediaType getContentType() {
        return this.mRequestBody.getContentType();
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        CountingSink countingSink = new CountingSink(bufferedSink);
        this.countingSink = countingSink;
        BufferedSink bufferedSinkBuffer = Okio.buffer(countingSink);
        this.mRequestBody.writeTo(bufferedSinkBuffer);
        bufferedSinkBuffer.flush();
    }
}
