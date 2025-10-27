package com.plv.livescenes.chatroom.playback;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.chatroom.event.PLVEventHelper;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes4.dex */
public class PLVChatPlaybackImg extends PLVChatPlaybackBase {
    public static final String MSGTYPE_CHATIMG = "chatImg";
    private ContentBean content;

    public static class ContentBean {
        private String id;
        private String msgSource;
        private SizeBean size;
        private String status;
        private String type;
        private String uploadImgUrl;

        public static class SizeBean {
            private double height;
            private double width;

            public double getHeight() {
                return this.height;
            }

            public double getWidth() {
                return this.width;
            }

            public void setHeight(double d3) {
                this.height = d3;
            }

            public void setWidth(double d3) {
                this.width = d3;
            }

            public String toString() {
                return "SizeBean{height=" + this.height + ", width=" + this.width + '}';
            }
        }

        public String getId() {
            return this.id;
        }

        public String getMsgSource() {
            return this.msgSource;
        }

        public SizeBean getSize() {
            SizeBean sizeBean = this.size;
            if (sizeBean != null) {
                return sizeBean;
            }
            SizeBean sizeBean2 = new SizeBean();
            sizeBean2.height = ConvertUtils.dp2px(132.0f);
            sizeBean2.width = ConvertUtils.dp2px(132.0f);
            return sizeBean2;
        }

        public String getStatus() {
            return this.status;
        }

        public String getType() {
            return this.type;
        }

        public String getUploadImgUrl() {
            return PLVEventHelper.fixChatPic(this.uploadImgUrl);
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setMsgSource(String str) {
            this.msgSource = str;
        }

        public void setSize(SizeBean sizeBean) {
            this.size = sizeBean;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUploadImgUrl(String str) {
            this.uploadImgUrl = str;
        }

        public String toString() {
            return "ContentBean{id='" + this.id + CharPool.SINGLE_QUOTE + ", size=" + this.size + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", uploadImgUrl='" + this.uploadImgUrl + CharPool.SINGLE_QUOTE + ", msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }

    @Override // com.plv.livescenes.chatroom.playback.PLVChatPlaybackBase, com.plv.livescenes.chatroom.PLVBaseHolder
    public String toString() {
        return "PLVChatPlaybackImg{id=" + this.id + ", msg='" + this.msg + CharPool.SINGLE_QUOTE + ", time='" + this.time + CharPool.SINGLE_QUOTE + ", fontSize='" + this.fontSize + CharPool.SINGLE_QUOTE + ", fontMode='" + this.fontMode + CharPool.SINGLE_QUOTE + ", fontColor='" + this.fontColor + CharPool.SINGLE_QUOTE + ", timestamp='" + this.timestamp + CharPool.SINGLE_QUOTE + ", sessionId=" + this.sessionId + ", param2=" + this.param2 + ", msgType='" + this.msgType + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", content=" + this.content + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + '}';
    }
}
