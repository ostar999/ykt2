package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/event/GoToLivingEvent;", "", "type", "", "message", "(Ljava/lang/String;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getType", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class GoToLivingEvent {

    @NotNull
    private final String message;

    @NotNull
    private final String type;

    public GoToLivingEvent(@NotNull String type, @NotNull String message) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(message, "message");
        this.type = type;
        this.message = message;
    }

    public static /* synthetic */ GoToLivingEvent copy$default(GoToLivingEvent goToLivingEvent, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = goToLivingEvent.type;
        }
        if ((i2 & 2) != 0) {
            str2 = goToLivingEvent.message;
        }
        return goToLivingEvent.copy(str, str2);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @NotNull
    public final GoToLivingEvent copy(@NotNull String type, @NotNull String message) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(message, "message");
        return new GoToLivingEvent(type, message);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GoToLivingEvent)) {
            return false;
        }
        GoToLivingEvent goToLivingEvent = (GoToLivingEvent) other;
        return Intrinsics.areEqual(this.type, goToLivingEvent.type) && Intrinsics.areEqual(this.message, goToLivingEvent.message);
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return (this.type.hashCode() * 31) + this.message.hashCode();
    }

    @NotNull
    public String toString() {
        return "GoToLivingEvent(type=" + this.type + ", message=" + this.message + ')';
    }
}
