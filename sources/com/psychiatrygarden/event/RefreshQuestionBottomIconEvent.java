package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\b\"\u0004\b\u000b\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/event/RefreshQuestionBottomIconEvent;", "", "questionId", "", "isPraise", "", "isComment", "(Ljava/lang/String;II)V", "()I", "setComment", "(I)V", "setPraise", "getQuestionId", "()Ljava/lang/String;", "setQuestionId", "(Ljava/lang/String;)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RefreshQuestionBottomIconEvent {
    private int isComment;
    private int isPraise;

    @NotNull
    private String questionId;

    public RefreshQuestionBottomIconEvent(@NotNull String questionId, int i2, int i3) {
        Intrinsics.checkNotNullParameter(questionId, "questionId");
        this.questionId = questionId;
        this.isPraise = i2;
        this.isComment = i3;
    }

    @NotNull
    public final String getQuestionId() {
        return this.questionId;
    }

    /* renamed from: isComment, reason: from getter */
    public final int getIsComment() {
        return this.isComment;
    }

    /* renamed from: isPraise, reason: from getter */
    public final int getIsPraise() {
        return this.isPraise;
    }

    public final void setComment(int i2) {
        this.isComment = i2;
    }

    public final void setPraise(int i2) {
        this.isPraise = i2;
    }

    public final void setQuestionId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.questionId = str;
    }
}
