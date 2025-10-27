package com.psychiatrygarden.utils;

import android.text.TextUtils;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.widget.treenode.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u001a \u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0001\u001a.\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001\u001a.\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001\u001a\u0010\u0010\b\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\n\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\u000b\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\f\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010\r\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a \u0010\u000e\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002¨\u0006\u0010"}, d2 = {"KnowledgeListToTreeNodeData", "", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "listData", "selectIds", "", "KnowledgeListToTreeNodeDataForRemake", "allSelect", "", "isBottomRadiusItem", "isOnlyOneItem", "isTopRadiusItem", "noOneSelect", "treeNodeEquals", "treeNode2", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nKnowledgeTreeNodeUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeTreeNodeUtil.kt\ncom/psychiatrygarden/utils/KnowledgeTreeNodeUtilKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,208:1\n1855#2:209\n1855#2,2:210\n1856#2:212\n1855#2:213\n1855#2,2:214\n1856#2:216\n*S KotlinDebug\n*F\n+ 1 KnowledgeTreeNodeUtil.kt\ncom/psychiatrygarden/utils/KnowledgeTreeNodeUtilKt\n*L\n163#1:209\n165#1:210,2\n163#1:212\n189#1:213\n191#1:214,2\n189#1:216\n*E\n"})
/* loaded from: classes6.dex */
public final class KnowledgeTreeNodeUtilKt {
    @NotNull
    public static final List<TreeNode<KnowledgeChartNodeBean>> KnowledgeListToTreeNodeData(@NotNull List<? extends KnowledgeChartNodeBean> listData) {
        Intrinsics.checkNotNullParameter(listData, "listData");
        ArrayList arrayList = new ArrayList();
        int size = listData.size();
        for (int i2 = 0; i2 < size; i2++) {
            KnowledgeChartNodeBean knowledgeChartNodeBean = listData.get(i2);
            TreeNode treeNode = new TreeNode(knowledgeChartNodeBean);
            treeNode.setCustomerLevel(0);
            arrayList.add(treeNode);
            if (knowledgeChartNodeBean.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(knowledgeChartNodeBean.getChildren(), "itemRoot.children");
                if (!r6.isEmpty()) {
                    List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                    int size2 = children.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        KnowledgeChartNodeBean knowledgeChartNodeBean2 = children.get(i3);
                        TreeNode treeNode2 = new TreeNode(knowledgeChartNodeBean2);
                        treeNode2.setCustomerLevel(1);
                        treeNode.addChild(treeNode2);
                        List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean2.getChildren();
                        int size3 = children2.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            TreeNode treeNode3 = new TreeNode(children2.get(i4));
                            treeNode3.setCustomerLevel(2);
                            treeNode2.addChild(treeNode3);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ca  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.util.List<com.psychiatrygarden.widget.treenode.TreeNode<com.psychiatrygarden.bean.KnowledgeChartNodeBean>> KnowledgeListToTreeNodeDataForRemake(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.KnowledgeChartNodeBean> r16, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r17) {
        /*
            r0 = r16
            r1 = r17
            java.lang.String r2 = "listData"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r2)
            java.lang.String r2 = "selectIds"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            int r3 = r16.size()
            r4 = 0
            r5 = r4
        L1a:
            if (r5 >= r3) goto Ld8
            java.lang.Object r6 = r0.get(r5)
            com.psychiatrygarden.bean.KnowledgeChartNodeBean r6 = (com.psychiatrygarden.bean.KnowledgeChartNodeBean) r6
            com.psychiatrygarden.widget.treenode.TreeNode r7 = new com.psychiatrygarden.widget.treenode.TreeNode
            r7.<init>(r6)
            r7.setCustomerLevel(r4)
            java.util.List r8 = r6.getChildren()
            r9 = 1
            if (r8 == 0) goto Lca
            java.util.List r8 = r6.getChildren()
            java.lang.String r10 = "itemRoot.children"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r10)
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r8 = r8.isEmpty()
            r8 = r8 ^ r9
            if (r8 == 0) goto Lca
            java.util.List r6 = r6.getChildren()
            int r8 = r6.size()
            r10 = r4
        L4c:
            if (r10 >= r8) goto Lce
            java.lang.Object r11 = r6.get(r10)
            com.psychiatrygarden.bean.KnowledgeChartNodeBean r11 = (com.psychiatrygarden.bean.KnowledgeChartNodeBean) r11
            com.psychiatrygarden.widget.treenode.TreeNode r12 = new com.psychiatrygarden.widget.treenode.TreeNode
            r12.<init>(r11)
            r12.setCustomerLevel(r9)
            r7.addChild(r12)
            java.util.List r11 = r11.getChildren()
            if (r11 == 0) goto Lc3
            int r13 = r11.size()
            r14 = r4
        L6a:
            if (r14 >= r13) goto Lc3
            java.lang.Object r15 = r11.get(r14)
            com.psychiatrygarden.bean.KnowledgeChartNodeBean r15 = (com.psychiatrygarden.bean.KnowledgeChartNodeBean) r15
            com.psychiatrygarden.widget.treenode.TreeNode r4 = new com.psychiatrygarden.widget.treenode.TreeNode
            r4.<init>(r15)
            java.lang.String r9 = r15.getId()
            boolean r9 = r1.contains(r9)
            if (r9 == 0) goto L8c
            java.lang.Object r9 = r4.getValue()
            com.psychiatrygarden.bean.KnowledgeChartNodeBean r9 = (com.psychiatrygarden.bean.KnowledgeChartNodeBean) r9
            r15 = 1
            r9.setSelect(r15)
            goto Lb5
        L8c:
            java.lang.Object r9 = r4.getValue()
            com.psychiatrygarden.bean.KnowledgeChartNodeBean r9 = (com.psychiatrygarden.bean.KnowledgeChartNodeBean) r9
            r0 = 0
            r9.setSelect(r0)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "KnowledgeTreeNode "
            r9.append(r0)
            java.lang.String r0 = r15.getId()
            r9.append(r0)
            java.lang.String r0 = " --- 设置为false"
            r9.append(r0)
            java.lang.String r0 = r9.toString()
            java.io.PrintStream r9 = java.lang.System.out
            r9.println(r0)
        Lb5:
            r0 = 2
            r4.setCustomerLevel(r0)
            r12.addChild(r4)
            int r14 = r14 + 1
            r0 = r16
            r4 = 0
            r9 = 1
            goto L6a
        Lc3:
            int r10 = r10 + 1
            r0 = r16
            r4 = 0
            r9 = 1
            goto L4c
        Lca:
            r0 = r9
            r7.setCustomerLevel(r0)
        Lce:
            r2.add(r7)
            int r5 = r5 + 1
            r0 = r16
            r4 = 0
            goto L1a
        Ld8:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.KnowledgeTreeNodeUtilKt.KnowledgeListToTreeNodeDataForRemake(java.util.List, java.util.List):java.util.List");
    }

    public static final boolean allSelect(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getChildren() == null || treeNode.getChildren().size() <= 0) {
            return false;
        }
        List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "children");
        Iterator<T> it = children.iterator();
        while (it.hasNext()) {
            TreeNode treeNode2 = (TreeNode) it.next();
            if (treeNode2.getChildren() != null && treeNode2.getChildren().size() > 0) {
                List children2 = treeNode2.getChildren();
                Intrinsics.checkNotNullExpressionValue(children2, "treeNode.children");
                Iterator it2 = children2.iterator();
                while (it2.hasNext()) {
                    if (!((KnowledgeChartNodeBean) ((TreeNode) it2.next()).getValue()).isSelect()) {
                        return false;
                    }
                }
            } else if (!((KnowledgeChartNodeBean) treeNode2.getValue()).isSelect()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isBottomRadiusItem(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(children.size() - 1));
    }

    public static final boolean isOnlyOneItem(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getParent().getChildren();
        return children != null && children.size() == 1;
    }

    public static final boolean isTopRadiusItem(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(0));
    }

    public static final boolean noOneSelect(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
            List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
            Intrinsics.checkNotNullExpressionValue(children, "children");
            Iterator<T> it = children.iterator();
            while (it.hasNext()) {
                TreeNode treeNode2 = (TreeNode) it.next();
                if (treeNode2.getChildren() != null && treeNode2.getChildren().size() > 0) {
                    List children2 = treeNode2.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children2, "treeNode.children");
                    Iterator it2 = children2.iterator();
                    while (it2.hasNext()) {
                        if (((KnowledgeChartNodeBean) ((TreeNode) it2.next()).getValue()).isSelect()) {
                            return false;
                        }
                    }
                } else if (((KnowledgeChartNodeBean) treeNode2.getValue()).isSelect()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final boolean treeNodeEquals(@NotNull TreeNode<KnowledgeChartNodeBean> treeNode, @Nullable TreeNode<KnowledgeChartNodeBean> treeNode2) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode2 == null) {
            return false;
        }
        return (treeNode2.getValue() != null && !TextUtils.isEmpty(treeNode2.getValue().getId())) && (treeNode.getValue() != null && !TextUtils.isEmpty(treeNode.getValue().getId())) && Intrinsics.areEqual(treeNode.getValue().getId(), treeNode2.getValue().getId());
    }

    @NotNull
    public static final List<TreeNode<KnowledgeChartNodeBean>> KnowledgeListToTreeNodeData(@NotNull List<? extends KnowledgeChartNodeBean> list, @NotNull List<String> selectIds) {
        List<? extends KnowledgeChartNodeBean> listData = list;
        Intrinsics.checkNotNullParameter(listData, "listData");
        Intrinsics.checkNotNullParameter(selectIds, "selectIds");
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            KnowledgeChartNodeBean knowledgeChartNodeBean = listData.get(i3);
            TreeNode treeNode = new TreeNode(knowledgeChartNodeBean);
            treeNode.setCustomerLevel(i2);
            arrayList.add(treeNode);
            if (knowledgeChartNodeBean.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(knowledgeChartNodeBean.getChildren(), "itemRoot.children");
                int i4 = 1;
                if (!r8.isEmpty()) {
                    List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                    int size2 = children.size();
                    int i5 = i2;
                    while (i5 < size2) {
                        KnowledgeChartNodeBean knowledgeChartNodeBean2 = children.get(i5);
                        TreeNode treeNode2 = new TreeNode(knowledgeChartNodeBean2);
                        treeNode2.setCustomerLevel(i4);
                        treeNode.addChild(treeNode2);
                        List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean2.getChildren();
                        if (children2 != null) {
                            int size3 = children2.size();
                            for (int i6 = i2; i6 < size3; i6++) {
                                KnowledgeChartNodeBean knowledgeChartNodeBean3 = children2.get(i6);
                                TreeNode treeNode3 = new TreeNode(knowledgeChartNodeBean3);
                                if (selectIds.contains(knowledgeChartNodeBean3.getId())) {
                                    ((KnowledgeChartNodeBean) treeNode3.getValue()).setSelect(true);
                                } else {
                                    ((KnowledgeChartNodeBean) treeNode3.getValue()).setSelect(false);
                                    System.out.println((Object) ("KnowledgeTreeNode " + knowledgeChartNodeBean3.getId() + " --- 设置为false"));
                                }
                                treeNode3.setCustomerLevel(2);
                                treeNode2.addChild(treeNode3);
                            }
                        }
                        i5++;
                        i2 = 0;
                        i4 = 1;
                    }
                }
            }
            i3++;
            listData = list;
            i2 = 0;
        }
        return arrayList;
    }
}
