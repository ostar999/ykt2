package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class NextNodeData {
    private String id;
    private String nodeTitle;

    public NextNodeData(String id, String nodeTitle) {
        this.id = id;
        this.nodeTitle = nodeTitle;
    }

    public String getId() {
        return this.id;
    }

    public String getNodeTitle() {
        return this.nodeTitle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
}
