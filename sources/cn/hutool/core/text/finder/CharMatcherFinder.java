package cn.hutool.core.text.finder;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Matcher;

/* loaded from: classes.dex */
public class CharMatcherFinder extends TextFinder {
    private static final long serialVersionUID = 1;
    private final Matcher<Character> matcher;

    public CharMatcherFinder(Matcher<Character> matcher) {
        this.matcher = matcher;
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int end(int i2) {
        if (i2 < 0) {
            return -1;
        }
        return i2 + 1;
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int start(int i2) throws IllegalArgumentException {
        Assert.notNull(this.text, "Text to find must be not null!", new Object[0]);
        int validEndIndex = getValidEndIndex();
        if (this.negative) {
            while (i2 > validEndIndex) {
                if (this.matcher.match(Character.valueOf(this.text.charAt(i2)))) {
                    return i2;
                }
                i2--;
            }
            return -1;
        }
        while (i2 < validEndIndex) {
            if (this.matcher.match(Character.valueOf(this.text.charAt(i2)))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }
}
