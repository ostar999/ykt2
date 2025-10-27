package com.psychiatrygarden.bean;

import com.psychiatrygarden.utils.CommonParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J-\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolTopInfo;", "", "school_num", "", "major_num", CommonParameter.notice, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getMajor_num", "()Ljava/lang/String;", "getNotice", "getSchool_num", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolTopInfo {

    @Nullable
    private final String major_num;

    @Nullable
    private final String notice;

    @Nullable
    private final String school_num;

    public ChooseSchoolTopInfo(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        this.school_num = str;
        this.major_num = str2;
        this.notice = str3;
    }

    public static /* synthetic */ ChooseSchoolTopInfo copy$default(ChooseSchoolTopInfo chooseSchoolTopInfo, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseSchoolTopInfo.school_num;
        }
        if ((i2 & 2) != 0) {
            str2 = chooseSchoolTopInfo.major_num;
        }
        if ((i2 & 4) != 0) {
            str3 = chooseSchoolTopInfo.notice;
        }
        return chooseSchoolTopInfo.copy(str, str2, str3);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getSchool_num() {
        return this.school_num;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getMajor_num() {
        return this.major_num;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getNotice() {
        return this.notice;
    }

    @NotNull
    public final ChooseSchoolTopInfo copy(@Nullable String school_num, @Nullable String major_num, @Nullable String notice) {
        return new ChooseSchoolTopInfo(school_num, major_num, notice);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolTopInfo)) {
            return false;
        }
        ChooseSchoolTopInfo chooseSchoolTopInfo = (ChooseSchoolTopInfo) other;
        return Intrinsics.areEqual(this.school_num, chooseSchoolTopInfo.school_num) && Intrinsics.areEqual(this.major_num, chooseSchoolTopInfo.major_num) && Intrinsics.areEqual(this.notice, chooseSchoolTopInfo.notice);
    }

    @Nullable
    public final String getMajor_num() {
        return this.major_num;
    }

    @Nullable
    public final String getNotice() {
        return this.notice;
    }

    @Nullable
    public final String getSchool_num() {
        return this.school_num;
    }

    public int hashCode() {
        String str = this.school_num;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.major_num;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.notice;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolTopInfo(school_num=" + this.school_num + ", major_num=" + this.major_num + ", notice=" + this.notice + ')';
    }
}
