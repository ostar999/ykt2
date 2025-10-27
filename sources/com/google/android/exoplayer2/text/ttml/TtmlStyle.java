package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
final class TtmlStyle {
    public static final int FONT_SIZE_UNIT_EM = 2;
    public static final int FONT_SIZE_UNIT_PERCENT = 3;
    public static final int FONT_SIZE_UNIT_PIXEL = 1;
    private static final int OFF = 0;
    private static final int ON = 1;
    public static final int RUBY_TYPE_BASE = 2;
    public static final int RUBY_TYPE_CONTAINER = 1;
    public static final int RUBY_TYPE_DELIMITER = 4;
    public static final int RUBY_TYPE_TEXT = 3;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_BOLD_ITALIC = 3;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_NORMAL = 0;
    public static final int UNSPECIFIED = -1;
    public static final float UNSPECIFIED_SHEAR = Float.MAX_VALUE;
    private int backgroundColor;
    private int fontColor;

    @Nullable
    private String fontFamily;
    private float fontSize;
    private boolean hasBackgroundColor;
    private boolean hasFontColor;

    @Nullable
    private String id;

    @Nullable
    private Layout.Alignment multiRowAlign;

    @Nullable
    private Layout.Alignment textAlign;

    @Nullable
    private TextEmphasis textEmphasis;
    private int linethrough = -1;
    private int underline = -1;
    private int bold = -1;
    private int italic = -1;
    private int fontSizeUnit = -1;
    private int rubyType = -1;
    private int rubyPosition = -1;
    private int textCombine = -1;
    private float shearPercentage = Float.MAX_VALUE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RubyType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface StyleFlags {
    }

    public TtmlStyle chain(@Nullable TtmlStyle ttmlStyle) {
        return inherit(ttmlStyle, true);
    }

    public int getBackgroundColor() {
        if (this.hasBackgroundColor) {
            return this.backgroundColor;
        }
        throw new IllegalStateException("Background color has not been defined.");
    }

    public int getFontColor() {
        if (this.hasFontColor) {
            return this.fontColor;
        }
        throw new IllegalStateException("Font color has not been defined.");
    }

