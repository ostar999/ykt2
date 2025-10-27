package com.plv.socket.event.linkmic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.socket.impl.PLVSocketManager;

/* loaded from: classes5.dex */
public class PLVRemoveMicSiteEvent {
    public static final String EVENT_NAME = "remove";
    public static final String SOCKET_EVENT_TYPE = "changeMicSite";
    private String EVENT = EVENT_NAME;
    private String id;
    private transient String parseLinkMicId;

    public PLVRemoveMicSiteEvent() {
    }

    @Nullable
    public static PLVRemoveMicSiteEvent fromJson(String str) {
        return (PLVRemoveMicSiteEvent) PLVGsonUtil.fromJson(PLVRemoveMicSiteEvent.class, str);
    }

    public void emitToSocket() {
        PLVSocketManager.getInstance().emit("changeMicSite", toJson());
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getId() {
        return this.id;
    }

    public String getLinkMicIdFromEventId() {
        if (this.parseLinkMicId == null) {
            this.parseLinkMicId = getId().replaceFirst("^plv-", "").replaceFirst("-drag$", "");
        }
        return this.parseLinkMicId;
    }

    public PLVRemoveMicSiteEvent setEventIdByLinkMicId(String str) {
        return setId("plv-" + str + "-drag");
    }

    public PLVRemoveMicSiteEvent setId(String str) {
        this.id = str;
        this.parseLinkMicId = null;
        return this;
    }

    @NonNull
    public String toJson() {
        return PLVGsonUtil.toJson(this);
    }

    public String toString() {
        return "PLVRemoveMicSiteEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + '}';
    }

    public PLVRemoveMicSiteEvent(String str) {
        this.id = str;
    }
}
