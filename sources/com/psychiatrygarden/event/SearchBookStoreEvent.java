package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class SearchBookStoreEvent {
    private String keyword;

    public SearchBookStoreEvent(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
