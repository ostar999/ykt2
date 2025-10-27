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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 82\u00020\u0001:\u000278B}\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011B}\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0012J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010!\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0016J\u0086\u0001\u0010)\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010*J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020\u0003HÖ\u0001J\t\u0010/\u001a\u00020\u0005HÖ\u0001J!\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206HÇ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0007\u0010\u0016R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001a\u0010\u0016R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001b\u0010\u0016R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001d\u0010\u0016R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001e\u0010\u0016¨\u00069"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo;", "", "seen1", "", "degree", "", "id", "isExam", "lastBeginTime", "lastStopTime", "patientId", "score", "status", "timeCost", "userId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getDegree", "()Ljava/lang/String;", "getId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getLastBeginTime", "getLastStopTime", "getPatientId", "getScore", "getStatus", "getTimeCost", "getUserId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ScoreAnalyseTrainInfo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("degree")
    @Nullable
    private final String degree;

    @SerializedName("id")
    @Nullable
    private final Integer id;

    @SerializedName("isExam")
    @Nullable
    private final Integer isExam;

    @SerializedName("lastBeginTime")
    @Nullable
    private final String lastBeginTime;

    @SerializedName("lastStopTime")
    @Nullable
    private final String lastStopTime;

    @SerializedName("patientId")
    @Nullable
    private final Integer patientId;

    @SerializedName("score")
    @Nullable
    private final Integer score;

    @SerializedName("status")
    @Nullable
    private final Integer status;

    @SerializedName("timeCost")
    @Nullable
    private final Integer timeCost;

    @SerializedName("userId")
    @Nullable
    private final Integer userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ScoreAnalyseTrainInfo> serializer() {
            return ScoreAnalyseTrainInfo$$serializer.INSTANCE;
        }
    }

    public ScoreAnalyseTrainInfo() {
        this((String) null, (Integer) null, (Integer) null, (String) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, 1023, (DefaultConstructorMarker) null);
    }

    public ScoreAnalyseTrainInfo(@Nullable String str, @Nullable Integer num, @Nullable Integer num2, @Nullable String str2, @Nullable String str3, @Nullable Integer num3, @Nullable Integer num4, @Nullable Integer num5, @Nullable Integer num6, @Nullable Integer num7) {
        this.degree = str;
        this.id = num;
        this.isExam = num2;
        this.lastBeginTime = str2;
        this.lastStopTime = str3;
        this.patientId = num3;
        this.score = num4;
        this.status = num5;
        this.timeCost = num6;
        this.userId = num7;
    }

    @JvmStatic
    public static final void write$Self(@NotNull ScoreAnalyseTrainInfo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.degree, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.degree);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.id) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num2 = self.isExam) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.isExam);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.lastBeginTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.lastBeginTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.lastStopTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.lastStopTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || (num3 = self.patientId) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 5, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num4 = self.score) == null || num4.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num5 = self.status) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || (num6 = self.timeCost) == null || num6.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 8, IntSerializer.INSTANCE, self.timeCost);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || (num7 = self.userId) == null || num7.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 9, IntSerializer.INSTANCE, self.userId);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getDegree() {
        return this.degree;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final Integer getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getIsExam() {
        return this.isExam;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getLastBeginTime() {
        return this.lastBeginTime;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getLastStopTime() {
        return this.lastStopTime;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getScore() {
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
    public final ScoreAnalyseTrainInfo copy(@Nullable String degree, @Nullable Integer id, @Nullable Integer isExam, @Nullable String lastBeginTime, @Nullable String lastStopTime, @Nullable Integer patientId, @Nullable Integer score, @Nullable Integer status, @Nullable Integer timeCost, @Nullable Integer userId) {
        return new ScoreAnalyseTrainInfo(degree, id, isExam, lastBeginTime, lastStopTime, patientId, score, status, timeCost, userId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreAnalyseTrainInfo)) {
            return false;
        }
        ScoreAnalyseTrainInfo scoreAnalyseTrainInfo = (ScoreAnalyseTrainInfo) other;
        return Intrinsics.areEqual(this.degree, scoreAnalyseTrainInfo.degree) && Intrinsics.areEqual(this.id, scoreAnalyseTrainInfo.id) && Intrinsics.areEqual(this.isExam, scoreAnalyseTrainInfo.isExam) && Intrinsics.areEqual(this.lastBeginTime, scoreAnalyseTrainInfo.lastBeginTime) && Intrinsics.areEqual(this.lastStopTime, scoreAnalyseTrainInfo.lastStopTime) && Intrinsics.areEqual(this.patientId, scoreAnalyseTrainInfo.patientId) && Intrinsics.areEqual(this.score, scoreAnalyseTrainInfo.score) && Intrinsics.areEqual(this.status, scoreAnalyseTrainInfo.status) && Intrinsics.areEqual(this.timeCost, scoreAnalyseTrainInfo.timeCost) && Intrinsics.areEqual(this.userId, scoreAnalyseTrainInfo.userId);
    }

    @Nullable
    public final String getDegree() {
        return this.degree;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final String getLastBeginTime() {
        return this.lastBeginTime;
    }

    @Nullable
    public final String getLastStopTime() {
        return this.lastStopTime;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final Integer getScore() {
        return this.score;
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
    public final Integer getUserId() {
        return this.userId;
    }

    public int hashCode() {
        String str = this.degree;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.id;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.isExam;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str2 = this.lastBeginTime;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.lastStopTime;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num3 = this.patientId;
        int iHashCode6 = (iHashCode5 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.score;
        int iHashCode7 = (iHashCode6 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.status;
        int iHashCode8 = (iHashCode7 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.timeCost;
        int iHashCode9 = (iHashCode8 + (num6 == null ? 0 : num6.hashCode())) * 31;
        Integer num7 = this.userId;
        return iHashCode9 + (num7 != null ? num7.hashCode() : 0);
    }

    @Nullable
    public final Integer isExam() {
        return this.isExam;
    }

    @NotNull
    public String toString() {
        return "ScoreAnalyseTrainInfo(degree=" + this.degree + ", id=" + this.id + ", isExam=" + this.isExam + ", lastBeginTime=" + this.lastBeginTime + ", lastStopTime=" + this.lastStopTime + ", patientId=" + this.patientId + ", score=" + this.score + ", status=" + this.status + ", timeCost=" + this.timeCost + ", userId=" + this.userId + ")";
    }

    public /* synthetic */ ScoreAnalyseTrainInfo(String str, Integer num, Integer num2, String str2, String str3, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? -1 : num, (i2 & 4) != 0 ? -1 : num2, (i2 & 8) != 0 ? "" : str2, (i2 & 16) == 0 ? str3 : "", (i2 & 32) != 0 ? -1 : num3, (i2 & 64) != 0 ? 0 : num4, (i2 & 128) != 0 ? -1 : num5, (i2 & 256) != 0 ? 0 : num6, (i2 & 512) != 0 ? -1 : num7);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ScoreAnalyseTrainInfo(int i2, String str, Integer num, Integer num2, String str2, String str3, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ScoreAnalyseTrainInfo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.degree = "";
        } else {
            this.degree = str;
        }
        if ((i2 & 2) == 0) {
            this.id = -1;
        } else {
            this.id = num;
        }
        if ((i2 & 4) == 0) {
            this.isExam = -1;
        } else {
            this.isExam = num2;
        }
        if ((i2 & 8) == 0) {
            this.lastBeginTime = "";
        } else {
            this.lastBeginTime = str2;
        }
        if ((i2 & 16) == 0) {
            this.lastStopTime = "";
        } else {
            this.lastStopTime = str3;
        }
        if ((i2 & 32) == 0) {
            this.patientId = -1;
        } else {
            this.patientId = num3;
        }
        if ((i2 & 64) == 0) {
            this.score = 0;
        } else {
            this.score = num4;
        }
        if ((i2 & 128) == 0) {
            this.status = -1;
        } else {
            this.status = num5;
        }
        if ((i2 & 256) == 0) {
            this.timeCost = 0;
        } else {
            this.timeCost = num6;
        }
        if ((i2 & 512) == 0) {
            this.userId = -1;
        } else {
            this.userId = num7;
        }
    }
}
