package org.eclipse.jetty.util;

import java.io.IOException;

/* loaded from: classes9.dex */
public class Utf8StringBuilder extends Utf8Appendable {
    final StringBuilder _buffer;

    public Utf8StringBuilder() {
        super(new StringBuilder());
        this._buffer = (StringBuilder) this._appendable;
    }

    public StringBuilder getStringBuilder() throws IOException {
        checkState();
        return this._buffer;
    }

    @Override // org.eclipse.jetty.util.Utf8Appendable
    public int length() {
        return this._buffer.length();
    }

    @Override // org.eclipse.jetty.util.Utf8Appendable
    public void reset() {
        super.reset();
        this._buffer.setLength(0);
    }

    public String toString() throws IOException {
        checkState();
        return this._buffer.toString();
    }

    public Utf8StringBuilder(int i2) {
        super(new StringBuilder(i2));
        this._buffer = (StringBuilder) this._appendable;
    }
}
