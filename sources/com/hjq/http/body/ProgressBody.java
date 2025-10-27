package com.hjq.http.body;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.lifecycle.HttpLifecycleManager;
import com.hjq.http.listener.OnUpdateListener;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public class ProgressBody extends RequestBody {
    private final LifecycleOwner mLifecycleOwner;
    private final OnUpdateListener<?> mListener;
    private final RequestBody mRequestBody;
    private long mTotalByte;
    private long mUpdateByte;
    private int mUpdateProgress;

    public class WrapperSink extends ForwardingSink {
        public WrapperSink(Sink sink) {
            super(sink);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$write$0() {
            if (ProgressBody.this.mListener != null && HttpLifecycleManager.isLifecycleActive(ProgressBody.this.mLifecycleOwner)) {
                ProgressBody.this.mListener.onByte(ProgressBody.this.mTotalByte, ProgressBody.this.mUpdateByte);
            }
            int progressProgress = EasyUtils.getProgressProgress(ProgressBody.this.mTotalByte, ProgressBody.this.mUpdateByte);
            if (progressProgress != ProgressBody.this.mUpdateProgress) {
                ProgressBody.this.mUpdateProgress = progressProgress;
                if (ProgressBody.this.mListener != null && HttpLifecycleManager.isLifecycleActive(ProgressBody.this.mLifecycleOwner)) {
                    ProgressBody.this.mListener.onProgress(progressProgress);
                }
                EasyLog.print("正在进行上传，总字节：" + ProgressBody.this.mTotalByte + "，已上传：" + ProgressBody.this.mUpdateByte + "，进度：" + progressProgress + "%");
            }
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            super.write(buffer, j2);
            ProgressBody.this.mUpdateByte += j2;
            EasyUtils.post(new Runnable() { // from class: com.hjq.http.body.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f7276c.lambda$write$0();
                }
            });
        }
    }

    public ProgressBody(RequestBody requestBody, LifecycleOwner lifecycleOwner, OnUpdateListener<?> onUpdateListener) {
        this.mRequestBody = requestBody;
        this.mLifecycleOwner = lifecycleOwner;
        this.mListener = onUpdateListener;
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
        this.mTotalByte = contentLength();
        BufferedSink bufferedSinkBuffer = Okio.buffer(new WrapperSink(bufferedSink));
        this.mRequestBody.writeTo(bufferedSinkBuffer);
        bufferedSinkBuffer.flush();
    }
}
