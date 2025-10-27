package net.tsz.afinal.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

/* loaded from: classes9.dex */
class MultipartEntity implements HttpEntity {
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    boolean isSetLast = false;
    boolean isSetFirst = false;

    public MultipartEntity() {
        this.boundary = null;
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i2 = 0; i2 < 30; i2++) {
            char[] cArr = MULTIPART_CHARS;
            stringBuffer.append(cArr[random.nextInt(cArr.length)]);
        }
        this.boundary = stringBuffer.toString();
    }

    public void addPart(String str, String str2) throws IOException {
        writeFirstBoundaryIfNeeds();
        try {
            this.out.write(("Content-Disposition: form-data; name=\"" + str + "\"\r\n\r\n").getBytes());
            this.out.write(str2.getBytes());
            this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.apache.http.HttpEntity
    public void consumeContent() throws UnsupportedOperationException, IOException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws UnsupportedOperationException, IOException {
        return new ByteArrayInputStream(this.out.toByteArray());
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentEncoding() {
        return null;
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() throws IOException {
        writeLastBoundaryIfNeeds();
        return this.out.toByteArray().length;
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    @Override // org.apache.http.HttpEntity
    public boolean isChunked() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    public void writeFirstBoundaryIfNeeds() throws IOException {
        if (!this.isSetFirst) {
            try {
                this.out.write(("--" + this.boundary + "\r\n").getBytes());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        this.isSetFirst = true;
    }

    public void writeLastBoundaryIfNeeds() throws IOException {
        if (this.isSetLast) {
            return;
        }
        try {
            this.out.write(("\r\n--" + this.boundary + "--\r\n").getBytes());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        this.isSetLast = true;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.out.toByteArray());
    }

    public void addPart(String str, String str2, InputStream inputStream, boolean z2) throws IOException {
        addPart(str, str2, inputStream, "application/octet-stream", z2);
    }

    public void addPart(String str, String str2, InputStream inputStream, String str3, boolean z2) throws IOException {
        writeFirstBoundaryIfNeeds();
        try {
            try {
                try {
                    this.out.write(("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"\r\n").getBytes());
                    this.out.write(("Content-Type: " + str3 + "\r\n").getBytes());
                    this.out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i2 = inputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            this.out.write(bArr, 0, i2);
                        }
                    }
                    if (!z2) {
                        this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
                    }
                    this.out.flush();
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    inputStream.close();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    public void addPart(String str, File file, boolean z2) throws IOException {
        try {
            addPart(str, file.getName(), new FileInputStream(file), z2);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }
}
