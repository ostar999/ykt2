package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class MaterialSearchEvent {
    private String category;
    private String keyword;
    private String rank;
    private String type;

    public MaterialSearchEvent(String keyword, String rank, String type, String category) {
        this.keyword = keyword;
        this.rank = rank;
        this.type = type;
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public String getRank() {
        return this.rank;
    }

    public String getType() {
        return this.type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MaterialSearchEvent(String keyword) {
        this.keyword = keyword;
    }
}
