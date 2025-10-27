package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/event/RedoOtherQuestionEvent;", "", "isError", "", "isLastDo", "", "(Ljava/lang/String;Z)V", "()Ljava/lang/String;", "setError", "(Ljava/lang/String;)V", "()Z", "setLastDo", "(Z)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class RedoOtherQuestionEvent {

    @NotNull
    private String isError;
    private boolean isLastDo;

    public RedoOtherQuestionEvent(@NotNull String isError, boolean z2) {
        Intrinsics.checkNotNullParameter(isError, "isError");
        this.isError = isError;
        this.isLastDo = z2;
    }

    @NotNull
    /* renamed from: isError, reason: from getter */
    public final String getIsError() {
        return this.isError;
    }

    /* renamed from: isLastDo, reason: from getter */
    public final boolean getIsLastDo() {
        return this.isLastDo;
    }

    public final void setError(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isError = str;
    }

    public final void setLastDo(boolean z2) {
        this.isLastDo = z2;
    }

    public /* synthetic */ RedoOtherQuestionEvent(String str, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? false : z2);
    }
}
