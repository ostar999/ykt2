package com.psychiatrygarden.widget.treenode;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class TreeNodeHelper {
    public static <T> void collapseAll(List<TreeNode<T>> parentList) {
        if (parentList == null) {
            return;
        }
        for (TreeNode<T> treeNode : parentList) {
            treeNode.setExpand(false);
            collapseAll(treeNode.getChildren());
        }
    }

    public static <T> void expandAll(List<TreeNode<T>> parentList) {
        if (parentList == null) {
            return;
        }
        for (TreeNode<T> treeNode : parentList) {
            treeNode.setExpand(true);
            expandAll(treeNode.getChildren());
        }
    }

    public static <T> void expandLevel(List<TreeNode<T>> parentList, int level) {
        if (parentList == null) {
            return;
        }
        for (TreeNode<T> treeNode : parentList) {
            if (treeNode.getLevel() < level) {
                treeNode.setExpand(true);
                expandLevel(treeNode.getChildren(), level);
            }
        }
    }

    public static <T> List<TreeNode<T>> getExpendedChildren(TreeNode<T> parent) {
        ArrayList arrayList = new ArrayList();
        List<TreeNode<T>> children = parent.getChildren();
        if (children != null) {
            for (TreeNode<T> treeNode : children) {
                arrayList.add(treeNode);
                if (treeNode.isExpand()) {
                    arrayList.addAll(getExpendedChildren(treeNode));
                }
            }
        }
        return arrayList;
    }
}
