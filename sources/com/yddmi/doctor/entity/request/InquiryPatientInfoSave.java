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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 &2\u00020\u0001:\u0002%&B3\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB%\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J)\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001d\u001a\u00020\u0006HÖ\u0001J!\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$HÇ\u0001R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012¨\u0006'"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryPatientInfoSave;", "", "seen1", "", "patientId", "examId", "", "isExam", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;I)V", "getExamId", "()Ljava/lang/String;", "setExamId", "(Ljava/lang/String;)V", "()I", "setExam", "(I)V", "getPatientId", "setPatientId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryPatientInfoSave {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String examId;
    private int isExam;
    private int patientId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryPatientInfoSave$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryPatientInfoSave;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryPatientInfoSave> serializer() {
            return InquiryPatientInfoSave$$serializer.INSTANCE;
        }
    }

    public InquiryPatientInfoSave() {
        this(0, (String) null, 0, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryPatientInfoSave(int i2, int i3, String str, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryPatientInfoSave$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.patientId = -1;
        } else {
            this.patientId = i3;
        }
        if ((i2 & 2) == 0) {
            this.examId = "";
        } else {
            this.examId = str;
        }
        if ((i2 & 4) == 0) {
            this.isExam = -1;
        } else {
            this.isExam = i4;
        }
    }

    public static /* synthetic */ InquiryPatientInfoSave copy$default(InquiryPatientInfoSave inquiryPatientInfoSave, int i2, String str, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = inquiryPatientInfoSave.patientId;
        }
        if ((i4 & 2) != 0) {
            str = inquiryPatientInfoSave.examId;
        }
        if ((i4 & 4) != 0) {
            i3 = inquiryPatientInfoSave.isExam;
        }
        return inquiryPatientInfoSave.copy(i2, str, i3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryPatientInfoSave self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.patientId != -1) {
            output.encodeIntElement(serialDesc, 0, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.examId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.isExam != -1) {
            output.encodeIntElement(serialDesc, 2, self.isExam);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getExamId() {
        return this.examId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getIsExam() {
        return this.isExam;
    }

    @NotNull
    public final InquiryPatientInfoSave copy(int patientId, @Nullable String examId, int isExam) {
        return new InquiryPatientInfoSave(patientId, examId, isExam);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryPatientInfoSave)) {
            return false;
        }
        InquiryPatientInfoSave inquiryPatientInfoSave = (InquiryPatientInfoSave) other;
        return this.patientId == inquiryPatientInfoSave.patientId && Intrinsics.areEqual(this.examId, inquiryPatientInfoSave.examId) && this.isExam == inquiryPatientInfoSave.isExam;
    }

    @Nullable
    public final String getExamId() {
        return this.examId;
    }

    public final int getPatientId() {
        return this.patientId;
    }

    public int hashCode() {
        int i2 = this.patientId * 31;
        String str = this.examId;
        return ((i2 + (str == null ? 0 : str.hashCode())) * 31) + this.isExam;
    }

    public final int isExam() {
        return this.isExam;
    }

    public final void setExam(int i2) {
        this.isExam = i2;
    }

    public final void setExamId(@Nullable String str) {
        this.examId = str;
    }

    public final void setPatientId(int i2) {
        this.patientId = i2;
    }

    @NotNull
    public String toString() {
        return "InquiryPatientInfoSave(patientId=" + this.patientId + ", examId=" + this.examId + ", isExam=" + this.isExam + ")";
    }

    public InquiryPatientInfoSave(int i2, @Nullable String str, int i3) {
        this.patientId = i2;
        this.examId = str;
        this.isExam = i3;
    }

    public /* synthetic */ InquiryPatientInfoSave(int i2, String str, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? -1 : i2, (i4 & 2) != 0 ? "" : str, (i4 & 4) != 0 ? -1 : i3);
    }
}
