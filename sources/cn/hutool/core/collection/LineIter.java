package cn.hutool.core.collection;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;

/* loaded from: classes.dex */
public class LineIter extends ComputeIter<String> implements IterableIter<String>, Closeable, Serializable {
    private static final long serialVersionUID = 1;
    private final BufferedReader bufferedReader;

    public LineIter(InputStream inputStream, Charset charset) throws IllegalArgumentException {
        this(IoUtil.getReader(inputStream, charset));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.finish();
        IoUtil.close((Closeable) this.bufferedReader);
    }

    public boolean isValidLine(String str) {
        return true;
    }

    @Override // cn.hutool.core.collection.IterableIter, java.lang.Iterable
    public /* synthetic */ Iterator iterator() {
        return t0.a(this);
    }

    public LineIter(Reader reader) throws IllegalArgumentException {
        Assert.notNull(reader, "Reader must not be null", new Object[0]);
        this.bufferedReader = IoUtil.getReader(reader);
    }

    @Override // cn.hutool.core.collection.ComputeIter
    public String computeNext() throws IOException {
        String line;
        do {
            try {
                line = this.bufferedReader.readLine();
                if (line == null) {
                    return null;
                }
            } catch (IOException e2) {
                close();
                throw new IORuntimeException(e2);
            }
        } while (!isValidLine(line));
        return line;
    }
}
