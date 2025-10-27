package cn.hutool.core.compress;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

/* loaded from: classes.dex */
public class Deflate implements Closeable {
    private final boolean nowrap;
    private final InputStream source;
    private OutputStream target;

    public Deflate(InputStream inputStream, OutputStream outputStream, boolean z2) {
        this.source = inputStream;
        this.target = outputStream;
        this.nowrap = z2;
    }

    public static Deflate of(InputStream inputStream, OutputStream outputStream, boolean z2) {
        return new Deflate(inputStream, outputStream, z2);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IoUtil.close((Closeable) this.target);
        IoUtil.close((Closeable) this.source);
    }

    public Deflate deflater(int i2) throws IOException, IORuntimeException {
        OutputStream outputStream = this.target;
        DeflaterOutputStream deflaterOutputStream = outputStream instanceof DeflaterOutputStream ? (DeflaterOutputStream) outputStream : new DeflaterOutputStream(this.target, new Deflater(i2, this.nowrap));
        this.target = deflaterOutputStream;
        IoUtil.copy(this.source, deflaterOutputStream);
        try {
            ((DeflaterOutputStream) this.target).finish();
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public OutputStream getTarget() {
        return this.target;
    }

    public Deflate inflater() throws IOException, IORuntimeException {
        OutputStream outputStream = this.target;
        InflaterOutputStream inflaterOutputStream = outputStream instanceof InflaterOutputStream ? (InflaterOutputStream) outputStream : new InflaterOutputStream(this.target, new Inflater(this.nowrap));
        this.target = inflaterOutputStream;
        IoUtil.copy(this.source, inflaterOutputStream);
        try {
            ((InflaterOutputStream) this.target).finish();
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
