package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/event/HaveFreeWatchEvent;", "", "courseId", "", "haveFreeWatch", "", "(Ljava/lang/String;Z)V", "getCourseId", "()Ljava/lang/String;", "getHaveFreeWatch", "()Z", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class HaveFreeWatchEvent {

    @Nullable
    private final String courseId;
    private final boolean haveFreeWatch;

    public HaveFreeWatchEvent(@Nullable String str, boolean z2) {
        this.courseId = str;
        this.haveFreeWatch = z2;
    }

    public static /* synthetic */ HaveFreeWatchEvent copy$default(HaveFreeWatchEvent haveFreeWatchEvent, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = haveFreeWatchEvent.courseId;
        }
        if ((i2 & 2) != 0) {
            z2 = haveFreeWatchEvent.haveFreeWatch;
        }
        return haveFreeWatchEvent.copy(str, z2);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCourseId() {
        return this.courseId;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getHaveFreeWatch() {
        return this.haveFreeWatch;
    }

    @NotNull
    public final HaveFreeWatchEvent copy(@Nullable String courseId, boolean haveFreeWatch) {
        return new HaveFreeWatchEvent(courseId, haveFreeWatch);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HaveFreeWatchEvent)) {
            return false;
        }
        HaveFreeWatchEvent haveFreeWatchEvent = (HaveFreeWatchEvent) other;
        return Intrinsics.areEqual(this.courseId, haveFreeWatchEvent.courseId) && this.haveFreeWatch == haveFreeWatchEvent.haveFreeWatch;
    }

    @Nullable
    public final String getCourseId() {
        return this.courseId;
    }

    public final boolean getHaveFreeWatch() {
        return this.haveFreeWatch;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.courseId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        boolean z2 = this.haveFreeWatch;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    @NotNull
    public String toString() {
        return "HaveFreeWatchEvent(courseId=" + this.courseId + ", haveFreeWatch=" + this.haveFreeWatch + ')';
    }
}
