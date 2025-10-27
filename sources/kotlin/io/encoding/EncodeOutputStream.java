package kotlin.io.encoding;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@ExperimentalEncodingApi
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0016J \u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J \u0010\u0016\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0016J \u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lkotlin/io/encoding/EncodeOutputStream;", "Ljava/io/OutputStream;", "output", "base64", "Lkotlin/io/encoding/Base64;", "(Ljava/io/OutputStream;Lkotlin/io/encoding/Base64;)V", "byteBuffer", "", "byteBufferLength", "", "isClosed", "", "lineLength", "symbolBuffer", "checkOpen", "", "close", "copyIntoByteBuffer", SocialConstants.PARAM_SOURCE, "startIndex", "endIndex", "encodeByteBufferIntoOutput", "encodeIntoOutput", Socket.EVENT_FLUSH, "write", "offset", SessionDescription.ATTR_LENGTH, "b", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
final class EncodeOutputStream extends OutputStream {

    @NotNull
    private final Base64 base64;

    @NotNull
    private final byte[] byteBuffer;
    private int byteBufferLength;
    private boolean isClosed;
    private int lineLength;

    @NotNull
    private final OutputStream output;

    @NotNull
    private final byte[] symbolBuffer;

    public EncodeOutputStream(@NotNull OutputStream output, @NotNull Base64 base64) {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(base64, "base64");
        this.output = output;
        this.base64 = base64;
        this.lineLength = base64.getIsMimeScheme() ? 76 : -1;
        this.symbolBuffer = new byte[1024];
        this.byteBuffer = new byte[3];
    }

    private final void checkOpen() throws IOException {
        if (this.isClosed) {
            throw new IOException("The output stream is closed.");
        }
    }

    private final int copyIntoByteBuffer(byte[] source, int startIndex, int endIndex) {
        int iMin = Math.min(3 - this.byteBufferLength, endIndex - startIndex);
        ArraysKt___ArraysJvmKt.copyInto(source, this.byteBuffer, this.byteBufferLength, startIndex, startIndex + iMin);
        int i2 = this.byteBufferLength + iMin;
        this.byteBufferLength = i2;
        if (i2 == 3) {
            encodeByteBufferIntoOutput();
        }
        return iMin;
    }

    private final void encodeByteBufferIntoOutput() {
        if (!(encodeIntoOutput(this.byteBuffer, 0, this.byteBufferLength) == 4)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        this.byteBufferLength = 0;
    }

    private final int encodeIntoOutput(byte[] source, int startIndex, int endIndex) throws IOException {
        int iEncodeIntoByteArray = this.base64.encodeIntoByteArray(source, this.symbolBuffer, 0, startIndex, endIndex);
        if (this.lineLength == 0) {
            this.output.write(Base64.INSTANCE.getMimeLineSeparatorSymbols$kotlin_stdlib());
            this.lineLength = 76;
            if (!(iEncodeIntoByteArray <= 76)) {
                throw new IllegalStateException("Check failed.".toString());
            }
        }
        this.output.write(this.symbolBuffer, 0, iEncodeIntoByteArray);
        this.lineLength -= iEncodeIntoByteArray;
        return iEncodeIntoByteArray;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.isClosed = true;
        if (this.byteBufferLength != 0) {
            encodeByteBufferIntoOutput();
        }
        this.output.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        checkOpen();
        this.output.flush();
    }

    @Override // java.io.OutputStream
    public void write(int b3) throws IOException {
        checkOpen();
        byte[] bArr = this.byteBuffer;
        int i2 = this.byteBufferLength;
        int i3 = i2 + 1;
        this.byteBufferLength = i3;
        bArr[i2] = (byte) b3;
        if (i3 == 3) {
            encodeByteBufferIntoOutput();
        }
    }

    @Override // java.io.OutputStream
    public void write(@NotNull byte[] source, int offset, int length) throws IOException {
        int i2;
        Intrinsics.checkNotNullParameter(source, "source");
        checkOpen();
        if (offset < 0 || length < 0 || (i2 = offset + length) > source.length) {
            throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + ", source size: " + source.length);
        }
        if (length == 0) {
            return;
        }
        int i3 = this.byteBufferLength;
        if (i3 < 3) {
            if (i3 != 0) {
                offset += copyIntoByteBuffer(source, offset, i2);
                if (this.byteBufferLength != 0) {
                    return;
                }
            }
            while (offset + 3 <= i2) {
                int iMin = Math.min((this.base64.getIsMimeScheme() ? this.lineLength : this.symbolBuffer.length) / 4, (i2 - offset) / 3);
                int i4 = (iMin * 3) + offset;
                if (!(encodeIntoOutput(source, offset, i4) == iMin * 4)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                offset = i4;
            }
            ArraysKt___ArraysJvmKt.copyInto(source, this.byteBuffer, 0, offset, i2);
            this.byteBufferLength = i2 - offset;
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }
}
