package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 -2\u00020\u0001:\u0002,-BU\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\b\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rBM\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\bHÆ\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\bHÆ\u0003JV\u0010\u001e\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\bHÆ\u0001¢\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0003HÖ\u0001J\t\u0010$\u001a\u00020\bHÖ\u0001J!\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+HÇ\u0001R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0013\u0010\u0010R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0016\u0010\u0010R\u0018\u0010\n\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015¨\u0006."}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryBindData;", "", "seen1", "", "assistId", "assistTypeId", "id", "name", "", "patientId", "result", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)V", "getAssistId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAssistTypeId", "getId", "getName", "()Ljava/lang/String;", "getPatientId", "getResult", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)Lcom/yddmi/doctor/entity/result/InquiryBindData;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryBindData {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("assistId")
    @Nullable
    private final Integer assistId;

    @SerializedName("assistTypeId")
    @Nullable
    private final Integer assistTypeId;

    @SerializedName("id")
    @Nullable
    private final Integer id;

    @SerializedName("name")
    @Nullable
    private final String name;

    @SerializedName("patientId")
    @Nullable
    private final Integer patientId;

    @SerializedName("result")
    @Nullable
    private final String result;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryBindData$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryBindData;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryBindData> serializer() {
            return InquiryBindData$$serializer.INSTANCE;
        }
    }

    public InquiryBindData() {
        this((Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (String) null, 63, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryBindData(int i2, Integer num, Integer num2, Integer num3, String str, Integer num4, String str2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryBindData$$serializer.INSTANCE.getDescriptor());
        }
        this.assistId = (i2 & 1) == 0 ? 0 : num;
        if ((i2 & 2) == 0) {
            this.assistTypeId = -1;
        } else {
            this.assistTypeId = num2;
        }
        if ((i2 & 4) == 0) {
            this.id = -1;
        } else {
            this.id = num3;
        }
        if ((i2 & 8) == 0) {
            this.name = "";
        } else {
            this.name = str;
        }
        if ((i2 & 16) == 0) {
            this.patientId = -1;
        } else {
            this.patientId = num4;
        }
        if ((i2 & 32) == 0) {
            this.result = "";
        } else {
            this.result = str2;
        }
    }

    public static /* synthetic */ InquiryBindData copy$default(InquiryBindData inquiryBindData, Integer num, Integer num2, Integer num3, String str, Integer num4, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = inquiryBindData.assistId;
        }
        if ((i2 & 2) != 0) {
            num2 = inquiryBindData.assistTypeId;
        }
        Integer num5 = num2;
        if ((i2 & 4) != 0) {
            num3 = inquiryBindData.id;
        }
        Integer num6 = num3;
        if ((i2 & 8) != 0) {
            str = inquiryBindData.name;
        }
        String str3 = str;
        if ((i2 & 16) != 0) {
            num4 = inquiryBindData.patientId;
        }
        Integer num7 = num4;
        if ((i2 & 32) != 0) {
            str2 = inquiryBindData.result;
        }
        return inquiryBindData.copy(num, num5, num6, str3, num7, str2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryBindData self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.assistId) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.assistId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num2 = self.assistTypeId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.assistTypeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num3 = self.id) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num4 = self.patientId) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.result);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAssistId() {
        return this.assistId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getAssistTypeId() {
        return this.assistTypeId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @NotNull
    public final InquiryBindData copy(@Nullable Integer assistId, @Nullable Integer assistTypeId, @Nullable Integer id, @Nullable String name, @Nullable Integer patientId, @Nullable String result) {
        return new InquiryBindData(assistId, assistTypeId, id, name, patientId, result);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryBindData)) {
            return false;
        }
        InquiryBindData inquiryBindData = (InquiryBindData) other;
        return Intrinsics.areEqual(this.assistId, inquiryBindData.assistId) && Intrinsics.areEqual(this.assistTypeId, inquiryBindData.assistTypeId) && Intrinsics.areEqual(this.id, inquiryBindData.id) && Intrinsics.areEqual(this.name, inquiryBindData.name) && Intrinsics.areEqual(this.patientId, inquiryBindData.patientId) && Intrinsics.areEqual(this.result, inquiryBindData.result);
    }

    @Nullable
    public final Integer getAssistId() {
        return this.assistId;
    }

    @Nullable
    public final Integer getAssistTypeId() {
        return this.assistTypeId;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    public int hashCode() {
        Integer num = this.assistId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.assistTypeId;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.id;
        int iHashCode3 = (iHashCode2 + (num3 == null ? 0 : num3.hashCode())) * 31;
        String str = this.name;
        int iHashCode4 = (iHashCode3 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num4 = this.patientId;
        int iHashCode5 = (iHashCode4 + (num4 == null ? 0 : num4.hashCode())) * 31;
        String str2 = this.result;
        return iHashCode5 + (str2 != null ? str2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "InquiryBindData(assistId=" + this.assistId + ", assistTypeId=" + this.assistTypeId + ", id=" + this.id + ", name=" + this.name + ", patientId=" + this.patientId + ", result=" + this.result + ")";
    }

    public InquiryBindData(@Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3, @Nullable String str, @Nullable Integer num4, @Nullable String str2) {
        this.assistId = num;
        this.assistTypeId = num2;
        this.id = num3;
        this.name = str;
        this.patientId = num4;
        this.result = str2;
    }

    public /* synthetic */ InquiryBindData(Integer num, Integer num2, Integer num3, String str, Integer num4, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? -1 : num2, (i2 & 4) != 0 ? -1 : num3, (i2 & 8) != 0 ? "" : str, (i2 & 16) != 0 ? -1 : num4, (i2 & 32) != 0 ? "" : str2);
    }
}
