package kotlinx.serialization.internal;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lkotlinx/serialization/internal/InternalHexConverter;", "", "()V", "hexCode", "", "hexToInt", "", "ch", "", "parseHexBinary", "", "s", "printHexBinary", "data", "lowerCase", "", "toHexString", "n", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPlatform.common.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Platform.common.kt\nkotlinx/serialization/internal/InternalHexConverter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,177:1\n1#2:178\n*E\n"})
/* loaded from: classes8.dex */
public final class InternalHexConverter {

    @NotNull
    public static final InternalHexConverter INSTANCE = new InternalHexConverter();

    @NotNull
    private static final String hexCode = "0123456789ABCDEF";

    private InternalHexConverter() {
    }

    private final int hexToInt(char ch) {
        if ('0' <= ch && ch < ':') {
            return ch - '0';
        }
        char c3 = 'A';
        if (!('A' <= ch && ch < 'G')) {
            c3 = 'a';
            if (!('a' <= ch && ch < 'g')) {
                return -1;
            }
        }
        return (ch - c3) + 10;
    }

    public static /* synthetic */ String printHexBinary$default(InternalHexConverter internalHexConverter, byte[] bArr, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return internalHexConverter.printHexBinary(bArr, z2);
    }

    @NotNull
    public final byte[] parseHexBinary(@NotNull String s2) {
        Intrinsics.checkNotNullParameter(s2, "s");
        int length = s2.length();
        if (!(length % 2 == 0)) {
            throw new IllegalArgumentException("HexBinary string must be even length".toString());
        }
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            int iHexToInt = hexToInt(s2.charAt(i2));
            int i3 = i2 + 1;
            int iHexToInt2 = hexToInt(s2.charAt(i3));
            if (!((iHexToInt == -1 || iHexToInt2 == -1) ? false : true)) {
                throw new IllegalArgumentException(("Invalid hex chars: " + s2.charAt(i2) + s2.charAt(i3)).toString());
            }
            bArr[i2 / 2] = (byte) ((iHexToInt << 4) + iHexToInt2);
        }
        return bArr;
    }

    @NotNull
    public final String printHexBinary(@NotNull byte[] data, boolean lowerCase) {
        Intrinsics.checkNotNullParameter(data, "data");
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b3 : data) {
            sb.append("0123456789ABCDEF".charAt((b3 >> 4) & 15));
            sb.append("0123456789ABCDEF".charAt(b3 & 15));
        }
        if (!lowerCase) {
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "r.toString()");
            return string;
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "r.toString()");
        String lowerCase2 = string2.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase2;
    }

    @NotNull
    public final String toHexString(int n2) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) (n2 >> (24 - (i2 * 8)));
        }
        String strTrimStart = StringsKt__StringsKt.trimStart(printHexBinary(bArr, true), '0');
        if (!(strTrimStart.length() > 0)) {
            strTrimStart = null;
        }
        return strTrimStart == null ? "0" : strTrimStart;
    }
}
