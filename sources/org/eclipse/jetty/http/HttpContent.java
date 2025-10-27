package org.eclipse.jetty.http;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes9.dex */
public interface HttpContent {

    public static class ResourceAsHttpContent implements HttpContent {
        private static final Logger LOG = Log.getLogger((Class<?>) ResourceAsHttpContent.class);
        final Buffer _etag;
        final int _maxBuffer;
        final Buffer _mimeType;
        final Resource _resource;

        public ResourceAsHttpContent(Resource resource, Buffer buffer) {
            this(resource, buffer, -1, false);
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public long getContentLength() {
            return this._resource.length();
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getContentType() {
            return this._mimeType;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getDirectBuffer() {
            return null;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getETag() {
            return this._etag;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getIndirectBuffer() throws IOException {
            InputStream inputStream = null;
            try {
                try {
                    if (this._resource.length() > 0 && this._maxBuffer >= this._resource.length()) {
                        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int) this._resource.length());
                        inputStream = this._resource.getInputStream();
                        byteArrayBuffer.readFrom(inputStream, (int) this._resource.length());
                        return byteArrayBuffer;
                    }
                    return null;
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            LOG.warn("Couldn't close inputStream. Possible file handle leak", e2);
                        }
                    }
                }
            } catch (IOException e3) {
                throw new RuntimeException(e3);
            }
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public InputStream getInputStream() throws IOException {
            return this._resource.getInputStream();
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Buffer getLastModified() {
            return null;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public Resource getResource() {
            return this._resource;
        }

        @Override // org.eclipse.jetty.http.HttpContent
        public void release() {
            this._resource.release();
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, int i2) {
            this(resource, buffer, i2, false);
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, boolean z2) {
            this(resource, buffer, -1, z2);
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, int i2, boolean z2) {
            this._resource = resource;
            this._mimeType = buffer;
            this._maxBuffer = i2;
            this._etag = z2 ? new ByteArrayBuffer(resource.getWeakETag()) : null;
        }
    }

    long getContentLength();

    Buffer getContentType();

    Buffer getDirectBuffer();

    Buffer getETag();

    Buffer getIndirectBuffer();

    InputStream getInputStream() throws IOException;

    Buffer getLastModified();

    Resource getResource();

    void release();
}
