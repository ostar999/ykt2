package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ByteBufferOutputStream extends OutputStream {
    private final ByteBuffer mByteBuffer;

    public ByteBufferOutputStream(@NonNull ByteBuffer byteBuffer) {
        this.mByteBuffer = byteBuffer;
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (!this.mByteBuffer.hasRemaining()) {
            throw new EOFException("Output ByteBuffer has no bytes remaining.");
        }
        this.mByteBuffer.put((byte) i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        int i4;
        bArr.getClass();
        if (i2 < 0 || i2 > bArr.length || i3 < 0 || (i4 = i2 + i3) > bArr.length || i4 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return;
        }
        if (this.mByteBuffer.remaining() >= i3) {
            this.mByteBuffer.put(bArr, i2, i3);
            return;
        }
        throw new EOFException("Output ByteBuffer has insufficient bytes remaining.");
    }
}
