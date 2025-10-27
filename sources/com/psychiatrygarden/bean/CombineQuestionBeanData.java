package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0095\u0001\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003¢\u0006\u0002\u0010\u000fJ\u0011\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010!\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\"\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0003J«\u0001\u0010#\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003HÆ\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020*HÖ\u0001R\u001e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u001e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u001e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u001e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u001e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u001e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011¨\u0006+"}, d2 = {"Lcom/psychiatrygarden/bean/CombineQuestionBeanData;", "", "chapterList", "", "Lcom/psychiatrygarden/bean/ChapterOptions;", "yearsList", "Lcom/psychiatrygarden/activity/online/bean/QuestionListBean$DataDTO$SearchDTO$SearchDataDTO;", "questionType", "Lcom/psychiatrygarden/bean/FilterOptions;", "sourceList", "modeList", "numberList", "outlineList", "frequencyList", "cognitionList", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getChapterList", "()Ljava/util/List;", "getCognitionList", "getFrequencyList", "getModeList", "getNumberList", "getOutlineList", "getQuestionType", "getSourceList", "getYearsList", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CombineQuestionBeanData {

    @SerializedName("chapter_list")
    @Nullable
    private final List<ChapterOptions> chapterList;

    @SerializedName("cognition_list")
    @Nullable
    private final List<FilterOptions> cognitionList;

    @SerializedName("frequency_list")
    @Nullable
    private final List<FilterOptions> frequencyList;

    @SerializedName("mode_list")
    @Nullable
    private final List<FilterOptions> modeList;

    @SerializedName("number_list")
    @Nullable
    private final List<FilterOptions> numberList;

    @SerializedName("outline_list")
    @Nullable
    private final List<FilterOptions> outlineList;

    @SerializedName("question_type")
    @Nullable
    private final List<FilterOptions> questionType;

    @SerializedName("source_list")
    @Nullable
    private final List<FilterOptions> sourceList;

    @SerializedName("years_list")
    @Nullable
    private final List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearsList;

    /* JADX WARN: Multi-variable type inference failed */
    public CombineQuestionBeanData(@Nullable List<ChapterOptions> list, @Nullable List<? extends QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> list2, @Nullable List<FilterOptions> list3, @Nullable List<FilterOptions> list4, @Nullable List<FilterOptions> list5, @Nullable List<FilterOptions> list6, @Nullable List<FilterOptions> list7, @Nullable List<FilterOptions> list8, @Nullable List<FilterOptions> list9) {
        this.chapterList = list;
        this.yearsList = list2;
        this.questionType = list3;
        this.sourceList = list4;
        this.modeList = list5;
        this.numberList = list6;
        this.outlineList = list7;
        this.frequencyList = list8;
        this.cognitionList = list9;
    }

    @Nullable
    public final List<ChapterOptions> component1() {
        return this.chapterList;
    }

    @Nullable
    public final List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> component2() {
        return this.yearsList;
    }

    @Nullable
    public final List<FilterOptions> component3() {
        return this.questionType;
    }

    @Nullable
    public final List<FilterOptions> component4() {
        return this.sourceList;
    }

    @Nullable
    public final List<FilterOptions> component5() {
        return this.modeList;
    }

    @Nullable
    public final List<FilterOptions> component6() {
        return this.numberList;
    }

    @Nullable
    public final List<FilterOptions> component7() {
        return this.outlineList;
    }

    @Nullable
    public final List<FilterOptions> component8() {
        return this.frequencyList;
    }

    @Nullable
    public final List<FilterOptions> component9() {
        return this.cognitionList;
    }

    @NotNull
    public final CombineQuestionBeanData copy(@Nullable List<ChapterOptions> chapterList, @Nullable List<? extends QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearsList, @Nullable List<FilterOptions> questionType, @Nullable List<FilterOptions> sourceList, @Nullable List<FilterOptions> modeList, @Nullable List<FilterOptions> numberList, @Nullable List<FilterOptions> outlineList, @Nullable List<FilterOptions> frequencyList, @Nullable List<FilterOptions> cognitionList) {
        return new CombineQuestionBeanData(chapterList, yearsList, questionType, sourceList, modeList, numberList, outlineList, frequencyList, cognitionList);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CombineQuestionBeanData)) {
            return false;
        }
        CombineQuestionBeanData combineQuestionBeanData = (CombineQuestionBeanData) other;
        return Intrinsics.areEqual(this.chapterList, combineQuestionBeanData.chapterList) && Intrinsics.areEqual(this.yearsList, combineQuestionBeanData.yearsList) && Intrinsics.areEqual(this.questionType, combineQuestionBeanData.questionType) && Intrinsics.areEqual(this.sourceList, combineQuestionBeanData.sourceList) && Intrinsics.areEqual(this.modeList, combineQuestionBeanData.modeList) && Intrinsics.areEqual(this.numberList, combineQuestionBeanData.numberList) && Intrinsics.areEqual(this.outlineList, combineQuestionBeanData.outlineList) && Intrinsics.areEqual(this.frequencyList, combineQuestionBeanData.frequencyList) && Intrinsics.areEqual(this.cognitionList, combineQuestionBeanData.cognitionList);
    }

    @Nullable
    public final List<ChapterOptions> getChapterList() {
        return this.chapterList;
    }

    @Nullable
    public final List<FilterOptions> getCognitionList() {
        return this.cognitionList;
    }

    @Nullable
    public final List<FilterOptions> getFrequencyList() {
        return this.frequencyList;
    }

    @Nullable
    public final List<FilterOptions> getModeList() {
        return this.modeList;
    }

    @Nullable
    public final List<FilterOptions> getNumberList() {
        return this.numberList;
    }

    @Nullable
    public final List<FilterOptions> getOutlineList() {
        return this.outlineList;
    }

    @Nullable
    public final List<FilterOptions> getQuestionType() {
        return this.questionType;
    }

    @Nullable
    public final List<FilterOptions> getSourceList() {
        return this.sourceList;
    }

    @Nullable
    public final List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> getYearsList() {
        return this.yearsList;
    }

    public int hashCode() {
        List<ChapterOptions> list = this.chapterList;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> list2 = this.yearsList;
        int iHashCode2 = (iHashCode + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<FilterOptions> list3 = this.questionType;
        int iHashCode3 = (iHashCode2 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<FilterOptions> list4 = this.sourceList;
        int iHashCode4 = (iHashCode3 + (list4 == null ? 0 : list4.hashCode())) * 31;
        List<FilterOptions> list5 = this.modeList;
        int iHashCode5 = (iHashCode4 + (list5 == null ? 0 : list5.hashCode())) * 31;
        List<FilterOptions> list6 = this.numberList;
        int iHashCode6 = (iHashCode5 + (list6 == null ? 0 : list6.hashCode())) * 31;
        List<FilterOptions> list7 = this.outlineList;
        int iHashCode7 = (iHashCode6 + (list7 == null ? 0 : list7.hashCode())) * 31;
        List<FilterOptions> list8 = this.frequencyList;
        int iHashCode8 = (iHashCode7 + (list8 == null ? 0 : list8.hashCode())) * 31;
        List<FilterOptions> list9 = this.cognitionList;
        return iHashCode8 + (list9 != null ? list9.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "CombineQuestionBeanData(chapterList=" + this.chapterList + ", yearsList=" + this.yearsList + ", questionType=" + this.questionType + ", sourceList=" + this.sourceList + ", modeList=" + this.modeList + ", numberList=" + this.numberList + ", outlineList=" + this.outlineList + ", frequencyList=" + this.frequencyList + ", cognitionList=" + this.cognitionList + ')';
    }
}
