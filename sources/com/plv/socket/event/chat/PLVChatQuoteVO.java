package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVChatQuoteVO {
    private String content;
    private ImageBean image;
    private String nick;
    private Object[] objects;
    private String userId;

    public static class ImageBean {
        private double height;
        private String url;
        private double width;

        public double getHeight() {
            return this.height;
        }

        public String getUrl() {
            return this.url;
        }

        public double getWidth() {
            return this.width;
        }

        public void setHeight(double d3) {
            this.height = d3;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public void setWidth(double d3) {
            this.width = d3;
        }
    }

    public String getContent() {
        return this.content;
    }

    public ImageBean getImage() {
        return this.image;
    }

    public String getNick() {
        return this.nick;
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isSpeakMessage() {
        return this.content != null;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setImage(ImageBean imageBean) {
        this.image = imageBean;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setObjects(Object... objArr) {
        this.objects = objArr;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVChatQuoteVO{userId='" + this.userId + CharPool.SINGLE_QUOTE + ", content='" + this.content + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", image='" + this.image + CharPool.SINGLE_QUOTE + '}';
    }
}
