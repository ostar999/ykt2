package org.eclipse.jetty.io;

import org.eclipse.jetty.io.Buffer;

/* loaded from: classes9.dex */
public class View extends AbstractBuffer {
    Buffer _buffer;

    public static class CaseInsensitive extends View implements Buffer.CaseInsensitve {
        public CaseInsensitive() {
        }

        @Override // org.eclipse.jetty.io.View, org.eclipse.jetty.io.AbstractBuffer
        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Buffer) && ((Buffer) obj).equalsIgnoreCase(this)) || super.equals(obj);
        }

        public CaseInsensitive(Buffer buffer, int i2, int i3, int i4, int i5) {
            super(buffer, i2, i3, i4, i5);
        }

        public CaseInsensitive(Buffer buffer) {
            super(buffer);
        }
    }

    public View(Buffer buffer, int i2, int i3, int i4, int i5) {
        super(2, !buffer.isImmutable());
        this._buffer = buffer.buffer();
        setPutIndex(i4);
        setGetIndex(i3);
        setMarkIndex(i2);
        this._access = i5;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return this._buffer.array();
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public Buffer buffer() {
        return this._buffer.buffer();
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._buffer.capacity();
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void clear() {
        setMarkIndex(-1);
        setGetIndex(0);
        setPutIndex(this._buffer.getIndex());
        setGetIndex(this._buffer.getIndex());
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void compact() {
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer
    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Buffer) && obj.equals(this)) || super.equals(obj);
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public boolean isReadOnly() {
        return this._buffer.isReadOnly();
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public boolean isVolatile() {
        return true;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i2) {
        return this._buffer.peek(i2);
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, Buffer buffer) {
        return this._buffer.poke(i2, buffer);
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer
    public String toString() {
        return this._buffer == null ? "INVALID" : super.toString();
    }

    public void update(Buffer buffer) {
        this._access = 2;
        this._buffer = buffer.buffer();
        setGetIndex(0);
        setPutIndex(buffer.putIndex());
        setGetIndex(buffer.getIndex());
        setMarkIndex(buffer.markIndex());
        this._access = buffer.isReadOnly() ? 1 : 2;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i2, byte[] bArr, int i3, int i4) {
        return this._buffer.peek(i2, bArr, i3, i4);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i2, byte b3) {
        this._buffer.poke(i2, b3);
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public Buffer peek(int i2, int i3) {
        return this._buffer.peek(i2, i3);
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, byte[] bArr, int i3, int i4) {
        return this._buffer.poke(i2, bArr, i3, i4);
    }

    public View(Buffer buffer) {
        super(2, !buffer.isImmutable());
        this._buffer = buffer.buffer();
        setPutIndex(buffer.putIndex());
        setGetIndex(buffer.getIndex());
        setMarkIndex(buffer.markIndex());
        this._access = buffer.isReadOnly() ? 1 : 2;
    }

    public void update(int i2, int i3) {
        int i4 = this._access;
        this._access = 2;
        setGetIndex(0);
        setPutIndex(i3);
        setGetIndex(i2);
        setMarkIndex(-1);
        this._access = i4;
    }

    public View() {
        super(2, true);
    }
}
