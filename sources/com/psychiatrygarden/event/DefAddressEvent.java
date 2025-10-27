package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/event/DefAddressEvent;", "", "addressId", "", "(Ljava/lang/String;)V", "getAddressId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class DefAddressEvent {

    @NotNull
    private final String addressId;

    public DefAddressEvent(@NotNull String addressId) {
        Intrinsics.checkNotNullParameter(addressId, "addressId");
        this.addressId = addressId;
    }

    public static /* synthetic */ DefAddressEvent copy$default(DefAddressEvent defAddressEvent, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = defAddressEvent.addressId;
        }
        return defAddressEvent.copy(str);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getAddressId() {
        return this.addressId;
    }

    @NotNull
    public final DefAddressEvent copy(@NotNull String addressId) {
        Intrinsics.checkNotNullParameter(addressId, "addressId");
        return new DefAddressEvent(addressId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DefAddressEvent) && Intrinsics.areEqual(this.addressId, ((DefAddressEvent) other).addressId);
    }

    @NotNull
    public final String getAddressId() {
        return this.addressId;
    }

    public int hashCode() {
        return this.addressId.hashCode();
    }

    @NotNull
    public String toString() {
        return "DefAddressEvent(addressId=" + this.addressId + ')';
    }
}
