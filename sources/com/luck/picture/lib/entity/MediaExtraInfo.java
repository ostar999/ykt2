package com.luck.picture.lib.entity;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class MediaExtraInfo {
    private long duration;
    private int height;
    private String orientation;
    private int width;

    public long getDuration() {
        return this.duration;
    }

    public int getHeight() {
        return this.height;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public int getWidth() {
        return this.width;
    }

    public void setDuration(long j2) {
        this.duration = j2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setOrientation(String str) {
        this.orientation = str;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    public String toString() {
        return "MediaExtraInfo{width=" + this.width + ", height=" + this.height + ", duration=" + this.duration + ", orientation='" + this.orientation + CharPool.SINGLE_QUOTE + '}';
    }
}
