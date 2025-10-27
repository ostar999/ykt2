package com.plv.livescenes.model;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVSocketSliceIdVO implements PLVBaseVO {
    private String EVENT;
    private DataBean data;

    public static class DataBean {
        private int autoId;
        private long duration;
        private int isCamClosed;
        private int isShareOpen;
        private int pageId;
        private long pushtime;
        private String roomId;
        private String sessionId;
        private long timeStamp;

        public int getAutoId() {
            return this.autoId;
        }

        public long getDuration() {
            return this.duration;
        }

        public int getIsCamClosed() {
            return this.isCamClosed;
        }

        public int getIsShareOpen() {
            return this.isShareOpen;
        }

        public int getPageId() {
            return this.pageId;
        }

        public long getPushtime() {
            return this.pushtime;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }

        public void setAutoId(int i2) {
            this.autoId = i2;
        }

        public void setDuration(long j2) {
            this.duration = j2;
        }

        public void setIsCamClosed(int i2) {
            this.isCamClosed = i2;
        }

        public void setIsShareOpen(int i2) {
            this.isShareOpen = i2;
        }

        public void setPageId(int i2) {
            this.pageId = i2;
        }

        public void setPushtime(long j2) {
            this.pushtime = j2;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setSessionId(String str) {
            this.sessionId = str;
        }

        public void setTimeStamp(long j2) {
            this.timeStamp = j2;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }
}
