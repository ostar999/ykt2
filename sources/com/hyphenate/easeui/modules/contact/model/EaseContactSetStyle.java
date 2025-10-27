package com.hyphenate.easeui.modules.contact.model;

import android.graphics.drawable.Drawable;
import com.hyphenate.easeui.modules.EaseBaseSetStyle;

/* loaded from: classes4.dex */
public class EaseContactSetStyle extends EaseBaseSetStyle {
    private Drawable avatarDefaultSrc;
    private Drawable headerBgDrawable;
    private int headerTextColor;
    private float headerTextSize;
    private boolean showItemHeader;
    private int titleTextColor;
    private float titleTextSize;

    public Drawable getAvatarDefaultSrc() {
        return this.avatarDefaultSrc;
    }

    public Drawable getHeaderBgDrawable() {
        return this.headerBgDrawable;
    }

    public int getHeaderTextColor() {
        return this.headerTextColor;
    }

    public float getHeaderTextSize() {
        return this.headerTextSize;
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public float getTitleTextSize() {
        return this.titleTextSize;
    }

    public boolean isShowItemHeader() {
        return this.showItemHeader;
    }

    public void setAvatarDefaultSrc(Drawable drawable) {
        this.avatarDefaultSrc = drawable;
    }

    public void setHeaderBgDrawable(Drawable drawable) {
        this.headerBgDrawable = drawable;
    }

    public void setHeaderTextColor(int i2) {
        this.headerTextColor = i2;
    }

    public void setHeaderTextSize(float f2) {
        this.headerTextSize = f2;
    }

    public void setShowItemHeader(boolean z2) {
        this.showItemHeader = z2;
    }

    public void setTitleTextColor(int i2) {
        this.titleTextColor = i2;
    }

    public void setTitleTextSize(float f2) {
        this.titleTextSize = f2;
    }
}
