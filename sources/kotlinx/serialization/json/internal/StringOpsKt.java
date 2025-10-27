package kotlinx.serialization.json.internal;

import com.umeng.analytics.pro.am;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0018\u0010\u0011\u001a\u00020\u0012*\u00060\u0013j\u0002`\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0000\u001a\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\bH\u0000¢\u0006\u0002\u0010\u0018\"\u001c\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"&\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00078\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\f\u0012\u0004\b\t\u0010\u0003\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"ESCAPE_MARKERS", "", "getESCAPE_MARKERS$annotations", "()V", "getESCAPE_MARKERS", "()[B", "ESCAPE_STRINGS", "", "", "getESCAPE_STRINGS$annotations", "getESCAPE_STRINGS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "toHexChar", "", am.aC, "", "printQuoted", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "toBooleanStrictOrNull", "", "(Ljava/lang/String;)Ljava/lang/Boolean;", "kotlinx-serialization-json"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class StringOpsKt {

    @NotNull
    private static final byte[] ESCAPE_MARKERS;

    @NotNull
    private static final String[] ESCAPE_STRINGS;

    static {
        String[] strArr = new String[93];
        for (int i2 = 0; i2 < 32; i2++) {
            strArr[i2] = "\\u" + toHexChar(i2 >> 12) + toHexChar(i2 >> 8) + toHexChar(i2 >> 4) + toHexChar(i2);
        }
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        ESCAPE_STRINGS = strArr;
        byte[] bArr = new byte[93];
        for (int i3 = 0; i3 < 32; i3++) {
            bArr[i3] = 1;
        }
        bArr[34] = 34;
        bArr[92] = 92;
        bArr[9] = 116;
        bArr[8] = 98;
        bArr[10] = 110;
        bArr[13] = 114;
        bArr[12] = 102;
        ESCAPE_MARKERS = bArr;
    }

    @NotNull
    public static final byte[] getESCAPE_MARKERS() {
        return ESCAPE_MARKERS;
    }

    public static /* synthetic */ void getESCAPE_MARKERS$annotations() {
    }

    @NotNull
    public static final String[] getESCAPE_STRINGS() {
        return ESCAPE_STRINGS;
    }

    @PublishedApi
    public static /* synthetic */ void getESCAPE_STRINGS$annotations() {
    }

    public static final void printQuoted(@NotNull StringBuilder sb, @NotNull String value) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        sb.append('\"');
        int length = value.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = value.charAt(i3);
            String[] strArr = ESCAPE_STRINGS;
            if (cCharAt < strArr.length && strArr[cCharAt] != null) {
                sb.append((CharSequence) value, i2, i3);
                sb.append(strArr[cCharAt]);
                i2 = i3 + 1;
            }
        }
        if (i2 != 0) {
            sb.append((CharSequence) value, i2, value.length());
        } else {
            sb.append(value);
        }
        sb.append('\"');
    }

    @Nullable
    public static final Boolean toBooleanStrictOrNull(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt__StringsJVMKt.equals(str, k.a.f27523u, true)) {
            return Boolean.TRUE;
        }
        if (StringsKt__StringsJVMKt.equals(str, k.a.f27524v, true)) {
            return Boolean.FALSE;
        }
        return null;
    }

    private static final char toHexChar(int i2) {
        int i3 = i2 & 15;
        return (char) (i3 < 10 ? i3 + 48 : (i3 - 10) + 97);
    }
}
