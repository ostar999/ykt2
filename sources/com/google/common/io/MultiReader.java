package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes4.dex */
class MultiReader extends Reader {

    @NullableDecl
    private Reader current;
    private final Iterator<? extends CharSource> it;

    public MultiReader(Iterator<? extends CharSource> it) throws IOException {
        this.it = it;
        advance();
    }

    private void advance() throws IOException {
        close();
        if (this.it.hasNext()) {
            this.current = this.it.next().openStream();
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Reader reader = this.current;
        if (reader != null) {
            try {
                reader.close();
            } finally {
                this.current = null;
            }
        }
    }

    @Override // java.io.Reader
    public int read(@NullableDecl char[] cArr, int i2, int i3) throws IOException {
        Reader reader = this.current;
        if (reader == null) {
            return -1;
        }
        int i4 = reader.read(cArr, i2, i3);
        if (i4 != -1) {
            return i4;
        }
        advance();
        return read(cArr, i2, i3);
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        Reader reader = this.current;
        return reader != null && reader.ready();
    }

    @Override // java.io.Reader
    public long skip(long j2) throws IOException {
        Preconditions.checkArgument(j2 >= 0, "n is negative");
        if (j2 > 0) {
            while (true) {
                Reader reader = this.current;
                if (reader == null) {
                    break;
                }
                long jSkip = reader.skip(j2);
                if (jSkip > 0) {
                    return jSkip;
                }
                advance();
            }
        }
        return 0L;
    }
}
