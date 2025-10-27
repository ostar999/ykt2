package com.ykb.ebook.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u001c\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b\u001a\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b¨\u0006\f"}, d2 = {"addIndentBlank", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "indent", "", "decodeUnicode", "", "str", "formatJson", "jsonStr", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class JsonUtilKt {
    private static final void addIndentBlank(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append('\t');
        }
    }

    @NotNull
    public static final String decodeUnicode(@NotNull String str) {
        int i2;
        Intrinsics.checkNotNullParameter(str, "str");
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + 1;
            char cCharAt = str.charAt(i3);
            if (cCharAt == '\\') {
                i3 = i4 + 1;
                char cCharAt2 = str.charAt(i4);
                if (cCharAt2 == 'u') {
                    int i5 = 0;
                    int i6 = 0;
                    while (i5 < 4) {
                        int i7 = i3 + 1;
                        char cCharAt3 = str.charAt(i3);
                        int i8 = 48;
                        boolean z2 = true;
                        if (((((((((cCharAt3 == '0' || cCharAt3 == '1') || cCharAt3 == '2') || cCharAt3 == '3') || cCharAt3 == '4') || cCharAt3 == '5') || cCharAt3 == '6') || cCharAt3 == '7') || cCharAt3 == '8') || cCharAt3 == '9') {
                            i2 = i6 << 4;
                        } else {
                            i8 = 97;
                            if (!(((((cCharAt3 == 'a' || cCharAt3 == 'b') || cCharAt3 == 'c') || cCharAt3 == 'd') || cCharAt3 == 'e') || cCharAt3 == 'f')) {
                                i8 = 65;
                                if (!((((cCharAt3 == 'A' || cCharAt3 == 'B') || cCharAt3 == 'C') || cCharAt3 == 'D') || cCharAt3 == 'E') && cCharAt3 != 'F') {
                                    z2 = false;
                                }
                                if (!z2) {
                                    throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                                }
                            }
                            i2 = (i6 << 4) + 10;
                        }
                        i6 = (i2 + cCharAt3) - i8;
                        i5++;
                        i3 = i7;
                    }
                    stringBuffer.append((char) i6);
                } else {
                    if (cCharAt2 == 't') {
                        cCharAt2 = '\t';
                    } else if (cCharAt2 == 'r') {
                        cCharAt2 = '\r';
                    } else if (cCharAt2 == 'n') {
                        cCharAt2 = '\n';
                    } else if (cCharAt2 == 'f') {
                        cCharAt2 = '\f';
                    }
                    stringBuffer.append(cCharAt2);
                }
            } else {
                stringBuffer.append(cCharAt);
                i3 = i4;
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "outBuffer.toString()");
        return string;
    }

    @NotNull
    public static final String formatJson(@NotNull String jsonStr) {
        Intrinsics.checkNotNullParameter(jsonStr, "jsonStr");
        if (jsonStr.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = jsonStr.length();
        int i2 = 0;
        char c3 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = jsonStr.charAt(i2);
            if (cCharAt == '{' || cCharAt == '[') {
                sb.append(cCharAt);
                sb.append('\n');
                i3++;
                addIndentBlank(sb, i3);
            } else {
                if (cCharAt == '}' || cCharAt == ']') {
                    sb.append('\n');
                    i3--;
                    addIndentBlank(sb, i3);
                    sb.append(cCharAt);
                } else if (cCharAt == ',') {
                    sb.append(cCharAt);
                    if (c3 != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, i3);
                    }
                } else {
                    sb.append(cCharAt);
                }
            }
            i2++;
            c3 = cCharAt;
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
