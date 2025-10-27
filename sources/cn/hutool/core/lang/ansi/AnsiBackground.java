package cn.hutool.core.lang.ansi;

import cn.hutool.core.util.StrUtil;

/* loaded from: classes.dex */
public enum AnsiBackground implements AnsiElement {
    DEFAULT(49),
    BLACK(40),
    RED(41),
    GREEN(42),
    YELLOW(43),
    BLUE(44),
    MAGENTA(45),
    CYAN(46),
    WHITE(47),
    BRIGHT_BLACK(100),
    BRIGHT_RED(101),
    BRIGHT_GREEN(102),
    BRIGHT_YELLOW(103),
    BRIGHT_BLUE(104),
    BRIGHT_MAGENTA(105),
    BRIGHT_CYAN(106),
    BRIGHT_WHITE(107);

    private final int code;

    AnsiBackground(int i2) {
        this.code = i2;
    }

    @Override // cn.hutool.core.lang.ansi.AnsiElement
    public int getCode() {
        return this.code;
    }

    @Override // java.lang.Enum, cn.hutool.core.lang.ansi.AnsiElement
    public String toString() {
        return StrUtil.toString(Integer.valueOf(this.code));
    }
}
