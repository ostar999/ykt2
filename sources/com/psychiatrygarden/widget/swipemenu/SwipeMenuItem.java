package com.psychiatrygarden.widget.swipemenu;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public class SwipeMenuItem {
    private Drawable background;
    private int height;
    private Drawable icon;
    private int id;
    private boolean isShowSort;
    private int jian_height = 0;
    private Context mContext;
    private String title;
    private int titleColor;
    private int titleSize;
    private int width;

    public SwipeMenuItem(Context context) {
        this.mContext = context;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public int getHeight() {
        return this.height;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public int getId() {
        return this.id;
    }

    public int getJian_height() {
        return this.jian_height;
    }

    public String getTitle() {
        return this.title;
    }

    public int getTitleColor() {
        return this.titleColor;
    }

    public int getTitleSize() {
        return this.titleSize;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isShowSort() {
        return this.isShowSort;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJian_height(int jian_height) {
        this.jian_height = jian_height;
    }

    public void setShowSort(boolean showSort) {
        this.isShowSort = showSort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setBackground(int resId) {
        this.background = this.mContext.getResources().getDrawable(resId);
    }

    public void setIcon(int resId) {
        this.icon = this.mContext.getResources().getDrawable(resId);
    }

    public void setTitle(int resId) {
        setTitle(this.mContext.getString(resId));
    }
}
