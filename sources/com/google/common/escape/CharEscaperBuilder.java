package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public final class CharEscaperBuilder {
    private int max = -1;
    private final Map<Character, String> map = new HashMap();

    /* JADX WARN: Multi-variable type inference failed */
    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscape(char c3, String str) {
        this.map.put(Character.valueOf(c3), Preconditions.checkNotNull(str));
        if (c3 > this.max) {
            this.max = c3;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscapes(char[] cArr, String str) {
        Preconditions.checkNotNull(str);
        for (char c3 : cArr) {
            addEscape(c3, str);
        }
        return this;
    }

    public char[][] toArray() {
        char[][] cArr = new char[this.max + 1][];
        for (Map.Entry<Character, String> entry : this.map.entrySet()) {
            cArr[entry.getKey().charValue()] = entry.getValue().toCharArray();
        }
        return cArr;
    }

    public Escaper toEscaper() {
        return new CharArrayDecorator(toArray());
    }

    public static class CharArrayDecorator extends CharEscaper {
        private final int replaceLength;
        private final char[][] replacements;

        public CharArrayDecorator(char[][] cArr) {
            this.replacements = cArr;
            this.replaceLength = cArr.length;
        }

        @Override // com.google.common.escape.CharEscaper, com.google.common.escape.Escaper
        public String escape(String str) {
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                char[][] cArr = this.replacements;
                if (cCharAt < cArr.length && cArr[cCharAt] != null) {
                    return escapeSlow(str, i2);
                }
            }
            return str;
        }

        @Override // com.google.common.escape.CharEscaper
        public char[] escape(char c3) {
            if (c3 < this.replaceLength) {
                return this.replacements[c3];
            }
            return null;
        }
    }
}
