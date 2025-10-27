package com.plv.livescenes.upload.model;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVPPTConvertStatusVO {
    private String convertStatus;
    private String errorMsg;
    private String fileId;
    private String htmlUrl;
    private int imageCount;
    private List<String> images;
    private List<String> smallImages;
    private int totalPage;
    private String type;

    public String getConvertStatus() {
        return this.convertStatus;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    public int getImageCount() {
        return this.imageCount;
    }

    public List<String> getImages() {
        return this.images;
    }

    public List<String> getSmallImages() {
        return this.smallImages;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public String getType() {
        return this.type;
    }

    public void setConvertStatus(String str) {
        this.convertStatus = str;
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public void setFileId(String str) {
        this.fileId = str;
    }

    public void setHtmlUrl(String str) {
        this.htmlUrl = str;
    }

    public void setImageCount(int i2) {
        this.imageCount = i2;
    }

    public void setImages(List<String> list) {
        this.images = list;
    }

    public void setSmallImages(List<String> list) {
        this.smallImages = list;
    }

    public void setTotalPage(int i2) {
        this.totalPage = i2;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String toString() {
        return "PLVPPTConvertStatusVO{totalPage=" + this.totalPage + ", imageCount=" + this.imageCount + ", errorMsg='" + this.errorMsg + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", htmlUrl='" + this.htmlUrl + CharPool.SINGLE_QUOTE + ", convertStatus='" + this.convertStatus + CharPool.SINGLE_QUOTE + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", smallImages=" + this.smallImages + ", images=" + this.images + '}';
    }
}
