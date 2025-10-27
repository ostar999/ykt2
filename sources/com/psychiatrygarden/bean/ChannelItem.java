package com.psychiatrygarden.bean;

import cn.hutool.core.text.StrPool;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class ChannelItem implements Serializable {
    private static final long serialVersionUID = -6465237897027410019L;
    public String Today_topic_num;
    public Integer id;
    public String initials;
    public String name;
    public Integer orderId;
    public Integer selected;
    public Integer sort;
    public String u_sort;

    public ChannelItem() {
    }

    public int getId() {
        return this.id.intValue();
    }

    public String getInitials() {
        return this.initials;
    }

    public String getName() {
        return this.name;
    }

    public int getOrderId() {
        return this.orderId.intValue();
    }

    public Integer getSelected() {
        return this.selected;
    }

    public Integer getSort() {
        return this.sort;
    }

    public String getToday_topic_num() {
        return this.Today_topic_num;
    }

    public String getU_sort() {
        return this.u_sort;
    }

    public void setId(int paramInt) {
        this.id = Integer.valueOf(paramInt);
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setOrderId(int paramInt) {
        this.orderId = Integer.valueOf(paramInt);
    }

    public void setSelected(Integer paramInteger) {
        this.selected = paramInteger;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setToday_topic_num(String today_topic_num) {
        this.Today_topic_num = today_topic_num;
    }

    public void setU_sort(String u_sort) {
        this.u_sort = u_sort;
    }

    public String toString() {
        return "ChannelItem [id=" + this.id + ", name=" + this.name + ", selected=" + this.selected + ", sort=" + this.sort + StrPool.BRACKET_END;
    }

    public ChannelItem(int id, String name, int orderId, int selected) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = Integer.valueOf(selected);
        this.sort = Integer.valueOf(this.sort.intValue());
    }
}
