package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/event/QuestionCombineDataEvent;", "", "hasData", "", "(Z)V", "getHasData", "()Z", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class QuestionCombineDataEvent {
    private final boolean hasData;

    public QuestionCombineDataEvent() {
        this(false, 1, null);
    }

    public QuestionCombineDataEvent(boolean z2) {
        this.hasData = z2;
    }

    public final boolean getHasData() {
        return this.hasData;
    }

    public /* synthetic */ QuestionCombineDataEvent(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2);
    }
}
