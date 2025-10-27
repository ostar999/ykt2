package com.yddmi.doctor.entity.request;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@Keep
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 (2\u00020\u0001:\u0002'(B?\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB+\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\rJ\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J1\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001J!\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&HÇ\u0001R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006)"}, d2 = {"Lcom/yddmi/doctor/entity/request/Operation;", "", "seen1", "", "operationCode", "", "businessRemark", "businessId", "operationTime", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getBusinessId", "()Ljava/lang/String;", "setBusinessId", "(Ljava/lang/String;)V", "getBusinessRemark", "getOperationCode", "getOperationTime", "()J", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class Operation {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String businessId;

    @NotNull
    private final String businessRemark;

    @NotNull
    private final String operationCode;
    private final long operationTime;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/Operation$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/Operation;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<Operation> serializer() {
            return Operation$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ Operation(int i2, String str, String str2, String str3, long j2, SerializationConstructorMarker serializationConstructorMarker) {
        if (1 != (i2 & 1)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 1, Operation$$serializer.INSTANCE.getDescriptor());
        }
        this.operationCode = str;
        if ((i2 & 2) == 0) {
            this.businessRemark = "";
        } else {
            this.businessRemark = str2;
        }
        if ((i2 & 4) == 0) {
            this.businessId = "";
        } else {
            this.businessId = str3;
        }
        if ((i2 & 8) == 0) {
            this.operationTime = 0L;
        } else {
            this.operationTime = j2;
        }
    }

    public static /* synthetic */ Operation copy$default(Operation operation, String str, String str2, String str3, long j2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = operation.operationCode;
        }
        if ((i2 & 2) != 0) {
            str2 = operation.businessRemark;
        }
        String str4 = str2;
        if ((i2 & 4) != 0) {
            str3 = operation.businessId;
        }
        String str5 = str3;
        if ((i2 & 8) != 0) {
            j2 = operation.operationTime;
        }
        return operation.copy(str, str4, str5, j2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull Operation self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.operationCode);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.businessRemark, "")) {
            output.encodeStringElement(serialDesc, 1, self.businessRemark);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.businessId, "")) {
            output.encodeStringElement(serialDesc, 2, self.businessId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.operationTime != 0) {
            output.encodeLongElement(serialDesc, 3, self.operationTime);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getOperationCode() {
        return this.operationCode;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getBusinessRemark() {
        return this.businessRemark;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getBusinessId() {
        return this.businessId;
    }

    /* renamed from: component4, reason: from getter */
    public final long getOperationTime() {
        return this.operationTime;
    }

    @NotNull
    public final Operation copy(@NotNull String operationCode, @NotNull String businessRemark, @NotNull String businessId, long operationTime) {
        Intrinsics.checkNotNullParameter(operationCode, "operationCode");
        Intrinsics.checkNotNullParameter(businessRemark, "businessRemark");
        Intrinsics.checkNotNullParameter(businessId, "businessId");
        return new Operation(operationCode, businessRemark, businessId, operationTime);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Operation)) {
            return false;
        }
        Operation operation = (Operation) other;
        return Intrinsics.areEqual(this.operationCode, operation.operationCode) && Intrinsics.areEqual(this.businessRemark, operation.businessRemark) && Intrinsics.areEqual(this.businessId, operation.businessId) && this.operationTime == operation.operationTime;
    }

    @NotNull
    public final String getBusinessId() {
        return this.businessId;
    }

    @NotNull
    public final String getBusinessRemark() {
        return this.businessRemark;
    }

    @NotNull
    public final String getOperationCode() {
        return this.operationCode;
    }

    public final long getOperationTime() {
        return this.operationTime;
    }

    public int hashCode() {
        return (((((this.operationCode.hashCode() * 31) + this.businessRemark.hashCode()) * 31) + this.businessId.hashCode()) * 31) + a.a(this.operationTime);
    }

    public final void setBusinessId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.businessId = str;
    }

    @NotNull
    public String toString() {
        return "Operation(operationCode=" + this.operationCode + ", businessRemark=" + this.businessRemark + ", businessId=" + this.businessId + ", operationTime=" + this.operationTime + ")";
    }

    public Operation(@NotNull String operationCode, @NotNull String businessRemark, @NotNull String businessId, long j2) {
        Intrinsics.checkNotNullParameter(operationCode, "operationCode");
        Intrinsics.checkNotNullParameter(businessRemark, "businessRemark");
        Intrinsics.checkNotNullParameter(businessId, "businessId");
        this.operationCode = operationCode;
        this.businessRemark = businessRemark;
        this.businessId = businessId;
        this.operationTime = j2;
    }

    public /* synthetic */ Operation(String str, String str2, String str3, long j2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? 0L : j2);
    }
}
