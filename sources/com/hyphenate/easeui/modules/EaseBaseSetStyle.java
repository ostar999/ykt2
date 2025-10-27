package com.hyphenate.easeui.modules;

import android.graphics.drawable.Drawable;
import com.hyphenate.easeui.widget.EaseImageView;

/* loaded from: classes4.dex */
public class EaseBaseSetStyle {
    private float avatarRadius;
    private float avatarSize;
    private Drawable bgDrawable;
    private int borderColor;
    private float borderWidth;
    private float itemHeight;
    private EaseImageView.ShapeType shapeType;

    public float getAvatarRadius() {
        return this.avatarRadius;
    }

    public float getAvatarSize() {
        return this.avatarSize;
    }

    public Drawable getBgDrawable() {
        return this.bgDrawable;
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    public float getBorderWidth() {
        return this.borderWidth;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public EaseImageView.ShapeType getShapeType() {
        return this.shapeType;
    }

    public void setAvatarRadius(float f2) {
        this.avatarRadius = f2;
    }

    public void setAvatarSize(float f2) {
        this.avatarSize = f2;
    }

    public void setBgDrawable(Drawable drawable) {
        this.bgDrawable = drawable;
    }

    public void setBorderColor(int i2) {
        this.borderColor = i2;
    }

    public void setBorderWidth(float f2) {
        this.borderWidth = f2;
    }

    public void setItemHeight(float f2) {
        this.itemHeight = f2;
    }

    public void setShapeType(EaseImageView.ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setShapeType(int i2) {
        if (i2 == 1) {
            this.shapeType = EaseImageView.ShapeType.ROUND;
        } else if (i2 == 2) {
            this.shapeType = EaseImageView.ShapeType.RECTANGLE;
        } else if (i2 >= 0) {
            this.shapeType = EaseImageView.ShapeType.NONE;
        }
    }
}
