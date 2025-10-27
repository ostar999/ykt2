package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes5.dex */
public class QuestionCountItem implements MultiItemEntity {
    private boolean select;
    private String title;
    private int type;

    public QuestionCountItem(String title, boolean select, int type) {
        this.title = title;
        this.select = select;
        this.type = type;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
