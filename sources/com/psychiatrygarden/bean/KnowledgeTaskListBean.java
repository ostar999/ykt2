package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bHÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003JW\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/bean/KnowledgeTaskListBean;", "", "vid", "", "stat_time", "cover_url", "rule_url", "list", "", "Lcom/psychiatrygarden/bean/KnowledgeTaskListItemBean;", "info_url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getCover_url", "()Ljava/lang/String;", "getInfo_url", "getList", "()Ljava/util/List;", "getRule_url", "getStat_time", "getVid", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class KnowledgeTaskListBean {

    @Nullable
    private final String cover_url;

    @Nullable
    private final String info_url;

    @Nullable
    private final List<KnowledgeTaskListItemBean> list;

    @Nullable
    private final String rule_url;

    @Nullable
    private final String stat_time;

    @Nullable
    private final String vid;

    public KnowledgeTaskListBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable List<KnowledgeTaskListItemBean> list, @Nullable String str5) {
        this.vid = str;
        this.stat_time = str2;
        this.cover_url = str3;
        this.rule_url = str4;
        this.list = list;
        this.info_url = str5;
    }

    public static /* synthetic */ KnowledgeTaskListBean copy$default(KnowledgeTaskListBean knowledgeTaskListBean, String str, String str2, String str3, String str4, List list, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = knowledgeTaskListBean.vid;
        }
        if ((i2 & 2) != 0) {
            str2 = knowledgeTaskListBean.stat_time;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = knowledgeTaskListBean.cover_url;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            str4 = knowledgeTaskListBean.rule_url;
        }
        String str8 = str4;
        if ((i2 & 16) != 0) {
            list = knowledgeTaskListBean.list;
        }
        List list2 = list;
        if ((i2 & 32) != 0) {
            str5 = knowledgeTaskListBean.info_url;
        }
        return knowledgeTaskListBean.copy(str, str6, str7, str8, list2, str5);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getVid() {
        return this.vid;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getStat_time() {
        return this.stat_time;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCover_url() {
        return this.cover_url;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getRule_url() {
        return this.rule_url;
    }

    @Nullable
    public final List<KnowledgeTaskListItemBean> component5() {
        return this.list;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getInfo_url() {
        return this.info_url;
    }

    @NotNull
    public final KnowledgeTaskListBean copy(@Nullable String vid, @Nullable String stat_time, @Nullable String cover_url, @Nullable String rule_url, @Nullable List<KnowledgeTaskListItemBean> list, @Nullable String info_url) {
        return new KnowledgeTaskListBean(vid, stat_time, cover_url, rule_url, list, info_url);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KnowledgeTaskListBean)) {
            return false;
        }
        KnowledgeTaskListBean knowledgeTaskListBean = (KnowledgeTaskListBean) other;
        return Intrinsics.areEqual(this.vid, knowledgeTaskListBean.vid) && Intrinsics.areEqual(this.stat_time, knowledgeTaskListBean.stat_time) && Intrinsics.areEqual(this.cover_url, knowledgeTaskListBean.cover_url) && Intrinsics.areEqual(this.rule_url, knowledgeTaskListBean.rule_url) && Intrinsics.areEqual(this.list, knowledgeTaskListBean.list) && Intrinsics.areEqual(this.info_url, knowledgeTaskListBean.info_url);
    }

    @Nullable
    public final String getCover_url() {
        return this.cover_url;
    }

    @Nullable
    public final String getInfo_url() {
        return this.info_url;
    }

    @Nullable
    public final List<KnowledgeTaskListItemBean> getList() {
        return this.list;
    }

    @Nullable
    public final String getRule_url() {
        return this.rule_url;
    }

    @Nullable
    public final String getStat_time() {
        return this.stat_time;
    }

    @Nullable
    public final String getVid() {
        return this.vid;
    }

    public int hashCode() {
        String str = this.vid;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.stat_time;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.cover_url;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.rule_url;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<KnowledgeTaskListItemBean> list = this.list;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        String str5 = this.info_url;
        return iHashCode5 + (str5 != null ? str5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "KnowledgeTaskListBean(vid=" + this.vid + ", stat_time=" + this.stat_time + ", cover_url=" + this.cover_url + ", rule_url=" + this.rule_url + ", list=" + this.list + ", info_url=" + this.info_url + ')';
    }
}
