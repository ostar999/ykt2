package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgeFilterEvent {
    private boolean filter;
    private List<String> ids;

    public KnowledgeFilterEvent(boolean filter, List<String> ids) {
        this.filter = filter;
        this.ids = ids;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public boolean isFilter() {
        return this.filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public KnowledgeFilterEvent(List<String> ids) {
        this.ids = ids;
    }
}
