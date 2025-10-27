package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003Ju\u0010\u001f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e¨\u0006&"}, d2 = {"Lcom/psychiatrygarden/bean/CouponBean;", "", "id", "", "user_id", "goods_id", "value", "is_used", "app_id", "description", "goods_type", "goods_name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getApp_id", "()Ljava/lang/String;", "getDescription", "getGoods_id", "getGoods_name", "getGoods_type", "getId", "getUser_id", "getValue", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CouponBean {

    @Nullable
    private final String app_id;

    @Nullable
    private final String description;

    @Nullable
    private final String goods_id;

    @Nullable
    private final String goods_name;

    @Nullable
    private final String goods_type;

    @Nullable
    private final String id;

    @Nullable
    private final String is_used;

    @Nullable
    private final String user_id;

    @Nullable
    private final String value;

    public CouponBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9) {
        this.id = str;
        this.user_id = str2;
        this.goods_id = str3;
        this.value = str4;
        this.is_used = str5;
        this.app_id = str6;
        this.description = str7;
        this.goods_type = str8;
        this.goods_name = str9;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getGoods_id() {
        return this.goods_id;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getIs_used() {
        return this.is_used;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getGoods_type() {
        return this.goods_type;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getGoods_name() {
        return this.goods_name;
    }

    @NotNull
    public final CouponBean copy(@Nullable String id, @Nullable String user_id, @Nullable String goods_id, @Nullable String value, @Nullable String is_used, @Nullable String app_id, @Nullable String description, @Nullable String goods_type, @Nullable String goods_name) {
        return new CouponBean(id, user_id, goods_id, value, is_used, app_id, description, goods_type, goods_name);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CouponBean)) {
            return false;
        }
        CouponBean couponBean = (CouponBean) other;
        return Intrinsics.areEqual(this.id, couponBean.id) && Intrinsics.areEqual(this.user_id, couponBean.user_id) && Intrinsics.areEqual(this.goods_id, couponBean.goods_id) && Intrinsics.areEqual(this.value, couponBean.value) && Intrinsics.areEqual(this.is_used, couponBean.is_used) && Intrinsics.areEqual(this.app_id, couponBean.app_id) && Intrinsics.areEqual(this.description, couponBean.description) && Intrinsics.areEqual(this.goods_type, couponBean.goods_type) && Intrinsics.areEqual(this.goods_name, couponBean.goods_name);
    }

    @Nullable
    public final String getApp_id() {
        return this.app_id;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getGoods_id() {
        return this.goods_id;
    }

    @Nullable
    public final String getGoods_name() {
        return this.goods_name;
    }

    @Nullable
    public final String getGoods_type() {
        return this.goods_type;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getUser_id() {
        return this.user_id;
    }

    @Nullable
    public final String getValue() {
        return this.value;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.user_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.goods_id;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.value;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.is_used;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.app_id;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.description;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.goods_type;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.goods_name;
        return iHashCode8 + (str9 != null ? str9.hashCode() : 0);
    }

    @Nullable
    public final String is_used() {
        return this.is_used;
    }

    @NotNull
    public String toString() {
        return "CouponBean(id=" + this.id + ", user_id=" + this.user_id + ", goods_id=" + this.goods_id + ", value=" + this.value + ", is_used=" + this.is_used + ", app_id=" + this.app_id + ", description=" + this.description + ", goods_type=" + this.goods_type + ", goods_name=" + this.goods_name + ')';
    }
}
