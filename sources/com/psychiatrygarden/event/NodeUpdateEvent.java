package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class NodeUpdateEvent {
    private String nodeId;
    private String right_rate;
    private String user_answer_total;

    public NodeUpdateEvent(String nodeId, String right_rate, String user_answer_total) {
        this.nodeId = nodeId;
        this.right_rate = right_rate;
        this.user_answer_total = user_answer_total;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public String getRight_rate() {
        return this.right_rate;
    }

    public String getUser_answer_total() {
        return this.user_answer_total;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setRight_rate(String right_rate) {
        this.right_rate = right_rate;
    }

    public void setUser_answer_total(String user_answer_total) {
        this.user_answer_total = user_answer_total;
    }
}
