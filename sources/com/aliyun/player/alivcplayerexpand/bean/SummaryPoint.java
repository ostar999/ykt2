package com.aliyun.player.alivcplayerexpand.bean;

/* loaded from: classes2.dex */
public class SummaryPoint {
    public int position;
    public float radius;
    public int time;

    public SummaryPoint(int i2, float f2, int i3) {
        this.time = i2;
        this.radius = f2;
        this.position = i3;
    }

    public int getPosition() {
        return this.position;
    }

    public float getRadius() {
        return this.radius;
    }

    public int getTime() {
        return this.time;
    }

    public void setPosition(int i2) {
        this.position = i2;
    }

    public void setRadius(float f2) {
        this.radius = f2;
    }

    public void setTime(int i2) {
        this.time = i2;
    }
}
