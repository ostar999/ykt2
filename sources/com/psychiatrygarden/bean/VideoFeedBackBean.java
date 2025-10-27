package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class VideoFeedBackBean {

    @SerializedName(alternate = {"evaluate_id"}, value = "id")
    private String id;
    private String star_level = "5";
    private String title;

    public String getId() {
        return this.id;
    }

    public String getStar_level() {
        return this.star_level;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
