package com.psychiatrygarden.utils;

import com.psychiatrygarden.bean.CouponItems;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fJ\u0010\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/utils/CouponUtils;", "", "()V", "TYPE_COUPON_ALL", "", "TYPE_ONLY_COUPON", "TYPE_ONLY_RED_PACKET", "checkCouponItemsShowType", "items", "", "Lcom/psychiatrygarden/bean/CouponItems;", "convertDiscount", "", "discountStr", "formatPrice", "priceStr", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCouponUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CouponUtils.kt\ncom/psychiatrygarden/utils/CouponUtils\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,77:1\n1726#2,3:78\n*S KotlinDebug\n*F\n+ 1 CouponUtils.kt\ncom/psychiatrygarden/utils/CouponUtils\n*L\n70#1:78,3\n*E\n"})
/* loaded from: classes6.dex */
public final class CouponUtils {

    @NotNull
    public static final CouponUtils INSTANCE = new CouponUtils();
    public static final int TYPE_COUPON_ALL = 1;
    public static final int TYPE_ONLY_COUPON = 3;
    public static final int TYPE_ONLY_RED_PACKET = 2;

    private CouponUtils() {
    }

    public final int checkCouponItemsShowType(@Nullable List<? extends CouponItems> items) {
        List<? extends CouponItems> list = items;
        boolean z2 = false;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        List<? extends CouponItems> list2 = items;
        if (SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.take(SequencesKt___SequencesKt.distinct(SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(list2), new Function1<CouponItems, String>() { // from class: com.psychiatrygarden.utils.CouponUtils$checkCouponItemsShowType$result$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(@NotNull CouponItems it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getCoupon_type();
            }
        })), 2)) >= 2) {
            return 1;
        }
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            z2 = true;
        } else {
            for (CouponItems couponItems : list2) {
                if (!(Intrinsics.areEqual(couponItems.getCoupon_type(), "0") || Intrinsics.areEqual(couponItems.getCoupon_type(), "1"))) {
                    break;
                }
            }
            z2 = true;
        }
        return z2 ? 3 : 2;
    }

    @Nullable
    public final String convertDiscount(@NotNull String discountStr) throws NumberFormatException {
        String strValueOf;
        Intrinsics.checkNotNullParameter(discountStr, "discountStr");
        try {
            double d3 = Double.parseDouble(discountStr);
            if (d3 >= 0.0d && d3 <= 100.0d) {
                double d4 = d3 / 10;
                if (d4 % ((double) 1) == 0.0d) {
                    strValueOf = String.valueOf((int) d4);
                } else {
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    strValueOf = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(d4)}, 1));
                    Intrinsics.checkNotNullExpressionValue(strValueOf, "format(format, *args)");
                }
                return strValueOf;
            }
            return "";
        } catch (NumberFormatException unused) {
            return "";
        }
    }

    @NotNull
    public final String formatPrice(@Nullable String priceStr) {
        if (!(priceStr == null || priceStr.length() == 0) && !StringsKt__StringsJVMKt.isBlank(priceStr)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(StringsKt__StringsKt.trim((CharSequence) priceStr).toString());
                if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                    return "0";
                }
                String plainString = bigDecimal.stripTrailingZeros().toPlainString();
                Intrinsics.checkNotNullExpressionValue(plainString, "number.stripTrailingZeros().toPlainString()");
                return plainString;
            } catch (NumberFormatException unused) {
            }
        }
        return "0";
    }
}
