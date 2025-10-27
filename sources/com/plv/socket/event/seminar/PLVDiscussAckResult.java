package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class PLVDiscussAckResult extends PLVSimpleAckResult {
    private DataBean data;

    public static class DataBean {
        private String fullScreenPPT;
        private Double height;
        private String id;
        private Double left;
        private List<PLVPPTBean> pptList;
        private List<PLVPPTBean> pptStorageList;
        private RoomStatus roomsStatus;
        private String sessionId;

        /* renamed from: top, reason: collision with root package name */
        private Double f10946top;
        private Double width;
        private Double zoom;

        public static class RoomStatus {
            private String avConnectMode;
            private Boolean hasGroupLeader;
            private String liveStatus;
            private String micSite;
            private String videoAndPPTPosition;

            public String getAvConnectMode() {
                return this.avConnectMode;
            }

            public Boolean getHasGroupLeader() {
                return this.hasGroupLeader;
            }

            public String getLiveStatus() {
                return this.liveStatus;
            }

            public String getMicSite() {
                return this.micSite;
            }

            public Map<String, PLVUpdateMicSiteEvent> getParsedMicSite() {
                return (Map) new Gson().fromJson(this.micSite, new TypeToken<Map<String, PLVUpdateMicSiteEvent>>() { // from class: com.plv.socket.event.seminar.PLVDiscussAckResult.DataBean.RoomStatus.1
                }.getType());
            }

            public String getVideoAndPPTPosition() {
                return this.videoAndPPTPosition;
            }

            public void setAvConnectMode(String str) {
                this.avConnectMode = str;
            }

            public void setHasGroupLeader(Boolean bool) {
                this.hasGroupLeader = bool;
            }

            public void setLiveStatus(String str) {
                this.liveStatus = str;
            }

            public void setMicSite(String str) {
                this.micSite = str;
            }

            public void setVideoAndPPTPosition(String str) {
                this.videoAndPPTPosition = str;
            }
        }

        public String getFullScreenPPT() {
            return this.fullScreenPPT;
        }

        public Double getHeight() {
            return this.height;
        }

        public String getId() {
            return this.id;
        }

        public Double getLeft() {
            return this.left;
        }

        public List<PLVPPTBean> getPptList() {
            return this.pptList;
        }

        public List<PLVPPTBean> getPptStorageList() {
            return this.pptStorageList;
        }

        public RoomStatus getRoomsStatus() {
            return this.roomsStatus;
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public Double getTop() {
            return this.f10946top;
        }

        public Double getWidth() {
            return this.width;
        }

        public Double getZoom() {
            return this.zoom;
        }

        public void setFullScreenPPT(String str) {
            this.fullScreenPPT = str;
        }

        public void setHeight(Double d3) {
            this.height = d3;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setLeft(Double d3) {
            this.left = d3;
        }

        public void setPptList(List<PLVPPTBean> list) {
            this.pptList = list;
        }

        public void setPptStorageList(List<PLVPPTBean> list) {
            this.pptStorageList = list;
        }

        public void setRoomsStatus(RoomStatus roomStatus) {
            this.roomsStatus = roomStatus;
        }

        public void setSessionId(String str) {
            this.sessionId = str;
        }

        public void setTop(Double d3) {
            this.f10946top = d3;
        }

        public void setWidth(Double d3) {
            this.width = d3;
        }

        public void setZoom(Double d3) {
            this.zoom = d3;
        }

        public String toString() {
            return "DataBean{zoom=" + this.zoom + ", width=" + this.width + ", height=" + this.height + ", top=" + this.f10946top + ", left=" + this.left + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", pptList=" + this.pptList + ", pptStorageList=" + this.pptStorageList + ", fullScreenPPT='" + this.fullScreenPPT + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    @Override // com.plv.socket.event.seminar.PLVSimpleAckResult
    public String toString() {
        return "PLVDiscussAckResult{data=" + this.data + '}';
    }
}
