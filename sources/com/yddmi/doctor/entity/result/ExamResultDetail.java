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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 *2\u00020\u0001:\u0002)*BK\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fBA\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJJ\u0010\u001b\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0003HÖ\u0001J\t\u0010!\u001a\u00020\u0006HÖ\u0001J!\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u0015\u0010\u000f¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamResultDetail;", "", "seen1", "", "maxScore", "questionCount", "", "rank", "score", "timeCost", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V", "getMaxScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getQuestionCount", "()Ljava/lang/String;", "getRank", "getScore", "getTimeCost", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ExamResultDetail;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamResultDetail {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer maxScore;

    @Nullable
    private final String questionCount;

    @Nullable
    private final String rank;

    @Nullable
    private final String score;

    @Nullable
    private final Integer timeCost;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamResultDetail$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamResultDetail;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamResultDetail> serializer() {
            return ExamResultDetail$$serializer.INSTANCE;
        }
    }

    public ExamResultDetail() {
        this((Integer) null, (String) null, (String) null, (String) null, (Integer) null, 31, (DefaultConstructorMarker) null);
    }

    public ExamResultDetail(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Integer num2) {
        this.maxScore = num;
        this.questionCount = str;
        this.rank = str2;
        this.score = str3;
        this.timeCost = num2;
    }

    public static /* synthetic */ ExamResultDetail copy$default(ExamResultDetail examResultDetail, Integer num, String str, String str2, String str3, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = examResultDetail.maxScore;
        }
        if ((i2 & 2) != 0) {
            str = examResultDetail.questionCount;
        }
        String str4 = str;
        if ((i2 & 4) != 0) {
            str2 = examResultDetail.rank;
        }
        String str5 = str2;
        if ((i2 & 8) != 0) {
            str3 = examResultDetail.score;
        }
        String str6 = str3;
        if ((i2 & 16) != 0) {
            num2 = examResultDetail.timeCost;
        }
        return examResultDetail.copy(num, str4, str5, str6, num2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamResultDetail self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.maxScore) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.maxScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.questionCount, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.questionCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.rank, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.rank);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.score, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num2 = self.timeCost) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.timeCost);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getMaxScore() {
        return this.maxScore;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getRank() {
        return this.rank;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getScore() {
        return this.score;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    @NotNull
    public final ExamResultDetail copy(@Nullable Integer maxScore, @Nullable String questionCount, @Nullable String rank, @Nullable String score, @Nullable Integer timeCost) {
        return new ExamResultDetail(maxScore, questionCount, rank, score, timeCost);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamResultDetail)) {
            return false;
        }
        ExamResultDetail examResultDetail = (ExamResultDetail) other;
        return Intrinsics.areEqual(this.maxScore, examResultDetail.maxScore) && Intrinsics.areEqual(this.questionCount, examResultDetail.questionCount) && Intrinsics.areEqual(this.rank, examResultDetail.rank) && Intrinsics.areEqual(this.score, examResultDetail.score) && Intrinsics.areEqual(this.timeCost, examResultDetail.timeCost);
    }

    @Nullable
    public final Integer getMaxScore() {
        return this.maxScore;
    }

    @Nullable
    public final String getQuestionCount() {
        return this.questionCount;
    }

    @Nullable
    public final String getRank() {
        return this.rank;
    }

    @Nullable
    public final String getScore() {
        return this.score;
    }

    @Nullable
    public final Integer getTimeCost() {
        return this.timeCost;
    }

    public int hashCode() {
        Integer num = this.maxScore;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.questionCount;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.rank;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.score;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num2 = this.timeCost;
        return iHashCode4 + (num2 != null ? num2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamResultDetail(maxScore=" + this.maxScore + ", questionCount=" + this.questionCount + ", rank=" + this.rank + ", score=" + this.score + ", timeCost=" + this.timeCost + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamResultDetail(int i2, Integer num, String str, String str2, String str3, Integer num2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamResultDetail$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.maxScore = 0;
        } else {
            this.maxScore = num;
        }
        if ((i2 & 2) == 0) {
            this.questionCount = "";
        } else {
            this.questionCount = str;
        }
        if ((i2 & 4) == 0) {
            this.rank = "";
        } else {
            this.rank = str2;
        }
        if ((i2 & 8) == 0) {
            this.score = "";
        } else {
            this.score = str3;
        }
        if ((i2 & 16) == 0) {
            this.timeCost = 0;
        } else {
            this.timeCost = num2;
        }
    }

    public /* synthetic */ ExamResultDetail(Integer num, String str, String str2, String str3, Integer num2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) == 0 ? str3 : "", (i2 & 16) != 0 ? 0 : num2);
    }
}
