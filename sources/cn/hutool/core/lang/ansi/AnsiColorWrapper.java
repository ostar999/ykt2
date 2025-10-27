package cn.hutool.core.lang.ansi;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.ansi.AnsiColors;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.Objects;

/* loaded from: classes.dex */
public class AnsiColorWrapper {
    private final AnsiColors.BitDepth bitDepth;
    private final int code;

    public AnsiColorWrapper(int i2, AnsiColors.BitDepth bitDepth) throws Throwable {
        if (bitDepth == AnsiColors.BitDepth.FOUR) {
            Assert.isTrue((30 <= i2 && i2 <= 37) || (90 <= i2 && i2 <= 97), "The value of 4 bit color only supported [30~37],[90~97].", new Object[0]);
        }
        Assert.isTrue(i2 >= 0 && i2 <= 255, "The value of 8 bit color only supported [0~255].", new Object[0]);
        this.code = i2;
        this.bitDepth = bitDepth;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnsiColorWrapper ansiColorWrapper = (AnsiColorWrapper) obj;
        return this.code == ansiColorWrapper.code && this.bitDepth == ansiColorWrapper.bitDepth;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.code), this.bitDepth);
    }

    public AnsiElement toAnsiElement(ForeOrBack foreOrBack) {
        if (this.bitDepth != AnsiColors.BitDepth.FOUR) {
            return foreOrBack == ForeOrBack.FORE ? Ansi8BitColor.foreground(this.code) : Ansi8BitColor.background(this.code);
        }
        if (foreOrBack == ForeOrBack.FORE) {
            for (AnsiColor ansiColor : AnsiColor.values()) {
                if (ansiColor.getCode() == this.code) {
                    return ansiColor;
                }
            }
            throw new IllegalArgumentException(CharSequenceUtil.format("No matched AnsiColor instance,code={}", Integer.valueOf(this.code)));
        }
        for (AnsiBackground ansiBackground : AnsiBackground.values()) {
            if (ansiBackground.getCode() == this.code + 10) {
                return ansiBackground;
            }
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("No matched AnsiBackground instance,code={}", Integer.valueOf(this.code)));
    }
}
