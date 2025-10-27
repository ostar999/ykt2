package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgeNode {
    private List<KnowledgeNodeChild> children;
    private String id;
    private String name;

    public static class KnowledgeNodeChild {
        private String chapter_id;
        private List<KnowledgeNodeChild> children;
        private String id;
        private String name;
        private String parent_id;

        public String getChapter_id() {
            return this.chapter_id;
        }

        public List<KnowledgeNodeChild> getChildren() {
            return this.children;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getParent_id() {
            return this.parent_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChildren(List<KnowledgeNodeChild> children) {
            this.children = children;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }

    public List<KnowledgeNodeChild> getChildren() {
        return this.children;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setChildren(List<KnowledgeNodeChild> children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
