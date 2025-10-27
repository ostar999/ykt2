package com.psychiatrygarden.bean;

import com.psychiatrygarden.activity.purchase.activity.ActSubmitGoodsComment;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bU\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bé\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001f\u0012\b\u0010 \u001a\u0004\u0018\u00010!\u0012\b\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010$\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010%\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010&\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010'\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010(\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010)\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010*J\u000b\u0010R\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010S\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010W\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010X\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010[\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010^\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010_\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010c\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cHÆ\u0003J\t\u0010d\u001a\u00020\u001fHÆ\u0003J\u000b\u0010e\u001a\u0004\u0018\u00010!HÆ\u0003J\u000b\u0010f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010h\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010j\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010m\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010o\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010p\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010q\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010s\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010t\u001a\u0004\u0018\u00010\u0003HÆ\u0003J±\u0003\u0010u\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010v\u001a\u00020w2\b\u0010x\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010y\u001a\u00020zHÖ\u0001J\t\u0010{\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0013\u0010)\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010.R\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010.R\u0013\u0010$\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010.R\u0013\u0010 \u001a\u0004\u0018\u00010!¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0013\u0010&\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u0010.R\u0013\u0010\"\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b7\u0010.R\u0013\u0010%\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b8\u0010.R\u0013\u0010#\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b9\u0010.R\u0013\u0010'\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b:\u0010.R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b;\u0010.R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b<\u0010.R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b=\u0010.R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b>\u0010.R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b?\u0010.R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b@\u0010.R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bA\u0010.R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bB\u0010.R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bC\u0010.R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bD\u0010.R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bE\u0010.R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bF\u0010.R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bG\u0010.R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bH\u0010.R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bI\u0010.R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010.R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bK\u0010.R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bL\u0010.R\u0013\u0010(\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bM\u0010.R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bN\u0010.R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bO\u0010.R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bP\u0010.R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010.¨\u0006|"}, d2 = {"Lcom/psychiatrygarden/bean/OrderDetailBean;", "", ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, "", "original_ctime", "pending_payment_ctime", "ctime", "status", "pay_type", "total_amount", "promotion", "privilege", "pay_notify_time", "leave_message", "total_amount_yuan", "original_price_yuan", "original_total_price_yuan", "promotion_yuan", "privilege_yuan", "total_discount_yuan", "quantity", "goods_type", "goods_id", "goods_name", "goods_thumbnail", "goods_description", "commented", "button", "", "Lcom/psychiatrygarden/bean/ButtonBean;", "cs", "Lcom/psychiatrygarden/bean/OnlineServiceBean;", "express_detail", "Lcom/psychiatrygarden/activity/purchase/beans/ShowAddressBean$DataBean;", "express_id", "express_no", "express_code", "express_name", "express_fee_yuan", "express_query_url", "red_packet_price_yuan", "coupon_price_yuan", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/psychiatrygarden/bean/OnlineServiceBean;Lcom/psychiatrygarden/activity/purchase/beans/ShowAddressBean$DataBean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getButton", "()Ljava/util/List;", "getCommented", "()Ljava/lang/String;", "getCoupon_price_yuan", "getCs", "()Lcom/psychiatrygarden/bean/OnlineServiceBean;", "getCtime", "getExpress_code", "getExpress_detail", "()Lcom/psychiatrygarden/activity/purchase/beans/ShowAddressBean$DataBean;", "getExpress_fee_yuan", "getExpress_id", "getExpress_name", "getExpress_no", "getExpress_query_url", "getGoods_description", "getGoods_id", "getGoods_name", "getGoods_thumbnail", "getGoods_type", "getLeave_message", "getOrder_no", "getOriginal_ctime", "getOriginal_price_yuan", "getOriginal_total_price_yuan", "getPay_notify_time", "getPay_type", "getPending_payment_ctime", "getPrivilege", "getPrivilege_yuan", "getPromotion", "getPromotion_yuan", "getQuantity", "getRed_packet_price_yuan", "getStatus", "getTotal_amount", "getTotal_amount_yuan", "getTotal_discount_yuan", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class OrderDetailBean {

    @Nullable
    private final List<ButtonBean> button;

    @Nullable
    private final String commented;

    @Nullable
    private final String coupon_price_yuan;

    @NotNull
    private final OnlineServiceBean cs;

    @Nullable
    private final String ctime;

    @Nullable
    private final String express_code;

    @Nullable
    private final ShowAddressBean.DataBean express_detail;

    @Nullable
    private final String express_fee_yuan;

    @Nullable
    private final String express_id;

    @Nullable
    private final String express_name;

    @Nullable
    private final String express_no;

    @Nullable
    private final String express_query_url;

    @Nullable
    private final String goods_description;

    @Nullable
    private final String goods_id;

    @Nullable
    private final String goods_name;

    @Nullable
    private final String goods_thumbnail;

    @Nullable
    private final String goods_type;

    @Nullable
    private final String leave_message;

    @Nullable
    private final String order_no;

    @Nullable
    private final String original_ctime;

    @Nullable
    private final String original_price_yuan;

    @Nullable
    private final String original_total_price_yuan;

    @Nullable
    private final String pay_notify_time;

    @Nullable
    private final String pay_type;

    @Nullable
    private final String pending_payment_ctime;

    @Nullable
    private final String privilege;

    @Nullable
    private final String privilege_yuan;

    @Nullable
    private final String promotion;

    @Nullable
    private final String promotion_yuan;

    @Nullable
    private final String quantity;

    @Nullable
    private final String red_packet_price_yuan;

    @Nullable
    private final String status;

    @Nullable
    private final String total_amount;

    @Nullable
    private final String total_amount_yuan;

    @Nullable
    private final String total_discount_yuan;

    public OrderDetailBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, @Nullable String str22, @Nullable String str23, @Nullable String str24, @Nullable List<ButtonBean> list, @NotNull OnlineServiceBean cs, @Nullable ShowAddressBean.DataBean dataBean, @Nullable String str25, @Nullable String str26, @Nullable String str27, @Nullable String str28, @Nullable String str29, @Nullable String str30, @Nullable String str31, @Nullable String str32) {
        Intrinsics.checkNotNullParameter(cs, "cs");
        this.order_no = str;
        this.original_ctime = str2;
        this.pending_payment_ctime = str3;
        this.ctime = str4;
        this.status = str5;
        this.pay_type = str6;
        this.total_amount = str7;
        this.promotion = str8;
        this.privilege = str9;
        this.pay_notify_time = str10;
        this.leave_message = str11;
        this.total_amount_yuan = str12;
        this.original_price_yuan = str13;
        this.original_total_price_yuan = str14;
        this.promotion_yuan = str15;
        this.privilege_yuan = str16;
        this.total_discount_yuan = str17;
        this.quantity = str18;
        this.goods_type = str19;
        this.goods_id = str20;
        this.goods_name = str21;
        this.goods_thumbnail = str22;
        this.goods_description = str23;
        this.commented = str24;
        this.button = list;
        this.cs = cs;
        this.express_detail = dataBean;
        this.express_id = str25;
        this.express_no = str26;
        this.express_code = str27;
        this.express_name = str28;
        this.express_fee_yuan = str29;
        this.express_query_url = str30;
        this.red_packet_price_yuan = str31;
        this.coupon_price_yuan = str32;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getOrder_no() {
        return this.order_no;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getPay_notify_time() {
        return this.pay_notify_time;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getLeave_message() {
        return this.leave_message;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getTotal_amount_yuan() {
        return this.total_amount_yuan;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getOriginal_price_yuan() {
        return this.original_price_yuan;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getOriginal_total_price_yuan() {
        return this.original_total_price_yuan;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getPromotion_yuan() {
        return this.promotion_yuan;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getPrivilege_yuan() {
        return this.privilege_yuan;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getTotal_discount_yuan() {
        return this.total_discount_yuan;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getQuantity() {
        return this.quantity;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getGoods_type() {
        return this.goods_type;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getOriginal_ctime() {
        return this.original_ctime;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getGoods_id() {
        return this.goods_id;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getGoods_name() {
        return this.goods_name;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getGoods_thumbnail() {
        return this.goods_thumbnail;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getGoods_description() {
        return this.goods_description;
    }

    @Nullable
    /* renamed from: component24, reason: from getter */
    public final String getCommented() {
        return this.commented;
    }

    @Nullable
    public final List<ButtonBean> component25() {
        return this.button;
    }

    @NotNull
    /* renamed from: component26, reason: from getter */
    public final OnlineServiceBean getCs() {
        return this.cs;
    }

    @Nullable
    /* renamed from: component27, reason: from getter */
    public final ShowAddressBean.DataBean getExpress_detail() {
        return this.express_detail;
    }

    @Nullable
    /* renamed from: component28, reason: from getter */
    public final String getExpress_id() {
        return this.express_id;
    }

    @Nullable
    /* renamed from: component29, reason: from getter */
    public final String getExpress_no() {
        return this.express_no;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getPending_payment_ctime() {
        return this.pending_payment_ctime;
    }

    @Nullable
    /* renamed from: component30, reason: from getter */
    public final String getExpress_code() {
        return this.express_code;
    }

    @Nullable
    /* renamed from: component31, reason: from getter */
    public final String getExpress_name() {
        return this.express_name;
    }

    @Nullable
    /* renamed from: component32, reason: from getter */
    public final String getExpress_fee_yuan() {
        return this.express_fee_yuan;
    }

    @Nullable
    /* renamed from: component33, reason: from getter */
    public final String getExpress_query_url() {
        return this.express_query_url;
    }

    @Nullable
    /* renamed from: component34, reason: from getter */
    public final String getRed_packet_price_yuan() {
        return this.red_packet_price_yuan;
    }

    @Nullable
    /* renamed from: component35, reason: from getter */
    public final String getCoupon_price_yuan() {
        return this.coupon_price_yuan;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getPay_type() {
        return this.pay_type;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getTotal_amount() {
        return this.total_amount;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getPromotion() {
        return this.promotion;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getPrivilege() {
        return this.privilege;
    }

    @NotNull
    public final OrderDetailBean copy(@Nullable String order_no, @Nullable String original_ctime, @Nullable String pending_payment_ctime, @Nullable String ctime, @Nullable String status, @Nullable String pay_type, @Nullable String total_amount, @Nullable String promotion, @Nullable String privilege, @Nullable String pay_notify_time, @Nullable String leave_message, @Nullable String total_amount_yuan, @Nullable String original_price_yuan, @Nullable String original_total_price_yuan, @Nullable String promotion_yuan, @Nullable String privilege_yuan, @Nullable String total_discount_yuan, @Nullable String quantity, @Nullable String goods_type, @Nullable String goods_id, @Nullable String goods_name, @Nullable String goods_thumbnail, @Nullable String goods_description, @Nullable String commented, @Nullable List<ButtonBean> button, @NotNull OnlineServiceBean cs, @Nullable ShowAddressBean.DataBean express_detail, @Nullable String express_id, @Nullable String express_no, @Nullable String express_code, @Nullable String express_name, @Nullable String express_fee_yuan, @Nullable String express_query_url, @Nullable String red_packet_price_yuan, @Nullable String coupon_price_yuan) {
        Intrinsics.checkNotNullParameter(cs, "cs");
        return new OrderDetailBean(order_no, original_ctime, pending_payment_ctime, ctime, status, pay_type, total_amount, promotion, privilege, pay_notify_time, leave_message, total_amount_yuan, original_price_yuan, original_total_price_yuan, promotion_yuan, privilege_yuan, total_discount_yuan, quantity, goods_type, goods_id, goods_name, goods_thumbnail, goods_description, commented, button, cs, express_detail, express_id, express_no, express_code, express_name, express_fee_yuan, express_query_url, red_packet_price_yuan, coupon_price_yuan);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrderDetailBean)) {
            return false;
        }
        OrderDetailBean orderDetailBean = (OrderDetailBean) other;
        return Intrinsics.areEqual(this.order_no, orderDetailBean.order_no) && Intrinsics.areEqual(this.original_ctime, orderDetailBean.original_ctime) && Intrinsics.areEqual(this.pending_payment_ctime, orderDetailBean.pending_payment_ctime) && Intrinsics.areEqual(this.ctime, orderDetailBean.ctime) && Intrinsics.areEqual(this.status, orderDetailBean.status) && Intrinsics.areEqual(this.pay_type, orderDetailBean.pay_type) && Intrinsics.areEqual(this.total_amount, orderDetailBean.total_amount) && Intrinsics.areEqual(this.promotion, orderDetailBean.promotion) && Intrinsics.areEqual(this.privilege, orderDetailBean.privilege) && Intrinsics.areEqual(this.pay_notify_time, orderDetailBean.pay_notify_time) && Intrinsics.areEqual(this.leave_message, orderDetailBean.leave_message) && Intrinsics.areEqual(this.total_amount_yuan, orderDetailBean.total_amount_yuan) && Intrinsics.areEqual(this.original_price_yuan, orderDetailBean.original_price_yuan) && Intrinsics.areEqual(this.original_total_price_yuan, orderDetailBean.original_total_price_yuan) && Intrinsics.areEqual(this.promotion_yuan, orderDetailBean.promotion_yuan) && Intrinsics.areEqual(this.privilege_yuan, orderDetailBean.privilege_yuan) && Intrinsics.areEqual(this.total_discount_yuan, orderDetailBean.total_discount_yuan) && Intrinsics.areEqual(this.quantity, orderDetailBean.quantity) && Intrinsics.areEqual(this.goods_type, orderDetailBean.goods_type) && Intrinsics.areEqual(this.goods_id, orderDetailBean.goods_id) && Intrinsics.areEqual(this.goods_name, orderDetailBean.goods_name) && Intrinsics.areEqual(this.goods_thumbnail, orderDetailBean.goods_thumbnail) && Intrinsics.areEqual(this.goods_description, orderDetailBean.goods_description) && Intrinsics.areEqual(this.commented, orderDetailBean.commented) && Intrinsics.areEqual(this.button, orderDetailBean.button) && Intrinsics.areEqual(this.cs, orderDetailBean.cs) && Intrinsics.areEqual(this.express_detail, orderDetailBean.express_detail) && Intrinsics.areEqual(this.express_id, orderDetailBean.express_id) && Intrinsics.areEqual(this.express_no, orderDetailBean.express_no) && Intrinsics.areEqual(this.express_code, orderDetailBean.express_code) && Intrinsics.areEqual(this.express_name, orderDetailBean.express_name) && Intrinsics.areEqual(this.express_fee_yuan, orderDetailBean.express_fee_yuan) && Intrinsics.areEqual(this.express_query_url, orderDetailBean.express_query_url) && Intrinsics.areEqual(this.red_packet_price_yuan, orderDetailBean.red_packet_price_yuan) && Intrinsics.areEqual(this.coupon_price_yuan, orderDetailBean.coupon_price_yuan);
    }

    @Nullable
    public final List<ButtonBean> getButton() {
        return this.button;
    }

    @Nullable
    public final String getCommented() {
        return this.commented;
    }

    @Nullable
    public final String getCoupon_price_yuan() {
        return this.coupon_price_yuan;
    }

    @NotNull
    public final OnlineServiceBean getCs() {
        return this.cs;
    }

    @Nullable
    public final String getCtime() {
        return this.ctime;
    }

    @Nullable
    public final String getExpress_code() {
        return this.express_code;
    }

    @Nullable
    public final ShowAddressBean.DataBean getExpress_detail() {
        return this.express_detail;
    }

    @Nullable
    public final String getExpress_fee_yuan() {
        return this.express_fee_yuan;
    }

    @Nullable
    public final String getExpress_id() {
        return this.express_id;
    }

    @Nullable
    public final String getExpress_name() {
        return this.express_name;
    }

    @Nullable
    public final String getExpress_no() {
        return this.express_no;
    }

    @Nullable
    public final String getExpress_query_url() {
        return this.express_query_url;
    }

    @Nullable
    public final String getGoods_description() {
        return this.goods_description;
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
    public final String getGoods_thumbnail() {
        return this.goods_thumbnail;
    }

    @Nullable
    public final String getGoods_type() {
        return this.goods_type;
    }

    @Nullable
    public final String getLeave_message() {
        return this.leave_message;
    }

    @Nullable
    public final String getOrder_no() {
        return this.order_no;
    }

    @Nullable
    public final String getOriginal_ctime() {
        return this.original_ctime;
    }

    @Nullable
    public final String getOriginal_price_yuan() {
        return this.original_price_yuan;
    }

    @Nullable
    public final String getOriginal_total_price_yuan() {
        return this.original_total_price_yuan;
    }

    @Nullable
    public final String getPay_notify_time() {
        return this.pay_notify_time;
    }

    @Nullable
    public final String getPay_type() {
        return this.pay_type;
    }

    @Nullable
    public final String getPending_payment_ctime() {
        return this.pending_payment_ctime;
    }

    @Nullable
    public final String getPrivilege() {
        return this.privilege;
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
    public final String getRed_packet_price_yuan() {
        return this.red_packet_price_yuan;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    public final String getTotal_amount() {
        return this.total_amount;
    }

    @Nullable
    public final String getTotal_amount_yuan() {
        return this.total_amount_yuan;
    }

    @Nullable
    public final String getTotal_discount_yuan() {
        return this.total_discount_yuan;
    }

    public int hashCode() {
        String str = this.order_no;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.original_ctime;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.pending_payment_ctime;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.ctime;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.status;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.pay_type;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.total_amount;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.promotion;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.privilege;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.pay_notify_time;
        int iHashCode10 = (iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.leave_message;
        int iHashCode11 = (iHashCode10 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.total_amount_yuan;
        int iHashCode12 = (iHashCode11 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.original_price_yuan;
        int iHashCode13 = (iHashCode12 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.original_total_price_yuan;
        int iHashCode14 = (iHashCode13 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.promotion_yuan;
        int iHashCode15 = (iHashCode14 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.privilege_yuan;
        int iHashCode16 = (iHashCode15 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.total_discount_yuan;
        int iHashCode17 = (iHashCode16 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.quantity;
        int iHashCode18 = (iHashCode17 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.goods_type;
        int iHashCode19 = (iHashCode18 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.goods_id;
        int iHashCode20 = (iHashCode19 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.goods_name;
        int iHashCode21 = (iHashCode20 + (str21 == null ? 0 : str21.hashCode())) * 31;
        String str22 = this.goods_thumbnail;
        int iHashCode22 = (iHashCode21 + (str22 == null ? 0 : str22.hashCode())) * 31;
        String str23 = this.goods_description;
        int iHashCode23 = (iHashCode22 + (str23 == null ? 0 : str23.hashCode())) * 31;
        String str24 = this.commented;
        int iHashCode24 = (iHashCode23 + (str24 == null ? 0 : str24.hashCode())) * 31;
        List<ButtonBean> list = this.button;
        int iHashCode25 = (((iHashCode24 + (list == null ? 0 : list.hashCode())) * 31) + this.cs.hashCode()) * 31;
        ShowAddressBean.DataBean dataBean = this.express_detail;
        int iHashCode26 = (iHashCode25 + (dataBean == null ? 0 : dataBean.hashCode())) * 31;
        String str25 = this.express_id;
        int iHashCode27 = (iHashCode26 + (str25 == null ? 0 : str25.hashCode())) * 31;
        String str26 = this.express_no;
        int iHashCode28 = (iHashCode27 + (str26 == null ? 0 : str26.hashCode())) * 31;
        String str27 = this.express_code;
        int iHashCode29 = (iHashCode28 + (str27 == null ? 0 : str27.hashCode())) * 31;
        String str28 = this.express_name;
        int iHashCode30 = (iHashCode29 + (str28 == null ? 0 : str28.hashCode())) * 31;
        String str29 = this.express_fee_yuan;
        int iHashCode31 = (iHashCode30 + (str29 == null ? 0 : str29.hashCode())) * 31;
        String str30 = this.express_query_url;
        int iHashCode32 = (iHashCode31 + (str30 == null ? 0 : str30.hashCode())) * 31;
        String str31 = this.red_packet_price_yuan;
        int iHashCode33 = (iHashCode32 + (str31 == null ? 0 : str31.hashCode())) * 31;
        String str32 = this.coupon_price_yuan;
        return iHashCode33 + (str32 != null ? str32.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "OrderDetailBean(order_no=" + this.order_no + ", original_ctime=" + this.original_ctime + ", pending_payment_ctime=" + this.pending_payment_ctime + ", ctime=" + this.ctime + ", status=" + this.status + ", pay_type=" + this.pay_type + ", total_amount=" + this.total_amount + ", promotion=" + this.promotion + ", privilege=" + this.privilege + ", pay_notify_time=" + this.pay_notify_time + ", leave_message=" + this.leave_message + ", total_amount_yuan=" + this.total_amount_yuan + ", original_price_yuan=" + this.original_price_yuan + ", original_total_price_yuan=" + this.original_total_price_yuan + ", promotion_yuan=" + this.promotion_yuan + ", privilege_yuan=" + this.privilege_yuan + ", total_discount_yuan=" + this.total_discount_yuan + ", quantity=" + this.quantity + ", goods_type=" + this.goods_type + ", goods_id=" + this.goods_id + ", goods_name=" + this.goods_name + ", goods_thumbnail=" + this.goods_thumbnail + ", goods_description=" + this.goods_description + ", commented=" + this.commented + ", button=" + this.button + ", cs=" + this.cs + ", express_detail=" + this.express_detail + ", express_id=" + this.express_id + ", express_no=" + this.express_no + ", express_code=" + this.express_code + ", express_name=" + this.express_name + ", express_fee_yuan=" + this.express_fee_yuan + ", express_query_url=" + this.express_query_url + ", red_packet_price_yuan=" + this.red_packet_price_yuan + ", coupon_price_yuan=" + this.coupon_price_yuan + ')';
    }

    public /* synthetic */ OrderDetailBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, String str22, String str23, String str24, List list, OnlineServiceBean onlineServiceBean, ShowAddressBean.DataBean dataBean, String str25, String str26, String str27, String str28, String str29, String str30, String str31, String str32, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24, list, (i2 & 33554432) != 0 ? new OnlineServiceBean() : onlineServiceBean, dataBean, str25, str26, str27, str28, str29, str30, str31, str32);
    }
}
