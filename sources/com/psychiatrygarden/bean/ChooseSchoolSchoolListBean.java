package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u009f\u0001\u0010+\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010/\u001a\u000200HÖ\u0001J\t\u00101\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014¨\u00062"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolSchoolListBean;", "", "school_id", "", "title", "attr", "", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "major_code", "major_title", "year", "student_count", "max_score", "min_score", "avg_score", "rate", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAttr", "()Ljava/util/List;", "getAvg_score", "()Ljava/lang/String;", "getCover", "getMajor_code", "getMajor_title", "getMax_score", "getMin_score", "getRate", "getSchool_id", "getStudent_count", "getTitle", "getYear", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolSchoolListBean {

    @Nullable
    private final List<String> attr;

    @Nullable
    private final String avg_score;

    @Nullable
    private final String cover;

    @Nullable
    private final String major_code;

    @Nullable
    private final String major_title;

    @Nullable
    private final String max_score;

    @Nullable
    private final String min_score;

    @Nullable
    private final String rate;

    @Nullable
    private final String school_id;

    @Nullable
    private final String student_count;

    @Nullable
    private final String title;

    @Nullable
    private final String year;

    public ChooseSchoolSchoolListBean(@Nullable String str, @Nullable String str2, @Nullable List<String> list, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11) {
        this.school_id = str;
        this.title = str2;
        this.attr = list;
        this.cover = str3;
        this.major_code = str4;
        this.major_title = str5;
        this.year = str6;
        this.student_count = str7;
        this.max_score = str8;
        this.min_score = str9;
        this.avg_score = str10;
        this.rate = str11;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getSchool_id() {
        return this.school_id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getMin_score() {
        return this.min_score;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getAvg_score() {
        return this.avg_score;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getRate() {
        return this.rate;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final List<String> component3() {
        return this.attr;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getMajor_code() {
        return this.major_code;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getMajor_title() {
        return this.major_title;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getYear() {
        return this.year;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getStudent_count() {
        return this.student_count;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getMax_score() {
        return this.max_score;
    }

    @NotNull
    public final ChooseSchoolSchoolListBean copy(@Nullable String school_id, @Nullable String title, @Nullable List<String> attr, @Nullable String cover, @Nullable String major_code, @Nullable String major_title, @Nullable String year, @Nullable String student_count, @Nullable String max_score, @Nullable String min_score, @Nullable String avg_score, @Nullable String rate) {
        return new ChooseSchoolSchoolListBean(school_id, title, attr, cover, major_code, major_title, year, student_count, max_score, min_score, avg_score, rate);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolSchoolListBean)) {
            return false;
        }
        ChooseSchoolSchoolListBean chooseSchoolSchoolListBean = (ChooseSchoolSchoolListBean) other;
        return Intrinsics.areEqual(this.school_id, chooseSchoolSchoolListBean.school_id) && Intrinsics.areEqual(this.title, chooseSchoolSchoolListBean.title) && Intrinsics.areEqual(this.attr, chooseSchoolSchoolListBean.attr) && Intrinsics.areEqual(this.cover, chooseSchoolSchoolListBean.cover) && Intrinsics.areEqual(this.major_code, chooseSchoolSchoolListBean.major_code) && Intrinsics.areEqual(this.major_title, chooseSchoolSchoolListBean.major_title) && Intrinsics.areEqual(this.year, chooseSchoolSchoolListBean.year) && Intrinsics.areEqual(this.student_count, chooseSchoolSchoolListBean.student_count) && Intrinsics.areEqual(this.max_score, chooseSchoolSchoolListBean.max_score) && Intrinsics.areEqual(this.min_score, chooseSchoolSchoolListBean.min_score) && Intrinsics.areEqual(this.avg_score, chooseSchoolSchoolListBean.avg_score) && Intrinsics.areEqual(this.rate, chooseSchoolSchoolListBean.rate);
    }

    @Nullable
    public final List<String> getAttr() {
        return this.attr;
    }

    @Nullable
    public final String getAvg_score() {
        return this.avg_score;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getMajor_code() {
        return this.major_code;
    }

    @Nullable
    public final String getMajor_title() {
        return this.major_title;
    }

    @Nullable
    public final String getMax_score() {
        return this.max_score;
    }

    @Nullable
    public final String getMin_score() {
        return this.min_score;
    }

    @Nullable
    public final String getRate() {
        return this.rate;
    }

    @Nullable
    public final String getSchool_id() {
        return this.school_id;
    }

    @Nullable
    public final String getStudent_count() {
        return this.student_count;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getYear() {
        return this.year;
    }

    public int hashCode() {
        String str = this.school_id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        List<String> list = this.attr;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        String str3 = this.cover;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.major_code;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.major_title;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.year;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.student_count;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.max_score;
        int iHashCode9 = (iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.min_score;
        int iHashCode10 = (iHashCode9 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.avg_score;
        int iHashCode11 = (iHashCode10 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.rate;
        return iHashCode11 + (str11 != null ? str11.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolSchoolListBean(school_id=" + this.school_id + ", title=" + this.title + ", attr=" + this.attr + ", cover=" + this.cover + ", major_code=" + this.major_code + ", major_title=" + this.major_title + ", year=" + this.year + ", student_count=" + this.student_count + ", max_score=" + this.max_score + ", min_score=" + this.min_score + ", avg_score=" + this.avg_score + ", rate=" + this.rate + ')';
    }
}
