package com.plv.livescenes.chatroom.send.img;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.IPLVIdEvent;
import com.plv.socket.event.chat.IPLVManagerChatEvent;

/* loaded from: classes4.dex */
public class PLVSendLocalImgEvent extends PLVBaseEvent implements IPLVIdEvent, IPLVManagerChatEvent {
    public static final int SENDSTATUS_FAIL = 1;
    public static final int SENDSTATUS_SENDING = 2;
    public static final int SENDSTATUS_SUCCESS = 0;
    private int height;
    private String id;
    private String imageFilePath;
    private boolean isSendFail;
    private boolean isSendSuccess;
    private int sendProgress;
    private int sendStatus;
    private String source;
    private Long time;
    private int width;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getImageFilePath() {
        return this.imageFilePath;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getListenEvent() {
        return null;
    }

    public int getSendProgress() {
        return this.sendProgress;
    }

    public int getSendStatus() {
        return this.sendStatus;
    }

    public String getSource() {
        return this.source;
    }

    public Long getTime() {
        return this.time;
    }

    public String getUserId() {
        return PLVSocketWrapper.getInstance().getLoginVO().getUserId();
    }

    public int getWidth() {
        return this.width;
    }

    public void initSendStatus() {
        this.isSendFail = false;
        this.isSendSuccess = false;
        this.sendProgress = 0;
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public boolean isManagerChatMsg() {
        return "extend".equals(this.source);
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    public void setImageFilePath(String str) {
        this.imageFilePath = str;
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public void setIsManagerChatMsg(boolean z2) {
        if (z2) {
            this.source = "extend";
        } else {
            this.source = "";
        }
    }

    public void setSendProgress(int i2) {
        this.sendProgress = i2;
    }

    public void setSendStatus(int i2) {
        this.sendStatus = i2;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setTime(Long l2) {
        this.time = l2;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }

    public String toString() {
        return "PLVSendLocalImgEvent{imageFilePath='" + this.imageFilePath + CharPool.SINGLE_QUOTE + ", width=" + this.width + ", height=" + this.height + ", isSendSuccess=" + this.isSendSuccess + ", sendProgress=" + this.sendProgress + ", isSendFail=" + this.isSendFail + ", sendStatus=" + this.sendStatus + ", id=" + this.id + ", time=" + this.time + '}';
    }
}
