package cn.hutool.core.lang.tree;

import java.io.Serializable;

/* loaded from: classes.dex */
public class TreeNodeConfig implements Serializable {
    public static TreeNodeConfig DEFAULT_CONFIG = new TreeNodeConfig();
    private static final long serialVersionUID = 1;
    private Integer deep;
    private String idKey = "id";
    private String parentIdKey = "parentId";
    private String weightKey = "weight";
    private String nameKey = "name";
    private String childrenKey = "children";

    public String getChildrenKey() {
        return this.childrenKey;
    }

    public Integer getDeep() {
        return this.deep;
    }

    public String getIdKey() {
        return this.idKey;
    }

    public String getNameKey() {
        return this.nameKey;
    }

    public String getParentIdKey() {
        return this.parentIdKey;
    }

    public String getWeightKey() {
        return this.weightKey;
    }

    public TreeNodeConfig setChildrenKey(String str) {
        this.childrenKey = str;
        return this;
    }

    public TreeNodeConfig setDeep(Integer num) {
        this.deep = num;
        return this;
    }

    public TreeNodeConfig setIdKey(String str) {
        this.idKey = str;
        return this;
    }

    public TreeNodeConfig setNameKey(String str) {
        this.nameKey = str;
        return this;
    }

    public TreeNodeConfig setParentIdKey(String str) {
        this.parentIdKey = str;
        return this;
    }

    public TreeNodeConfig setWeightKey(String str) {
        this.weightKey = str;
        return this;
    }
}
