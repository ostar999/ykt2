package com.plv.socket.event.ppt;

import cn.hutool.core.text.CharPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.plv.socket.user.PLVClassStatusBean;
import java.util.Map;

/* loaded from: classes5.dex */
public class PLVOnSliceIDEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "onSliceID";
    private PLVClassStatusBean classStatus;
    private DataBean data;
    private String groupId;
    private boolean inClass;
    private boolean inDiscuss;
    private boolean isLeader;
    private String leader;
    private int pptAndVedioPosition;

    public static class DataBean {
        private int autoId;
        private String avConnectMode;
        private String docType;
        private long duration;
        private String focusSpecialSpeak;
        private int isCamClosed;
        private String micSite;
        private int pageId;
        private long pushtime;
        private String roomId;
        private String sessionId;
        private String step;
        private long timeStamp;
        private String userId;

        public int getAutoId() {
            return this.autoId;
        }

        public String getAvConnectMode() {
            return this.avConnectMode;
        }

        public String getDocType() {
            return this.docType;
        }

        public long getDuration() {
            return this.duration;
        }

        public String getFocusSpecialSpeak() {
            return this.focusSpecialSpeak;
        }

        public int getIsCamClosed() {
            return this.isCamClosed;
        }

        public String getMicSite() {
            return this.micSite;
        }

        public int getPageId() {
            return this.pageId;
        }

        public Map<String, PLVUpdateMicSiteEvent> getParsedMicSite() {
            return (Map) new Gson().fromJson(this.micSite, new TypeToken<Map<String, PLVUpdateMicSiteEvent>>() { // from class: com.plv.socket.event.ppt.PLVOnSliceIDEvent.DataBean.1
            }.getType());
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

        public String getStep() {
            return this.step;
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }

        public String getUserId() {
            return this.userId;
        }

        public boolean isFocusMode() {
            return "Y".equals(this.focusSpecialSpeak);
        }

        public void setAutoId(int i2) {
            this.autoId = i2;
        }

        public void setAvConnectMode(String str) {
            this.avConnectMode = str;
        }

        public void setDocType(String str) {
            this.docType = str;
        }

        public void setDuration(long j2) {
            this.duration = j2;
        }

        public void setFocusSpecialSpeak(String str) {
            this.focusSpecialSpeak = str;
        }

        public void setIsCamClosed(int i2) {
            this.isCamClosed = i2;
        }

        public void setMicSite(String str) {
            this.micSite = str;
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

        public void setStep(String str) {
            this.step = str;
        }

        public void setTimeStamp(long j2) {
            this.timeStamp = j2;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String toString() {
            return "DataBean{autoId=" + this.autoId + ", docType='" + this.docType + CharPool.SINGLE_QUOTE + ", duration=" + this.duration + ", isCamClosed=" + this.isCamClosed + ", pageId=" + this.pageId + ", pushtime=" + this.pushtime + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", step='" + this.step + CharPool.SINGLE_QUOTE + ", timeStamp=" + this.timeStamp + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", avConnectMode='" + this.avConnectMode + CharPool.SINGLE_QUOTE + ", micSite='" + this.micSite + CharPool.SINGLE_QUOTE + ", focusSpecialSpeak='" + this.focusSpecialSpeak + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public PLVClassStatusBean getClassStatus() {
        return this.classStatus;
    }

    public DataBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "onSliceID";
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getLeader() {
        return this.leader;
    }

    public int getPptAndVedioPosition() {
        return this.pptAndVedioPosition;
    }

    public boolean isInClass() {
        return this.inClass;
    }

    public boolean isInDiscuss() {
        return this.inDiscuss;
    }

    public boolean isLeader() {
        return this.isLeader;
    }

    public void setClassStatus(PLVClassStatusBean pLVClassStatusBean) {
        this.classStatus = pLVClassStatusBean;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setGroupId(String str) {
        this.groupId = str;
    }

    public void setInClass(boolean z2) {
        this.inClass = z2;
    }

    public void setInDiscuss(boolean z2) {
        this.inDiscuss = z2;
    }

    public void setLeader(boolean z2) {
        this.isLeader = z2;
    }

    public void setPptAndVedioPosition(int i2) {
        this.pptAndVedioPosition = i2;
    }

    public String toString() {
        return "PLVOnSliceIDEvent{data=" + this.data + ", classStatus=" + this.classStatus + ", inClass=" + this.inClass + ", pptAndVedioPosition=" + this.pptAndVedioPosition + ", groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", inDiscuss=" + this.inDiscuss + ", isLeader=" + this.isLeader + ", leader='" + this.leader + CharPool.SINGLE_QUOTE + '}';
    }

    public void setLeader(String str) {
        this.leader = str;
    }
}
