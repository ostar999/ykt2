package com.plv.livescenes.document.event;

import com.google.gson.Gson;
import com.plv.socket.event.seminar.PLVDiscussAckResult;
import com.plv.socket.event.seminar.PLVPPTBean;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVSwitchRoomEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "switchRoom";
    private String fullScreenPPT;
    private Double height;
    private Double left;
    private List<PLVPPTBean> pptList;
    private List<PLVPPTBean> pptStorageList;
    private String roomId;
    private String sessionId;

    /* renamed from: top, reason: collision with root package name */
    private Double f10821top;
    private Double width;
    private Double zoom;

    public PLVSwitchRoomEvent(Double d3, Double d4, Double d5, Double d6, Double d7, String str, String str2, List<PLVPPTBean> list, List<PLVPPTBean> list2, String str3) {
        this.zoom = d3;
        this.width = d4;
        this.height = d5;
        this.f10821top = d6;
        this.left = d7;
        this.roomId = str;
        this.sessionId = str2;
        this.pptList = list;
        this.pptStorageList = list2;
        this.fullScreenPPT = str3;
    }

    public static PLVSwitchRoomEvent fromDataBean(PLVDiscussAckResult.DataBean dataBean) {
        return new PLVSwitchRoomEvent(dataBean.getZoom(), dataBean.getWidth(), dataBean.getHeight(), dataBean.getTop(), dataBean.getLeft(), dataBean.getId(), dataBean.getSessionId(), dataBean.getPptList(), dataBean.getPptStorageList(), dataBean.getFullScreenPPT());
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public String getFullScreenPPT() {
        return this.fullScreenPPT;
    }

    public Double getHeight() {
        return this.height;
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

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Double getTop() {
        return this.f10821top;
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

    public void setLeft(Double d3) {
        this.left = d3;
    }

    public void setPptList(List<PLVPPTBean> list) {
        this.pptList = list;
    }

    public void setPptStorageList(List<PLVPPTBean> list) {
        this.pptStorageList = list;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setTop(Double d3) {
        this.f10821top = d3;
    }

    public void setWidth(Double d3) {
        this.width = d3;
    }

    public void setZoom(Double d3) {
        this.zoom = d3;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
