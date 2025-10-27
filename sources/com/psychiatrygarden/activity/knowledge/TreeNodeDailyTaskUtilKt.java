package com.psychiatrygarden.activity.knowledge;

import android.text.TextUtils;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.widget.treenode.TreeNode;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u001a4\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u001a\u0010\u0010\t\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\u000b\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\f\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\r\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a \u0010\u000e\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002¨\u0006\u0010"}, d2 = {"dailyTaskDataItemToTreeNodeData", "", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "Lcom/psychiatrygarden/activity/knowledge/KnowledgeChartNodeBeanTreeNode;", "listData", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "lastNodeId", "", "lastKnowledgeNodeId", "isBottomRadiusItem", "", "isLastItem", "isOnlyOneItem", "isTopRadiusItem", "treeNodeEquals", "treeNode2", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class TreeNodeDailyTaskUtilKt {
    @NotNull
    public static final List<TreeNode<KnowledgeChartNodeBeanTreeNode>> dailyTaskDataItemToTreeNodeData(@NotNull List<? extends KnowledgeChartNodeBean> listData, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(listData, "listData");
        ArrayList arrayList = new ArrayList();
        int size = listData.size();
        for (int i2 = 0; i2 < size; i2++) {
            KnowledgeChartNodeBean knowledgeChartNodeBean = listData.get(i2);
            knowledgeChartNodeBean.setContinueDo(Intrinsics.areEqual(str, knowledgeChartNodeBean.getId()));
            TreeNode treeNode = new TreeNode(new KnowledgeChartNodeBeanTreeNode(knowledgeChartNodeBean));
            treeNode.setCustomerLevel(0);
            arrayList.add(treeNode);
            if (knowledgeChartNodeBean.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(knowledgeChartNodeBean.getChildren(), "itemRoot.children");
                if (!r5.isEmpty()) {
                    List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                    int size2 = children.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        KnowledgeChartNodeBean child2 = children.get(i3);
                        child2.setContinueDo(Intrinsics.areEqual(str2, child2.getId()));
                        Intrinsics.checkNotNullExpressionValue(child2, "child2");
                        TreeNode treeNode2 = new TreeNode(new KnowledgeChartNodeBeanTreeNode(child2));
                        treeNode2.setCustomerLevel(1);
                        treeNode.addChild(treeNode2);
                    }
                }
            }
        }
        return arrayList;
    }

    public static final boolean isBottomRadiusItem(@NotNull TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBeanTreeNode>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(children.size() - 1));
    }

    public static final boolean isLastItem(@NotNull TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBeanTreeNode>> children = treeNode.getParent().getChildren();
        if (children != null) {
            return treeNodeEquals(treeNode, children.get(children.size() - 1));
        }
        return false;
    }

    public static final boolean isOnlyOneItem(@NotNull TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBeanTreeNode>> children = treeNode.getParent().getChildren();
        return children != null && children.size() == 1;
    }

    public static final boolean isTopRadiusItem(@NotNull TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBeanTreeNode>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(0));
    }

    public static final boolean treeNodeEquals(@NotNull TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode, @Nullable TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode2) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode2 == null) {
            return false;
        }
        return (treeNode2.getValue() != null && !TextUtils.isEmpty(treeNode2.getValue().getData().getId())) && (treeNode.getValue() != null && !TextUtils.isEmpty(treeNode.getValue().getData().getId())) && Intrinsics.areEqual(treeNode.getValue().getData().getId(), treeNode2.getValue().getData().getId());
    }
}
