package com.plv.livescenes.model.interact;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.plv.livescenes.chatroom.event.PLVEventHelper;

/* loaded from: classes5.dex */
public class PLVCardPushVO {
    private int code;
    private DataBean data;
    private String message;
    private String status;
    private boolean success;

    public static class DataBean {
        private String cardImage;
        private int conditionValue;
        private String countdownMsg;
        private int duration;
        private String enterEnabled;
        private String enterImage;
        private String imageType;
        private String link;
        private String redirectType;
        private String showCondition;
        private String title;

        public String getCardImage() {
            return PLVEventHelper.fixChatPic(this.cardImage);
        }

        public int getConditionValue() {
            return this.conditionValue;
        }

        public String getCountdownMsg() {
            return this.countdownMsg;
        }

        public int getDuration() {
            return this.duration;
        }

        public String getEnterEnabled() {
            return this.enterEnabled;
        }

        public String getEnterImage() {
            return PLVEventHelper.fixChatPic(this.enterImage);
        }

        public String getImageType() {
            return this.imageType;
        }

        public String getLink() {
            return this.link;
        }

        public String getRedirectType() {
            return this.redirectType;
        }

        public String getShowCondition() {
            return this.showCondition;
        }

        public String getTitle() {
            return this.title;
        }

        public void setCardImage(String str) {
            this.cardImage = str;
        }

        public void setConditionValue(int i2) {
            this.conditionValue = i2;
        }

        public void setCountdownMsg(String str) {
            this.countdownMsg = str;
        }

        public void setDuration(int i2) {
            this.duration = i2;
        }

        public void setEnterEnabled(String str) {
            this.enterEnabled = str;
        }

        public void setEnterImage(String str) {
            this.enterImage = str;
        }

        public void setImageType(String str) {
            this.imageType = str;
        }

        public void setLink(String str) {
            this.link = str;
        }

        public void setRedirectType(String str) {
            this.redirectType = str;
        }

        public void setShowCondition(String str) {
            this.showCondition = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String toString() {
            return "DataBean{cardImage='" + this.cardImage + CharPool.SINGLE_QUOTE + ", conditionValue=" + this.conditionValue + ", countdownMsg='" + this.countdownMsg + CharPool.SINGLE_QUOTE + ", duration=" + this.duration + ", enterEnabled='" + this.enterEnabled + CharPool.SINGLE_QUOTE + ", enterImage='" + this.enterImage + CharPool.SINGLE_QUOTE + ", imageType='" + this.imageType + CharPool.SINGLE_QUOTE + ", link='" + this.link + CharPool.SINGLE_QUOTE + ", redirectType='" + this.redirectType + CharPool.SINGLE_QUOTE + ", showCondition='" + this.showCondition + CharPool.SINGLE_QUOTE + ", title='" + this.title + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTipsMsg() {
        DataBean dataBean = this.data;
        return dataBean == null ? "" : TextUtils.isEmpty(dataBean.countdownMsg) ? "连续观看有奖励哦" : this.data.countdownMsg;
    }

    public boolean isCustomType() {
        DataBean dataBean = this.data;
        if (dataBean == null) {
            return false;
        }
        return "custom".equals(dataBean.imageType);
    }

    public boolean isGiftboxType() {
        DataBean dataBean = this.data;
        if (dataBean == null) {
            return false;
        }
        return "giftbox".equals(dataBean.imageType);
    }

    public boolean isRedpackType() {
        DataBean dataBean = this.data;
        if (dataBean == null) {
            return false;
        }
        return "redpack".equals(dataBean.imageType);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setSuccess(boolean z2) {
        this.success = z2;
    }

    public String toString() {
        return "PLVCardPushVO{code=" + this.code + ", data=" + this.data + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", success=" + this.success + '}';
    }
}
