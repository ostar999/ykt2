package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/CS;", "", "cs_name", "", "cs_type", "contact", "wechat_corpid", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getContact", "()Ljava/lang/String;", "getCs_name", "getCs_type", "getWechat_corpid", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CS {

    @Nullable
    private final String contact;

    @Nullable
    private final String cs_name;

    @Nullable
    private final String cs_type;

    @Nullable
    private final String wechat_corpid;

    public CS(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.cs_name = str;
        this.cs_type = str2;
        this.contact = str3;
        this.wechat_corpid = str4;
    }

    public static /* synthetic */ CS copy$default(CS cs, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = cs.cs_name;
        }
        if ((i2 & 2) != 0) {
            str2 = cs.cs_type;
        }
        if ((i2 & 4) != 0) {
            str3 = cs.contact;
        }
        if ((i2 & 8) != 0) {
            str4 = cs.wechat_corpid;
        }
        return cs.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCs_name() {
        return this.cs_name;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCs_type() {
        return this.cs_type;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getContact() {
        return this.contact;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getWechat_corpid() {
        return this.wechat_corpid;
    }

    @NotNull
    public final CS copy(@Nullable String cs_name, @Nullable String cs_type, @Nullable String contact, @Nullable String wechat_corpid) {
        return new CS(cs_name, cs_type, contact, wechat_corpid);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CS)) {
            return false;
        }
        CS cs = (CS) other;
        return Intrinsics.areEqual(this.cs_name, cs.cs_name) && Intrinsics.areEqual(this.cs_type, cs.cs_type) && Intrinsics.areEqual(this.contact, cs.contact) && Intrinsics.areEqual(this.wechat_corpid, cs.wechat_corpid);
    }

    @Nullable
    public final String getContact() {
        return this.contact;
    }

    @Nullable
    public final String getCs_name() {
        return this.cs_name;
    }

    @Nullable
    public final String getCs_type() {
        return this.cs_type;
    }

    @Nullable
    public final String getWechat_corpid() {
        return this.wechat_corpid;
    }

    public int hashCode() {
        String str = this.cs_name;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.cs_type;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.contact;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.wechat_corpid;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "CS(cs_name=" + this.cs_name + ", cs_type=" + this.cs_type + ", contact=" + this.contact + ", wechat_corpid=" + this.wechat_corpid + ')';
    }
}
