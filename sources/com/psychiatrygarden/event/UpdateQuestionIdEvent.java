package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/event/UpdateQuestionIdEvent;", "", "questionId", "", "(Ljava/lang/String;)V", "getQuestionId", "()Ljava/lang/String;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class UpdateQuestionIdEvent {

    @NotNull
    private final String questionId;

    public UpdateQuestionIdEvent(@NotNull String questionId) {
        Intrinsics.checkNotNullParameter(questionId, "questionId");
        this.questionId = questionId;
    }

    @NotNull
    public final String getQuestionId() {
        return this.questionId;
    }
}
