package cn.hutool.core.io;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class FastByteArrayOutputStream extends OutputStream {
    private final FastByteBuffer buffer;

    public FastByteArrayOutputStream() {
        this(1024);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public void reset() {
        this.buffer.reset();
    }

    public int size() {
        return this.buffer.size();
    }

    public byte[] toByteArray() {
        return this.buffer.toArray();
    }

    public String toString() {
        return toString(CharsetUtil.defaultCharset());
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.buffer.append(bArr, i2, i3);
    }

    public void writeTo(OutputStream outputStream) throws IOException, IORuntimeException {
        int iIndex = this.buffer.index();
        if (iIndex < 0) {
            return;
        }
        for (int i2 = 0; i2 < iIndex; i2++) {
            try {
                outputStream.write(this.buffer.array(i2));
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
        outputStream.write(this.buffer.array(iIndex), 0, this.buffer.offset());
    }

    public FastByteArrayOutputStream(int i2) {
        this.buffer = new FastByteBuffer(i2);
    }

    public String toString(String str) {
        return toString(CharsetUtil.charset(str));
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.buffer.append((byte) i2);
    }

    public String toString(Charset charset) {
        return new String(toByteArray(), (Charset) ObjectUtil.defaultIfNull(charset, CharsetUtil.defaultCharset()));
    }
}
