package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshLastDoEvent {
    private String knowledgeId;
    private String nodeId;

    public RefreshLastDoEvent(String nodeId, String knowledgeId) {
        this.nodeId = nodeId;
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgeId() {
        return this.knowledgeId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
