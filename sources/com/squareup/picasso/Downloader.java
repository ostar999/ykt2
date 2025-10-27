package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public interface Downloader {

    public static class ResponseException extends IOException {
        public ResponseException(String str) {
            super(str);
        }
    }

    Response load(Uri uri, boolean z2) throws IOException;

    void shutdown();

    public static class Response {
        final Bitmap bitmap;
        final boolean cached;
        final long contentLength;
        final InputStream stream;

        public Response(Bitmap bitmap, boolean z2) {
            if (bitmap == null) {
                throw new IllegalArgumentException("Bitmap may not be null.");
            }
            this.stream = null;
            this.bitmap = bitmap;
            this.cached = z2;
            this.contentLength = -1L;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public long getContentLength() {
            return this.contentLength;
        }

        public InputStream getInputStream() {
            return this.stream;
        }

        @Deprecated
        public Response(InputStream inputStream, boolean z2) {
            this(inputStream, z2, -1L);
        }

        @Deprecated
        public Response(Bitmap bitmap, boolean z2, long j2) {
            this(bitmap, z2);
        }

        public Response(InputStream inputStream, boolean z2, long j2) {
            if (inputStream != null) {
                this.stream = inputStream;
                this.bitmap = null;
                this.cached = z2;
                this.contentLength = j2;
                return;
            }
            throw new IllegalArgumentException("Stream may not be null.");
        }
    }
}
