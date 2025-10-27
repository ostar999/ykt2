package com.yddmi.doctor.entity.request;

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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 #2\u00020\u0001:\u0002\"#B+\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J\u001d\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0006HÖ\u0001J!\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/entity/request/ExamUpdateReq;", "", "seen1", "", "candidateId", "examId", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;)V", "getCandidateId", "()I", "setCandidateId", "(I)V", "getExamId", "()Ljava/lang/String;", "setExamId", "(Ljava/lang/String;)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamUpdateReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int candidateId;

    @NotNull
    private String examId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/ExamUpdateReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/ExamUpdateReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamUpdateReq> serializer() {
            return ExamUpdateReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamUpdateReq(int i2, int i3, String str, SerializationConstructorMarker serializationConstructorMarker) {
        if (3 != (i2 & 3)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 3, ExamUpdateReq$$serializer.INSTANCE.getDescriptor());
        }
        this.candidateId = i3;
        this.examId = str;
    }

    public static /* synthetic */ ExamUpdateReq copy$default(ExamUpdateReq examUpdateReq, int i2, String str, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = examUpdateReq.candidateId;
        }
        if ((i3 & 2) != 0) {
            str = examUpdateReq.examId;
        }
        return examUpdateReq.copy(i2, str);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamUpdateReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.candidateId);
        output.encodeStringElement(serialDesc, 1, self.examId);
    }

    /* renamed from: component1, reason: from getter */
    public final int getCandidateId() {
        return this.candidateId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    @NotNull
    public final ExamUpdateReq copy(int candidateId, @NotNull String examId) {
        Intrinsics.checkNotNullParameter(examId, "examId");
        return new ExamUpdateReq(candidateId, examId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamUpdateReq)) {
            return false;
        }
        ExamUpdateReq examUpdateReq = (ExamUpdateReq) other;
        return this.candidateId == examUpdateReq.candidateId && Intrinsics.areEqual(this.examId, examUpdateReq.examId);
    }

    public final int getCandidateId() {
        return this.candidateId;
    }

    @NotNull
    public final String getExamId() {
        return this.examId;
    }

    public int hashCode() {
        return (this.candidateId * 31) + this.examId.hashCode();
    }

    public final void setCandidateId(int i2) {
        this.candidateId = i2;
    }

    public final void setExamId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.examId = str;
    }

    @NotNull
    public String toString() {
        return "ExamUpdateReq(candidateId=" + this.candidateId + ", examId=" + this.examId + ")";
    }

    public ExamUpdateReq(int i2, @NotNull String examId) {
        Intrinsics.checkNotNullParameter(examId, "examId");
        this.candidateId = i2;
        this.examId = examId;
    }
}
