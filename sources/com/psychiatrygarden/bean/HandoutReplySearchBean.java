package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.CommentSearchBean;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class HandoutReplySearchBean extends CommentSearchBean.DataBean implements Serializable {
    private String cat_id;
    private String h5_path;
    private String html_path;
    private String is_rich_text;
    private String json_path;

    public String getCat_id() {
        return this.cat_id;
    }

    public String getH5_path() {
        return this.h5_path;
    }

    public String getHtml_path() {
        return this.html_path;
    }

    public String getIs_rich_text() {
        return this.is_rich_text;
    }

    public String getJson_path() {
        return this.json_path;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setH5_path(String h5_path) {
        this.h5_path = h5_path;
    }

    public void setHtml_path(String html_path) {
        this.html_path = html_path;
    }

    public void setIs_rich_text(String is_rich_text) {
        this.is_rich_text = is_rich_text;
    }

    public void setJson_path(String json_path) {
        this.json_path = json_path;
    }
}
