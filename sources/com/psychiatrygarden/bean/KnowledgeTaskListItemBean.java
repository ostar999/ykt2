package com.psychiatrygarden.bean;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0081\u0001\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000fR\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u000f\"\u0004\b\u0013\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/bean/KnowledgeTaskListItemBean;", "", "id", "", PushConstants.TASK_ID, "title", "description", "is_complete", "is_receive", "should_num", "complete_num", "reward_days", "can_receive", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCan_receive", "()Ljava/lang/String;", "getComplete_num", "getDescription", "getId", "set_receive", "(Ljava/lang/String;)V", "getReward_days", "getShould_num", "getTask_id", "getTitle", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class KnowledgeTaskListItemBean {

    @Nullable
    private final String can_receive;

    @Nullable
    private final String complete_num;

    @Nullable
    private final String description;

    @Nullable
    private final String id;

    @Nullable
    private final String is_complete;

    @Nullable
    private String is_receive;

    @Nullable
    private final String reward_days;

    @Nullable
    private final String should_num;

    @Nullable
    private final String task_id;

    @Nullable
    private final String title;

    public KnowledgeTaskListItemBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10) {
        this.id = str;
        this.task_id = str2;
        this.title = str3;
        this.description = str4;
        this.is_complete = str5;
        this.is_receive = str6;
        this.should_num = str7;
        this.complete_num = str8;
        this.reward_days = str9;
        this.can_receive = str10;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getCan_receive() {
        return this.can_receive;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTask_id() {
        return this.task_id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getIs_complete() {
        return this.is_complete;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getIs_receive() {
        return this.is_receive;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getShould_num() {
        return this.should_num;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getComplete_num() {
        return this.complete_num;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getReward_days() {
        return this.reward_days;
    }

    @NotNull
    public final KnowledgeTaskListItemBean copy(@Nullable String id, @Nullable String task_id, @Nullable String title, @Nullable String description, @Nullable String is_complete, @Nullable String is_receive, @Nullable String should_num, @Nullable String complete_num, @Nullable String reward_days, @Nullable String can_receive) {
        return new KnowledgeTaskListItemBean(id, task_id, title, description, is_complete, is_receive, should_num, complete_num, reward_days, can_receive);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KnowledgeTaskListItemBean)) {
            return false;
        }
        KnowledgeTaskListItemBean knowledgeTaskListItemBean = (KnowledgeTaskListItemBean) other;
        return Intrinsics.areEqual(this.id, knowledgeTaskListItemBean.id) && Intrinsics.areEqual(this.task_id, knowledgeTaskListItemBean.task_id) && Intrinsics.areEqual(this.title, knowledgeTaskListItemBean.title) && Intrinsics.areEqual(this.description, knowledgeTaskListItemBean.description) && Intrinsics.areEqual(this.is_complete, knowledgeTaskListItemBean.is_complete) && Intrinsics.areEqual(this.is_receive, knowledgeTaskListItemBean.is_receive) && Intrinsics.areEqual(this.should_num, knowledgeTaskListItemBean.should_num) && Intrinsics.areEqual(this.complete_num, knowledgeTaskListItemBean.complete_num) && Intrinsics.areEqual(this.reward_days, knowledgeTaskListItemBean.reward_days) && Intrinsics.areEqual(this.can_receive, knowledgeTaskListItemBean.can_receive);
    }

    @Nullable
    public final String getCan_receive() {
        return this.can_receive;
    }

    @Nullable
    public final String getComplete_num() {
        return this.complete_num;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getReward_days() {
        return this.reward_days;
    }

    @Nullable
    public final String getShould_num() {
        return this.should_num;
    }

    @Nullable
    public final String getTask_id() {
        return this.task_id;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.task_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.title;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.description;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.is_complete;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.is_receive;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.should_num;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.complete_num;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.reward_days;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.can_receive;
        return iHashCode9 + (str10 != null ? str10.hashCode() : 0);
    }

    @Nullable
    public final String is_complete() {
        return this.is_complete;
    }

    @Nullable
    public final String is_receive() {
        return this.is_receive;
    }

    public final void set_receive(@Nullable String str) {
        this.is_receive = str;
    }

    @NotNull
    public String toString() {
        return "KnowledgeTaskListItemBean(id=" + this.id + ", task_id=" + this.task_id + ", title=" + this.title + ", description=" + this.description + ", is_complete=" + this.is_complete + ", is_receive=" + this.is_receive + ", should_num=" + this.should_num + ", complete_num=" + this.complete_num + ", reward_days=" + this.reward_days + ", can_receive=" + this.can_receive + ')';
    }
}
