package okio;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.common.base.Ascii;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\u0080\b\u001a4\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006 "}, d2 = {"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", DatabaseManager.SIZE, "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
@JvmName(name = "Utf8")
/* loaded from: classes9.dex */
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = 65533;
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final boolean isIsoControl(int i2) {
        if (i2 >= 0 && i2 < 32) {
            return true;
        }
        return 127 <= i2 && i2 < 160;
    }

    public static final boolean isUtf8Continuation(byte b3) {
        return (b3 & 192) == 128;
    }

    public static final int process2Utf8Bytes(@NotNull byte[] bArr, int i2, int i3, @NotNull Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i4 = i2 + 1;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i3 <= i4) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b3 = bArr[i2];
        byte b4 = bArr[i4];
        if (!((b4 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        int i5 = (b4 ^ 3968) ^ (b3 << 6);
        if (i5 < 128) {
            yield.invoke(numValueOf);
            return 2;
        }
        yield.invoke(Integer.valueOf(i5));
        return 2;
    }

    public static final int process3Utf8Bytes(@NotNull byte[] bArr, int i2, int i3, @NotNull Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i4 = i2 + 2;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i3 <= i4) {
            yield.invoke(numValueOf);
            int i5 = i2 + 1;
            if (i3 > i5) {
                if ((bArr[i5] & 192) == 128) {
                    return 2;
                }
            }
            return 1;
        }
        byte b3 = bArr[i2];
        byte b4 = bArr[i2 + 1];
        if (!((b4 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b5 = bArr[i4];
        if (!((b5 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 2;
        }
        int i6 = ((b5 ^ (-123008)) ^ (b4 << 6)) ^ (b3 << 12);
        if (i6 < 2048) {
            yield.invoke(numValueOf);
            return 3;
        }
        if (55296 <= i6 && i6 < 57344) {
            z = true;
        }
        if (z) {
            yield.invoke(numValueOf);
            return 3;
        }
        yield.invoke(Integer.valueOf(i6));
        return 3;
    }

    public static final int process4Utf8Bytes(@NotNull byte[] bArr, int i2, int i3, @NotNull Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i4 = i2 + 3;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i3 <= i4) {
            yield.invoke(numValueOf);
            int i5 = i2 + 1;
            if (i3 > i5) {
                if ((bArr[i5] & 192) == 128) {
                    int i6 = i2 + 2;
                    if (i3 > i6) {
                        if ((bArr[i6] & 192) == 128) {
                            return 3;
                        }
                    }
                    return 2;
                }
            }
            return 1;
        }
        byte b3 = bArr[i2];
        byte b4 = bArr[i2 + 1];
        if (!((b4 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b5 = bArr[i2 + 2];
        if (!((b5 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 2;
        }
        byte b6 = bArr[i4];
        if (!((b6 & 192) == 128)) {
            yield.invoke(numValueOf);
            return 3;
        }
        int i7 = (((b6 ^ 3678080) ^ (b5 << 6)) ^ (b4 << 12)) ^ (b3 << Ascii.DC2);
        if (i7 > 1114111) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (55296 <= i7 && i7 < 57344) {
            z = true;
        }
        if (z) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (i7 < 65536) {
            yield.invoke(numValueOf);
            return 4;
        }
        yield.invoke(Integer.valueOf(i7));
        return 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void processUtf16Chars(@org.jetbrains.annotations.NotNull byte[] r16, int r17, int r18, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super java.lang.Character, kotlin.Unit> r19) {
        /*
            Method dump skipped, instructions count: 415
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Utf8.processUtf16Chars(byte[], int, int, kotlin.jvm.functions.Function1):void");
    }

    public static final void processUtf8Bytes(@NotNull String str, int i2, int i3, @NotNull Function1<? super Byte, Unit> yield) {
        int i4;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        while (i2 < i3) {
            char cCharAt = str.charAt(i2);
            if (Intrinsics.compare((int) cCharAt, 128) < 0) {
                yield.invoke(Byte.valueOf((byte) cCharAt));
                i2++;
                while (i2 < i3 && Intrinsics.compare((int) str.charAt(i2), 128) < 0) {
                    yield.invoke(Byte.valueOf((byte) str.charAt(i2)));
                    i2++;
                }
            } else {
                if (Intrinsics.compare((int) cCharAt, 2048) < 0) {
                    yield.invoke(Byte.valueOf((byte) ((cCharAt >> 6) | 192)));
                    yield.invoke(Byte.valueOf((byte) ((cCharAt & '?') | 128)));
                } else {
                    boolean z2 = false;
                    if (55296 <= cCharAt && cCharAt < 57344) {
                        if (Intrinsics.compare((int) cCharAt, 56319) <= 0 && i3 > (i4 = i2 + 1)) {
                            char cCharAt2 = str.charAt(i4);
                            if (56320 <= cCharAt2 && cCharAt2 < 57344) {
                                z2 = true;
                            }
                            if (z2) {
                                int iCharAt = ((cCharAt << '\n') + str.charAt(i4)) - 56613888;
                                yield.invoke(Byte.valueOf((byte) ((iCharAt >> 18) | 240)));
                                yield.invoke(Byte.valueOf((byte) (((iCharAt >> 12) & 63) | 128)));
                                yield.invoke(Byte.valueOf((byte) (((iCharAt >> 6) & 63) | 128)));
                                yield.invoke(Byte.valueOf((byte) ((iCharAt & 63) | 128)));
                                i2 += 2;
                            }
                        }
                        yield.invoke(Byte.valueOf(REPLACEMENT_BYTE));
                    } else {
                        yield.invoke(Byte.valueOf((byte) ((cCharAt >> '\f') | 224)));
                        yield.invoke(Byte.valueOf((byte) (((cCharAt >> 6) & 63) | 128)));
                        yield.invoke(Byte.valueOf((byte) ((cCharAt & '?') | 128)));
                    }
                }
                i2++;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void processUtf8CodePoints(@org.jetbrains.annotations.NotNull byte[] r16, int r17, int r18, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> r19) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Utf8.processUtf8CodePoints(byte[], int, int, kotlin.jvm.functions.Function1):void");
    }

    @JvmOverloads
    @JvmName(name = DatabaseManager.SIZE)
    public static final long size(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, 0, 0, 3, null);
    }

    @JvmOverloads
    @JvmName(name = DatabaseManager.SIZE)
    public static final long size(@NotNull String str, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, i2, 0, 2, null);
    }

    @JvmOverloads
    @JvmName(name = DatabaseManager.SIZE)
    public static final long size(@NotNull String str, int i2, int i3) {
        int i4;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (!(i2 >= 0)) {
            throw new IllegalArgumentException(("beginIndex < 0: " + i2).toString());
        }
        if (!(i3 >= i2)) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + i3 + " < " + i2).toString());
        }
        if (!(i3 <= str.length())) {
            throw new IllegalArgumentException(("endIndex > string.length: " + i3 + " > " + str.length()).toString());
        }
        long j2 = 0;
        while (i2 < i3) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                j2++;
            } else {
                if (cCharAt < 2048) {
                    i4 = 2;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    i4 = 3;
                } else {
                    int i5 = i2 + 1;
                    char cCharAt2 = i5 < i3 ? str.charAt(i5) : (char) 0;
                    if (cCharAt > 56319 || cCharAt2 < 56320 || cCharAt2 > 57343) {
                        j2++;
                        i2 = i5;
                    } else {
                        j2 += 4;
                        i2 += 2;
                    }
                }
                j2 += i4;
            }
            i2++;
        }
        return j2;
    }

    public static /* synthetic */ long size$default(String str, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        return size(str, i2, i3);
    }
}
