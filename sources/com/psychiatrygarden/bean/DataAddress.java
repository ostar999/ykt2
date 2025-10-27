package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/DataAddress;", "", "express_name", "", "express_user_name", "express_phone", "express_address", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getExpress_address", "()Ljava/lang/String;", "getExpress_name", "getExpress_phone", "getExpress_user_name", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class DataAddress {

    @Nullable
    private final String express_address;

    @Nullable
    private final String express_name;

    @Nullable
    private final String express_phone;

    @Nullable
    private final String express_user_name;

    public DataAddress(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.express_name = str;
        this.express_user_name = str2;
        this.express_phone = str3;
        this.express_address = str4;
    }

    public static /* synthetic */ DataAddress copy$default(DataAddress dataAddress, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = dataAddress.express_name;
        }
        if ((i2 & 2) != 0) {
            str2 = dataAddress.express_user_name;
        }
        if ((i2 & 4) != 0) {
            str3 = dataAddress.express_phone;
        }
        if ((i2 & 8) != 0) {
            str4 = dataAddress.express_address;
        }
        return dataAddress.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getExpress_name() {
        return this.express_name;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getExpress_user_name() {
        return this.express_user_name;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getExpress_phone() {
        return this.express_phone;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getExpress_address() {
        return this.express_address;
    }

    @NotNull
    public final DataAddress copy(@Nullable String express_name, @Nullable String express_user_name, @Nullable String express_phone, @Nullable String express_address) {
        return new DataAddress(express_name, express_user_name, express_phone, express_address);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DataAddress)) {
            return false;
        }
        DataAddress dataAddress = (DataAddress) other;
        return Intrinsics.areEqual(this.express_name, dataAddress.express_name) && Intrinsics.areEqual(this.express_user_name, dataAddress.express_user_name) && Intrinsics.areEqual(this.express_phone, dataAddress.express_phone) && Intrinsics.areEqual(this.express_address, dataAddress.express_address);
    }

    @Nullable
    public final String getExpress_address() {
        return this.express_address;
    }

    @Nullable
    public final String getExpress_name() {
        return this.express_name;
    }

    @Nullable
    public final String getExpress_phone() {
        return this.express_phone;
    }

    @Nullable
    public final String getExpress_user_name() {
        return this.express_user_name;
    }

    public int hashCode() {
        String str = this.express_name;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.express_user_name;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.express_phone;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.express_address;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "DataAddress(express_name=" + this.express_name + ", express_user_name=" + this.express_user_name + ", express_phone=" + this.express_phone + ", express_address=" + this.express_address + ')';
    }
}
