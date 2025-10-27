package cn.hutool.core.io;

import cn.hutool.core.lang.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class BomReader extends Reader {
    private InputStreamReader reader;

    public BomReader(InputStream inputStream) throws IllegalArgumentException {
        Assert.notNull(inputStream, "InputStream must be not null!", new Object[0]);
        BOMInputStream bOMInputStream = inputStream instanceof BOMInputStream ? (BOMInputStream) inputStream : new BOMInputStream(inputStream);
        try {
            this.reader = new InputStreamReader(bOMInputStream, bOMInputStream.getCharset());
        } catch (UnsupportedEncodingException unused) {
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i2, int i3) throws IOException {
        return this.reader.read(cArr, i2, i3);
    }
}
