package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model;

/* loaded from: classes3.dex */
public class PLVPositionData {
    private int mBottom;
    private int mContentBottom;
    private int mContentLeft;
    private int mContentRight;
    private int mContentTop;
    private int mLeft;
    private int mRight;
    private int mTop;

    public int contentHeight() {
        return this.mContentBottom - this.mContentTop;
    }

    public int contentWidth() {
        return this.mContentRight - this.mContentLeft;
    }

    public int getBottom() {
        return this.mBottom;
    }

    public int getContentBottom() {
        return this.mContentBottom;
    }

    public int getContentLeft() {
        return this.mContentLeft;
    }

    public int getContentRight() {
        return this.mContentRight;
    }

    public int getContentTop() {
        return this.mContentTop;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getTop() {
        return this.mTop;
    }

    public int height() {
        return this.mBottom - this.mTop;
    }

    public int horizontalCenter() {
        return this.mLeft + (width() / 2);
    }

    public void setBottom(int bottom) {
        this.mBottom = bottom;
    }

    public void setContentBottom(int contentBottom) {
        this.mContentBottom = contentBottom;
    }

    public void setContentLeft(int contentLeft) {
        this.mContentLeft = contentLeft;
    }

    public void setContentRight(int contentRight) {
        this.mContentRight = contentRight;
    }

    public void setContentTop(int contentTop) {
        this.mContentTop = contentTop;
    }

    public void setLeft(int left) {
        this.mLeft = left;
    }

    public void setRight(int right) {
        this.mRight = right;
    }

    public void setTop(int top2) {
        this.mTop = top2;
    }

    public int verticalCenter() {
        return this.mTop + (height() / 2);
    }

    public int width() {
        return this.mRight - this.mLeft;
    }
}
