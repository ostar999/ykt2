package com.psychiatrygarden.widget.treenode;

/* loaded from: classes6.dex */
public abstract class TreeNodeDelegate<T> {
    public TreeNodeAdapter<T> adapter;

    public abstract void convert(ViewHolder holder, TreeNode<T> treeNode);

    public abstract int getLayoutId();

    public abstract boolean isItemType(TreeNode<T> treeNode);
}
