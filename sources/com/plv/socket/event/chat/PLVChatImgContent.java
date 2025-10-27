package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVEventHelper;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes5.dex */
public class PLVChatImgContent {
    private String id;
    private String localImgPath;
    private String msg;
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

    public String getLocalImgPath() {
        return this.localImgPath;
    }

    public String getMsg() {
        return this.msg;
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

    public void setLocalImgPath(String str) {
        this.localImgPath = str;
    }

    public void setMsg(String str) {
        this.msg = str;
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
        return "PLVChatImgContent{id='" + this.id + CharPool.SINGLE_QUOTE + ", msg='" + this.msg + CharPool.SINGLE_QUOTE + ", msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + ", size=" + this.size + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", uploadImgUrl='" + this.uploadImgUrl + CharPool.SINGLE_QUOTE + ", localImgPath='" + this.localImgPath + CharPool.SINGLE_QUOTE + '}';
    }
}
