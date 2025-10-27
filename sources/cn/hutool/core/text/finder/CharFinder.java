package cn.hutool.core.text.finder;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class CharFinder extends TextFinder {
    private static final long serialVersionUID = 1;

    /* renamed from: c, reason: collision with root package name */
    private final char f2586c;
    private final boolean caseInsensitive;

    public CharFinder(char c3) {
        this(c3, false);
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
                if (NumberUtil.equals(this.f2586c, this.text.charAt(i2), this.caseInsensitive)) {
                    return i2;
                }
                i2--;
            }
            return -1;
        }
        while (i2 < validEndIndex) {
            if (NumberUtil.equals(this.f2586c, this.text.charAt(i2), this.caseInsensitive)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public CharFinder(char c3, boolean z2) {
        this.f2586c = c3;
        this.caseInsensitive = z2;
    }
}
