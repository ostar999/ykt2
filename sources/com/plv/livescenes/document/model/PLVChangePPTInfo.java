package com.plv.livescenes.document.model;

/* loaded from: classes4.dex */
public class PLVChangePPTInfo {
    private int autoId;
    private int docType;
    private int isCamClosed;
    private int pageId;

    public PLVChangePPTInfo(int i2, int i3) {
        this.docType = 1;
        this.autoId = i2;
        this.pageId = i3;
    }

    public int getAutoId() {
        return this.autoId;
    }

    public int getDocType() {
        return this.docType;
    }

    public int getIsCamClosed() {
        return this.isCamClosed;
    }

    public int getPageId() {
        return this.pageId;
    }

    public void setAutoId(int i2) {
        this.autoId = i2;
    }

    public void setDocType(int i2) {
        this.docType = i2;
    }

    public void setIsCamClosed(int i2) {
        this.isCamClosed = i2;
    }

    public void setPageId(int i2) {
        this.pageId = i2;
    }

    public PLVChangePPTInfo(int i2) {
        this.autoId = -1;
        this.docType = 1;
        this.pageId = i2;
    }
}
