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

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 K2\u00020\u0001:\u0002JKB¿\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u0010\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017¢\u0006\u0002\u0010\u0018BÉ\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0019J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u0010\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u000b\u00101\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u0010\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u0010\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001dJ\u000b\u00105\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0013\u00109\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\fHÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0013\u0010;\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\fHÆ\u0003JÒ\u0001\u0010<\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\u0012\b\u0002\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0012\b\u0002\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010A\u001a\u00020\u0003HÖ\u0001J\t\u0010B\u001a\u00020\u0005HÖ\u0001J!\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u00002\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IHÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u001b\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u001b\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001bR\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b(\u0010\u001dR\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b)\u0010\u001dR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001bR\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b+\u0010\u001dR\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b,\u0010\u001d¨\u0006L"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamResultVo;", "", "seen1", "", "candidateId", "", "candidateSize", "content", "contentType", "endTime", "examId", "examRecordVoList", "", "Lcom/yddmi/doctor/entity/result/ExamRecordVo;", "examScore", "examTrainRecordVoList", "id", "paperFinishCount", "patientFinishCount", "startTime", "status", "timeCost", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getCandidateId", "()Ljava/lang/String;", "getCandidateSize", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getContent", "getContentType", "getEndTime", "getExamId", "getExamRecordVoList", "()Ljava/util/List;", "getExamScore", "getExamTrainRecordVoList", "getId", "getPaperFinishCount", "getPatientFinishCount", "getStartTime", "getStatus", "getTimeCost", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ExamResultVo;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamResultVo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String candidateId;

    @Nullable
    private final Integer candidateSize;

    @Nullable
    private final String content;

    @Nullable
    private final String contentType;

    @Nullable
    private final String endTime;

    @Nullable
    private final String examId;

    @Nullable
    private final List<ExamRecordVo> examRecordVoList;

    @Nullable
    private final String examScore;

    @Nullable
    private final List<ExamRecordVo> examTrainRecordVoList;

    @Nullable
    private final String id;

    @Nullable
    private final Integer paperFinishCount;

    @Nullable
    private final Integer patientFinishCount;

    @Nullable
    private final String startTime;

    @Nullable
    private final Integer status;

    @Nullable
    private final Integer timeCost;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamResultVo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamResultVo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamResultVo> serializer() {
            return ExamResultVo$$serializer.INSTANCE;
        }
    }

    public ExamResultVo() {
        this((String) null, (Integer) null, (String) null, (String) null, (String) null, (String) null, (List) null, (String) null, (List) null, (String) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, 32767, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamResultVo(int i2, String str, Integer num, String str2, String str3, String str4, String str5, List list, String str6, List list2, String str7, Integer num2, Integer num3, String str8, Integer num4, Integer num5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamResultVo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.candidateId = "";
        } else {
            this.candidateId = str;
        }
        this.candidateSize = (i2 & 2) == 0 ? -1 : num;
        if ((i2 & 4) == 0) {
            this.content = "";
        } else {
            this.content = str2;
        }
        if ((i2 & 8) == 0) {
            this.contentType = "";
        } else {
            this.contentType = str3;
        }
        if ((i2 & 16) == 0) {
            this.endTime = "";
        } else {
            this.endTime = str4;
        }
        if ((i2 & 32) == 0) {
            this.examId = "";
        } else {
            this.examId = str5;
        }
        if ((i2 & 64) == 0) {
            this.examRecordVoList = null;
        } else {
            this.examRecordVoList = list;
        }
        if ((i2 & 128) == 0) {
            this.examScore = "";
        } else {
            this.examScore = str6;
        }
        if ((i2 & 256) == 0) {
            this.examTrainRecordVoList = null;
        } else {
            this.examTrainRecordVoList = list2;
        }
        if ((i2 & 512) == 0) {
            this.id = "";
        } else {
            this.id = str7;
        }
        this.paperFinishCount = (i2 & 1024) == 0 ? -1 : num2;
        this.patientFinishCount = (i2 & 2048) == 0 ? -1 : num3;
        if ((i2 & 4096) == 0) {
            this.startTime = "";
        } else {
            this.startTime = str8;
        }
        this.status = (i2 & 8192) == 0 ? -1 : num4;
        this.timeCost = (i2 & 16384) == 0 ? -1 : num5;
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamResultVo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.candidateId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.candidateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.candidateSize) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.candidateSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.contentType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.contentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.endTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.endTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.examId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.examRecordVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 6, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRecordVo$$serializer.INSTANCE)), self.examRecordVoList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.examScore, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.examScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.examTrainRecordVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 8, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRecordVo$$serializer.INSTANCE)), self.examTrainRecordVoList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || (num2 = self.paperFinishCount) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 10, IntSerializer.INSTANCE, self.paperFinishCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || (num3 = self.patientFinishCount) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 11, IntSerializer.INSTANCE, self.patientFinishCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.startTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.startTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || (num4 = self.status) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 13, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || (num5 = self.timeCost) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 14, IntSerializer.INSTANCE, self.timeCost);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final Integer getPaperFinishCount() {
        return this.paperFinishCount;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final Integer getPatientFinishCount() {
        return this.patientFinishCount;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getStartTime() {
        return this.startTime;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getCandidateSize() {
        return this.candidateSize;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getContentType() {
        return this.contentType;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getEndTime() {
        return this.endTime;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    @Nullable
    public final List<ExamRecordVo> component7() {
        return this.examRecordVoList;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getExamScore() {
        return this.examScore;
    }

    @Nullable
    public final List<ExamRecordVo> component9() {
        return this.examTrainRecordVoList;
    }

    @NotNull
    public final ExamResultVo copy(@Nullable String candidateId, @Nullable Integer candidateSize, @Nullable String content, @Nullable String contentType, @Nullable String endTime, @Nullable String examId, @Nullable List<ExamRecordVo> examRecordVoList, @Nullable String examScore, @Nullable List<ExamRecordVo> examTrainRecordVoList, @Nullable String id, @Nullable Integer paperFinishCount, @Nullable Integer patientFinishCount, @Nullable String startTime, @Nullable Integer status, @Nullable Integer timeCost) {
        return new ExamResultVo(candidateId, candidateSize, content, contentType, endTime, examId, examRecordVoList, examScore, examTrainRecordVoList, id, paperFinishCount, patientFinishCount, startTime, status, timeCost);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamResultVo)) {
            return false;
        }
        ExamResultVo examResultVo = (ExamResultVo) other;
        return Intrinsics.areEqual(this.candidateId, examResultVo.candidateId) && Intrinsics.areEqual(this.candidateSize, examResultVo.candidateSize) && Intrinsics.areEqual(this.content, examResultVo.content) && Intrinsics.areEqual(this.contentType, examResultVo.contentType) && Intrinsics.areEqual(this.endTime, examResultVo.endTime) && Intrinsics.areEqual(this.examId, examResultVo.examId) && Intrinsics.areEqual(this.examRecordVoList, examResultVo.examRecordVoList) && Intrinsics.areEqual(this.examScore, examResultVo.examScore) && Intrinsics.areEqual(this.examTrainRecordVoList, examResultVo.examTrainRecordVoList) && Intrinsics.areEqual(this.id, examResultVo.id) && Intrinsics.areEqual(this.paperFinishCount, examResultVo.paperFinishCount) && Intrinsics.areEqual(this.patientFinishCount, examResultVo.patientFinishCount) && Intrinsics.areEqual(this.startTime, examResultVo.startTime) && Intrinsics.areEqual(this.status, examResultVo.status) && Intrinsics.areEqual(this.timeCost, examResultVo.timeCost);
    }

    @Nullable
    public final String getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    public final Integer getCandidateSize() {
        return this.candidateSize;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final String getContentType() {
        return this.contentType;
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
    public final List<ExamRecordVo> getExamRecordVoList() {
        return this.examRecordVoList;
    }

    @Nullable
    public final String getExamScore() {
        return this.examScore;
    }

    @Nullable
    public final List<ExamRecordVo> getExamTrainRecordVoList() {
        return this.examTrainRecordVoList;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final Integer getPaperFinishCount() {
        return this.paperFinishCount;
    }

    @Nullable
    public final Integer getPatientFinishCount() {
        return this.patientFinishCount;
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

    public int hashCode() {
        String str = this.candidateId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.candidateSize;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str2 = this.content;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.contentType;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.endTime;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.examId;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        List<ExamRecordVo> list = this.examRecordVoList;
        int iHashCode7 = (iHashCode6 + (list == null ? 0 : list.hashCode())) * 31;
        String str6 = this.examScore;
        int iHashCode8 = (iHashCode7 + (str6 == null ? 0 : str6.hashCode())) * 31;
        List<ExamRecordVo> list2 = this.examTrainRecordVoList;
        int iHashCode9 = (iHashCode8 + (list2 == null ? 0 : list2.hashCode())) * 31;
        String str7 = this.id;
        int iHashCode10 = (iHashCode9 + (str7 == null ? 0 : str7.hashCode())) * 31;
        Integer num2 = this.paperFinishCount;
        int iHashCode11 = (iHashCode10 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.patientFinishCount;
        int iHashCode12 = (iHashCode11 + (num3 == null ? 0 : num3.hashCode())) * 31;
        String str8 = this.startTime;
        int iHashCode13 = (iHashCode12 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Integer num4 = this.status;
        int iHashCode14 = (iHashCode13 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.timeCost;
        return iHashCode14 + (num5 != null ? num5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamResultVo(candidateId=" + this.candidateId + ", candidateSize=" + this.candidateSize + ", content=" + this.content + ", contentType=" + this.contentType + ", endTime=" + this.endTime + ", examId=" + this.examId + ", examRecordVoList=" + this.examRecordVoList + ", examScore=" + this.examScore + ", examTrainRecordVoList=" + this.examTrainRecordVoList + ", id=" + this.id + ", paperFinishCount=" + this.paperFinishCount + ", patientFinishCount=" + this.patientFinishCount + ", startTime=" + this.startTime + ", status=" + this.status + ", timeCost=" + this.timeCost + ")";
    }

    public ExamResultVo(@Nullable String str, @Nullable Integer num, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable List<ExamRecordVo> list, @Nullable String str6, @Nullable List<ExamRecordVo> list2, @Nullable String str7, @Nullable Integer num2, @Nullable Integer num3, @Nullable String str8, @Nullable Integer num4, @Nullable Integer num5) {
        this.candidateId = str;
        this.candidateSize = num;
        this.content = str2;
        this.contentType = str3;
        this.endTime = str4;
        this.examId = str5;
        this.examRecordVoList = list;
        this.examScore = str6;
        this.examTrainRecordVoList = list2;
        this.id = str7;
        this.paperFinishCount = num2;
        this.patientFinishCount = num3;
        this.startTime = str8;
        this.status = num4;
        this.timeCost = num5;
    }

    public /* synthetic */ ExamResultVo(String str, Integer num, String str2, String str3, String str4, String str5, List list, String str6, List list2, String str7, Integer num2, Integer num3, String str8, Integer num4, Integer num5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? -1 : num, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? "" : str5, (i2 & 64) != 0 ? null : list, (i2 & 128) != 0 ? "" : str6, (i2 & 256) == 0 ? list2 : null, (i2 & 512) != 0 ? "" : str7, (i2 & 1024) != 0 ? -1 : num2, (i2 & 2048) != 0 ? -1 : num3, (i2 & 4096) == 0 ? str8 : "", (i2 & 8192) != 0 ? -1 : num4, (i2 & 16384) != 0 ? -1 : num5);
    }
}
