package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/bean/CommonProblem;", "", "question", "", "answer", "(Ljava/lang/String;Ljava/lang/String;)V", "getAnswer", "()Ljava/lang/String;", "getQuestion", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CommonProblem {

    @NotNull
    private final String answer;

    @NotNull
    private final String question;

    public CommonProblem(@NotNull String question, @NotNull String answer) {
        Intrinsics.checkNotNullParameter(question, "question");
        Intrinsics.checkNotNullParameter(answer, "answer");
        this.question = question;
        this.answer = answer;
    }

    public static /* synthetic */ CommonProblem copy$default(CommonProblem commonProblem, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = commonProblem.question;
        }
        if ((i2 & 2) != 0) {
            str2 = commonProblem.answer;
        }
        return commonProblem.copy(str, str2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getQuestion() {
        return this.question;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getAnswer() {
        return this.answer;
    }

    @NotNull
    public final CommonProblem copy(@NotNull String question, @NotNull String answer) {
        Intrinsics.checkNotNullParameter(question, "question");
        Intrinsics.checkNotNullParameter(answer, "answer");
        return new CommonProblem(question, answer);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CommonProblem)) {
            return false;
        }
        CommonProblem commonProblem = (CommonProblem) other;
        return Intrinsics.areEqual(this.question, commonProblem.question) && Intrinsics.areEqual(this.answer, commonProblem.answer);
    }

    @NotNull
    public final String getAnswer() {
        return this.answer;
    }

    @NotNull
    public final String getQuestion() {
        return this.question;
    }

    public int hashCode() {
        return (this.question.hashCode() * 31) + this.answer.hashCode();
    }

    @NotNull
    public String toString() {
        return "CommonProblem(question=" + this.question + ", answer=" + this.answer + ')';
    }
}
