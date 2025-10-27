package com.psychiatrygarden.bean;

import com.aliyun.vod.common.utils.UriUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u0011\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u0011\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J·\u0001\u0010&\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eHÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020+HÖ\u0001J\t\u0010,\u001a\u00020\u000eHÖ\u0001R\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0019\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0019\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0019\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011¨\u0006-"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolFilterBeanList;", "", UriUtil.QUERY_CATEGORY, "", "Lcom/psychiatrygarden/bean/ChooseSchoolFilterBean;", "attr", "area", "nature", "year", "major_type", "school_department", "batch", "type", "type_describe", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getArea", "()Ljava/util/List;", "getAttr", "getBatch", "getCategory", "getMajor_type", "getNature", "getSchool_department", "getType", "getType_describe", "()Ljava/lang/String;", "getYear", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolFilterBeanList {

    @Nullable
    private final List<ChooseSchoolFilterBean> area;

    @Nullable
    private final List<ChooseSchoolFilterBean> attr;

    @Nullable
    private final List<ChooseSchoolFilterBean> batch;

    @Nullable
    private final List<ChooseSchoolFilterBean> category;

    @Nullable
    private final List<ChooseSchoolFilterBean> major_type;

    @Nullable
    private final List<ChooseSchoolFilterBean> nature;

    @Nullable
    private final List<ChooseSchoolFilterBean> school_department;

    @Nullable
    private final List<ChooseSchoolFilterBean> type;

    @Nullable
    private final String type_describe;

    @Nullable
    private final List<ChooseSchoolFilterBean> year;

    public ChooseSchoolFilterBeanList(@Nullable List<ChooseSchoolFilterBean> list, @Nullable List<ChooseSchoolFilterBean> list2, @Nullable List<ChooseSchoolFilterBean> list3, @Nullable List<ChooseSchoolFilterBean> list4, @Nullable List<ChooseSchoolFilterBean> list5, @Nullable List<ChooseSchoolFilterBean> list6, @Nullable List<ChooseSchoolFilterBean> list7, @Nullable List<ChooseSchoolFilterBean> list8, @Nullable List<ChooseSchoolFilterBean> list9, @Nullable String str) {
        this.category = list;
        this.attr = list2;
        this.area = list3;
        this.nature = list4;
        this.year = list5;
        this.major_type = list6;
        this.school_department = list7;
        this.batch = list8;
        this.type = list9;
        this.type_describe = str;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component1() {
        return this.category;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getType_describe() {
        return this.type_describe;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component2() {
        return this.attr;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component3() {
        return this.area;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component4() {
        return this.nature;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component5() {
        return this.year;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component6() {
        return this.major_type;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component7() {
        return this.school_department;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component8() {
        return this.batch;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> component9() {
        return this.type;
    }

    @NotNull
    public final ChooseSchoolFilterBeanList copy(@Nullable List<ChooseSchoolFilterBean> category, @Nullable List<ChooseSchoolFilterBean> attr, @Nullable List<ChooseSchoolFilterBean> area, @Nullable List<ChooseSchoolFilterBean> nature, @Nullable List<ChooseSchoolFilterBean> year, @Nullable List<ChooseSchoolFilterBean> major_type, @Nullable List<ChooseSchoolFilterBean> school_department, @Nullable List<ChooseSchoolFilterBean> batch, @Nullable List<ChooseSchoolFilterBean> type, @Nullable String type_describe) {
        return new ChooseSchoolFilterBeanList(category, attr, area, nature, year, major_type, school_department, batch, type, type_describe);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolFilterBeanList)) {
            return false;
        }
        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) other;
        return Intrinsics.areEqual(this.category, chooseSchoolFilterBeanList.category) && Intrinsics.areEqual(this.attr, chooseSchoolFilterBeanList.attr) && Intrinsics.areEqual(this.area, chooseSchoolFilterBeanList.area) && Intrinsics.areEqual(this.nature, chooseSchoolFilterBeanList.nature) && Intrinsics.areEqual(this.year, chooseSchoolFilterBeanList.year) && Intrinsics.areEqual(this.major_type, chooseSchoolFilterBeanList.major_type) && Intrinsics.areEqual(this.school_department, chooseSchoolFilterBeanList.school_department) && Intrinsics.areEqual(this.batch, chooseSchoolFilterBeanList.batch) && Intrinsics.areEqual(this.type, chooseSchoolFilterBeanList.type) && Intrinsics.areEqual(this.type_describe, chooseSchoolFilterBeanList.type_describe);
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getArea() {
        return this.area;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getAttr() {
        return this.attr;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getBatch() {
        return this.batch;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getCategory() {
        return this.category;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getMajor_type() {
        return this.major_type;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getNature() {
        return this.nature;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getSchool_department() {
        return this.school_department;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getType() {
        return this.type;
    }

    @Nullable
    public final String getType_describe() {
        return this.type_describe;
    }

    @Nullable
    public final List<ChooseSchoolFilterBean> getYear() {
        return this.year;
    }

    public int hashCode() {
        List<ChooseSchoolFilterBean> list = this.category;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<ChooseSchoolFilterBean> list2 = this.attr;
        int iHashCode2 = (iHashCode + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list3 = this.area;
        int iHashCode3 = (iHashCode2 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list4 = this.nature;
        int iHashCode4 = (iHashCode3 + (list4 == null ? 0 : list4.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list5 = this.year;
        int iHashCode5 = (iHashCode4 + (list5 == null ? 0 : list5.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list6 = this.major_type;
        int iHashCode6 = (iHashCode5 + (list6 == null ? 0 : list6.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list7 = this.school_department;
        int iHashCode7 = (iHashCode6 + (list7 == null ? 0 : list7.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list8 = this.batch;
        int iHashCode8 = (iHashCode7 + (list8 == null ? 0 : list8.hashCode())) * 31;
        List<ChooseSchoolFilterBean> list9 = this.type;
        int iHashCode9 = (iHashCode8 + (list9 == null ? 0 : list9.hashCode())) * 31;
        String str = this.type_describe;
        return iHashCode9 + (str != null ? str.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolFilterBeanList(category=" + this.category + ", attr=" + this.attr + ", area=" + this.area + ", nature=" + this.nature + ", year=" + this.year + ", major_type=" + this.major_type + ", school_department=" + this.school_department + ", batch=" + this.batch + ", type=" + this.type + ", type_describe=" + this.type_describe + ')';
    }
}
