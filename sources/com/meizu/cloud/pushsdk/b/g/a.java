package com.meizu.cloud.pushsdk.b.g;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaPeriodQueue;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.internal.connection.RealConnection;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes4.dex */
public final class a implements b, c, Cloneable {

    /* renamed from: c, reason: collision with root package name */
    private static final byte[] f9177c = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: a, reason: collision with root package name */
    i f9178a;

    /* renamed from: b, reason: collision with root package name */
    long f9179b;

    public int a(byte[] bArr, int i2, int i3) {
        n.a(bArr.length, i2, i3);
        i iVar = this.f9178a;
        if (iVar == null) {
            return -1;
        }
        int iMin = Math.min(i3, iVar.f9201c - iVar.f9200b);
        System.arraycopy(iVar.f9199a, iVar.f9200b, bArr, i2, iMin);
        int i4 = iVar.f9200b + iMin;
        iVar.f9200b = i4;
        this.f9179b -= iMin;
        if (i4 == iVar.f9201c) {
            this.f9178a = iVar.a();
            j.a(iVar);
        }
        return iMin;
    }

    public long a() {
        return this.f9179b;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public long a(l lVar) throws IOException {
        if (lVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long jB = lVar.b(this, 2048L);
            if (jB == -1) {
                return j2;
            }
            j2 += jB;
        }
    }

    public a a(int i2) {
        int i3;
        int i4;
        if (i2 >= 128) {
            if (i2 < 2048) {
                i4 = (i2 >> 6) | 192;
            } else {
                if (i2 < 65536) {
                    if (i2 >= 55296 && i2 <= 57343) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
                    }
                    i3 = (i2 >> 12) | 224;
                } else {
                    if (i2 > 1114111) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
                    }
                    b((i2 >> 18) | 240);
                    i3 = ((i2 >> 12) & 63) | 128;
                }
                b(i3);
                i4 = ((i2 >> 6) & 63) | 128;
            }
            b(i4);
            i2 = (i2 & 63) | 128;
        }
        b(i2);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public a b(d dVar) {
        if (dVar == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        dVar.a(this);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public a b(String str) {
        return a(str, 0, str.length());
    }

    public a a(String str, int i2, int i3) {
        int i4;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i2);
        }
        if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        }
        if (i3 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        }
        while (i2 < i3) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                i iVarC = c(1);
                byte[] bArr = iVarC.f9199a;
                int i5 = iVarC.f9201c - i2;
                int iMin = Math.min(i3, 2048 - i5);
                int i6 = i2 + 1;
                bArr[i2 + i5] = (byte) cCharAt;
                while (i6 < iMin) {
                    char cCharAt2 = str.charAt(i6);
                    if (cCharAt2 >= 128) {
                        break;
                    }
                    bArr[i6 + i5] = (byte) cCharAt2;
                    i6++;
                }
                int i7 = iVarC.f9201c;
                int i8 = (i5 + i6) - i7;
                iVarC.f9201c = i7 + i8;
                this.f9179b += i8;
                i2 = i6;
            } else {
                if (cCharAt < 2048) {
                    i4 = (cCharAt >> 6) | 192;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    b((cCharAt >> '\f') | 224);
                    i4 = ((cCharAt >> 6) & 63) | 128;
                } else {
                    int i9 = i2 + 1;
                    char cCharAt3 = i9 < i3 ? str.charAt(i9) : (char) 0;
                    if (cCharAt > 56319 || cCharAt3 < 56320 || cCharAt3 > 57343) {
                        b(63);
                        i2 = i9;
                    } else {
                        int i10 = (((cCharAt & 10239) << 10) | (9215 & cCharAt3)) + 65536;
                        b((i10 >> 18) | 240);
                        b(((i10 >> 12) & 63) | 128);
                        b(((i10 >> 6) & 63) | 128);
                        b((i10 & 63) | 128);
                        i2 += 2;
                    }
                }
                b(i4);
                b((cCharAt & '?') | 128);
                i2++;
            }
        }
        return this;
    }

    public String a(long j2, Charset charset) throws EOFException {
        n.a(this.f9179b, 0L, j2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        }
        if (j2 == 0) {
            return "";
        }
        i iVar = this.f9178a;
        int i2 = iVar.f9200b;
        if (i2 + j2 > iVar.f9201c) {
            return new String(a(j2), charset);
        }
        String str = new String(iVar.f9199a, i2, (int) j2, charset);
        int i3 = (int) (iVar.f9200b + j2);
        iVar.f9200b = i3;
        this.f9179b -= j2;
        if (i3 == iVar.f9201c) {
            this.f9178a = iVar.a();
            j.a(iVar);
        }
        return str;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k
    public void a(a aVar, long j2) {
        if (aVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (aVar == this) {
            throw new IllegalArgumentException("source == this");
        }
        n.a(aVar.f9179b, 0L, j2);
        while (j2 > 0) {
            i iVar = aVar.f9178a;
            if (j2 < iVar.f9201c - iVar.f9200b) {
                i iVar2 = this.f9178a;
                i iVar3 = iVar2 != null ? iVar2.f9205g : null;
                if (iVar3 != null && iVar3.f9203e) {
                    if ((iVar3.f9201c + j2) - (iVar3.f9202d ? 0 : iVar3.f9200b) <= 2048) {
                        iVar.a(iVar3, (int) j2);
                        aVar.f9179b -= j2;
                        this.f9179b += j2;
                        return;
                    }
                }
                aVar.f9178a = iVar.a((int) j2);
            }
            i iVar4 = aVar.f9178a;
            long j3 = iVar4.f9201c - iVar4.f9200b;
            aVar.f9178a = iVar4.a();
            i iVar5 = this.f9178a;
            if (iVar5 == null) {
                this.f9178a = iVar4;
                iVar4.f9205g = iVar4;
                iVar4.f9204f = iVar4;
            } else {
                iVar5.f9205g.a(iVar4).b();
            }
            aVar.f9179b -= j3;
            this.f9179b += j3;
            j2 -= j3;
        }
    }

    public void a(byte[] bArr) throws EOFException {
        int i2 = 0;
        while (i2 < bArr.length) {
            int iA = a(bArr, i2, bArr.length - i2);
            if (iA == -1) {
                throw new EOFException();
            }
            i2 += iA;
        }
    }

    public byte[] a(long j2) throws EOFException {
        n.a(this.f9179b, 0L, j2);
        if (j2 <= 2147483647L) {
            byte[] bArr = new byte[(int) j2];
            a(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
    }

    @Override // com.meizu.cloud.pushsdk.b.g.l
    public long b(a aVar, long j2) {
        if (aVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        long j3 = this.f9179b;
        if (j3 == 0) {
            return -1L;
        }
        if (j2 > j3) {
            j2 = j3;
        }
        aVar.a(this, j2);
        return j2;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    public a b() {
        return this;
    }

    public a b(int i2) {
        i iVarC = c(1);
        byte[] bArr = iVarC.f9199a;
        int i3 = iVarC.f9201c;
        iVarC.f9201c = i3 + 1;
        bArr[i3] = (byte) i2;
        this.f9179b++;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public a c(byte[] bArr) {
        if (bArr != null) {
            return c(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public a c(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = i3;
        n.a(bArr.length, i2, j2);
        int i4 = i3 + i2;
        while (i2 < i4) {
            i iVarC = c(1);
            int iMin = Math.min(i4 - i2, 2048 - iVarC.f9201c);
            System.arraycopy(bArr, i2, iVarC.f9199a, iVarC.f9201c, iMin);
            i2 += iMin;
            iVarC.f9201c += iMin;
        }
        this.f9179b += j2;
        return this;
    }

    public void b(long j2) throws EOFException {
        while (j2 > 0) {
            if (this.f9178a == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j2, r0.f9201c - r0.f9200b);
            long j3 = iMin;
            this.f9179b -= j3;
            j2 -= j3;
            i iVar = this.f9178a;
            int i2 = iVar.f9200b + iMin;
            iVar.f9200b = i2;
            if (i2 == iVar.f9201c) {
                this.f9178a = iVar.a();
                j.a(iVar);
            }
        }
    }

    @Override // com.meizu.cloud.pushsdk.b.g.b
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public a e(long j2) {
        boolean z2;
        if (j2 == 0) {
            return b(48);
        }
        int i2 = 1;
        if (j2 < 0) {
            j2 = -j2;
            if (j2 < 0) {
                return b("-9223372036854775808");
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (j2 >= 100000000) {
            i2 = j2 < MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US ? j2 < RealConnection.IDLE_CONNECTION_HEALTHY_NS ? j2 < C.NANOS_PER_SECOND ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        } else if (j2 >= com.heytap.mcssdk.constant.a.f7153q) {
            i2 = j2 < 1000000 ? j2 < 100000 ? 5 : 6 : j2 < 10000000 ? 7 : 8;
        } else if (j2 >= 100) {
            i2 = j2 < 1000 ? 3 : 4;
        } else if (j2 >= 10) {
            i2 = 2;
        }
        if (z2) {
            i2++;
        }
        i iVarC = c(i2);
        byte[] bArr = iVarC.f9199a;
        int i3 = iVarC.f9201c + i2;
        while (j2 != 0) {
            i3--;
            bArr[i3] = f9177c[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z2) {
            bArr[i3 - 1] = 45;
        }
        iVarC.f9201c += i2;
        this.f9179b += i2;
        return this;
    }

    public i c(int i2) {
        if (i2 < 1 || i2 > 2048) {
            throw new IllegalArgumentException();
        }
        i iVar = this.f9178a;
        if (iVar != null) {
            i iVar2 = iVar.f9205g;
            return (iVar2.f9201c + i2 > 2048 || !iVar2.f9203e) ? iVar2.a(j.a()) : iVar2;
        }
        i iVarA = j.a();
        this.f9178a = iVarA;
        iVarA.f9205g = iVarA;
        iVarA.f9204f = iVarA;
        return iVarA;
    }

    public boolean c() {
        return this.f9179b == 0;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.b.g.l
    public void close() {
    }

    public a d(long j2) {
        if (j2 == 0) {
            return b(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j2)) / 4) + 1;
        i iVarC = c(iNumberOfTrailingZeros);
        byte[] bArr = iVarC.f9199a;
        int i2 = iVarC.f9201c;
        for (int i3 = (i2 + iNumberOfTrailingZeros) - 1; i3 >= i2; i3--) {
            bArr[i3] = f9177c[(int) (15 & j2)];
            j2 >>>= 4;
        }
        iVarC.f9201c += iNumberOfTrailingZeros;
        this.f9179b += iNumberOfTrailingZeros;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public InputStream d() {
        return new InputStream() { // from class: com.meizu.cloud.pushsdk.b.g.a.1
            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(a.this.f9179b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() {
                a aVar = a.this;
                if (aVar.f9179b > 0) {
                    return aVar.f() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i2, int i3) {
                return a.this.a(bArr, i2, i3);
            }

            public String toString() {
                return a.this + ".inputStream()";
            }
        };
    }

    public long e() {
        long j2 = this.f9179b;
        if (j2 == 0) {
            return 0L;
        }
        i iVar = this.f9178a.f9205g;
        return (iVar.f9201c >= 2048 || !iVar.f9203e) ? j2 : j2 - (r3 - iVar.f9200b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        long j2 = this.f9179b;
        if (j2 != aVar.f9179b) {
            return false;
        }
        long j3 = 0;
        if (j2 == 0) {
            return true;
        }
        i iVar = this.f9178a;
        i iVar2 = aVar.f9178a;
        int i2 = iVar.f9200b;
        int i3 = iVar2.f9200b;
        while (j3 < this.f9179b) {
            long jMin = Math.min(iVar.f9201c - i2, iVar2.f9201c - i3);
            int i4 = 0;
            while (i4 < jMin) {
                int i5 = i2 + 1;
                int i6 = i3 + 1;
                if (iVar.f9199a[i2] != iVar2.f9199a[i3]) {
                    return false;
                }
                i4++;
                i2 = i5;
                i3 = i6;
            }
            if (i2 == iVar.f9201c) {
                iVar = iVar.f9204f;
                i2 = iVar.f9200b;
            }
            if (i3 == iVar2.f9201c) {
                iVar2 = iVar2.f9204f;
                i3 = iVar2.f9200b;
            }
            j3 += jMin;
        }
        return true;
    }

    public byte f() {
        long j2 = this.f9179b;
        if (j2 == 0) {
            throw new IllegalStateException("size == 0");
        }
        i iVar = this.f9178a;
        int i2 = iVar.f9200b;
        int i3 = iVar.f9201c;
        int i4 = i2 + 1;
        byte b3 = iVar.f9199a[i2];
        this.f9179b = j2 - 1;
        if (i4 == i3) {
            this.f9178a = iVar.a();
            j.a(iVar);
        } else {
            iVar.f9200b = i4;
        }
        return b3;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Flushable
    public void flush() {
    }

    public d g() {
        return new d(i());
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public String h() {
        try {
            return a(this.f9179b, n.f9211a);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public int hashCode() {
        i iVar = this.f9178a;
        if (iVar == null) {
            return 0;
        }
        int i2 = 1;
        do {
            int i3 = iVar.f9201c;
            for (int i4 = iVar.f9200b; i4 < i3; i4++) {
                i2 = (i2 * 31) + iVar.f9199a[i4];
            }
            iVar = iVar.f9204f;
        } while (iVar != this.f9178a);
        return i2;
    }

    @Override // com.meizu.cloud.pushsdk.b.g.c
    public byte[] i() {
        try {
            return a(this.f9179b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public void j() {
        try {
            b(this.f9179b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public a clone() {
        a aVar = new a();
        if (this.f9179b == 0) {
            return aVar;
        }
        i iVar = new i(this.f9178a);
        aVar.f9178a = iVar;
        iVar.f9205g = iVar;
        iVar.f9204f = iVar;
        i iVar2 = this.f9178a;
        while (true) {
            iVar2 = iVar2.f9204f;
            if (iVar2 == this.f9178a) {
                aVar.f9179b = this.f9179b;
                return aVar;
            }
            aVar.f9178a.f9205g.a(new i(iVar2));
        }
    }

    public String toString() throws NoSuchAlgorithmException {
        long j2 = this.f9179b;
        if (j2 == 0) {
            return "Buffer[size=0]";
        }
        if (j2 <= 16) {
            return String.format("Buffer[size=%s data=%s]", Long.valueOf(this.f9179b), clone().g().c());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            i iVar = this.f9178a;
            byte[] bArr = iVar.f9199a;
            int i2 = iVar.f9200b;
            messageDigest.update(bArr, i2, iVar.f9201c - i2);
            i iVar2 = this.f9178a;
            while (true) {
                iVar2 = iVar2.f9204f;
                if (iVar2 == this.f9178a) {
                    return String.format("Buffer[size=%s md5=%s]", Long.valueOf(this.f9179b), d.a(messageDigest.digest()).c());
                }
                byte[] bArr2 = iVar2.f9199a;
                int i3 = iVar2.f9200b;
                messageDigest.update(bArr2, i3, iVar2.f9201c - i3);
            }
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }
}
