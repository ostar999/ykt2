package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ChannelItems implements Serializable {
    public Integer id;
    public String initials;
    public Integer is_default;
    public String pid;
    public Integer sort;
    public String title;
    public String u_sort;

    public Integer getId() {
        return this.id;
    }

    public String getInitials() {
        return this.initials;
    }

    public Integer getIs_default() {
        return this.is_default;
    }

    public String getPid() {
        return this.pid;
    }

    public Integer getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public String getU_sort() {
        return this.u_sort;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setU_sort(String u_sort) {
        this.u_sort = u_sort;
    }
}
