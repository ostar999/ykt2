package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0007\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/event/ShowQuestionCombineEvent;", "", "show", "", "showShortCut", "(ZZ)V", "getShow", "()Z", "getShowShortCut", "setShowShortCut", "(Z)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ShowQuestionCombineEvent {
    private final boolean show;
    private boolean showShortCut;

    /* JADX WARN: Illegal instructions before constructor call */
    public ShowQuestionCombineEvent() {
        boolean z2 = false;
        this(z2, z2, 3, null);
    }

    public ShowQuestionCombineEvent(boolean z2, boolean z3) {
        this.show = z2;
        this.showShortCut = z3;
    }

    public final boolean getShow() {
        return this.show;
    }

    public final boolean getShowShortCut() {
        return this.showShortCut;
    }

    public final void setShowShortCut(boolean z2) {
        this.showShortCut = z2;
    }

    public /* synthetic */ ShowQuestionCombineEvent(boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) != 0 ? false : z3);
    }
}
