package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
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
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 +2\u00020\u0001:\u0002*+B=\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB/\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u0011\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J8\u0010\u001b\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0003HÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001J!\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)HÇ\u0001R&\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\"\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\"\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014¨\u0006,"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquirySaveBasisReq;", "", "seen1", "", "operateList", "", "Lcom/yddmi/doctor/entity/request/Operate;", "trainDiagnoseId", "trainId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getOperateList", "()Ljava/util/List;", "setOperateList", "(Ljava/util/List;)V", "getTrainDiagnoseId", "()Ljava/lang/Integer;", "setTrainDiagnoseId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getTrainId", "setTrainId", "component1", "component2", "component3", "copy", "(Ljava/util/List;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/request/InquirySaveBasisReq;", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquirySaveBasisReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("operateList")
    @Nullable
    private List<Operate> operateList;

    @SerializedName("trainDiagnoseId")
    @Nullable
    private Integer trainDiagnoseId;

    @SerializedName("trainId")
    @Nullable
    private Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquirySaveBasisReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquirySaveBasisReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquirySaveBasisReq> serializer() {
            return InquirySaveBasisReq$$serializer.INSTANCE;
        }
    }

    public InquirySaveBasisReq() {
        this((List) null, (Integer) null, (Integer) null, 7, (DefaultConstructorMarker) null);
    }

    public InquirySaveBasisReq(@Nullable List<Operate> list, @Nullable Integer num, @Nullable Integer num2) {
        this.operateList = list;
        this.trainDiagnoseId = num;
        this.trainId = num2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InquirySaveBasisReq copy$default(InquirySaveBasisReq inquirySaveBasisReq, List list, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = inquirySaveBasisReq.operateList;
        }
        if ((i2 & 2) != 0) {
            num = inquirySaveBasisReq.trainDiagnoseId;
        }
        if ((i2 & 4) != 0) {
            num2 = inquirySaveBasisReq.trainId;
        }
        return inquirySaveBasisReq.copy(list, num, num2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquirySaveBasisReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.operateList != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(Operate$$serializer.INSTANCE), self.operateList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.trainDiagnoseId) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.trainDiagnoseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num2 = self.trainId) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.trainId);
        }
    }

    @Nullable
    public final List<Operate> component1() {
        return this.operateList;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getTrainDiagnoseId() {
        return this.trainDiagnoseId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @NotNull
    public final InquirySaveBasisReq copy(@Nullable List<Operate> operateList, @Nullable Integer trainDiagnoseId, @Nullable Integer trainId) {
        return new InquirySaveBasisReq(operateList, trainDiagnoseId, trainId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquirySaveBasisReq)) {
            return false;
        }
        InquirySaveBasisReq inquirySaveBasisReq = (InquirySaveBasisReq) other;
        return Intrinsics.areEqual(this.operateList, inquirySaveBasisReq.operateList) && Intrinsics.areEqual(this.trainDiagnoseId, inquirySaveBasisReq.trainDiagnoseId) && Intrinsics.areEqual(this.trainId, inquirySaveBasisReq.trainId);
    }

    @Nullable
    public final List<Operate> getOperateList() {
        return this.operateList;
    }

    @Nullable
    public final Integer getTrainDiagnoseId() {
        return this.trainDiagnoseId;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    public int hashCode() {
        List<Operate> list = this.operateList;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        Integer num = this.trainDiagnoseId;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.trainId;
        return iHashCode2 + (num2 != null ? num2.hashCode() : 0);
    }

    public final void setOperateList(@Nullable List<Operate> list) {
        this.operateList = list;
    }

    public final void setTrainDiagnoseId(@Nullable Integer num) {
        this.trainDiagnoseId = num;
    }

    public final void setTrainId(@Nullable Integer num) {
        this.trainId = num;
    }

    @NotNull
    public String toString() {
        return "InquirySaveBasisReq(operateList=" + this.operateList + ", trainDiagnoseId=" + this.trainDiagnoseId + ", trainId=" + this.trainId + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquirySaveBasisReq(int i2, List list, Integer num, Integer num2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquirySaveBasisReq$$serializer.INSTANCE.getDescriptor());
        }
        this.operateList = (i2 & 1) == 0 ? null : list;
        if ((i2 & 2) == 0) {
            this.trainDiagnoseId = 0;
        } else {
            this.trainDiagnoseId = num;
        }
        if ((i2 & 4) == 0) {
            this.trainId = 0;
        } else {
            this.trainId = num2;
        }
    }

    public /* synthetic */ InquirySaveBasisReq(List list, Integer num, Integer num2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? 0 : num, (i2 & 4) != 0 ? 0 : num2);
    }
}
