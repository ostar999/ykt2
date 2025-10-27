package cn.hutool.core.lang.ansi;

import cn.hutool.core.util.StrUtil;

/* loaded from: classes.dex */
public enum AnsiStyle implements AnsiElement {
    NORMAL(0),
    BOLD(1),
    FAINT(2),
    ITALIC(3),
    UNDERLINE(4);

    private final int code;

    AnsiStyle(int i2) {
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
