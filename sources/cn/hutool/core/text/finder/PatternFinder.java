package cn.hutool.core.text.finder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class PatternFinder extends TextFinder {
    private static final long serialVersionUID = 1;
    private Matcher matcher;
    private final Pattern pattern;

    public PatternFinder(String str, boolean z2) {
        this(Pattern.compile(str, z2 ? 2 : 0));
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int end(int i2) {
        int iEnd = this.matcher.end();
        int i3 = this.endIndex;
        if (iEnd <= (i3 < 0 ? this.text.length() : Math.min(i3, this.text.length()))) {
            return iEnd;
        }
        return -1;
    }

    @Override // cn.hutool.core.text.finder.TextFinder
    public TextFinder setNegative(boolean z2) {
        throw new UnsupportedOperationException("Negative is invalid for Pattern!");
    }

    @Override // cn.hutool.core.text.finder.TextFinder
    public TextFinder setText(CharSequence charSequence) {
        this.matcher = this.pattern.matcher(charSequence);
        return super.setText(charSequence);
    }

    @Override // cn.hutool.core.text.finder.Finder
    public int start(int i2) {
        if (!this.matcher.find(i2) || this.matcher.end() > getValidEndIndex()) {
            return -1;
        }
        return this.matcher.start();
    }

    public PatternFinder(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override // cn.hutool.core.text.finder.TextFinder, cn.hutool.core.text.finder.Finder
    public PatternFinder reset() {
        this.matcher.reset();
        return this;
    }
}
