package com.ykb.ebook.model;

import android.content.AppCtxKt;
import android.text.TextUtils;
import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.R;
import com.ykb.ebook.common.AppPatternKt;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.ChineseUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\bQ\b\u0087\b\u0018\u00002\u00020\u0001B\u0083\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u0012\u001a\b\u0002\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000e0\u0018\u0012\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u0012\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u0012\b\b\u0002\u0010\u001b\u001a\u00020\t\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0003¢\u0006\u0002\u0010\u001dJ\t\u0010N\u001a\u00020\u0003HÆ\u0003J\u000f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eHÆ\u0003J\t\u0010P\u001a\u00020\u0003HÆ\u0003J\t\u0010Q\u001a\u00020\u0012HÆ\u0003J\u000f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eHÆ\u0003J\u000f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0003J\u000f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0003J\u001b\u0010U\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000e0\u0018HÆ\u0003J\u000f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0003J\u000f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eHÆ\u0003J\t\u0010X\u001a\u00020\tHÆ\u0003J\t\u0010Y\u001a\u00020\u0003HÆ\u0003J\t\u0010Z\u001a\u00020\u0003HÆ\u0003J\t\u0010[\u001a\u00020\u0003HÆ\u0003J\t\u0010\\\u001a\u00020\u0003HÆ\u0003J\t\u0010]\u001a\u00020\u0003HÆ\u0003J\t\u0010^\u001a\u00020\tHÆ\u0003J\t\u0010_\u001a\u00020\u0003HÆ\u0003J\t\u0010`\u001a\u00020\u0003HÆ\u0003J\t\u0010a\u001a\u00020\tHÆ\u0003J\u0087\u0002\u0010b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u001a\b\u0002\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000e0\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\b\b\u0002\u0010\u001b\u001a\u00020\t2\b\b\u0002\u0010\u001c\u001a\u00020\u0003HÆ\u0001J\u0013\u0010c\u001a\u00020\u00122\b\u0010d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0010\u0010e\u001a\u00020\u00032\b\b\u0002\u0010f\u001a\u00020\u0012J\t\u0010g\u001a\u00020\tHÖ\u0001J\t\u0010h\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u001b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010#\"\u0004\b'\u0010%R$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010#\"\u0004\b-\u0010%R\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010#\"\u0004\b/\u0010%R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010#\"\u0004\b1\u0010%R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010)\"\u0004\b3\u0010+R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010#\"\u0004\b4\u0010%R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u00105\"\u0004\b6\u00107R\u001a\u0010\u001c\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010#\"\u0004\b9\u0010%R \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010)\"\u0004\b;\u0010+R \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010)\"\u0004\b=\u0010+R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010)\"\u0004\b?\u0010+R,\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000e0\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010)\"\u0004\bE\u0010+R\u001e\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\u001f\"\u0004\bG\u0010!R\u001e\u0010\n\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010#\"\u0004\bI\u0010%R\u001e\u0010\u000b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010#\"\u0004\bK\u0010%R\u001e\u0010\f\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u001f\"\u0004\bM\u0010!¨\u0006i"}, d2 = {"Lcom/ykb/ebook/model/ChapterInfo;", "", "content", "", "ctime", "ebookId", "id", "isDel", "sort", "", "title", "utime", "wordCount", "drawNotesList", "", "Lcom/ykb/ebook/model/Note;", "handleContent", "isPay", "", "payWay", "Lcom/ykb/ebook/model/Ways;", "imgList", "questionIdList", "questionIds", "", "paragraphIdList", "reviewList", "allWordLength", "originalContentStr", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/lang/String;ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/List;Ljava/util/List;ILjava/lang/String;)V", "getAllWordLength", "()I", "setAllWordLength", "(I)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getCtime", "setCtime", "getDrawNotesList", "()Ljava/util/List;", "setDrawNotesList", "(Ljava/util/List;)V", "getEbookId", "setEbookId", "getHandleContent", "setHandleContent", "getId", "setId", "getImgList", "setImgList", "setDel", "()Z", "setPay", "(Z)V", "getOriginalContentStr", "setOriginalContentStr", "getParagraphIdList", "setParagraphIdList", "getPayWay", "setPayWay", "getQuestionIdList", "setQuestionIdList", "getQuestionIds", "()Ljava/util/Map;", "setQuestionIds", "(Ljava/util/Map;)V", "getReviewList", "setReviewList", "getSort", "setSort", "getTitle", "setTitle", "getUtime", "setUtime", "getWordCount", "setWordCount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getDisplayTitle", "chineseConvert", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChapterInfo.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChapterInfo.kt\ncom/ykb/ebook/model/ChapterInfo\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,104:1\n1855#2,2:105\n*S KotlinDebug\n*F\n+ 1 ChapterInfo.kt\ncom/ykb/ebook/model/ChapterInfo\n*L\n51#1:105,2\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class ChapterInfo {
    private int allWordLength;

    @SerializedName("content")
    @NotNull
    private String content;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("draw_notes_list")
    @NotNull
    private List<Note> drawNotesList;

    @SerializedName("ebook_id")
    @NotNull
    private String ebookId;

    @NotNull
    private String handleContent;

    @SerializedName("id")
    @NotNull
    private String id;

    @NotNull
    private List<String> imgList;

    @SerializedName("is_del")
    @NotNull
    private String isDel;
    private boolean isPay;

    @NotNull
    private String originalContentStr;

    @NotNull
    private List<String> paragraphIdList;

    @NotNull
    private List<Ways> payWay;

    @NotNull
    private List<String> questionIdList;

    @NotNull
    private Map<Integer, ? extends List<String>> questionIds;

    @NotNull
    private List<String> reviewList;

    @SerializedName("sort")
    private int sort;

    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("utime")
    @NotNull
    private String utime;

    @SerializedName("word_count")
    private int wordCount;

    public ChapterInfo() {
        this(null, null, null, null, null, 0, null, null, 0, null, null, false, null, null, null, null, null, null, 0, null, 1048575, null);
    }

    public ChapterInfo(@NotNull String content, @NotNull String ctime, @NotNull String ebookId, @NotNull String id, @NotNull String isDel, int i2, @NotNull String title, @NotNull String utime, int i3, @NotNull List<Note> drawNotesList, @NotNull String handleContent, boolean z2, @NotNull List<Ways> payWay, @NotNull List<String> imgList, @NotNull List<String> questionIdList, @NotNull Map<Integer, ? extends List<String>> questionIds, @NotNull List<String> paragraphIdList, @NotNull List<String> reviewList, int i4, @NotNull String originalContentStr) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(ebookId, "ebookId");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isDel, "isDel");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(utime, "utime");
        Intrinsics.checkNotNullParameter(drawNotesList, "drawNotesList");
        Intrinsics.checkNotNullParameter(handleContent, "handleContent");
        Intrinsics.checkNotNullParameter(payWay, "payWay");
        Intrinsics.checkNotNullParameter(imgList, "imgList");
        Intrinsics.checkNotNullParameter(questionIdList, "questionIdList");
        Intrinsics.checkNotNullParameter(questionIds, "questionIds");
        Intrinsics.checkNotNullParameter(paragraphIdList, "paragraphIdList");
        Intrinsics.checkNotNullParameter(reviewList, "reviewList");
        Intrinsics.checkNotNullParameter(originalContentStr, "originalContentStr");
        this.content = content;
        this.ctime = ctime;
        this.ebookId = ebookId;
        this.id = id;
        this.isDel = isDel;
        this.sort = i2;
        this.title = title;
        this.utime = utime;
        this.wordCount = i3;
        this.drawNotesList = drawNotesList;
        this.handleContent = handleContent;
        this.isPay = z2;
        this.payWay = payWay;
        this.imgList = imgList;
        this.questionIdList = questionIdList;
        this.questionIds = questionIds;
        this.paragraphIdList = paragraphIdList;
        this.reviewList = reviewList;
        this.allWordLength = i4;
        this.originalContentStr = originalContentStr;
    }

    public static /* synthetic */ String getDisplayTitle$default(ChapterInfo chapterInfo, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        return chapterInfo.getDisplayTitle(z2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @NotNull
    public final List<Note> component10() {
        return this.drawNotesList;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getHandleContent() {
        return this.handleContent;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getIsPay() {
        return this.isPay;
    }

    @NotNull
    public final List<Ways> component13() {
        return this.payWay;
    }

    @NotNull
    public final List<String> component14() {
        return this.imgList;
    }

    @NotNull
    public final List<String> component15() {
        return this.questionIdList;
    }

    @NotNull
    public final Map<Integer, List<String>> component16() {
        return this.questionIds;
    }

    @NotNull
    public final List<String> component17() {
        return this.paragraphIdList;
    }

    @NotNull
    public final List<String> component18() {
        return this.reviewList;
    }

    /* renamed from: component19, reason: from getter */
    public final int getAllWordLength() {
        return this.allWordLength;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component20, reason: from getter */
    public final String getOriginalContentStr() {
        return this.originalContentStr;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getEbookId() {
        return this.ebookId;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getIsDel() {
        return this.isDel;
    }

    /* renamed from: component6, reason: from getter */
    public final int getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getUtime() {
        return this.utime;
    }

    /* renamed from: component9, reason: from getter */
    public final int getWordCount() {
        return this.wordCount;
    }

    @NotNull
    public final ChapterInfo copy(@NotNull String content, @NotNull String ctime, @NotNull String ebookId, @NotNull String id, @NotNull String isDel, int sort, @NotNull String title, @NotNull String utime, int wordCount, @NotNull List<Note> drawNotesList, @NotNull String handleContent, boolean isPay, @NotNull List<Ways> payWay, @NotNull List<String> imgList, @NotNull List<String> questionIdList, @NotNull Map<Integer, ? extends List<String>> questionIds, @NotNull List<String> paragraphIdList, @NotNull List<String> reviewList, int allWordLength, @NotNull String originalContentStr) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(ebookId, "ebookId");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isDel, "isDel");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(utime, "utime");
        Intrinsics.checkNotNullParameter(drawNotesList, "drawNotesList");
        Intrinsics.checkNotNullParameter(handleContent, "handleContent");
        Intrinsics.checkNotNullParameter(payWay, "payWay");
        Intrinsics.checkNotNullParameter(imgList, "imgList");
        Intrinsics.checkNotNullParameter(questionIdList, "questionIdList");
        Intrinsics.checkNotNullParameter(questionIds, "questionIds");
        Intrinsics.checkNotNullParameter(paragraphIdList, "paragraphIdList");
        Intrinsics.checkNotNullParameter(reviewList, "reviewList");
        Intrinsics.checkNotNullParameter(originalContentStr, "originalContentStr");
        return new ChapterInfo(content, ctime, ebookId, id, isDel, sort, title, utime, wordCount, drawNotesList, handleContent, isPay, payWay, imgList, questionIdList, questionIds, paragraphIdList, reviewList, allWordLength, originalContentStr);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChapterInfo)) {
            return false;
        }
        ChapterInfo chapterInfo = (ChapterInfo) other;
        return Intrinsics.areEqual(this.content, chapterInfo.content) && Intrinsics.areEqual(this.ctime, chapterInfo.ctime) && Intrinsics.areEqual(this.ebookId, chapterInfo.ebookId) && Intrinsics.areEqual(this.id, chapterInfo.id) && Intrinsics.areEqual(this.isDel, chapterInfo.isDel) && this.sort == chapterInfo.sort && Intrinsics.areEqual(this.title, chapterInfo.title) && Intrinsics.areEqual(this.utime, chapterInfo.utime) && this.wordCount == chapterInfo.wordCount && Intrinsics.areEqual(this.drawNotesList, chapterInfo.drawNotesList) && Intrinsics.areEqual(this.handleContent, chapterInfo.handleContent) && this.isPay == chapterInfo.isPay && Intrinsics.areEqual(this.payWay, chapterInfo.payWay) && Intrinsics.areEqual(this.imgList, chapterInfo.imgList) && Intrinsics.areEqual(this.questionIdList, chapterInfo.questionIdList) && Intrinsics.areEqual(this.questionIds, chapterInfo.questionIds) && Intrinsics.areEqual(this.paragraphIdList, chapterInfo.paragraphIdList) && Intrinsics.areEqual(this.reviewList, chapterInfo.reviewList) && this.allWordLength == chapterInfo.allWordLength && Intrinsics.areEqual(this.originalContentStr, chapterInfo.originalContentStr);
    }

    public final int getAllWordLength() {
        return this.allWordLength;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getDisplayTitle(boolean chineseConvert) {
        boolean zIsPay;
        List<Chapter> chapterList;
        String strT2s = AppPatternKt.getRnRegex().replace(this.title, "") + '\n';
        if (chineseConvert) {
            strT2s = ChineseUtils.INSTANCE.t2s(strT2s);
        }
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (chapterList = book.getChapterList()) == null) {
            zIsPay = false;
        } else {
            zIsPay = false;
            for (Chapter chapter : chapterList) {
                if (TextUtils.equals(chapter.getId(), this.id)) {
                    zIsPay = chapter.isPay();
                }
            }
        }
        if (zIsPay) {
            return strT2s;
        }
        String string = AppCtxKt.getAppCtx().getString(R.string.lock_title, strT2s);
        Intrinsics.checkNotNullExpressionValue(string, "appCtx.getString(R.strin…lock_title, displayTitle)");
        return string;
    }

    @NotNull
    public final List<Note> getDrawNotesList() {
        return this.drawNotesList;
    }

    @NotNull
    public final String getEbookId() {
        return this.ebookId;
    }

    @NotNull
    public final String getHandleContent() {
        return this.handleContent;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final List<String> getImgList() {
        return this.imgList;
    }

    @NotNull
    public final String getOriginalContentStr() {
        return this.originalContentStr;
    }

    @NotNull
    public final List<String> getParagraphIdList() {
        return this.paragraphIdList;
    }

    @NotNull
    public final List<Ways> getPayWay() {
        return this.payWay;
    }

    @NotNull
    public final List<String> getQuestionIdList() {
        return this.questionIdList;
    }

    @NotNull
    public final Map<Integer, List<String>> getQuestionIds() {
        return this.questionIds;
    }

    @NotNull
    public final List<String> getReviewList() {
        return this.reviewList;
    }

    public final int getSort() {
        return this.sort;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getUtime() {
        return this.utime;
    }

    public final int getWordCount() {
        return this.wordCount;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((((((((((((((this.content.hashCode() * 31) + this.ctime.hashCode()) * 31) + this.ebookId.hashCode()) * 31) + this.id.hashCode()) * 31) + this.isDel.hashCode()) * 31) + this.sort) * 31) + this.title.hashCode()) * 31) + this.utime.hashCode()) * 31) + this.wordCount) * 31) + this.drawNotesList.hashCode()) * 31) + this.handleContent.hashCode()) * 31;
        boolean z2 = this.isPay;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return ((((((((((((((((iHashCode + i2) * 31) + this.payWay.hashCode()) * 31) + this.imgList.hashCode()) * 31) + this.questionIdList.hashCode()) * 31) + this.questionIds.hashCode()) * 31) + this.paragraphIdList.hashCode()) * 31) + this.reviewList.hashCode()) * 31) + this.allWordLength) * 31) + this.originalContentStr.hashCode();
    }

    @NotNull
    public final String isDel() {
        return this.isDel;
    }

    public final boolean isPay() {
        return this.isPay;
    }

    public final void setAllWordLength(int i2) {
        this.allWordLength = i2;
    }

    public final void setContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setDel(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isDel = str;
    }

    public final void setDrawNotesList(@NotNull List<Note> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.drawNotesList = list;
    }

    public final void setEbookId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ebookId = str;
    }

    public final void setHandleContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.handleContent = str;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setImgList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.imgList = list;
    }

    public final void setOriginalContentStr(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.originalContentStr = str;
    }

    public final void setParagraphIdList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.paragraphIdList = list;
    }

    public final void setPay(boolean z2) {
        this.isPay = z2;
    }

    public final void setPayWay(@NotNull List<Ways> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.payWay = list;
    }

    public final void setQuestionIdList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.questionIdList = list;
    }

    public final void setQuestionIds(@NotNull Map<Integer, ? extends List<String>> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.questionIds = map;
    }

    public final void setReviewList(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.reviewList = list;
    }

    public final void setSort(int i2) {
        this.sort = i2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setUtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.utime = str;
    }

    public final void setWordCount(int i2) {
        this.wordCount = i2;
    }

    @NotNull
    public String toString() {
        return "ChapterInfo(content=" + this.content + ", ctime=" + this.ctime + ", ebookId=" + this.ebookId + ", id=" + this.id + ", isDel=" + this.isDel + ", sort=" + this.sort + ", title=" + this.title + ", utime=" + this.utime + ", wordCount=" + this.wordCount + ", drawNotesList=" + this.drawNotesList + ", handleContent=" + this.handleContent + ", isPay=" + this.isPay + ", payWay=" + this.payWay + ", imgList=" + this.imgList + ", questionIdList=" + this.questionIdList + ", questionIds=" + this.questionIds + ", paragraphIdList=" + this.paragraphIdList + ", reviewList=" + this.reviewList + ", allWordLength=" + this.allWordLength + ", originalContentStr=" + this.originalContentStr + ')';
    }

    public /* synthetic */ ChapterInfo(String str, String str2, String str3, String str4, String str5, int i2, String str6, String str7, int i3, List list, String str8, boolean z2, List list2, List list3, List list4, Map map, List list5, List list6, int i4, String str9, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? "" : str, (i5 & 2) != 0 ? "" : str2, (i5 & 4) != 0 ? "" : str3, (i5 & 8) != 0 ? "" : str4, (i5 & 16) != 0 ? "" : str5, (i5 & 32) != 0 ? 0 : i2, (i5 & 64) != 0 ? "" : str6, (i5 & 128) != 0 ? "" : str7, (i5 & 256) != 0 ? 0 : i3, (i5 & 512) != 0 ? new ArrayList() : list, (i5 & 1024) != 0 ? "" : str8, (i5 & 2048) != 0 ? true : z2, (i5 & 4096) != 0 ? CollectionsKt__CollectionsKt.emptyList() : list2, (i5 & 8192) != 0 ? new ArrayList() : list3, (i5 & 16384) != 0 ? new ArrayList() : list4, (i5 & 32768) != 0 ? new HashMap() : map, (i5 & 65536) != 0 ? new ArrayList() : list5, (i5 & 131072) != 0 ? new ArrayList() : list6, (i5 & 262144) != 0 ? 0 : i4, (i5 & 524288) != 0 ? "" : str9);
    }
}
