package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseInfoSchoolInfo;", "", "count", "", "type", "title", "tab_title", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCount", "()Ljava/lang/String;", "getTab_title", "getTitle", "getType", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseInfoSchoolInfo {

    @Nullable
    private final String count;

    @Nullable
    private final String tab_title;

    @Nullable
    private final String title;

    @Nullable
    private final String type;

    public ChooseInfoSchoolInfo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.count = str;
        this.type = str2;
        this.title = str3;
        this.tab_title = str4;
    }

    public static /* synthetic */ ChooseInfoSchoolInfo copy$default(ChooseInfoSchoolInfo chooseInfoSchoolInfo, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseInfoSchoolInfo.count;
        }
        if ((i2 & 2) != 0) {
            str2 = chooseInfoSchoolInfo.type;
        }
        if ((i2 & 4) != 0) {
            str3 = chooseInfoSchoolInfo.title;
        }
        if ((i2 & 8) != 0) {
            str4 = chooseInfoSchoolInfo.tab_title;
        }
        return chooseInfoSchoolInfo.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCount() {
        return this.count;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getTab_title() {
        return this.tab_title;
    }

    @NotNull
    public final ChooseInfoSchoolInfo copy(@Nullable String count, @Nullable String type, @Nullable String title, @Nullable String tab_title) {
        return new ChooseInfoSchoolInfo(count, type, title, tab_title);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseInfoSchoolInfo)) {
            return false;
        }
        ChooseInfoSchoolInfo chooseInfoSchoolInfo = (ChooseInfoSchoolInfo) other;
        return Intrinsics.areEqual(this.count, chooseInfoSchoolInfo.count) && Intrinsics.areEqual(this.type, chooseInfoSchoolInfo.type) && Intrinsics.areEqual(this.title, chooseInfoSchoolInfo.title) && Intrinsics.areEqual(this.tab_title, chooseInfoSchoolInfo.tab_title);
    }

    @Nullable
    public final String getCount() {
        return this.count;
    }

    @Nullable
    public final String getTab_title() {
        return this.tab_title;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        String str = this.count;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.type;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.title;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.tab_title;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChooseInfoSchoolInfo(count=" + this.count + ", type=" + this.type + ", title=" + this.title + ", tab_title=" + this.tab_title + ')';
    }
}
