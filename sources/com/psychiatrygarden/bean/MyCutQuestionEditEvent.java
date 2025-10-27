package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/bean/MyCutQuestionEditEvent;", "", "identityId", "", "isShowEdit", "", "isKnowledge", "(Ljava/lang/String;ZZ)V", "getIdentityId", "()Ljava/lang/String;", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class MyCutQuestionEditEvent {

    @NotNull
    private final String identityId;
    private final boolean isKnowledge;
    private final boolean isShowEdit;

    public MyCutQuestionEditEvent(@NotNull String identityId, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(identityId, "identityId");
        this.identityId = identityId;
        this.isShowEdit = z2;
        this.isKnowledge = z3;
    }

    public static /* synthetic */ MyCutQuestionEditEvent copy$default(MyCutQuestionEditEvent myCutQuestionEditEvent, String str, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = myCutQuestionEditEvent.identityId;
        }
        if ((i2 & 2) != 0) {
            z2 = myCutQuestionEditEvent.isShowEdit;
        }
        if ((i2 & 4) != 0) {
            z3 = myCutQuestionEditEvent.isKnowledge;
        }
        return myCutQuestionEditEvent.copy(str, z2, z3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getIdentityId() {
        return this.identityId;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsShowEdit() {
        return this.isShowEdit;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getIsKnowledge() {
        return this.isKnowledge;
    }

    @NotNull
    public final MyCutQuestionEditEvent copy(@NotNull String identityId, boolean isShowEdit, boolean isKnowledge) {
        Intrinsics.checkNotNullParameter(identityId, "identityId");
        return new MyCutQuestionEditEvent(identityId, isShowEdit, isKnowledge);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MyCutQuestionEditEvent)) {
            return false;
        }
        MyCutQuestionEditEvent myCutQuestionEditEvent = (MyCutQuestionEditEvent) other;
        return Intrinsics.areEqual(this.identityId, myCutQuestionEditEvent.identityId) && this.isShowEdit == myCutQuestionEditEvent.isShowEdit && this.isKnowledge == myCutQuestionEditEvent.isKnowledge;
    }

    @NotNull
    public final String getIdentityId() {
        return this.identityId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = this.identityId.hashCode() * 31;
        boolean z2 = this.isShowEdit;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode + i2) * 31;
        boolean z3 = this.isKnowledge;
        return i3 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final boolean isKnowledge() {
        return this.isKnowledge;
    }

    public final boolean isShowEdit() {
        return this.isShowEdit;
    }

    @NotNull
    public String toString() {
        return "MyCutQuestionEditEvent(identityId=" + this.identityId + ", isShowEdit=" + this.isShowEdit + ", isKnowledge=" + this.isKnowledge + ')';
    }
}
