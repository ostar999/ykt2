package com.plv.socket.event.chat;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVRewardEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "REWARD";
    private ContentBean content;
    private int roomId;

    public static class ContentBean {
        private String gimg;
        private int goodNum;
        private String rewardContent;
        private String uimg;
        private String unick;

        public String getGimg() {
            return PLVEventHelper.fixChatPic(this.gimg);
        }

        public int getGoodNum() {
            return this.goodNum;
        }

        public String getRewardContent() {
            return this.rewardContent;
        }

        public String getUimg() {
            return PLVEventHelper.fixChatPic(this.uimg);
        }

        public String getUnick() {
            return this.unick;
        }

        public void setGimg(String str) {
            this.gimg = str;
        }

        public void setGoodNum(int i2) {
            this.goodNum = i2;
        }

        public void setRewardContent(String str) {
            this.rewardContent = str;
        }

        public void setUimg(String str) {
            this.uimg = str;
        }

        public void setUnick(String str) {
            this.unick = str;
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "REWARD";
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }

    public void setRoomId(int i2) {
        this.roomId = i2;
    }
}
