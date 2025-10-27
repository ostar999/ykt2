package org.apache.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class FileEntity extends AbstractHttpEntity implements Cloneable {
    protected final File file;

    public FileEntity(File file, String str) {
        if (file == null) {
            throw new IllegalArgumentException("File may not be null");
        }
        this.file = file;
        setContentType(str);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        return this.file.length();
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        FileInputStream fileInputStream = new FileInputStream(this.file);
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 == -1) {
                    outputStream.flush();
                    return;
                }
                outputStream.write(bArr, 0, i2);
            }
        } finally {
            fileInputStream.close();
        }
    }
}
