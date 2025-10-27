package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVOperateContainerEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "operateContainer";
    private String containerId;
    private OperateType operateType;

    public enum OperateType {
        OPEN("open"),
        CLOSE("close"),
        MAXIMIZE("maximize"),
        CANCEL_MAXIMIZE("cancelMaximize"),
        MINIMIZE("minimize");

        protected final String type;

        OperateType(String str) {
            this.type = str;
        }
    }

    public PLVOperateContainerEvent() {
    }

    public static PLVOperateContainerEvent fromJson(String str) {
        return (PLVOperateContainerEvent) new Gson().fromJson(str, PLVOperateContainerEvent.class);
    }

    public String getContainerId() {
        return this.containerId;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public OperateType getOperateType() {
        return this.operateType;
    }

    public PLVOperateContainerEvent setContainerId(String str) {
        this.containerId = str;
        return this;
    }

    public PLVOperateContainerEvent setOperateType(OperateType operateType) {
        this.operateType = operateType;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVOperateContainerEvent(String str, OperateType operateType) {
        this.containerId = str;
        this.operateType = operateType;
    }
}
