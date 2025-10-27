package com.plv.livescenes.model.signin;

/* loaded from: classes5.dex */
public class PLVSignInVO {
    private String EVENT;
    private DataBean data;
    private String roomId;

    public static class DataBean {
        private String checkinId;
        private String limitTime;
        private String message;

        public String getCheckinId() {
            return this.checkinId;
        }

        public String getLimitTime() {
            return this.limitTime;
        }

        public String getMessage() {
            return this.message;
        }

        public void setCheckinId(String str) {
            this.checkinId = str;
        }

        public void setLimitTime(String str) {
            this.limitTime = str;
        }

        public void setMessage(String str) {
            this.message = str;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }
}
