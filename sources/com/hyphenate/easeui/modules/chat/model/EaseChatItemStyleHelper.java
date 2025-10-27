package com.hyphenate.easeui.modules.chat.model;

import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public class EaseChatItemStyleHelper {
    private static EaseChatItemStyleHelper instance;
    private EaseChatSetStyle style;

    private EaseChatItemStyleHelper() {
        EaseChatSetStyle easeChatSetStyle = new EaseChatSetStyle();
        this.style = easeChatSetStyle;
        easeChatSetStyle.setShowAvatar(true);
        this.style.setShowNickname(false);
    }

    public static EaseChatItemStyleHelper getInstance() {
        if (instance == null) {
            synchronized (EaseChatItemStyleHelper.class) {
                if (instance == null) {
                    instance = new EaseChatItemStyleHelper();
                }
            }
        }
        return instance;
    }

    public void clear() {
        this.style = null;
        instance = null;
    }

    public EaseChatSetStyle getStyle() {
        return this.style;
    }

    public void setAvatarDefaultSrc(Drawable drawable) {
        this.style.setAvatarDefaultSrc(drawable);
    }

    public void setAvatarRadius(float f2) {
        this.style.setAvatarRadius(f2);
    }

    public void setAvatarSize(float f2) {
        this.style.setAvatarSize(f2);
    }

    public void setBgDrawable(Drawable drawable) {
        this.style.setBgDrawable(drawable);
    }

    public void setBorderColor(int i2) {
        this.style.setBorderColor(i2);
    }

    public void setBorderWidth(float f2) {
        this.style.setBorderWidth(f2);
    }

    public void setItemHeight(float f2) {
        this.style.setItemHeight(f2);
    }

    public void setItemMinHeight(int i2) {
        this.style.setItemMinHeight(i2);
    }

    public void setItemShowType(int i2) {
        this.style.setItemShowType(i2);
    }

    public void setReceiverBgDrawable(Drawable drawable) {
        this.style.setReceiverBgDrawable(drawable);
    }

    public void setSenderBgDrawable(Drawable drawable) {
        this.style.setSenderBgDrawable(drawable);
    }

    public void setShapeType(int i2) {
        this.style.setShapeType(i2);
    }

    public void setShowAvatar(boolean z2) {
        this.style.setShowAvatar(z2);
    }

    public void setShowNickname(boolean z2) {
        this.style.setShowNickname(z2);
    }

    public void setTextColor(int i2) {
        this.style.setTextColor(i2);
    }

    public void setTextSize(int i2) {
        this.style.setTextSize(i2);
    }

    public void setTimeBgDrawable(Drawable drawable) {
        this.style.setTimeBgDrawable(drawable);
    }

    public void setTimeTextColor(int i2) {
        this.style.setTimeTextColor(i2);
    }

    public void setTimeTextSize(int i2) {
        this.style.setTimeTextSize(i2);
    }
}
