package com.plv.socket.event.interact;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVNewsPushStartEvent {
    private String EVENT;
    private String entrance;
    private String id;
    private int lookTime;
    private String roomId;
    private int time;
    private int total;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getEntrance() {
        return this.entrance;
    }

    public String getId() {
        return this.id;
    }

    public int getLookTime() {
        return this.lookTime;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public int getTime() {
        return this.time;
    }

    public int getTotal() {
        return this.total;
    }

    public boolean isEntrance() {
        return "Y".equals(this.entrance);
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setEntrance(String str) {
        this.entrance = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setLookTime(int i2) {
        this.lookTime = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setTime(int i2) {
        this.time = i2;
    }

    public void setTotal(int i2) {
        this.total = i2;
    }

    public String toString() {
        return "PLVNewsPushStartEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", total=" + this.total + ", entrance='" + this.entrance + CharPool.SINGLE_QUOTE + ", lookTime=" + this.lookTime + '}';
    }
}
