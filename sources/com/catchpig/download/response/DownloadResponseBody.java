package com.catchpig.download.response;

import com.catchpig.download.callback.DownloadProgressListener;
import com.tencent.open.SocialConstants;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\f\u001a\u00020\u0007H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rH\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/catchpig/download/response/DownloadResponseBody;", "Lokhttp3/ResponseBody;", "responseBody", "progressListener", "Lcom/catchpig/download/callback/DownloadProgressListener;", "(Lokhttp3/ResponseBody;Lcom/catchpig/download/callback/DownloadProgressListener;)V", "bufferedSource", "Lokio/BufferedSource;", "contentLength", "", "contentType", "Lokhttp3/MediaType;", SocialConstants.PARAM_SOURCE, "Lokio/Source;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DownloadResponseBody extends ResponseBody {

    @Nullable
    private BufferedSource bufferedSource;

    @Nullable
    private final DownloadProgressListener progressListener;

    @NotNull
    private final ResponseBody responseBody;

    public DownloadResponseBody(@NotNull ResponseBody responseBody, @Nullable DownloadProgressListener downloadProgressListener) {
        Intrinsics.checkNotNullParameter(responseBody, "responseBody");
        this.responseBody = responseBody;
        this.progressListener = downloadProgressListener;
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentLength */
    public long getContentLength() {
        return this.responseBody.getContentLength();
    }

    @Override // okhttp3.ResponseBody
    @Nullable
    /* renamed from: contentType */
    public MediaType get$contentType() {
        return this.responseBody.get$contentType();
    }

    @Override // okhttp3.ResponseBody
    @NotNull
    /* renamed from: source */
    public BufferedSource getSource() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.getSource()));
        }
        BufferedSource bufferedSource = this.bufferedSource;
        Intrinsics.checkNotNull(bufferedSource);
        return bufferedSource;
    }

    private final Source source(Source source) {
        return new ForwardingSource(source) { // from class: com.catchpig.download.response.DownloadResponseBody.source.1
            private long totalBytesRead;

            public final long getTotalBytesRead() {
                return this.totalBytesRead;
            }

            @Override // okio.ForwardingSource, okio.Source
            public long read(@NotNull Buffer sink, long byteCount) throws IOException {
                Intrinsics.checkNotNullParameter(sink, "sink");
                long j2 = super.read(sink, byteCount);
                this.totalBytesRead += j2 != -1 ? j2 : 0L;
                DownloadProgressListener downloadProgressListener = this.progressListener;
                if (downloadProgressListener != null) {
                    downloadProgressListener.update(this.totalBytesRead, this.responseBody.getContentLength(), j2 == -1);
                }
                return j2;
            }

            public final void setTotalBytesRead(long j2) {
                this.totalBytesRead = j2;
            }
        };
    }
}
