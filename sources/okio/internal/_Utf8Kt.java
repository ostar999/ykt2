package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class _Utf8Kt {
    @NotNull
    public static final byte[] commonAsUtf8ToByteArray(@NotNull String str) {
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bArr = new byte[str.length() * 4];
        int length = str.length();
        int i4 = 0;
        while (i4 < length) {
            char cCharAt = str.charAt(i4);
            if (Intrinsics.compare((int) cCharAt, 128) >= 0) {
                int length2 = str.length();
                int i5 = i4;
                while (i4 < length2) {
                    char cCharAt2 = str.charAt(i4);
                    if (Intrinsics.compare((int) cCharAt2, 128) < 0) {
                        int i6 = i5 + 1;
                        bArr[i5] = (byte) cCharAt2;
                        i4++;
                        while (true) {
                            i5 = i6;
                            if (i4 >= length2 || Intrinsics.compare((int) str.charAt(i4), 128) >= 0) {
                                break;
                            }
                            i6 = i5 + 1;
                            bArr[i5] = (byte) str.charAt(i4);
                            i4++;
                        }
                    } else {
                        if (Intrinsics.compare((int) cCharAt2, 2048) < 0) {
                            int i7 = i5 + 1;
                            bArr[i5] = (byte) ((cCharAt2 >> 6) | 192);
                            byte b3 = (byte) ((cCharAt2 & '?') | 128);
                            i2 = i7 + 1;
                            bArr[i7] = b3;
                        } else {
                            if (55296 <= cCharAt2 && cCharAt2 < 57344) {
                                if (Intrinsics.compare((int) cCharAt2, 56319) <= 0 && length2 > (i3 = i4 + 1)) {
                                    char cCharAt3 = str.charAt(i3);
                                    if (56320 <= cCharAt3 && cCharAt3 < 57344) {
                                        int iCharAt = ((cCharAt2 << '\n') + str.charAt(i3)) - 56613888;
                                        int i8 = i5 + 1;
                                        bArr[i5] = (byte) ((iCharAt >> 18) | 240);
                                        int i9 = i8 + 1;
                                        bArr[i8] = (byte) (((iCharAt >> 12) & 63) | 128);
                                        int i10 = i9 + 1;
                                        bArr[i9] = (byte) (((iCharAt >> 6) & 63) | 128);
                                        byte b4 = (byte) ((iCharAt & 63) | 128);
                                        i2 = i10 + 1;
                                        bArr[i10] = b4;
                                        i4 += 2;
                                        i5 = i2;
                                    }
                                }
                                i2 = i5 + 1;
                                bArr[i5] = Utf8.REPLACEMENT_BYTE;
                            } else {
                                int i11 = i5 + 1;
                                bArr[i5] = (byte) ((cCharAt2 >> '\f') | 224);
                                int i12 = i11 + 1;
                                bArr[i11] = (byte) (((cCharAt2 >> 6) & 63) | 128);
                                byte b5 = (byte) ((cCharAt2 & '?') | 128);
                                i2 = i12 + 1;
                                bArr[i12] = b5;
                            }
                        }
                        i4++;
                        i5 = i2;
                    }
                }
                byte[] bArrCopyOf = Arrays.copyOf(bArr, i5);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(this, newSize)");
                return bArrCopyOf;
            }
            bArr[i4] = (byte) cCharAt;
            i4++;
        }
        byte[] bArrCopyOf2 = Arrays.copyOf(bArr, str.length());
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf2, "copyOf(this, newSize)");
        return bArrCopyOf2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0092, code lost:
    
        if (((r16[r5] & 192) == 128) == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x010d, code lost:
    
        if (((r16[r5] & 192) == 128) == false) goto L94;
     */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String commonToUtf8String(@org.jetbrains.annotations.NotNull byte[] r16, int r17, int r18) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal._Utf8Kt.commonToUtf8String(byte[], int, int):java.lang.String");
    }

    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = bArr.length;
        }
        return commonToUtf8String(bArr, i2, i3);
    }
}
