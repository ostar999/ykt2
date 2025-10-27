package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshChapterKnowledgeEvent {
    private String treeId;

    public RefreshChapterKnowledgeEvent(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeId() {
        return this.treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
}
