package com.plv.livescenes.document.model;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVPPTStatus implements PLVFoundationVO {
    private int autoId;
    private String docType;
    private boolean isAnimation;
    private MaxTeacherOp maxTeacherOp;
    private int pageId;
    private String roomId;
    private String slideEvent;
    private int step;
    private int total;
    private String userId;

    public static class MaxTeacherOp {
        private int autoId;
        private int pageId;
        private int step;

        public int getAutoId() {
            return this.autoId;
        }

        public int getPageId() {
            return this.pageId;
        }

        public int getStep() {
            return this.step;
        }

        public void setAutoId(int i2) {
            this.autoId = i2;
        }

        public void setPageId(int i2) {
            this.pageId = i2;
        }

        public void setStep(int i2) {
            this.step = i2;
        }
    }

    public int getAutoId() {
        return this.autoId;
    }

    public String getDocType() {
        return this.docType;
    }

    public MaxTeacherOp getMaxTeacherOp() {
        return this.maxTeacherOp;
    }

    public int getPageId() {
        return this.pageId;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSlideEvent() {
        return this.slideEvent;
    }

    public int getStep() {
        return this.step;
    }

    public int getTotal() {
        return this.total;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isAnimation() {
        return this.isAnimation;
    }

    public boolean isIsAnimation() {
        return this.isAnimation;
    }

    public boolean isWhiteBoard() {
        return this.autoId == 0;
    }

    public void setAnimation(boolean z2) {
        this.isAnimation = z2;
    }

    public void setAutoId(int i2) {
        this.autoId = i2;
    }

    public void setDocType(String str) {
        this.docType = str;
    }

    public void setIsAnimation(boolean z2) {
        this.isAnimation = z2;
    }

    public void setMaxTeacherOp(MaxTeacherOp maxTeacherOp) {
        this.maxTeacherOp = maxTeacherOp;
    }

    public void setPageId(int i2) {
        this.pageId = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSlideEvent(String str) {
        this.slideEvent = str;
    }

    public void setStep(int i2) {
        this.step = i2;
    }

    public void setTotal(int i2) {
        this.total = i2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
