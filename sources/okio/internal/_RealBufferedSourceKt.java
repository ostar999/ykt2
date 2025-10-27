package okio.internal;

import java.io.EOFException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.Typography;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.Sink;
import okio.Timeout;
import okio._UtilKt;
import okio.buffer;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\u0080\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0080\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\u0080\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\u0080\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\u0080\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\u0080\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\u0080\b¨\u00068"}, d2 = {"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", HttpHeaderValues.BYTES, "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class _RealBufferedSourceKt {
    public static final void commonClose(@NotNull buffer bufferVar) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (bufferVar.closed) {
            return;
        }
        bufferVar.closed = true;
        bufferVar.source.close();
        bufferVar.bufferField.clear();
    }

    public static final boolean commonExhausted(@NotNull buffer bufferVar) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!bufferVar.closed) {
            return bufferVar.bufferField.exhausted() && bufferVar.source.read(bufferVar.bufferField, 8192L) == -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(@NotNull buffer bufferVar, byte b3, long j2, long j3) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (!(0 <= j2 && j2 <= j3)) {
            throw new IllegalArgumentException(("fromIndex=" + j2 + " toIndex=" + j3).toString());
        }
        while (j2 < j3) {
            long jIndexOf = bufferVar.bufferField.indexOf(b3, j2, j3);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = bufferVar.bufferField.size();
            if (size >= j3 || bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
                break;
            }
            j2 = Math.max(j2, size);
        }
        return -1L;
    }

    public static final long commonIndexOfElement(@NotNull buffer bufferVar, @NotNull ByteString targetBytes, long j2) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOfElement = bufferVar.bufferField.indexOfElement(targetBytes, j2);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            long size = bufferVar.bufferField.size();
            if (bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
                return -1L;
            }
            j2 = Math.max(j2, size);
        }
    }

    @NotNull
    public static final BufferedSource commonPeek(@NotNull buffer bufferVar) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        return Okio.buffer(new PeekSource(bufferVar));
    }

    public static final boolean commonRangeEquals(@NotNull buffer bufferVar, long j2, @NotNull ByteString bytes, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (j2 < 0 || i2 < 0 || i3 < 0 || bytes.size() - i2 < i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            long j3 = i4 + j2;
            if (!bufferVar.request(1 + j3) || bufferVar.bufferField.getByte(j3) != bytes.getByte(i2 + i4)) {
                return false;
            }
        }
        return true;
    }

    public static final long commonRead(@NotNull buffer bufferVar, @NotNull Buffer sink, long j2) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (!(j2 >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j2).toString());
        }
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (bufferVar.bufferField.size() == 0 && bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
            return -1L;
        }
        return bufferVar.bufferField.read(sink, Math.min(j2, bufferVar.bufferField.size()));
    }

    public static final long commonReadAll(@NotNull buffer bufferVar, @NotNull Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j2 = 0;
        while (bufferVar.source.read(bufferVar.bufferField, 8192L) != -1) {
            long jCompleteSegmentByteCount = bufferVar.bufferField.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j2 += jCompleteSegmentByteCount;
                sink.write(bufferVar.bufferField, jCompleteSegmentByteCount);
            }
        }
        if (bufferVar.bufferField.size() <= 0) {
            return j2;
        }
        long size = j2 + bufferVar.bufferField.size();
        Buffer buffer = bufferVar.bufferField;
        sink.write(buffer, buffer.size());
        return size;
    }

    public static final byte commonReadByte(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(1L);
        return bufferVar.bufferField.readByte();
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(j2);
        return bufferVar.bufferField.readByteArray(j2);
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(j2);
        return bufferVar.bufferField.readByteString(j2);
    }

    public static final long commonReadDecimalLong(@NotNull buffer bufferVar) throws EOFException {
        byte b3;
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(1L);
        long j2 = 0;
        while (true) {
            long j3 = j2 + 1;
            if (!bufferVar.request(j3)) {
                break;
            }
            b3 = bufferVar.bufferField.getByte(j2);
            if ((b3 < ((byte) 48) || b3 > ((byte) 57)) && !(j2 == 0 && b3 == ((byte) 45))) {
                break;
            }
            j2 = j3;
        }
        if (j2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected a digit or '-' but was 0x");
            String string = Integer.toString(b3, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(16)));
            Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
            sb.append(string);
            throw new NumberFormatException(sb.toString());
        }
        return bufferVar.bufferField.readDecimalLong();
    }

    public static final void commonReadFully(@NotNull buffer bufferVar, @NotNull byte[] sink) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            bufferVar.require(sink.length);
            bufferVar.bufferField.readFully(sink);
        } catch (EOFException e2) {
            int i2 = 0;
            while (bufferVar.bufferField.size() > 0) {
                Buffer buffer = bufferVar.bufferField;
                int i3 = buffer.read(sink, i2, (int) buffer.size());
                if (i3 == -1) {
                    throw new AssertionError();
                }
                i2 += i3;
            }
            throw e2;
        }
    }

    public static final long commonReadHexadecimalUnsignedLong(@NotNull buffer bufferVar) throws EOFException {
        byte b3;
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(1L);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (!bufferVar.request(i3)) {
                break;
            }
            b3 = bufferVar.bufferField.getByte(i2);
            if ((b3 < ((byte) 48) || b3 > ((byte) 57)) && ((b3 < ((byte) 97) || b3 > ((byte) 102)) && (b3 < ((byte) 65) || b3 > ((byte) 70)))) {
                break;
            }
            i2 = i3;
        }
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected leading [0-9a-fA-F] character but was 0x");
            String string = Integer.toString(b3, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(16)));
            Intrinsics.checkNotNullExpressionValue(string, "toString(this, checkRadix(radix))");
            sb.append(string);
            throw new NumberFormatException(sb.toString());
        }
        return bufferVar.bufferField.readHexadecimalUnsignedLong();
    }

    public static final int commonReadInt(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(4L);
        return bufferVar.bufferField.readInt();
    }

    public static final int commonReadIntLe(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(4L);
        return bufferVar.bufferField.readIntLe();
    }

    public static final long commonReadLong(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(8L);
        return bufferVar.bufferField.readLong();
    }

    public static final long commonReadLongLe(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(8L);
        return bufferVar.bufferField.readLongLe();
    }

    public static final short commonReadShort(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(2L);
        return bufferVar.bufferField.readShort();
    }

    public static final short commonReadShortLe(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(2L);
        return bufferVar.bufferField.readShortLe();
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(j2);
        return bufferVar.bufferField.readUtf8(j2);
    }

    public static final int commonReadUtf8CodePoint(@NotNull buffer bufferVar) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.require(1L);
        byte b3 = bufferVar.bufferField.getByte(0L);
        if ((b3 & 224) == 192) {
            bufferVar.require(2L);
        } else if ((b3 & 240) == 224) {
            bufferVar.require(3L);
        } else if ((b3 & 248) == 240) {
            bufferVar.require(4L);
        }
        return bufferVar.bufferField.readUtf8CodePoint();
    }

    @Nullable
    public static final String commonReadUtf8Line(@NotNull buffer bufferVar) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        long jIndexOf = bufferVar.indexOf((byte) 10);
        if (jIndexOf != -1) {
            return _BufferKt.readUtf8Line(bufferVar.bufferField, jIndexOf);
        }
        if (bufferVar.bufferField.size() != 0) {
            return bufferVar.readUtf8(bufferVar.bufferField.size());
        }
        return null;
    }

    @NotNull
    public static final String commonReadUtf8LineStrict(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!(j2 >= 0)) {
            throw new IllegalArgumentException(("limit < 0: " + j2).toString());
        }
        long j3 = j2 == Long.MAX_VALUE ? Long.MAX_VALUE : j2 + 1;
        byte b3 = (byte) 10;
        long jIndexOf = bufferVar.indexOf(b3, 0L, j3);
        if (jIndexOf != -1) {
            return _BufferKt.readUtf8Line(bufferVar.bufferField, jIndexOf);
        }
        if (j3 < Long.MAX_VALUE && bufferVar.request(j3) && bufferVar.bufferField.getByte(j3 - 1) == ((byte) 13) && bufferVar.request(1 + j3) && bufferVar.bufferField.getByte(j3) == b3) {
            return _BufferKt.readUtf8Line(bufferVar.bufferField, j3);
        }
        Buffer buffer = new Buffer();
        Buffer buffer2 = bufferVar.bufferField;
        buffer2.copyTo(buffer, 0L, Math.min(32, buffer2.size()));
        throw new EOFException("\\n not found: limit=" + Math.min(bufferVar.bufferField.size(), j2) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
    }

    public static final boolean commonRequest(@NotNull buffer bufferVar, long j2) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!(j2 >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j2).toString());
        }
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (bufferVar.bufferField.size() < j2) {
            if (bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
                return false;
            }
        }
        return true;
    }

    public static final void commonRequire(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!bufferVar.request(j2)) {
            throw new EOFException();
        }
    }

    public static final int commonSelect(@NotNull buffer bufferVar, @NotNull Options options) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        do {
            int iSelectPrefix = _BufferKt.selectPrefix(bufferVar.bufferField, options, true);
            if (iSelectPrefix != -2) {
                if (iSelectPrefix == -1) {
                    return -1;
                }
                bufferVar.bufferField.skip(options.getByteStrings()[iSelectPrefix].size());
                return iSelectPrefix;
            }
        } while (bufferVar.source.read(bufferVar.bufferField, 8192L) != -1);
        return -1;
    }

    public static final void commonSkip(@NotNull buffer bufferVar, long j2) throws EOFException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (j2 > 0) {
            if (bufferVar.bufferField.size() == 0 && bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j2, bufferVar.bufferField.size());
            bufferVar.bufferField.skip(jMin);
            j2 -= jMin;
        }
    }

    @NotNull
    public static final Timeout commonTimeout(@NotNull buffer bufferVar) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        return bufferVar.source.getTimeout();
    }

    @NotNull
    public static final String commonToString(@NotNull buffer bufferVar) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        return "buffer(" + bufferVar.source + ')';
    }

    @NotNull
    public static final byte[] commonReadByteArray(@NotNull buffer bufferVar) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.bufferField.writeAll(bufferVar.source);
        return bufferVar.bufferField.readByteArray();
    }

    @NotNull
    public static final ByteString commonReadByteString(@NotNull buffer bufferVar) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.bufferField.writeAll(bufferVar.source);
        return bufferVar.bufferField.readByteString();
    }

    @NotNull
    public static final String commonReadUtf8(@NotNull buffer bufferVar) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        bufferVar.bufferField.writeAll(bufferVar.source);
        return bufferVar.bufferField.readUtf8();
    }

    public static final void commonReadFully(@NotNull buffer bufferVar, @NotNull Buffer sink, long j2) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            bufferVar.require(j2);
            bufferVar.bufferField.readFully(sink, j2);
        } catch (EOFException e2) {
            sink.writeAll(bufferVar.bufferField);
            throw e2;
        }
    }

    public static final long commonIndexOf(@NotNull buffer bufferVar, @NotNull ByteString bytes, long j2) throws IOException {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (!(!bufferVar.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOf = bufferVar.bufferField.indexOf(bytes, j2);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = bufferVar.bufferField.size();
            if (bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
                return -1L;
            }
            j2 = Math.max(j2, (size - bytes.size()) + 1);
        }
    }

    public static final int commonRead(@NotNull buffer bufferVar, @NotNull byte[] sink, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bufferVar, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j2 = i3;
        _UtilKt.checkOffsetAndCount(sink.length, i2, j2);
        if (bufferVar.bufferField.size() == 0 && bufferVar.source.read(bufferVar.bufferField, 8192L) == -1) {
            return -1;
        }
        return bufferVar.bufferField.read(sink, i2, (int) Math.min(j2, bufferVar.bufferField.size()));
    }
}
