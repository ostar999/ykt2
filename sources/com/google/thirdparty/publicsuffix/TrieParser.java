package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;

@GwtCompatible
/* loaded from: classes4.dex */
final class TrieParser {
    private static final Joiner PREFIX_JOINER = Joiner.on("");

    private static int doParseTrieToBuilder(List<CharSequence> list, CharSequence charSequence, int i2, ImmutableMap.Builder<String, PublicSuffixType> builder) {
        int length = charSequence.length();
        int i3 = i2;
        char cCharAt = 0;
        while (i3 < length && (cCharAt = charSequence.charAt(i3)) != '&' && cCharAt != '?' && cCharAt != '!' && cCharAt != ':' && cCharAt != ',') {
            i3++;
        }
        list.add(0, reverse(charSequence.subSequence(i2, i3)));
        if (cCharAt == '!' || cCharAt == '?' || cCharAt == ':' || cCharAt == ',') {
            String strJoin = PREFIX_JOINER.join(list);
            if (strJoin.length() > 0) {
                builder.put(strJoin, PublicSuffixType.fromCode(cCharAt));
            }
        }
        int iDoParseTrieToBuilder = i3 + 1;
        if (cCharAt != '?' && cCharAt != ',') {
            while (iDoParseTrieToBuilder < length) {
                iDoParseTrieToBuilder += doParseTrieToBuilder(list, charSequence, iDoParseTrieToBuilder, builder);
                if (charSequence.charAt(iDoParseTrieToBuilder) == '?' || charSequence.charAt(iDoParseTrieToBuilder) == ',') {
                    iDoParseTrieToBuilder++;
                    break;
                }
            }
        }
        list.remove(0);
        return iDoParseTrieToBuilder - i2;
    }

    public static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence charSequence) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int length = charSequence.length();
        int iDoParseTrieToBuilder = 0;
        while (iDoParseTrieToBuilder < length) {
            iDoParseTrieToBuilder += doParseTrieToBuilder(Lists.newLinkedList(), charSequence, iDoParseTrieToBuilder, builder);
        }
        return builder.build();
    }

    private static CharSequence reverse(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}
