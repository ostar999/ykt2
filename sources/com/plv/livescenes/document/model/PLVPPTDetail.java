package com.plv.livescenes.document.model;

/* loaded from: classes4.dex */
public class PLVPPTDetail extends PLVPPTBaseModel {
    private int autoId;
    private String imageUrl;

    public PLVPPTDetail(int i2, String str, int i3) {
        this.autoId = i2;
        this.imageUrl = str;
        this.pos = i3;
    }

    public int getAutoId() {
        return this.autoId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override // com.plv.livescenes.document.model.PLVPPTBaseModel
    public int getPos() {
        return this.pos;
    }

    public void setAutoId(int i2) {
        this.autoId = i2;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    @Override // com.plv.livescenes.document.model.PLVPPTBaseModel
    public void setPos(int i2) {
        this.pos = i2;
    }
}
