package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public abstract class ArrayBasedCharEscaper extends CharEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final char safeMax;
    private final char safeMin;

    public ArrayBasedCharEscaper(Map<Character, String> map, char c3, char c4) {
        this(ArrayBasedEscaperMap.create(map), c3, c4);
    }

    @Override // com.google.common.escape.CharEscaper, com.google.common.escape.Escaper
    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if ((cCharAt < this.replacementsLength && this.replacements[cCharAt] != null) || cCharAt > this.safeMax || cCharAt < this.safeMin) {
                return escapeSlow(str, i2);
            }
        }
        return str;
    }

    public abstract char[] escapeUnsafe(char c3);

    public ArrayBasedCharEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, char c3, char c4) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        char[][] replacementArray = arrayBasedEscaperMap.getReplacementArray();
        this.replacements = replacementArray;
        this.replacementsLength = replacementArray.length;
        if (c4 < c3) {
            c4 = 0;
            c3 = CharCompanionObject.MAX_VALUE;
        }
        this.safeMin = c3;
        this.safeMax = c4;
    }

    @Override // com.google.common.escape.CharEscaper
    public final char[] escape(char c3) {
        char[] cArr;
        if (c3 < this.replacementsLength && (cArr = this.replacements[c3]) != null) {
            return cArr;
        }
        if (c3 < this.safeMin || c3 > this.safeMax) {
            return escapeUnsafe(c3);
        }
        return null;
    }
}
