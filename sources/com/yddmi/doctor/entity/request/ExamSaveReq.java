package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.yddmi.doctor.entity.result.ExamQuestion;
import com.yddmi.doctor.entity.result.ExamQuestion$$serializer;
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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 /2\u00020\u0001:\u0002./BG\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0010\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB1\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\t¢\u0006\u0002\u0010\u000eJ\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u0013\u0010 \u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\tHÆ\u0003J;\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\tHÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\u0003HÖ\u0001J\t\u0010&\u001a\u00020\u0005HÖ\u0001J!\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-HÇ\u0001R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016¨\u00060"}, d2 = {"Lcom/yddmi/doctor/entity/request/ExamSaveReq;", "", "seen1", "", "paperId", "", "examId", "candidateId", "list", "", "Lcom/yddmi/doctor/entity/result/ExamQuestion;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;ILjava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;ILjava/util/List;)V", "getCandidateId", "()I", "setCandidateId", "(I)V", "getExamId", "()Ljava/lang/String;", "setExamId", "(Ljava/lang/String;)V", "getList", "()Ljava/util/List;", "setList", "(Ljava/util/List;)V", "getPaperId", "setPaperId", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamSaveReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int candidateId;

    @NotNull
    private String examId;

    @Nullable
    private List<ExamQuestion> list;

    @NotNull
    private String paperId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/ExamSaveReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/ExamSaveReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamSaveReq> serializer() {
            return ExamSaveReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamSaveReq(int i2, String str, String str2, int i3, List list, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, ExamSaveReq$$serializer.INSTANCE.getDescriptor());
        }
        this.paperId = str;
        this.examId = str2;
        this.candidateId = i3;
        if ((i2 & 8) == 0) {
            this.list = null;
        } else {
            this.list = list;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExamSaveReq copy$default(ExamSaveReq examSaveReq, String str, String str2, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = examSaveReq.paperId;
        }
        if ((i3 & 2) != 0) {
            str2 = examSaveReq.examId;
        }
        if ((i3 & 4) != 0) {
            i2 = examSaveReq.candidateId;
        }
        if ((i3 & 8) != 0) {
            list = examSaveReq.list;
        }
        return examSaveReq.copy(str, str2, i2, list);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamSaveReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.paperId);
        output.encodeStringElement(serialDesc, 1, self.examId);
        output.encodeIntElement(serialDesc, 2, self.candidateId);
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.list != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestion$$serializer.INSTANCE)), self.list);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getPaperId() {
        return this.paperId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getCandidateId() {
        return this.candidateId;
    }

    @Nullable
    public final List<ExamQuestion> component4() {
        return this.list;
    }

    @NotNull
    public final ExamSaveReq copy(@NotNull String paperId, @NotNull String examId, int candidateId, @Nullable List<ExamQuestion> list) {
        Intrinsics.checkNotNullParameter(paperId, "paperId");
        Intrinsics.checkNotNullParameter(examId, "examId");
        return new ExamSaveReq(paperId, examId, candidateId, list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamSaveReq)) {
            return false;
        }
        ExamSaveReq examSaveReq = (ExamSaveReq) other;
        return Intrinsics.areEqual(this.paperId, examSaveReq.paperId) && Intrinsics.areEqual(this.examId, examSaveReq.examId) && this.candidateId == examSaveReq.candidateId && Intrinsics.areEqual(this.list, examSaveReq.list);
    }

    public final int getCandidateId() {
        return this.candidateId;
    }

    @NotNull
    public final String getExamId() {
        return this.examId;
    }

    @Nullable
    public final List<ExamQuestion> getList() {
        return this.list;
    }

    @NotNull
    public final String getPaperId() {
        return this.paperId;
    }

    public int hashCode() {
        int iHashCode = ((((this.paperId.hashCode() * 31) + this.examId.hashCode()) * 31) + this.candidateId) * 31;
        List<ExamQuestion> list = this.list;
        return iHashCode + (list == null ? 0 : list.hashCode());
    }

    public final void setCandidateId(int i2) {
        this.candidateId = i2;
    }

    public final void setExamId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.examId = str;
    }

    public final void setList(@Nullable List<ExamQuestion> list) {
        this.list = list;
    }

    public final void setPaperId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paperId = str;
    }

    @NotNull
    public String toString() {
        return "ExamSaveReq(paperId=" + this.paperId + ", examId=" + this.examId + ", candidateId=" + this.candidateId + ", list=" + this.list + ")";
    }

    public ExamSaveReq(@NotNull String paperId, @NotNull String examId, int i2, @Nullable List<ExamQuestion> list) {
        Intrinsics.checkNotNullParameter(paperId, "paperId");
        Intrinsics.checkNotNullParameter(examId, "examId");
        this.paperId = paperId;
        this.examId = examId;
        this.candidateId = i2;
        this.list = list;
    }

    public /* synthetic */ ExamSaveReq(String str, String str2, int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i2, (i3 & 8) != 0 ? null : list);
    }
}