    @Nullable
    public String getFontFamily() {
        return this.fontFamily;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public int getFontSizeUnit() {
        return this.fontSizeUnit;
    }

    @Nullable
    public String getId() {
        return this.id;
    }

    @Nullable
    public Layout.Alignment getMultiRowAlign() {
        return this.multiRowAlign;
    }

    public int getRubyPosition() {
        return this.rubyPosition;
    }

    public int getRubyType() {
        return this.rubyType;
    }

    public float getShearPercentage() {
        return this.shearPercentage;
    }

    public int getStyle() {
        int i2 = this.bold;
        if (i2 == -1 && this.italic == -1) {
            return -1;
        }
        return (i2 == 1 ? 1 : 0) | (this.italic == 1 ? 2 : 0);
    }

    @Nullable
    public Layout.Alignment getTextAlign() {
        return this.textAlign;
    }

    public boolean getTextCombine() {
        return this.textCombine == 1;
    }

    @Nullable
    public TextEmphasis getTextEmphasis() {
        return this.textEmphasis;
    }

    public boolean hasBackgroundColor() {
        return this.hasBackgroundColor;
    }

    public boolean hasFontColor() {
        return this.hasFontColor;
    }

    public TtmlStyle inherit(@Nullable TtmlStyle ttmlStyle) {
        return inherit(ttmlStyle, false);
    }

    public boolean isLinethrough() {
        return this.linethrough == 1;
    }

    public boolean isUnderline() {
        return this.underline == 1;
    }

    public TtmlStyle setBackgroundColor(int i2) {
        this.backgroundColor = i2;
        this.hasBackgroundColor = true;
        return this;
    }

    public TtmlStyle setBold(boolean z2) {
        this.bold = z2 ? 1 : 0;
        return this;
    }

    public TtmlStyle setFontColor(int i2) {
        this.fontColor = i2;
        this.hasFontColor = true;
        return this;
    }

    public TtmlStyle setFontFamily(@Nullable String str) {
        this.fontFamily = str;
        return this;
    }

    public TtmlStyle setFontSize(float f2) {
        this.fontSize = f2;
        return this;
    }

    public TtmlStyle setFontSizeUnit(int i2) {
        this.fontSizeUnit = i2;
        return this;
    }

    public TtmlStyle setId(@Nullable String str) {
        this.id = str;
        return this;
    }

    public TtmlStyle setItalic(boolean z2) {
        this.italic = z2 ? 1 : 0;
        return this;
    }

    public TtmlStyle setLinethrough(boolean z2) {
        this.linethrough = z2 ? 1 : 0;
        return this;
    }

    public TtmlStyle setMultiRowAlign(@Nullable Layout.Alignment alignment) {
        this.multiRowAlign = alignment;
        return this;
    }

    public TtmlStyle setRubyPosition(int i2) {
        this.rubyPosition = i2;
        return this;
    }

    public TtmlStyle setRubyType(int i2) {
        this.rubyType = i2;
        return this;
    }

    public TtmlStyle setShearPercentage(float f2) {
        this.shearPercentage = f2;
        return this;
    }

    public TtmlStyle setTextAlign(@Nullable Layout.Alignment alignment) {
        this.textAlign = alignment;
        return this;
    }

    public TtmlStyle setTextCombine(boolean z2) {
        this.textCombine = z2 ? 1 : 0;
        return this;
    }

    public TtmlStyle setTextEmphasis(@Nullable TextEmphasis textEmphasis) {
        this.textEmphasis = textEmphasis;
        return this;
    }

    public TtmlStyle setUnderline(boolean z2) {
        this.underline = z2 ? 1 : 0;
        return this;
    }

    private TtmlStyle inherit(@Nullable TtmlStyle ttmlStyle, boolean z2) {
        int i2;
        Layout.Alignment alignment;
        Layout.Alignment alignment2;
        String str;
        if (ttmlStyle != null) {
            if (!this.hasFontColor && ttmlStyle.hasFontColor) {
                setFontColor(ttmlStyle.fontColor);
            }
            if (this.bold == -1) {
                this.bold = ttmlStyle.bold;
            }
            if (this.italic == -1) {
                this.italic = ttmlStyle.italic;
            }
            if (this.fontFamily == null && (str = ttmlStyle.fontFamily) != null) {
                this.fontFamily = str;
            }
            if (this.linethrough == -1) {
                this.linethrough = ttmlStyle.linethrough;
            }
            if (this.underline == -1) {
                this.underline = ttmlStyle.underline;
            }
            if (this.rubyPosition == -1) {
                this.rubyPosition = ttmlStyle.rubyPosition;
            }
            if (this.textAlign == null && (alignment2 = ttmlStyle.textAlign) != null) {
                this.textAlign = alignment2;
            }
            if (this.multiRowAlign == null && (alignment = ttmlStyle.multiRowAlign) != null) {
                this.multiRowAlign = alignment;
            }
            if (this.textCombine == -1) {
                this.textCombine = ttmlStyle.textCombine;
            }
            if (this.fontSizeUnit == -1) {
                this.fontSizeUnit = ttmlStyle.fontSizeUnit;
                this.fontSize = ttmlStyle.fontSize;
            }
            if (this.textEmphasis == null) {
                this.textEmphasis = ttmlStyle.textEmphasis;
            }
            if (this.shearPercentage == Float.MAX_VALUE) {
                this.shearPercentage = ttmlStyle.shearPercentage;
            }
            if (z2 && !this.hasBackgroundColor && ttmlStyle.hasBackgroundColor) {
                setBackgroundColor(ttmlStyle.backgroundColor);
            }
            if (z2 && this.rubyType == -1 && (i2 = ttmlStyle.rubyType) != -1) {
                this.rubyType = i2;
            }
        }
        return this;
    }
}
