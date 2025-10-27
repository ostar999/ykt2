package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class HandoutImage implements Serializable {
    public String aid;
    private String cid;
    public String cover;
    private String hot_img;
    public String id;
    public String json_url;
    public String title;
    public String type;
    public String comment_count = "0";
    public String is_share = "";
    public String is_focus = "";
    private String is_read = "";

    public String getAid() {
        return this.aid;
    }

    public String getCid() {
        return this.cid;
    }

    public String getComment_count() {
        return this.comment_count;
    }

    public String getCover() {
        return this.cover;
    }

    public String getHot_img() {
        return this.hot_img;
    }

    public String getId() {
        return this.id;
    }

    public String getIs_focus() {
        return this.is_focus;
    }

    public String getIs_read() {
        return this.is_read;
    }

    public String getIs_share() {
        return this.is_share;
    }

    public String getJson_url() {
        return this.json_url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setHot_img(String hot_img) {
        this.hot_img = hot_img;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_focus(String is_focus) {
        this.is_focus = is_focus;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public void setIs_share(String is_share) {
        this.is_share = is_share;
    }

    public void setJson_url(String json_url) {
        this.json_url = json_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }
}
