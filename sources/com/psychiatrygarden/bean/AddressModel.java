package com.psychiatrygarden.bean;

import java.util.List;
import top.defaults.view.Division;

/* loaded from: classes5.dex */
public class AddressModel implements Division {
    public List<Division> children;
    public String id;
    public int lvl;
    public String name;
    public AddressModel parent;
    public int parentId;
    private String parentName;

    @Override // top.defaults.view.Division
    public List<Division> getChildren() {
        return this.children;
    }

    @Override // top.defaults.view.Division
    public String getName() {
        return this.name;
    }

    @Override // top.defaults.view.Division
    public Division getParent() {
        return this.parent;
    }

    public String getParentName() {
        return this.parentName;
    }

    @Override // top.defaults.view.PickerView.PickerItem
    public String getText() {
        return this.name;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
