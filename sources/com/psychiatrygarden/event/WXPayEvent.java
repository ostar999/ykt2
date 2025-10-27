package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/event/WXPayEvent;", "", "status", "Lcom/psychiatrygarden/event/WXPayStatus;", "(Lcom/psychiatrygarden/event/WXPayStatus;)V", "getStatus", "()Lcom/psychiatrygarden/event/WXPayStatus;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class WXPayEvent {

    @NotNull
    private final WXPayStatus status;

    public WXPayEvent(@NotNull WXPayStatus status) {
        Intrinsics.checkNotNullParameter(status, "status");
        this.status = status;
    }

    public static /* synthetic */ WXPayEvent copy$default(WXPayEvent wXPayEvent, WXPayStatus wXPayStatus, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            wXPayStatus = wXPayEvent.status;
        }
        return wXPayEvent.copy(wXPayStatus);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final WXPayStatus getStatus() {
        return this.status;
    }

    @NotNull
    public final WXPayEvent copy(@NotNull WXPayStatus status) {
        Intrinsics.checkNotNullParameter(status, "status");
        return new WXPayEvent(status);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof WXPayEvent) && this.status == ((WXPayEvent) other).status;
    }

    @NotNull
    public final WXPayStatus getStatus() {
        return this.status;
    }

    public int hashCode() {
        return this.status.hashCode();
    }

    @NotNull
    public String toString() {
        return "WXPayEvent(status=" + this.status + ')';
    }
}
