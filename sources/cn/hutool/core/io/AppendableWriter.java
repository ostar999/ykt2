package cn.hutool.core.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;

/* loaded from: classes.dex */
public class AppendableWriter extends Writer implements Appendable {
    private final Appendable appendable;
    private boolean closed = false;
    private final boolean flushable;

    public AppendableWriter(Appendable appendable) {
        this.appendable = appendable;
        this.flushable = appendable instanceof Flushable;
    }

    private void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Writer is closed!" + this);
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        flush();
        Appendable appendable = this.appendable;
        if (appendable instanceof Closeable) {
            ((Closeable) appendable).close();
        }
        this.closed = true;
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        checkNotClosed();
        if (this.flushable) {
            ((Flushable) this.appendable).flush();
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) throws IOException {
        checkNotClosed();
        this.appendable.append(CharBuffer.wrap(cArr), i2, i3 + i2);
    }

    @Override // java.io.Writer
    public void write(int i2) throws IOException {
        checkNotClosed();
        this.appendable.append((char) i2);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c3) throws IOException {
        checkNotClosed();
        this.appendable.append(c3);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) throws IOException {
        checkNotClosed();
        this.appendable.append(str, i2, i3 + i2);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i2, int i3) throws IOException {
        checkNotClosed();
        this.appendable.append(charSequence, i2, i3);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        this.appendable.append(str);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) throws IOException {
        checkNotClosed();
        this.appendable.append(charSequence);
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        this.appendable.append(CharBuffer.wrap(cArr));
    }
}
