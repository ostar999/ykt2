package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class NodeRedoBean {
    private String chapter_id;
    private List<NodeRedoBean> children;
    private String id;
    private String name;
    private String node_id;
    private String parent_id;

    public String getChapter_id() {
        return this.chapter_id;
    }

    public List<NodeRedoBean> getChildren() {
        return this.children;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getNode_id() {
        return this.node_id;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChildren(List<NodeRedoBean> children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
