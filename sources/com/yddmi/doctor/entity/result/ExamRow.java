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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ?2\u00020\u0001:\u0002>?B\u0091\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013B\u0095\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0014J\u0010\u0010$\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u000b\u0010(\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u000b\u0010/\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u009e\u0001\u00100\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u00101J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00105\u001a\u00020\u0003HÖ\u0001J\t\u00106\u001a\u00020\u0006HÖ\u0001J!\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001f\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\"\u0010\u0016R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b#\u0010\u0016¨\u0006@"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamRow;", "", "seen1", "", "candidateId", "examId", "", "fullMarks", "id", "name", "paperId", "paperName", "patientId", "patientName", "questionCount", "trainId", "status", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getCandidateId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getExamId", "()Ljava/lang/String;", "getFullMarks", "getId", "getName", "getPaperId", "getPaperName", "getPatientId", "getPatientName", "getQuestionCount", "getStatus", "getTrainId", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ExamRow;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamRow {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer candidateId;

    @Nullable
    private final String examId;

    @Nullable
    private final String fullMarks;

    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String paperId;

    @Nullable
    private final String paperName;

    @Nullable
    private final Integer patientId;

    @Nullable
    private final String patientName;

    @Nullable
    private final String questionCount;

    @Nullable
    private final Integer status;

    @Nullable
    private final Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamRow$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamRow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamRow> serializer() {
            return ExamRow$$serializer.INSTANCE;
        }
    }

    public ExamRow() {
        this((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (Integer) null, (Integer) null, 4095, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamRow(int i2, Integer num, String str, String str2, String str3, String str4, String str5, String str6, Integer num2, String str7, String str8, Integer num3, Integer num4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamRow$$serializer.INSTANCE.getDescriptor());
        }
        this.candidateId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.examId = "";
        } else {
            this.examId = str;
        }
        if ((i2 & 4) == 0) {
            this.fullMarks = "";
        } else {
            this.fullMarks = str2;
        }
        if ((i2 & 8) == 0) {
            this.id = "";
        } else {
            this.id = str3;
        }
        if ((i2 & 16) == 0) {
            this.name = "";
        } else {
            this.name = str4;
        }
        if ((i2 & 32) == 0) {
            this.paperId = "";
        } else {
            this.paperId = str5;
        }
        if ((i2 & 64) == 0) {
            this.paperName = "";
        } else {
            this.paperName = str6;
        }
        if ((i2 & 128) == 0) {
            this.patientId = -1;
        } else {
            this.patientId = num2;
        }
        if ((i2 & 256) == 0) {
            this.patientName = "";
        } else {
            this.patientName = str7;
        }
        if ((i2 & 512) == 0) {
            this.questionCount = "";
        } else {
            this.questionCount = str8;
        }
        if ((i2 & 1024) == 0) {
            this.trainId = -1;
        } else {
            this.trainId = num3;
        }
        if ((i2 & 2048) == 0) {
            this.status = -1;
        } else {
            this.status = num4;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamRow self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.candidateId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.candidateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.examId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.fullMarks, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.fullMarks);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.paperId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.paperId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.paperName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.paperName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num2 = self.patientId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.patientName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.patientName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.questionCount, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.questionCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || (num3 = self.trainId) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 10, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || (num4 = self.status) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 11, IntSerializer.INSTANCE, self.status);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getFullMarks() {
        return this.fullMarks;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getPaperId() {
        return this.paperId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPaperName() {
        return this.paperName;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getPatientName() {
        return this.patientName;
    }

    @NotNull
    public final ExamRow copy(@Nullable Integer candidateId, @Nullable String examId, @Nullable String fullMarks, @Nullable String id, @Nullable String name, @Nullable String paperId, @Nullable String paperName, @Nullable Integer patientId, @Nullable String patientName, @Nullable String questionCount, @Nullable Integer trainId, @Nullable Integer status) {
        return new ExamRow(candidateId, examId, fullMarks, id, name, paperId, paperName, patientId, patientName, questionCount, trainId, status);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamRow)) {
            return false;
        }
        ExamRow examRow = (ExamRow) other;
        return Intrinsics.areEqual(this.candidateId, examRow.candidateId) && Intrinsics.areEqual(this.examId, examRow.examId) && Intrinsics.areEqual(this.fullMarks, examRow.fullMarks) && Intrinsics.areEqual(this.id, examRow.id) && Intrinsics.areEqual(this.name, examRow.name) && Intrinsics.areEqual(this.paperId, examRow.paperId) && Intrinsics.areEqual(this.paperName, examRow.paperName) && Intrinsics.areEqual(this.patientId, examRow.patientId) && Intrinsics.areEqual(this.patientName, examRow.patientName) && Intrinsics.areEqual(this.questionCount, examRow.questionCount) && Intrinsics.areEqual(this.trainId, examRow.trainId) && Intrinsics.areEqual(this.status, examRow.status);
    }

    @Nullable
    public final Integer getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    public final String getExamId() {
        return this.examId;
    }

    @Nullable
    public final String getFullMarks() {
        return this.fullMarks;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPaperId() {
        return this.paperId;
    }

    @Nullable
    public final String getPaperName() {
        return this.paperName;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final String getPatientName() {
        return this.patientName;
    }

    @Nullable
    public final String getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    public int hashCode() {
        Integer num = this.candidateId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.examId;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.fullMarks;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.id;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.name;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.paperId;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.paperName;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Integer num2 = this.patientId;
        int iHashCode8 = (iHashCode7 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str7 = this.patientName;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.questionCount;
        int iHashCode10 = (iHashCode9 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Integer num3 = this.trainId;
        int iHashCode11 = (iHashCode10 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.status;
        return iHashCode11 + (num4 != null ? num4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamRow(candidateId=" + this.candidateId + ", examId=" + this.examId + ", fullMarks=" + this.fullMarks + ", id=" + this.id + ", name=" + this.name + ", paperId=" + this.paperId + ", paperName=" + this.paperName + ", patientId=" + this.patientId + ", patientName=" + this.patientName + ", questionCount=" + this.questionCount + ", trainId=" + this.trainId + ", status=" + this.status + ")";
    }

    public ExamRow(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Integer num2, @Nullable String str7, @Nullable String str8, @Nullable Integer num3, @Nullable Integer num4) {
        this.candidateId = num;
        this.examId = str;
        this.fullMarks = str2;
        this.id = str3;
        this.name = str4;
        this.paperId = str5;
        this.paperName = str6;
        this.patientId = num2;
        this.patientName = str7;
        this.questionCount = str8;
        this.trainId = num3;
        this.status = num4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ExamRow(Integer num, String str, String str2, String str3, String str4, String str5, String str6, Integer num2, String str7, String str8, Integer num3, Integer num4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? num : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? "" : str5, (i2 & 64) != 0 ? "" : str6, (i2 & 128) != 0 ? num : num2, (i2 & 256) != 0 ? "" : str7, (i2 & 512) == 0 ? str8 : "", (i2 & 1024) != 0 ? num : num3, (i2 & 2048) == 0 ? num4 : -1);
    }
}
