package com.psychiatrygarden.widget.treenode;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class TreeNode<T> {
    private List<TreeNode<T>> children;
    private int customerLevel;
    private boolean expand;
    private TreeNode<T> parent;
    private boolean select;
    private T value;

    public TreeNode() {
    }

    public void addChild(TreeNode<T> child) {
        if (child == null) {
            return;
        }
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(child);
        child.setParent(this);
    }

    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    public int getCustomerLevel() {
        return this.customerLevel;
    }

    public int getLevel() {
        TreeNode<T> treeNode = this.parent;
        if (treeNode == null) {
            return 0;
        }
        return treeNode.getLevel() + 1;
    }

    public TreeNode<T> getParent() {
        return this.parent;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public boolean isLeaf() {
        List<TreeNode<T>> list = this.children;
        return list == null || list.size() == 0;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
        if (children != null) {
            for (TreeNode<T> treeNode : children) {
                if (treeNode != null) {
                    treeNode.setParent(this);
                }
            }
        }
    }

    public void setCustomerLevel(int customerLevel) {
        this.customerLevel = customerLevel;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode(T value) {
        this.value = value;
    }
}
