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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 32\u00020\u0001:\u000223BU\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rBM\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010 \u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010JV\u0010$\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\u0003HÖ\u0001J\t\u0010*\u001a\u00020\u0007HÖ\u0001J!\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201HÇ\u0001R\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u001e\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\t\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u001e\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\b\u0010\u0010\"\u0004\b\u0017\u0010\u0012R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u001c\u0010\u0010\"\u0004\b\u001d\u0010\u0012¨\u00064"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryDiseaseReq;", "", "seen1", "", "diagnoseId", "trainId", "name", "", "isMain", "isIdentity", "id", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getDiagnoseId", "()Ljava/lang/Integer;", "setDiagnoseId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getId", "setId", "setIdentity", "setMain", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getTrainId", "setTrainId", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/request/InquiryDiseaseReq;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryDiseaseReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private Integer diagnoseId;

    @Nullable
    private Integer id;

    @Nullable
    private Integer isIdentity;

    @Nullable
    private Integer isMain;

    @Nullable
    private String name;

    @Nullable
    private Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryDiseaseReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryDiseaseReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryDiseaseReq> serializer() {
            return InquiryDiseaseReq$$serializer.INSTANCE;
        }
    }

    public InquiryDiseaseReq() {
        this((Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryDiseaseReq(int i2, Integer num, Integer num2, String str, Integer num3, Integer num4, Integer num5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryDiseaseReq$$serializer.INSTANCE.getDescriptor());
        }
        this.diagnoseId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.trainId = -1;
        } else {
            this.trainId = num2;
        }
        if ((i2 & 4) == 0) {
            this.name = "";
        } else {
            this.name = str;
        }
        if ((i2 & 8) == 0) {
            this.isMain = -1;
        } else {
            this.isMain = num3;
        }
        if ((i2 & 16) == 0) {
            this.isIdentity = -1;
        } else {
            this.isIdentity = num4;
        }
        if ((i2 & 32) == 0) {
            this.id = -1;
        } else {
            this.id = num5;
        }
    }

    public static /* synthetic */ InquiryDiseaseReq copy$default(InquiryDiseaseReq inquiryDiseaseReq, Integer num, Integer num2, String str, Integer num3, Integer num4, Integer num5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = inquiryDiseaseReq.diagnoseId;
        }
        if ((i2 & 2) != 0) {
            num2 = inquiryDiseaseReq.trainId;
        }
        Integer num6 = num2;
        if ((i2 & 4) != 0) {
            str = inquiryDiseaseReq.name;
        }
        String str2 = str;
        if ((i2 & 8) != 0) {
            num3 = inquiryDiseaseReq.isMain;
        }
        Integer num7 = num3;
        if ((i2 & 16) != 0) {
            num4 = inquiryDiseaseReq.isIdentity;
        }
        Integer num8 = num4;
        if ((i2 & 32) != 0) {
            num5 = inquiryDiseaseReq.id;
        }
        return inquiryDiseaseReq.copy(num, num6, str2, num7, num8, num5);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryDiseaseReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.diagnoseId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.diagnoseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num2 = self.trainId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.isMain) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.isMain);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num4 = self.isIdentity) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.isIdentity);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || (num5 = self.id) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 5, IntSerializer.INSTANCE, self.id);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getIsMain() {
        return this.isMain;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getIsIdentity() {
        return this.isIdentity;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @NotNull
    public final InquiryDiseaseReq copy(@Nullable Integer diagnoseId, @Nullable Integer trainId, @Nullable String name, @Nullable Integer isMain, @Nullable Integer isIdentity, @Nullable Integer id) {
        return new InquiryDiseaseReq(diagnoseId, trainId, name, isMain, isIdentity, id);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryDiseaseReq)) {
            return false;
        }
        InquiryDiseaseReq inquiryDiseaseReq = (InquiryDiseaseReq) other;
        return Intrinsics.areEqual(this.diagnoseId, inquiryDiseaseReq.diagnoseId) && Intrinsics.areEqual(this.trainId, inquiryDiseaseReq.trainId) && Intrinsics.areEqual(this.name, inquiryDiseaseReq.name) && Intrinsics.areEqual(this.isMain, inquiryDiseaseReq.isMain) && Intrinsics.areEqual(this.isIdentity, inquiryDiseaseReq.isIdentity) && Intrinsics.areEqual(this.id, inquiryDiseaseReq.id);
    }

    @Nullable
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
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
    public final Integer getTrainId() {
        return this.trainId;
    }

    public int hashCode() {
        Integer num = this.diagnoseId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.trainId;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str = this.name;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num3 = this.isMain;
        int iHashCode4 = (iHashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.isIdentity;
        int iHashCode5 = (iHashCode4 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.id;
        return iHashCode5 + (num5 != null ? num5.hashCode() : 0);
    }

    @Nullable
    public final Integer isIdentity() {
        return this.isIdentity;
    }

    @Nullable
    public final Integer isMain() {
        return this.isMain;
    }

    public final void setDiagnoseId(@Nullable Integer num) {
        this.diagnoseId = num;
    }

    public final void setId(@Nullable Integer num) {
        this.id = num;
    }

    public final void setIdentity(@Nullable Integer num) {
        this.isIdentity = num;
    }

    public final void setMain(@Nullable Integer num) {
        this.isMain = num;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setTrainId(@Nullable Integer num) {
        this.trainId = num;
    }

    @NotNull
    public String toString() {
        return "InquiryDiseaseReq(diagnoseId=" + this.diagnoseId + ", trainId=" + this.trainId + ", name=" + this.name + ", isMain=" + this.isMain + ", isIdentity=" + this.isIdentity + ", id=" + this.id + ")";
    }

    public InquiryDiseaseReq(@Nullable Integer num, @Nullable Integer num2, @Nullable String str, @Nullable Integer num3, @Nullable Integer num4, @Nullable Integer num5) {
        this.diagnoseId = num;
        this.trainId = num2;
        this.name = str;
        this.isMain = num3;
        this.isIdentity = num4;
        this.id = num5;
    }

    public /* synthetic */ InquiryDiseaseReq(Integer num, Integer num2, String str, Integer num3, Integer num4, Integer num5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : num, (i2 & 2) != 0 ? -1 : num2, (i2 & 4) != 0 ? "" : str, (i2 & 8) != 0 ? -1 : num3, (i2 & 16) != 0 ? -1 : num4, (i2 & 32) != 0 ? -1 : num5);
    }
}
