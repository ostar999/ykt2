package com.psychiatrygarden.bean;

import com.arialyy.aria.core.download.DownloadEntity;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.entity.a;

/* loaded from: classes5.dex */
public class DownloadItem extends DownloadEntity implements SectionEntity {
    private String author;
    private String converedCurrentProgress;
    private int downloadCount;
    private boolean editMode;
    private boolean header;
    private String headerTitle;
    private boolean select;
    private String showFileName;
    private int stateCount;

    public String getAuthor() {
        return this.author;
    }

    public String getConveredCurrentProgress() {
        return this.converedCurrentProgress;
    }

    public int getDownloadCount() {
        return this.downloadCount;
    }

    public String getHeaderTitle() {
        return this.headerTitle;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity, com.chad.library.adapter.base.entity.MultiItemEntity
    public /* synthetic */ int getItemType() {
        return a.a(this);
    }

    public String getShowFileName() {
        return this.showFileName;
    }

    public int getStateCount() {
        return this.stateCount;
    }

    public String getStatusText() {
        int state = getState();
        return state != 0 ? state != 1 ? state != 2 ? state != 3 ? state != 4 ? state != 7 ? "未知状态" : "下载取消" : "下载中" : "等待下载" : "暂停下载" : "已下载" : "下载失败";
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity
    public boolean isHeader() {
        return this.header;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setConveredCurrentProgress(String converedCurrentProgress) {
        this.converedCurrentProgress = converedCurrentProgress;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setShowFileName(String showFileName) {
        this.showFileName = showFileName;
    }

    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }
}
