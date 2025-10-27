package com.psychiatrygarden.widget.treenode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class TreeNodeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    private LayoutInflater layoutInflater;
    private int maxSelectCount;
    private List<TreeNode<T>> rootTreeNodeList;
    private List<TreeNodeDelegate<T>> treeNodeDelegateList = new ArrayList();
    private List<TreeNode<T>> showTreeNodeList = new ArrayList();
    private List<TreeNode<T>> selectTreeNodeList = new ArrayList();

    public TreeNodeAdapter(Context context, @NonNull List<TreeNode<T>> rootList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.rootTreeNodeList = rootList;
        init();
    }

    private void init() {
        this.showTreeNodeList.clear();
        for (TreeNode<T> treeNode : this.rootTreeNodeList) {
            this.showTreeNodeList.add(treeNode);
            if (treeNode.isExpand()) {
                this.showTreeNodeList.addAll(TreeNodeHelper.getExpendedChildren(treeNode));
            }
        }
    }

    public void addItemViewDelegate(TreeNodeDelegate<T> delegate) {
        delegate.adapter = this;
        this.treeNodeDelegateList.add(delegate);
    }

    public void changeSelectTreeNode(TreeNode<T> treeNode) {
        selectTreeNode(treeNode, !treeNode.isSelect());
    }

    public void collapseAllTreeNode() {
        TreeNodeHelper.collapseAll(this.rootTreeNodeList);
        refreshTreeNode();
    }

    public void collapseTreeNode(TreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }
        treeNode.setExpand(!treeNode.isExpand());
        int iIndexOf = this.showTreeNodeList.indexOf(treeNode);
        List expendedChildren = TreeNodeHelper.getExpendedChildren(treeNode);
        if (iIndexOf < 0 || iIndexOf > this.showTreeNodeList.size() - 1 || expendedChildren == null || expendedChildren.size() == 0) {
            return;
        }
        this.showTreeNodeList.removeAll(expendedChildren);
        notifyItemRangeRemoved(iIndexOf + 1, expendedChildren.size());
        notifyTreeNode(treeNode);
    }

    public void expandAllTreeNode() {
        TreeNodeHelper.expandAll(this.rootTreeNodeList);
        refreshTreeNode();
    }

    public void expandLevelTreeNode(int level) {
        TreeNodeHelper.expandLevel(this.rootTreeNodeList, level);
        refreshTreeNode();
    }

    public void expandOrCollapseTreeNode(TreeNode<T> treeNode) {
        if (treeNode.isExpand()) {
            collapseTreeNode(treeNode);
        } else {
            expandTreeNode(treeNode);
        }
    }

    public void expandTreeNode(TreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }
        treeNode.setExpand(!treeNode.isExpand());
        int iIndexOf = this.showTreeNodeList.indexOf(treeNode);
        List expendedChildren = TreeNodeHelper.getExpendedChildren(treeNode);
        if (iIndexOf < 0 || iIndexOf > this.showTreeNodeList.size() - 1 || expendedChildren == null || expendedChildren.size() == 0) {
            return;
        }
        int i2 = iIndexOf + 1;
        this.showTreeNodeList.addAll(i2, expendedChildren);
        notifyItemRangeInserted(i2, expendedChildren.size());
        notifyTreeNode(treeNode);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.showTreeNodeList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        TreeNode<T> treeNode = this.showTreeNodeList.get(position);
        for (int i2 = 0; i2 < this.treeNodeDelegateList.size(); i2++) {
            if (this.treeNodeDelegateList.get(i2).isItemType(treeNode)) {
                return i2;
            }
        }
        throw new IllegalArgumentException("No TreeNodeDelegate added that matches position=" + position + " in data source");
    }

    public List<TreeNode<T>> getSelectTreeNodeList() {
        return this.selectTreeNodeList;
    }

    public void notifyTreeNode(TreeNode<T> treeNode) {
        int iIndexOf = this.showTreeNodeList.indexOf(treeNode);
        if (iIndexOf < 0 || iIndexOf > this.showTreeNodeList.size() - 1) {
            return;
        }
        notifyItemChanged(this.showTreeNodeList.indexOf(treeNode));
    }

    public void refreshTreeNode() {
        init();
        notifyDataSetChanged();
    }

    public void removeItemViewDelegate(TreeNodeDelegate<T> delegate) {
        this.treeNodeDelegateList.remove(delegate);
    }

    public void selectTreeNode(TreeNode<T> treeNode, boolean select) {
        treeNode.setSelect(select);
        if (!select) {
            if (this.selectTreeNodeList.contains(treeNode)) {
                this.selectTreeNodeList.remove(treeNode);
                notifyTreeNode(treeNode);
                return;
            }
            return;
        }
        if (!this.selectTreeNodeList.contains(treeNode)) {
            this.selectTreeNodeList.add(treeNode);
            notifyTreeNode(treeNode);
        }
        if (this.maxSelectCount == 0 || this.selectTreeNodeList.size() <= this.maxSelectCount) {
            return;
        }
        TreeNode<T> treeNode2 = this.selectTreeNodeList.get(0);
        treeNode2.setSelect(false);
        this.selectTreeNodeList.remove(treeNode2);
        notifyTreeNode(treeNode2);
    }

    public void setMaxSelectCount(int maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.treeNodeDelegateList.get(getItemViewType(position)).convert(holder, this.showTreeNodeList.get(position));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this.layoutInflater.inflate(this.treeNodeDelegateList.get(viewType).getLayoutId(), parent, false));
    }
}
