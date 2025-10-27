package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/event/EditAddressEvent;", "", "addressId", "", "full_address", "mobile", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAddressId", "()Ljava/lang/String;", "getFull_address", "getMobile", "getName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class EditAddressEvent {

    @NotNull
    private final String addressId;

    @NotNull
    private final String full_address;

    @NotNull
    private final String mobile;

    @NotNull
    private final String name;

    public EditAddressEvent(@NotNull String addressId, @NotNull String full_address, @NotNull String mobile, @NotNull String name) {
        Intrinsics.checkNotNullParameter(addressId, "addressId");
        Intrinsics.checkNotNullParameter(full_address, "full_address");
        Intrinsics.checkNotNullParameter(mobile, "mobile");
        Intrinsics.checkNotNullParameter(name, "name");
        this.addressId = addressId;
        this.full_address = full_address;
        this.mobile = mobile;
        this.name = name;
    }

    public static /* synthetic */ EditAddressEvent copy$default(EditAddressEvent editAddressEvent, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = editAddressEvent.addressId;
        }
        if ((i2 & 2) != 0) {
            str2 = editAddressEvent.full_address;
        }
        if ((i2 & 4) != 0) {
            str3 = editAddressEvent.mobile;
        }
        if ((i2 & 8) != 0) {
            str4 = editAddressEvent.name;
        }
        return editAddressEvent.copy(str, str2, str3, str4);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getAddressId() {
        return this.addressId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getFull_address() {
        return this.full_address;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getMobile() {
        return this.mobile;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final EditAddressEvent copy(@NotNull String addressId, @NotNull String full_address, @NotNull String mobile, @NotNull String name) {
        Intrinsics.checkNotNullParameter(addressId, "addressId");
        Intrinsics.checkNotNullParameter(full_address, "full_address");
        Intrinsics.checkNotNullParameter(mobile, "mobile");
        Intrinsics.checkNotNullParameter(name, "name");
        return new EditAddressEvent(addressId, full_address, mobile, name);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EditAddressEvent)) {
            return false;
        }
        EditAddressEvent editAddressEvent = (EditAddressEvent) other;
        return Intrinsics.areEqual(this.addressId, editAddressEvent.addressId) && Intrinsics.areEqual(this.full_address, editAddressEvent.full_address) && Intrinsics.areEqual(this.mobile, editAddressEvent.mobile) && Intrinsics.areEqual(this.name, editAddressEvent.name);
    }

    @NotNull
    public final String getAddressId() {
        return this.addressId;
    }

    @NotNull
    public final String getFull_address() {
        return this.full_address;
    }

    @NotNull
    public final String getMobile() {
        return this.mobile;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (((((this.addressId.hashCode() * 31) + this.full_address.hashCode()) * 31) + this.mobile.hashCode()) * 31) + this.name.hashCode();
    }

    @NotNull
    public String toString() {
        return "EditAddressEvent(addressId=" + this.addressId + ", full_address=" + this.full_address + ", mobile=" + this.mobile + ", name=" + this.name + ')';
    }
}
