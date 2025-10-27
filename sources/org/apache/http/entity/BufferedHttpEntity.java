package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/* loaded from: classes9.dex */
public class BufferedHttpEntity extends HttpEntityWrapper {
    private final byte[] buffer;

    public BufferedHttpEntity(HttpEntity httpEntity) throws IOException {
        super(httpEntity);
        if (!httpEntity.isRepeatable() || httpEntity.getContentLength() < 0) {
            this.buffer = EntityUtils.toByteArray(httpEntity);
        } else {
            this.buffer = null;
        }
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return this.buffer != null ? new ByteArrayInputStream(this.buffer) : this.wrappedEntity.getContent();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public long getContentLength() {
        return this.buffer != null ? r0.length : this.wrappedEntity.getContentLength();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isChunked() {
        return this.buffer == null && this.wrappedEntity.isChunked();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isStreaming() {
        return this.buffer == null && this.wrappedEntity.isStreaming();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        byte[] bArr = this.buffer;
        if (bArr != null) {
            outputStream.write(bArr);
        } else {
            this.wrappedEntity.writeTo(outputStream);
        }
    }
}
