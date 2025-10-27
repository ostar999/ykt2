package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ,2\u00020\u0001:\u0002+,B[\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eBI\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fJ\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\u0011\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\tHÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003JM\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\t\u0010#\u001a\u00020\u0005HÖ\u0001J!\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011¨\u0006-"}, d2 = {"Lcom/yddmi/doctor/entity/result/BatchInfo;", "", "seen1", "", "costPrice", "", "marketPrice", "preferentialPrice", "productIds", "", "validEndDate", "validStartDate", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getCostPrice", "()Ljava/lang/String;", "getMarketPrice", "getPreferentialPrice", "getProductIds", "()Ljava/util/List;", "getValidEndDate", "getValidStartDate", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class BatchInfo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String costPrice;

    @NotNull
    private final String marketPrice;

    @NotNull
    private final String preferentialPrice;

    @Nullable
    private final List<Integer> productIds;

    @NotNull
    private final String validEndDate;

    @NotNull
    private final String validStartDate;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/BatchInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/BatchInfo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<BatchInfo> serializer() {
            return BatchInfo$$serializer.INSTANCE;
        }
    }

    public BatchInfo() {
        this((String) null, (String) null, (String) null, (List) null, (String) null, (String) null, 63, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ BatchInfo(int i2, String str, String str2, String str3, List list, String str4, String str5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, BatchInfo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.costPrice = "";
        } else {
            this.costPrice = str;
        }
        if ((i2 & 2) == 0) {
            this.marketPrice = "";
        } else {
            this.marketPrice = str2;
        }
        if ((i2 & 4) == 0) {
            this.preferentialPrice = "";
        } else {
            this.preferentialPrice = str3;
        }
        if ((i2 & 8) == 0) {
            this.productIds = null;
        } else {
            this.productIds = list;
        }
        if ((i2 & 16) == 0) {
            this.validEndDate = "";
        } else {
            this.validEndDate = str4;
        }
        if ((i2 & 32) == 0) {
            this.validStartDate = "";
        } else {
            this.validStartDate = str5;
        }
    }

    public static /* synthetic */ BatchInfo copy$default(BatchInfo batchInfo, String str, String str2, String str3, List list, String str4, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = batchInfo.costPrice;
        }
        if ((i2 & 2) != 0) {
            str2 = batchInfo.marketPrice;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = batchInfo.preferentialPrice;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            list = batchInfo.productIds;
        }
        List list2 = list;
        if ((i2 & 16) != 0) {
            str4 = batchInfo.validEndDate;
        }
        String str8 = str4;
        if ((i2 & 32) != 0) {
            str5 = batchInfo.validStartDate;
        }
        return batchInfo.copy(str, str6, str7, list2, str8, str5);
    }

    @JvmStatic
    public static final void write$Self(@NotNull BatchInfo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.costPrice, "")) {
            output.encodeStringElement(serialDesc, 0, self.costPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.marketPrice, "")) {
            output.encodeStringElement(serialDesc, 1, self.marketPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.preferentialPrice, "")) {
            output.encodeStringElement(serialDesc, 2, self.preferentialPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.productIds != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(IntSerializer.INSTANCE), self.productIds);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.validEndDate, "")) {
            output.encodeStringElement(serialDesc, 4, self.validEndDate);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.validStartDate, "")) {
            output.encodeStringElement(serialDesc, 5, self.validStartDate);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getCostPrice() {
        return this.costPrice;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getMarketPrice() {
        return this.marketPrice;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getPreferentialPrice() {
        return this.preferentialPrice;
    }

    @Nullable
    public final List<Integer> component4() {
        return this.productIds;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getValidEndDate() {
        return this.validEndDate;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getValidStartDate() {
        return this.validStartDate;
    }

    @NotNull
    public final BatchInfo copy(@NotNull String costPrice, @NotNull String marketPrice, @NotNull String preferentialPrice, @Nullable List<Integer> productIds, @NotNull String validEndDate, @NotNull String validStartDate) {
        Intrinsics.checkNotNullParameter(costPrice, "costPrice");
        Intrinsics.checkNotNullParameter(marketPrice, "marketPrice");
        Intrinsics.checkNotNullParameter(preferentialPrice, "preferentialPrice");
        Intrinsics.checkNotNullParameter(validEndDate, "validEndDate");
        Intrinsics.checkNotNullParameter(validStartDate, "validStartDate");
        return new BatchInfo(costPrice, marketPrice, preferentialPrice, productIds, validEndDate, validStartDate);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BatchInfo)) {
            return false;
        }
        BatchInfo batchInfo = (BatchInfo) other;
        return Intrinsics.areEqual(this.costPrice, batchInfo.costPrice) && Intrinsics.areEqual(this.marketPrice, batchInfo.marketPrice) && Intrinsics.areEqual(this.preferentialPrice, batchInfo.preferentialPrice) && Intrinsics.areEqual(this.productIds, batchInfo.productIds) && Intrinsics.areEqual(this.validEndDate, batchInfo.validEndDate) && Intrinsics.areEqual(this.validStartDate, batchInfo.validStartDate);
    }

    @NotNull
    public final String getCostPrice() {
        return this.costPrice;
    }

    @NotNull
    public final String getMarketPrice() {
        return this.marketPrice;
    }

    @NotNull
    public final String getPreferentialPrice() {
        return this.preferentialPrice;
    }

    @Nullable
    public final List<Integer> getProductIds() {
        return this.productIds;
    }

    @NotNull
    public final String getValidEndDate() {
        return this.validEndDate;
    }

    @NotNull
    public final String getValidStartDate() {
        return this.validStartDate;
    }

    public int hashCode() {
        int iHashCode = ((((this.costPrice.hashCode() * 31) + this.marketPrice.hashCode()) * 31) + this.preferentialPrice.hashCode()) * 31;
        List<Integer> list = this.productIds;
        return ((((iHashCode + (list == null ? 0 : list.hashCode())) * 31) + this.validEndDate.hashCode()) * 31) + this.validStartDate.hashCode();
    }

    @NotNull
    public String toString() {
        return "BatchInfo(costPrice=" + this.costPrice + ", marketPrice=" + this.marketPrice + ", preferentialPrice=" + this.preferentialPrice + ", productIds=" + this.productIds + ", validEndDate=" + this.validEndDate + ", validStartDate=" + this.validStartDate + ")";
    }

    public BatchInfo(@NotNull String costPrice, @NotNull String marketPrice, @NotNull String preferentialPrice, @Nullable List<Integer> list, @NotNull String validEndDate, @NotNull String validStartDate) {
        Intrinsics.checkNotNullParameter(costPrice, "costPrice");
        Intrinsics.checkNotNullParameter(marketPrice, "marketPrice");
        Intrinsics.checkNotNullParameter(preferentialPrice, "preferentialPrice");
        Intrinsics.checkNotNullParameter(validEndDate, "validEndDate");
        Intrinsics.checkNotNullParameter(validStartDate, "validStartDate");
        this.costPrice = costPrice;
        this.marketPrice = marketPrice;
        this.preferentialPrice = preferentialPrice;
        this.productIds = list;
        this.validEndDate = validEndDate;
        this.validStartDate = validStartDate;
    }

    public /* synthetic */ BatchInfo(String str, String str2, String str3, List list, String str4, String str5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? null : list, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? "" : str5);
    }
}
