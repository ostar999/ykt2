package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\u0002\u0010\tR\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/bean/DateTabBean;", "", "type", "", "id", "name", "children", "", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getChildren", "()Ljava/util/List;", "getId", "()Ljava/lang/String;", "getName", "getType", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DateTabBean {

    @Nullable
    private final List<KnowledgeChartNodeBean> children;

    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String type;

    /* JADX WARN: Multi-variable type inference failed */
    public DateTabBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable List<? extends KnowledgeChartNodeBean> list) {
        this.type = str;
        this.id = str2;
        this.name = str3;
        this.children = list;
    }

    @Nullable
    public final List<KnowledgeChartNodeBean> getChildren() {
        return this.children;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }
}
