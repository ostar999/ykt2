package com.aliyun.player.alivcplayerexpand.view.more;

import com.aliyun.player.IPlayer;

/* loaded from: classes2.dex */
public class AliyunShowMoreValue {
    private boolean loop;
    private IPlayer.ScaleMode scaleMode;
    private int screenBrightness;
    private float speed;
    private int volume;

    public IPlayer.ScaleMode getScaleMode() {
        return this.scaleMode;
    }

    public int getScreenBrightness() {
        return this.screenBrightness;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getVolume() {
        return this.volume;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public void setLoop(boolean z2) {
        this.loop = z2;
    }

    public void setScaleMode(IPlayer.ScaleMode scaleMode) {
        this.scaleMode = scaleMode;
    }

    public void setScreenBrightness(int i2) {
        this.screenBrightness = i2;
    }

    public void setSpeed(float f2) {
        this.speed = f2;
    }

    public void setVolume(int i2) {
        this.volume = i2;
    }
}
