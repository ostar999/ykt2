package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class TabColumnSortEvent {
    private String sortList;

    public TabColumnSortEvent(String sortList) {
        this.sortList = sortList;
    }

    public String getSortList() {
        return this.sortList;
    }

    public void setSortList(String sortList) {
        this.sortList = sortList;
    }
}
