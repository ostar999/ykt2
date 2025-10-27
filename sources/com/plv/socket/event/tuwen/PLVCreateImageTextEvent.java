package com.plv.socket.event.tuwen;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVCreateImageTextEvent {
    private String EVENT;
    private DataBean data;

    public static class DataBean {
        private int channelId;
        private long createdTime;
        private String id;
        private List<String> images;
        private boolean isEdit;
        private String text;

        /* renamed from: top, reason: collision with root package name */
        private String f10948top;
        private int total;

        public int getChannelId() {
            return this.channelId;
        }

        public long getCreatedTime() {
            return this.createdTime;
        }

        public String getId() {
            return this.id;
        }

        public List<String> getImages() {
            return this.images;
        }

        public String getText() {
            return this.text;
        }

        public String getTop() {
            return this.f10948top;
        }

        public int getTotal() {
            return this.total;
        }

        public boolean isIsEdit() {
            return this.isEdit;
        }

        public void setChannelId(int i2) {
            this.channelId = i2;
        }

        public void setCreatedTime(long j2) {
            this.createdTime = j2;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setImages(List<String> list) {
            this.images = list;
        }

        public void setIsEdit(boolean z2) {
            this.isEdit = z2;
        }

        public void setText(String str) {
            this.text = str;
        }

        public void setTop(String str) {
            this.f10948top = str;
        }

        public void setTotal(int i2) {
            this.total = i2;
        }

        public String toString() {
            return "DataBean{total=" + this.total + ", top='" + this.f10948top + CharPool.SINGLE_QUOTE + ", isEdit=" + this.isEdit + ", createdTime=" + this.createdTime + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", text='" + this.text + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", images=" + this.images + '}';
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

    public String toString() {
        return "PLVCreateImageTextEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
