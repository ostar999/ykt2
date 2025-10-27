package com.squareup.wire;

import java.io.IOException;
import okio.BufferedSink;
import okio.ByteString;

/* loaded from: classes6.dex */
public final class ProtoWriter {
    private final BufferedSink sink;

    public ProtoWriter(BufferedSink bufferedSink) {
        this.sink = bufferedSink;
    }

    public static int decodeZigZag32(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    public static long decodeZigZag64(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }

    public static int encodeZigZag32(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    public static long encodeZigZag64(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    public static int int32Size(int i2) {
        if (i2 >= 0) {
            return varint32Size(i2);
        }
        return 10;
    }

    private static int makeTag(int i2, FieldEncoding fieldEncoding) {
        return (i2 << 3) | fieldEncoding.value;
    }

    public static int tagSize(int i2) {
        return varint32Size(makeTag(i2, FieldEncoding.VARINT));
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int utf8Length(java.lang.String r7) {
        /*
            int r0 = r7.length()
            r1 = 0
            r2 = r1
        L6:
            if (r1 >= r0) goto L46
            char r3 = r7.charAt(r1)
            r4 = 128(0x80, float:1.8E-43)
            if (r3 >= r4) goto L13
        L10:
            int r2 = r2 + 1
            goto L43
        L13:
            r4 = 2048(0x800, float:2.87E-42)
            if (r3 >= r4) goto L1a
            int r2 = r2 + 2
            goto L43
        L1a:
            r4 = 55296(0xd800, float:7.7486E-41)
            if (r3 < r4) goto L41
            r4 = 57343(0xdfff, float:8.0355E-41)
            if (r3 <= r4) goto L25
            goto L41
        L25:
            r5 = 56319(0xdbff, float:7.892E-41)
            if (r3 > r5) goto L10
            int r3 = r1 + 1
            if (r3 >= r0) goto L10
            char r5 = r7.charAt(r3)
            r6 = 56320(0xdc00, float:7.8921E-41)
            if (r5 < r6) goto L10
            char r5 = r7.charAt(r3)
            if (r5 > r4) goto L10
            int r2 = r2 + 4
            r1 = r3
            goto L43
        L41:
            int r2 = r2 + 3
        L43:
            int r1 = r1 + 1
            goto L6
        L46:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.wire.ProtoWriter.utf8Length(java.lang.String):int");
    }

    public static int varint32Size(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    public static int varint64Size(long j2) {
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (((-16384) & j2) == 0) {
            return 2;
        }
        if (((-2097152) & j2) == 0) {
            return 3;
        }
        if (((-268435456) & j2) == 0) {
            return 4;
        }
        if (((-34359738368L) & j2) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j2) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j2) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j2) == 0) {
            return 8;
        }
        return (j2 & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public void writeBytes(ByteString byteString) throws IOException {
        this.sink.write(byteString);
    }

    public void writeFixed32(int i2) throws IOException {
        this.sink.writeIntLe(i2);
    }

    public void writeFixed64(long j2) throws IOException {
        this.sink.writeLongLe(j2);
    }

    public void writeSignedVarint32(int i2) throws IOException {
        if (i2 >= 0) {
            writeVarint32(i2);
        } else {
            writeVarint64(i2);
        }
    }

    public void writeString(String str) throws IOException {
        this.sink.writeUtf8(str);
    }

    public void writeTag(int i2, FieldEncoding fieldEncoding) throws IOException {
        writeVarint32(makeTag(i2, fieldEncoding));
    }

    public void writeVarint32(int i2) throws IOException {
        while ((i2 & (-128)) != 0) {
            this.sink.writeByte((i2 & 127) | 128);
            i2 >>>= 7;
        }
        this.sink.writeByte(i2);
    }

    public void writeVarint64(long j2) throws IOException {
        while (((-128) & j2) != 0) {
            this.sink.writeByte((((int) j2) & 127) | 128);
            j2 >>>= 7;
        }
        this.sink.writeByte((int) j2);
    }
}
