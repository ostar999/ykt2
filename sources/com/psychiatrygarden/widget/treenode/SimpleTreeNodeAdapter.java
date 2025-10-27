package com.psychiatrygarden.widget.treenode;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class SimpleTreeNodeAdapter<T> extends TreeNodeAdapter<T> {
    public SimpleTreeNodeAdapter(Context context, final int layoutId, @NonNull List<TreeNode<T>> rootList) {
        super(context, rootList);
        addItemViewDelegate(new TreeNodeDelegate<T>() { // from class: com.psychiatrygarden.widget.treenode.SimpleTreeNodeAdapter.1
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, TreeNode<T> treeNode) {
                SimpleTreeNodeAdapter.this.convert(this.adapter, holder, treeNode);
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return layoutId;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<T> treeNode) {
                return true;
            }
        });
    }

    public abstract void convert(TreeNodeAdapter<T> adapter, ViewHolder holder, TreeNode<T> treeNode);
}
