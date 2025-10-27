package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class PushCommentNoteUploadPicItem {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_SCREEN_SHOT = 2;
    public static final int UPLOAD_STATUS_COMPLETE = 2;
    public static final int UPLOAD_STATUS_ERROR = 3;
    public static final int UPLOAD_STATUS_LOADING = 1;
    private String bImg;
    private String picFilePath;
    private String sImg;
    private int status;
    private int type = 1;

    public String getPicFilePath() {
        return this.picFilePath;
    }

    public int getStatus() {
        return this.status;
    }

    public int getType() {
        return this.type;
    }

    public String getbImg() {
        return this.bImg;
    }

    public String getsImg() {
        return this.sImg;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setbImg(String bImg) {
        this.bImg = bImg;
    }

    public void setsImg(String sImg) {
        this.sImg = sImg;
    }
}
