package cn.hutool.core.io;

import cn.hutool.core.text.StrBuilder;
import java.io.Writer;

/* loaded from: classes.dex */
public final class FastStringWriter extends Writer {
    private final StrBuilder builder;

    public FastStringWriter() {
        this(16);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public String toString() {
        return this.builder.toString();
    }

    @Override // java.io.Writer
    public void write(int i2) {
        this.builder.append((char) i2);
    }

    public FastStringWriter(int i2) {
        this.builder = new StrBuilder(i2 < 0 ? 16 : i2);
    }

    @Override // java.io.Writer
    public void write(String str) {
        this.builder.append((CharSequence) str);
    }

    @Override // java.io.Writer
    public void write(String str, int i2, int i3) {
        this.builder.append((CharSequence) str, i2, i3 + i2);
    }

    @Override // java.io.Writer
    public void write(char[] cArr) {
        this.builder.append(cArr, 0, cArr.length);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) {
        int i4;
        if (i2 < 0 || i2 > cArr.length || i3 < 0 || (i4 = i2 + i3) > cArr.length || i4 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return;
        }
        this.builder.append(cArr, i2, i3);
    }
}
