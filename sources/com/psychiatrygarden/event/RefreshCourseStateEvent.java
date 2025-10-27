package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshCourseStateEvent {
    private boolean isCollect;
    private boolean isComment;
    private boolean isDaKa;
    private boolean refreshCollection;
    private boolean refreshComment;
    private boolean refreshDaKa;
    private String vid;

    public RefreshCourseStateEvent() {
    }

    public String getVid() {
        return this.vid;
    }

    public boolean isCollect() {
        return this.isCollect;
    }

    public boolean isComment() {
        return this.isComment;
    }

    public boolean isDaKa() {
        return this.isDaKa;
    }

    public boolean isRefreshCollection() {
        return this.refreshCollection;
    }

    public boolean isRefreshComment() {
        return this.refreshComment;
    }

    public boolean isRefreshDaKa() {
        return this.refreshDaKa;
    }

    public void setCollect(boolean collect) {
        this.isCollect = collect;
    }

    public void setComment(boolean comment) {
        this.isComment = comment;
    }

    public void setDaKa(boolean daKa) {
        this.isDaKa = daKa;
    }

    public void setRefreshCollection(boolean refreshCollection) {
        this.refreshCollection = refreshCollection;
    }

    public void setRefreshComment(boolean refreshComment) {
        this.refreshComment = refreshComment;
    }

    public void setRefreshDaKa(boolean refreshDaKa) {
        this.refreshDaKa = refreshDaKa;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public RefreshCourseStateEvent(String vid, boolean isCollect) {
        this.vid = vid;
        this.isCollect = isCollect;
    }
}
