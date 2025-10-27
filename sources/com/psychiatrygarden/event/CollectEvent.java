package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/event/CollectEvent;", "", "courseId", "", "collect", "", "(Ljava/lang/String;Z)V", "getCollect", "()Z", "getCourseId", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CollectEvent {
    private final boolean collect;

    @NotNull
    private final String courseId;

    public CollectEvent(@NotNull String courseId, boolean z2) {
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        this.courseId = courseId;
        this.collect = z2;
    }

    public static /* synthetic */ CollectEvent copy$default(CollectEvent collectEvent, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = collectEvent.courseId;
        }
        if ((i2 & 2) != 0) {
            z2 = collectEvent.collect;
        }
        return collectEvent.copy(str, z2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getCourseId() {
        return this.courseId;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getCollect() {
        return this.collect;
    }

    @NotNull
    public final CollectEvent copy(@NotNull String courseId, boolean collect) {
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        return new CollectEvent(courseId, collect);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CollectEvent)) {
            return false;
        }
        CollectEvent collectEvent = (CollectEvent) other;
        return Intrinsics.areEqual(this.courseId, collectEvent.courseId) && this.collect == collectEvent.collect;
    }

    public final boolean getCollect() {
        return this.collect;
    }

    @NotNull
    public final String getCourseId() {
        return this.courseId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = this.courseId.hashCode() * 31;
        boolean z2 = this.collect;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    @NotNull
    public String toString() {
        return "CollectEvent(courseId=" + this.courseId + ", collect=" + this.collect + ')';
    }
}
