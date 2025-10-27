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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB?\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\u0002\u0010\u000eJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0003JC\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/result/HeartData;", "", "seen1", "", "totalNum", "unMasterNum", "masterNum", "isExchange", "medicalKnowledgeNum", "", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIIILjava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IIIILjava/util/List;)V", "()I", "getMasterNum", "getMedicalKnowledgeNum", "()Ljava/util/List;", "getTotalNum", "getUnMasterNum", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HeartData {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int isExchange;
    private final int masterNum;

    @Nullable
    private final List<HeartDetail> medicalKnowledgeNum;
    private final int totalNum;
    private final int unMasterNum;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HeartData$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HeartData;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HeartData> serializer() {
            return HeartData$$serializer.INSTANCE;
        }
    }

    public HeartData() {
        this(0, 0, 0, 0, (List) null, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HeartData(int i2, int i3, int i4, int i5, int i6, List list, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HeartData$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.totalNum = 0;
        } else {
            this.totalNum = i3;
        }
        if ((i2 & 2) == 0) {
            this.unMasterNum = 0;
        } else {
            this.unMasterNum = i4;
        }
        if ((i2 & 4) == 0) {
            this.masterNum = 0;
        } else {
            this.masterNum = i5;
        }
        if ((i2 & 8) == 0) {
            this.isExchange = 0;
        } else {
            this.isExchange = i6;
        }
        if ((i2 & 16) == 0) {
            this.medicalKnowledgeNum = null;
        } else {
            this.medicalKnowledgeNum = list;
        }
    }

    public static /* synthetic */ HeartData copy$default(HeartData heartData, int i2, int i3, int i4, int i5, List list, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = heartData.totalNum;
        }
        if ((i6 & 2) != 0) {
            i3 = heartData.unMasterNum;
        }
        int i7 = i3;
        if ((i6 & 4) != 0) {
            i4 = heartData.masterNum;
        }
        int i8 = i4;
        if ((i6 & 8) != 0) {
            i5 = heartData.isExchange;
        }
        int i9 = i5;
        if ((i6 & 16) != 0) {
            list = heartData.medicalKnowledgeNum;
        }
        return heartData.copy(i2, i7, i8, i9, list);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HeartData self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.totalNum != 0) {
            output.encodeIntElement(serialDesc, 0, self.totalNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.unMasterNum != 0) {
            output.encodeIntElement(serialDesc, 1, self.unMasterNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.masterNum != 0) {
            output.encodeIntElement(serialDesc, 2, self.masterNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.isExchange != 0) {
            output.encodeIntElement(serialDesc, 3, self.isExchange);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.medicalKnowledgeNum != null) {
            output.encodeNullableSerializableElement(serialDesc, 4, new ArrayListSerializer(HeartDetail$$serializer.INSTANCE), self.medicalKnowledgeNum);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getTotalNum() {
        return this.totalNum;
    }

    /* renamed from: component2, reason: from getter */
    public final int getUnMasterNum() {
        return this.unMasterNum;
    }

    /* renamed from: component3, reason: from getter */
    public final int getMasterNum() {
        return this.masterNum;
    }

    /* renamed from: component4, reason: from getter */
    public final int getIsExchange() {
        return this.isExchange;
    }

    @Nullable
    public final List<HeartDetail> component5() {
        return this.medicalKnowledgeNum;
    }

    @NotNull
    public final HeartData copy(int totalNum, int unMasterNum, int masterNum, int isExchange, @Nullable List<HeartDetail> medicalKnowledgeNum) {
        return new HeartData(totalNum, unMasterNum, masterNum, isExchange, medicalKnowledgeNum);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HeartData)) {
            return false;
        }
        HeartData heartData = (HeartData) other;
        return this.totalNum == heartData.totalNum && this.unMasterNum == heartData.unMasterNum && this.masterNum == heartData.masterNum && this.isExchange == heartData.isExchange && Intrinsics.areEqual(this.medicalKnowledgeNum, heartData.medicalKnowledgeNum);
    }

    public final int getMasterNum() {
        return this.masterNum;
    }

    @Nullable
    public final List<HeartDetail> getMedicalKnowledgeNum() {
        return this.medicalKnowledgeNum;
    }

    public final int getTotalNum() {
        return this.totalNum;
    }

    public final int getUnMasterNum() {
        return this.unMasterNum;
    }

    public int hashCode() {
        int i2 = ((((((this.totalNum * 31) + this.unMasterNum) * 31) + this.masterNum) * 31) + this.isExchange) * 31;
        List<HeartDetail> list = this.medicalKnowledgeNum;
        return i2 + (list == null ? 0 : list.hashCode());
    }

    public final int isExchange() {
        return this.isExchange;
    }

    @NotNull
    public String toString() {
        return "HeartData(totalNum=" + this.totalNum + ", unMasterNum=" + this.unMasterNum + ", masterNum=" + this.masterNum + ", isExchange=" + this.isExchange + ", medicalKnowledgeNum=" + this.medicalKnowledgeNum + ")";
    }

    public HeartData(int i2, int i3, int i4, int i5, @Nullable List<HeartDetail> list) {
        this.totalNum = i2;
        this.unMasterNum = i3;
        this.masterNum = i4;
        this.isExchange = i5;
        this.medicalKnowledgeNum = list;
    }

    public /* synthetic */ HeartData(int i2, int i3, int i4, int i5, List list, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i2, (i6 & 2) != 0 ? 0 : i3, (i6 & 4) != 0 ? 0 : i4, (i6 & 8) == 0 ? i5 : 0, (i6 & 16) != 0 ? null : list);
    }
}
