package cn.hutool.core.lang.ansi;

import cn.hutool.core.util.StrUtil;

/* loaded from: classes.dex */
public enum AnsiColor implements AnsiElement {
    DEFAULT(39),
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),
    BRIGHT_BLACK(90),
    BRIGHT_RED(91),
    BRIGHT_GREEN(92),
    BRIGHT_YELLOW(93),
    BRIGHT_BLUE(94),
    BRIGHT_MAGENTA(95),
    BRIGHT_CYAN(96),
    BRIGHT_WHITE(97);

    private final int code;

    AnsiColor(int i2) {
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
