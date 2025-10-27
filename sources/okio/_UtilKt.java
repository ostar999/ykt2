package okio;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.umeng.analytics.pro.am;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.Buffer;
import okio.internal._ByteStringKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a0\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001H\u0000\u001a \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0015H\u0000\u001a\u0019\u0010\u0017\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0015H\u0080\b\u001a\u0019\u0010\u0017\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0001H\u0080\b\u001a\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\u0000\u001a\u0015\u0010\u001a\u001a\u00020\u0001*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0001H\u0080\f\u001a\u0015\u0010\u001a\u001a\u00020\u0015*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0015H\u0080\f\u001a\u0015\u0010\u001a\u001a\u00020\u0015*\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u0015H\u0080\f\u001a\u0015\u0010\u001d\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u0001H\u0080\f\u001a\u0014\u0010\u0018\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0001H\u0000\u001a\u0014\u0010\u0018\u001a\u00020\u0001*\u00020 2\u0006\u0010!\u001a\u00020\u0001H\u0000\u001a\f\u0010\"\u001a\u00020\u0001*\u00020\u0001H\u0000\u001a\f\u0010\"\u001a\u00020\u0015*\u00020\u0015H\u0000\u001a\f\u0010\"\u001a\u00020#*\u00020#H\u0000\u001a\u0015\u0010$\u001a\u00020\u0015*\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0001H\u0080\f\u001a\u0015\u0010%\u001a\u00020\u0001*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0001H\u0080\f\u001a\u0015\u0010&\u001a\u00020\u0001*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0001H\u0080\f\u001a\f\u0010'\u001a\u00020(*\u00020\u001bH\u0000\u001a\f\u0010'\u001a\u00020(*\u00020\u0001H\u0000\u001a\f\u0010'\u001a\u00020(*\u00020\u0015H\u0000\u001a\u0015\u0010)\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0080\f\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u00020\u00058\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t¨\u0006*"}, d2 = {"DEFAULT__ByteString_size", "", "getDEFAULT__ByteString_size", "()I", "DEFAULT__new_UnsafeCursor", "Lokio/Buffer$UnsafeCursor;", "getDEFAULT__new_UnsafeCursor$annotations", "()V", "getDEFAULT__new_UnsafeCursor", "()Lokio/Buffer$UnsafeCursor;", "arrayRangeEquals", "", am.av, "", "aOffset", "b", "bOffset", "byteCount", "checkOffsetAndCount", "", DatabaseManager.SIZE, "", "offset", "minOf", "resolveDefaultParameter", "unsafeCursor", "and", "", "other", "leftRotate", "bitCount", "sizeParam", "Lokio/ByteString;", "position", "reverseBytes", "", "rightRotate", "shl", "shr", "toHexString", "", "xor", "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class _UtilKt {

    @NotNull
    private static final Buffer.UnsafeCursor DEFAULT__new_UnsafeCursor = new Buffer.UnsafeCursor();
    private static final int DEFAULT__ByteString_size = -1234567890;

    public static final int and(byte b3, int i2) {
        return b3 & i2;
    }

    public static final long and(byte b3, long j2) {
        return b3 & j2;
    }

    public static final long and(int i2, long j2) {
        return i2 & j2;
    }

    public static final boolean arrayRangeEquals(@NotNull byte[] a3, int i2, @NotNull byte[] b3, int i3, int i4) {
        Intrinsics.checkNotNullParameter(a3, "a");
        Intrinsics.checkNotNullParameter(b3, "b");
        for (int i5 = 0; i5 < i4; i5++) {
            if (a3[i5 + i2] != b3[i5 + i3]) {
                return false;
            }
        }
        return true;
    }

    public static final void checkOffsetAndCount(long j2, long j3, long j4) {
        if ((j3 | j4) < 0 || j3 > j2 || j2 - j3 < j4) {
            throw new ArrayIndexOutOfBoundsException("size=" + j2 + " offset=" + j3 + " byteCount=" + j4);
        }
    }

    public static final int getDEFAULT__ByteString_size() {
        return DEFAULT__ByteString_size;
    }

    @NotNull
    public static final Buffer.UnsafeCursor getDEFAULT__new_UnsafeCursor() {
        return DEFAULT__new_UnsafeCursor;
    }

    public static /* synthetic */ void getDEFAULT__new_UnsafeCursor$annotations() {
    }

    public static final int leftRotate(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    public static final long minOf(long j2, int i2) {
        return Math.min(j2, i2);
    }

    @NotNull
    public static final Buffer.UnsafeCursor resolveDefaultParameter(@NotNull Buffer.UnsafeCursor unsafeCursor) {
        Intrinsics.checkNotNullParameter(unsafeCursor, "unsafeCursor");
        return unsafeCursor == DEFAULT__new_UnsafeCursor ? new Buffer.UnsafeCursor() : unsafeCursor;
    }

    public static final int reverseBytes(int i2) {
        return ((i2 & 255) << 24) | (((-16777216) & i2) >>> 24) | ((16711680 & i2) >>> 8) | ((65280 & i2) << 8);
    }

    public static final long reverseBytes(long j2) {
        return ((j2 & 255) << 56) | (((-72057594037927936L) & j2) >>> 56) | ((71776119061217280L & j2) >>> 40) | ((280375465082880L & j2) >>> 24) | ((1095216660480L & j2) >>> 8) | ((4278190080L & j2) << 8) | ((16711680 & j2) << 24) | ((65280 & j2) << 40);
    }

    public static final short reverseBytes(short s2) {
        int i2 = s2 & 65535;
        return (short) (((i2 & 255) << 8) | ((65280 & i2) >>> 8));
    }

    public static final long rightRotate(long j2, int i2) {
        return (j2 << (64 - i2)) | (j2 >>> i2);
    }

    public static final int shl(byte b3, int i2) {
        return b3 << i2;
    }

    public static final int shr(byte b3, int i2) {
        return b3 >> i2;
    }

    @NotNull
    public static final String toHexString(byte b3) {
        return StringsKt__StringsJVMKt.concatToString(new char[]{_ByteStringKt.getHEX_DIGIT_CHARS()[(b3 >> 4) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[b3 & 15]});
    }

    public static final byte xor(byte b3, byte b4) {
        return (byte) (b3 ^ b4);
    }

    public static final long minOf(int i2, long j2) {
        return Math.min(i2, j2);
    }

    public static final int resolveDefaultParameter(@NotNull ByteString byteString, int i2) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return i2 == DEFAULT__ByteString_size ? byteString.size() : i2;
    }

    public static final int resolveDefaultParameter(@NotNull byte[] bArr, int i2) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return i2 == DEFAULT__ByteString_size ? bArr.length : i2;
    }

    @NotNull
    public static final String toHexString(int i2) {
        if (i2 == 0) {
            return "0";
        }
        int i3 = 0;
        char[] cArr = {_ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 28) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 24) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 20) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 16) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 12) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 8) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[(i2 >> 4) & 15], _ByteStringKt.getHEX_DIGIT_CHARS()[i2 & 15]};
        while (i3 < 8 && cArr[i3] == '0') {
            i3++;
        }
        return StringsKt__StringsJVMKt.concatToString(cArr, i3, 8);
    }

    @NotNull
    public static final String toHexString(long j2) {
        if (j2 == 0) {
            return "0";
        }
        int i2 = 0;
        char[] cArr = {_ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 60) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 56) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 52) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 48) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 44) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 40) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 36) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 32) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 28) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 24) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 20) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 16) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 12) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 8) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) ((j2 >> 4) & 15)], _ByteStringKt.getHEX_DIGIT_CHARS()[(int) (j2 & 15)]};
        while (i2 < 16 && cArr[i2] == '0') {
            i2++;
        }
        return StringsKt__StringsJVMKt.concatToString(cArr, i2, 16);
    }
}
