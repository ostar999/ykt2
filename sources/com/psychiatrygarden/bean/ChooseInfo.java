package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003JM\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e¨\u0006\u001e"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseInfo;", "", "user_score", "", "score_count", "", "Lcom/psychiatrygarden/bean/ChooseInfoScore;", "choose_school", "Lcom/psychiatrygarden/bean/ChooseInfoSchoolInfo;", "major_id", "is_open", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getChoose_school", "()Ljava/util/List;", "()Ljava/lang/String;", "getMajor_id", "getScore_count", "getUser_score", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseInfo {

    @NotNull
    private final List<ChooseInfoSchoolInfo> choose_school;

    @Nullable
    private final String is_open;

    @Nullable
    private final String major_id;

    @NotNull
    private final List<ChooseInfoScore> score_count;

    @Nullable
    private final String user_score;

    public ChooseInfo(@Nullable String str, @NotNull List<ChooseInfoScore> score_count, @NotNull List<ChooseInfoSchoolInfo> choose_school, @Nullable String str2, @Nullable String str3) {
        Intrinsics.checkNotNullParameter(score_count, "score_count");
        Intrinsics.checkNotNullParameter(choose_school, "choose_school");
        this.user_score = str;
        this.score_count = score_count;
        this.choose_school = choose_school;
        this.major_id = str2;
        this.is_open = str3;
    }

    public static /* synthetic */ ChooseInfo copy$default(ChooseInfo chooseInfo, String str, List list, List list2, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseInfo.user_score;
        }
        if ((i2 & 2) != 0) {
            list = chooseInfo.score_count;
        }
        List list3 = list;
        if ((i2 & 4) != 0) {
            list2 = chooseInfo.choose_school;
        }
        List list4 = list2;
        if ((i2 & 8) != 0) {
            str2 = chooseInfo.major_id;
        }
        String str4 = str2;
        if ((i2 & 16) != 0) {
            str3 = chooseInfo.is_open;
        }
        return chooseInfo.copy(str, list3, list4, str4, str3);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getUser_score() {
        return this.user_score;
    }

    @NotNull
    public final List<ChooseInfoScore> component2() {
        return this.score_count;
    }

    @NotNull
    public final List<ChooseInfoSchoolInfo> component3() {
        return this.choose_school;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getMajor_id() {
        return this.major_id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getIs_open() {
        return this.is_open;
    }

    @NotNull
    public final ChooseInfo copy(@Nullable String user_score, @NotNull List<ChooseInfoScore> score_count, @NotNull List<ChooseInfoSchoolInfo> choose_school, @Nullable String major_id, @Nullable String is_open) {
        Intrinsics.checkNotNullParameter(score_count, "score_count");
        Intrinsics.checkNotNullParameter(choose_school, "choose_school");
        return new ChooseInfo(user_score, score_count, choose_school, major_id, is_open);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseInfo)) {
            return false;
        }
        ChooseInfo chooseInfo = (ChooseInfo) other;
        return Intrinsics.areEqual(this.user_score, chooseInfo.user_score) && Intrinsics.areEqual(this.score_count, chooseInfo.score_count) && Intrinsics.areEqual(this.choose_school, chooseInfo.choose_school) && Intrinsics.areEqual(this.major_id, chooseInfo.major_id) && Intrinsics.areEqual(this.is_open, chooseInfo.is_open);
    }

    @NotNull
    public final List<ChooseInfoSchoolInfo> getChoose_school() {
        return this.choose_school;
    }

    @Nullable
    public final String getMajor_id() {
        return this.major_id;
    }

    @NotNull
    public final List<ChooseInfoScore> getScore_count() {
        return this.score_count;
    }

    @Nullable
    public final String getUser_score() {
        return this.user_score;
    }

    public int hashCode() {
        String str = this.user_score;
        int iHashCode = (((((str == null ? 0 : str.hashCode()) * 31) + this.score_count.hashCode()) * 31) + this.choose_school.hashCode()) * 31;
        String str2 = this.major_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.is_open;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @Nullable
    public final String is_open() {
        return this.is_open;
    }

    @NotNull
    public String toString() {
        return "ChooseInfo(user_score=" + this.user_score + ", score_count=" + this.score_count + ", choose_school=" + this.choose_school + ", major_id=" + this.major_id + ", is_open=" + this.is_open + ')';
    }
}
