package com.psychiatrygarden.activity.knowledge;

import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/KnowledgeChartNodeBeanTreeNode;", "", "data", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "(Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;)V", "getData", "()Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class KnowledgeChartNodeBeanTreeNode {

    @NotNull
    private final KnowledgeChartNodeBean data;

    public KnowledgeChartNodeBeanTreeNode(@NotNull KnowledgeChartNodeBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    @NotNull
    public final KnowledgeChartNodeBean getData() {
        return this.data;
    }
}
