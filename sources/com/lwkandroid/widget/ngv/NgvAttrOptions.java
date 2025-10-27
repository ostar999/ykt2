package com.lwkandroid.widget.ngv;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes4.dex */
class NgvAttrOptions {
    private int mDividerSize;
    private boolean mEnableEditMode;
    private int mHorizontalChildCount;
    private Drawable mIconDeleteDrawable;
    private float mIconDeleteSizeRatio;
    private Drawable mIconPlusDrawable;
    private ImageView.ScaleType mImageScaleType;
    private int mSingleImageHeight;
    private int mSingleImageWidth;

    public int getDividerSize() {
        return this.mDividerSize;
    }

    public int getHorizontalChildCount() {
        return this.mHorizontalChildCount;
    }

    public Drawable getIconDeleteDrawable() {
        return this.mIconDeleteDrawable;
    }

    public float getIconDeleteSizeRatio() {
        return this.mIconDeleteSizeRatio;
    }

    public Drawable getIconPlusDrawable() {
        return this.mIconPlusDrawable;
    }

    public ImageView.ScaleType getImageScaleType() {
        return this.mImageScaleType;
    }

    public int getSingleImageHeight() {
        return this.mSingleImageHeight;
    }

    public int getSingleImageWidth() {
        return this.mSingleImageWidth;
    }

    public boolean isEnableEditMode() {
        return this.mEnableEditMode;
    }

    public void setDividerSize(int dividerSize) {
        this.mDividerSize = dividerSize;
    }

    public void setEnableEditMode(boolean enableEditMode) {
        this.mEnableEditMode = enableEditMode;
    }

    public void setHorizontalChildCount(int count) {
        this.mHorizontalChildCount = count;
    }

    public void setIconDeleteDrawable(Drawable iconDeleteDrawable) {
        this.mIconDeleteDrawable = iconDeleteDrawable;
    }

    public void setIconDeleteSizeRatio(float iconDeleteSizeRatio) {
        this.mIconDeleteSizeRatio = iconDeleteSizeRatio;
    }

    public void setIconPlusDrawable(Drawable iconPlusDrawable) {
        this.mIconPlusDrawable = iconPlusDrawable;
    }

    public void setImageScaleType(ImageView.ScaleType scaleType) {
        this.mImageScaleType = scaleType;
    }

    public void setSingleImageHeight(int singleImageHeight) {
        this.mSingleImageHeight = singleImageHeight;
    }

    public void setSingleImageWidth(int singleImageWidth) {
        this.mSingleImageWidth = singleImageWidth;
    }
}
