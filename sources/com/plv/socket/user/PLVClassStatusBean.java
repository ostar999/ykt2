package com.plv.socket.user;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVClassStatusBean implements PLVFoundationVO {
    public static final int BANNED_STATUS_OFF = 0;
    public static final int BANNED_STATUS_ON = 1;
    public static final int STATUS_OFF = 0;
    public static final int STATUS_ON = 1;
    private int banned;
    private int cup;
    private int groupLeader;
    private int inWaitVoice;
    private int paint;
    private int raiseHand;
    private int voice;

    public int getBanned() {
        return this.banned;
    }

    public int getCup() {
        return this.cup;
    }

    public int getGroupLeader() {
        return this.groupLeader;
    }

    public int getInWaitVoice() {
        return this.inWaitVoice;
    }

    public int getPaint() {
        return this.paint;
    }

    public int getRaiseHand() {
        return this.raiseHand;
    }

    public int getVoice() {
        return this.voice;
    }

    public boolean hasPaint() {
        return 1 == this.paint;
    }

    public boolean isGroupLeader() {
        return 1 == this.groupLeader;
    }

    public boolean isRaiseHand() {
        return 1 == this.raiseHand;
    }

    public boolean isVoice() {
        return 1 == this.voice;
    }

    public void setBanned(int i2) {
        this.banned = i2;
    }

    public void setCup(int i2) {
        this.cup = i2;
    }

    public void setGroupLeader(int i2) {
        this.groupLeader = i2;
    }

    public void setInWaitVoice(int i2) {
        this.inWaitVoice = i2;
    }

    public void setPaint(int i2) {
        this.paint = i2;
    }

    public void setRaiseHand(int i2) {
        this.raiseHand = i2;
    }

    public void setVoice(int i2) {
        this.voice = i2;
    }

    public String toString() {
        return "PLVClassStatusBean{groupLeader=" + this.groupLeader + ", banned=" + this.banned + ", cup=" + this.cup + ", inWaitVoice=" + this.inWaitVoice + ", raiseHand=" + this.raiseHand + ", voice=" + this.voice + ", paint=" + this.paint + '}';
    }
}
