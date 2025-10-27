package com.yddmi.doctor.entity.result;

import java.util.ArrayList;
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
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 @2\u00020\u0001:\u0002?@B«\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\u0010\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0010\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014B«\u0001\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003¢\u0006\u0002\u0010\u0015J\u0013\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u0013\u0010)\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005HÆ\u0003J\u0013\u0010*\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005HÆ\u0003J\u0013\u0010+\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005HÆ\u0003J\u0013\u0010,\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005HÆ\u0003J\u0013\u0010-\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005HÆ\u0003J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001bJ\u000b\u0010/\u001a\u0004\u0018\u00010\u000fHÆ\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001bJ´\u0001\u00101\u001a\u00020\u00002\u0012\b\u0002\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00052\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00052\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u00052\u0012\b\u0002\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u00052\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u00052\u0012\b\u0002\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u00102J\u0013\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00106\u001a\u00020\u0003HÖ\u0001J\t\u00107\u001a\u00020\u000fHÖ\u0001J!\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>HÇ\u0001R\u001b\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u001b\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b \u0010\u001bR\u001a\u0010\u0011\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001b\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u001b\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017¨\u0006A"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAskList;", "", "seen1", "", "patientAskList", "", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "askList", "bodyCheckList", "Lcom/yddmi/doctor/entity/result/RowCase;", "assistCheckList", "diagnoseList", "treatmentList", "diagnoseId", "diagnoseName", "", "kind", "mScore", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;I)V", "getAskList", "()Ljava/util/List;", "getAssistCheckList", "getBodyCheckList", "getDiagnoseId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getDiagnoseList", "getDiagnoseName", "()Ljava/lang/String;", "getKind", "getMScore", "()I", "setMScore", "(I)V", "getPatientAskList", "getTreatmentList", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;I)Lcom/yddmi/doctor/entity/result/InquiryAskList;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryAskList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<InquiryAskInfo> askList;

    @Nullable
    private final List<RowCase> assistCheckList;

    @Nullable
    private final List<RowCase> bodyCheckList;

    @Nullable
    private final Integer diagnoseId;

    @Nullable
    private final List<RowCase> diagnoseList;

    @Nullable
    private final String diagnoseName;

    @Nullable
    private final Integer kind;
    private int mScore;

    @Nullable
    private final List<InquiryAskInfo> patientAskList;

    @Nullable
    private final List<RowCase> treatmentList;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAskList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryAskList> serializer() {
            return InquiryAskList$$serializer.INSTANCE;
        }
    }

    public InquiryAskList() {
        this((List) null, (List) null, (List) null, (List) null, (List) null, (List) null, (Integer) null, (String) null, (Integer) null, 0, 1023, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryAskList(int i2, List list, List list2, List list3, List list4, List list5, List list6, Integer num, String str, Integer num2, int i3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryAskList$$serializer.INSTANCE.getDescriptor());
        }
        this.patientAskList = (i2 & 1) == 0 ? new ArrayList() : list;
        if ((i2 & 2) == 0) {
            this.askList = new ArrayList();
        } else {
            this.askList = list2;
        }
        if ((i2 & 4) == 0) {
            this.bodyCheckList = new ArrayList();
        } else {
            this.bodyCheckList = list3;
        }
        if ((i2 & 8) == 0) {
            this.assistCheckList = new ArrayList();
        } else {
            this.assistCheckList = list4;
        }
        if ((i2 & 16) == 0) {
            this.diagnoseList = new ArrayList();
        } else {
            this.diagnoseList = list5;
        }
        if ((i2 & 32) == 0) {
            this.treatmentList = new ArrayList();
        } else {
            this.treatmentList = list6;
        }
        if ((i2 & 64) == 0) {
            this.diagnoseId = -1;
        } else {
            this.diagnoseId = num;
        }
        if ((i2 & 128) == 0) {
            this.diagnoseName = "";
        } else {
            this.diagnoseName = str;
        }
        if ((i2 & 256) == 0) {
            this.kind = -1;
        } else {
            this.kind = num2;
        }
        if ((i2 & 512) == 0) {
            this.mScore = 0;
        } else {
            this.mScore = i3;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryAskList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.patientAskList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryAskInfo$$serializer.INSTANCE)), self.patientAskList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.askList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(InquiryAskInfo$$serializer.INSTANCE)), self.askList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.bodyCheckList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), self.bodyCheckList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.assistCheckList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), self.assistCheckList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.diagnoseList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), self.diagnoseList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.treatmentList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 5, new ArrayListSerializer(BuiltinSerializersKt.getNullable(RowCase$$serializer.INSTANCE)), self.treatmentList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num = self.diagnoseId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.diagnoseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.diagnoseName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.diagnoseName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || (num2 = self.kind) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 8, IntSerializer.INSTANCE, self.kind);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.mScore != 0) {
            output.encodeIntElement(serialDesc, 9, self.mScore);
        }
    }

    @Nullable
    public final List<InquiryAskInfo> component1() {
        return this.patientAskList;
    }

    /* renamed from: component10, reason: from getter */
    public final int getMScore() {
        return this.mScore;
    }

    @Nullable
    public final List<InquiryAskInfo> component2() {
        return this.askList;
    }

    @Nullable
    public final List<RowCase> component3() {
        return this.bodyCheckList;
    }

    @Nullable
    public final List<RowCase> component4() {
        return this.assistCheckList;
    }

    @Nullable
    public final List<RowCase> component5() {
        return this.diagnoseList;
    }

    @Nullable
    public final List<RowCase> component6() {
        return this.treatmentList;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getDiagnoseName() {
        return this.diagnoseName;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final Integer getKind() {
        return this.kind;
    }

    @NotNull
    public final InquiryAskList copy(@Nullable List<InquiryAskInfo> patientAskList, @Nullable List<InquiryAskInfo> askList, @Nullable List<RowCase> bodyCheckList, @Nullable List<RowCase> assistCheckList, @Nullable List<RowCase> diagnoseList, @Nullable List<RowCase> treatmentList, @Nullable Integer diagnoseId, @Nullable String diagnoseName, @Nullable Integer kind, int mScore) {
        return new InquiryAskList(patientAskList, askList, bodyCheckList, assistCheckList, diagnoseList, treatmentList, diagnoseId, diagnoseName, kind, mScore);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryAskList)) {
            return false;
        }
        InquiryAskList inquiryAskList = (InquiryAskList) other;
        return Intrinsics.areEqual(this.patientAskList, inquiryAskList.patientAskList) && Intrinsics.areEqual(this.askList, inquiryAskList.askList) && Intrinsics.areEqual(this.bodyCheckList, inquiryAskList.bodyCheckList) && Intrinsics.areEqual(this.assistCheckList, inquiryAskList.assistCheckList) && Intrinsics.areEqual(this.diagnoseList, inquiryAskList.diagnoseList) && Intrinsics.areEqual(this.treatmentList, inquiryAskList.treatmentList) && Intrinsics.areEqual(this.diagnoseId, inquiryAskList.diagnoseId) && Intrinsics.areEqual(this.diagnoseName, inquiryAskList.diagnoseName) && Intrinsics.areEqual(this.kind, inquiryAskList.kind) && this.mScore == inquiryAskList.mScore;
    }

    @Nullable
    public final List<InquiryAskInfo> getAskList() {
        return this.askList;
    }

    @Nullable
    public final List<RowCase> getAssistCheckList() {
        return this.assistCheckList;
    }

    @Nullable
    public final List<RowCase> getBodyCheckList() {
        return this.bodyCheckList;
    }

    @Nullable
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
    }

    @Nullable
    public final List<RowCase> getDiagnoseList() {
        return this.diagnoseList;
    }

    @Nullable
    public final String getDiagnoseName() {
        return this.diagnoseName;
    }

    @Nullable
    public final Integer getKind() {
        return this.kind;
    }

    public final int getMScore() {
        return this.mScore;
    }

    @Nullable
    public final List<InquiryAskInfo> getPatientAskList() {
        return this.patientAskList;
    }

    @Nullable
    public final List<RowCase> getTreatmentList() {
        return this.treatmentList;
    }

    public int hashCode() {
        List<InquiryAskInfo> list = this.patientAskList;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<InquiryAskInfo> list2 = this.askList;
        int iHashCode2 = (iHashCode + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<RowCase> list3 = this.bodyCheckList;
        int iHashCode3 = (iHashCode2 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<RowCase> list4 = this.assistCheckList;
        int iHashCode4 = (iHashCode3 + (list4 == null ? 0 : list4.hashCode())) * 31;
        List<RowCase> list5 = this.diagnoseList;
        int iHashCode5 = (iHashCode4 + (list5 == null ? 0 : list5.hashCode())) * 31;
        List<RowCase> list6 = this.treatmentList;
        int iHashCode6 = (iHashCode5 + (list6 == null ? 0 : list6.hashCode())) * 31;
        Integer num = this.diagnoseId;
        int iHashCode7 = (iHashCode6 + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.diagnoseName;
        int iHashCode8 = (iHashCode7 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num2 = this.kind;
        return ((iHashCode8 + (num2 != null ? num2.hashCode() : 0)) * 31) + this.mScore;
    }

    public final void setMScore(int i2) {
        this.mScore = i2;
    }

    @NotNull
    public String toString() {
        return "InquiryAskList(patientAskList=" + this.patientAskList + ", askList=" + this.askList + ", bodyCheckList=" + this.bodyCheckList + ", assistCheckList=" + this.assistCheckList + ", diagnoseList=" + this.diagnoseList + ", treatmentList=" + this.treatmentList + ", diagnoseId=" + this.diagnoseId + ", diagnoseName=" + this.diagnoseName + ", kind=" + this.kind + ", mScore=" + this.mScore + ")";
    }

    public InquiryAskList(@Nullable List<InquiryAskInfo> list, @Nullable List<InquiryAskInfo> list2, @Nullable List<RowCase> list3, @Nullable List<RowCase> list4, @Nullable List<RowCase> list5, @Nullable List<RowCase> list6, @Nullable Integer num, @Nullable String str, @Nullable Integer num2, int i2) {
        this.patientAskList = list;
        this.askList = list2;
        this.bodyCheckList = list3;
        this.assistCheckList = list4;
        this.diagnoseList = list5;
        this.treatmentList = list6;
        this.diagnoseId = num;
        this.diagnoseName = str;
        this.kind = num2;
        this.mScore = i2;
    }

    public /* synthetic */ InquiryAskList(List list, List list2, List list3, List list4, List list5, List list6, Integer num, String str, Integer num2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? new ArrayList() : list, (i3 & 2) != 0 ? new ArrayList() : list2, (i3 & 4) != 0 ? new ArrayList() : list3, (i3 & 8) != 0 ? new ArrayList() : list4, (i3 & 16) != 0 ? new ArrayList() : list5, (i3 & 32) != 0 ? new ArrayList() : list6, (i3 & 64) != 0 ? -1 : num, (i3 & 128) != 0 ? "" : str, (i3 & 256) != 0 ? -1 : num2, (i3 & 512) != 0 ? 0 : i2);
    }
}
