package com.plv.socket.event.ppt;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVOnSliceStartEvent extends PLVMessageBaseEvent {
    public static final String COURSE_TYPE_HI_CLASS = "smallClass";
    public final String EVENT;
    private long classInterval;
    private String courseType;
    private DataBean data;
    private int docType;
    private int emitMode;
    private int isNoCount;
    private boolean keepClass;
    private long pushtime;
    private String roomId;
    private String sessionId;
    private String streamName;
    private long timeStamp;
    private String userId;
    private String videoAndPPTPosition;

    public static class DataBean {
        private int autoId;
        private int isCamClosed;
        private int pageId;
        private int step;

        public DataBean(int i2, int i3, int i4, int i5) {
            this.autoId = i2;
            this.isCamClosed = i3;
            this.pageId = i4;
            this.step = i5;
        }

        public int getAutoId() {
            return this.autoId;
        }

        public int getIsCamClosed() {
            return this.isCamClosed;
        }

        public int getPageId() {
            return this.pageId;
        }

        public int getStep() {
            return this.step;
        }

        public void setAutoId(int i2) {
            this.autoId = i2;
        }

        public void setIsCamClosed(int i2) {
            this.isCamClosed = i2;
        }

        public void setPageId(int i2) {
            this.pageId = i2;
        }

        public void setStep(int i2) {
            this.step = i2;
        }
    }

    public PLVOnSliceStartEvent(DataBean dataBean, long j2, String str, String str2, long j3, String str3) {
        this.EVENT = "onSliceStart";
        this.docType = 1;
        this.isNoCount = 0;
        this.data = dataBean;
        this.pushtime = j2;
        this.roomId = str;
        this.sessionId = str2;
        this.timeStamp = j3;
        this.streamName = str3;
    }

    public DataBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "onSliceStart";
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

    public String getStreamName() {
        return this.streamName;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getVideoAndPPTPosition() {
        return this.videoAndPPTPosition;
    }

    public boolean isPptOnMainScreen() {
        return "ppt".equals(getVideoAndPPTPosition());
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
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

    public void setStreamName(String str) {
        this.streamName = str;
    }

    public void setTimeStamp(long j2) {
        this.timeStamp = j2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setVideoAndPPTPosition(String str) {
        this.videoAndPPTPosition = str;
    }

    public PLVOnSliceStartEvent(int i2, String str, String str2, long j2, String str3, int i3, String str4, boolean z2, long j3) {
        this.EVENT = "onSliceStart";
        this.isNoCount = 0;
        this.docType = i2;
        this.roomId = str;
        this.sessionId = str2;
        this.timeStamp = j2;
        this.userId = str3;
        this.emitMode = i3;
        this.courseType = str4;
        this.keepClass = z2;
        this.classInterval = j3;
    }
}
