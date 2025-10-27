package com.plv.socket.event.ppt;

import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVFinishClassEvent extends PLVMessageBaseEvent {
    private String EVENT = PLVEventConstant.Class.FINISH_CLASS;
    private String channelId;
    private int clearPermission;
    private String roomId;
    private String userId;

    public PLVFinishClassEvent(String str, String str2, String str3) {
        this.roomId = str;
        this.channelId = str2;
        this.userId = str3;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public int getClearPermission() {
        return this.clearPermission;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setClearPermission(int i2) {
        this.clearPermission = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public PLVFinishClassEvent(int i2) {
        this.clearPermission = i2;
    }
}
