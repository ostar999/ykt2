package com.easefun.polyv.livecommon.module.modules.marquee.model;

/* loaded from: classes3.dex */
public class PLVMarqueeTextVO {
    private String content = "Polyv跑马灯";
    private int fontColor = -16777216;
    private int fontSize = 30;
    private int fontAlpha = 255;
    private boolean isFilter = false;
    private int filterColor = -16777216;
    private float filterAlpha = 255.0f;
    private int filterStrength = 4;
    private int filterBlurX = 2;
    private int filterBlurY = 2;

    public String getContent() {
        return this.content;
    }

    public float getFilterAlpha() {
        return this.filterAlpha;
    }

    public int getFilterBlurX() {
        return this.filterBlurX;
    }

    public int getFilterBlurY() {
        return this.filterBlurY;
    }

    public int getFilterColor() {
        return this.filterColor;
    }

    public int getFilterStrength() {
        return this.filterStrength;
    }

    public int getFontAlpha() {
        return this.fontAlpha;
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public boolean isFilter() {
        return this.isFilter;
    }

    public PLVMarqueeTextVO setContent(String content) {
        this.content = content;
        return this;
    }

    public PLVMarqueeTextVO setFilter(boolean filter) {
        this.isFilter = filter;
        return this;
    }

    public PLVMarqueeTextVO setFilterAlpha(float filterAlpha) {
        this.filterAlpha = filterAlpha;
        return this;
    }

    public PLVMarqueeTextVO setFilterBlurX(int filterBlurX) {
        this.filterBlurX = filterBlurX;
        return this;
    }

    public PLVMarqueeTextVO setFilterBlurY(int filterBlurY) {
        this.filterBlurY = filterBlurY;
        return this;
    }

    public PLVMarqueeTextVO setFilterColor(int filterColor) {
        this.filterColor = filterColor;
        return this;
    }

    public PLVMarqueeTextVO setFilterStrength(int filterStrength) {
        this.filterStrength = filterStrength;
        return this;
    }

    public PLVMarqueeTextVO setFontAlpha(int fontAlpha) {
        this.fontAlpha = fontAlpha;
        return this;
    }

    public PLVMarqueeTextVO setFontColor(int fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public PLVMarqueeTextVO setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
