package cn.hutool.core.text.finder;

import cn.hutool.core.lang.Assert;

/* loaded from: classes.dex */
public class LengthFinder extends TextFinder {
    private static final long serialVersionUID = 1;
    private final int length;

    public LengthFinder(int i2) throws Throwable {
        Assert.isTrue(i2 > 0, "Length must be great than 0", new Object[0]);
        this.length = i2;
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int end(int i2) {
        return i2;
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int start(int i2) throws IllegalArgumentException {
        Assert.notNull(this.text, "Text to find must be not null!", new Object[0]);
        int validEndIndex = getValidEndIndex();
        if (this.negative) {
            int i3 = i2 - this.length;
            if (i3 > validEndIndex) {
                return i3;
            }
            return -1;
        }
        int i4 = i2 + this.length;
        if (i4 < validEndIndex) {
            return i4;
        }
        return -1;
    }
}
