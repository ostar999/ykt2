package cn.hutool.core.text.finder;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class StrFinder extends TextFinder {
    private static final long serialVersionUID = 1;
    private final boolean caseInsensitive;
    private final CharSequence strToFind;

    public StrFinder(CharSequence charSequence, boolean z2) throws IllegalArgumentException {
        Assert.notEmpty(charSequence);
        this.strToFind = charSequence;
        this.caseInsensitive = z2;
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int end(int i2) {
        if (i2 < 0) {
            return -1;
        }
        return i2 + this.strToFind.length();
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int start(int i2) throws IllegalArgumentException {
        Assert.notNull(this.text, "Text to find must be not null!", new Object[0]);
        int length = this.strToFind.length();
        if (i2 < 0) {
            i2 = 0;
        }
        int validEndIndex = getValidEndIndex();
        if (this.negative) {
            while (i2 > validEndIndex) {
                if (CharSequenceUtil.isSubEquals(this.text, i2, this.strToFind, 0, length, this.caseInsensitive)) {
                    return i2;
                }
                i2--;
            }
            return -1;
        }
        int i3 = (validEndIndex - length) + 1;
        while (i2 < i3) {
            if (CharSequenceUtil.isSubEquals(this.text, i2, this.strToFind, 0, length, this.caseInsensitive)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }
}
