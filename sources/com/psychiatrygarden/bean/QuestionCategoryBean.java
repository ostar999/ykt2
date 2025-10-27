package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.bean.SelectIdentityBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionCategoryBean implements Serializable {

    @SerializedName(alternate = {"knowledge_children"}, value = "children")
    private List<SelectIdentityBean.DataBean> children;
    private String id;
    private String label;
    private boolean select;
    private String title;
    private String type;
    private String vid;

    public List<SelectIdentityBean.DataBean> getChildren() {
        return this.children;
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getVid() {
        return this.vid;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setChildren(List<SelectIdentityBean.DataBean> children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
