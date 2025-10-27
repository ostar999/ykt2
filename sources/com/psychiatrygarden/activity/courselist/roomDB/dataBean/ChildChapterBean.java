package com.psychiatrygarden.activity.courselist.roomDB.dataBean;

import androidx.room.Ignore;

/* loaded from: classes5.dex */
public class ChildChapterBean {
    public int id;
    public String nodeChapterId;
    public int status;
    public String title;

    @Ignore
    public ChildChapterBean(String nodeChapterId, int status, String title) {
        this.nodeChapterId = nodeChapterId;
        this.status = status;
        this.title = title;
    }

    public String getNodeChapterId() {
        return this.nodeChapterId;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setNodeChapterId(String nodeChapterId) {
        this.nodeChapterId = nodeChapterId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChildChapterBean() {
    }
}
