package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\bW\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u009d\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u001fJ\u000b\u0010=\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010M\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010N\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010O\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010S\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010W\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010X\u001a\u0004\u0018\u00010\u0003HÆ\u0003JÙ\u0002\u0010Y\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010Z\u001a\u00020[2\b\u0010\\\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010]\u001a\u00020^HÖ\u0001J\t\u0010_\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010!R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010!R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010!R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010!R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010!R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010!R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010!R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010!R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010!R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010!R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010!R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010!R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u0010!R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u0010!R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010!R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010!R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b4\u0010!R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b5\u0010!R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u0010!R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b7\u0010!R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b8\u0010!R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b9\u0010!R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b:\u0010!R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b;\u0010!R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b<\u0010!¨\u0006`"}, d2 = {"Lcom/psychiatrygarden/bean/OrderFeeAddressMultipleBean;", "", "total_amount", "", "original_price", "original_total_price", "promotion", "total_amount_yuan", "privilege_yuan", "original_price_yuan", "original_total_price_yuan", "promotion_yuan", "privilege_id", "quantity", "title", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "price", "postage_price", "postage_price_yuan", "description", "coupon_price", "red_packet_price", "coupon_price_yuan", "red_packet_price_yuan", "coupon_id", "red_packet_id", "order_price", "coupon_available_count", "coupon_not_available_count", "red_packet_available_count", "red_packet_not_available_count", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCoupon_available_count", "()Ljava/lang/String;", "getCoupon_id", "getCoupon_not_available_count", "getCoupon_price", "getCoupon_price_yuan", "getCover", "getDescription", "getOrder_price", "getOriginal_price", "getOriginal_price_yuan", "getOriginal_total_price", "getOriginal_total_price_yuan", "getPostage_price", "getPostage_price_yuan", "getPrice", "getPrivilege_id", "getPrivilege_yuan", "getPromotion", "getPromotion_yuan", "getQuantity", "getRed_packet_available_count", "getRed_packet_id", "getRed_packet_not_available_count", "getRed_packet_price", "getRed_packet_price_yuan", "getTitle", "getTotal_amount", "getTotal_amount_yuan", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class OrderFeeAddressMultipleBean {

    @Nullable
    private final String coupon_available_count;

    @Nullable
    private final String coupon_id;

    @Nullable
    private final String coupon_not_available_count;

    @Nullable
    private final String coupon_price;

    @Nullable
    private final String coupon_price_yuan;

    @Nullable
    private final String cover;

    @Nullable
    private final String description;

    @Nullable
    private final String order_price;

    @Nullable
    private final String original_price;

    @Nullable
    private final String original_price_yuan;

    @Nullable
    private final String original_total_price;

    @Nullable
    private final String original_total_price_yuan;

    @Nullable
    private final String postage_price;

    @Nullable
    private final String postage_price_yuan;

    @Nullable
    private final String price;

    @Nullable
    private final String privilege_id;

    @Nullable
    private final String privilege_yuan;

    @Nullable
    private final String promotion;

    @Nullable
    private final String promotion_yuan;

    @Nullable
    private final String quantity;

    @Nullable
    private final String red_packet_available_count;

    @Nullable
    private final String red_packet_id;

    @Nullable
    private final String red_packet_not_available_count;

    @Nullable
    private final String red_packet_price;

    @Nullable
    private final String red_packet_price_yuan;

    @Nullable
    private final String title;

    @Nullable
    private final String total_amount;

    @Nullable
    private final String total_amount_yuan;

    public OrderFeeAddressMultipleBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, @Nullable String str22, @Nullable String str23, @Nullable String str24, @Nullable String str25, @Nullable String str26, @Nullable String str27, @Nullable String str28) {
        this.total_amount = str;
        this.original_price = str2;
        this.original_total_price = str3;
        this.promotion = str4;
        this.total_amount_yuan = str5;
        this.privilege_yuan = str6;
        this.original_price_yuan = str7;
        this.original_total_price_yuan = str8;
        this.promotion_yuan = str9;
        this.privilege_id = str10;
        this.quantity = str11;
        this.title = str12;
        this.cover = str13;
        this.price = str14;
        this.postage_price = str15;
        this.postage_price_yuan = str16;
        this.description = str17;
        this.coupon_price = str18;
        this.red_packet_price = str19;
        this.coupon_price_yuan = str20;
        this.red_packet_price_yuan = str21;
        this.coupon_id = str22;
        this.red_packet_id = str23;
        this.order_price = str24;
        this.coupon_available_count = str25;
        this.coupon_not_available_count = str26;
        this.red_packet_available_count = str27;
        this.red_packet_not_available_count = str28;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTotal_amount() {
        return this.total_amount;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getPrivilege_id() {
        return this.privilege_id;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getQuantity() {
        return this.quantity;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPrice() {
        return this.price;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getPostage_price() {
        return this.postage_price;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getPostage_price_yuan() {
        return this.postage_price_yuan;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getCoupon_price() {
        return this.coupon_price;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getRed_packet_price() {
        return this.red_packet_price;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getOriginal_price() {
        return this.original_price;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getCoupon_price_yuan() {
        return this.coupon_price_yuan;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getRed_packet_price_yuan() {
        return this.red_packet_price_yuan;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getCoupon_id() {
        return this.coupon_id;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getRed_packet_id() {
        return this.red_packet_id;
    }

    @Nullable
    /* renamed from: component24, reason: from getter */
    public final String getOrder_price() {
        return this.order_price;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final String getCoupon_available_count() {
        return this.coupon_available_count;
    }

    @Nullable
    /* renamed from: component26, reason: from getter */
    public final String getCoupon_not_available_count() {
        return this.coupon_not_available_count;
    }

    @Nullable
    /* renamed from: component27, reason: from getter */
    public final String getRed_packet_available_count() {
        return this.red_packet_available_count;
    }

    @Nullable
    /* renamed from: component28, reason: from getter */
    public final String getRed_packet_not_available_count() {
        return this.red_packet_not_available_count;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getOriginal_total_price() {
        return this.original_total_price;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getPromotion() {
        return this.promotion;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getTotal_amount_yuan() {
        return this.total_amount_yuan;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getPrivilege_yuan() {
        return this.privilege_yuan;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getOriginal_price_yuan() {
        return this.original_price_yuan;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getOriginal_total_price_yuan() {
        return this.original_total_price_yuan;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getPromotion_yuan() {
        return this.promotion_yuan;
    }

    @NotNull
    public final OrderFeeAddressMultipleBean copy(@Nullable String total_amount, @Nullable String original_price, @Nullable String original_total_price, @Nullable String promotion, @Nullable String total_amount_yuan, @Nullable String privilege_yuan, @Nullable String original_price_yuan, @Nullable String original_total_price_yuan, @Nullable String promotion_yuan, @Nullable String privilege_id, @Nullable String quantity, @Nullable String title, @Nullable String cover, @Nullable String price, @Nullable String postage_price, @Nullable String postage_price_yuan, @Nullable String description, @Nullable String coupon_price, @Nullable String red_packet_price, @Nullable String coupon_price_yuan, @Nullable String red_packet_price_yuan, @Nullable String coupon_id, @Nullable String red_packet_id, @Nullable String order_price, @Nullable String coupon_available_count, @Nullable String coupon_not_available_count, @Nullable String red_packet_available_count, @Nullable String red_packet_not_available_count) {
        return new OrderFeeAddressMultipleBean(total_amount, original_price, original_total_price, promotion, total_amount_yuan, privilege_yuan, original_price_yuan, original_total_price_yuan, promotion_yuan, privilege_id, quantity, title, cover, price, postage_price, postage_price_yuan, description, coupon_price, red_packet_price, coupon_price_yuan, red_packet_price_yuan, coupon_id, red_packet_id, order_price, coupon_available_count, coupon_not_available_count, red_packet_available_count, red_packet_not_available_count);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrderFeeAddressMultipleBean)) {
            return false;
        }
        OrderFeeAddressMultipleBean orderFeeAddressMultipleBean = (OrderFeeAddressMultipleBean) other;
        return Intrinsics.areEqual(this.total_amount, orderFeeAddressMultipleBean.total_amount) && Intrinsics.areEqual(this.original_price, orderFeeAddressMultipleBean.original_price) && Intrinsics.areEqual(this.original_total_price, orderFeeAddressMultipleBean.original_total_price) && Intrinsics.areEqual(this.promotion, orderFeeAddressMultipleBean.promotion) && Intrinsics.areEqual(this.total_amount_yuan, orderFeeAddressMultipleBean.total_amount_yuan) && Intrinsics.areEqual(this.privilege_yuan, orderFeeAddressMultipleBean.privilege_yuan) && Intrinsics.areEqual(this.original_price_yuan, orderFeeAddressMultipleBean.original_price_yuan) && Intrinsics.areEqual(this.original_total_price_yuan, orderFeeAddressMultipleBean.original_total_price_yuan) && Intrinsics.areEqual(this.promotion_yuan, orderFeeAddressMultipleBean.promotion_yuan) && Intrinsics.areEqual(this.privilege_id, orderFeeAddressMultipleBean.privilege_id) && Intrinsics.areEqual(this.quantity, orderFeeAddressMultipleBean.quantity) && Intrinsics.areEqual(this.title, orderFeeAddressMultipleBean.title) && Intrinsics.areEqual(this.cover, orderFeeAddressMultipleBean.cover) && Intrinsics.areEqual(this.price, orderFeeAddressMultipleBean.price) && Intrinsics.areEqual(this.postage_price, orderFeeAddressMultipleBean.postage_price) && Intrinsics.areEqual(this.postage_price_yuan, orderFeeAddressMultipleBean.postage_price_yuan) && Intrinsics.areEqual(this.description, orderFeeAddressMultipleBean.description) && Intrinsics.areEqual(this.coupon_price, orderFeeAddressMultipleBean.coupon_price) && Intrinsics.areEqual(this.red_packet_price, orderFeeAddressMultipleBean.red_packet_price) && Intrinsics.areEqual(this.coupon_price_yuan, orderFeeAddressMultipleBean.coupon_price_yuan) && Intrinsics.areEqual(this.red_packet_price_yuan, orderFeeAddressMultipleBean.red_packet_price_yuan) && Intrinsics.areEqual(this.coupon_id, orderFeeAddressMultipleBean.coupon_id) && Intrinsics.areEqual(this.red_packet_id, orderFeeAddressMultipleBean.red_packet_id) && Intrinsics.areEqual(this.order_price, orderFeeAddressMultipleBean.order_price) && Intrinsics.areEqual(this.coupon_available_count, orderFeeAddressMultipleBean.coupon_available_count) && Intrinsics.areEqual(this.coupon_not_available_count, orderFeeAddressMultipleBean.coupon_not_available_count) && Intrinsics.areEqual(this.red_packet_available_count, orderFeeAddressMultipleBean.red_packet_available_count) && Intrinsics.areEqual(this.red_packet_not_available_count, orderFeeAddressMultipleBean.red_packet_not_available_count);
    }

    @Nullable
    public final String getCoupon_available_count() {
        return this.coupon_available_count;
    }

    @Nullable
    public final String getCoupon_id() {
        return this.coupon_id;
    }

    @Nullable
    public final String getCoupon_not_available_count() {
        return this.coupon_not_available_count;
    }

    @Nullable
    public final String getCoupon_price() {
        return this.coupon_price;
    }

    @Nullable
    public final String getCoupon_price_yuan() {
        return this.coupon_price_yuan;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getOrder_price() {
        return this.order_price;
    }

    @Nullable
    public final String getOriginal_price() {
        return this.original_price;
    }

    @Nullable
    public final String getOriginal_price_yuan() {
        return this.original_price_yuan;
    }

    @Nullable
    public final String getOriginal_total_price() {
        return this.original_total_price;
    }

    @Nullable
    public final String getOriginal_total_price_yuan() {
        return this.original_total_price_yuan;
    }

    @Nullable
    public final String getPostage_price() {
        return this.postage_price;
    }

    @Nullable
    public final String getPostage_price_yuan() {
        return this.postage_price_yuan;
    }

    @Nullable
    public final String getPrice() {
        return this.price;
    }

    @Nullable
    public final String getPrivilege_id() {
        return this.privilege_id;
    }

    @Nullable
    public final String getPrivilege_yuan() {
        return this.privilege_yuan;
    }

    @Nullable
    public final String getPromotion() {
        return this.promotion;
    }

    @Nullable
    public final String getPromotion_yuan() {
        return this.promotion_yuan;
    }

    @Nullable
    public final String getQuantity() {
        return this.quantity;
    }

    @Nullable
    public final String getRed_packet_available_count() {
        return this.red_packet_available_count;
    }

    @Nullable
    public final String getRed_packet_id() {
        return this.red_packet_id;
    }

    @Nullable
    public final String getRed_packet_not_available_count() {
        return this.red_packet_not_available_count;
    }

    @Nullable
    public final String getRed_packet_price() {
        return this.red_packet_price;
    }

    @Nullable
    public final String getRed_packet_price_yuan() {
        return this.red_packet_price_yuan;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getTotal_amount() {
        return this.total_amount;
    }

    @Nullable
    public final String getTotal_amount_yuan() {
        return this.total_amount_yuan;
    }

    public int hashCode() {
        String str = this.total_amount;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.original_price;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.original_total_price;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.promotion;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.total_amount_yuan;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.privilege_yuan;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.original_price_yuan;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.original_total_price_yuan;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.promotion_yuan;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.privilege_id;
        int iHashCode10 = (iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.quantity;
        int iHashCode11 = (iHashCode10 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.title;
        int iHashCode12 = (iHashCode11 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.cover;
        int iHashCode13 = (iHashCode12 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.price;
        int iHashCode14 = (iHashCode13 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.postage_price;
        int iHashCode15 = (iHashCode14 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.postage_price_yuan;
        int iHashCode16 = (iHashCode15 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.description;
        int iHashCode17 = (iHashCode16 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.coupon_price;
        int iHashCode18 = (iHashCode17 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.red_packet_price;
        int iHashCode19 = (iHashCode18 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.coupon_price_yuan;
        int iHashCode20 = (iHashCode19 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.red_packet_price_yuan;
        int iHashCode21 = (iHashCode20 + (str21 == null ? 0 : str21.hashCode())) * 31;
        String str22 = this.coupon_id;
        int iHashCode22 = (iHashCode21 + (str22 == null ? 0 : str22.hashCode())) * 31;
        String str23 = this.red_packet_id;
        int iHashCode23 = (iHashCode22 + (str23 == null ? 0 : str23.hashCode())) * 31;
        String str24 = this.order_price;
        int iHashCode24 = (iHashCode23 + (str24 == null ? 0 : str24.hashCode())) * 31;
        String str25 = this.coupon_available_count;
        int iHashCode25 = (iHashCode24 + (str25 == null ? 0 : str25.hashCode())) * 31;
        String str26 = this.coupon_not_available_count;
        int iHashCode26 = (iHashCode25 + (str26 == null ? 0 : str26.hashCode())) * 31;
        String str27 = this.red_packet_available_count;
        int iHashCode27 = (iHashCode26 + (str27 == null ? 0 : str27.hashCode())) * 31;
        String str28 = this.red_packet_not_available_count;
        return iHashCode27 + (str28 != null ? str28.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "OrderFeeAddressMultipleBean(total_amount=" + this.total_amount + ", original_price=" + this.original_price + ", original_total_price=" + this.original_total_price + ", promotion=" + this.promotion + ", total_amount_yuan=" + this.total_amount_yuan + ", privilege_yuan=" + this.privilege_yuan + ", original_price_yuan=" + this.original_price_yuan + ", original_total_price_yuan=" + this.original_total_price_yuan + ", promotion_yuan=" + this.promotion_yuan + ", privilege_id=" + this.privilege_id + ", quantity=" + this.quantity + ", title=" + this.title + ", cover=" + this.cover + ", price=" + this.price + ", postage_price=" + this.postage_price + ", postage_price_yuan=" + this.postage_price_yuan + ", description=" + this.description + ", coupon_price=" + this.coupon_price + ", red_packet_price=" + this.red_packet_price + ", coupon_price_yuan=" + this.coupon_price_yuan + ", red_packet_price_yuan=" + this.red_packet_price_yuan + ", coupon_id=" + this.coupon_id + ", red_packet_id=" + this.red_packet_id + ", order_price=" + this.order_price + ", coupon_available_count=" + this.coupon_available_count + ", coupon_not_available_count=" + this.coupon_not_available_count + ", red_packet_available_count=" + this.red_packet_available_count + ", red_packet_not_available_count=" + this.red_packet_not_available_count + ')';
    }
}
