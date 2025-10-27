package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0018\u0018\u00002\u00020\u0001Bk\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000fJ\b\u0010#\u001a\u00020\u0003H\u0016R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0013\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\r\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0017R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0019\"\u0004\b \u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0011¨\u0006$"}, d2 = {"Lcom/psychiatrygarden/bean/StatisticsData;", "", "chapter", "", "childChapter", "total", "", "answered", "right", "wrong", "describle", "showNextChapter", "", "fromQuestionCombine", "rightPercent", "(Ljava/lang/String;Ljava/lang/String;IIIILjava/lang/String;ZZLjava/lang/String;)V", "getAnswered", "()I", "getChapter", "()Ljava/lang/String;", "getChildChapter", "getDescrible", "setDescrible", "(Ljava/lang/String;)V", "getFromQuestionCombine", "()Z", "setFromQuestionCombine", "(Z)V", "getRight", "getRightPercent", "setRightPercent", "getShowNextChapter", "setShowNextChapter", "getTotal", "getWrong", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class StatisticsData {
    private final int answered;

    @Nullable
    private final String chapter;

    @Nullable
    private final String childChapter;

    @Nullable
    private String describle;
    private boolean fromQuestionCombine;
    private final int right;

    @Nullable
    private String rightPercent;
    private boolean showNextChapter;
    private final int total;
    private final int wrong;

    public StatisticsData(@Nullable String str, @Nullable String str2, int i2, int i3, int i4, int i5, @Nullable String str3, boolean z2, boolean z3, @Nullable String str4) {
        this.chapter = str;
        this.childChapter = str2;
        this.total = i2;
        this.answered = i3;
        this.right = i4;
        this.wrong = i5;
        this.describle = str3;
        this.showNextChapter = z2;
        this.fromQuestionCombine = z3;
        this.rightPercent = str4;
    }

    public final int getAnswered() {
        return this.answered;
    }

    @Nullable
    public final String getChapter() {
        return this.chapter;
    }

    @Nullable
    public final String getChildChapter() {
        return this.childChapter;
    }

    @Nullable
    public final String getDescrible() {
        return this.describle;
    }

    public final boolean getFromQuestionCombine() {
        return this.fromQuestionCombine;
    }

    public final int getRight() {
        return this.right;
    }

    @Nullable
    public final String getRightPercent() {
        return this.rightPercent;
    }

    public final boolean getShowNextChapter() {
        return this.showNextChapter;
    }

    public final int getTotal() {
        return this.total;
    }

    public final int getWrong() {
        return this.wrong;
    }

    public final void setDescrible(@Nullable String str) {
        this.describle = str;
    }

    public final void setFromQuestionCombine(boolean z2) {
        this.fromQuestionCombine = z2;
    }

    public final void setRightPercent(@Nullable String str) {
        this.rightPercent = str;
    }

    public final void setShowNextChapter(boolean z2) {
        this.showNextChapter = z2;
    }

    @NotNull
    public String toString() {
        return "StatisticsData(chapter=" + this.chapter + ", childChapter=" + this.childChapter + ", total=" + this.total + ", answered=" + this.answered + ", right=" + this.right + ", wrong=" + this.wrong + ", describle=" + this.describle + ", showNextChapter=" + this.showNextChapter + ", fromQuestionCombine=" + this.fromQuestionCombine + ", rightPercent=" + this.rightPercent + ')';
    }

    public /* synthetic */ StatisticsData(String str, String str2, int i2, int i3, int i4, int i5, String str3, boolean z2, boolean z3, String str4, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i6 & 4) != 0 ? 0 : i2, (i6 & 8) != 0 ? 0 : i3, (i6 & 16) != 0 ? 0 : i4, (i6 & 32) != 0 ? 0 : i5, str3, (i6 & 128) != 0 ? false : z2, (i6 & 256) != 0 ? false : z3, (i6 & 512) != 0 ? "0" : str4);
    }
}
