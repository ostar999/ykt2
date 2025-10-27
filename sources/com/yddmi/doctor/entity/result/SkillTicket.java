package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 :2\u00020\u0001:\u00029:B\u0081\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012B{\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0013J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010&\u001a\u00020\u0006HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\u0085\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0006HÖ\u0001J!\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillTicket;", "", "seen1", "", "canUse", "claimTime", "", "couponAmount", "couponId", "couponRecord", "couponRecordId", "description", "enableTime", "type", "typeName", "usableRange", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;I)V", "getCanUse", "()I", "getClaimTime", "()Ljava/lang/String;", "getCouponAmount", "getCouponId", "getCouponRecord", "getCouponRecordId", "getDescription", "getEnableTime", "getType", "getTypeName", "getUsableRange", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillTicket {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int canUse;

    @Nullable
    private final String claimTime;

    @Nullable
    private final String couponAmount;

    @NotNull
    private final String couponId;

    @Nullable
    private final String couponRecord;

    @Nullable
    private final String couponRecordId;

    @Nullable
    private final String description;

    @Nullable
    private final String enableTime;
    private final int type;

    @Nullable
    private final String typeName;
    private final int usableRange;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillTicket$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillTicket> serializer() {
            return SkillTicket$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillTicket(int i2, int i3, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i4, String str8, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if (224 != (i2 & 224)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 224, SkillTicket$$serializer.INSTANCE.getDescriptor());
        }
        this.canUse = (i2 & 1) == 0 ? 0 : i3;
        if ((i2 & 2) == 0) {
            this.claimTime = "";
        } else {
            this.claimTime = str;
        }
        if ((i2 & 4) == 0) {
            this.couponAmount = "";
        } else {
            this.couponAmount = str2;
        }
        if ((i2 & 8) == 0) {
            this.couponId = "";
        } else {
            this.couponId = str3;
        }
        if ((i2 & 16) == 0) {
            this.couponRecord = "";
        } else {
            this.couponRecord = str4;
        }
        this.couponRecordId = str5;
        this.description = str6;
        this.enableTime = str7;
        if ((i2 & 256) == 0) {
            this.type = 1;
        } else {
            this.type = i4;
        }
        if ((i2 & 512) == 0) {
            this.typeName = "";
        } else {
            this.typeName = str8;
        }
        if ((i2 & 1024) == 0) {
            this.usableRange = 1;
        } else {
            this.usableRange = i5;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillTicket self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.canUse != 0) {
            output.encodeIntElement(serialDesc, 0, self.canUse);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.claimTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.claimTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.couponAmount, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.couponAmount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.couponId, "")) {
            output.encodeStringElement(serialDesc, 3, self.couponId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.couponRecord, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.couponRecord);
        }
        StringSerializer stringSerializer = StringSerializer.INSTANCE;
        output.encodeNullableSerializableElement(serialDesc, 5, stringSerializer, self.couponRecordId);
        output.encodeNullableSerializableElement(serialDesc, 6, stringSerializer, self.description);
        output.encodeNullableSerializableElement(serialDesc, 7, stringSerializer, self.enableTime);
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.type != 1) {
            output.encodeIntElement(serialDesc, 8, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.typeName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, stringSerializer, self.typeName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.usableRange != 1) {
            output.encodeIntElement(serialDesc, 10, self.usableRange);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getCanUse() {
        return this.canUse;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getTypeName() {
        return this.typeName;
    }

    /* renamed from: component11, reason: from getter */
    public final int getUsableRange() {
        return this.usableRange;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getClaimTime() {
        return this.claimTime;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCouponAmount() {
        return this.couponAmount;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getCouponId() {
        return this.couponId;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCouponRecord() {
        return this.couponRecord;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getCouponRecordId() {
        return this.couponRecordId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getEnableTime() {
        return this.enableTime;
    }

    /* renamed from: component9, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    public final SkillTicket copy(int canUse, @Nullable String claimTime, @Nullable String couponAmount, @NotNull String couponId, @Nullable String couponRecord, @Nullable String couponRecordId, @Nullable String description, @Nullable String enableTime, int type, @Nullable String typeName, int usableRange) {
        Intrinsics.checkNotNullParameter(couponId, "couponId");
        return new SkillTicket(canUse, claimTime, couponAmount, couponId, couponRecord, couponRecordId, description, enableTime, type, typeName, usableRange);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillTicket)) {
            return false;
        }
        SkillTicket skillTicket = (SkillTicket) other;
        return this.canUse == skillTicket.canUse && Intrinsics.areEqual(this.claimTime, skillTicket.claimTime) && Intrinsics.areEqual(this.couponAmount, skillTicket.couponAmount) && Intrinsics.areEqual(this.couponId, skillTicket.couponId) && Intrinsics.areEqual(this.couponRecord, skillTicket.couponRecord) && Intrinsics.areEqual(this.couponRecordId, skillTicket.couponRecordId) && Intrinsics.areEqual(this.description, skillTicket.description) && Intrinsics.areEqual(this.enableTime, skillTicket.enableTime) && this.type == skillTicket.type && Intrinsics.areEqual(this.typeName, skillTicket.typeName) && this.usableRange == skillTicket.usableRange;
    }

    public final int getCanUse() {
        return this.canUse;
    }

    @Nullable
    public final String getClaimTime() {
        return this.claimTime;
    }

    @Nullable
    public final String getCouponAmount() {
        return this.couponAmount;
    }

    @NotNull
    public final String getCouponId() {
        return this.couponId;
    }

    @Nullable
    public final String getCouponRecord() {
        return this.couponRecord;
    }

    @Nullable
    public final String getCouponRecordId() {
        return this.couponRecordId;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getEnableTime() {
        return this.enableTime;
    }

    public final int getType() {
        return this.type;
    }

    @Nullable
    public final String getTypeName() {
        return this.typeName;
    }

    public final int getUsableRange() {
        return this.usableRange;
    }

    public int hashCode() {
        int i2 = this.canUse * 31;
        String str = this.claimTime;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.couponAmount;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.couponId.hashCode()) * 31;
        String str3 = this.couponRecord;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.couponRecordId;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.description;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.enableTime;
        int iHashCode6 = (((iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31) + this.type) * 31;
        String str7 = this.typeName;
        return ((iHashCode6 + (str7 != null ? str7.hashCode() : 0)) * 31) + this.usableRange;
    }

    @NotNull
    public String toString() {
        return "SkillTicket(canUse=" + this.canUse + ", claimTime=" + this.claimTime + ", couponAmount=" + this.couponAmount + ", couponId=" + this.couponId + ", couponRecord=" + this.couponRecord + ", couponRecordId=" + this.couponRecordId + ", description=" + this.description + ", enableTime=" + this.enableTime + ", type=" + this.type + ", typeName=" + this.typeName + ", usableRange=" + this.usableRange + ")";
    }

    public SkillTicket(int i2, @Nullable String str, @Nullable String str2, @NotNull String couponId, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, int i3, @Nullable String str7, int i4) {
        Intrinsics.checkNotNullParameter(couponId, "couponId");
        this.canUse = i2;
        this.claimTime = str;
        this.couponAmount = str2;
        this.couponId = couponId;
        this.couponRecord = str3;
        this.couponRecordId = str4;
        this.description = str5;
        this.enableTime = str6;
        this.type = i3;
        this.typeName = str7;
        this.usableRange = i4;
    }

    public /* synthetic */ SkillTicket(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i3, String str8, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0 : i2, (i5 & 2) != 0 ? "" : str, (i5 & 4) != 0 ? "" : str2, (i5 & 8) != 0 ? "" : str3, (i5 & 16) != 0 ? "" : str4, str5, str6, str7, (i5 & 256) != 0 ? 1 : i3, (i5 & 512) != 0 ? "" : str8, (i5 & 1024) != 0 ? 1 : i4);
    }
}
