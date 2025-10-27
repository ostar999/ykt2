package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class CourseSearchData {
    private boolean isRefresh;
    private String keyword;

    public CourseSearchData(String keyword, boolean isRefresh) {
        this.keyword = keyword;
        this.isRefresh = isRefresh;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public boolean isRefresh() {
        return this.isRefresh;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setRefresh(boolean refresh) {
        this.isRefresh = refresh;
    }
}
