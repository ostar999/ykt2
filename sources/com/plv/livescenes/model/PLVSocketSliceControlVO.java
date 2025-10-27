package com.plv.livescenes.model;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVSocketSliceControlVO implements PLVBaseVO {
    private String EVENT;
    private DataBean data;
    private String roomId;

    public static class DataBean implements PLVBaseVO {
        private int isCamClosed;
        private boolean isMute;
        private int timeStamp;
        private String type;

        public int getIsCamClosed() {
            return this.isCamClosed;
        }

        public int getTimeStamp() {
            return this.timeStamp;
        }

        public String getType() {
            return this.type;
        }

        public boolean isMute() {
            return this.isMute;
        }

        public void setIsCamClosed(int i2) {
            this.isCamClosed = i2;
        }

        public void setMute(boolean z2) {
            this.isMute = z2;
        }

        public void setTimeStamp(int i2) {
            this.timeStamp = i2;
        }

        public void setType(String str) {
            this.type = str;
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
