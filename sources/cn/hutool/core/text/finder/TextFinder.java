package cn.hutool.core.text.finder;

import cn.hutool.core.lang.Assert;
import java.io.Serializable;

/* loaded from: classes.dex */
public abstract class TextFinder implements Finder, Serializable {
    private static final long serialVersionUID = 1;
    protected int endIndex = -1;
    protected boolean negative;
    protected CharSequence text;

    public int getValidEndIndex() {
        if (this.negative && -1 == this.endIndex) {
            return -1;
        }
        int i2 = this.endIndex;
        return i2 < 0 ? i2 + this.text.length() + 1 : Math.min(i2, this.text.length());
    }

    @Override // cn.hutool.core.text.finder.Finder
    public /* synthetic */ Finder reset() {
        return a.a(this);
    }

    public TextFinder setEndIndex(int i2) {
        this.endIndex = i2;
        return this;
    }

    public TextFinder setNegative(boolean z2) {
        this.negative = z2;
        return this;
    }

    public TextFinder setText(CharSequence charSequence) {
        this.text = (CharSequence) Assert.notNull(charSequence, "Text must be not null!", new Object[0]);
        return this;
    }
}
