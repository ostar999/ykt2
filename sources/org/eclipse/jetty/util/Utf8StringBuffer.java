package org.eclipse.jetty.util;

import java.io.IOException;

/* loaded from: classes9.dex */
public class Utf8StringBuffer extends Utf8Appendable {
    final StringBuffer _buffer;

    public Utf8StringBuffer() {
        super(new StringBuffer());
        this._buffer = (StringBuffer) this._appendable;
    }

    public StringBuffer getStringBuffer() throws IOException {
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

    public Utf8StringBuffer(int i2) {
        super(new StringBuffer(i2));
        this._buffer = (StringBuffer) this._appendable;
    }
}
