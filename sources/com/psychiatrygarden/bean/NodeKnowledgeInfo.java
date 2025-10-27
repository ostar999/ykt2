package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class NodeKnowledgeInfo {
    private List<KnowledgeInfo> knowledge;
    private NodeInfo node;

    public static class KnowledgeInfo {
        private String knowledge_id;
        private String right_rate;
        private String user_answer_total;

        public String getKnowledge_id() {
            return this.knowledge_id;
        }

        public String getRight_rate() {
            return this.right_rate;
        }

        public String getUser_answer_total() {
            return this.user_answer_total;
        }

        public void setKnowledge_id(String knowledge_id) {
            this.knowledge_id = knowledge_id;
        }

        public void setRight_rate(String right_rate) {
            this.right_rate = right_rate;
        }

        public void setUser_answer_total(String user_answer_total) {
            this.user_answer_total = user_answer_total;
        }
    }

    public static class NodeInfo {
        private String right_rate;
        private String user_answer_total;

        public String getRight_rate() {
            return this.right_rate;
        }

        public String getUser_answer_total() {
            return this.user_answer_total;
        }

        public void setRight_rate(String right_rate) {
            this.right_rate = right_rate;
        }

        public void setUser_answer_total(String user_answer_total) {
            this.user_answer_total = user_answer_total;
        }
    }

    public List<KnowledgeInfo> getKnowledge() {
        return this.knowledge;
    }

    public NodeInfo getNode() {
        return this.node;
    }

    public void setKnowledge(List<KnowledgeInfo> knowledge) {
        this.knowledge = knowledge;
    }

    public void setNode(NodeInfo node) {
        this.node = node;
    }
}
