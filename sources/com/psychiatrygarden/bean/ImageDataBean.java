package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ImageDataBean implements Serializable {
    public String b_img;
    public long id;
    public String s_img;

    public String getB_img() {
        return this.b_img;
    }

    public long getId() {
        return this.id;
    }

    public String getS_img() {
        return this.s_img;
    }

    public void setB_img(String b_img) {
        this.b_img = b_img;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }
}
