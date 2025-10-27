package com.plv.livescenes.model.lottery;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVLotteryEndVO {
    private String EVENT;
    private String channelId;
    private String collectInfo;
    private List<DataBean> data;
    private String lotteryId;
    private String prize;
    private String sessionId;
    private List<WinnersListBean> winnersList;

    public static class DataBean {
        private boolean banned;
        private String channelId;
        private String clientIp;
        private String nick;
        private String pic;
        private String roomId;
        private String uid;
        private String userId;
        private String userType;
        private String winnerCode;

        public String getChannelId() {
            return this.channelId;
        }

        public String getClientIp() {
            return this.clientIp;
        }

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return this.pic;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getUid() {
            return this.uid;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getUserType() {
            return this.userType;
        }

        public String getWinnerCode() {
            return this.winnerCode;
        }

        public boolean isBanned() {
            return this.banned;
        }

        public void setBanned(boolean z2) {
            this.banned = z2;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setClientIp(String str) {
            this.clientIp = str;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setPic(String str) {
            this.pic = str;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setUid(String str) {
            this.uid = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setUserType(String str) {
            this.userType = str;
        }

        public void setWinnerCode(String str) {
            this.winnerCode = str;
        }
    }

    public static class WinnersListBean {
        private boolean banned;

        @SerializedName("channelId")
        private String channelIdX;
        private String clientIp;

        @SerializedName("lotteryId")
        private String lotteryIdX;
        private String nick;
        private String pic;
        private String roomId;
        private String uid;
        private String userId;
        private String userType;

        public String getChannelIdX() {
            return this.channelIdX;
        }

        public String getClientIp() {
            return this.clientIp;
        }

        public String getLotteryIdX() {
            return this.lotteryIdX;
        }

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return this.pic;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getUid() {
            return this.uid;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getUserType() {
            return this.userType;
        }

        public boolean isBanned() {
            return this.banned;
        }

        public void setBanned(boolean z2) {
            this.banned = z2;
        }

        public void setChannelIdX(String str) {
            this.channelIdX = str;
        }

        public void setClientIp(String str) {
            this.clientIp = str;
        }

        public void setLotteryIdX(String str) {
            this.lotteryIdX = str;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setPic(String str) {
            this.pic = str;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setUid(String str) {
            this.uid = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setUserType(String str) {
            this.userType = str;
        }
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getCollectInfo() {
        return this.collectInfo;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getLotteryId() {
        return this.lotteryId;
    }

    public String getPrize() {
        return this.prize;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public List<WinnersListBean> getWinnersList() {
        return this.winnersList;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setCollectInfo(String str) {
        this.collectInfo = str;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setLotteryId(String str) {
        this.lotteryId = str;
    }

    public void setPrize(String str) {
        this.prize = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setWinnersList(List<WinnersListBean> list) {
        this.winnersList = list;
    }
}
