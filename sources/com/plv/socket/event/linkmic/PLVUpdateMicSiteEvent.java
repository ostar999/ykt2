package com.plv.socket.event.linkmic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.socket.impl.PLVSocketManager;

/* loaded from: classes5.dex */
public class PLVUpdateMicSiteEvent {
    public static final String EVENT_NAME = "update";
    public static final String SOCKET_EVENT_TYPE = "changeMicSite";
    private String EVENT = EVENT_NAME;
    private Float height;
    private String id;
    private Integer index;
    private Float left;
    private Float parentHeight;
    private Float parentWidth;
    private transient String parseLinkMicId;

    /* renamed from: top, reason: collision with root package name */
    private Float f10945top;
    private Float width;

    public PLVUpdateMicSiteEvent() {
    }

    @Nullable
    public static PLVUpdateMicSiteEvent fromJson(String str) {
        return (PLVUpdateMicSiteEvent) PLVGsonUtil.fromJson(PLVUpdateMicSiteEvent.class, str);
    }

    public boolean checkIsValid() {
        return (this.id == null || this.left == null || this.f10945top == null || this.width == null || this.height == null || this.parentWidth == null || this.parentHeight == null || this.index == null) ? false : true;
    }

    public void emitToSocket() {
        PLVSocketManager.getInstance().emit("changeMicSite", toJson());
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public Float getHeight() {
        return this.height;
    }

    public String getId() {
        return this.id;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Float getLeft() {
        return this.left;
    }

    public String getLinkMicIdFromEventId() {
        if (this.parseLinkMicId == null) {
            this.parseLinkMicId = getId().replaceFirst("^plv-", "").replaceFirst("-drag$", "");
        }
        return this.parseLinkMicId;
    }

    public Float getParentHeight() {
        return this.parentHeight;
    }

    public Float getParentWidth() {
        return this.parentWidth;
    }

    public Float getTop() {
        return this.f10945top;
    }

    public Float getWidth() {
        return this.width;
    }

    public PLVUpdateMicSiteEvent setEventIdByLinkMicId(String str) {
        return setId("plv-" + str + "-drag");
    }

    public PLVUpdateMicSiteEvent setHeight(Float f2) {
        this.height = f2;
        return this;
    }

    public PLVUpdateMicSiteEvent setId(String str) {
        this.id = str;
        this.parseLinkMicId = null;
        return this;
    }

    public PLVUpdateMicSiteEvent setIndex(Integer num) {
        this.index = num;
        return this;
    }

    public PLVUpdateMicSiteEvent setLeft(Float f2) {
        this.left = f2;
        return this;
    }

    public PLVUpdateMicSiteEvent setParentHeight(Float f2) {
        this.parentHeight = f2;
        return this;
    }

    public PLVUpdateMicSiteEvent setParentWidth(Float f2) {
        this.parentWidth = f2;
        return this;
    }

    public PLVUpdateMicSiteEvent setTop(Float f2) {
        this.f10945top = f2;
        return this;
    }

    public PLVUpdateMicSiteEvent setWidth(Float f2) {
        this.width = f2;
        return this;
    }

    @NonNull
    public String toJson() {
        return PLVGsonUtil.toJson(this);
    }

    public String toString() {
        return "PLVUpdateMicSiteEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", left=" + this.left + ", top=" + this.f10945top + ", width=" + this.width + ", height=" + this.height + ", parentWidth=" + this.parentWidth + ", parentHeight=" + this.parentHeight + ", index=" + this.index + '}';
    }

    public PLVUpdateMicSiteEvent(String str, Float f2, Float f3, Float f4, Float f5, Float f6, Float f7, Integer num) {
        this.id = str;
        this.left = f2;
        this.f10945top = f3;
        this.width = f4;
        this.height = f5;
        this.parentWidth = f6;
        this.parentHeight = f7;
        this.index = num;
    }
}
