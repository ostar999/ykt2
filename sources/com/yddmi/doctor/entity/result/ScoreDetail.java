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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 +2\u00020\u0001:\u0002*+BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB?\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0011JH\u0010\u001c\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001dJ\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\u0003HÖ\u0001J\t\u0010\"\u001a\u00020\u0005HÖ\u0001J!\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)HÇ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0015\u0010\u0011R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0016\u0010\u0011¨\u0006,"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreDetail;", "", "seen1", "", "description", "", "gradeId", "score", "trainId", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;ILjava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/Integer;Ljava/lang/Integer;)V", "getDescription", "()Ljava/lang/String;", "getGradeId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getScore", "()I", "getTrainId", "getType", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/lang/Integer;ILjava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ScoreDetail;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ScoreDetail {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("description")
    @Nullable
    private final String description;

    @SerializedName("gradeId")
    @Nullable
    private final Integer gradeId;

    @SerializedName("score")
    private final int score;

    @SerializedName("trainId")
    @Nullable
    private final Integer trainId;

    @SerializedName("type")
    @Nullable
    private final Integer type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ScoreDetail$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ScoreDetail;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ScoreDetail> serializer() {
            return ScoreDetail$$serializer.INSTANCE;
        }
    }

    public ScoreDetail() {
        this((String) null, (Integer) null, 0, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ScoreDetail(int i2, String str, Integer num, int i3, Integer num2, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ScoreDetail$$serializer.INSTANCE.getDescriptor());
        }
        this.description = (i2 & 1) == 0 ? "" : str;
        if ((i2 & 2) == 0) {
            this.gradeId = -1;
        } else {
            this.gradeId = num;
        }
        if ((i2 & 4) == 0) {
            this.score = 0;
        } else {
            this.score = i3;
        }
        if ((i2 & 8) == 0) {
            this.trainId = -1;
        } else {
            this.trainId = num2;
        }
        if ((i2 & 16) == 0) {
            this.type = -1;
        } else {
            this.type = num3;
        }
    }

    public static /* synthetic */ ScoreDetail copy$default(ScoreDetail scoreDetail, String str, Integer num, int i2, Integer num2, Integer num3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = scoreDetail.description;
        }
        if ((i3 & 2) != 0) {
            num = scoreDetail.gradeId;
        }
        Integer num4 = num;
        if ((i3 & 4) != 0) {
            i2 = scoreDetail.score;
        }
        int i4 = i2;
        if ((i3 & 8) != 0) {
            num2 = scoreDetail.trainId;
        }
        Integer num5 = num2;
        if ((i3 & 16) != 0) {
            num3 = scoreDetail.type;
        }
        return scoreDetail.copy(str, num4, i4, num5, num3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ScoreDetail self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.gradeId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.gradeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.score != 0) {
            output.encodeIntElement(serialDesc, 2, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num2 = self.trainId) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num3 = self.type) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.type);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getGradeId() {
        return this.gradeId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getType() {
        return this.type;
    }

    @NotNull
    public final ScoreDetail copy(@Nullable String description, @Nullable Integer gradeId, int score, @Nullable Integer trainId, @Nullable Integer type) {
        return new ScoreDetail(description, gradeId, score, trainId, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreDetail)) {
            return false;
        }
        ScoreDetail scoreDetail = (ScoreDetail) other;
        return Intrinsics.areEqual(this.description, scoreDetail.description) && Intrinsics.areEqual(this.gradeId, scoreDetail.gradeId) && this.score == scoreDetail.score && Intrinsics.areEqual(this.trainId, scoreDetail.trainId) && Intrinsics.areEqual(this.type, scoreDetail.type);
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final Integer getGradeId() {
        return this.gradeId;
    }

    public final int getScore() {
        return this.score;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    public final Integer getType() {
        return this.type;
    }

    public int hashCode() {
        String str = this.description;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.gradeId;
        int iHashCode2 = (((iHashCode + (num == null ? 0 : num.hashCode())) * 31) + this.score) * 31;
        Integer num2 = this.trainId;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.type;
        return iHashCode3 + (num3 != null ? num3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ScoreDetail(description=" + this.description + ", gradeId=" + this.gradeId + ", score=" + this.score + ", trainId=" + this.trainId + ", type=" + this.type + ")";
    }

    public ScoreDetail(@Nullable String str, @Nullable Integer num, int i2, @Nullable Integer num2, @Nullable Integer num3) {
        this.description = str;
        this.gradeId = num;
        this.score = i2;
        this.trainId = num2;
        this.type = num3;
    }

    public /* synthetic */ ScoreDetail(String str, Integer num, int i2, Integer num2, Integer num3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? -1 : num, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? -1 : num2, (i3 & 16) != 0 ? -1 : num3);
    }
}
