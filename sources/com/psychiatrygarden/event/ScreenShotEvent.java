package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class ScreenShotEvent {
    public int imgHeight;
    public String imgPath;

    public ScreenShotEvent(String imgPath, int imgHeight) {
        this.imgPath = imgPath;
        this.imgHeight = imgHeight;
    }
}
