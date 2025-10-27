package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 @2\u00020\u0001:\u0002?@B\u0087\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013B\u008b\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003¢\u0006\u0002\u0010\u0014J\u000b\u0010%\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u0010\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0018J\u000b\u0010*\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0018J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\u0094\u0001\u00101\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u00102J\u0013\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00106\u001a\u00020\u0003HÖ\u0001J\t\u00107\u001a\u00020\u0005HÖ\u0001J!\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001d¨\u0006A"}, d2 = {"Lcom/yddmi/doctor/entity/result/IntegralRow;", "", "seen1", "", "businessName", "", "businessType", "createTime", "createUser", "currentScore", "name", AliyunLogCommon.TERMINAL_TYPE, "reason", "score", "status", "type", "userId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIII)V", "getBusinessName", "()Ljava/lang/String;", "getBusinessType", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCreateTime", "getCreateUser", "getCurrentScore", "()I", "getName", "getPhone", "getReason", "getScore", "getStatus", "getType", "getUserId", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIII)Lcom/yddmi/doctor/entity/result/IntegralRow;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class IntegralRow {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String businessName;

    @Nullable
    private final Integer businessType;

    @Nullable
    private final String createTime;

    @Nullable
    private final Integer createUser;
    private final int currentScore;

    @Nullable
    private final String name;

    @Nullable
    private final String phone;

    @Nullable
    private final String reason;
    private final int score;
    private final int status;
    private final int type;
    private final int userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/IntegralRow$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/IntegralRow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<IntegralRow> serializer() {
            return IntegralRow$$serializer.INSTANCE;
        }
    }

    public IntegralRow() {
        this((String) null, (Integer) null, (String) null, (Integer) null, 0, (String) null, (String) null, (String) null, 0, 0, 0, 0, 4095, (DefaultConstructorMarker) null);
    }

    public IntegralRow(@Nullable String str, @Nullable Integer num, @Nullable String str2, @Nullable Integer num2, int i2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i3, int i4, int i5, int i6) {
        this.businessName = str;
        this.businessType = num;
        this.createTime = str2;
        this.createUser = num2;
        this.currentScore = i2;
        this.name = str3;
        this.phone = str4;
        this.reason = str5;
        this.score = i3;
        this.status = i4;
        this.type = i5;
        this.userId = i6;
    }

    @JvmStatic
    public static final void write$Self(@NotNull IntegralRow self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.businessName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.businessName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.businessType) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.businessType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num2 = self.createUser) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.createUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.currentScore != 0) {
            output.encodeIntElement(serialDesc, 4, self.currentScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.phone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.phone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.reason, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.reason);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.score != 0) {
            output.encodeIntElement(serialDesc, 8, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.status != 0) {
            output.encodeIntElement(serialDesc, 9, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.type != 0) {
            output.encodeIntElement(serialDesc, 10, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.userId != 0) {
            output.encodeIntElement(serialDesc, 11, self.userId);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getBusinessName() {
        return this.businessName;
    }

    /* renamed from: component10, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    /* renamed from: component11, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component12, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getBusinessType() {
        return this.businessType;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getCreateUser() {
        return this.createUser;
    }

    /* renamed from: component5, reason: from getter */
    public final int getCurrentScore() {
        return this.currentScore;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getReason() {
        return this.reason;
    }

    /* renamed from: component9, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    @NotNull
    public final IntegralRow copy(@Nullable String businessName, @Nullable Integer businessType, @Nullable String createTime, @Nullable Integer createUser, int currentScore, @Nullable String name, @Nullable String phone, @Nullable String reason, int score, int status, int type, int userId) {
        return new IntegralRow(businessName, businessType, createTime, createUser, currentScore, name, phone, reason, score, status, type, userId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntegralRow)) {
            return false;
        }
        IntegralRow integralRow = (IntegralRow) other;
        return Intrinsics.areEqual(this.businessName, integralRow.businessName) && Intrinsics.areEqual(this.businessType, integralRow.businessType) && Intrinsics.areEqual(this.createTime, integralRow.createTime) && Intrinsics.areEqual(this.createUser, integralRow.createUser) && this.currentScore == integralRow.currentScore && Intrinsics.areEqual(this.name, integralRow.name) && Intrinsics.areEqual(this.phone, integralRow.phone) && Intrinsics.areEqual(this.reason, integralRow.reason) && this.score == integralRow.score && this.status == integralRow.status && this.type == integralRow.type && this.userId == integralRow.userId;
    }

    @Nullable
    public final String getBusinessName() {
        return this.businessName;
    }

    @Nullable
    public final Integer getBusinessType() {
        return this.businessType;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final Integer getCreateUser() {
        return this.createUser;
    }

    public final int getCurrentScore() {
        return this.currentScore;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    public final String getReason() {
        return this.reason;
    }

    public final int getScore() {
        return this.score;
    }

    public final int getStatus() {
        return this.status;
    }

    public final int getType() {
        return this.type;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        String str = this.businessName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.businessType;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str2 = this.createTime;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num2 = this.createUser;
        int iHashCode4 = (((iHashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31) + this.currentScore) * 31;
        String str3 = this.name;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.phone;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.reason;
        return ((((((((iHashCode6 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.score) * 31) + this.status) * 31) + this.type) * 31) + this.userId;
    }

    @NotNull
    public String toString() {
        return "IntegralRow(businessName=" + this.businessName + ", businessType=" + this.businessType + ", createTime=" + this.createTime + ", createUser=" + this.createUser + ", currentScore=" + this.currentScore + ", name=" + this.name + ", phone=" + this.phone + ", reason=" + this.reason + ", score=" + this.score + ", status=" + this.status + ", type=" + this.type + ", userId=" + this.userId + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ IntegralRow(int i2, String str, Integer num, String str2, Integer num2, int i3, String str3, String str4, String str5, int i4, int i5, int i6, int i7, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, IntegralRow$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.businessName = "";
        } else {
            this.businessName = str;
        }
        if ((i2 & 2) == 0) {
            this.businessType = 0;
        } else {
            this.businessType = num;
        }
        if ((i2 & 4) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str2;
        }
        if ((i2 & 8) == 0) {
            this.createUser = 0;
        } else {
            this.createUser = num2;
        }
        if ((i2 & 16) == 0) {
            this.currentScore = 0;
        } else {
            this.currentScore = i3;
        }
        if ((i2 & 32) == 0) {
            this.name = "";
        } else {
            this.name = str3;
        }
        if ((i2 & 64) == 0) {
            this.phone = "";
        } else {
            this.phone = str4;
        }
        if ((i2 & 128) == 0) {
            this.reason = "";
        } else {
            this.reason = str5;
        }
        if ((i2 & 256) == 0) {
            this.score = 0;
        } else {
            this.score = i4;
        }
        if ((i2 & 512) == 0) {
            this.status = 0;
        } else {
            this.status = i5;
        }
        if ((i2 & 1024) == 0) {
            this.type = 0;
        } else {
            this.type = i6;
        }
        if ((i2 & 2048) == 0) {
            this.userId = 0;
        } else {
            this.userId = i7;
        }
    }

    public /* synthetic */ IntegralRow(String str, Integer num, String str2, Integer num2, int i2, String str3, String str4, String str5, int i3, int i4, int i5, int i6, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? "" : str, (i7 & 2) != 0 ? 0 : num, (i7 & 4) != 0 ? "" : str2, (i7 & 8) != 0 ? 0 : num2, (i7 & 16) != 0 ? 0 : i2, (i7 & 32) != 0 ? "" : str3, (i7 & 64) != 0 ? "" : str4, (i7 & 128) == 0 ? str5 : "", (i7 & 256) != 0 ? 0 : i3, (i7 & 512) != 0 ? 0 : i4, (i7 & 1024) != 0 ? 0 : i5, (i7 & 2048) == 0 ? i6 : 0);
    }
}
