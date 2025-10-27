package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\nH\u0000\u001a\u0016\u0010\u000b\u001a\u00020\n*\u00020\u00012\b\b\u0002\u0010\f\u001a\u00020\u0001H\u0000\"\u001c\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001c\u0010\u0006\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\u0005¨\u0006\r"}, d2 = {"BASE64", "", "getBASE64$annotations", "()V", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE$annotations", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class _Base64Kt {

    @NotNull
    private static final byte[] BASE64;

    @NotNull
    private static final byte[] BASE64_URL_SAFE;

    static {
        ByteString.Companion companion = ByteString.INSTANCE;
        BASE64 = companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData();
        BASE64_URL_SAFE = companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData();
    }

    @Nullable
    public static final byte[] decodeBase64ToArray(@NotNull String str) {
        int i2;
        char cCharAt;
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        while (length > 0 && ((cCharAt = str.charAt(length - 1)) == '=' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == ' ' || cCharAt == '\t')) {
            length--;
        }
        int i3 = (int) ((length * 6) / 8);
        byte[] bArr = new byte[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            if (i4 >= length) {
                int i8 = i5 % 4;
                if (i8 == 1) {
                    return null;
                }
                if (i8 == 2) {
                    bArr[i7] = (byte) ((i6 << 12) >> 16);
                    i7++;
                } else if (i8 == 3) {
                    int i9 = i6 << 6;
                    int i10 = i7 + 1;
                    bArr[i7] = (byte) (i9 >> 16);
                    i7 = i10 + 1;
                    bArr[i10] = (byte) (i9 >> 8);
                }
                if (i7 == i3) {
                    return bArr;
                }
                byte[] bArrCopyOf = Arrays.copyOf(bArr, i7);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(this, newSize)");
                return bArrCopyOf;
            }
            char cCharAt2 = str.charAt(i4);
            if ('A' <= cCharAt2 && cCharAt2 < '[') {
                i2 = cCharAt2 - 'A';
            } else {
                if ('a' <= cCharAt2 && cCharAt2 < '{') {
                    i2 = cCharAt2 - 'G';
                } else {
                    if ('0' <= cCharAt2 && cCharAt2 < ':') {
                        i2 = cCharAt2 + 4;
                    } else if (cCharAt2 == '+' || cCharAt2 == '-') {
                        i2 = 62;
                    } else if (cCharAt2 == '/' || cCharAt2 == '_') {
                        i2 = 63;
                    } else {
                        if (cCharAt2 != '\n' && cCharAt2 != '\r' && cCharAt2 != ' ' && cCharAt2 != '\t') {
                            return null;
                        }
                        i4++;
                    }
                }
            }
            i6 = (i6 << 6) | i2;
            i5++;
            if (i5 % 4 == 0) {
                int i11 = i7 + 1;
                bArr[i7] = (byte) (i6 >> 16);
                int i12 = i11 + 1;
                bArr[i11] = (byte) (i6 >> 8);
                bArr[i12] = (byte) i6;
                i7 = i12 + 1;
            }
            i4++;
        }
    }

    @NotNull
    public static final String encodeBase64(@NotNull byte[] bArr, @NotNull byte[] map) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(map, "map");
        byte[] bArr2 = new byte[((bArr.length + 2) / 3) * 4];
        int length = bArr.length - (bArr.length % 3);
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i2 + 1;
            byte b3 = bArr[i2];
            int i5 = i4 + 1;
            byte b4 = bArr[i4];
            int i6 = i5 + 1;
            byte b5 = bArr[i5];
            int i7 = i3 + 1;
            bArr2[i3] = map[(b3 & 255) >> 2];
            int i8 = i7 + 1;
            bArr2[i7] = map[((b3 & 3) << 4) | ((b4 & 255) >> 4)];
            int i9 = i8 + 1;
            bArr2[i8] = map[((b4 & 15) << 2) | ((b5 & 255) >> 6)];
            i3 = i9 + 1;
            bArr2[i9] = map[b5 & Utf8.REPLACEMENT_BYTE];
            i2 = i6;
        }
        int length2 = bArr.length - length;
        if (length2 == 1) {
            byte b6 = bArr[i2];
            int i10 = i3 + 1;
            bArr2[i3] = map[(b6 & 255) >> 2];
            int i11 = i10 + 1;
            bArr2[i10] = map[(b6 & 3) << 4];
            byte b7 = (byte) 61;
            bArr2[i11] = b7;
            bArr2[i11 + 1] = b7;
        } else if (length2 == 2) {
            int i12 = i2 + 1;
            byte b8 = bArr[i2];
            byte b9 = bArr[i12];
            int i13 = i3 + 1;
            bArr2[i3] = map[(b8 & 255) >> 2];
            int i14 = i13 + 1;
            bArr2[i13] = map[((b8 & 3) << 4) | ((b9 & 255) >> 4)];
            bArr2[i14] = map[(b9 & 15) << 2];
            bArr2[i14 + 1] = (byte) 61;
        }
        return _JvmPlatformKt.toUtf8String(bArr2);
    }

    public static /* synthetic */ String encodeBase64$default(byte[] bArr, byte[] bArr2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            bArr2 = BASE64;
        }
        return encodeBase64(bArr, bArr2);
    }

    @NotNull
    public static final byte[] getBASE64() {
        return BASE64;
    }

    public static /* synthetic */ void getBASE64$annotations() {
    }

    @NotNull
    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }

    public static /* synthetic */ void getBASE64_URL_SAFE$annotations() {
    }
}
