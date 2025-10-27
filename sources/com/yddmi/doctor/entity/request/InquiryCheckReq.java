package com.yddmi.doctor.entity.request;

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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()B9\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB%\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\u0011\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J/\u0010\u001a\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryCheckReq;", "", "seen1", "", "operateList", "", "Lcom/yddmi/doctor/entity/request/InquiryCheckReqList;", "trainId", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;IILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;II)V", "getOperateList", "()Ljava/util/List;", "setOperateList", "(Ljava/util/List;)V", "getTrainId", "()I", "setTrainId", "(I)V", "getType", "setType", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryCheckReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private List<InquiryCheckReqList> operateList;
    private int trainId;
    private int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryCheckReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryCheckReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryCheckReq> serializer() {
            return InquiryCheckReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryCheckReq(int i2, List list, int i3, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, InquiryCheckReq$$serializer.INSTANCE.getDescriptor());
        }
        this.operateList = list;
        this.trainId = i3;
        this.type = i4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InquiryCheckReq copy$default(InquiryCheckReq inquiryCheckReq, List list, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            list = inquiryCheckReq.operateList;
        }
        if ((i4 & 2) != 0) {
            i2 = inquiryCheckReq.trainId;
        }
        if ((i4 & 4) != 0) {
            i3 = inquiryCheckReq.type;
        }
        return inquiryCheckReq.copy(list, i2, i3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryCheckReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(InquiryCheckReqList$$serializer.INSTANCE), self.operateList);
        output.encodeIntElement(serialDesc, 1, self.trainId);
        output.encodeIntElement(serialDesc, 2, self.type);
    }

    @Nullable
    public final List<InquiryCheckReqList> component1() {
        return this.operateList;
    }

    /* renamed from: component2, reason: from getter */
    public final int getTrainId() {
        return this.trainId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    public final InquiryCheckReq copy(@Nullable List<InquiryCheckReqList> operateList, int trainId, int type) {
        return new InquiryCheckReq(operateList, trainId, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryCheckReq)) {
            return false;
        }
        InquiryCheckReq inquiryCheckReq = (InquiryCheckReq) other;
        return Intrinsics.areEqual(this.operateList, inquiryCheckReq.operateList) && this.trainId == inquiryCheckReq.trainId && this.type == inquiryCheckReq.type;
    }

    @Nullable
    public final List<InquiryCheckReqList> getOperateList() {
        return this.operateList;
    }

    public final int getTrainId() {
        return this.trainId;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        List<InquiryCheckReqList> list = this.operateList;
        return ((((list == null ? 0 : list.hashCode()) * 31) + this.trainId) * 31) + this.type;
    }

    public final void setOperateList(@Nullable List<InquiryCheckReqList> list) {
        this.operateList = list;
    }

    public final void setTrainId(int i2) {
        this.trainId = i2;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public String toString() {
        return "InquiryCheckReq(operateList=" + this.operateList + ", trainId=" + this.trainId + ", type=" + this.type + ")";
    }

    public InquiryCheckReq(@Nullable List<InquiryCheckReqList> list, int i2, int i3) {
        this.operateList = list;
        this.trainId = i2;
        this.type = i3;
    }
}
