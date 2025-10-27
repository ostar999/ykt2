package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

/* loaded from: classes5.dex */
public class ChapterNodeBean extends JSectionEntity {
    public static final int NODE_LEVEL_CHAPTER = 1;
    public static final int NODE_LEVEL_CHILD = 2;
    public static final int NODE_LEVEL_CHILD_NODE = 3;
    private String chapter_id;
    private String id;
    private String name;
    private String parentId;
    private boolean select;
    private boolean selectAll;
    private boolean visible = true;
    private boolean expand = true;
    private int nodeLevel = 1;

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getId() {
        return this.id;
    }

    @Override // com.chad.library.adapter.base.entity.JSectionEntity, com.chad.library.adapter.base.entity.SectionEntity, com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.nodeLevel;
    }

    public String getName() {
        return this.name;
    }

    public int getNodeLevel() {
        return this.nodeLevel;
    }

    public String getParentId() {
        return this.parentId;
    }

    public boolean isExpand() {
        return this.expand;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity
    public boolean isHeader() {
        return false;
    }

    public boolean isSelect() {
        return this.select;
    }

    public boolean isSelectAll() {
        return this.selectAll;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
