package cn.hutool.core.compress;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class Gzip implements Closeable {
    private InputStream source;
    private OutputStream target;

    public Gzip(InputStream inputStream, OutputStream outputStream) {
        this.source = inputStream;
        this.target = outputStream;
    }

    public static Gzip of(InputStream inputStream, OutputStream outputStream) {
        return new Gzip(inputStream, outputStream);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IoUtil.close((Closeable) this.target);
        IoUtil.close((Closeable) this.source);
    }

    public OutputStream getTarget() {
        return this.target;
    }

    public Gzip gzip() throws IOException, IORuntimeException {
        try {
            OutputStream outputStream = this.target;
            GZIPOutputStream gZIPOutputStream = outputStream instanceof GZIPOutputStream ? (GZIPOutputStream) outputStream : new GZIPOutputStream(this.target);
            this.target = gZIPOutputStream;
            IoUtil.copy(this.source, gZIPOutputStream);
            ((GZIPOutputStream) this.target).finish();
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public Gzip unGzip() throws IORuntimeException {
        try {
            InputStream inputStream = this.source;
            GZIPInputStream gZIPInputStream = inputStream instanceof GZIPInputStream ? (GZIPInputStream) inputStream : new GZIPInputStream(this.source);
            this.source = gZIPInputStream;
            IoUtil.copy(gZIPInputStream, this.target);
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
