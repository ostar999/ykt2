package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class InputStreamEntity extends AbstractHttpEntity {
    private static final int BUFFER_SIZE = 2048;
    private final InputStream content;
    private final long length;

    public InputStreamEntity(InputStream inputStream, long j2) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Source input stream may not be null");
        }
        this.content = inputStream;
        this.length = j2;
    }

    @Override // org.apache.http.entity.AbstractHttpEntity, org.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        this.content.close();
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return this.content;
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        return this.length;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return true;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        int i2;
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream inputStream = this.content;
        try {
            byte[] bArr = new byte[2048];
            long j2 = this.length;
            if (j2 < 0) {
                while (true) {
                    int i3 = inputStream.read(bArr);
                    if (i3 == -1) {
                        break;
                    } else {
                        outputStream.write(bArr, 0, i3);
                    }
                }
            } else {
                while (j2 > 0 && (i2 = inputStream.read(bArr, 0, (int) Math.min(2048L, j2))) != -1) {
                    outputStream.write(bArr, 0, i2);
                    j2 -= i2;
                }
            }
        } finally {
            inputStream.close();
        }
    }
}
