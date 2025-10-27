package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/* loaded from: classes9.dex */
public class DeflateDecompressingEntity extends DecompressingEntity {
    public DeflateDecompressingEntity(HttpEntity httpEntity) {
        super(httpEntity);
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public Header getContentEncoding() {
        return null;
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public long getContentLength() {
        return -1L;
    }

    @Override // org.apache.http.client.entity.DecompressingEntity
    public InputStream getDecompressingInputStream(InputStream inputStream) throws DataFormatException, IOException {
        int iInflate;
        byte[] bArr = new byte[6];
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 6);
        int i2 = pushbackInputStream.read(bArr);
        if (i2 == -1) {
            throw new IOException("Unable to read the response");
        }
        byte[] bArr2 = new byte[1];
        Inflater inflater = new Inflater();
        while (true) {
            try {
                iInflate = inflater.inflate(bArr2);
                if (iInflate != 0) {
                    break;
                }
                if (inflater.finished()) {
                    throw new IOException("Unable to read the response");
                }
                if (inflater.needsDictionary()) {
                    break;
                }
                if (inflater.needsInput()) {
                    inflater.setInput(bArr);
                }
            } catch (DataFormatException unused) {
                pushbackInputStream.unread(bArr, 0, i2);
                return new InflaterInputStream(pushbackInputStream, new Inflater(true));
            }
        }
        if (iInflate == -1) {
            throw new IOException("Unable to read the response");
        }
        pushbackInputStream.unread(bArr, 0, i2);
        return new InflaterInputStream(pushbackInputStream);
    }
}
