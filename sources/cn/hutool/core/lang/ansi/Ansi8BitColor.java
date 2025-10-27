package cn.hutool.core.lang.ansi;

import cn.hutool.core.lang.Assert;

/* loaded from: classes.dex */
public final class Ansi8BitColor implements AnsiElement {
    private static final String PREFIX_BACK = "48;5;";
    private static final String PREFIX_FORE = "38;5;";
    private final int code;
    private final String prefix;

    private Ansi8BitColor(String str, int i2) throws Throwable {
        Assert.isTrue(i2 >= 0 && i2 <= 255, "Code must be between 0 and 255", new Object[0]);
        this.prefix = str;
        this.code = i2;
    }

    public static Ansi8BitColor background(int i2) {
        return new Ansi8BitColor(PREFIX_BACK, i2);
    }

    public static Ansi8BitColor foreground(int i2) {
        return new Ansi8BitColor(PREFIX_FORE, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Ansi8BitColor.class != obj.getClass()) {
            return false;
        }
        Ansi8BitColor ansi8BitColor = (Ansi8BitColor) obj;
        return this.prefix.equals(ansi8BitColor.prefix) && this.code == ansi8BitColor.code;
    }

    @Override // cn.hutool.core.lang.ansi.AnsiElement
    public int getCode() {
        return this.code;
    }

    public int hashCode() {
        return (this.prefix.hashCode() * 31) + this.code;
    }

    @Override // cn.hutool.core.lang.ansi.AnsiElement
    public String toString() {
        return this.prefix + this.code;
    }
}
