package com.hyphenate.easeui.modules.menu;

import java.util.Objects;

/* loaded from: classes4.dex */
public class MenuItemBean {
    private int groupId;
    private int itemId;
    private int order;
    private int resourceId;
    private String title;
    private boolean visible = true;

    public MenuItemBean(int i2, int i3, int i4, String str) {
        this.groupId = i2;
        this.itemId = i3;
        this.order = i4;
        this.title = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuItemBean menuItemBean = (MenuItemBean) obj;
        return this.groupId == menuItemBean.groupId && this.itemId == menuItemBean.itemId && this.order == menuItemBean.order;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getOrder() {
        return this.order;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.groupId), Integer.valueOf(this.itemId), Integer.valueOf(this.order));
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setGroupId(int i2) {
        this.groupId = i2;
    }

    public void setItemId(int i2) {
        this.itemId = i2;
    }

    public void setOrder(int i2) {
        this.order = i2;
    }

    public void setResourceId(int i2) {
        this.resourceId = i2;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setVisible(boolean z2) {
        this.visible = z2;
    }
}
