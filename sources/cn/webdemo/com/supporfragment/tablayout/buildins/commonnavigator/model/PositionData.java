package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.model;

/* loaded from: classes.dex */
public class PositionData {
    public int mBottom;
    public int mContentBottom;
    public int mContentLeft;
    public int mContentRight;
    public int mContentTop;
    public int mLeft;
    public int mRight;
    public int mTop;

    public int contentHeight() {
        return this.mContentBottom - this.mContentTop;
    }

    public int contentWidth() {
        return this.mContentRight - this.mContentLeft;
    }

    public int height() {
        return this.mBottom - this.mTop;
    }

    public int horizontalCenter() {
        return this.mLeft + (width() / 2);
    }

    public int verticalCenter() {
        return this.mTop + (height() / 2);
    }

    public int width() {
        return this.mRight - this.mLeft;
    }
}
