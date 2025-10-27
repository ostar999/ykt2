package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes5.dex */
public class SelectPicItem implements MultiItemEntity {
    public static final int TYPE_ADD = 1;
    public static final int TYPE_PIC = 2;
    private String localPath;
    private int type;
    private String uploadUrl;
    private boolean uploading;

    public SelectPicItem(int type) {
        this.type = type;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public int getType() {
        return this.type;
    }

    public String getUploadUrl() {
        return this.uploadUrl;
    }

    public boolean isUploading() {
        return this.uploading;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }
}
