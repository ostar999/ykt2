package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
/* loaded from: classes4.dex */
public enum PublicSuffixType {
    PRIVATE(':', ','),
    REGISTRY('!', '?');

    private final char innerNodeCode;
    private final char leafNodeCode;

    PublicSuffixType(char c3, char c4) {
        this.innerNodeCode = c3;
        this.leafNodeCode = c4;
    }

    public static PublicSuffixType fromCode(char c3) {
        for (PublicSuffixType publicSuffixType : values()) {
            if (publicSuffixType.getInnerNodeCode() == c3 || publicSuffixType.getLeafNodeCode() == c3) {
                return publicSuffixType;
            }
        }
        throw new IllegalArgumentException("No enum corresponding to given code: " + c3);
    }

    public static PublicSuffixType fromIsPrivate(boolean z2) {
        return z2 ? PRIVATE : REGISTRY;
    }

    public char getInnerNodeCode() {
        return this.innerNodeCode;
    }

    public char getLeafNodeCode() {
        return this.leafNodeCode;
    }
}
