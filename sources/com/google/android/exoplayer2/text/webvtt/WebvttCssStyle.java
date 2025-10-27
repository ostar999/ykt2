package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.common.base.Ascii;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public final class WebvttCssStyle {
    public static final int FONT_SIZE_UNIT_EM = 2;
    public static final int FONT_SIZE_UNIT_PERCENT = 3;
    public static final int FONT_SIZE_UNIT_PIXEL = 1;
    private static final int OFF = 0;
    private static final int ON = 1;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_BOLD_ITALIC = 3;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_NORMAL = 0;
    public static final int UNSPECIFIED = -1;
    private int backgroundColor;

    @ColorInt
    private int fontColor;
    private float fontSize;
    private String targetId = "";
    private String targetTag = "";
    private Set<String> targetClasses = Collections.emptySet();
    private String targetVoice = "";

    @Nullable
    private String fontFamily = null;
    private boolean hasFontColor = false;
    private boolean hasBackgroundColor = false;
    private int linethrough = -1;
    private int underline = -1;
    private int bold = -1;
    private int italic = -1;
    private int fontSizeUnit = -1;
    private int rubyPosition = -1;
    private boolean combineUpright = false;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface StyleFlags {
    }

    private static int updateScoreForMatch(int i2, String str, @Nullable String str2, int i3) {
        if (str.isEmpty() || i2 == -1) {
            return i2;
        }
        if (str.equals(str2)) {
            return i2 + i3;
        }
        return -1;
    }

    public int getBackgroundColor() {
        if (this.hasBackgroundColor) {
            return this.backgroundColor;
        }
        throw new IllegalStateException("Background color not defined.");
    }

    public boolean getCombineUpright() {
        return this.combineUpright;
    }

    public int getFontColor() {
        if (this.hasFontColor) {
            return this.fontColor;
        }
        throw new IllegalStateException("Font color not defined");
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

    public int getRubyPosition() {
        return this.rubyPosition;
    }

    public int getSpecificityScore(@Nullable String str, @Nullable String str2, Set<String> set, @Nullable String str3) {
        if (this.targetId.isEmpty() && this.targetTag.isEmpty() && this.targetClasses.isEmpty() && this.targetVoice.isEmpty()) {
            return TextUtils.isEmpty(str2) ? 1 : 0;
        }
        int iUpdateScoreForMatch = updateScoreForMatch(updateScoreForMatch(updateScoreForMatch(0, this.targetId, str, 1073741824), this.targetTag, str2, 2), this.targetVoice, str3, 4);
        if (iUpdateScoreForMatch == -1 || !set.containsAll(this.targetClasses)) {
            return 0;
        }
        return iUpdateScoreForMatch + (this.targetClasses.size() * 4);
    }

    public int getStyle() {
        int i2 = this.bold;
        if (i2 == -1 && this.italic == -1) {
            return -1;
        }
        return (i2 == 1 ? 1 : 0) | (this.italic == 1 ? 2 : 0);
    }

    public boolean hasBackgroundColor() {
        return this.hasBackgroundColor;
    }

    public boolean hasFontColor() {
        return this.hasFontColor;
    }

    public boolean isLinethrough() {
        return this.linethrough == 1;
    }

    public boolean isUnderline() {
        return this.underline == 1;
    }

    public WebvttCssStyle setBackgroundColor(int i2) {
        this.backgroundColor = i2;
        this.hasBackgroundColor = true;
        return this;
    }

    public WebvttCssStyle setBold(boolean z2) {
        this.bold = z2 ? 1 : 0;
        return this;
    }

    public WebvttCssStyle setCombineUpright(boolean z2) {
        this.combineUpright = z2;
        return this;
    }

    public WebvttCssStyle setFontColor(int i2) {
        this.fontColor = i2;
        this.hasFontColor = true;
        return this;
    }

    public WebvttCssStyle setFontFamily(@Nullable String str) {
        this.fontFamily = str == null ? null : Ascii.toLowerCase(str);
        return this;
    }

    public WebvttCssStyle setFontSize(float f2) {
        this.fontSize = f2;
        return this;
    }

    public WebvttCssStyle setFontSizeUnit(int i2) {
        this.fontSizeUnit = i2;
        return this;
    }

    public WebvttCssStyle setItalic(boolean z2) {
        this.italic = z2 ? 1 : 0;
        return this;
    }

    public WebvttCssStyle setLinethrough(boolean z2) {
        this.linethrough = z2 ? 1 : 0;
        return this;
    }

    public WebvttCssStyle setRubyPosition(int i2) {
        this.rubyPosition = i2;
        return this;
    }

    public void setTargetClasses(String[] strArr) {
        this.targetClasses = new HashSet(Arrays.asList(strArr));
    }

    public void setTargetId(String str) {
        this.targetId = str;
    }

    public void setTargetTagName(String str) {
        this.targetTag = str;
    }

    public void setTargetVoice(String str) {
        this.targetVoice = str;
    }

    public WebvttCssStyle setUnderline(boolean z2) {
        this.underline = z2 ? 1 : 0;
        return this;
    }
}
