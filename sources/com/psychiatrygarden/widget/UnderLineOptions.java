package com.psychiatrygarden.widget;

import androidx.core.internal.view.SupportMenu;
import java.util.List;

/* loaded from: classes6.dex */
public class UnderLineOptions {
    private boolean clickable;
    private CustomClickableSpan clickableSpan;
    private String content;
    private int lineColor;
    private int lineEnd;
    private int lineHeight;
    private int lineStart;
    private int lineStyle;
    private List<float[]> lineXYs;

    public @interface Style {
        public static final int LINE_STYLE_DOTTED = 1;
        public static final int LINE_STYLE_STROKE = 2;
    }

    public UnderLineOptions(int lineStyle, int lineColor, int lineStart, int lineEnd, CustomClickableSpan clickableSpan) {
        this.lineHeight = -1;
        this.content = "";
        this.clickable = false;
        this.lineStyle = lineStyle;
        this.lineColor = lineColor;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.clickableSpan = clickableSpan;
    }

    public CustomClickableSpan getClickableSpan() {
        return this.clickableSpan;
    }

    public String getContent() {
        return this.content;
    }

    public int getLineColor() {
        return this.lineColor;
    }

    public int getLineEnd() {
        return this.lineEnd;
    }

    public int getLineStart() {
        return this.lineStart;
    }

    public int getLineStyle() {
        return this.lineStyle;
    }

    public List<float[]> getLineXYs() {
        return this.lineXYs;
    }

    public void setClickableSpan(CustomClickableSpan clickableSpan) {
        this.clickableSpan = clickableSpan;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLineXYs(List<float[]> lineXYs) {
        this.lineXYs = lineXYs;
    }

    public UnderLineOptions(int lineStart, int lineEnd) {
        this(1, SupportMenu.CATEGORY_MASK, lineStart, lineEnd, null);
    }

    public UnderLineOptions(int lineStart, int lineEnd, CustomClickableSpan clickableSpan) {
        this.lineHeight = -1;
        this.lineStyle = 1;
        this.lineColor = -1;
        this.content = "";
        this.clickable = false;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.clickableSpan = clickableSpan;
    }

    public UnderLineOptions(int lineColor, int lineStart, int lineEnd) {
        this(1, lineColor, lineStart, lineEnd, null);
    }
}
