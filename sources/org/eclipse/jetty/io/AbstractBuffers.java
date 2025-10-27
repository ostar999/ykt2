package org.eclipse.jetty.io;

import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.nio.DirectNIOBuffer;
import org.eclipse.jetty.io.nio.IndirectNIOBuffer;

/* loaded from: classes9.dex */
public abstract class AbstractBuffers implements Buffers {
    protected final int _bufferSize;
    protected final Buffers.Type _bufferType;
    protected final int _headerSize;
    protected final Buffers.Type _headerType;
    protected final Buffers.Type _otherType;

    /* renamed from: org.eclipse.jetty.io.AbstractBuffers$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$io$Buffers$Type;

        static {
            int[] iArr = new int[Buffers.Type.values().length];
            $SwitchMap$org$eclipse$jetty$io$Buffers$Type = iArr;
            try {
                iArr[Buffers.Type.BYTE_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$eclipse$jetty$io$Buffers$Type[Buffers.Type.DIRECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$eclipse$jetty$io$Buffers$Type[Buffers.Type.INDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public AbstractBuffers(Buffers.Type type, int i2, Buffers.Type type2, int i3, Buffers.Type type3) {
        this._headerType = type;
        this._headerSize = i2;
        this._bufferType = type2;
        this._bufferSize = i3;
        this._otherType = type3;
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public int getHeaderSize() {
        return this._headerSize;
    }

    public final boolean isBuffer(Buffer buffer) {
        if (buffer.capacity() != this._bufferSize) {
            return false;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._bufferType.ordinal()];
        if (i2 == 1) {
            return (buffer instanceof ByteArrayBuffer) && !(buffer instanceof IndirectNIOBuffer);
        }
        if (i2 == 2) {
            return buffer instanceof DirectNIOBuffer;
        }
        if (i2 != 3) {
            return false;
        }
        return buffer instanceof IndirectNIOBuffer;
    }

    public final boolean isHeader(Buffer buffer) {
        if (buffer.capacity() != this._headerSize) {
            return false;
        }
        int i2 = AnonymousClass1.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._headerType.ordinal()];
        if (i2 == 1) {
            return (buffer instanceof ByteArrayBuffer) && !(buffer instanceof IndirectNIOBuffer);
        }
        if (i2 == 2) {
            return buffer instanceof DirectNIOBuffer;
        }
        if (i2 != 3) {
            return false;
        }
        return buffer instanceof IndirectNIOBuffer;
    }

    public final Buffer newBuffer() {
        int i2 = AnonymousClass1.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._bufferType.ordinal()];
        if (i2 == 1) {
            return new ByteArrayBuffer(this._bufferSize);
        }
        if (i2 == 2) {
            return new DirectNIOBuffer(this._bufferSize);
        }
        if (i2 == 3) {
            return new IndirectNIOBuffer(this._bufferSize);
        }
        throw new IllegalStateException();
    }

    public final Buffer newHeader() {
        int i2 = AnonymousClass1.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._headerType.ordinal()];
        if (i2 == 1) {
            return new ByteArrayBuffer(this._headerSize);
        }
        if (i2 == 2) {
            return new DirectNIOBuffer(this._headerSize);
        }
        if (i2 == 3) {
            return new IndirectNIOBuffer(this._headerSize);
        }
        throw new IllegalStateException();
    }

    public String toString() {
        return String.format("%s [%d,%d]", getClass().getSimpleName(), Integer.valueOf(this._headerSize), Integer.valueOf(this._bufferSize));
    }

    public final Buffer newBuffer(int i2) {
        int i3 = AnonymousClass1.$SwitchMap$org$eclipse$jetty$io$Buffers$Type[this._otherType.ordinal()];
        if (i3 == 1) {
            return new ByteArrayBuffer(i2);
        }
        if (i3 == 2) {
            return new DirectNIOBuffer(i2);
        }
        if (i3 == 3) {
            return new IndirectNIOBuffer(i2);
        }
        throw new IllegalStateException();
    }
}
