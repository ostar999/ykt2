package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ImagesBean implements Serializable {
    private int b_height;
    private int b_width;
    private String b_img = "";
    private String s_img = "";
    private int s_width = 2;
    private int s_height = 1;
    private String status = "";
    private String videoId = "";
    private String watch_permission = "";

    public int getB_height() {
        return this.b_height;
    }

    public String getB_img() {
        return this.b_img;
    }

    public int getB_width() {
        return this.b_width;
    }

    public int getS_height() {
        return this.s_height;
    }

    public String getS_img() {
        return this.s_img;
    }

    public int getS_width() {
        return this.s_width;
    }

    public String getStatus() {
        return this.status;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getWatch_permission() {
        return this.watch_permission;
    }

    public void setB_height(int b_height) {
        this.b_height = b_height;
    }

    public void setB_img(String b_img) {
        this.b_img = b_img;
    }

    public void setB_width(int b_width) {
        this.b_width = b_width;
    }

    public void setS_height(int s_height) {
        this.s_height = s_height;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public void setS_width(int s_width) {
        this.s_width = s_width;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setWatch_permission(String watch_permission) {
        this.watch_permission = watch_permission;
    }
}
