package com.plv.socket.event.tuwen;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVSetTopImageTextEvent {
    private String EVENT;
    private DataBean data;

    public static class DataBean {
        private String channelId;
        private ContentBean content;
        private String id;

        /* renamed from: top, reason: collision with root package name */
        private String f10949top;

        public static class ContentBean {
            private int channelId;
            private long createdTime;
            private int id;
            private List<String> images;
            private String text;

            /* renamed from: top, reason: collision with root package name */
            private String f10950top;

            public int getChannelId() {
                return this.channelId;
            }

            public long getCreatedTime() {
                return this.createdTime;
            }

            public int getId() {
                return this.id;
            }

            public List<String> getImages() {
                return this.images;
            }

            public String getText() {
                return this.text;
            }

            public String getTop() {
                return this.f10950top;
            }

            public void setChannelId(int i2) {
                this.channelId = i2;
            }

            public void setCreatedTime(long j2) {
                this.createdTime = j2;
            }

            public void setId(int i2) {
                this.id = i2;
            }

            public void setImages(List<String> list) {
                this.images = list;
            }

            public void setText(String str) {
                this.text = str;
            }

            public void setTop(String str) {
                this.f10950top = str;
            }

            public String toString() {
                return "ContentBean{top='" + this.f10950top + CharPool.SINGLE_QUOTE + ", createdTime=" + this.createdTime + ", id=" + this.id + ", text='" + this.text + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", images=" + this.images + '}';
            }
        }

        public String getChannelId() {
            return this.channelId;
        }

        public ContentBean getContent() {
            return this.content;
        }

        public String getId() {
            return this.id;
        }

        public String getTop() {
            return this.f10949top;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setContent(ContentBean contentBean) {
            this.content = contentBean;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setTop(String str) {
            this.f10949top = str;
        }

        public String toString() {
            return "DataBean{top='" + this.f10949top + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", content=" + this.content + '}';
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
        return "PLVSetTopImageTextEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
