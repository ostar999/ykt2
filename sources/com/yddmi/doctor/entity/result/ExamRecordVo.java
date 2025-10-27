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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 L2\u00020\u0001:\u0002KLB·\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\u0002\u0010\u0017BÃ\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0018J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u0010\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u000b\u0010/\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u00103\u001a\u00020\u0003HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u000b\u0010:\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u0010\u0010<\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJÌ\u0001\u0010=\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006HÆ\u0001¢\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010B\u001a\u00020\u0003HÖ\u0001J\t\u0010C\u001a\u00020\u0006HÖ\u0001J!\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020\u00002\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020JHÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b%\u0010\u001aR\u0011\u0010\u0013\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b*\u0010\u001aR\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b+\u0010\u001aR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b,\u0010\u001a¨\u0006M"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamRecordVo;", "", "seen1", "", "candidateId", "caseName", "", "degree", "examId", "id", "patientId", "score", "status", "timeCost", "trainId", "endTime", "name", "paperId", "paperScore", "questionCount", "startTime", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getCandidateId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCaseName", "()Ljava/lang/String;", "getDegree", "getEndTime", "getExamId", "getId", "getName", "getPaperId", "getPaperScore", "getPatientId", "getQuestionCount", "()I", "getScore", "getStartTime", "getStatus", "getTimeCost", "getTrainId", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lcom/yddmi/doctor/entity/result/ExamRecordVo;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamRecordVo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer candidateId;

    @Nullable
    private final String caseName;

    @Nullable
    private final String degree;

    @Nullable
    private final String endTime;

    @Nullable
    private final String examId;

    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String paperId;

    @Nullable
    private final String paperScore;

    @Nullable
    private final Integer patientId;
    private final int questionCount;

    @Nullable
    private final String score;

    @Nullable
    private final String startTime;

    @Nullable
    private final Integer status;

    @Nullable
    private final Integer timeCost;

    @Nullable
    private final Integer trainId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamRecordVo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamRecordVo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamRecordVo> serializer() {
            return ExamRecordVo$$serializer.INSTANCE;
        }
    }

    public ExamRecordVo() {
        this((Integer) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null, (String) null, (String) null, 0, (String) null, 65535, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamRecordVo(int i2, Integer num, String str, String str2, String str3, String str4, Integer num2, String str5, Integer num3, Integer num4, Integer num5, String str6, String str7, String str8, String str9, int i3, String str10, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamRecordVo$$serializer.INSTANCE.getDescriptor());
        }
        this.candidateId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.caseName = "";
        } else {
            this.caseName = str;
        }
        if ((i2 & 4) == 0) {
            this.degree = "";
        } else {
            this.degree = str2;
        }
        if ((i2 & 8) == 0) {
            this.examId = "";
        } else {
            this.examId = str3;
        }
        if ((i2 & 16) == 0) {
            this.id = "";
        } else {
            this.id = str4;
        }
        this.patientId = (i2 & 32) == 0 ? -1 : num2;
        if ((i2 & 64) == 0) {
            this.score = "";
        } else {
            this.score = str5;
        }
        this.status = (i2 & 128) == 0 ? -1 : num3;
        this.timeCost = (i2 & 256) == 0 ? -1 : num4;
        this.trainId = (i2 & 512) == 0 ? -1 : num5;
        if ((i2 & 1024) == 0) {
            this.endTime = "";
        } else {
            this.endTime = str6;
        }
        if ((i2 & 2048) == 0) {
            this.name = "";
        } else {
            this.name = str7;
        }
        if ((i2 & 4096) == 0) {
            this.paperId = "";
        } else {
            this.paperId = str8;
        }
        if ((i2 & 8192) == 0) {
            this.paperScore = "";
        } else {
            this.paperScore = str9;
        }
        if ((i2 & 16384) == 0) {
            this.questionCount = -1;
        } else {
            this.questionCount = i3;
        }
        if ((i2 & 32768) == 0) {
            this.startTime = "";
        } else {
            this.startTime = str10;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamRecordVo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.candidateId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.candidateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.caseName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.caseName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.degree, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.degree);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.examId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || (num2 = self.patientId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 5, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.score, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num3 = self.status) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || (num4 = self.timeCost) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 8, IntSerializer.INSTANCE, self.timeCost);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || (num5 = self.trainId) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 9, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.endTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.endTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.paperId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.paperId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.paperScore, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.paperScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || self.questionCount != -1) {
            output.encodeIntElement(serialDesc, 14, self.questionCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual(self.startTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 15, StringSerializer.INSTANCE, self.startTime);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getEndTime() {
        return this.endTime;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getPaperId() {
        return this.paperId;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPaperScore() {
        return this.paperScore;
    }

    /* renamed from: component15, reason: from getter */
    public final int getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getStartTime() {
        return this.startTime;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCaseName() {
        return this.caseName;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getDegree() {
        return this.degree;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getScore() {
        return this.score;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    @NotNull
    public final ExamRecordVo copy(@Nullable Integer candidateId, @Nullable String caseName, @Nullable String degree, @Nullable String examId, @Nullable String id, @Nullable Integer patientId, @Nullable String score, @Nullable Integer status, @Nullable Integer timeCost, @Nullable Integer trainId, @Nullable String endTime, @Nullable String name, @Nullable String paperId, @Nullable String paperScore, int questionCount, @Nullable String startTime) {
        return new ExamRecordVo(candidateId, caseName, degree, examId, id, patientId, score, status, timeCost, trainId, endTime, name, paperId, paperScore, questionCount, startTime);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamRecordVo)) {
            return false;
        }
        ExamRecordVo examRecordVo = (ExamRecordVo) other;
        return Intrinsics.areEqual(this.candidateId, examRecordVo.candidateId) && Intrinsics.areEqual(this.caseName, examRecordVo.caseName) && Intrinsics.areEqual(this.degree, examRecordVo.degree) && Intrinsics.areEqual(this.examId, examRecordVo.examId) && Intrinsics.areEqual(this.id, examRecordVo.id) && Intrinsics.areEqual(this.patientId, examRecordVo.patientId) && Intrinsics.areEqual(this.score, examRecordVo.score) && Intrinsics.areEqual(this.status, examRecordVo.status) && Intrinsics.areEqual(this.timeCost, examRecordVo.timeCost) && Intrinsics.areEqual(this.trainId, examRecordVo.trainId) && Intrinsics.areEqual(this.endTime, examRecordVo.endTime) && Intrinsics.areEqual(this.name, examRecordVo.name) && Intrinsics.areEqual(this.paperId, examRecordVo.paperId) && Intrinsics.areEqual(this.paperScore, examRecordVo.paperScore) && this.questionCount == examRecordVo.questionCount && Intrinsics.areEqual(this.startTime, examRecordVo.startTime);
    }

    @Nullable
    public final Integer getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    public final String getCaseName() {
        return this.caseName;
    }

    @Nullable
    public final String getDegree() {
        return this.degree;
    }

    @Nullable
    public final String getEndTime() {
        return this.endTime;
    }

    @Nullable
    public final String getExamId() {
        return this.examId;
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
    public final String getPaperScore() {
        return this.paperScore;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    public final int getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    public final String getScore() {
        return this.score;
    }

    @Nullable
    public final String getStartTime() {
        return this.startTime;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    public int hashCode() {
        Integer num = this.candidateId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.caseName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.degree;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.examId;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.id;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Integer num2 = this.patientId;
        int iHashCode6 = (iHashCode5 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str5 = this.score;
        int iHashCode7 = (iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        Integer num3 = this.status;
        int iHashCode8 = (iHashCode7 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.timeCost;
        int iHashCode9 = (iHashCode8 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.trainId;
        int iHashCode10 = (iHashCode9 + (num5 == null ? 0 : num5.hashCode())) * 31;
        String str6 = this.endTime;
        int iHashCode11 = (iHashCode10 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.name;
        int iHashCode12 = (iHashCode11 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.paperId;
        int iHashCode13 = (iHashCode12 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.paperScore;
        int iHashCode14 = (((iHashCode13 + (str9 == null ? 0 : str9.hashCode())) * 31) + this.questionCount) * 31;
        String str10 = this.startTime;
        return iHashCode14 + (str10 != null ? str10.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamRecordVo(candidateId=" + this.candidateId + ", caseName=" + this.caseName + ", degree=" + this.degree + ", examId=" + this.examId + ", id=" + this.id + ", patientId=" + this.patientId + ", score=" + this.score + ", status=" + this.status + ", timeCost=" + this.timeCost + ", trainId=" + this.trainId + ", endTime=" + this.endTime + ", name=" + this.name + ", paperId=" + this.paperId + ", paperScore=" + this.paperScore + ", questionCount=" + this.questionCount + ", startTime=" + this.startTime + ")";
    }

    public ExamRecordVo(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable Integer num2, @Nullable String str5, @Nullable Integer num3, @Nullable Integer num4, @Nullable Integer num5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, int i2, @Nullable String str10) {
        this.candidateId = num;
        this.caseName = str;
        this.degree = str2;
        this.examId = str3;
        this.id = str4;
        this.patientId = num2;
        this.score = str5;
        this.status = num3;
        this.timeCost = num4;
        this.trainId = num5;
        this.endTime = str6;
        this.name = str7;
        this.paperId = str8;
        this.paperScore = str9;
        this.questionCount = i2;
        this.startTime = str10;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ExamRecordVo(Integer num, String str, String str2, String str3, String str4, Integer num2, String str5, Integer num3, Integer num4, Integer num5, String str6, String str7, String str8, String str9, int i2, String str10, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? num : num, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? "" : str2, (i3 & 8) != 0 ? "" : str3, (i3 & 16) != 0 ? "" : str4, (i3 & 32) != 0 ? num : num2, (i3 & 64) != 0 ? "" : str5, (i3 & 128) != 0 ? num : num3, (i3 & 256) != 0 ? num : num4, (i3 & 512) == 0 ? num5 : -1, (i3 & 1024) != 0 ? "" : str6, (i3 & 2048) != 0 ? "" : str7, (i3 & 4096) != 0 ? "" : str8, (i3 & 8192) != 0 ? "" : str9, (i3 & 16384) != 0 ? -1 : i2, (i3 & 32768) != 0 ? "" : str10);
    }
}
